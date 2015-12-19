package org.styl.gravitus;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class Specs {
	final static Logger logger = Logger.getLogger(Specs.class);

	public static final String USER_FILE = System.getProperty("user.home") + "Documents" +
			File.separator + "Gravitus" + File.separator + "application.properties";
	public static final String DEFAULT_FILE = "resources" + File.separator + "specs.properties";
	
	public static Specs instance;

	public int clockInterval;
	public double G;
	public int fpsRenderPeriod;

	public boolean orbitTrails;
	public boolean orbitsFixed;
	public int orbitTrailFrequency;
	public int orbitTrailMaxSize;

	public int frameX;
	public int frameY;

	public static void createInstance() {
		instance = new Specs();
	}
	
	public void loadProperties(File file) throws IOException {
		Properties prop = new Properties();
		prop.load(new FileInputStream(file));

		clockInterval = Integer.parseInt(prop.getProperty("clock_interval"));
		G = Double.parseDouble(prop.getProperty("gravitational_constant(G)"));
		fpsRenderPeriod = Integer.parseInt(prop.getProperty("fps_indicator_render_period"));

		orbitTrails = Integer.parseInt(prop.getProperty("orbit_trails")) != 0;
		orbitsFixed = Integer.parseInt(prop.getProperty("orbit_fixed")) != 0;
		orbitTrailFrequency = Integer.parseInt(prop.getProperty("orbit_trail_frequency"));
		orbitTrailMaxSize = Integer.parseInt(prop.getProperty("orbit_trail_max_size"));

		frameX = Integer.parseInt(prop.getProperty("frame_x"));
		frameY = Integer.parseInt(prop.getProperty("frame_y"));

		logger.info("properties loaded successfully!");
	}

}
