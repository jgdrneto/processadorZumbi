package pipeline;

import zumbi.ProcessadorZumbi;

public class Executar extends Thread{

	ProcessadorZumbi pN;
	
	public Executar(ProcessadorZumbi nPN){
		pN = nPN;
	}
	
	@Override
	public void run() {
		
		pN.interpretarInstrucao();
		
	}	
}
