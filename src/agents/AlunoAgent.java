package agents;
import jade.core.Agent;

public class AlunoAgent extends Agent implements AlunoAgentInterface {
	private int status, nota;

	public AlunoAgent() {
		// Register the interfaces that must be accessible by an external program through the O2A interface
		registerO2AInterface(AlunoAgentInterface.class, this);
	}

	protected void setup() {
		System.out.println(getLocalName() + " entrou na sala!");
		status = 0;
		nota = 0;
	}

	@Override
	public int getNota() {
		return nota;
	}

	@Override
	public int getAlunoStatus() {
		return status;
	}

	@Override
	public String getAlunoNome() {
		return getLocalName();
	}
	
}