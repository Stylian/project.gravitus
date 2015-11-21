package engine;

import java.awt.Dimension;

import org.apache.log4j.Logger;

import ui.Controller;
import ui.ViewRunner;

public class Initialiser {
	final static Logger logger = Logger.getLogger(Initialiser.class);
	
	public static void main(String[] args) {
		logger.info("starting Application");
		
		Dimension size = new Dimension(1200, 1000);
		 
		ViewRunner view = new ViewRunner(size);
		
		Controller control = new Controller(view);
		
		view.setController(control);
		view.startUI();
		
		control.init();
		
		view.render();
	}

}
