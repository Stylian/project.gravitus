package org.styl.gravitus.engine;

import org.apache.log4j.Logger;
import org.styl.gravitus.ui.Screen;

public class Simulation {
	final static Logger logger = Logger.getLogger(Simulation.class);
	
	public static final int RUNNING = 1;
	public static final int PAUSED = 2;
	public static final int STOPPED = 3;
	
	private int fps;
	private Thread thread;
	private Ticker ticker;
	private int status;
	
	public Simulation(Screen view, Runner runner) {
		ticker = new Ticker();
		ticker.setView(view);
		ticker.setRunner(runner);
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

	public int getFps() {
		return fps;
	}

	public void setFps(int fps) {
		this.fps = fps;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
}
