package org.styl.gravitus.engine

import org.styl.gravitus.Specs
import java.util.ArrayDeque
import java.util.Deque

class Clock private constructor() {

	private var prevTime: Long = 0L
	private val fpsBuffer: Deque<Long> = ArrayDeque(100)

	companion object {
		val INSTANCE: Clock = Clock()
	}

	fun t(): Int {
		return Specs.instance?.clockInterval ?: 0
	}

	fun fps(): Int {
		if (fpsBuffer.size == 100) {
			fpsBuffer.removeLast()
		}

		val time = System.currentTimeMillis()
		if (time != prevTime) {
			val delta = time - prevTime
			if (delta > 0) {
				fpsBuffer.addFirst(1000 / delta)
			}
		}
		prevTime = time

		return if (fpsBuffer.isNotEmpty()) {
			fpsBuffer.map { it.toDouble() }.average().toInt()
		} else 0
	}

	fun delay() {
		try {
			Thread.sleep(t().toLong())
		} catch (e: InterruptedException) {
			e.printStackTrace()
		}
	}
}
