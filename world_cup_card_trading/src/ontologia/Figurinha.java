package ontologia;

import jade.content.Concept;
import jade.util.leap.List;

public class Figurinha implements Concept {
	
	private String nomeJogador;
	  
	private String numero;

	public String getNomeJogador() { return nomeJogador; }

	public void setNomeJogador(String nomeJogador) { this.nomeJogador = nomeJogador; }

	public String getNumero() { return numero; }

	public void setNumero(String numero) { this.numero = numero; }

}
