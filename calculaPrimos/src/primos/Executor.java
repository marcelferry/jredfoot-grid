package primos;

import org.jredfoot.utgrid.tasks.IBagOfTask;


public class Executor {
	
	void execute(IBagOfTask pacote){
		try {
			pacote.run();			
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		CalculaPrimos cp = new CalculaPrimos();
		Executor exec = new Executor();
		exec.execute(cp);
	}

}
