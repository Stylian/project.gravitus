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
	private PVector position = new PVector();
	private PVector velocity = new PVector();
	private transient PVector acceleration = new PVector();

	public SpaceObject() {
		this.id = ++counter;
	}

}
