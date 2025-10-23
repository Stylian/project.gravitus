package org.styl.gravitus.ui

import org.styl.gravitus.Specs
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.JFrame

@Suppress("serial")
class View : JFrame() {

	val windowSize: Dimension = Dimension(Specs.instance?.frameX ?: 800, Specs.instance?.frameY ?: 600)

	val screen: Screen = Screen()
	val toolBar: ToolBar = ToolBar()

	init {
		layout = BorderLayout()
		add(toolBar, BorderLayout.NORTH)
		add(screen, BorderLayout.CENTER)

		title = "Gravity Simulator"
		defaultCloseOperation = EXIT_ON_CLOSE
		isResizable = false
		preferredSize = windowSize
		maximumSize = windowSize
		minimumSize = windowSize
		pack()
		setLocationRelativeTo(null)
	}
}
