package org.styl.gravitus.ui

import org.styl.gravitus.Specs
import org.styl.gravitus.engine.Clock
import java.awt.Point

class Renderer(
	private val screen: Screen,
	var wrappers: MutableList<SpaceObjectUIWrapper> = mutableListOf()
) {

	private var zoom: Int = 1000

	companion object {
		private var renderFPSCounter: Int = 0
		private var positionsCounter: Int = 0
	}

	fun render() {
		val calcFPS = Clock.INSTANCE.fps()

		if (renderFPSCounter++ > (Specs.instance?.fpsRenderPeriod ?: 0)) {
			renderFPSCounter = 0
			screen.fps.text = "$calcFPS FPS"
		}

		// update wrappers
		positionsCounter++

		wrappers.forEach { updateWrapper(it) }

		screen.repaint()
	}

	fun zoomBy(by: Int) {
		zoom += by
	}

	private fun updateWrapper(w: SpaceObjectUIWrapper) {
		val so = w.spaceObject
		val x = (so.position.x * zoom / 1_000_000).toInt()
		val y = (so.position.y * zoom / 1_000_000).toInt()
		val rf = (so.radius * 1414 * zoom / 1_000_000).toInt()

		w.setLocation(x - rf, y - rf)

		if (Specs.instance?.orbitTrails == true) {
			if (positionsCounter % (Specs.instance?.orbitTrailFrequency ?: 1) == 0) {
				positionsCounter = 0
				val newPoint = Point(x, y)
				val pastPositions = w.pastPositions

				if (Specs.instance?.orbitsFixed == false) {
					if (pastPositions.size > (Specs.instance?.orbitTrailMaxSize ?: 0)) {
						pastPositions.removeAt(0)
					}
				} else {
					if (pastPositions.size > 50) {
						if (isClose(newPoint, pastPositions[0])) {
							pastPositions.removeAt(0)
						}
					}
				}

				pastPositions.add(newPoint)
			}
		}
	}

	private fun isClose(newPoint: Point, oldPoint: Point): Boolean {
		val dx = newPoint.x - oldPoint.x
		val dy = newPoint.y - oldPoint.y
		val dist = Math.sqrt((dx * dx + dy * dy).toDouble())
		return dist < 10
	}
}
