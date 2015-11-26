package org.styl.gravitus.ui;

import java.util.List;

import org.styl.gravitus.Specs;
import org.styl.gravitus.engine.Clock;
import org.styl.gravitus.entities.SpaceObjectUIWrapper;

import lombok.Getter;
import lombok.Setter;

public class Renderer {

	@Getter @Setter private List<SpaceObjectUIWrapper> wrappers;
	private Screen screen;
	
	private int renderFPSCounter = 0;
	
	public Renderer(Screen screen) {
		this.screen = screen;
	}
	
	public void render() {
		
		int calcFPS = Clock.INSTANCE.fps();
		
		if(renderFPSCounter++ > Specs.instance.fpsRenderPeriod) {
			renderFPSCounter = 0;
			screen.getFps().setText(calcFPS + " FPS");
		}
		
		//update wrappers
		SpaceObjectUIWrapper.positionsCounter ++;
		
		if(wrappers != null) {
			wrappers.forEach(SpaceObjectUIWrapper::update);
		}
		
		screen.repaint();
	}
}
