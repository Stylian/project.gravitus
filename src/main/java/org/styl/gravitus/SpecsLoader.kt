package org.styl.gravitus

import org.apache.commons.io.FileUtils
import org.apache.log4j.Logger
import java.io.File
import java.io.IOException

class SpecsLoader {

	private val logger: Logger = Logger.getLogger(SpecsLoader::class.java)

	init {
		logger.info("getting properties from file")

		Specs.createInstance()

		val userFile = File(SystemLocations.userPropertiesFile)

		if (userFile.exists()) {
			try {
				Specs.instance.loadProperties(userFile)
			} catch (e: IOException) {
				logger.error("application.properties file is corrupted.", e)
			}
		} else {
			logger.info("could not find user properties!")
			logger.info("attempting to load default properties!")

			try {
				Specs.instance.loadProperties(File(SystemLocations.defaultPropertiesFile))
			} catch (e: IOException) {
				logger.error("failed to load properties! The application will now terminate.", e)
				kotlin.system.exitProcess(0)
			}

			try {
				createUserFile()
				logger.info("created user preferences.")
			} catch (e: IOException) {
				logger.error("failed to create user preferences.", e)
			}
		}
	}

	@Throws(IOException::class)
	private fun createUserFile() {
		FileUtils.copyFile(
			File(SystemLocations.defaultPropertiesFile),
			File(SystemLocations.userPropertiesFile)
		)
	}
}
