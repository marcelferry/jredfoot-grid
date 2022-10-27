package org.jredfoot.utgrid.tasks;

public interface TaskCompleteListener {
	void notifyOfTaskComplete(final Runnable runnable);
}
