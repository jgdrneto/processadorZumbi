package util;

import neander.ProcessadorNeander;

public class Buscar extends Thread {
	ProcessadorNeander pN;	
	
	public Buscar(ProcessadorNeander nPN){
		pN=nPN;
	}
		
	@Override
	public void run() {
		
		pN.Busca();
	}
	
}
