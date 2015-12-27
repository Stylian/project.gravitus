package org.styl.gravitus.entities;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class SpaceObject {

	private static int counter = 0;

	private int id;
	private String name;
	private int radius;
	@SerializedName("img-uri") private String image;
	private int mass;
	private PVector position;
	private PVector velocity;
	private transient PVector acceleration;

	public SpaceObject() {
		this.id = ++counter;
		this.position = new PVector();
		this.velocity = new PVector();
		this.acceleration = new PVector();
	}

}
