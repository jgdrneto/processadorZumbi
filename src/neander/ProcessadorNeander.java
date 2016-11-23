package neander;

import java.util.List;

import modelo.Processador;
import neander.InstrucaoNeander.CodNeander;

public class ProcessadorNeander extends Processador{
	
	public boolean negativo=false;
	public boolean zero=false;
	public boolean parar=false;
	public boolean permissao = true;
	
	public List<InstrucaoNeander> instrucoes;
	public InstrucaoNeander i;
	public int CP;	
	
	public ProcessadorNeander(List<InstrucaoNeander> nInstrucoes, int memoria) {
		
		super(memoria+1, 1);

		instrucoes = resolverConflitosDados(nInstrucoes);
		
		CP=0;
	}
	
	public synchronized void  Busca(){
		while (permissao == false) {
	        try {
	            //wait for Producer to put value
	            wait();
	        } catch (InterruptedException e) { }
	    }
		
		permissao=false;
		
		i = instrucoes.get(CP++);
		
		notifyAll();
		
	}
	
	private List<InstrucaoNeander> resolverConflitosDados(List<InstrucaoNeander> nInstrucoes) {
		
		for(int i=0;i<nInstrucoes.size()-1;i++){
			
			if(nInstrucoes.get(i).getEndereco()!=0 && nInstrucoes.get(i).getEndereco()==nInstrucoes.get(i+1).getEndereco()){
				nInstrucoes.add(i+1, new InstrucaoNeander(CodNeander.NOP));
			}
			
			if(nInstrucoes.get(i).getCodigo()==CodNeander.JMP||
			   nInstrucoes.get(i).getCodigo()==CodNeander.JN ||
			   nInstrucoes.get(i).getCodigo()==CodNeander.JZ){
			   
			   nInstrucoes.add(i+1, new InstrucaoNeander(CodNeander.NOP));
			}
		}
		
		for(InstrucaoNeander i : nInstrucoes){
			System.out.println(i.toString());
		}
		
		return nInstrucoes;
	}

	public List<InstrucaoNeander> getInstrucoes() {
		return instrucoes;
	}

	public void setInstrucoes(List<InstrucaoNeander> instrucoes) {
		this.instrucoes = instrucoes;
	}
	
public synchronized void interpretarInstrucao(){
		
		while (this.permissao == true) {
	        try {
	            //wait for Consumer to get value
	            wait();
	        } catch (InterruptedException e) { }
	    }
		
		if(i!=null){
		
			switch(this.i.getCodigo()){
				case MEM:
					this.getMemoria().set(this.i.getLinha(),this.i.getEndereco());
				break;
				case NOP :
		
				break;
				case STA:
					this.getMemoria().set(this.i.getEndereco(),this.getRegistradores().get(0));
				break;
				case LDA:
					this.getRegistradores().set(0, this.getMemoria().get(this.i.getEndereco()));
		
					verificarRegistradoresDeEstado();
				break;
				case ADD:
					this.getRegistradores().set(0, this.getRegistradores().get(0)+this.getMemoria().get(this.i.getEndereco()));
		
					verificarRegistradoresDeEstado();
		
				break;
				case OR:
					this.getRegistradores().set(0, this.getRegistradores().get(0) | this.getMemoria().get(this.i.getEndereco()));
		
					verificarRegistradoresDeEstado();
				break;
				case AND:
					this.getRegistradores().set(0, this.getRegistradores().get(0) & this.getMemoria().get(this.i.getEndereco()));
		
					verificarRegistradoresDeEstado();
				break;
				case NOT:
					this.getRegistradores().set(0, ~this.getRegistradores().get(0));
		
					verificarRegistradoresDeEstado();
				break;
				case JMP:
					this.CP = this.i.getEndereco();
				break;
				case JN:
					if(this.negativo){
						
						decodificarEndereco();
					}
				break;
				case JZ:
					if(this.zero){
						decodificarEndereco();
					}
				break;
				case HLT:
					this.parar=true;
				break;
				default:
					System.out.println("Instrução não encontrada");
				break;
			}
		}
		
		this.permissao=true;
		
		notifyAll();
	}

	private synchronized void decodificarEndereco() {
		
		boolean entrou=false;
		
		for(int j=0;j<this.instrucoes.size();j++){
			if(this.instrucoes.get(j).getLinha()==this.CP){
				this.CP=j;
				entrou=true;
			}
		}
		
		if(entrou==false){
			System.out.println("Houve erro no endereçamento de pulo, olhe a linha " + this.i.getLinha());
			System.exit(0);
		}
	}

	private synchronized void verificarRegistradoresDeEstado(){
		if(this.getRegistradores().get(0)==0){
			this.zero=true;
		}else{
			this.zero=false;
		}
		if(this.getRegistradores().get(0)<0){
			this.negativo= true;
		}else{
			this.negativo=false;
		}
	}
	
	
	
	@Override
	public double cpi() {
		double cont=0;
		int instMem=0;
		for(InstrucaoNeander i : this.instrucoes){
			
			//System.out.println("Instrução:" + i.getCodigo().name());
			
			if(!i.getCodigo().equals(CodNeander.MEM)){
				cont+=quantCicloDaInstrucao(i);
			}else{
				instMem++;
			}
		}
		
		return cont/(this.instrucoes.size()-instMem);
	}

	private int quantCicloDaInstrucao(InstrucaoNeander i) {
		switch(i.getCodigo()){
			case STA:
			case LDA:
			case ADD:
			case OR:
			case AND:
				return 3;
			default:
				return 1;	
		}
	}
}