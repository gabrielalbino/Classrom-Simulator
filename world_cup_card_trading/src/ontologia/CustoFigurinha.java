package ontologia;
import jade.content.Predicate;
import jade.core.AID;

public class CustoFigurinha implements Predicate {
	
	private Figurinha figurina;
	 
	private int raridade;

	public Figurinha getFigurina() { return figurina; }

	public void setFigurina(Figurinha figurina) { this.figurina = figurina; }

	public int getRaridade() { return raridade; }

	public void setRaridade(int raridade) { this.raridade = raridade; }
}
