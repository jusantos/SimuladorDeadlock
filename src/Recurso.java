import java.util.concurrent.Semaphore;

public class Recurso {
	public String nome;
	public int identificadorRecurso;
	public int instanciasDoRecurso;
	public static Semaphore disponiveis = new Semaphore(0);
	public static int contRecursos = 0;
	
	public Recurso(String nome, int identificadorRecurso, int instanciasDoRecurso){
		this.nome = nome;
		this.identificadorRecurso = identificadorRecurso;
		this.instanciasDoRecurso = instanciasDoRecurso;
		contRecursos++;
		disponiveis.release(instanciasDoRecurso);
		statusRecurso();
		
	}
	
	public void statusRecurso(){
		System.out.println("Recurso " + this.nome + " de Id = " + this.identificadorRecurso + " tem " + this.instanciasDoRecurso + " recursos");
	}
	
	public Semaphore getInstanciasDoRecurso(){
		return disponiveis;
	}
	
	public String getNome(){
		return nome;
	}
	
}
