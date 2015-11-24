package org.styl.gravitus.engine;

import org.apache.log4j.Logger;
import org.styl.gravitus.ui.Screen;

import lombok.Getter;
import lombok.Setter;

public class Ticker implements Runnable {
	final static Logger logger = Logger.getLogger(Ticker.class);
	
	@Getter @Setter private boolean running;
	
	@Setter private Screen view;
	@Setter private Runner runner;
	@Setter private int fps;

	public void run() {
		logger.info("starting clock at " + fps + " fps");

		int skipTicks = 1000 / fps;	
		double previous = getTicktime();
		double delta = 0.0;

		while (running) {
			
			double current = getTicktime();
			double elapsed = current - previous;
			previous = current;

			if(delta < 0.0) {
				delta = 0.0;
			}
			
			delta += elapsed / skipTicks;

			if(delta < 1.0) {
				update();
				Clock.INSTANCE.delay();
			}else {
				render();
				delta --;
			}

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

}
