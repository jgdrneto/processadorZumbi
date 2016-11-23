package zumbi;

import modelo.Instrucao;

public class InstrucaoZumbi extends Instrucao{
	public enum CodNeander{
		MEM,
		SUB,
		XOR,
		CMP,
		NOP,
		STA,
		LDA,
		ADD,
		OR,
		AND,
		NOT,
		JMP,
		JN,
		JZ,
		HLT	
	}
	
	CodNeander codigo;
	
	public InstrucaoZumbi() {
		// TODO Auto-generated constructor stub
	}
	
	public InstrucaoZumbi(CodNeander nCodigo) {
		// TODO Auto-generated constructor stub
		codigo = nCodigo;
	}
	public InstrucaoZumbi(CodNeander nCodigo, int endereco) {
		// TODO Auto-generated constructor stub
		codigo = nCodigo;
	}
	
	public CodNeander getCodigo() {
		return codigo;
	}

	public void setCodigo(CodNeander codigo) {
		this.codigo = codigo;
	}
	
	@Override
	public String toString(){
		return "Código: " + codigo.name() + " Operando 1: " + getEndereco() + " Linha: " + getLinha();
	}
	
	
}
