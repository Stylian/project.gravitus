package org.styl.gravitus;

import org.apache.log4j.Logger;
import org.styl.gravitus.engine.Clock;
import org.styl.gravitus.engine.UniverseEngine;
import org.styl.gravitus.engine.Simulation;
import org.styl.gravitus.entities.SpaceObject;
import org.styl.gravitus.entities.SpaceObjectFactory;
import org.styl.gravitus.entities.SpaceObjectUIWrapper;
import org.styl.gravitus.ui.Screen;

import lombok.Getter;

public class Runner {
	final static Logger logger = Logger.getLogger(Runner.class);

	@Getter private Simulation simulation;

	public void createSimulation(Controller controller) {
		simulation = new Simulation(controller);
		simulation.getEngine().setObjects(SpaceObjectFactory.createSpaceObjects());
		
		logger.info(simulation.getEngine().getObjects().size() + " space objects have been created!");
	}

	public void nextTick() {
		simulation.tick();
	}
	
	public void reset() {
		simulation.getEngine().getObjects().clear();
		
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
