package engine;

import org.apache.log4j.Logger;

import ui.ViewRunner;

public class Ticker implements Runnable {
	final static Logger logger = Logger.getLogger(Ticker.class);
	
	private boolean running;
	
	private ViewRunner view;
	private ModelRunner runner;
	private int fps;
	
	public void init() {
		runner.buildSpaceObjects();	
	}

	public void reset() {
		runner.reset();
	}
	
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


	public ModelRunner getRunner() {
		return runner;
	}

	public void setRunner(ModelRunner runner) {
		this.runner = runner;
	}

	public ViewRunner getView() {
		return view;
	}

	public void setView(ViewRunner view) {
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
