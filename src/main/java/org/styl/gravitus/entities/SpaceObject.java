package org.styl.gravitus.entities;

import lombok.Data;

@Data
public class SpaceObject {

	private static int counter = 0;

	private int id;
	private String name;
	private int radius;
	private String image;
	private int mass;
	private PVector position;
	private PVector velocity;
	private PVector acceleration;

	public SpaceObject() {
		this.id = ++counter;
		this.position = new PVector();
		this.velocity = new PVector();
		this.acceleration = new PVector();
	}

}
