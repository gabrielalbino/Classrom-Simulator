package agents;

import java.util.Map;

import agents.interfaces.ProfessorAgentInterface;
import constants.StatusAula;
import constants.Time;
import constants.Topics;
import jade.core.AID;
import jade.core.Agent;
import jade.core.ServiceException;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.core.messaging.TopicManagementHelper;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.proto.AchieveREInitiator;
import jade.lang.acl.MessageTemplate;

public class ProfessorAgent extends Agent implements ProfessorAgentInterface {
	private static final long serialVersionUID = -7358105736294185663L;
	private int tipoConteudo;
	private int conteudoPausado;

	protected AID aulaTopic, alunosTopic, topicUpdateAlunos, notasTopic, updateTopic;

	public ProfessorAgent() {
		// Register the interfaces that must be accessible by an external program
		// through the O2A interface
		registerO2AInterface(ProfessorAgentInterface.class, this);
	}

	protected void setup() {
		try {
			tipoConteudo = StatusAula.INICIANDO;

			registerAulaService();

			// registrando topic da aula
			TopicManagementHelper topicHelper = (TopicManagementHelper) getHelper(TopicManagementHelper.SERVICE_NAME);

			aulaTopic = topicHelper.createTopic(Topics.AULA);
			alunosTopic = topicHelper.createTopic(Topics.ALUNOS);
			topicUpdateAlunos = topicHelper.createTopic(Topics.UPDATE_DATA_ALUNOS);
			notasTopic = topicHelper.createTopic(Topics.COMPUTA_NOTA);
			updateTopic = topicHelper.createTopic(Topics.UPDATE_INTERFACE_REQUEST);
			topicHelper.register(alunosTopic);
			final TickerBehaviour contentUpdaterBehaviour = new TickerBehaviour(this, Time.AULA_TIME_STEP) {
				private static final long serialVersionUID = 7053736115204224490L;

				protected void onTick() {
					System.out.println("Status da Aula: " + tipoConteudo);
					if (tipoConteudo == StatusAula.RECEBENDO_PALESTRINHA
							|| tipoConteudo == StatusAula.CHAMANDO_ATENCAO) {
						tipoConteudo = conteudoPausado;
						System.out.println("Retornando conteúdo (id: " + tipoConteudo + ")");
						return;
					}
					if (tipoConteudo == StatusAula.CONTEUDO_INTERESSANTE) {
						tipoConteudo = StatusAula.CONTEUDO_IRRELEVANTE;
					} else {
						tipoConteudo = StatusAula.CONTEUDO_INTERESSANTE;
					}
					ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
					msg.addReceiver(aulaTopic);
					msg.setContent("" + tipoConteudo);
					myAgent.send(msg);
					System.out.println("Novo conteúdo (id: " + tipoConteudo + ")");
					/* Agenda a ação de atualizar notas */
					myAgent.addBehaviour(notasUpdaterBehaviour());
					myAgent.addBehaviour(classCheckerBehaviour());
				}
			};

			// Comportamento de update do tipo de conteudo
			addBehaviour(contentUpdaterBehaviour);
			addBehaviour(requestInterfaceUpdate());
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private final WakerBehaviour classCheckerBehaviour() {
		return (new WakerBehaviour(this, Time.AULA_TIME_STEP / 3) {

			private static final long serialVersionUID = 1L;

			protected void handleElapsedTimeout() {
				ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
				msg.setContent("" + tipoConteudo);
				msg.addReceiver(topicUpdateAlunos);
				myAgent.send(msg);
				System.out.println("Sending  ");
				ACLMessage alunosMsg = myAgent.receive(MessageTemplate.MatchTopic(alunosTopic));
				if (alunosMsg != null) {
					String[] content = alunosMsg.getContent().split("/");
					int alunosDispersos = Integer.parseInt(content[0]);
					int palestrinha = Integer.parseInt(content[1]);
					System.out.println("Palestrinhas " + palestrinha + " Dispersos" + alunosDispersos);
					if (palestrinha == 1) {
						if (tipoConteudo == StatusAula.CONTEUDO_INTERESSANTE
								|| tipoConteudo == StatusAula.CONTEUDO_INTERESSANTE) {
							conteudoPausado = tipoConteudo;
						}
						tipoConteudo = StatusAula.RECEBENDO_PALESTRINHA;
						if (alunosDispersos > 5) {
							if (tipoConteudo == StatusAula.CONTEUDO_INTERESSANTE
									|| tipoConteudo == StatusAula.CONTEUDO_INTERESSANTE) {
								conteudoPausado = tipoConteudo;
							}
							tipoConteudo = StatusAula.CHAMANDO_ATENCAO;
						}
					} else {
						System.out.println("Null no professor ");
						block();
					}
				}
			}
		});
	}

	private final WakerBehaviour notasUpdaterBehaviour() {
		return (new WakerBehaviour(this, Time.AULA_TIME_STEP / 2) {

			private static final long serialVersionUID = 1L;

			protected void handleElapsedTimeout() {
				ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
				msg.setContent("" + tipoConteudo);
				msg.addReceiver(notasTopic);
				myAgent.send(msg);
			}
		});
	}

	// Retorna um mapa com o nome de cada aluno como chave e a nota e o status como
	// valores.
	private TickerBehaviour requestInterfaceUpdate() {
		return (new TickerBehaviour(this, Time.AULA_TIME_STEP / 10) {
			private static final long serialVersionUID = 7053736115204224490L;

			protected void onTick() {
				ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
				msg.addReceiver(updateTopic);
				myAgent.send(msg);
			}
		});
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
		} catch (Exception e) {
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
