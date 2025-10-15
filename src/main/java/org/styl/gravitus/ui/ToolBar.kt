package org.styl.gravitus.ui

import org.styl.gravitus.Specs
import org.styl.gravitus.SystemLocations
import java.awt.event.ActionListener
import javax.swing.*

@Suppress("serial")
class ToolBar : JMenuBar() {

	var listener: ActionListener? = null

	lateinit var start: JMenuItem
		private set
	lateinit var pause: JMenuItem
		private set
	lateinit var stop: JMenuItem
		private set
	lateinit var fpsMenu: JMenu
		private set
	lateinit var prefsMenu: JMenu
		private set

	fun init() {
		// File menu
		val fileMenu = JMenu("File")
		val exit = JMenuItem("Exit").apply {
			icon = ImageIcon(SystemLocations.ICONS_FOLDER + "exit.png")
			actionCommand = "exit"
			listener?.let { addActionListener(it) }
		}
		fileMenu.add(exit)
		add(fileMenu)

		// Simulation menu
		val simMenu = JMenu("Simulation")

		start = JMenuItem("Start").apply {
			icon = ImageIcon(SystemLocations.ICONS_FOLDER + "start.png")
			actionCommand = "start"
			listener?.let { addActionListener(it) }
		}
		simMenu.add(start)

		pause = JMenuItem("Pause").apply {
			icon = ImageIcon(SystemLocations.ICONS_FOLDER + "pause.png")
			isEnabled = false
			actionCommand = "pause"
			listener?.let { addActionListener(it) }
		}
		simMenu.add(pause)

		stop = JMenuItem("Stop").apply {
			icon = ImageIcon(SystemLocations.ICONS_FOLDER + "stop.png")
			isEnabled = false
			actionCommand = "stop"
			listener?.let { addActionListener(it) }
		}
		simMenu.add(stop)

		add(simMenu)

		// Preferences menu
		prefsMenu = JMenu("Preferences").apply { isEnabled = false }

		val trails = JCheckBoxMenuItem("Track Orbits").apply {
			isSelected = Specs.instance?.orbitTrails == true
			actionCommand = "trails"
			listener?.let { addActionListener(it) }
		}
		prefsMenu.add(trails)

		val orbitPath = JCheckBoxMenuItem("Fixed Orbits").apply {
			isSelected = Specs.instance?.orbitsFixed == true
			actionCommand = "orbit_path"
			listener?.let { addActionListener(it) }
		}
		prefsMenu.add(orbitPath)

		fpsMenu = JMenu("FPS")
		val fpsGroup = ButtonGroup()

		listOf(15, 20, 30, 40, 60, 120, 240).forEach { fpsValue ->
			val item = JRadioButtonMenuItem(fpsValue.toString()).apply {
				actionCommand = "change_fps"
				listener?.let { addActionListener(it) }
				if (fpsValue == 40) isSelected = true
			}
			fpsMenu.add(item)
			fpsGroup.add(item)
		}

		prefsMenu.add(fpsMenu)
		add(prefsMenu)
	}
}
