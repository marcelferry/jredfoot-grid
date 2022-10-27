package org.jredfoot.utgrid.tasks;

import java.io.Serializable;
import java.util.List;

public interface IBagOfTask extends Runnable, Serializable {

	int MAX_SIMULTANION_TASKS = 2000;

	public boolean isComplete();

	public void populateDataProvider();

	public void generateTasks();

	public void executeTasks() throws Throwable;

	public List<ITask> getTasks();

}
