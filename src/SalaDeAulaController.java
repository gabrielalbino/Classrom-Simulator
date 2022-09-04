import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;

import agents.interfaces.AlunoAgentInterface;
import agents.interfaces.AlunoInfo;
import agents.interfaces.ProfessorAgentInterface;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class SalaDeAulaController {
	private List<AgentController> alunos;
	private AgentController professor, interfaceController;
	private jade.core.Runtime rt;
	private AgentContainer mainContainer;
	public JFrame frame;
	
	public SalaDeAulaController(int quantidadeAlunos) {
		alunos = new ArrayList<AgentController>();
		createAndShowJade(quantidadeAlunos);
        //Create and set up the window.
        frame = new JFrame("Simulador de sala de aula");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });


	}
	
	private int getAulaTipoConteudo() {
		try {
			ProfessorAgentInterface professor = this.professor.getO2AInterface(ProfessorAgentInterface.class);
			int tipoConteudo = professor.getTipoConteudo();
			return tipoConteudo;
		} catch (StaleProxyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	

	// Cria o runtime JADE e 
	private void createAndShowJade(int quantidadeAlunos) {
		this.rt = jade.core.Runtime.instance();
		Profile profile = new ProfileImpl();
		
		profile.setParameter(Profile.PLATFORM_ID, "Simulador Sala de Aula");
		profile.setParameter(Profile.CONTAINER_NAME, "Sala de aula");
		profile.setParameter("services",Profile.DEFAULT_SERVICES + ";jade.core.messaging.TopicManagementService");
		
		this.mainContainer = rt.createMainContainer(profile);
		try {

			interfaceController = mainContainer.createNewAgent("interface","agents.InterfaceAgent",null);
			interfaceController.start();
			 
			for(int i = 0; i < quantidadeAlunos; i++) {
				 AgentController ac = mainContainer.createNewAgent("Aluno " + i,"agents.AlunoNerdAgent",null);
				 ac.start();
				 alunos.add(ac);
			}

			professor = mainContainer.createNewAgent("professor","agents.ProfessorAgent",null);
			professor.start();
			 
			
			AgentController rma = mainContainer.createNewAgent("rma", "jade.tools.rma.rma", null);
			rma.start();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private void createAndShowGUI() {
        //Add the ubiquitous "Hello World" label.

        JLabel labelProfessor = new JLabel("Aula: " + getAulaTipoConteudo());
        frame.getContentPane().add(labelProfessor);
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

}
