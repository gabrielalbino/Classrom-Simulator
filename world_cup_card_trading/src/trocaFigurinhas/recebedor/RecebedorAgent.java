package trocaFigurinhas.recebedor;

import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;
import jade.domain.*;
import jade.domain.FIPAAgentManagement.*;
import jade.proto.ContractNetInitiator;
import ontologia.*;

import java.util.Vector;
import java.util.Date;
import java.util.Objects;

import jade.content.*; 
import jade.content.lang.Codec;
import jade.content.lang.Codec.CodecException;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.*;
import jade.content.onto.basic.*; 

public class RecebedorAgent extends Agent {
	
  private Vector sellerAgents = new Vector();

  private RecebedorAgentGui modalTroca;

  /** The following parts, where the SLCodec and BookTradingOntology are 
    * registered, are explained in section 5.1.3.4 page 88 of the book.
   **/ 
  private Codec codec = new SLCodec();
  private Ontology ontology = OntologiaTrocaFigurinhas.getInstance();

  protected void setup() {
 
    /** The following piece of code is explained in section 5.6.1 pag. 113 of the book.
     * It processes notifications from the external buying system (other modifications 
     * also need to be introduced to handle the successful purchase or deadline expiration).
    **/
    // Enable O2A Communication
    setEnabledO2ACommunication(true, 0);
    // Add the behaviour serving notifications from the external system
    addBehaviour(new CyclicBehaviour(this) {
      public void action() {
    	InformacoesFigurinha fig = (InformacoesFigurinha) myAgent.getO2AObject();
        if (Objects.nonNull(fig)) {
          purchase(fig.getNumeroFigurinha(), fig.getNomeJogador(), fig.getRaridadeMinima());
        }
        else {
          block();
        }
      }
    });


    // Printout a welcome message
    System.out.println(new StringBuilder(getAID().getName()).append(" esta pronto para comecar a trocar"));
    
    getContentManager().registerLanguage(codec);
    getContentManager().registerOntology(ontology);

    // Get names of seller agents as arguments
    Object[] args = getArguments();
    if (Objects.nonNull(args) && args.length > 0) {
      for (int i = 0; i < args.length; ++i) {
        AID seller = new AID((String) args[i], AID.ISLOCALNAME);
        //add seller to the vector list
        sellerAgents.addElement(seller);
      }
    }

    // Show the GUI to interact with the user
    modalTroca = new RecebedorAgentGui();
    modalTroca.setAgent(this);
    modalTroca.show();

    /** This piece of code, to search services with the DF, is explained
     * in the book in section 4.4.3, page 74
    **/
    // Update the list of seller agents every minute
    addBehaviour(new TickerBehaviour(this, 5000) {
      protected void onTick() {
        // Update the list of seller agents
        DFAgentDescription template = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType("Oferecendo-figurinhas");
        template.addServices(sd);
        try {
          DFAgentDescription[] result = DFService.search(myAgent, template);
          sellerAgents.clear();
          for (int i = 0; i < result.length; ++i) {
            sellerAgents.addElement(result[i].getName());
          }
        }
        catch (FIPAException fe) {
          fe.printStackTrace();
        }
      }
    });
  }

  protected void takeDown() {
    if (Objects.nonNull(modalTroca)) {
      modalTroca.dispose();
    }
    System.out.println(new StringBuilder(getAID().getName()).append(" n."));
  }

  /**
   * This method is called by the GUI when the user inserts a new
   * book to buy
   * @param title The title of the book to buy
   * @param maxPrice The maximum acceptable price to buy the book
   * @param deadline The deadline by which to buy the book
  **/
  public void purchase(int numeroFigurinha, String nomeJogador, int raridadeMinima) {
    addBehaviour(new PurchaseManager(this, numeroFigurinha, nomeJogador, raridadeMinima));
  }


  /**
   * Section 4.2.4, Page 62
   **/
  private class PurchaseManager extends TickerBehaviour {
	  
    private int numeroFigurinha;
    
    private int raridadeMinima;
    
    private String nomeJogador;

