/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.back2heaven.pattern;

import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author Jens Kapitza
 */
public class AbstractObservable implements Observable {
    
    private LinkedBlockingQueue<Observer> observers = new LinkedBlockingQueue<>();
    

    @Override
    public void addObserver(Observer observer) {
     observers.offer(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(Object arg) {
        for (Observer o : observers){
            if (o.accept(arg)){
                o.update(this, arg);
            }
        }
    }
    
    
    
}
