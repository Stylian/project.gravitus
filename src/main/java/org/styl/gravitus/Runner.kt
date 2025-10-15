package org.styl.gravitus;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.styl.gravitus.entities.Simulation;
import org.styl.gravitus.entities.Stage;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import lombok.Getter;

public class Runner {
	final static Logger logger = Logger.getLogger(Runner.class);

	@Getter private Simulation simulation;

	private List<String> stages = new ArrayList<>();

	public Runner() {

		stages.add("lvl-1.json");

	}

	public void initNextStage() {

		if (stages.iterator().hasNext()) {
			logger.info("attempting to load next stage...");
			
			String stageName = stages.iterator().next();
			
			simulation = new Simulation(loadStage(stageName));
			
			logger.info("stage " + stageName.split("\\.")[0] + " has been loaded.");
			logger.info(simulation.getEngine().getObjects().size() + " space objects have been created!");

		} else {
			gameOver();
		}
	}

	private Stage loadStage(String nameOfStage) {

		File stageFile = new File(SystemLocations.STAGES_FOLDER + nameOfStage);

		Stage stage = null;
		try {
			stage = new Gson().fromJson(FileUtils.readFileToString(stageFile), Stage.class);

		} catch (JsonSyntaxException e) {
			logger.error("Error with stage json file format!");
			logger.error("Program will now terminate!");
			System.exit(0);
		} catch (IOException e) {
			logger.error("failed to load stage!");
			e.printStackTrace();
			logger.error("Program will now terminate!");
			System.exit(0);
		}

		return stage;
	}

	private void gameOver() {
		logger.info("GAME OVER! TODO");
	}

	public void nextTick() {
		simulation.tick();
	}

	public void reset() {
		simulation.getEngine().getObjects().clear();

		logger.info("simulation has been reset!");
	}

}
