package agents;

import constants.StatusAlunos;
import constants.StatusAula;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class AlunoPalestrinhaAgent extends AlunoAgent {
	private static final long serialVersionUID = 1L;

	public AlunoPalestrinhaAgent() {
		super();
	}

	protected void setup() {
		super.setup();

		Behaviour handleContentUpdate = new CyclicBehaviour(this) {
			private static final long serialVersionUID = 1L;

			/*
			 * Recebe as alterações de conteúdo e seta seu status de acordo. O Aluno nerd se
			 * comporta da seguinte maneira: Caso o conteudo esteja interessante, ele sempre
			 * presta atenção... Caso seja irrelevante, ele tem uma probabilidade de 5% de
			 * sair da aula.
			 */
			public void action() {
				ACLMessage msg = myAgent.receive(MessageTemplate.MatchTopic(topicAula));
				if (msg != null) {

					int statusAula = Integer.parseInt(msg.getContent());

					switch (statusAula) {
						case StatusAula.CONTEUDO_INTERESSANTE:
							dispersao += Math.random() * 0.1;
							break;
						case StatusAula.CONTEUDO_IRRELEVANTE:
							dispersao += Math.random() * 0.2;
							break;
						case StatusAula.RESPONDENDO_PERGUNTA:
							dispersao += Math.random() * 0.1;
							break;
						case StatusAula.CHAMANDO_ATENCAO:
							dispersao = 0;
							break;
					}
					setStatus(getActionByChance(dispersao, StatusAlunos.FORA_DA_SALA, StatusAlunos.PRESTANDO_ATENCAO));

					if (dispersao < 0.2) {
						setStatus(getActionByChance(dispersao, StatusAlunos.DANDO_PALESTRINHA,
								StatusAlunos.PRESTANDO_ATENCAO));
					} else {
						setStatus(getActionByChance(0.1, StatusAlunos.FORA_DA_SALA,
								StatusAlunos.PRESTANDO_ATENCAO));
					}

				} else {
					block();
				}
			}
		};

		addAlunoBehaviour(handleContentUpdate);

	}
}