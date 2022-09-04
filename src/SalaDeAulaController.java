import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;

import agents.AlunoAgentInterface;
import agents.AlunoStatus;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

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
	
	// Retorna um mapa com o nome de cada aluno como chave e a nota e o status como valores.
	private Map<String, AlunoStatus> getAlunosStatus(){
		Map<String, AlunoStatus> map = new HashMap<String, AlunoStatus>();
		
        for(int i = 0; i < alunos.size(); i++) {
        	try {
				AlunoAgentInterface aluno = alunos.get(i).getO2AInterface(AlunoAgentInterface.class);
				map.put(aluno.getAlunoNome(), new AlunoStatus(aluno.getAlunoStatus(), aluno.getNota()));
        	} catch (StaleProxyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
        return map;
	}

	// Cria o runtime JADE e 
	private void createAndShowJade(int quantidadeAlunos) {
		this.rt = jade.core.Runtime.instance();
		Profile profile = new ProfileImpl();
		
		profile.setParameter(Profile.PLATFORM_ID, "Simulador Sala de Aula");
		profile.setParameter(Profile.CONTAINER_NAME, "Sala de aula");
		
		this.mainContainer = rt.createMainContainer(profile);
		try {
			for(int i = 0; i < quantidadeAlunos; i++) {
				 AgentController ac = mainContainer.createNewAgent("Aluno " + i,"agents.AlunoAgent",null);
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
