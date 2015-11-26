package org.styl.gravitus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JRadioButtonMenuItem;

import org.apache.log4j.Logger;
import org.styl.gravitus.engine.EngineTicksListener;
import org.styl.gravitus.engine.ProccessFailureException;
import org.styl.gravitus.engine.Simulation;
import org.styl.gravitus.entities.SpaceObjectFactory;
import org.styl.gravitus.entities.SpaceObjectUIWrapper;
import org.styl.gravitus.ui.Screen;

import lombok.Getter;

public class Controller implements EngineTicksListener, ActionListener {
	final static Logger logger = Logger.getLogger(Controller.class);
	
	@Getter private Runner runner;
	@Getter private Screen screen;
	
	public Controller(Screen screen, Runner runner) {
		this.runner = runner;
		this.screen = screen;
		
		screen.setController(this);
		screen.init();
	}

	@Override
	public void updateData() {
		runner.nextTick();
	}

	@Override
	public void updateUI() {
		screen.getRenderer().render();
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
			runner.getSimulation().setFps(Integer.parseInt(btnText));
			break;		
		case "exit" :	
			screen.getFrame().dispose();
			break;
		case "trails" :
			SpaceObjectUIWrapper.switchOrbitTrails();	
			break;	
		case "orbit_path" :	
			SpaceObjectUIWrapper.switchOrbitPath();
			break;
		}
	
	}
	
	public void initSimulation() {
		logger.info("initializing simulation");
		
		runner.createSimulation();
		runner.getSimulation().setWrappers(SpaceObjectFactory
				.createSpaceObjectUIWrappers(runner.getSimulation().getEngine().getObjects()));
		runner.getSimulation().getWrappers().forEach( w -> screen.add(w));
		screen.getRenderer().setWrappers(runner.getSimulation().getWrappers());
		screen.getPrefsMenu().setEnabled(true);
		runner.getSimulation().getTicker().setListener(this);
	}
	
	public void startSimulation() {
		logger.info("attempting to start simulation");

		// initialize simulation if not any
		if(runner.getSimulation() ==  null || runner.getSimulation().getStatus() == Simulation.STOPPED) {
			initSimulation();
		}
		
		try {
			runner.getSimulation().start();
			runner.getSimulation().setStatus(Simulation.RUNNING);
			
			logger.info("simulation started!");
		} catch (ProccessFailureException e) {
			logger.error("failed to start simulation.");
			e.printStackTrace();
		}
		
	}
	
	public void pauseSimulation() throws InterruptedException {
		logger.info("attempting to pause simulation");

		try {
			runner.getSimulation().pause();
			runner.getSimulation().setStatus(Simulation.PAUSED);
			
			logger.info("simulation paused!");
		} catch (ProccessFailureException e) {
			logger.error("failed to paused simulation.");
			e.printStackTrace();
		}
		
	}
	
	public void stopSimulation() throws InterruptedException {
		logger.info("attempting to stop simulation");
		
		try {
			runner.getSimulation().stop();
			runner.getSimulation().setStatus(Simulation.STOPPED);

			// clear data
			runner.getSimulation().getWrappers().forEach(w -> screen.remove(w));		
			runner.getSimulation().getWrappers().clear();		
			runner.getSimulation().getEngine().getObjects().clear();
			
			logger.info("simulation has been reset!");
		} catch (ProccessFailureException e) {
			logger.error("failed to stop simulation.");
			e.printStackTrace();
		}
		
	}

}
