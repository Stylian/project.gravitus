package org.styl.gravitus.engine;

import org.apache.log4j.Logger;
import org.styl.gravitus.entities.SpaceObject;
import org.styl.gravitus.entities.SpaceObjectFactory;
import org.styl.gravitus.entities.SpaceObjectUIWrapper;
import org.styl.gravitus.ui.Controller;
import org.styl.gravitus.ui.Screen;

import lombok.Getter;

public class Runner {
	final static Logger logger = Logger.getLogger(Runner.class);

	@Getter private Simulation simulation;
	
	private GravityCalculator grCal;

	public Runner() {
		grCal = new GravityCalculator();
	}
	
	public void createSimulation(Controller controller) {
		simulation = new Simulation(controller);
		simulation.setObjects(SpaceObjectFactory.createSpaceObjects());
		grCal.setObjects(simulation.getObjects());
		
		logger.info(simulation.getObjects().size() + " space objects have been created!");
	}

	public void nextTick() {
		simulation.getObjects().forEach( o -> grCal.calculate(o) );	
		simulation.getObjects().forEach(SpaceObject::tick);
	}
	
	public void reset() {
		simulation.getObjects().clear();
		
		logger.info("simulation has been reset!");
	}

	
	private int renderFPSCounter = 0;
	
	public void render(Screen screen) {
		
		int calcFPS = Clock.INSTANCE.fps();
		
		if(renderFPSCounter++ > 10) { //GravitusProperties.INSTANCE.fpsRenderPeriod) {
			renderFPSCounter = 0;
			screen.getFps().setText(calcFPS + " FPS");
		}
		
		//update wrappers
		SpaceObjectUIWrapper.positionsCounter ++;
		if(simulation.getWrappers() != null) {
			simulation.getWrappers().forEach(SpaceObjectUIWrapper::update);
		}
		
		screen.repaint();
	}
	
}
