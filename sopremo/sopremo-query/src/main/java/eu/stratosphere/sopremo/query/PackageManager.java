/***********************************************************************************************************************
 *
 * Copyright (C) 2010-2013 by the Stratosphere project (http://stratosphere.eu)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 **********************************************************************************************************************/
package eu.stratosphere.sopremo.query;

import java.io.File;
import java.io.FilenameFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import eu.stratosphere.sopremo.io.CsvFormat;
import eu.stratosphere.sopremo.io.JsonFormat;
import eu.stratosphere.sopremo.io.Sink;
import eu.stratosphere.sopremo.io.SopremoFormat;
import eu.stratosphere.sopremo.io.Source;
import eu.stratosphere.sopremo.operator.Operator;
import eu.stratosphere.sopremo.packages.IConstantRegistry;
import eu.stratosphere.sopremo.packages.IFunctionRegistry;

/**
 * @author Arvid Heise
 */
public class PackageManager implements ParsingScope {
	private Map<String, PackageInfo> packages = new HashMap<String, PackageInfo>();

	private List<File> jarPathLocations = new ArrayList<File>(Arrays.asList(new File(".")));

	public final static IConfObjectRegistry<Operator<?>> IORegistry = new DefaultConfObjectRegistry<Operator<?>>();

	public final static IConfObjectRegistry<SopremoFormat> DefaultFormatRegistry = new DefaultConfObjectRegistry<SopremoFormat>();

	static {
		IORegistry.put(Sink.class);
		IORegistry.put(Source.class);

		DefaultFormatRegistry.put(CsvFormat.class);
		DefaultFormatRegistry.put(JsonFormat.class);
	}

	public PackageManager() {
		this.operatorRegistries.push(IORegistry);
		this.fileFormatRegistries.push(DefaultFormatRegistry);
	}

	private StackedConstantRegistry constantRegistries = new StackedConstantRegistry();

	private StackedFunctionRegistry functionRegistries = new StackedFunctionRegistry();

	private StackedTypeRegistry typeRegistries = new StackedTypeRegistry();

	private StackedConfObjectRegistry<Operator<?>> operatorRegistries = new StackedConfObjectRegistry<Operator<?>>();

	private StackedConfObjectRegistry<SopremoFormat> fileFormatRegistries = new StackedConfObjectRegistry<SopremoFormat>();

	/**
	 * Imports sopremo-&lt;packageName&gt;.jar or returns a cached package
	 * structure.
	 * 
	 * @param packageName
	 */
	public PackageInfo getPackageInfo(String packageName) {
		PackageInfo packageInfo = this.packages.get(packageName);
		if (packageInfo == null) {
			List<File> packagePath = this.findPackageInClassPath(packageName);
			if (!packagePath.isEmpty()) {
				packageInfo = new PackageInfo(packageName, ClassLoader.getSystemClassLoader());
				File jarFile = null;
				for (File file : packagePath)
					if (file.isFile() && file.getName().endsWith(".jar")) {
						jarFile = file;
						break;
					}
				if (jarFile != null)
					packagePath = Arrays.asList(jarFile);
			} else {
				File jarLocation = this.findPackageInJarPathLocations(packageName);
				if (jarLocation == null)
					throw new IllegalArgumentException(String.format("no package %s found", packageName));
				try {
					packageInfo = new PackageInfo(packageName, new URLClassLoader(new URL[] { jarLocation.toURI().toURL() }));
				} catch (MalformedURLException e) {
					throw new IllegalStateException(e);
				}
				packagePath = Arrays.asList(jarLocation);
			}
			QueryUtil.LOG.debug("adding package " + packagePath);
			try {
				for (File path : packagePath)
					packageInfo.importFrom(path, packageName);
			} catch (Exception e) {
				throw new IllegalArgumentException(String.format("could not load package %s; please make sure that sopremo-%s.jar is in classpath",
						packagePath, packageName), e);
			}
			this.packages.put(packageName, packageInfo);
		}
		return packageInfo;
	}

	/**
	 * Returns the names of the imported packages.
	 * 
	 * @return the packages
	 */
	public Collection<PackageInfo> getImportedPackages() {
		return this.packages.values();
	}

	/**
	 * Returns the fileFormatRegistries.
	 * 
	 * @return the fileFormatRegistries
	 */
	@Override
	public IConfObjectRegistry<SopremoFormat> getFileFormatRegistry() {
		return this.fileFormatRegistries;
	}

