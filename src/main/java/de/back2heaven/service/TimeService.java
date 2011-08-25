package de.back2heaven.service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import de.back2heaven.jbus.JavaService;
import de.back2heaven.pattern.AbstractObservable;

public class TimeService extends AbstractObservable implements JavaService {

	private boolean active;

	public synchronized void setActive(boolean active) {
		this.active = active;
	}

	public synchronized boolean isActive() {
		return active;
	}

	@Override
	public void close() {
		setActive(false);
	}
	
	@Override
	public void run() {
		setActive(true);
		while (isActive()) {
			try {
				TimeUnit.SECONDS.sleep(1);
				Date date = new Date();
				notifyObservers("Current Time is. " + date);
			} catch (InterruptedException e) {
				throw new RuntimeException("TimeService dead");
			}

		}
		System.out.println("ENDE2");
	}
}
