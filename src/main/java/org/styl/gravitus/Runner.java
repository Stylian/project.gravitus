package org.styl.gravitus;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.styl.gravitus.engine.Simulation;
import org.styl.gravitus.entities.Stage;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import lombok.Getter;

public class Runner {
	final static Logger logger = Logger.getLogger(Runner.class);

	@Getter private Simulation simulation;

	public void initNextStage() {
	//	try {
			simulation = new Simulation();//new Gson().fromJson(FileUtils.readFileToString(new File("resources/stages/lvl-1.json")), Stage.class));
	//	} catch (JsonSyntaxException | IOException e) {
//			logger.error("failed to load stage!");
//			System.exit(0);
//		}
		
		//simulation.loadStage(1); // TODO
		
		logger.info(simulation.getEngine().getObjects().size() + " space objects have been created!");
	}

	public void nextTick() {
		simulation.tick();
	}

	public void reset() {
		simulation.getEngine().getObjects().clear();

		logger.info("simulation has been reset!");
	}

}
