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
		
		//initialize model
		Runner runner = new Runner();

		//initialize view
		Screen screen = new Screen();
	
		//initialize controller
		new Controller(screen, runner);

		screen.getFrame().setVisible(true);
	}

}
