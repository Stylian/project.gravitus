package org.styl.gravitus.engine;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.styl.gravitus.entities.SpaceObject;
import org.styl.gravitus.entities.Stage;

import lombok.Getter;
import lombok.Setter;

public class Simulation {
	final static Logger logger = Logger.getLogger(Simulation.class);

	public static final int RUNNING = 1;
	public static final int PAUSED = 2;
	public static final int STOPPED = 3;

	@Getter @Setter private int fps;
	@Getter @Setter private int status;
	@Getter private UniverseEngine engine;
	@Getter private Ticker ticker;
	private Thread thread;

	public Simulation() { //Stage stage) {
		ticker = new Ticker();
		engine = new UniverseEngine();
		
	//	loadStage(stage);
		
		List<SpaceObject> objects = new LinkedList<>();

		SpaceObject aaa = new SpaceObject();
		aaa.setName("Earth");

		aaa.setMass(598600); // 5.97237×1024
		aaa.setImage("resources/images/earth.png");
		aaa.getPosition().x = 500000;
		aaa.getPosition().y = 500000;
		aaa.setRadius(6); // 6371 km
		aaa.getVelocity().y = -0.56;

		objects.add(aaa);

		// dist 380 000

		SpaceObject bbb = new SpaceObject();
		bbb.setName("Moon");

		bbb.setMass(7300); // 0.07342×1024 kg
		bbb.setImage("resources/images/moon.png");
		bbb.getPosition().x = 240000;
		bbb.getPosition().y = 500000;
		bbb.setRadius(2); // 1737 km
		bbb.getVelocity().y = 45.92; // 4.627 m/s

		objects.add(bbb);
		
		getEngine().setObjects(objects);
	}

	private void loadStage(Stage stage) {
		
		
		
/*
		List<SpaceObject> objects = new LinkedList<>();

		SpaceObject aaa = new SpaceObject();
		aaa.setName("Earth");

		aaa.setMass(598600); // 5.97237×1024
		aaa.setImage("resources/images/earth.png");
		aaa.setPosx(500000);
		aaa.setPosy(500000);
		aaa.setRadius(6); // 6371 km
		aaa.setVely(-0.56);

		objects.add(aaa);

		// dist 380 000

		SpaceObject bbb = new SpaceObject();
		bbb.setName("Moon");

		bbb.setMass(7300); // 0.07342×1024 kg
		bbb.setImage("resources/images/moon.png");
		bbb.setPosx(240000);
		bbb.setPosy(500000);
		bbb.setRadius(2); // 1737 km
		bbb.setVely(45.92); // 4.627 m/s

		objects.add(bbb);
		
		getEngine().setObjects(objects);
		*/
	}

	public void start() throws ProccessFailureException {
		ticker.setFps(fps == 0 ? 40 : fps);
		ticker.setRunning(true);
		thread = new Thread(ticker);
		thread.start();
	}

	public void pause() throws ProccessFailureException {
		ticker.setRunning(false);
	}

	public void stop() throws ProccessFailureException, InterruptedException {
		ticker.setRunning(false);
		thread.join();
	}

	public void tick() {
		engine.estimateTick();
	}
}
