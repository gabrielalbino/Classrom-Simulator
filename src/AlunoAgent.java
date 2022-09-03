import jade.core.Agent;

public class AlunoAgent extends Agent {
	protected void setup() {
		System.out.println("Alo Mundo! ");
		System.out.println("Meu nome: " + getLocalName());
	}
}
