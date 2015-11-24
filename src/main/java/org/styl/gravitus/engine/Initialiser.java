package org.styl.gravitus.engine;

import java.awt.Dimension;

import org.apache.log4j.Logger;
import org.styl.gravitus.Specs;
import org.styl.gravitus.ui.Controller;
import org.styl.gravitus.ui.ViewRunner;

public class Initialiser {
	final static Logger logger = Logger.getLogger(Initialiser.class);
	
	public static void main(String[] args) {
		logger.info("starting Application");
		
		//initialize properties
		Specs.createInstance();
		

		
		ViewRunner view = new ViewRunner();
		
		Controller control = new Controller(view);
		
		view.setController(control);
		view.startUI();
		
		control.init();
		
		view.render();
	}

}
