package ui;

import org.apache.log4j.Logger;

import engine.Simulation;

public class Controller {
	final static Logger logger = Logger.getLogger(Controller.class);
	
	private Simulation simulation;
	
	public Controller(ViewRunner view) {
		simulation = new Simulation(view);
	}
	
	public void init() {
		simulation.init();
	}
	
	public void startSimulation() {
		logger.info("attempting to start simulation");
		
		simulation.start();
	}
	
	public void pauseSimulation() throws InterruptedException {
		logger.info("attempting to pause simulation");
		
		simulation.pause();
	}
	
	public void stopSimulation() throws InterruptedException {
		logger.info("attempting to stop simulation");
		
		simulation.stop();
	}
	
	public Simulation getSimulation() {
		return simulation;
	}
}
