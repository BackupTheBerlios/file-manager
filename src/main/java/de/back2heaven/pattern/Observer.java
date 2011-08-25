package de.back2heaven.pattern;

public interface Observer {
	boolean accept(Object obj);

	 void update(Observable from, Object args);
}
