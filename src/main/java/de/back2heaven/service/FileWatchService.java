/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.back2heaven.service;

import de.back2heaven.jbus.JavaService;
import de.back2heaven.pattern.AbstractObservable;
import java.io.IOException;
import java.nio.file.ClosedWatchServiceException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

/**
 *
 * @author Jens Kapitza
 */
public class FileWatchService extends AbstractObservable implements JavaService {

    private WatchService watcher;

    @Override
    public void close() {
        try {
            watcher.close();
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public FileWatchService() throws IOException {
        Path path = Paths.get(".", "data");
        Files.createDirectories(path);
        watcher = FileSystems.getDefault().newWatchService();
        path.register(watcher, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
    }

    @Override
    public void run() {
        while (true) {
            try {
                WatchKey key = watcher.take();
                for (WatchEvent<?> event : key.pollEvents()) {
                    notifyObservers("Kind: " + event.kind() + ", Path: " + event.context());
                }
                key.reset();
            }
            catch (InterruptedException ex) {
                break;
            }
            catch(ClosedWatchServiceException e2){
                break; // ignore
            }

        }
    }
}
