package org.styl.gravitus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JRadioButtonMenuItem;

import org.apache.log4j.Logger;
import org.styl.gravitus.engine.EngineTicksListener;
import org.styl.gravitus.engine.ProccessFailureException;
import org.styl.gravitus.engine.Simulation;
import org.styl.gravitus.ui.Renderer;
import org.styl.gravitus.ui.SpaceObjectUIWrapper;
import org.styl.gravitus.ui.View;

import lombok.Getter;

public class Controller implements EngineTicksListener, ActionListener {
	final static Logger logger = Logger.getLogger(Controller.class);
	
	@Getter private Runner runner;
	@Getter private View view;
	private Renderer renderer;
	
	public Controller(View view, Runner runner) {
		this.runner = runner;
		this.view = view;
		
		view.getToolBar().setListener(this);
		view.getScreen().init();
		view.getToolBar().init();
	}

	@Override
	public void updateData() {
		runner.nextTick();
	}

	@Override
	public void updateUI() {
		renderer.render();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		switch(e.getActionCommand()) {
		
		case "start" :	
			startSimulation();	
			view.getToolBar().getStart().setEnabled(false);
			view.getToolBar().getPause().setEnabled(true);
			view.getToolBar().getStop().setEnabled(true);
			break;
		case "pause" :
			
			try {
				pauseSimulation();			
				view.getToolBar().getPause().setEnabled(false);
				view.getToolBar().getStop().setEnabled(true);
				view.getToolBar().getStart().setEnabled(true);
				view.getToolBar().getStart().setText("Continue");
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			break;		
		case "stop" :		
			try {
				stopSimulation();				
				view.getToolBar().getStop().setEnabled(false);
				view.getToolBar().getPause().setEnabled(false);
				view.getToolBar().getStart().setEnabled(true);
				view.getToolBar().getStart().setText("Start");
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			break;		
		case "change_fps" :
			String btnText = ((JRadioButtonMenuItem) e.getSource()).getText();
			runner.getSimulation().setFps(Integer.parseInt(btnText));
			break;		
		case "exit" :	
			view.dispose();
			break;
		case "trails" :
			renderer.switchOrbitTrails();
			break;	
		case "orbit_path" :	
			renderer.switchOrbitPath();
			break;
		}
	
	}
	
	public void initSimulation() {
		logger.info("initializing simulation");
		
		runner.createSimulation();
		
		List<SpaceObjectUIWrapper> wrappers = SpaceObjectFactory
			.createSpaceObjectUIWrappers(runner.getSimulation().getEngine().getObjects());
				
		renderer = new Renderer(view.getScreen(), wrappers);
		runner.getSimulation().getTicker().setListener(this);	
		view.getToolBar().getPrefsMenu().setEnabled(true);
		wrappers.forEach( w -> view.getScreen().add(w));
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
			renderer.getWrappers().forEach(w -> view.getScreen().remove(w));		
			renderer.getWrappers().clear();		
			runner.getSimulation().getEngine().getObjects().clear();
			
			logger.info("simulation has been reset!");
		} catch (ProccessFailureException e) {
			logger.error("failed to stop simulation.");
			e.printStackTrace();
		}
		
	}

}
