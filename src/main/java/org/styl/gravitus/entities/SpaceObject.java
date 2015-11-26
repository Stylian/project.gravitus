package org.styl.gravitus.entities;

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

	public void incrPosx(double incr) {
		posx += incr;
	}

	public void incrPosy(double incr) {
		posy += incr;
	}
	public void incrVelx(double incr) {
		velx += incr;
	}

	public void incrVely(double incr) {
		vely += incr;
	}
	
	public void incrAccx(double incr) {
		accx += incr;
	}

	public void incrAccy(double incr) {
		accy += incr;
	}
	
}
