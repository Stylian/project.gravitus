package org.styl.gravitus

import java.io.File

object SystemLocations {
	private val sep = File.separator

	val userHomeFolder = System.getProperty("user.home") + sep + "Gravitus$sep"
	val resourcesFolder = "src${sep}main${sep}resources"

	val imagesFolder = "$resourcesFolder${sep}images$sep"
	val iconsFolder = "$resourcesFolder${sep}icons$sep"
	val stagesFolder = "$resourcesFolder${sep}stages$sep"

	val userPropertiesFile = "${userHomeFolder}specs.properties"
	val defaultPropertiesFile = "$resourcesFolder${sep}specs.properties"
}
