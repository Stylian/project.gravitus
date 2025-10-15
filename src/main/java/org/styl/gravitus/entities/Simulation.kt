package org.styl.gravitus.entities

import org.apache.log4j.Logger
import org.styl.gravitus.engine.ProccessFailureException
import org.styl.gravitus.engine.Ticker
import org.styl.gravitus.engine.UniverseEngine

class Simulation(private val stage: Stage) {

	private val logger: Logger = Logger.getLogger(Simulation::class.java)

	companion object {
		const val RUNNING = 1
		const val PAUSED = 2
		const val STOPPED = 3
	}

	var fps: Int = 0
	var status: Int = STOPPED
	var engine: UniverseEngine = UniverseEngine()
		private set
	var ticker: Ticker = Ticker()
		private set
	private var thread: Thread? = null

	init {
		loadStage(stage)
	}

	private fun loadStage(stage: Stage) {
		engine.objects = stage.objects
	}

	@Throws(ProccessFailureException::class)
	fun start() {
		ticker.fps = if (fps == 0) 40 else fps
		ticker.isRunning = true
		thread = Thread(ticker)
		thread?.start()
		status = RUNNING
		logger.info("Simulation started at $fps FPS.")
	}

	@Throws(ProccessFailureException::class)
	fun pause() {
		ticker.isRunning = false
		status = PAUSED
		logger.info("Simulation paused.")
	}

	@Throws(ProccessFailureException::class, InterruptedException::class)
	fun stop() {
		ticker.isRunning = false
		thread?.join()
		status = STOPPED
		logger.info("Simulation stopped.")
	}

	fun tick() {
		engine.estimateTick()
	}
}
