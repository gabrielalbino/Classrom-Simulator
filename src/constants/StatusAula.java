package constants;

public class StatusAula {
	public final static int INICIANDO = 0;
	public final static int CONTEUDO_INTERESSANTE = 1;
	public final static int CONTEUDO_IRRELEVANTE = 2;
	public final static int CHAMANDO_ATENCAO = 3;
	public final static int RECEBENDO_PALESTRINHA = 4;
	public final static int RESPONDENDO_PERGUNTA = 5;

	public final static int FINALIZADA = 6;

	public static String getStatusName(int status) {
		switch (status) {
			case INICIANDO:
				return "Iniciando aula";
			case CONTEUDO_INTERESSANTE:
				return "Conteúdo interessante";
			case CONTEUDO_IRRELEVANTE:
				return "Conteúdo Irrelevante";
			case CHAMANDO_ATENCAO:
				return "Chamando Atenção";
			case RECEBENDO_PALESTRINHA:
				return "Recebendo Palestrinha";
			case RESPONDENDO_PERGUNTA:
				return "Respondendo Pergunta";
			case FINALIZADA:
				return "Finalizada";
			default:
				return "" + status;
		}
	}
}
