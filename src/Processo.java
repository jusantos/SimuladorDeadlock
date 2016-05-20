public class Processo extends Thread {
	
	public int pid;
	public int tempoDeSolicitacao;
	public int tempoDeUtilizacao;
	
	public static boolean recursoAtual = false;

	public Processo(int pid, int tempoDeSolicitacao, int tempoDeUtilizacao){
		this.pid = pid;
		this.tempoDeSolicitacao = tempoDeSolicitacao;
		this.tempoDeUtilizacao = tempoDeUtilizacao;
	}
	
	public void run(){
	}
	
	public void solicitarRecurso() throws InterruptedException{
	}
	
	public void liberarRecurso() throws InterruptedException{
	}
	
}
