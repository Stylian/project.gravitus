package org.styl.gravitus.ui

import org.styl.gravitus.Specs
import org.styl.gravitus.engine.Clock
import java.awt.Point

data class Bounds(val minX: Int, val maxX: Int, val minY: Int, val maxY: Int)

class Renderer(
	private val screen: Screen,
	val wrappers: MutableList<SpaceObjectUIWrapper> = mutableListOf()
) {

	private var zoom: Int = 1000

	val bounds = getBounds(wrappers)

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
		val x = mapX(so.position.x)
		val y = mapY(so.position.y)
		val rf = (so.radius * 1414 * zoom / 1_000_000)

		w.setLocation(x - rf, y - rf)

		if (Specs.instance?.orbitTrails == true) {
			applyOrbitTrails(x, y, w)
		}
	}

	private fun mapX(x: Double): Int {
		var posX = x - (bounds.minX - bounds.maxX)/4

		return (posX * zoom / 1_000_000).toInt()
	}

	private fun mapY(y: Double): Int {
		var posY = y - (bounds.minY - bounds.maxY)/4

		return (posY * zoom / 1_000_000).toInt()
	}

	private fun applyOrbitTrails(x: Int, y: Int, w: SpaceObjectUIWrapper) {
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

	fun getBounds(wrappers: List<SpaceObjectUIWrapper>): Bounds {
		var minX = Int.MAX_VALUE
		var maxX = Int.MIN_VALUE
		var minY = Int.MAX_VALUE
		var maxY = Int.MIN_VALUE

		for (wrapper in wrappers) {
			val obj = wrapper.spaceObject
			val left = (obj.position.x - obj.radius).toInt()
			val right = (obj.position.x + obj.radius).toInt()
			val top = (obj.position.y - obj.radius).toInt()
			val bottom = (obj.position.y + obj.radius).toInt()

			if (left < minX) minX = left
			if (right > maxX) maxX = right
			if (top < minY) minY = top
			if (bottom > maxY) maxY = bottom
		}

		return Bounds(minX, maxX, minY, maxY)
	}

	private fun isClose(newPoint: Point, oldPoint: Point): Boolean {
		val dx = newPoint.x - oldPoint.x
		val dy = newPoint.y - oldPoint.y
		val dist = Math.sqrt((dx * dx + dy * dy).toDouble())
		return dist < 10
	}
}
