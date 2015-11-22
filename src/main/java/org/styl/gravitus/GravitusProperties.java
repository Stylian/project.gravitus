package org.styl.gravitus;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class GravitusProperties {
	final static Logger logger = Logger.getLogger(GravitusProperties.class);
	
	public static GravitusProperties INSTANCE;

	public int clockInterval;
	public double G;
	public int fpsRenderPeriod;
	public int orbitTrailFrequency;
	public int orbitTrailMaxSize;
	
	public static void createInstance() {
		INSTANCE = new GravitusProperties();
	}
	
	public GravitusProperties() {
		logger.info("getting properties from file");
		
		try(InputStream input = new FileInputStream("resources/gravitus.properties")) {
			
			Properties prop = new Properties();
			prop.load(input);
			
			clockInterval = Integer.parseInt(prop.getProperty("clock_interval"));
			G = Double.parseDouble(prop.getProperty("gravitational_constant(G)"));
			fpsRenderPeriod = Integer.parseInt(prop.getProperty("fps_indicator_render_period"));
			orbitTrailFrequency = Integer.parseInt(prop.getProperty("orbit_trail_frequency"));
			orbitTrailMaxSize = Integer.parseInt(prop.getProperty("orbit_trail_max_size"));
			
			logger.info("properties loaded successfully!");
		} catch (IOException ex) {
			logger.error("failed to load properties!");
			System.exit(0);
		}
		
	}
	
}
