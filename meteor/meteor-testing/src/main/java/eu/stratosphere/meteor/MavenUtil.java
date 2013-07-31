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
package eu.stratosphere.meteor;

import java.io.FileReader;
import java.io.Reader;

import org.apache.maven.project.Model;
import org.apache.maven.project.io.xpp3.MavenXpp3Reader;

/**
 * @author arv
 */
public class MavenUtil {

	/**
	 * @return
	 */
	public static String getProjectName() {
		try {
			Reader reader = new FileReader("pom.xml");
			MavenXpp3Reader xpp3Reader = new MavenXpp3Reader();
			Model model = xpp3Reader.read(reader);
			reader.close();
			return model.getName();
		} catch(Exception e) {
			throw new RuntimeException("Could not read project name", e);
		}
	}
}
