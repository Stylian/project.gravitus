package org.styl.gravitus.engine;

import java.util.List;

import org.styl.gravitus.Specs;
import org.styl.gravitus.entities.SpaceObject;

import lombok.Getter;
import lombok.Setter;

public class GravityCalculator {
	
	@Getter @Setter private List<SpaceObject> objects;

	public void calculate(SpaceObject o) {
		objects.forEach( (a) -> {
			if(o != a) {
				long dist = distance(o, a);
				double angle = angle(o, a);
				double g = Specs.instance.G * a.getMass() / Math.pow(dist, 2); 
				double gx = g * Math.cos(angle);
				double gy = g * Math.sin(angle);
				o.incrAccx(gx);
				o.incrAccy(gy);
			}
		});
	}

	private long distance(SpaceObject o, SpaceObject a) {
		long dx = a.getPosx() - o.getPosx();
		long dy = a.getPosy() - o.getPosy();
		long sumsqr = (long) (Math.pow(dx, 2) + Math.pow(dy, 2));
		return (long) Math.sqrt(sumsqr);
	}
	
	private double angle(SpaceObject o, SpaceObject a) {
		long dx = a.getPosx() - o.getPosx();
		long dy = a.getPosy() - o.getPosy();
		return Math.atan2(dy, dx);
	}

}
