package org.styl.gravitus;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class Specs {
	final static Logger logger = Logger.getLogger(Specs.class);
	
	public static Specs instance;

	public int clockInterval;
	public double G;
	public int fpsRenderPeriod;
	public int orbitTrailFrequency;
	public int orbitTrailMaxSize;
	
	public int frameX;
	public int frameY;
	
	public static void createInstance() {
		instance = new Specs();
	}
	
	public Specs() {
		logger.info("getting properties from file");
		
		try(InputStream input = new FileInputStream("resources/gravitus.properties")) {
			
			Properties prop = new Properties();
			prop.load(input);
			
			clockInterval = Integer.parseInt(prop.getProperty("clock_interval"));
			G = Double.parseDouble(prop.getProperty("gravitational_constant(G)"));
			fpsRenderPeriod = Integer.parseInt(prop.getProperty("fps_indicator_render_period"));
			orbitTrailFrequency = Integer.parseInt(prop.getProperty("orbit_trail_frequency"));
			orbitTrailMaxSize = Integer.parseInt(prop.getProperty("orbit_trail_max_size"));
			
			frameX = Integer.parseInt(prop.getProperty("frame_x"));
			frameY = Integer.parseInt(prop.getProperty("frame_y"));
			
			logger.info("properties loaded successfully!");
		} catch (IOException ex) {
			logger.error("failed to load properties!");
			System.exit(0);
		}
		
	}
	
}
