package agents;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

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
	public JFrame frame;
	public int teste = 0;
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
		/*
        frame = new JFrame("Simulador de sala de aula");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel labelProfessor = new JLabel("Aula: " + "Teste");
        frame.getContentPane().add(labelProfessor);
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
        */
		
		System.out.println("NERD 😀");
		Tela salaDeAula = new Tela();
		salaDeAula.frame.setVisible(true);
		
	    int delay = 1000; //milliseconds
		
	      ActionListener taskPerformer = new ActionListener() {
	          public void actionPerformed(ActionEvent evt) {
	              String date = new java.text.SimpleDateFormat("HH:mm:ss").format(new java.util.Date(System.currentTimeMillis()));
	              
	              if (teste > 4) teste = 0;

	              teste++;
	          }
	      };
	      new Timer(delay, taskPerformer).start();

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
		addBehaviour(getRequestAulasBehaviour());
		

	}
	
	/*	Comportamento para atualizar a interface
	 * 
	 * */
	private CyclicBehaviour getUpdateInterfaceBehaviour(Tela sala) {
		return (new CyclicBehaviour(this) {
			private static final long serialVersionUID = 1L;

			public void action() {
				ACLMessage msg = myAgent.receive(MessageTemplate.MatchTopic(topicUpdate));
				if (msg != null) {
					// System.out.println(statusAlunos.toString());
					sala.changeProfessorStatus(teste);
					for(String key : statusAlunos.keySet()){
							if( !key.equals("professor") ) {
						  System.out.println(key + "----" + statusAlunos.get(key));
						  sala.changeStudentStatus(key, statusAlunos.get(key).status);
							} else {
								System.out.println(key + "----" + statusAlunos.get(key));
								sala.changeProfessorStatus(statusAlunos.get(key).status);
							}
					}
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
	private TickerBehaviour getRequestAulasBehaviour(){
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
