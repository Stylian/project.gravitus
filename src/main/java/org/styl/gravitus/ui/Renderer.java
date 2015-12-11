package org.styl.gravitus.ui;

import java.awt.Point;
import java.util.List;

import org.styl.gravitus.Specs;
import org.styl.gravitus.engine.Clock;

import lombok.Getter;

public class Renderer {

	@Getter private List<SpaceObjectUIWrapper> wrappers;
	private Screen screen;
	private int zoom = 1000;

	private static int renderFPSCounter = 0;
	private static int positionsCounter = 0;

	public Renderer(Screen screen, List<SpaceObjectUIWrapper> wrappers) {
		this.screen = screen;
		this.wrappers = wrappers;
	}

	public void render() {

		int calcFPS = Clock.INSTANCE.fps();

		if (renderFPSCounter++ > Specs.instance.fpsRenderPeriod) {
			renderFPSCounter = 0;
			screen.getFps().setText(calcFPS + " FPS");
		}

		// update wrappers
		positionsCounter++;

		if (wrappers != null) {
			wrappers.forEach(w -> updateWrapper(w));
		}

		screen.repaint();
	}

	public void zoomBy(int by) {
		zoom += by;
	}

	private void updateWrapper(SpaceObjectUIWrapper w) {

		int x = (int) (w.getSpaceObject().getPosx() * zoom / 1000000);
		int y = (int) (w.getSpaceObject().getPosy() * zoom / 1000000);
		int rf = (int) (w.getSpaceObject().getRadius() * 1414 * zoom / 1000000);

		w.setLocation(x - rf, y - rf);

		if (Specs.instance.orbitTrails) {

			if (positionsCounter % Specs.instance.orbitTrailFrequency == 0) {
				positionsCounter = 0;

				Point newPoint = new Point(x, y);

				if (!Specs.instance.orbitsFixed) {
					if (w.getPastPositions().size() > Specs.instance.orbitTrailMaxSize) {
						w.getPastPositions().remove(0);
					}
				} else {
					if (w.getPastPositions().size() > 50) {
						if (isClose(newPoint, w.getPastPositions().get(0))) {
							w.getPastPositions().remove(0);
						}

					}
				}

				w.getPastPositions().add(newPoint);
			}

		}
	}

	private boolean isClose(Point newPoint, Point oldPoint) {
		int dx = newPoint.x - oldPoint.x;
		int dy = newPoint.y - oldPoint.y;
		long sumsqr = (long) (Math.pow(dx, 2) + Math.pow(dy, 2));
		double dist = Math.sqrt(sumsqr);
		return dist < 10;
	}
}
