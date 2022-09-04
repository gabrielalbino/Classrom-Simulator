package constants;

public class StatusAlunos {
	public final static int AGUARDANDO_INICIO = 0;
	public final static int PRESTANDO_ATENCAO = 1;
	public final static int VIAJANDO_NA_MAIONESE = 2;
	public final static int FORA_DA_SALA = 3;
	
	public static String getStatusName(int status) {
		switch(status) {
		case AGUARDANDO_INICIO:
			return "Aguardando início";
		case PRESTANDO_ATENCAO:
			return "Prestando atenção";
		case VIAJANDO_NA_MAIONESE:
			return "Viajando na maionese";
		case FORA_DA_SALA:
			return "Fora da sala";
		default:
			return "" + status;
		}
	}
}
