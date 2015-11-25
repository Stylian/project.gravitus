package org.styl.gravitus.entities;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import org.styl.gravitus.Specs;

import lombok.Getter;

@SuppressWarnings("serial")
public class SpaceObjectUIWrapper extends JLabel {

	public static boolean orbitTrails = false;
	public static boolean fixedOrbits = false;

	public static int positionsCounter = 0;
	
	private SpaceObject obj;
	@Getter private List<Point> pastPositions;

	public static void switchOrbitTrails(List<SpaceObjectUIWrapper> wrappers) {
		wrappers.forEach( w -> w.pastPositions.clear() );
		orbitTrails = !orbitTrails;
	}
	
	public static void switchOrbitPath() {
		fixedOrbits = !fixedOrbits;
	}
	
	public SpaceObjectUIWrapper(SpaceObject so) {
		super();
		
		this.obj = so;
		
		pastPositions = new LinkedList<>();
		
		ImageIcon imageIcon = new ImageIcon( so.getImage() );
		Image image = imageIcon.getImage();	
		Image newimg = image.getScaledInstance( so.getRadius()*2, so.getRadius()*2, java.awt.Image.SCALE_SMOOTH );	
		setIcon( new ImageIcon(newimg) );
		
		setSize( new Dimension(so.getRadius()*2, so.getRadius()*2) );
		setLocation( (int)(so.getPosx()/1000), (int)(so.getPosy()/1000) );
	}

	public void update() {
		int x = (int)(obj.getPosx()/1000);
		int y = (int)(obj.getPosy()/1000);
		int rf = (int) (obj.getRadius() * 1.414);
		
		setLocation(x - rf, y - rf);

		if(orbitTrails) {

			if(positionsCounter % Specs.instance.orbitTrailFrequency == 0) {
				positionsCounter = 0;
				
				Point newPoint = new Point(x,y);
				
				if(!fixedOrbits) {
					if(pastPositions.size() > Specs.instance.orbitTrailMaxSize) {
						pastPositions.remove(0);
					}
				}else {
					if(pastPositions.size() > 50) {
						if(isClose(newPoint, pastPositions.get(0))) {
							pastPositions.remove(0);
						}
						
					}	
				}
				
				pastPositions.add(newPoint);
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
