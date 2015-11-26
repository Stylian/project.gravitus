package org.styl.gravitus.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

import org.styl.gravitus.Specs;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter @Setter
public class View extends JFrame {

	private Dimension size;
	
	private Screen screen;
	private ToolBar toolBar;
	
	public View() {
		super();
		

		this.size = new Dimension(Specs.instance.frameX, Specs.instance.frameY);
		
		toolBar = new ToolBar();
		screen = new Screen();
		
		setLayout(new BorderLayout());
		add(toolBar, BorderLayout.NORTH);
		add(screen, BorderLayout.CENTER);
		
		setTitle("Gravity Simulator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setPreferredSize(size);
		setMaximumSize(size);
		setMinimumSize(size);
		pack();
		setLocationRelativeTo(null);
	}
	
}
