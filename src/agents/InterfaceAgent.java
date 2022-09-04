package agents;

import java.util.HashMap;
import java.util.Map;

import agents.interfaces.AlunoAgentInterface;
import agents.interfaces.AlunoInfo;
import constants.StatusAlunos;
import constants.StatusAula;
import constants.Time;
import constants.Topics;
import jade.core.AID;
import jade.core.Agent;
import jade.core.ServiceException;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.core.messaging.TopicManagementHelper;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.AchieveREInitiator;
import jade.wrapper.StaleProxyException;

public class InterfaceAgent extends Agent {

	private static final long serialVersionUID = 1L;
	private AID topicUpdate;
	private AID topicUpdateRequest;
	private AID topicUpdateResponse;
	Map<String, AlunoInfo> statusAlunos;
	
	protected void setup() {
		statusAlunos = new HashMap<String, AlunoInfo>();
		// Cria e registra o novo tópico que será utilizado pelo agente para receber as atualizações
		TopicManagementHelper topicHelper;
		try {
			topicHelper = (TopicManagementHelper) getHelper(TopicManagementHelper.SERVICE_NAME);
			topicUpdate = topicHelper.createTopic(Topics.UPDATE_INTERFACE_REQUEST);
			topicUpdateRequest = topicHelper.createTopic(Topics.UPDATE_DATA_REQUEST);
			topicUpdateResponse = topicHelper.createTopic(Topics.UPDATE_DATA_RESPONSE);
			topicHelper.register(topicUpdate);
			topicHelper.register(topicUpdateResponse);
			
		} catch (ServiceException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		addBehaviour(getUpdateInterfaceBehaviour());
		addBehaviour(getUpdateDataBehaviour());
		addBehaviour(getRequestAlunosStatusBehaviour());
	}
	
	/*	Comportamento para atualizar a interface
	 * 
	 * */
	private CyclicBehaviour getUpdateInterfaceBehaviour() {
		return (new CyclicBehaviour(this) {
			private static final long serialVersionUID = 1L;

			public void action() {
				ACLMessage msg = myAgent.receive(MessageTemplate.MatchTopic(topicUpdate));
				if (msg != null) {
					System.out.println(statusAlunos.toString());
				}
				else {
					block();
				}
            } 
		});
	}
	

	/*	Comportamento para receber os dados dos alunos.
	 * 
	 * */
	private CyclicBehaviour getUpdateDataBehaviour() {
		return (new CyclicBehaviour(this) {
			private static final long serialVersionUID = 1L;
			
			public void action() {
				ACLMessage msg = myAgent.receive(MessageTemplate.MatchTopic(topicUpdateResponse));
				if (msg != null) {
					String[] content = msg.getContent().split("/");
					String nome = content[0];
					int status = Integer.parseInt(content[1]);
					int nota = Integer.parseInt(content[2]);
					AlunoInfo info = new AlunoInfo(status, nota);
					if(statusAlunos.get(nome) != null) {
						statusAlunos.replace(nome, info);
					}
					else {
						statusAlunos.put(nome, info);
					}
				}
				else {
					block();
				}
				
            } 
		});
	}
	
	// Retorna um mapa com o nome de cada aluno como chave e a nota e o status como valores.
	private TickerBehaviour getRequestAlunosStatusBehaviour(){
		return (new TickerBehaviour(this, Time.AULA_TIME_STEP/10) {
	        private static final long serialVersionUID = 7053736115204224490L;

			protected void onTick() {
				ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
				msg.addReceiver(topicUpdateRequest);
				myAgent.send(msg);
	        } 
		});
	}
}
