package agents;
import jade.core.Agent;

public class AlunoAgent extends Agent implements o2a.AlunoAgentInterface {
	private int status, nota;
	
	protected void setup() {
		System.out.println("Alo Mundo! ");
		System.out.println("Meu nome: " + getLocalName());
		status = 0;
		nota = 0;
	}

	@Override
	public int getStatus() {
		return status;
	}

	@Override
	public int getNota() {
		return nota;
	}
}