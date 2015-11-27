package org.styl.gravitus.ui;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import org.styl.gravitus.entities.SpaceObject;

import lombok.Getter;

@SuppressWarnings("serial")
@Getter
public class SpaceObjectUIWrapper extends JLabel {
	
	private SpaceObject spaceObject;
	private List<Point> pastPositions;
	
	public SpaceObjectUIWrapper(SpaceObject so) {
		super();
		
		this.spaceObject = so;
		
		pastPositions = new LinkedList<>();
		
		ImageIcon imageIcon = new ImageIcon( so.getImage() );
		Image image = imageIcon.getImage();	
		Image newimg = image.getScaledInstance( so.getRadius()*2, so.getRadius()*2, java.awt.Image.SCALE_SMOOTH );	
		setIcon( new ImageIcon(newimg) );
		
		setSize( new Dimension(so.getRadius()*2, so.getRadius()*2) );
		setLocation( (int)(so.getPosx()/1000), (int)(so.getPosy()/1000) );
	}


}
