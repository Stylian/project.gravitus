package org.styl.gravitus.ui;

import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

import org.styl.gravitus.Specs;
import org.styl.gravitus.SystemLocations;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
public class ToolBar extends JMenuBar {

	private ActionListener listener;

	private JMenuItem start;
	private JMenuItem pause;
	private JMenuItem stop;
	private JMenu fpsMenu;
	private JMenu prefsMenu;

	public ToolBar() {
		super();
	}

	public void init() {

		// File
		JMenu fileMenu = new JMenu("File");

		JMenuItem exit = new JMenuItem("Exit");
		exit.setIcon(new ImageIcon(SystemLocations.ICONS_FOLDER + "exit.png"));
		exit.setActionCommand("exit");
		exit.addActionListener(listener);
		fileMenu.add(exit);

		add(fileMenu);

		// Simulation
		JMenu simMenu = new JMenu("Simulation");

		start = new JMenuItem("Start");
		start.setIcon(new ImageIcon(SystemLocations.ICONS_FOLDER + "start.png"));
		start.setActionCommand("start");
		start.addActionListener(listener);
		simMenu.add(start);

		pause = new JMenuItem("Pause");
		pause.setIcon(new ImageIcon(SystemLocations.ICONS_FOLDER + "pause.png"));
		pause.setEnabled(false);
		pause.setActionCommand("pause");
		pause.addActionListener(listener);
		simMenu.add(pause);

		stop = new JMenuItem("Stop");
		stop.setIcon(new ImageIcon(SystemLocations.ICONS_FOLDER + "stop.png"));
		stop.setEnabled(false);
		stop.setActionCommand("stop");
		stop.addActionListener(listener);
		simMenu.add(stop);

		add(simMenu);

		// Preferences
		prefsMenu = new JMenu("Preferences");
		prefsMenu.setEnabled(false);

		JCheckBoxMenuItem trails = new JCheckBoxMenuItem("Track Orbits");
		trails.setSelected(Specs.instance.orbitTrails);
		trails.setActionCommand("trails");
		trails.addActionListener(listener);
		prefsMenu.add(trails);

		JCheckBoxMenuItem orbitPath = new JCheckBoxMenuItem("Fixed Orbits");
		orbitPath.setSelected(Specs.instance.orbitsFixed);
		orbitPath.setActionCommand("orbit_path");
		orbitPath.addActionListener(listener);
		prefsMenu.add(orbitPath);

		fpsMenu = new JMenu("FPS");

		ButtonGroup fpsGroup = new ButtonGroup();

		JRadioButtonMenuItem fps15 = new JRadioButtonMenuItem("15");
		fps15.setActionCommand("change_fps");
		fps15.addActionListener(listener);
		fpsMenu.add(fps15);
		fpsGroup.add(fps15);

		JRadioButtonMenuItem fps20 = new JRadioButtonMenuItem("20");
		fps20.setActionCommand("change_fps");
		fps20.addActionListener(listener);
		fpsMenu.add(fps20);
		fpsGroup.add(fps20);

		JRadioButtonMenuItem fps30 = new JRadioButtonMenuItem("30");
		fps30.setActionCommand("change_fps");
		fps30.addActionListener(listener);
		fpsMenu.add(fps30);
		fpsGroup.add(fps30);

		JRadioButtonMenuItem fps40 = new JRadioButtonMenuItem("40");
		fps40.setSelected(true);
		fps40.setActionCommand("change_fps");
		fps40.addActionListener(listener);
		fpsMenu.add(fps40);
		fpsGroup.add(fps40);

		JRadioButtonMenuItem fps60 = new JRadioButtonMenuItem("60");
		fps60.setActionCommand("change_fps");
		fps60.addActionListener(listener);
		fpsMenu.add(fps60);
		fpsGroup.add(fps60);

		JRadioButtonMenuItem fps120 = new JRadioButtonMenuItem("120");
		fps120.setActionCommand("change_fps");
		fps120.addActionListener(listener);
		fpsMenu.add(fps120);
		fpsGroup.add(fps120);

		JRadioButtonMenuItem fps240 = new JRadioButtonMenuItem("240");
		fps240.setActionCommand("change_fps");
		fps240.addActionListener(listener);
		fpsMenu.add(fps240);
		fpsGroup.add(fps240);

		prefsMenu.add(fpsMenu);

		add(prefsMenu);
	}
}
