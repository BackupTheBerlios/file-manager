package de.back2heaven.jbus.events;

public interface CallbackListener {
	void callback(Callback question, Event answer);
}
