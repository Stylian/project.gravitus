package org.styl.gravitus

import java.io.File

object SystemLocations {
	private val sep = File.separator

	val userHomeFolder = System.getProperty("user.home") + sep + "Documents${sep}Gravitus$sep"
	val resourcesFolder = "resources"

	val imagesFolder = "$resourcesFolder${sep}images$sep"
	val iconsFolder = "$resourcesFolder${sep}icons$sep"
	val stagesFolder = "$resourcesFolder${sep}stages$sep"

	val userPropertiesFile = "${userHomeFolder}application.properties"
	val defaultPropertiesFile = "$resourcesFolder${sep}specs.properties"
}
