package org.styl.gravitus.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingConstants;

import org.styl.gravitus.Specs;
import org.styl.gravitus.engine.Clock;

@SuppressWarnings("serial")
public class Screen extends JPanel implements ActionListener {
	
	private Controller controller;
	
	private Dimension size;
	
	private JFrame frame;
	private JLabel fps;
	
	private JMenuItem start;
	private JMenuItem pause;
	private JMenuItem stop;
	private JMenu fpsMenu;
	
	private int renderFPSCounter = 0;
	
	private List<SpaceObjectUIWrapper> wrappers;
	
	
	public Screen() {
		super();
		
		this.size = new Dimension(Specs.instance.frameX, Specs.instance.frameY);
		
		fps = new JLabel();
		fps.setForeground(Color.WHITE);
		fps.setHorizontalTextPosition(SwingConstants.RIGHT);
		fps.setBounds((int) (size.getWidth() - 60), 8, 70, 10);

		setBackground(Color.BLACK);
		setLayout(null);
		add(fps);
		
		Container frameContainer = new Container();
		frameContainer.setLayout(new BorderLayout());
		frameContainer.add(createMenuBar(), BorderLayout.NORTH);
		frameContainer.add(this, BorderLayout.CENTER);

		frame = new JFrame();		
		frame.setTitle("Gravity Simulator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setPreferredSize(size);
		frame.setMaximumSize(size);
		frame.setMinimumSize(size);
		frame.setContentPane(frameContainer);
		frame.pack();
		frame.setLocationRelativeTo(null);
	}

	private JMenuBar createMenuBar() {
		
		JMenuBar menuBar = new JMenuBar();
		
		// File
		JMenu fileMenu = new JMenu("File");
		
		JMenuItem exit = new JMenuItem("Exit");
		exit.setActionCommand("exit");
		exit.addActionListener(this);
		fileMenu.add(exit);
		
		
		menuBar.add(fileMenu);
		
		// Simulation
		JMenu simMenu = new JMenu("Simulation");
		
		start = new JMenuItem("Start");
		start.setActionCommand("start");
		start.addActionListener(this);
		simMenu.add(start);
		
		pause = new JMenuItem("Pause");
		pause.setEnabled(false);
		pause.setActionCommand("pause");
		pause.addActionListener(this);
		simMenu.add(pause);
		
		stop = new JMenuItem("Stop");
		stop.setEnabled(false);
		stop.setActionCommand("stop");
		stop.addActionListener(this);
		simMenu.add(stop);
		
		fpsMenu = new JMenu("FPS");
		addFpsOptions();
		simMenu.add(fpsMenu);
		
		menuBar.add(simMenu);
		
		// Preferences
		JMenu prefsMenu = new JMenu("Preferences");
		
		JCheckBoxMenuItem trails = new JCheckBoxMenuItem("Track Orbits");
		trails.setActionCommand("trails");
		trails.addActionListener(this);
		prefsMenu.add(trails);
		
		JCheckBoxMenuItem orbitPath = new JCheckBoxMenuItem("Fixed Orbits");
		orbitPath.setActionCommand("orbit_path");
		orbitPath.addActionListener(this);
		prefsMenu.add(orbitPath);
		
		menuBar.add(prefsMenu);
		
		return menuBar;
	}

	private void addFpsOptions() {
		ButtonGroup fpsGroup = new ButtonGroup(); 
		
		JRadioButtonMenuItem fps15 = new JRadioButtonMenuItem("15");
		fps15.setActionCommand("change_fps");
		fps15.addActionListener(this);
		fpsMenu.add(fps15);
		fpsGroup.add(fps15);
		
		JRadioButtonMenuItem fps20 = new JRadioButtonMenuItem("20");
		fps20.setActionCommand("change_fps");
		fps20.addActionListener(this);
		fpsMenu.add(fps20);
		fpsGroup.add(fps20);
		
		JRadioButtonMenuItem fps30 = new JRadioButtonMenuItem("30");
		fps30.setActionCommand("change_fps");
		fps30.addActionListener(this);
		fpsMenu.add(fps30);
		fpsGroup.add(fps30);
		
		JRadioButtonMenuItem fps40 = new JRadioButtonMenuItem("40");
		fps40.setSelected(true);
		fps40.setActionCommand("change_fps");
		fps40.addActionListener(this);
		fpsMenu.add(fps40);
		fpsGroup.add(fps40);
		
		JRadioButtonMenuItem fps60 = new JRadioButtonMenuItem("60");
		fps60.setActionCommand("change_fps");
		fps60.addActionListener(this);
		fpsMenu.add(fps60);
		fpsGroup.add(fps60);
		
		JRadioButtonMenuItem fps120 = new JRadioButtonMenuItem("120");
		fps120.setActionCommand("change_fps");
		fps120.addActionListener(this);
		fpsMenu.add(fps120);
		fpsGroup.add(fps120);
		
		JRadioButtonMenuItem fps240 = new JRadioButtonMenuItem("240");
		fps240.setActionCommand("change_fps");
		fps240.addActionListener(this);
		fpsMenu.add(fps240);
		fpsGroup.add(fps240);
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public List<SpaceObjectUIWrapper> getWrappers() {
		return wrappers;
	}

	public void setWrappers(List<SpaceObjectUIWrapper> wrappers) {
		this.wrappers = wrappers;
		
		for(SpaceObjectUIWrapper wrapper : wrappers) {
			add(wrapper);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		switch(e.getActionCommand()) {
		
		case "start" :	
			controller.startSimulation();	
			start.setEnabled(false);
			pause.setEnabled(true);
			stop.setEnabled(true);
			break;
		case "pause" :
			
			try {
				controller.pauseSimulation();			
				pause.setEnabled(false);
				stop.setEnabled(true);
				start.setEnabled(true);
				start.setText("Continue");
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			break;		
		case "stop" :		
			try {
				controller.stopSimulation();				
				stop.setEnabled(false);
				pause.setEnabled(false);
				start.setEnabled(true);
				start.setText("Start");
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			break;		
		case "change_fps" :
			String btnText = ((JRadioButtonMenuItem) e.getSource()).getText();
			controller.getSimulation().setFps(Integer.parseInt(btnText));
			break;		
		case "exit" :	
			frame.dispose();	
			break;		
		case "trails" :		
			SpaceObjectUIWrapper.switchOrbitTrails(wrappers);	
			break;	
		case "orbit_path" :	
			SpaceObjectUIWrapper.switchOrbitPath();
			break;
		}
	
	}
	
	public void render() {
		
		int calcFPS = Clock.INSTANCE.fps();
		
		if(renderFPSCounter++ > 10) { //GravitusProperties.INSTANCE.fpsRenderPeriod) {
			renderFPSCounter = 0;
			fps.setText(calcFPS + " FPS");
		}
		
		//update wrappers
		SpaceObjectUIWrapper.positionsCounter ++;
		if(wrappers != null) {
			wrappers.forEach(SpaceObjectUIWrapper::update);
		}
		
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		if(SpaceObjectUIWrapper.orbitTrails) {
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

	public JFrame getFrame() {
		return frame;
	}
}