	/**
	 * Returns the operatorFactory.
	 * 
	 * @return the operatorFactory
	 */
	@Override
	public IConfObjectRegistry<Operator<?>> getOperatorRegistry() {
		return this.operatorRegistries;
	}

	@Override
	public IConstantRegistry getConstantRegistry() {
		return this.constantRegistries;
	}

	@Override
	public IFunctionRegistry getFunctionRegistry() {
		return this.functionRegistries;
	}

	/**
	 * Returns the typeRegistries.
	 * 
	 * @return the typeRegistries
	 */
	@Override
	public StackedTypeRegistry getTypeRegistry() {
		return this.typeRegistries;
	}

	protected List<File> findPackageInClassPath(String packageName) {
		String classpath = System.getProperty("java.class.path");
		String sopremoPackage = getJarFileNameForPackageName(packageName);
		// check in class paths
		List<File> paths = new ArrayList<File>();
		for (String path : classpath.split(File.pathSeparator)) {
			final int pathIndex = path.indexOf(sopremoPackage);
			if (pathIndex == -1)
				continue;
			// preceding character must be a file separator
			if (pathIndex > 0 && path.charAt(pathIndex - 1) != File.separatorChar)
				continue;
			int nextIndex = pathIndex + sopremoPackage.length();
			// next character must be '.', '-', or file separator
			if (nextIndex < path.length() && path.charAt(nextIndex) != File.separatorChar && path.charAt(nextIndex) != '.' && path.charAt(nextIndex) != '-')
				continue;
			paths.add(new File(path));
		}

		return paths;
	}

	protected File findPackageInJarPathLocations(String packageName) {
		String sopremoPackage = getJarFileNameForPackageName(packageName);
		// look in additional directories
		final Pattern filePattern = Pattern.compile(sopremoPackage + ".*\\.jar");
		for (File jarPathLocation : this.jarPathLocations) {
			final File[] jars = jarPathLocation.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return filePattern.matcher(name).matches();
				}
			});
			if (jars.length > 0) {
				return jars[0];
			}
		}
		return null;
	}
	
	protected static String getJarFileNameForPackageName(String packageName){
		return "sopremo-"+packageName;
	}

	/**
	 * Sets the defaultJarPath to the specified value.
	 * 
	 * @param defaultJarPath
	 *            the defaultJarPath to set
	 */
	public void addJarPathLocation(File jarPathLocation) {
		if (jarPathLocation == null)
			throw new NullPointerException("jarPathLocation must not be null");

		this.jarPathLocations.add(jarPathLocation);
	}

	/**
	 * Returns the jarPathLocations.
	 * 
	 * @return the jarPathLocations
	 */
	public List<File> getJarPathLocations() {
		return this.jarPathLocations;
	}

	public void importPackage(String packageName) {
		this.importPackage(this.getPackageInfo(packageName));
	}

	public void importPackageFrom(String packageName, File directory) {
		try {
			PackageInfo packageInfo = new PackageInfo(packageName, new URLClassLoader(new URL[] { directory.getAbsoluteFile().toURI().toURL() }));
			packageInfo.importFrom(directory, packageName);
			this.packages.put(packageName, packageInfo);
			this.importPackage(packageInfo);
		} catch (Exception e) {
			throw new IllegalArgumentException("Cannot load package from directory", e);
		}
	}

	public void importPackage(PackageInfo packageInfo) {
		this.constantRegistries.push(packageInfo.getConstantRegistry());
		this.functionRegistries.push(packageInfo.getFunctionRegistry());
		this.operatorRegistries.push(packageInfo.getOperatorRegistry());
		this.fileFormatRegistries.push(packageInfo.getFileFormatRegistry());
		this.typeRegistries.push(packageInfo.getTypeRegistry());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("Package manager with packages %s", this.packages);
	}

	public void addAll(PackageManager packageManager) {
		this.constantRegistries.push(packageManager.getConstantRegistry());
		this.functionRegistries.push(packageManager.getFunctionRegistry());
		this.operatorRegistries.push(packageManager.getOperatorRegistry());
		this.fileFormatRegistries.push(packageManager.getFileFormatRegistry());
		this.typeRegistries.push(packageManager.getTypeRegistry());
		this.packages.putAll(packageManager.packages);
	}
}
