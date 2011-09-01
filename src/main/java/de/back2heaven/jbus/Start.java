package de.back2heaven.jbus;

/**
 * Bootstrap JBUS-System.
 *
 * @author Jens Kapitza
 *
 */
public class Start {

    public static void main(String[] args) throws Exception {
    	JBus bus = new JBus();
        bus.load("de.back2heaven.jbus.AutoReloadConfiguration");
//        bus.load("de.back2heaven.service.TimeService");
        bus.load("de.back2heaven.service.TimeReader");

        // service Agent
        // startet und stop alles
        // ohne Swing wird sofor beendet
        bus.load("de.back2heaven.jbus.agent.SwingAgent");
        
        bus.load("de.back2heaven.service.FileWatchService");
        
        bus.load("de.back2heaven.udp.NetIO");
        
        bus.load("de.back2heaven.udp.StunService");
        
        
        for (String clazz : args){
        	bus.load(clazz);
        }
        

    }
}