package eu.stratosphere.meteor.client.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

/**
 * Utility class for accessing HDFS Files
 * 
 * 
 * 
 * @author mleich
 *
 */
public class HDFSUtil {
	
	/**
	 * writes the contents of the file/directory to the passed writer
	 * if the path is a directory, all the content of all files in the directory is returned
	 * nesting is not supported, directories in directories are ignored
	 * 
	 * @param hdfsPath
	 * @param writer
	 * @throws Exception
	 */
	@SuppressWarnings({ "deprecation" })
	public static void getHDFSContent(String hdfsPath, Writer writer) throws Exception {
		// split full hdfs path into host and path component... regex ftw!
		// first group is host, second is path
		Pattern pattern = Pattern
				.compile("(hdfs://[a-zA-Z0-9\\.:\\-]+)(/[a-zA-Z\\./_]+)");
		Matcher matcher = pattern.matcher(hdfsPath);
		String host = null;
		String path = null;
		if (matcher.find()) {
			host = matcher.group(1);
			path = matcher.group(2);
		}

		if (host == null) {
			throw new Exception("Coud not parse HDFS path: " + hdfsPath);
		} else {

			Path pt = new Path(path);
			Configuration conf = new Configuration();
//			final Class<?> clazz = conf.getClass("fs.hdfs.impl", null);
//			if (clazz == null) {
//				throw new IOException("No FileSystem found for "
//						+ "fs.hdfs.impl");
//			}
			// TODO: mleich code for CDH4 Version
//			final Class<?> clazz = FileSystem.getFileSystemClass("hdfs", conf);
//			FileSystem fs = null;
//			fs = (org.apache.hadoop.fs.FileSystem) clazz.newInstance();
			
			final Class<?> clazz = conf.getClass("fs.hdfs.impl", null);
			if (clazz == null) {
				throw new IOException("No FileSystem found for fs.hdfs.impl");
			}
			FileSystem fs = (org.apache.hadoop.fs.FileSystem) clazz.newInstance();
			
			// For HDFS we have to have an authority
			URI name = URI.create(host);
			// Initialize HDFS
			fs.initialize(name, conf); 
			if (fs != null) {
				FileStatus[] files;
				if (fs.getFileStatus(pt).isDir()) {
					// enumerate all files in directory and merge content
					// we ignore nested paths!
					files = fs.listStatus(pt);
				} else {
					// just put the one path in the list
					files = new FileStatus[] { fs.getFileStatus(pt) };
				}
				
				char[] buffer = new char[4 * 1024];
				for (FileStatus file : files) {
					if (!file.isDir()) {
						System.out.println("trying to open " + file.getPath().toString());
						BufferedReader br = new BufferedReader(new InputStreamReader(fs.open(file.getPath())));
						int len = br.read(buffer);
						while (len > 0) {
							writer.write(buffer, 0, len);
							len = br.read(buffer);
						}
						br.close();
					}
				}
				fs.close();
				System.out.println("done reading hdfs");
			}
		}
	}

}
