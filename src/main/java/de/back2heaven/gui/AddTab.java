/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.back2heaven.gui;

import de.back2heaven.jbus.events.GUINotify;
import javax.swing.JPanel;

/**
 * 
 * @author Jens Kapitza
 */
public class AddTab implements GUINotify {

	private static final long serialVersionUID = 5693436756575600158L;
	private JPanel tab;
	private String name;

	public AddTab(String name, JPanel tab) {
		this.tab = tab;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public JPanel getTab() {
		return tab;
	}
}
