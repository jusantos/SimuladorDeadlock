import java.util.ArrayList;

public class Processo extends Thread {
	
	public int pid;
	public int tempoDeSolicitacao;
	public int tempoDeUtilizacao;
	public boolean keepAlive = true;
	private Sistema sistema;

	private int requisicaoCorrente = -1;
	private ArrayList<Integer> temposCorrentes = new ArrayList<Integer>();
	private ArrayList<Recurso> recursosAlocados = new ArrayList<Recurso>();

	public Processo(int pid, int tempoDeSolicitacao, int tempoDeUtilizacao, Sistema sistema){
		this.pid = pid;
		this.tempoDeSolicitacao = tempoDeSolicitacao;
		this.tempoDeUtilizacao = tempoDeUtilizacao;
		this.sistema = sistema;
	}
	
	public void run(){

		int contadorSolicitacao = this.tempoDeSolicitacao;

		while(this.keepAlive) {

			// Esperando o tempo
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}

			contadorSolicitacao--;

			// Pede recurso
			if(contadorSolicitacao == 0) {

				// Pegando o índice de um recurso aleatório
				this.sistema.downMutex();
				this.requisicaoCorrente = this.sistema.getIndiceDeRecurso();
				this.sistema.upMutex();

				Recurso recurso = this.sistema.recursos.get(this.requisicaoCorrente);

				// Pegando o recurso a partir do semáforo
				System.out.println("Processo " + this.pid + " solicitou o recurso " + recurso.nome);

				this.sistema.downMutex();
				if(recurso.instancias == 0) {
					System.out.println("Processo " + this.pid + " bloqueou pelo recurso " + recurso.nome);
				}
				this.sistema.upMutex();

				recurso.pegarInstancia();

				this.sistema.downMutex();
				recurso.instancias++;
				System.out.println("Processo " + this.pid + " pegou o recurso " + recurso.nome);
				this.sistema.upMutex();

				this.temposCorrentes.add(this.tempoDeUtilizacao);

				contadorSolicitacao = this.tempoDeSolicitacao;

			}

			// Decrementando tempos e vendo se algum dos recursos deve ser liberado
			for(int i = 0; i < this.temposCorrentes.size(); i++) {
				int qtd = this.temposCorrentes.get(i) - 1;
				if(qtd == 0) { // Deve liberar o recurso
					Recurso recurso = this.recursosAlocados.get(i);
					this.sistema.downMutex();
					recurso.instancias++;
					recurso.liberarInstancia();
					this.sistema.upMutex();
				} else {
					this.temposCorrentes.set(i, qtd);
				}
			}

		}

	}
	
}
