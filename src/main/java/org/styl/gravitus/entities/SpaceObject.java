package org.styl.gravitus.entities;

import org.styl.gravitus.engine.Clock;

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
		velx += accx * Clock.t();
		vely += accy * Clock.t();

		posx += velx * Clock.t();
		posy += vely * Clock.t();
		
		accx = 0;
		accy = 0;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public long getPosx() {
		return posx;
	}

	public void setPosx(long posx) {
		this.posx = posx;
	}

	public long getPosy() {
		return posy;
	}

	public void setPosy(long posy) {
		this.posy = posy;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public double getVelx() {
		return velx;
	}

	public void setVelx(double velx) {
		this.velx = velx;
	}

	public double getVely() {
		return vely;
	}

	public void setVely(double vely) {
		this.vely = vely;
	}

	public void incrAccx(double incr) {
		accx += incr;
	}

	public void incrAccy(double incr) {
		accy += incr;
	}

	public int getMass() {
		return mass;
	}

	public void setMass(int mass) {
		this.mass = mass;
	}
	
}
