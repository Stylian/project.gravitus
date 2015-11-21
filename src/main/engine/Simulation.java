package engine;

import org.apache.log4j.Logger;

import ui.ViewRunner;

public class Simulation {
	final static Logger logger = Logger.getLogger(Simulation.class);
	
	public static final int STATUS_RUNNING = 1;
	public static final int STATUS_PAUSED = 2;
	public static final int STATUS_STOPPED = 3;
	
	private int fps;
	private Thread thread;
	private Ticker ticker;
	private int status;
	
	public Simulation(ViewRunner view) {
		ticker = new Ticker();
		ticker.setView(view);
		ticker.setRunner(new ModelRunner(view));
	}

	public void init() {
		ticker.init();
	}
	
	public void start() {
		
		if(status == STATUS_STOPPED) {
			ticker.init();
		}
		
		ticker.setFps(fps);
		ticker.setRunning(true);
		
		thread = new Thread(ticker);
		thread.start();
		
		status = STATUS_RUNNING;
		
		logger.info("simulation started");
	}
	
	public void pause() {
		ticker.setRunning(false);
		
		status = STATUS_PAUSED;
		
		logger.info("simulation paused");
	}
	
	public void stop() throws InterruptedException {
		ticker.setRunning(false);
		ticker.reset();
		
		thread.join();
		
		status = STATUS_STOPPED;
		
		logger.info("simulation stopped");
	}

	public int getFps() {
		return fps;
	}

	public void setFps(int fps) {
		this.fps = fps;
	}
	
}
