package de.back2heaven.jbus.events.notify;

import de.back2heaven.jbus.JavaService;
import de.back2heaven.jbus.events.Notify;
import de.back2heaven.jbus.events.StateEvent;

public class ServiceStart implements Notify, StateEvent {

	private static final long serialVersionUID = 4049731869313741784L;
	private JavaService service;

	public ServiceStart(JavaService service) {
		this.service = service;
	}

	public JavaService getService() {
		return service;
	}
}