    private PurchaseManager(Agent a, int numeroFigurinha, String nomeJogador, int raridadeMinima) {
      super(a, 3000); // tick every second 
      this.nomeJogador = nomeJogador;
      this.numeroFigurinha = numeroFigurinha;
      this.raridadeMinima = raridadeMinima;
    }

    public void onTick() {
	    myAgent.addBehaviour(new BookNegotiator(nomeJogador, numeroFigurinha, raridadeMinima--, this));
    }
  }



  public ACLMessage cfp = new ACLMessage(ACLMessage.CFP);

  /**
   * Section 5.4.2 of the book, page 104
   * Inner class BookNegotiator.
   * This is the behaviour reimplemented by using the ContractNetInitiator 
  **/
  public class BookNegotiator extends ContractNetInitiator {
    
	private int numeroFigurinha;
	
	private String nomeJogador;
        
	private int raridadeMinima;
	
    private PurchaseManager manager;

    public BookNegotiator(String nomeJogador, int numeroFigurinha, int raridadeMinima, PurchaseManager m) {
    	
      super(RecebedorAgent.this, cfp);
      
      this.numeroFigurinha = numeroFigurinha;
      this.nomeJogador = nomeJogador;
      this.raridadeMinima = raridadeMinima;
      
      this.manager = m;
      
      Figurinha figurinha = new Figurinha();
      figurinha.setNomeJogador(nomeJogador);
      figurinha.setNumero(Integer.valueOf(raridadeMinima).toString());
      
      Sell sellAction = new Sell();
      sellAction.setItem(figurinha); 
      Action act = new Action(RecebedorAgent.this.getAID(), sellAction);
      
      try {
    	  cfp.setLanguage(codec.getName());
    	  cfp.setOntology(ontology.getName());
    	  RecebedorAgent.this.getContentManager().fillContent(cfp, act);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    protected Vector prepareCfps(ACLMessage cfp) {
      cfp.clearAllReceiver();
      for (int i = 0; i < sellerAgents.size(); ++i) {
        cfp.addReceiver((AID) sellerAgents.get(i));
      }
      Vector v = new Vector();
      v.add(cfp);
      if (!sellerAgents.isEmpty())
         modalTroca.notifyUser(new StringBuilder("Enviando proposta de figurinha ").append(this.numeroFigurinha).append(" para").append(sellerAgents.size()).append(" possiveis interessados").toString());
      return v;
    }

    protected void handleAllResponses(Vector responses, Vector acceptances) {
      ACLMessage bestOffer = null;
      int bestPrice = -1;
      for (int i = 0; i < responses.size(); i++) {
        ACLMessage rsp = (ACLMessage) responses.get(i);
        
        if (rsp.getPerformative() == ACLMessage.PROPOSE) {
          try {
            ContentElementList cel = (ContentElementList) myAgent.getContentManager().extractContent(rsp);
            int price = ((CustoFigurinha)cel.get(1)).getRaridade();  
            modalTroca.notifyUser("Received Proposal at " + price + " when maximum acceptable price was " + raridadeMinima);
            if (Objects.isNull(bestOffer) || price < bestPrice) {
              bestOffer = rsp;
              bestPrice = price;
            }
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }

      for (int i = 0; i < responses.size(); i++) {
        ACLMessage rsp = (ACLMessage) responses.get(i);
        ACLMessage accept = rsp.createReply();
        if (rsp == bestOffer) {
          boolean acceptedProposal = (bestPrice <= raridadeMinima);
          accept.setPerformative(acceptedProposal ? ACLMessage.ACCEPT_PROPOSAL : ACLMessage.REJECT_PROPOSAL);
          accept.setContent(Integer.valueOf(numeroFigurinha).toString());
          modalTroca.notifyUser(acceptedProposal ? "sent Accept Proposal" : "sent Reject Proposal");
        } else {
          accept.setPerformative(ACLMessage.REJECT_PROPOSAL);
        }
        acceptances.add(accept);
      }
    }

    protected void handleInform(ACLMessage inform) {
      int price = Integer.parseInt(inform.getContent());
      modalTroca.notifyUser(new StringBuilder("Figurinha comprada com sucesso").toString());
      manager.stop();
    }
    
  } 
}


