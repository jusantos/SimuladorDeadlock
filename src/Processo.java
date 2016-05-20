import java.util.ArrayList;

public class Processo extends Thread {
	
	public int pid;
	public int tempoDeSolicitacao;
	public int tempoDeUtilizacao;
	public boolean keepAlive = true;

	private Sistema sistema;
	public Principal principal;

	public int requisicaoCorrente = -1;

	private ArrayList<Integer> temposCorrentes = new ArrayList<Integer>();
	private ArrayList<Recurso> recursosAlocados = new ArrayList<Recurso>();
	public int[] numeroDeInstancias;


	public Processo(int pid, int tempoDeSolicitacao, int tempoDeUtilizacao, Sistema sistema){
		this.pid = pid;
		this.tempoDeSolicitacao = tempoDeSolicitacao;
		this.tempoDeUtilizacao = tempoDeUtilizacao;
		this.sistema = sistema;
		this.numeroDeInstancias = new int[sistema.recursos.size()];
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
				this.requisicaoCorrente = this.sistema.getIndiceDeRecurso(); // TODO Pegar um recurso aleatório de forma 'correta'
				Recurso recurso = this.sistema.recursos.get(this.requisicaoCorrente);
				this.sistema.upMutex();

				// Pegando o recurso a partir do semáforo
				principal.log("Processo " + this.pid + " solicitou o recurso " + recurso.nome);
				principal.desenharTabelaDeRequisicoes();

				if(recurso.instancias == 0) {
					principal.log("Processo " + this.pid + " bloqueou pelo recurso " + recurso.nome);
				}

				// Dorme se não houver nenhuma instância disponível
				recurso.pegarInstancia();

				this.sistema.downMutex();
				recurso.instancias--;
				numeroDeInstancias[this.requisicaoCorrente]++;
				principal.log("Processo " + this.pid + " pegou o recurso " + recurso.nome);
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
			if(this.temposCorrentes.get(0) == 0) {
				Recurso recurso = this.recursosAlocados.get(0);
				this.sistema.downMutex();
//				this.numeroDeInstancias-- TODO Decrementar o número de instancias de um recurso para este processo
				recurso.instancias++;
				recurso.liberarInstancia();
				this.sistema.upMutex();
				this.recursosAlocados.remove(0);
				this.temposCorrentes.remove(0);
			}

		}

		principal.log("Processo " + this.pid + " finalizou");

	}

	private void decrementaTempoDasInstancias() {
		for(int i = 0; i < this.temposCorrentes.size(); i++) {
			this.temposCorrentes.set(i, this.temposCorrentes.get(i) - 1);
		}
	}
	
}
