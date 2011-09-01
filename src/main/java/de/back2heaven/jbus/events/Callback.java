package de.back2heaven.jbus.events;

public interface Callback extends Event {
    CallbackListener getListener();
}
