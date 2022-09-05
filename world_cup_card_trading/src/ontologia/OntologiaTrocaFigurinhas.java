package ontologia;
import jade.content.onto.*;
import jade.content.schema.*;

public class OntologiaTrocaFigurinhas extends Ontology implements VocabularioTrocaFigurinhas {
	
	  public static final String NOME_ONTOLOGIA = "Ontologia-troca-figurinhas";

	  private static Ontology theInstance = new OntologiaTrocaFigurinhas();

	  public static Ontology getInstance() {
	    return theInstance;
	  }

	  private OntologiaTrocaFigurinhas() {
	    super(NOME_ONTOLOGIA, BasicOntology.getInstance());
	    try {
	      add(new ConceptSchema(FIGURINHA), Figurinha.class);
	      add(new AgentActionSchema(TROCA), Sell.class);

	      ConceptSchema cs = (ConceptSchema) getSchema(FIGURINHA);
	      cs.add(NOME_JOGADOR_FIGURINHA, (PrimitiveSchema) getSchema(BasicOntology.STRING));
	      cs.add(NUMERO_FIGURINHA, (PrimitiveSchema) getSchema(BasicOntology.STRING));

	      AgentActionSchema as = (AgentActionSchema) getSchema(TROCA);
	      as.add(TROCA_FIGURINHAS, (ConceptSchema) getSchema(FIGURINHA));
	    }
	    catch (OntologyException oe) {
	      oe.printStackTrace();
	    }
	  }

}
