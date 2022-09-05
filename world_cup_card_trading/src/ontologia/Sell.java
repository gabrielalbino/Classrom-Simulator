package ontologia;

import jade.content.AgentAction;
import jade.core.AID;

public class Sell implements AgentAction {
	
	private Figurinha figurinha;
	
	public Figurinha getItem() { return figurinha; }
	
	public void setItem(Figurinha figurinha) { this.figurinha = figurinha; }
}
