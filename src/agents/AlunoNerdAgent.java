package agents;
import constants.StatusAlunos;
import constants.StatusAula;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.messaging.TopicManagementHelper;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class AlunoNerdAgent extends AlunoAgent {
	private static final long serialVersionUID = 1L;
	public AlunoNerdAgent() {
		super();
	}

	protected void setup() {
		super.setup();
		
		Behaviour handleContentUpdate = new CyclicBehaviour(this) {
			private static final long serialVersionUID = 1L;

			/* Recebe as alterações de conteúdo e seta seu status de acordo.
			 * Pode-se utilizar aleatórios (Math.Random()) para calcular as probabilidades de cada classe.
			 * */
			public void action() {
				ACLMessage msg = myAgent.receive(MessageTemplate.MatchTopic(topicAula));
				if (msg != null) {
					int statusAula = Integer.parseInt(msg.getContent());
					
					switch(statusAula) {
					case StatusAula.CONTEUDO_INTERESSANTE:
						status = StatusAlunos.PRESTANDO_ATENCAO;
						break;
					case StatusAula.CONTEUDO_IRRELEVANTE:
						status = getActionByChance(0.05, StatusAlunos.FORA_DA_SALA, StatusAlunos.PRESTANDO_ATENCAO);
					}
				}
				else {
					block();
				}
			}
		};
		
		addAlunoBehaviour(handleContentUpdate);
		
	}
	

	/*
	 * Adiciona os alunos como prestadores do serviço "sv-aluno" nas Páginas amarelas. Isso é util para acesso em outros agentes.
	 * */
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

	/* Métodos expostos pelo O2A */
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