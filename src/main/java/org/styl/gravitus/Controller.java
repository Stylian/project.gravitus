package org.styl.gravitus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JRadioButtonMenuItem;

import org.apache.log4j.Logger;
import org.styl.gravitus.engine.EngineTicksListener;
import org.styl.gravitus.engine.ProccessFailureException;
import org.styl.gravitus.entities.Simulation;
import org.styl.gravitus.ui.Renderer;
import org.styl.gravitus.ui.SpaceObjectUIWrapper;
import org.styl.gravitus.ui.View;

public class Controller implements EngineTicksListener, ActionListener {
	final static Logger logger = Logger.getLogger(Controller.class);

	private Runner runner;
	private View view;
	private Renderer renderer;

	public Controller(View view, Runner runner) {
		this.runner = runner;
		this.view = view;

		view.getToolBar().setListener(this);
		view.getScreen().setListener(this);
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

		switch (e.getActionCommand()) {

		case "start":
			startSimulation();
			break;
		case "pause":

			try {
				pauseSimulation();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			break;
		case "stop":
			try {
				stopSimulation();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			break;
		case "change_fps":
			String btnText = ((JRadioButtonMenuItem) e.getSource()).getText();
			runner.getSimulation().setFps(Integer.parseInt(btnText));
			break;
		case "exit":
			view.dispose();
			break;
		case "trails":
			renderer.getWrappers().forEach(w -> w.getPastPositions().clear());
			Specs.instance.orbitTrails = !Specs.instance.orbitTrails;
			break;
		case "orbit_path":
			Specs.instance.orbitsFixed = !Specs.instance.orbitsFixed;
			break;
		case "zoomIn":
			renderer.zoomBy(100);
			break;
		case "zoomOut":
			renderer.zoomBy(-100);
			break;
		}

	}

	private void initSimulation() {
		logger.info("initializing simulation");

		runner.initNextStage();

		List<SpaceObjectUIWrapper> wrappers = new ArrayList<>();
		runner.getSimulation().getEngine().getObjects().forEach(o -> wrappers.add(new SpaceObjectUIWrapper(o)));

		renderer = new Renderer(view.getScreen(), wrappers);
		runner.getSimulation().getTicker().setListener(this);
		view.getToolBar().getPrefsMenu().setEnabled(true);
		wrappers.forEach(w -> view.getScreen().add(w));
	}

	public void startSimulation() {
		logger.info("attempting to start simulation");

		// initialize simulation if not any
		if (runner.getSimulation() == null || runner.getSimulation().getStatus() == Simulation.STOPPED) {
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

		view.getToolBar().getStart().setEnabled(false);
		view.getToolBar().getPause().setEnabled(true);
		view.getToolBar().getStop().setEnabled(true);
	}

	private void pauseSimulation() throws InterruptedException {
		logger.info("attempting to pause simulation");

		try {
			runner.getSimulation().pause();
			runner.getSimulation().setStatus(Simulation.PAUSED);

			logger.info("simulation paused!");
		} catch (ProccessFailureException e) {
			logger.error("failed to paused simulation.");
			e.printStackTrace();
		}

		view.getToolBar().getPause().setEnabled(false);
		view.getToolBar().getStop().setEnabled(true);
		view.getToolBar().getStart().setEnabled(true);
		view.getToolBar().getStart().setText("Continue");
	}

	private void stopSimulation() throws InterruptedException {
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

		view.getToolBar().getStop().setEnabled(false);
		view.getToolBar().getPause().setEnabled(false);
		view.getToolBar().getStart().setEnabled(true);
		view.getToolBar().getStart().setText("Start");
	}

}
