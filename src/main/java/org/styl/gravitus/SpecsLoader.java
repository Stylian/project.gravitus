package org.styl.gravitus;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

public class SpecsLoader {
	final static Logger logger = Logger.getLogger(SpecsLoader.class);
	
	public SpecsLoader() {
		logger.info("getting properties from file");

		Specs.createInstance();
		
		File userFile = new File(SystemLocations.USER_PROPERTIES_FILE);
		
		if(userFile.exists()) {
			try {
				Specs.instance.loadProperties(userFile);
			} catch (IOException e) {
				logger.error("application.properties file is corrupted.");
			}
		}else {
			logger.info("could not find user properties!");
			logger.info("attempting to load default properties!");
			try {
				Specs.instance.loadProperties(new File(SystemLocations.DEFAULT_PROPERTIES_FILE));
			} catch (IOException e) {
				logger.error("failed to load properties! The application will now terminate.");
				System.exit(0);
			}
			try {
				createUserFile();
				logger.info("created user preferences.");
			} catch (IOException e) {
				logger.error("failed to create user preferences.");
			}
		}
		
	}

	private void createUserFile() throws IOException {
		FileUtils.copyFile(new File(SystemLocations.DEFAULT_PROPERTIES_FILE), new File(SystemLocations.USER_PROPERTIES_FILE));
	}
}
