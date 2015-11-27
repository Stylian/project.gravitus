package org.styl.gravitus;

import java.util.LinkedList;
import java.util.List;

import org.styl.gravitus.entities.SpaceObject;
import org.styl.gravitus.ui.SpaceObjectUIWrapper;

public class SpaceObjectFactory {

	
	public static List<SpaceObject> createSpaceObjects() {
		
		List<SpaceObject> objects = new LinkedList<>();
		
		
		SpaceObject aaa = new SpaceObject();
		aaa.setName("Earth");

		
		aaa.setMass(598600); // 5.97237×1024
		aaa.setImage("resources/images/earth.png");
		aaa.setPosx(500000);
		aaa.setPosy(500000);
		aaa.setRadius(6); // 6371 km
		aaa.setVely(-0.56);
		
		aaa.setVelx(1);
		
		objects.add(aaa);
	
	//dist 380 000 	
		
		SpaceObject bbb = new SpaceObject();
		bbb.setName("Moon");

		bbb.setMass(7300); // 0.07342×1024 kg
		bbb.setImage("resources/images/moon.png");
		bbb.setPosx(240000);
		bbb.setPosy(500000);
		bbb.setRadius(2);  //1737 km
		bbb.setVely(45.92); // 4.627 m/s
		
		bbb.setVelx(1);
		
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
