package org.styl.gravitus.engine;

import org.apache.log4j.Logger;
import org.styl.gravitus.Controller;
import org.styl.gravitus.Runner;
import org.styl.gravitus.Specs;
import org.styl.gravitus.ui.View;

public class Initialiser {
	final static Logger logger = Logger.getLogger(Initialiser.class);
	
	public static void main(String[] args) {
		logger.info("starting Application");
		
		//initialize properties
		Specs.createInstance();
		
		//initialize model
		Runner runner = new Runner();

		//initialize view
		View view = new View();
	
		//initialize controller
		new Controller(view, runner);

		view.setVisible(true);
	}

}
