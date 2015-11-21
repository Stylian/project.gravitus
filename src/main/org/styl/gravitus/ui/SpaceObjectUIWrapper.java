package org.styl.gravitus.ui;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import org.styl.gravitus.entities.SpaceObject;

@SuppressWarnings("serial")
public class SpaceObjectUIWrapper extends JLabel {

	private static int positionsCounter = 0;
	
	private SpaceObject obj;
	
	private List<Point> pastPositions;

	public SpaceObjectUIWrapper(SpaceObject so) {
		super();

		pastPositions = new LinkedList<>();
		this.obj = so;
		
		ImageIcon imageIcon = new ImageIcon(so.getImage());
		Image image = imageIcon.getImage();
		
		Image newimg = image.getScaledInstance(
				so.getRadius()*2,
				so.getRadius()*2,
				java.awt.Image.SCALE_SMOOTH
			);
		
		setIcon(new ImageIcon(newimg));
		
	
		setSize(
				new Dimension(
						so.getRadius()*2,
						so.getRadius()*2
					)
				);
		
		setLocation(
				(int)(so.getPosx()/1000),
				(int)(so.getPosy()/1000)
			);

	}

	public void update() {
		int x = (int)(obj.getPosx()/1000);
		int y = (int)(obj.getPosy()/1000);
		
		setLocation(x, y);
		
		if(++positionsCounter % 100 == 0) {
		
			if(pastPositions.size() > 50) {
				pastPositions.remove(0);
			}
			
			pastPositions.add(new Point(x,y));
		}
		
	}
	
	public List<Point> getPastPositions() {
		return pastPositions;
	}
	
}
