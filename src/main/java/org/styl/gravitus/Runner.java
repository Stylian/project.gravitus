package org.styl.gravitus;

import org.apache.log4j.Logger;
import org.styl.gravitus.engine.Clock;
import org.styl.gravitus.engine.Simulation;
import org.styl.gravitus.entities.SpaceObjectFactory;
import org.styl.gravitus.entities.SpaceObjectUIWrapper;
import org.styl.gravitus.ui.Screen;

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
