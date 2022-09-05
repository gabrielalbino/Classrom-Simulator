package constants;

public class StatusAlunos {
	public final static int AGUARDANDO_INICIO = 0;
	public final static int PRESTANDO_ATENCAO = 1;
	public final static int VIAJANDO_NA_MAIONESE = 2;
	public final static int FORA_DA_SALA = 3;
	public final static int DANDO_PALESTRINHA = 4;
	public final static int CONVERSANDO = 5;
	public final static int PERGUNTANDO = 6;
	public final static int TRABALHANDO = 7; 

	public static String getStatusName(int status) {
		switch (status) {
			case AGUARDANDO_INICIO:
				return "Aguardando início";
			case PRESTANDO_ATENCAO:
				return "Prestando atenção";
			case VIAJANDO_NA_MAIONESE:
				return "Viajando na maionese";
			case FORA_DA_SALA:
				return "Fora da sala";
			case DANDO_PALESTRINHA:
				return "Dando palestrinha";
			case CONVERSANDO:
				return "Conversando";
			case PERGUNTANDO:
				return "Perguntando";
			default:
				return "" + status;
		}
	}
}
