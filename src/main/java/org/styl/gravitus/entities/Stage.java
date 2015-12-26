package org.styl.gravitus.entities;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Stage {

	private String name;
	private long mass;
	private String img;
	private int radius;
	private PVector position;
	private PVector velocity;

}
