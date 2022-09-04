import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;

public class SalaDeAulaController {
	private List<AgentController> alunos;
	private AgentController professor;
	private jade.core.Runtime rt;
	private AgentContainer mainContainer;
	
	
	public SalaDeAulaController(int quantidadeAlunos) {
		alunos = new ArrayList<AgentController>();
		createAndShowJade(quantidadeAlunos);
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
	}

	private void createAndShowJade(int quantidadeAlunos) {
		this.rt = jade.core.Runtime.instance();
		Profile profile = new ProfileImpl();
		
		profile.setParameter(Profile.PLATFORM_ID, "Simulador Sala de Aula");
		profile.setParameter(Profile.CONTAINER_NAME, "Sala de aula");
		
		this.mainContainer = rt.createMainContainer(profile);
		try {
			for(int i = 0; i < quantidadeAlunos; i++) {
				 AgentController ac = mainContainer.createNewAgent("aluno" + i,"agents.AlunoAgent",null);
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
        //Create and set up the window.
        JFrame frame = new JFrame("HelloWorldSwing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add the ubiquitous "Hello World" label.
        JLabel label = new JLabel("Hello World");
        frame.getContentPane().add(label);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

}
