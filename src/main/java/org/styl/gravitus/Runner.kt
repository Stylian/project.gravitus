package org.styl.gravitus

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import org.apache.commons.io.FileUtils
import org.apache.log4j.Logger
import org.styl.gravitus.entities.Simulation
import org.styl.gravitus.entities.Stage
import java.io.File
import java.io.IOException
import kotlin.system.exitProcess

class Runner {

	private val logger: Logger = Logger.getLogger(Runner::class.java)

	var simulation: Simulation? = null
		private set

	private val stages: MutableList<String> = mutableListOf()

	init {
		stages.add("lvl-1.json")
	}

	fun initNextStage() {
		if (stages.isNotEmpty()) {
			logger.info("attempting to load next stage...")

			val stageName = stages.first() // get first stage name
			simulation = Simulation(loadStage(stageName))

			logger.info("stage ${stageName.substringBefore('.')} has been loaded.")
			logger.info("${simulation?.engine?.objects?.size ?: 0} space objects have been created!")
		} else {
			gameOver()
		}
	}

	private fun loadStage(nameOfStage: String): Stage {
		val stageFile = File(SystemLocations.stagesFolder + nameOfStage)

		return try {
			Gson().fromJson(FileUtils.readFileToString(stageFile, Charsets.UTF_8), Stage::class.java)
		} catch (e: JsonSyntaxException) {
			logger.error("Error with stage JSON file format!", e)
			logger.error("Program will now terminate!")
			exitProcess(0)
		} catch (e: IOException) {
			logger.error("Failed to load stage!", e)
			logger.error("Program will now terminate!")
			exitProcess(0)
		}
	}

	private fun gameOver() {
		logger.info("GAME OVER! TODO")
	}

	fun nextTick() {
		simulation?.tick()
	}

	fun reset() {
		simulation?.engine?.objects?.clear()
		logger.info("Simulation has been reset!")
	}
}
