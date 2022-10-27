package org.jredfoot.utgrid.tasks;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public abstract class NotifyingTask extends Task {

	private final Set<TaskCompleteListener> listeners = 
		new CopyOnWriteArraySet<TaskCompleteListener>();

	public final void addListener(TaskCompleteListener listener) {
		this.listeners.add(listener);
	}

	public final void removeListener(TaskCompleteListener listener) {
		this.listeners.remove(listener);
	}

	private final void notifyListeners() {
		for(TaskCompleteListener listener : listeners){
			listener.notifyOfTaskComplete(this);
		}
	}

	@Override
	public final void run() {
		try{
			runWithNotify();
		} finally {
			notifyListeners();
		}
	}
	
	public abstract void runWithNotify();

}
