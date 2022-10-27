package primos;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.List;

import org.jredfoot.utgrid.tasks.BagOfTask;
import org.jredfoot.utgrid.tasks.IBagOfTask;
import org.jredfoot.utgrid.tasks.ITask;
import org.jredfoot.utgrid.tasks.NotifyingTask;
import org.jredfoot.utgrid.tasks.Result;
import org.jredfoot.utgrid.tasks.Task;
import org.jredfoot.utgrid.tasks.TaskCompleteListener;



public class CalculaPrimos extends BagOfTask {

	/**
	 *  Default Serial
	 */
	private static final long serialVersionUID = 8398581768408276084L;

	@Override
	public void generateTasks() {		
		if(this.provider != null){
			this.tasks = new ArrayList<ITask>();			
			while(provider.hasNext()){
				if(tasks.size() == IBagOfTask.MAX_SIMULTANION_TASKS){
					Thread.yield();
					continue;
				}
				final Long numero = (Long)provider.next();
				ITask tarefa = new NotifyingTask() {					
					@Override
					public void runWithNotify() {
						long verificar  = (long)Math.sqrt(numero);						
						verificar++;
						//System.out.print("Verificar: " + verificar + " - ");
						long divisor = 2;
						boolean primo = true;
						while(divisor <= verificar){							
							if((numero % divisor) == 0){								
								primo = false;
								if(numero == 2) primo = true;
								break;
							} 
							divisor++;							
						}
						if(primo){
							result = new Result(numero.toString(), new String( numero + " é primo"), 0);
						} else {
							result = new Result(numero.toString(), new String( numero + " NÃO é primo"), 0);
						}
					}
					
					@Override
					public String toString() {
						return "Tarefa com numero: " + numero;
					}

					@Override
					public Date getTimeout() {
						// TODO Auto-generated method stub
						return null;
					}
				};
				tasks.add(tarefa);
				tarefa = null;
			}
		}
	}
	
	@Override
	public void executeTasks() throws Throwable {
		//long inicio = System.currentTimeMillis();
		List<ITask> tarefas = getTasks();
		if(tarefas!=null){
			try{
				for(ITask tarefa : tarefas){
					//System.out.println("Executando Tarefa com numero" + tarefa.toString());
					if(tarefa != null && tarefa instanceof NotifyingTask){
						NotifyingTask t1 = (NotifyingTask)tarefa;										
						TaskCompleteListener listener = new TaskCompleteListener() {						
							@Override
							public void notifyOfTaskComplete(Runnable runnable) {							
								if(runnable instanceof ITask){
									Task tarefa = (Task) runnable;
									System.out.println( tarefa.getResult().getMessage() );
								}
							}
						};
											
						t1.addListener(listener);
						new Thread(t1).start();
						
						// Limpeza de memória
						tarefas.remove(tarefa);
						t1 = null;
						listener = null;
					}
					tarefa = null;
				}
			} catch(ConcurrentModificationException ex){
				//
			}
		}
		//long fim = System.currentTimeMillis();
		//System.out.println( ((fim - inicio) / 1000) + " seg");
	}

	@Override
	public void populateDataProvider() {		
			//this.provider = new ArquivoEntradaDataProvider(new File("C:\\Dados.txt"));
			this.provider = new MemoryDataProvider(100000L);
	}

}
