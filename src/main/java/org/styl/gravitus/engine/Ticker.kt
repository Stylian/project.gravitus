package org.styl.gravitus.engine

import org.apache.log4j.Logger

class Ticker : Runnable {

	private val logger: Logger = Logger.getLogger(Ticker::class.java)

	var listener: EngineTicksListener? = null
	var running: Boolean = false
	var fps: Int = 0

	override fun run() {
		logger.info("starting clock at $fps fps")

		val skipTicks = if (fps != 0) 1000 / fps else 25
		var previous = tickTime()
		var delta = 0.0

		while (running) {
			val current = tickTime()
			val elapsed = current - previous
			previous = current

			if (delta < 0.0) delta = 0.0
			delta += elapsed.toDouble() / skipTicks

			if (delta < 1.0) {
				update()
				Clock.INSTANCE.delay()
			} else {
				render()
				delta--
			}
		}
	}

	private fun tickTime(): Int {
		return System.currentTimeMillis().toInt()
	}

	private fun update() {
		listener?.updateData()
	}

	private fun render() {
		listener?.updateUI()
	}
}
