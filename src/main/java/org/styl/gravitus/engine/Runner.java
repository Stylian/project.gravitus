package org.styl.gravitus.engine;

import java.util.List;

import org.apache.log4j.Logger;
import org.styl.gravitus.entities.SpaceObject;
import org.styl.gravitus.entities.SpaceObjectFactory;

import lombok.Getter;
import lombok.Setter;

/**
 * runs around every 20ms the run method 
 * 
 * @author Stel
 *
 */
public class Runner {
	final static Logger logger = Logger.getLogger(Runner.class);
	
	@Getter @Setter private List<SpaceObject> objects;
	private GravityCalculator grCal;
	
	public Runner() {	
		grCal = new GravityCalculator();
	}

	public void nextTick() {
		objects.forEach( o -> grCal.calculate(o) );	
		objects.forEach(SpaceObject::tick);
	}

	public void buildSpaceObjects() {
		objects = SpaceObjectFactory.createSpaceObjects();
		grCal.setObjects(objects);
		
		logger.info(objects.size() + " space objects have been created!");
	}
	
	public void reset() {
		objects.clear();
		
		logger.info("simulation has been reset!");
	}

}
