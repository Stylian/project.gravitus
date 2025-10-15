package org.styl.gravitus.ui

import org.styl.gravitus.SystemLocations
import org.styl.gravitus.entities.SpaceObject
import java.awt.Dimension
import java.awt.Image
import java.awt.Point
import javax.swing.ImageIcon
import javax.swing.JLabel
import java.util.LinkedList

@Suppress("serial")
class SpaceObjectUIWrapper(val spaceObject: SpaceObject) : JLabel() {

	val pastPositions: MutableList<Point> = LinkedList()

	init {
		val imageIcon = ImageIcon(SystemLocations.imagesFolder + spaceObject.image)
		val image: Image = imageIcon.image
		val newImg: Image = image.getScaledInstance(
			spaceObject.radius * 2,
			spaceObject.radius * 2,
			Image.SCALE_SMOOTH
		)
		icon = ImageIcon(newImg)

		preferredSize = Dimension(spaceObject.radius * 2, spaceObject.radius * 2)
		setSize(spaceObject.radius * 2, spaceObject.radius * 2)
		setLocation(
			(spaceObject.position.x / 1000).toInt(),
			(spaceObject.position.y / 1000).toInt()
		)
	}
}
