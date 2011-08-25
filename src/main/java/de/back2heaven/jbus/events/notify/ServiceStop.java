package de.back2heaven.jbus.events.notify;

import de.back2heaven.jbus.JavaService;
import de.back2heaven.jbus.events.Notify;
import de.back2heaven.jbus.events.StateEvent;

public class ServiceStop implements Notify, StateEvent {

	private static final long serialVersionUID = -8100116112737798217L;
	private JavaService service;

	public ServiceStop(JavaService service) {
		this.service = service;
	}

	public JavaService getService() {
		return service;
	}
}
