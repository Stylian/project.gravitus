package org.styl.gravitus;

import org.apache.log4j.Logger;
import org.styl.gravitus.engine.Simulation;

import lombok.Getter;

public class Runner {
	final static Logger logger = Logger.getLogger(Runner.class);

	@Getter private Simulation simulation;

	public void createSimulation() {
		simulation = new Simulation();
		simulation.getEngine().setObjects(SpaceObjectFactory.createSpaceObjects());
		
		logger.info(simulation.getEngine().getObjects().size() + " space objects have been created!");
	}

	public void nextTick() {
		simulation.tick();
	}
	
	public void reset() {
		simulation.getEngine().getObjects().clear();
		
		logger.info("simulation has been reset!");
	}

}
