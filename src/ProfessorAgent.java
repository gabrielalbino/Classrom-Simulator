import jade.core.Agent;

public class ProfessorAgent extends Agent {
	protected void setup() {
		System.out.println("Alo Mundo! ");
		System.out.println("Meu nome: " + getLocalName());
	}
}
