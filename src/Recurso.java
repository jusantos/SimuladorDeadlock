import java.util.concurrent.Semaphore;

public class Recurso {
	
	public String nome;
	public int instancias;
	public static Semaphore disponiveis = new Semaphore(0);
	public static int contRecursos = 0;
	public Semaphore semaphoreIntances;
	
	public Recurso(String nome, int instancias) {
		this.nome = nome;
		this.instancias = instancias;
		this.semaphoreIntances = new Semaphore(this.instancias);
	}

	public void pegarInstancia() {
		try {
			this.semaphoreIntances.acquire();
		} catch (InterruptedException e) {
		}
	}

	public void liberarInstancia() {
		this.semaphoreIntances.release();
	}
	
}
