package org.styl.gravitus.entities;

import org.apache.log4j.Logger;
import org.styl.gravitus.engine.ProccessFailureException;
import org.styl.gravitus.engine.Ticker;
import org.styl.gravitus.engine.UniverseEngine;

import lombok.Data;

@Data
public class Simulation {
	final static Logger logger = Logger.getLogger(Simulation.class);

	public static final int RUNNING = 1;
	public static final int PAUSED = 2;
	public static final int STOPPED = 3;

	private Stage stage;
	private int fps;
	private int status;
	private UniverseEngine engine;
	private Ticker ticker;
	private Thread thread;

	public Simulation(Stage stage) {
		ticker = new Ticker();
		engine = new UniverseEngine();

		loadStage(stage);
	}

	private void loadStage(Stage stage) {

		engine.setObjects(stage.getObjects());

	}

	public void start() throws ProccessFailureException {
		ticker.setFps(fps == 0 ? 40 : fps);
		ticker.setRunning(true);
		thread = new Thread(ticker);
		thread.start();
	}

	public void pause() throws ProccessFailureException {
		ticker.setRunning(false);
	}

	public void stop() throws ProccessFailureException, InterruptedException {
		ticker.setRunning(false);
		thread.join();
	}

	public void tick() {
		engine.estimateTick();
	}
}
