package org.styl.gravitus.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.styl.gravitus.Specs;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter @Setter
public class Screen extends JPanel {

	private JLabel fps;
	private JLabel zoomOut;
	private JLabel zoomIn;
	
	public Screen() {
		super();
	}

	public void init() {
		
		fps = new JLabel();
		fps.setForeground(Color.WHITE);
		fps.setHorizontalTextPosition(SwingConstants.RIGHT);
		fps.setBounds((int) (Specs.instance.frameX - 60), 8, 70, 10);
		add(fps);
		
		zoomIn = new JLabel("r");
		zoomIn.setBounds((int) (Specs.instance.frameX - 60), 30, 16, 16);
		zoomIn.setIcon(new ImageIcon("resources/icons/plus.png"));
		add(zoomIn);
		
		zoomOut = new JLabel("a");
		zoomOut.setBounds((int) (Specs.instance.frameX - 35), 30, 16, 16);
		zoomOut.setIcon(new ImageIcon("resources/icons/minus.png"));
		add(zoomOut);

		
		setBackground(Color.BLACK);
		setLayout(null);

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		if(Specs.instance.orbitTrails) {
			g.setColor(Color.WHITE);
			Graphics2D g2d = (Graphics2D) g;
			
			for(Component cmpnt : this.getComponents()) {
				if(cmpnt instanceof SpaceObjectUIWrapper) {
					SpaceObjectUIWrapper wrap = (SpaceObjectUIWrapper) cmpnt;

					Point prev = null;
					for(Point p : wrap.getPastPositions()) {
						if(prev != null) {
							g2d.drawLine(
								(int)(p.getX()),
								(int)(p.getY()),
								(int)(prev.getX()),
								(int)(prev.getY())
							);
						}
						prev = p;
					}
					
				}
			}
		}
		
	}

}
