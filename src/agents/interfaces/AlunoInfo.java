package agents.interfaces;

public class AlunoInfo {
	public AlunoInfo(int status, int nota){
		this.status = status;
		this.nota = nota;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Nota: " + nota + ", Status: " + status;
	}
	public int status;
	public int nota;
}
