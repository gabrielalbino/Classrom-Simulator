package agents;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.Timer;


import agents.interfaces.AlunoInfo;
import constants.StatusAlunos;
import constants.Time;
import constants.Topics;
import jade.core.AID;
import jade.core.Agent;
import jade.core.ServiceException;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.core.messaging.TopicManagementHelper;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class InterfaceAgent extends Agent {

	private static final long serialVersionUID = 1L;
	private AID topicUpdate;
	private AID topicUpdateRequest;
	private AID topicUpdateResponse;
	public JFrame frame;
	public int teste = 0;
	private AID topicUpdateAlunos;
	private AID topicAlunos;
	Map<String, AlunoInfo> statusAlunos;
	protected void setup() {
		statusAlunos = new HashMap<String, AlunoInfo>();
		// Cria e registra o novo tópico que será utilizado pelo agente para receber as
		// atualizações
		TopicManagementHelper topicHelper;
		try {
			topicHelper = (TopicManagementHelper) getHelper(TopicManagementHelper.SERVICE_NAME);
			topicUpdate = topicHelper.createTopic(Topics.UPDATE_INTERFACE_REQUEST);
			topicUpdateRequest = topicHelper.createTopic(Topics.UPDATE_DATA_REQUEST);
			topicUpdateResponse = topicHelper.createTopic(Topics.UPDATE_DATA_RESPONSE);
			topicUpdateAlunos = topicHelper.createTopic(Topics.UPDATE_DATA_ALUNOS);
			topicAlunos = topicHelper.createTopic(Topics.ALUNOS);
			topicHelper.register(topicUpdate);
			topicHelper.register(topicUpdateResponse);
			topicHelper.register(topicUpdateAlunos);
			topicHelper.register(topicAlunos);

		} catch (ServiceException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		/*
        frame = new JFrame("Simulador de sala de aula");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel labelProfessor = new JLabel("Aula: " + "Teste");
        frame.getContentPane().add(labelProfessor);
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
        */
		
		Tela salaDeAula = new Tela();
		salaDeAula.frame.setVisible(true);
		
		
		/*
	    int delay = 1000; //milliseconds
		
	      ActionListener taskPerformer = new ActionListener() {
	          public void actionPerformed(ActionEvent evt) {
	              if (teste > 4) teste = 0;

	              teste++;
	          }
	      };
	      new Timer(delay, taskPerformer).start();
	      /*
	      
		/*
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					System.out.println("NERD 😀");
					Tela salaDeAula = new Tela();
					salaDeAula.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		*/

		
		addBehaviour(getUpdateInterfaceBehaviour(salaDeAula));
		addBehaviour(getUpdateDataBehaviour());
		addBehaviour(getRequestInfoBehaviour());
		

	}

	/*
	 * Comportamento para atualizar a interface
	 * 
	 */
	private TickerBehaviour getUpdateInterfaceBehaviour(Tela sala) {
		return (new TickerBehaviour(this, 1000) {
			private static final long serialVersionUID = 1L;

			public void onTick() {
				ACLMessage msg = myAgent.receive(MessageTemplate.MatchTopic(topicUpdate));
				if (msg != null) {
					// System.out.println(statusAlunos.toString());
					// sala.changeProfessorStatus(teste);
					for(String key : statusAlunos.keySet()){
							if( !key.equals("professor") ) {
						  // System.out.println(key + "----" + statusAlunos.get(key));
						  sala.changeStudentName(key, statusAlunos.get(key).nota);
						  sala.changeStudentStatus(key, statusAlunos.get(key).status);
						  sala.frame.revalidate();
						  sala.frame.repaint();

							} else {
								// System.out.println(key + "----" + statusAlunos.get(key));
								sala.changeProfessorStatus(statusAlunos.get(key).status);
							}
					}
					

				}
			}
		});
	}

	/*
	 * Comportamento para receber os dados dos alunos.
	 * 
	 */
	private CyclicBehaviour getUpdateDataBehaviour() {
		return (new CyclicBehaviour(this) {
			private static final long serialVersionUID = 1L;
			public void action() {
				ACLMessage msg = myAgent.receive(MessageTemplate.MatchTopic(topicUpdateResponse));
				if (msg != null) {
					String[] content = msg.getContent().split("/");
					String nome = content[0];
					System.out.println(nome);
					int status = Integer.parseInt(content[1]);
					int nota = Integer.parseInt(content[2]);
					AlunoInfo info = new AlunoInfo(status, nota);
					if (statusAlunos.get(nome) != null) {
						statusAlunos.replace(nome, info);
					} else {
						statusAlunos.put(nome, info);
					}
				} else {
					block();
				}

				ACLMessage reqAlunos = myAgent.receive(MessageTemplate.MatchTopic(topicUpdateAlunos));
				if (reqAlunos != null) {
					System.out.println("MSG no interface " + reqAlunos.getContent());
					int alunosDispersos = 0;
					int palestrinha = 0; // 0 - Sem palestrinha / 1 - Com palestrinha
					int perguntando = 0; // 0 - Ninguém perguntando / 1 - Aluno perguntando
					int status;

					for (Map.Entry<String, AlunoInfo> entry : statusAlunos.entrySet()) {
						status = entry.getValue().status;
						if (status == StatusAlunos.DANDO_PALESTRINHA) {
							palestrinha = 1;
						}
						if (status == StatusAlunos.PERGUNTANDO) {
							perguntando = 1;
						}
						if (status == StatusAlunos.CONVERSANDO || status == StatusAlunos.VIAJANDO_NA_MAIONESE) {
							alunosDispersos += 1;
						}
					}
					ACLMessage msgAlunos = new ACLMessage(ACLMessage.INFORM);
					System.out.println("Dispersos: " + alunosDispersos + " Palestrinha: " + palestrinha
							+ " Perguntando: " + perguntando);
					msgAlunos.setContent(alunosDispersos + "/" + palestrinha + "/" + perguntando);
					msgAlunos.addReceiver(topicAlunos);
					myAgent.send(msgAlunos);
				} else {
					block();
				}

			}
		});
	}
	
	// Retorna um mapa com o nome de cada aluno como chave e a nota e o status como valores.
	private TickerBehaviour getRequestInfoBehaviour(){
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
