package org.styl.gravitus.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JRadioButtonMenuItem;

import org.apache.log4j.Logger;
import org.styl.gravitus.engine.ProccessFailureException;
import org.styl.gravitus.engine.Runner;
import org.styl.gravitus.engine.Simulation;
import org.styl.gravitus.entities.SpaceObjectFactory;

public class Controller implements ActionListener {
	final static Logger logger = Logger.getLogger(Controller.class);
	
	private Simulation simulation;
	private Runner runner;
	private Screen screen;
	private List<SpaceObjectUIWrapper> wrappers;
	
	public Controller(Screen screen, Runner runner) {
		this.runner = runner;
		this.screen = screen;
		
		screen.setController(this);
		screen.init();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		switch(e.getActionCommand()) {
		
		case "start" :	
			startSimulation();	
			screen.getStart().setEnabled(false);
			screen.getPause().setEnabled(true);
			screen.getStop().setEnabled(true);
			break;
		case "pause" :
			
			try {
				pauseSimulation();			
				screen.getPause().setEnabled(false);
				screen.getStop().setEnabled(true);
				screen.getStart().setEnabled(true);
				screen.getStart().setText("Continue");
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			break;		
		case "stop" :		
			try {
				stopSimulation();				
				screen.getStop().setEnabled(false);
				screen.getPause().setEnabled(false);
				screen.getStart().setEnabled(true);
				screen.getStart().setText("Start");
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			break;		
		case "change_fps" :
			String btnText = ((JRadioButtonMenuItem) e.getSource()).getText();
			getSimulation().setFps(Integer.parseInt(btnText));
			break;		
		case "exit" :	
			screen.getFrame().dispose();
			break;
		case "trails" :		
			SpaceObjectUIWrapper.switchOrbitTrails(wrappers);	
			break;	
		case "orbit_path" :	
			SpaceObjectUIWrapper.switchOrbitPath();
			break;
		}
	
	}
	
	public void initSimulation() {
		logger.info("initializing simulation");
		
		runner.buildSpaceObjects();
		wrappers = SpaceObjectFactory.createSpaceObjectUIWrappers(runner.getObjects());
		screen.setWrappers(wrappers);
		simulation = new Simulation(screen, runner);
		screen.getPrefsMenu().setEnabled(true);
	}
	
	public void startSimulation() {
		logger.info("attempting to start simulation");

		// initialize simulation if not any
		if(simulation ==  null || simulation.getStatus() == Simulation.STOPPED) {
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

		try {
			simulation.pause();
			simulation.setStatus(Simulation.PAUSED);
			
			logger.info("simulation paused!");
		} catch (ProccessFailureException e) {
			logger.error("failed to paused simulation.");
			e.printStackTrace();
		}
		
	}
	
	public void stopSimulation() throws InterruptedException {
		logger.info("attempting to stop simulation");
		
		try {
			simulation.stop();
			simulation.setStatus(Simulation.STOPPED);
			clearData();
			
			logger.info("simulation has been reset!");
		} catch (ProccessFailureException e) {
			logger.error("failed to stop simulation.");
			e.printStackTrace();
		}
		
	}
	
	private void clearData() {
		wrappers.forEach(w -> screen.remove(w));		
		wrappers.clear();		
		runner.getObjects().clear();
	}

	public Simulation getSimulation() {
		return simulation;
	}
}
