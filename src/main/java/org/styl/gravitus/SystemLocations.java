package org.styl.gravitus;

import java.io.File;

public abstract class SystemLocations {

	public static final String USER_HOME_FOLDER = System.getProperty("user.home") + File.separator + "Documents" +
			File.separator + "Gravitus" + File.separator;
	
	public static final String RESOURCES_FOLDER = "resources";
	
	public static final String IMAGES_FOLDER = RESOURCES_FOLDER + File.separator + "images/";
	public static final String ICONS_FOLDER = RESOURCES_FOLDER + File.separator + "icons/";
	public static final String STAGES_FOLDER = RESOURCES_FOLDER + File.separator + "stages/";
	
	public static final String USER_PROPERTIES_FILE = USER_HOME_FOLDER + "application.properties";
	public static final String DEFAULT_PROPERTIES_FILE = RESOURCES_FOLDER + File.separator + "specs.properties";
	
	
	
}
