package de.back2heaven.pattern;

public interface Observable {
	void addObserver(Observer observer);

	void removeObserver(Observer observer);

	void notifyObservers(Object arg);
}
