package agents;
import jade.core.AID;
import jade.core.Agent;
import jade.core.ServiceException;
import jade.core.behaviours.TickerBehaviour;
import jade.core.messaging.TopicManagementHelper;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import util.StatusAula;

public class ProfessorAgent extends Agent  implements ProfessorAgentInterface {
	private static final long serialVersionUID = -7358105736294185663L;
	private int tipoConteudo;

	public ProfessorAgent() {
		// Register the interfaces that must be accessible by an external program through the O2A interface
		registerO2AInterface(ProfessorAgentInterface.class, this);
	}
	
	protected void setup() {
		try {
			tipoConteudo = StatusAula.INICIANDO;
			
			registerAulaService();
	
			//registrando topic da aula
			TopicManagementHelper topicHelper = (TopicManagementHelper) getHelper(TopicManagementHelper.SERVICE_NAME);
			final AID topic = topicHelper.createTopic("AULA");
			
			//Comportamento de update do tipo de conteudo
		    addBehaviour(new TickerBehaviour(this, 10000) {
		        private static final long serialVersionUID = 7053736115204224490L;
	
				protected void onTick() {
					System.out.println("onTick");
					if(tipoConteudo == StatusAula.CONTEUDO_INTERESSANTE) {
						tipoConteudo = StatusAula.CONTEUDO_IRRELEVANTE;
					}
					else {
						tipoConteudo = StatusAula.CONTEUDO_INTERESSANTE;
					}
					ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
					msg.addReceiver(topic);
					msg.setContent("" + tipoConteudo);
					myAgent.send(msg);
		        } 
		      });
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private void registerAulaService() {

		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setName("sv-professor");
		sd.setType("aula-forecast");
		
		dfd.addServices(sd);
		
		try {
			DFService.register(this, dfd);
		} catch (FIPAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private DFAgentDescription[] getAlunosFromYellowPages() {
		try {
	  		// Build the description used as template for the search
	  		DFAgentDescription template = new DFAgentDescription();
	  		ServiceDescription templateSd = new ServiceDescription();
	  		templateSd.setType("weather-forecast");
	  		template.addServices(templateSd);
	  		
	  		DFAgentDescription[] results = DFService.search(this, template);
	  		return results;
	  	}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int getTipoConteudo() {
		// TODO Auto-generated method stub
		return tipoConteudo;
	}
	
}
