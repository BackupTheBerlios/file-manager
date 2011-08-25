package de.back2heaven.service;

import de.back2heaven.jbus.InterruptableLinkedBlockingQueue;
import de.back2heaven.jbus.JavaService;
import de.back2heaven.pattern.Observable;
import de.back2heaven.pattern.Observer;

public class TimeReader implements Observer, JavaService {

	private boolean active;
	private InterruptableLinkedBlockingQueue<String> msg = new InterruptableLinkedBlockingQueue<>();

	public synchronized void setActive(boolean active) {
		this.active = active;
	}

	public synchronized boolean isActive() {
		return active;
	}

	@Override
	public void close() {
		setActive(false);
		msg.interrupt();
	}

	@Override
	public void run() {
		setActive(true);
		while (isActive()) {
			String message = msg.take();
			System.out.println(message);
		}
		System.out.println("ENDE");
	}

	@Override
	public boolean accept(Object obj) {
		return String.class.equals(obj.getClass());
	}

	@Override
	public void update(Observable from, Object args) {
		msg.offer((String) args);
	}
}
