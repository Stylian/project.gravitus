package org.styl.gravitus

import org.apache.log4j.Logger
import org.styl.gravitus.engine.EngineTicksListener
import org.styl.gravitus.engine.ProccessFailureException
import org.styl.gravitus.entities.Simulation
import org.styl.gravitus.ui.Renderer
import org.styl.gravitus.ui.SpaceObjectUIWrapper
import org.styl.gravitus.ui.View
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.JRadioButtonMenuItem

class Controller(
	private val view: View,
	private val runner: Runner
) : EngineTicksListener, ActionListener {

	private val logger: Logger = Logger.getLogger(Controller::class.java)
	private lateinit var renderer: Renderer

	init {
		view.toolBar.listener = this
		view.screen.listener = this
		view.screen.init()
		view.toolBar.init()
	}

	// EngineTicksListener implementations
	override fun updateData() {
		runner.nextTick()
	}

	override fun updateUI() {
		renderer.render()
	}

	// ActionListener implementation
	override fun actionPerformed(e: ActionEvent) {
		when (e.actionCommand) {
			"start" -> startSimulation()
			"pause" -> {
				try {
					pauseSimulation()
				} catch (ex: InterruptedException) {
					ex.printStackTrace()
				}
			}
			"stop" -> {
				try {
					stopSimulation()
				} catch (ex: InterruptedException) {
					ex.printStackTrace()
				}
			}
			"change_fps" -> {
				val btnText = (e.source as JRadioButtonMenuItem).text
				runner.simulation?.fps = btnText.toInt()
			}
			"exit" -> view.dispose()
			"trails" -> {
				renderer.wrappers.forEach { it.pastPositions.clear() }
				Specs.instance.orbitTrails = !Specs.instance.orbitTrails
			}
			"orbit_path" -> {
				Specs.instance.orbitsFixed = !Specs.instance.orbitsFixed
			}
			"zoomIn" -> renderer.zoomBy(100)
			"zoomOut" -> renderer.zoomBy(-100)
		}
	}

	private fun initSimulation() {
		logger.info("initializing simulation")

		runner.initNextStage()

		val wrappers = runner.simulation?.engine?.objects
			?.map { SpaceObjectUIWrapper(it) }
			?.toMutableList() ?: mutableListOf()

		renderer = Renderer(view.screen, wrappers)
		runner.simulation?.ticker?.listener = this

		view.toolBar.prefsMenu.isEnabled = true
		wrappers.forEach { view.screen.add(it) }
	}

	fun startSimulation() {
		logger.info("attempting to start simulation")

		val sim = runner.simulation
		if (sim == null || sim.status == Simulation.STOPPED) {
			initSimulation()
		}

		try {
			runner.simulation?.start()
			runner.simulation?.status = Simulation.RUNNING
			logger.info("simulation started!")
		} catch (e: ProccessFailureException) {
			logger.error("failed to start simulation.", e)
		}

		view.toolBar.start.isEnabled = false
		view.toolBar.pause.isEnabled = true
		view.toolBar.stop.isEnabled = true
	}

	@Throws(InterruptedException::class)
	private fun pauseSimulation() {
		logger.info("attempting to pause simulation")
		try {
			runner.simulation?.pause()
			runner.simulation?.status = Simulation.PAUSED
			logger.info("simulation paused!")
		} catch (e: ProccessFailureException) {
			logger.error("failed to pause simulation.", e)
		}

		view.toolBar.pause.isEnabled = false
		view.toolBar.stop.isEnabled = true
		view.toolBar.start.isEnabled = true
		view.toolBar.start.text = "Continue"
	}

	@Throws(InterruptedException::class)
	private fun stopSimulation() {
		logger.info("attempting to stop simulation")
		try {
			runner.simulation?.stop()
			runner.simulation?.status = Simulation.STOPPED

			// clear data
			renderer.wrappers.forEach { view.screen.remove(it) }
			renderer.wrappers.clear()
			runner.simulation?.engine?.objects?.clear()

			logger.info("simulation has been reset!")
		} catch (e: ProccessFailureException) {
			logger.error("failed to stop simulation.", e)
		}

		view.toolBar.stop.isEnabled = false
		view.toolBar.pause.isEnabled = false
		view.toolBar.start.isEnabled = true
		view.toolBar.start.text = "Start"
	}
}
