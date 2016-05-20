public class Processo extends Thread {
	
	public int pid;
	public int tempoDeSolicitacao;
	public int tempoDeUtilizacao;
	public boolean keepAlive = true;
	private Sistema sistema;

	public Processo(int pid, int tempoDeSolicitacao, int tempoDeUtilizacao, Sistema sistema){
		this.pid = pid;
		this.tempoDeSolicitacao = tempoDeSolicitacao;
		this.tempoDeUtilizacao = tempoDeUtilizacao;
		this.sistema = sistema;
	}
	
	public void run(){

		int contadorSolicitacao = this.tempoDeSolicitacao;

		while(this.keepAlive) {

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}

			contadorSolicitacao--;

			// Pede recurso
			if(contadorSolicitacao == 0) {

				int indice = this.sistema.getIndiceDeRecurso();

				Recurso recurso = this.sistema.recursos.get(indice);
				recurso.pegarInstancia(); // Dorme se não houverem mais instancias

				this.sistema.downMutex();
				recurso.instancias++;
				System.out.println("Processo " + this.pid + "pegou o recurso " + recurso.nome);
				this.sistema.upMutex();


				contadorSolicitacao = this.tempoDeSolicitacao;

			}

		}

	}
	
	public void solicitarRecurso() throws InterruptedException{
	}
	
	public void liberarRecurso() throws InterruptedException {
	}
	
}
