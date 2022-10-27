package org.jredfoot.utgrid.tasks;

import java.util.List;

public abstract class BagOfTask implements IBagOfTask {

	public static final int MAX_SIMULT_TASKS = 1000;
	protected List<ITask> tasks;
	@SuppressWarnings("unchecked")
	protected IDataProvider provider;
	boolean completeGenerate;
	boolean completeExecute;
	
	@Override
	public void run() {
		populateDataProvider();
		Thread generate = new Thread() {
			
			@Override
			public void run() {				
				try {
					generateTasks();
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					completeGenerate = true;
					System.out.println("Tarefas geradas");
				}
			}
		}; 		
		Thread execute = new Thread() {
			
			@Override
			public void run() {	
				long i = System.currentTimeMillis();
				try {
					while(!completeGenerate){
						executeTasks();
					}
				} catch (Throwable e) {
					e.printStackTrace();
				} finally {
					completeExecute = true;
					System.out.println("Tarefas executadas");
				}
				long k = System.currentTimeMillis();
				
			}
		};
		generate.start();
		execute.start();
	}
	
	@Override
	public List<ITask> getTasks() {
		return tasks;
	}
	
	@Override
	public boolean isComplete() {
		return completeGenerate & completeExecute;
	}

}
