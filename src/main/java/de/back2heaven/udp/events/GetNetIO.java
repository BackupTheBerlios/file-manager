/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package de.back2heaven.udp.events;

import de.back2heaven.jbus.events.Callback;
import de.back2heaven.jbus.events.CallbackListener;

/**
 *
 * @author Jens Kapitza
 */
public class GetNetIO implements  Callback {

    private CallbackListener listener;

    public GetNetIO(CallbackListener listener) {
        this.listener = listener;
    }
    
    
    @Override
    public CallbackListener getListener() {
        return listener;
    }
    
    
}
