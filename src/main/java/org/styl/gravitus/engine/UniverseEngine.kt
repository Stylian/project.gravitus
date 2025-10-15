package org.styl.gravitus.engine

import org.styl.gravitus.Specs
import org.styl.gravitus.entities.SpaceObject

class UniverseEngine {

	var objects: MutableList<SpaceObject> = mutableListOf()

	fun estimateTick() {
		objects.forEach { calculate(it) }
		objects.forEach { update(it) }
	}

	private fun calculate(o: SpaceObject) {
		objects.forEach { a ->
			if (o != a) {
				val dist = distance(o, a)
				val angle = angle(o, a)
				val g = Specs.instance.G * a.mass / Math.pow(dist.toDouble(), 2.0)
				val gx = g * Math.cos(angle)
				val gy = g * Math.sin(angle)
				o.acceleration.x += gx
				o.acceleration.y += gy
			}
		}
	}

	private fun update(o: SpaceObject) {
		val t = Clock.INSTANCE.t().toDouble()
		o.velocity.x += o.acceleration.x * t
		o.velocity.y += o.acceleration.y * t
		o.position.x += o.velocity.x * t
		o.position.y += o.velocity.y * t
		o.acceleration.x = 0.0
		o.acceleration.y = 0.0
	}

	private fun distance(o: SpaceObject, a: SpaceObject): Long {
		val dx = a.position.x - o.position.x
		val dy = a.position.y - o.position.y
		val sumsqr = dx * dx + dy * dy
		return Math.sqrt(sumsqr).toLong()
	}

	private fun angle(o: SpaceObject, a: SpaceObject): Double {
		val dx = a.position.x - o.position.x
		val dy = a.position.y - o.position.y
		return Math.atan2(dy, dx)
	}
}
