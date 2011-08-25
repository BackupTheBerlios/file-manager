/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.back2heaven.jbus;

import java.io.IOException;
import java.nio.file.ClosedWatchServiceException;
import java.nio.file.FileSystems;
import java.nio.file.NoSuchFileException;
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
public class AutoReloadConfiguration extends Configuration implements Runnable,
		AutoCloseable {

	private Path path;
	private WatchService watcher;

	public AutoReloadConfiguration(String file) {
		path = Paths.get(file);
	}

	@Override
	public void run() {
		boolean reload = true;
		boolean save = false;
		try {
			watcher = FileSystems.getDefault().newWatchService();
			path.getParent().register(watcher,
					StandardWatchEventKinds.ENTRY_MODIFY,
					StandardWatchEventKinds.ENTRY_DELETE);
			while (true) {
				WatchKey key = watcher.take();
				for (WatchEvent<?> event : key.pollEvents()) {
					Path a = path.getFileName();
					Path b = (Path) event.context();

					if (a.equals(b)) {
						if (StandardWatchEventKinds.ENTRY_MODIFY.equals(event
								.kind())) {
							// reload kontext if path is the same
							reload = true;
						}
						if (StandardWatchEventKinds.ENTRY_DELETE.equals(event
								.kind())) {
							// bei delete neu schreiben
							save = true;
						}
					}
				}
				key.reset();
				if (reload) {
					try {
						read(path);
					} catch (NoSuchFileException e) {
						// ignore
					}
					reload = false;
				}
				if (save) {
					save(path);
					save = false;
				}
			}
		} catch (IOException | InterruptedException
				| ClosedWatchServiceException e) {
			// ignore just schutdown the service
		}

		watcher = null;

	}

	@Override
	public void close() throws Exception {
		watcher.close();
	}
}
