/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.back2heaven.gui;

import de.back2heaven.jbus.events.GUINotify;

/**
 *
 * @author Jens Kapitza
 */
public class RemoveTab implements GUINotify{
    
    private String name;

    public RemoveTab(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    
    
}
