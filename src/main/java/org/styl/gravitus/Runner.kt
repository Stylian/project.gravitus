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
	private var currentStageIndex = 3 // to reset

	init {
		loadStageList()
	}

	/** Loads all JSON stage files from the stages folder */
	private fun loadStageList() {
		val folder = File(SystemLocations.stagesFolder)
		if (!folder.exists() || !folder.isDirectory) {
			logger.error("Stages folder not found: ${folder.absolutePath}")
			exitProcess(0)
		}

		val jsonFiles = folder.listFiles { _, name ->
			name.endsWith(".json", ignoreCase = true)
		}?.sortedBy { it.name } // you can sort alphabetically (lvl-1.json, lvl-2.json, etc.)

		if (jsonFiles.isNullOrEmpty()) {
			logger.error("No stage files found in folder: ${folder.absolutePath}")
			exitProcess(0)
		}

		stages.clear()
		stages.addAll(jsonFiles.map { it.name })

		logger.info("Discovered ${stages.size} stage(s): $stages")
	}

	fun initNextStage() {
		if (currentStageIndex < stages.size) {
			val stageName = stages[currentStageIndex]
			logger.info("Attempting to load stage: $stageName")

			simulation = Simulation(loadStage(stageName))

			logger.info("Stage ${stageName.substringBefore('.')} has been loaded.")
			logger.info("${simulation?.engine?.objects?.size ?: 0} space objects have been created!")

			currentStageIndex++
		} else {
			gameOver()
		}
	}

	fun getStageList(): List<String> {
		return stages.toList()
	}

	fun initStageByName(stageName: String) {
		logger.info("Attempting to load stage by name: $stageName")

		if (!stages.contains(stageName)) {
			logger.error("Stage not found: $stageName")
			return
		}

		simulation = Simulation(loadStage(stageName))

		logger.info("Stage ${stageName.substringBefore('.')} has been loaded.")
		logger.info("${simulation?.engine?.objects?.size ?: 0} space objects have been created!")
	}

	private fun loadStage(nameOfStage: String): Stage {
		val stageFile = File(SystemLocations.stagesFolder, nameOfStage)

		return try {
			Gson().fromJson(FileUtils.readFileToString(stageFile, Charsets.UTF_8), Stage::class.java)
		} catch (e: JsonSyntaxException) {
			logger.error("Error with stage JSON file format in $nameOfStage!", e)
			exitProcess(0)
		} catch (e: IOException) {
			logger.error("Failed to load stage file $nameOfStage!", e)
			exitProcess(0)
		}
	}

	private fun gameOver() {
		logger.info("GAME OVER! All stages completed.")
	}

	fun nextTick() {
		simulation?.tick()
	}

	fun reset() {
		simulation?.engine?.objects?.clear()
		logger.info("Simulation has been reset!")
	}
}
