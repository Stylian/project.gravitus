package org.styl.gravitus.entities;

import java.util.LinkedList;
import java.util.List;

import org.styl.gravitus.ui.SpaceObjectUIWrapper;

public class SpaceObjectFactory {

	
	public static List<SpaceObject> createSpaceObjects() {
		
		List<SpaceObject> objects = new LinkedList<>();
		
		
		SpaceObject aaa = new SpaceObject();
		aaa.setName("Earth");

		
		aaa.setMass(30000);
		aaa.setImage("resources/images/earth.png");
		aaa.setPosx(500000);
		aaa.setPosy(500000);
		aaa.setRadius(8);
		aaa.setVely(-6);
		
		objects.add(aaa);
	
		
		
		SpaceObject bbb = new SpaceObject();
		bbb.setName("Moon");

		bbb.setMass(6000);
		bbb.setImage("resources/images/moon.png");
		bbb.setPosx(300000);
		bbb.setPosy(500000);
		bbb.setRadius(2);
		bbb.setVely(30);
		
		objects.add(bbb);
		
		
		return objects;
	}
	
	public static List<SpaceObjectUIWrapper> createSpaceObjectUIWrappers(List<SpaceObject> objects) {
		List<SpaceObjectUIWrapper> wrappers = new LinkedList<>();	
		for(SpaceObject object : objects) {
			wrappers.add(new SpaceObjectUIWrapper(object));
		}	
		return wrappers;
	}

}
