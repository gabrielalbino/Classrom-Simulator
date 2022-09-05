import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;

public class SalaDeAulaController {
	private List<AgentController> alunos;
	private AgentController professor, interfaceController;
	private jade.core.Runtime rt;
	private AgentContainer mainContainer;
	public JFrame frame;

	public SalaDeAulaController(int quantidadeAlunos) {
		alunos = new ArrayList<AgentController>();
		createAndShowJade(quantidadeAlunos);
	}


	// Cria o runtime JADE e
	private void createAndShowJade(int quantidadeAlunos) {
		this.rt = jade.core.Runtime.instance();
		Profile profile = new ProfileImpl();

		profile.setParameter(Profile.PLATFORM_ID, "Simulador Sala de Aula");
		profile.setParameter(Profile.CONTAINER_NAME, "Sala de aula");
		profile.setParameter("services", Profile.DEFAULT_SERVICES + ";jade.core.messaging.TopicManagementService");

		this.mainContainer = rt.createMainContainer(profile);
		try {

			interfaceController = mainContainer.createNewAgent("interface", "agents.InterfaceAgent", null);
			interfaceController.start();

			for (int i = 0; i < quantidadeAlunos; i++) {
				String tipoAluno;
				if (i <= 3) {
					tipoAluno = "agents.AlunoPalestrinhaAgent";
				} else if (i <= 5) {
					tipoAluno = "agents.AlunoPerguntadorAgent";
				} else if (i <= 14) {
					tipoAluno = "agents.AlunoConversadorAgent";
				} else if (i <= 18) {
					tipoAluno = "agents.AlunoMigueAgent";
				} else {
					tipoAluno = "agents.AlunoNerdAgent";
				}

				AgentController ac = mainContainer.createNewAgent("Aluno " + i, tipoAluno, null);
				ac.start();
				alunos.add(ac);
			}

			professor = mainContainer.createNewAgent("professor", "agents.ProfessorAgent", null);
			professor.start();

			AgentController rma = mainContainer.createNewAgent("rma", "jade.tools.rma.rma", null);
			rma.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
