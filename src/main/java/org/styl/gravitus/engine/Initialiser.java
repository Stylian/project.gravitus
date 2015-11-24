package org.styl.gravitus.engine;

import org.apache.log4j.Logger;
import org.styl.gravitus.Specs;
import org.styl.gravitus.ui.Controller;
import org.styl.gravitus.ui.Screen;

public class Initialiser {
	final static Logger logger = Logger.getLogger(Initialiser.class);
	
	public static void main(String[] args) {
		logger.info("starting Application");
		
		//initialize properties
		Specs.createInstance();
		
		Runner runner = new Runner();

		Screen screen = new Screen();
		
		Controller controller = new Controller(screen, runner);
		
		screen.setController(controller);
		
		screen.startUI();
		
		controller.initSimulation();
		
	//	screen.render();
	}

}
