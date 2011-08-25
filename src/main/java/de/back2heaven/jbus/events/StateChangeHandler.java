/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.back2heaven.jbus.events;

import de.back2heaven.jbus.events.notify.ServiceStart;
import de.back2heaven.jbus.events.notify.ServiceStop;

/**
 *
 * @author Jens Kapitza
 */
public interface StateChangeHandler {
    void onStart(ServiceStart service);
    void onStop(ServiceStop service);
}
