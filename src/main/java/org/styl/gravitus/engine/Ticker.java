package org.styl.gravitus.engine;

import org.apache.log4j.Logger;
import org.styl.gravitus.ui.Screen;

public class Ticker implements Runnable {
	final static Logger logger = Logger.getLogger(Ticker.class);
	
	private boolean running;
	
	private Screen view;
	private Runner runner;
	private int fps;

	public void run() {
		
		logger.info("starting clock at " + fps + " fps");

		int skipTicks = 1000 / fps;
		
		double previous = getTicktime();
		double delta = 0.0;

		while (running) {
			
			double current = getTicktime();
			double elapsed = current - previous;
			previous = current;

			if(delta < 0.0)
				delta = 0.0;

			delta += elapsed / skipTicks;

			if(delta < 1.0) {
				update();
				delay();
			}else {

				render();
				delta --;
			}

		}
		
	}

	private void delay() {
		try {
			Thread.sleep(Clock.t());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static int getTicktime() {
		return (int) System.currentTimeMillis();
	}

	private void update() {
		runner.nextTick();
	}

	private void render() {
		view.render();
	}


	public Runner getRunner() {
		return runner;
	}

	public void setRunner(Runner runner) {
		this.runner = runner;
	}

	public Screen getView() {
		return view;
	}

	public void setView(Screen view) {
		this.view = view;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public int getFps() {
		return fps;
	}

	public void setFps(int fps) {
		this.fps = fps == 0 ? 40 : fps;
	}

}
