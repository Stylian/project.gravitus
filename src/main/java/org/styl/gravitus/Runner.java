package org.styl.gravitus;

import org.apache.log4j.Logger;

import lombok.Getter;

public class Runner {
	final static Logger logger = Logger.getLogger(Runner.class);

	@Getter private Stage simulation;

	public void initNextStage() {
		simulation = new Stage();
		
		simulation.loadStage(1); // TODO
		
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
