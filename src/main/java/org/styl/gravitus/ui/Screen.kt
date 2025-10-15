package org.styl.gravitus.ui

import org.styl.gravitus.Specs
import java.awt.*
import java.awt.event.ActionListener
import javax.swing.*

@Suppress("serial")
class Screen : JPanel() {

	var listener: ActionListener? = null

	lateinit var fps: JLabel
		private set
	lateinit var zoomOut: JButton
		private set
	lateinit var zoomIn: JButton
		private set

	fun init() {
		fps = JLabel().apply {
			foreground = Color.WHITE
			horizontalTextPosition = SwingConstants.RIGHT
			setBounds((Specs.instance?.frameX ?: 800) - 60, 8, 70, 10)
		}
		add(fps)

		zoomIn = JButton().apply {
			setContentAreaFilled(false)
			setBorderPainted(false)
			cursor = Cursor(Cursor.HAND_CURSOR)
			setBounds((Specs.instance?.frameX ?: 800) - 60, 30, 16, 16)
			icon = ImageIcon("resources/icons/plus.png")
			actionCommand = "zoomIn"
			listener?.let { addActionListener(it) }
		}
		add(zoomIn)

		zoomOut = JButton().apply {
			setContentAreaFilled(false)
			setBorderPainted(false)
			cursor = Cursor(Cursor.HAND_CURSOR)
			setBounds((Specs.instance?.frameX ?: 800) - 35, 30, 16, 16)
			icon = ImageIcon("resources/icons/minus.png")
			actionCommand = "zoomOut"
			listener?.let { addActionListener(it) }
		}
		add(zoomOut)

		background = Color.BLACK
		layout = null
	}

	override fun paintComponent(g: Graphics) {
		super.paintComponent(g)

		if (Specs.instance?.orbitTrails == true) {
			g.color = Color.WHITE
			val g2d = g as Graphics2D

			for (cmpnt in components) {
				if (cmpnt is SpaceObjectUIWrapper) {
					var prev: Point? = null
					for (p in cmpnt.pastPositions) {
						prev?.let { g2d.drawLine(p.x, p.y, it.x, it.y) }
						prev = p
					}
				}
			}
		}
	}
}
