package principal;

import javax.swing.JFileChooser;

import neander.InterpretadorNeander;
import neander.ProcessadorNeander;
import util.Buscar;
import util.Executar;


public class principal {

	public static void main(String[] args) {
		
		// TODO Auto-generated method stub
		
		//Iniguaçu
		
		/*
		JFileChooser abrirArquivo = new JFileChooser();

		abrirArquivo.showOpenDialog(null);

		System.out.println(abrirArquivo.getSelectedFile().getPath());
		
		InterpretadorNeander n = new InterpretadorNeander(abrirArquivo.getSelectedFile().getPath());
		processadorNeander processadorN = new processadorNeander(n.getInstrucoes(), 10);

		processadorN.start();
		*/
		
		String nome = "testes/Qb - Neander.txt";
		
		InterpretadorNeander n = new InterpretadorNeander(nome);
				
		ProcessadorNeander pN = new ProcessadorNeander(n.getInstrucoes(), 10);
		
		Buscar buscarInstrucao; // new Buscar(pN);
		
		Executar executar; //=  new Executar(pN);
		
		while(!pN.parar){
			
			buscarInstrucao = new Buscar(pN); 
			
			executar = new Executar(pN);
			
			buscarInstrucao.start();
			executar.start();
			
			while(buscarInstrucao.isAlive() || executar.isAlive()){
				//Esperando as threads morrerem para poder executá-las denovo
			}
			
			
		}
		
		System.out.println("Acumulador: " + pN.getRegistradores().get(0));
		System.out.println("Memória:");
		for(int i=1;i<pN.getMemoria().size();i++){
			System.out.println(i + " " + pN.getMemoria().get(i));
		}
	}

}
