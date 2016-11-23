package pipeline;

import zumbi.ProcessadorZumbi;

public class Buscar extends Thread {
	ProcessadorZumbi pN;	
	
	public Buscar(ProcessadorZumbi nPN){
		pN=nPN;
	}
		
	@Override
	public void run() {
		
		pN.Busca();
	}
	
}
