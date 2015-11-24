package org.styl.gravitus.ui;

import java.util.List;

import org.apache.log4j.Logger;
import org.styl.gravitus.engine.ProccessFailureException;
import org.styl.gravitus.engine.Runner;
import org.styl.gravitus.engine.Simulation;
import org.styl.gravitus.entities.SpaceObjectFactory;

public class Controller {
	final static Logger logger = Logger.getLogger(Controller.class);
	
	private Simulation simulation;
	
	private Runner runner;
	private Screen screen;
	
	private List<SpaceObjectUIWrapper> wrappers;
	
	public Controller(Screen screen, Runner runner) {
		this.runner = runner;
		this.screen = screen;
	}
	
	public void initSimulation() {
		
		runner.buildSpaceObjects();
		wrappers = SpaceObjectFactory.createSpaceObjectUIWrappers(runner.getObjects());
		screen.setWrappers(wrappers);

		simulation = new Simulation(screen, runner);
	}
	
	public void startSimulation() {
		logger.info("attempting to start simulation");

		if(simulation.getStatus() == Simulation.STOPPED) {
			 initSimulation();
		}
		
		try {
			simulation.start();
			simulation.setStatus(Simulation.RUNNING);
			logger.info("simulation started!");
		} catch (ProccessFailureException e) {
			logger.error("failed to start simulation.");
			e.printStackTrace();
		}
	}
	
	public void pauseSimulation() throws InterruptedException {
		logger.info("attempting to pause simulation");
		
		simulation.pause();
	}
	
	public void stopSimulation() throws InterruptedException {
		logger.info("attempting to stop simulation");
		
		simulation.stop();
		
		resetWrappers();
		
		runner.reset();
	}
	
	private void resetWrappers() {
		for(SpaceObjectUIWrapper wrapper : wrappers) {
			screen.remove(wrapper);
		}
		wrappers.clear();
	}

	public Simulation getSimulation() {
		return simulation;
	}
}
