package util;

import neander.ProcessadorNeander;

public class Executar extends Thread{

	ProcessadorNeander pN;
	
	public Executar(ProcessadorNeander nPN){
		pN = nPN;
	}
	
	@Override
	public void run() {
		
		System.out.println("run Executar");
		
		pN.interpretarInstrucao();
		
	}	
}
