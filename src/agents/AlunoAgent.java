package agents;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.messaging.TopicManagementHelper;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import util.StatusAlunos;
import util.StatusAula;

public class AlunoAgent extends Agent implements AlunoAgentInterface {
	private static final long serialVersionUID = 1L;
	private int status, nota;

	public AlunoAgent() {
		// Register the interfaces that must be accessible by an external program through the O2A interface
		registerO2AInterface(AlunoAgentInterface.class, this);
	}

	protected void setup() {
		System.out.println(getLocalName() + " entrou na sala!");
		status = StatusAlunos.AGUARDANDO_INICIO;
		nota = 0;
		registerAlunoService();
		

		try {
			// Register to messages about topic "JADE"
			TopicManagementHelper topicHelper = (TopicManagementHelper) getHelper(TopicManagementHelper.SERVICE_NAME);
			final AID topic = topicHelper.createTopic("AULA");
			topicHelper.register(topic);
			
			// Add a behaviour collecting messages about topic "JADE"
			addBehaviour(new CyclicBehaviour(this) {
				private static final long serialVersionUID = 1L;

				public void action() {
					ACLMessage msg = myAgent.receive(MessageTemplate.MatchTopic(topic));
					if (msg != null) {
						int statusAula = Integer.parseInt(msg.getContent());
						if(statusAula == StatusAula.CONTEUDO_INTERESSANTE) {
							status = StatusAlunos.PRESTANDO_ATENCAO;
						}
						else if(statusAula == StatusAula.CONTEUDO_IRRELEVANTE) {
							status = StatusAlunos.VIAJANDO_NA_MAIONESE;
						}
					}
					else {
						block();
					}
				}
			} );
		}
		catch (Exception e) {
			System.err.println("Agent "+getLocalName()+": ERROR registering to topic \"JADE\"");
			e.printStackTrace();
		}
		
	}
	

	private void registerAlunoService() {

		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setName("sv-aluno");
		sd.setType("aluno-forecast");
		
		dfd.addServices(sd);
		
		try {
			DFService.register(this, dfd);
		} catch (FIPAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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