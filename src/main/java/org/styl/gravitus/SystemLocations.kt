package org.styl.gravitus;

import java.io.File;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SystemLocations {

	public final String USER_HOME_FOLDER = System.getProperty("user.home") + File.separator + "Documents"
			+ File.separator + "Gravitus" + File.separator;

	public final String RESOURCES_FOLDER = "resources";

	public final String IMAGES_FOLDER = RESOURCES_FOLDER + File.separator + "images" + File.separator;
	public final String ICONS_FOLDER = RESOURCES_FOLDER + File.separator + "icons" + File.separator;
	public final String STAGES_FOLDER = RESOURCES_FOLDER + File.separator + "stages" + File.separator;

	public final String USER_PROPERTIES_FILE = USER_HOME_FOLDER + "application.properties";
	public final String DEFAULT_PROPERTIES_FILE = RESOURCES_FOLDER + File.separator + "specs.properties";

}
