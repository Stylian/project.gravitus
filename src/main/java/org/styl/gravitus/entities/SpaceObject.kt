package org.styl.gravitus.entities

import com.google.gson.annotations.SerializedName

data class SpaceObject(
	var id: Int = nextId(),
	var name: String = "",
	var radius: Int = 0,
	@SerializedName("img-uri")
	var image: String = "",
	var mass: Int = 0,
	var position: PVector = PVector(),
	var velocity: PVector = PVector(),
	@Transient
	var acceleration: PVector = PVector()
) {
	companion object {
		private var counter: Int = 0
		private fun nextId(): Int = ++counter
	}
}
