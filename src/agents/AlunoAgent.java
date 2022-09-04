package agents;
import java.util.concurrent.CyclicBarrier;

import agents.interfaces.AlunoAgentInterface;
import constants.StatusAlunos;
import constants.StatusAula;
import constants.Topics;
import jade.core.AID;
import jade.core.Agent;
import jade.core.ServiceException;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.messaging.TopicManagementHelper;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.AchieveREInitiator;

public abstract class AlunoAgent extends Agent implements AlunoAgentInterface {
	private static final long serialVersionUID = 1L;
	/* Atributos do aluno
	* Status: Estado atual do aluno, um dos valores de "
	*
	*/
	protected int status;
	protected int nota;
	protected AID topicAula;
	protected AID topicNotas;
	private AID topicUpdateRequest;
	private AID topicUpdateResponse;
	public AlunoAgent() {
		/* Registra uma interface que vai permitir o acesso externo do agente por meio do O2A.
		 * Caso deseje criar novos métodos para expor, deve-se editar o arquivo "AlunoAgentInterface" e implementar os métodos novos.
		 * Para utilizar os métodos em uma classe externa, use alunoAgentObject.getO2AInterface(AlunoAgentInterface.class);
		 */
		registerO2AInterface(AlunoAgentInterface.class, this);
	}

	protected void setup() {
		System.out.println(getLocalName() + " entrou na sala!");
		status = StatusAlunos.AGUARDANDO_INICIO;
		nota = 0;
		
		registerAlunoService();

		// Cria e registra o novo tópico que será utilizado pelo agente para receber as atualizações
		TopicManagementHelper topicHelper;
		try {
			topicHelper = (TopicManagementHelper) getHelper(TopicManagementHelper.SERVICE_NAME);
			topicAula = topicHelper.createTopic(Topics.AULA);
			topicNotas = topicHelper.createTopic(Topics.COMPUTA_NOTA);
			topicUpdateRequest = topicHelper.createTopic(Topics.UPDATE_DATA_REQUEST);
			topicUpdateResponse = topicHelper.createTopic(Topics.UPDATE_DATA_RESPONSE);
			topicHelper.register(topicAula);
			topicHelper.register(topicNotas);
			topicHelper.register(topicUpdateRequest);
			topicHelper.register(topicUpdateResponse);
		} catch (ServiceException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}
	
	protected void addAlunoBehaviour(Behaviour updateBehaviour) {
		/* Adiciona o comportamento de mudar seu proprio status durante as mudanças de conteudo
		 * Ex: Implementa o que deve ser feito quando o conteúdo da aula for importante.
		 * */
		addBehaviour(updateBehaviour);
		/* Adiciona o comportamento de atualizar sua propria nota de acordo com o status
		 * */
		addBehaviour(getComputaNotasBehaviour());
		/* Adiciona o comportamento de responder requisições de atualização com sua nota e status
		 * */
		addBehaviour(getInfoBehaviour());
	}

	/*	Comportamento de atualizar a sua nota de acordo com seu proprio status:
	 * Se o conteudo for interessante, o aluno ganha ponto se tiver prestando atenção
	 * Se o conteúdo não for, o aluno ganha ponto se não estiver prestando atenção.
	 * 
	 * */
	private CyclicBehaviour getComputaNotasBehaviour() {
		return (new CyclicBehaviour(this) {
			private static final long serialVersionUID = 1L;

			public void action() {
				ACLMessage msg = myAgent.receive(MessageTemplate.MatchTopic(topicNotas));
				if (msg != null) {
					int statusAula = Integer.parseInt(msg.getContent());
					
					switch(statusAula) {
					case StatusAula.CONTEUDO_INTERESSANTE:
						switch(status) {
						case StatusAlunos.PRESTANDO_ATENCAO:
							nota += 1;
							break;
						}
						break;
					case StatusAula.CONTEUDO_IRRELEVANTE:
						switch (status) {
						case StatusAlunos.PRESTANDO_ATENCAO:
							break;
						default:
							nota += 1;
						}
					}
					
				}
				else {
					block();
				}
            } 
		});
	}

	/*	Comportamento que responde informações de nota e status do aluno quando requisitado
	 * */
	private CyclicBehaviour getInfoBehaviour() {
		return (new CyclicBehaviour(this) {
			private static final long serialVersionUID = 1L;

			public void action() {
				ACLMessage msg = myAgent.receive(MessageTemplate.MatchTopic(topicUpdateRequest));
				if (msg != null) {
					ACLMessage msg1 = new ACLMessage(ACLMessage.INFORM);
					msg1.addReceiver(topicUpdateResponse);
					msg1.setContent(getAlunoNome() + "/" + getAlunoStatus() + "/" + getNota());
					myAgent.send(msg1);
					//System.out.println(msg1);

				}
				else {
					block();
				}
            } 
		});
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
	
	/* Retorna uma das ações de acordo com a probabilidade... 
	 * Ex: getActionByChance(0.3, Status.status1, Status.status2) = 30% de chance de ser estado1, 70% de chance de ser estado 2.
	 * */
	protected int getActionByChance(double chance, int action1, int action2) {
		return Math.random() < chance ? action1 : action2; 
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