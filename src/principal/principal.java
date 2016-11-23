package principal;

import javax.swing.JFileChooser;

import pipeline.Buscar;
import pipeline.Executar;
import zumbi.InterpretadorZumbi;
import zumbi.ProcessadorZumbi;


public class principal {

	public static void main(String[] args) {
		
		JFileChooser abrirArquivo = new JFileChooser();

		abrirArquivo.showOpenDialog(null);
		
		InterpretadorZumbi n = new InterpretadorZumbi(abrirArquivo.getSelectedFile().getPath());
				
		ProcessadorZumbi pN = new ProcessadorZumbi(n.getInstrucoes(), 10);
		
		Buscar buscarInstrucao;
		
		Executar executar;
		
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
