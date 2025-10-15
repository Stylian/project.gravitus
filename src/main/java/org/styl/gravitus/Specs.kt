package org.styl.gravitus

import org.apache.log4j.Logger
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.util.Properties

class Specs private constructor() {

	private val logger: Logger = Logger.getLogger(Specs::class.java)

	var clockInterval: Int = 0
	var G: Double = 0.0
	var fpsRenderPeriod: Int = 0

	var orbitTrails: Boolean = false
	var orbitsFixed: Boolean = false
	var orbitTrailFrequency: Int = 0
	var orbitTrailMaxSize: Int = 0

	var frameX: Int = 0
	var frameY: Int = 0

	companion object {
		lateinit var instance: Specs

		fun createInstance() {
			instance = Specs()
		}
	}

	@Throws(IOException::class)
	fun loadProperties(file: File) {
		val prop = Properties().apply {
			load(FileInputStream(file))
		}

		clockInterval = prop.getProperty("clock_interval")?.toInt() ?: 0
		G = prop.getProperty("gravitational_constant(G)")?.toDouble() ?: 0.0
		fpsRenderPeriod = prop.getProperty("fps_indicator_render_period")?.toInt() ?: 0

		orbitTrails = prop.getProperty("orbit_trails")?.toIntOrNull() != 0
		orbitsFixed = prop.getProperty("orbit_fixed")?.toIntOrNull() != 0
		orbitTrailFrequency = prop.getProperty("orbit_trail_frequency")?.toInt() ?: 0
		orbitTrailMaxSize = prop.getProperty("orbit_trail_max_size")?.toInt() ?: 0

		frameX = prop.getProperty("frame_x")?.toInt() ?: 0
		frameY = prop.getProperty("frame_y")?.toInt() ?: 0

		logger.info("properties loaded successfully!")
	}
}
