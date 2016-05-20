import java.util.concurrent.Semaphore;

public class Recurso {
	
	public String nome;
	public int maxInstancias;
	public int instancias;
	public static Semaphore disponiveis = new Semaphore(0);
	public static int contRecursos = 0;
	
	public Recurso(String nome, int instancias) {
		this.nome = nome;
		this.maxInstancias = instancias;
		this.instancias = instancias;
		this.disponiveis = new Semaphore(this.instancias);
	}

	public void pegarInstancia() {
		try {
			this.disponiveis.acquire();
		} catch (InterruptedException e) {
		}
	}

	public void liberarInstancia() {
		this.disponiveis.release();
	}
	
}
