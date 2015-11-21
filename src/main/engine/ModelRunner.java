package engine;

import java.util.List;

import org.apache.log4j.Logger;

import entities.SpaceObject;
import entities.SpaceObjectFactory;
import ui.SpaceObjectUIWrapper;
import ui.ViewRunner;

/**
 * runs around every 20ms the run method 
 * 
 * @author Stel
 *
 */
public class ModelRunner {
	final static Logger logger = Logger.getLogger(ModelRunner.class);
	
	private ViewRunner view;
	
	private List<SpaceObject> objects;
	private List<SpaceObjectUIWrapper> wrappers;
	
	private GravityCalculator grCal;
	
	public ModelRunner(ViewRunner view) {
		this.view = view;
		
		grCal = new GravityCalculator();
	}

	public void nextTick() {
		
		objects.forEach( o -> grCal.calculate(o) );
		
		//estimate velocities & locations
		objects.forEach(SpaceObject::tick);
		
		//update wrappers
		wrappers.forEach(SpaceObjectUIWrapper::update);
	}

	public void buildSpaceObjects() {

		objects = SpaceObjectFactory.createSpaceObjects();
		wrappers = SpaceObjectFactory.createSpaceObjectUIWrappers(objects);
		
		for(SpaceObjectUIWrapper wrapper : wrappers) {
			view.getSpace().add(wrapper);
		}
		
		grCal.setObjects(objects);
		
		logger.info(wrappers.size() + " space objects have been created!");
	}
	
	public void reset() {
		
		for(SpaceObjectUIWrapper wrapper : wrappers) {
			view.getSpace().remove(wrapper);
		}
		wrappers.clear();
		objects.clear();
		
		logger.info("simulation has been reset!");
	}

}
