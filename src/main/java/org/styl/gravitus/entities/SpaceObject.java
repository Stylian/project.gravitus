package org.styl.gravitus.entities;

import org.styl.gravitus.engine.Clock;

import lombok.Data;

@Data
public class SpaceObject {

	private static int counter = 0;

	private int id;
	private String name;
	private int radius;
	private String image;
	private long posx;
	private long posy;
	private double velx;
	private double vely;
	private double accx;
	private double accy;
	private int mass;
	
	public SpaceObject() {
		this.id = ++counter;
	}

	public void tick() {
		velx += accx * Clock.INSTANCE.t();
		vely += accy * Clock.INSTANCE.t();
		posx += velx * Clock.INSTANCE.t();
		posy += vely * Clock.INSTANCE.t();
		
		accx = 0;
		accy = 0;
	}

	public void incrAccx(double incr) {
		accx += incr;
	}

	public void incrAccy(double incr) {
		accy += incr;
	}
	
}
