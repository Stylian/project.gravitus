package org.styl.gravitus.engine;

import java.util.List;

import org.styl.gravitus.Specs;
import org.styl.gravitus.entities.SpaceObject;

import lombok.Getter;
import lombok.Setter;

public class UniverseEngine {

	@Getter @Setter private List<SpaceObject> objects;

	public void estimateTick() {
		objects.forEach(o -> calculate(o));
		objects.forEach(o -> update(o));
	}

	private void calculate(SpaceObject o) {
		objects.forEach((a) -> {
			if (o != a) {
				long dist = distance(o, a);
				double angle = angle(o, a);
				double g = Specs.instance.G * a.getMass() / Math.pow(dist, 2);
				double gx = g * Math.cos(angle);
				double gy = g * Math.sin(angle);
				o.getAcceleration().x += gx;
				o.getAcceleration().y += gy;
			}
		});
	}

	private void update(SpaceObject o) {
		o.getVelocity().x += o.getAcceleration().x * Clock.INSTANCE.t();
		o.getVelocity().y += o.getAcceleration().y * Clock.INSTANCE.t();
		o.getPosition().x += o.getVelocity().x * Clock.INSTANCE.t();
		o.getPosition().y += o.getVelocity().y * Clock.INSTANCE.t();
		o.getAcceleration().x = 0;
		o.getAcceleration().y = 0;
	}

	private long distance(SpaceObject o, SpaceObject a) {
		double dx = a.getPosition().x - o.getPosition().x;
		double dy = a.getPosition().y - o.getPosition().y;
		long sumsqr = (long) (Math.pow(dx, 2) + Math.pow(dy, 2));
		return (long) Math.sqrt(sumsqr);
	}

	private double angle(SpaceObject o, SpaceObject a) {
		double dx = a.getPosition().x - o.getPosition().x;
		double dy = a.getPosition().y - o.getPosition().y;
		return Math.atan2(dy, dx);
	}

}
