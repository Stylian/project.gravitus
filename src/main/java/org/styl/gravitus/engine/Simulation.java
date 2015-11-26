package org.styl.gravitus.engine;

import java.util.List;

import org.apache.log4j.Logger;
import org.styl.gravitus.Controller;
import org.styl.gravitus.entities.SpaceObject;
import org.styl.gravitus.entities.SpaceObjectUIWrapper;

import lombok.Getter;
import lombok.Setter;

public class Simulation {
	final static Logger logger = Logger.getLogger(Simulation.class);
	
	public static final int RUNNING = 1;
	public static final int PAUSED = 2;
	public static final int STOPPED = 3;
	
	@Getter @Setter private List<SpaceObjectUIWrapper> wrappers;
	@Getter @Setter private int fps;
	@Getter @Setter private int status;
	@Getter private UniverseEngine engine;
	@Getter private Ticker ticker;
	private Thread thread;
	
	public Simulation() {
		ticker = new Ticker();
		engine = new UniverseEngine();
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
