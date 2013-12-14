/***********************************************************************************************************************
 *
 * Copyright (C) 2010 by the Stratosphere project (http://stratosphere.eu)
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

import it.unimi.dsi.fastutil.bytes.ByteArrayList;
import it.unimi.dsi.fastutil.chars.CharArrayList;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.stratosphere.util.StreamUtil;

/**
 * @author arv
 */
public class PackageClassLoader extends ClassLoader {
	private final List<JarInfo> jarInfos = new ArrayList<JarInfo>();

	public PackageClassLoader() {
		super();
	}

	public PackageClassLoader(final ClassLoader parent) {
		super(parent);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.ClassLoader#loadClass(java.lang.String, boolean)
	 */
	@Override
	protected synchronized Class<?> loadClass(final String name, final boolean resolve) throws ClassNotFoundException {

		// First, check if the class has already been loaded
		Class<?> c = this.findLoadedClass(name);
		if (c == null)
			try {
				c = this.findClass(name);
			} catch (final ClassNotFoundException e) {
				if (this.getParent() != null)
					c = this.getParent().loadClass(name);
			}
		if (resolve)
			this.resolveClass(c);
		return c;
	}

	@Override
	protected Class<?> findClass(final String name) throws ClassNotFoundException {
		for (final JarInfo jarInfo : this.jarInfos) {
			final Class<?> clazz = jarInfo.findClass(name);
			if (clazz != null)
				return clazz;
		}
		throw new ClassNotFoundException(name);
	}

	@Override
	protected URL findResource(final String name) {
		for (final JarInfo jarInfo : this.jarInfos) {
			final URL resource = jarInfo.findResource(name);
			if (resource != null)
				return resource;
		}
		return null;
	}

	public List<File> getFiles() {
		final List<File> files = new ArrayList<File>();
		for (final JarInfo jarInfo : this.jarInfos)
			jarInfo.collectFiles(files);
		return files;
	}

	/**
	 * @param jarFileLocation
	 */
	public void addJar(final File jarFileLocation) {
		try {
			this.jarInfos.add(new JarInfo(jarFileLocation));
		} catch (final IOException e) {
			LOG.error("Error loading jar " + jarFileLocation, e);
		}
	}

	private static final Log LOG = LogFactory.getLog(PackageClassLoader.class);

	private final class JarInfo implements Closeable {
		private File file;

		private JarFile jarFile;

		private InputStream inputStream;

		private final Map<String, JarEntry> containedClasses = new HashMap<String, JarEntry>();

		private final Map<String, JarEntry> containedResources = new HashMap<String, JarEntry>();

		private final Set<JarInfo> containedInfos = new HashSet<JarInfo>();

		/**
		 * Initializes PackageClassLoader.JarInfo.
		 */
		public JarInfo(final InputStream inputStream) {
			this.inputStream = inputStream;
		}

		/**
		 * Returns the file.
		 * 
		 * @return the file
		 */
		public File getFile() {
			if (this.file == null)
				this.cache();
			return this.file;
		}

		public void collectFiles(final List<File> files) {
			files.add(this.getFile());
			for (final JarInfo info : this.containedInfos)
				info.collectFiles(files);
		}

		/**
		 * @param name
		 * @return
		 */
		public URL findResource(final String name) {
			if (this.jarFile == null)
				this.cache();
			final JarEntry jarEntry = this.containedResources.get(name);
			if (jarEntry == null) {
				for (final JarInfo info : this.containedInfos) {
					final URL resource = info.findResource(name);
					if (resource != null)
						return resource;
				}
				return null;
			}
			try {
				return new URL("jar", "", -1,
					String.format("%s!/%s", this.file.toURI().toURL(), jarEntry.getName()));
			} catch (final MalformedURLException e) {
				LOG.error("Error loading resource " + name, e);
				return null;
			}
		}

		/**
		 * @param name
		 * @return
		 */
		public Class<?> findClass(final String name) {
			if (this.file == null)
				this.cache();
			final JarEntry jarEntry = this.containedClasses.get(name);
			if (jarEntry == null) {
				for (final JarInfo info : this.containedInfos) {
					final Class<?> clazz = info.findClass(name);
					if (clazz != null)
						return clazz;
				}
				return null;
			}
			final ByteArrayList buffer = new ByteArrayList((int) jarEntry.getSize());
			try {
				StreamUtil.readFully(this.jarFile.getInputStream(jarEntry), buffer);
				return PackageClassLoader.this.defineClass(name, buffer.elements(), 0, buffer.size());
			} catch (final IOException e) {
				LOG.error("Error loading class " + name, e);
				return null;
			}
		}

		/**
		 * 
		 */
		private void cache() {
			try {
				this.file = StreamUtil.cacheFile(this.inputStream);
				this.loadJar();
			} catch (final IOException e) {
				LOG.error(e.getMessage(), e);
			}
		}

		/*
		 * (non-Javadoc)
		 * @see java.io.Closeable#close()
		 */
		@Override
		public void close() throws IOException {
			if (this.inputStream != null)
				this.inputStream.close();
			if (this.jarFile != null)
				this.jarFile.close();
		}

		/**
		 * Initializes JarInfo.
		 * 
		 * @param jarFileLocation
		 */
		public JarInfo(final File jarFileLocation) throws IOException {
			this.file = jarFileLocation;
			this.loadJar();
		}

		public void loadJar() throws IOException {
			this.jarFile = new JarFile(this.file);
			final Enumeration<JarEntry> entries = this.jarFile.entries();
			while (entries.hasMoreElements()) {
				final JarEntry jarEntry = entries.nextElement();
				final String entryName = jarEntry.getName();
				if (entryName.endsWith(".class")) {
					final CharArrayList chars = new CharArrayList(entryName.toCharArray());
					final int length = chars.size() - ".class".length();
					chars.size(length);
					final char[] charArray = chars.elements();
					for (int index = 0; index < length; index++)
						if (charArray[index] == '/')
							charArray[index] = '.';
					this.containedClasses.put(new String(charArray, 0, length), jarEntry);
				}
				else if (entryName.endsWith(".jar"))
					this.containedInfos.add(new JarInfo(this.jarFile.getInputStream(jarEntry)));
				else
					this.containedResources.put(entryName, jarEntry);
			}
		}

	}
}
