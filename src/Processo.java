import java.util.ArrayList;

public class Processo extends Thread {
	
	public int pid;
	public int tempoDeSolicitacao;
	public int tempoDeUtilizacao;
	public boolean keepAlive = true;
	private Sistema sistema;

	public int requisicaoCorrente = -1;
	private ArrayList<Integer> temposCorrentes = new ArrayList<Integer>();
	private ArrayList<Recurso> recursosAlocados = new ArrayList<Recurso>();
	public Principal principal;

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
				principal.desenharTabelaDeRequisicoes();

				this.sistema.downMutex();
				if(recurso.instancias == 0) {
					System.out.println("Processo " + this.pid + " bloqueou pelo recurso " + recurso.nome);
				}
				this.sistema.upMutex();

				// Dorme se não houver nenhuma instância disponível
				recurso.pegarInstancia();

				this.sistema.downMutex();
				recurso.instancias--;
				System.out.println("Processo " + this.pid + " pegou o recurso " + recurso.nome);
				this.sistema.upMutex();

				this.recursosAlocados.add(recurso);
				this.temposCorrentes.add(this.tempoDeUtilizacao + 1);

				this.requisicaoCorrente = -1;
				contadorSolicitacao = this.tempoDeSolicitacao;

				this.principal.desenharTabelaDeRecursosDisponiveis();
				principal.desenharTabelaDeRequisicoes();

			}

			// Decrementando tempos
			this.decrementaTempoDasInstancias();

			// Liberando recursos
			int index = -1;
			while((index = this.indexDoPrimeiroRecursoALiberar()) >= 0) {
				Recurso recurso = this.recursosAlocados.get(index);
				this.sistema.downMutex();
				recurso.instancias++;
				recurso.liberarInstancia();
				this.sistema.upMutex();
				this.recursosAlocados.remove(index);
				this.temposCorrentes.remove(index);
			}

		}

		System.out.println("Processo " + this.pid + " finalizou");

	}

	private void decrementaTempoDasInstancias() {
		for(int i = 0; i < this.temposCorrentes.size(); i++) {
			this.temposCorrentes.set(i, this.temposCorrentes.get(i) - 1);
		}
	}

	private int indexDoPrimeiroRecursoALiberar() {
		for(int i = 0; i < this.temposCorrentes.size(); i++) {
			if(this.temposCorrentes.get(i) == 0) {
				return i;
			}
		}
		return -1;
	}
	
}
