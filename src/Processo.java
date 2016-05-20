import java.util.Random;
import java.util.concurrent.Semaphore;

public class Processo extends Thread {
	
	public int identificadorProcesso;
	public int tempoDeSolicitacao;
	public int tempoDeUtilizacao;
	public int recurso;
	public static int numeroDeProcessos;
	public Sistema dados;
	public Recurso recursos;
	public int tempo = 0;
	public int tempoAux = 0;
	public boolean sinal = false;
	public int recursoUtilizado = 0;
	public static int sinalNum = 0;
	public static int processosMortos = 0;
	Random processoAleatorio = new Random();
	
	public static Recurso[] rec = new Recurso[10];
	public static Processo[] proc = new Processo[15];
	public static Semaphore mutex = new Semaphore(1);
	public static Semaphore para = new Semaphore(0);
	public static boolean recursoAtual = false;
	
	
	Processo(Recurso recursos){
		this.recursos = recursos;
	}

	Processo(int identificadorProcesso, int tempoDeSolicitacao, int tempoDeUtilizacao, int recurso){
		this.identificadorProcesso = identificadorProcesso;
		this.tempoDeSolicitacao = tempoDeSolicitacao;
		this.tempoDeUtilizacao = tempoDeUtilizacao;
		this.recurso = 0;
		this.sinal = false;
		Processo.numeroDeProcessos++;
		statusProcesso();
	}
	
	public void run(){
		while(Sistema.ActiveProcesso){
			try{

				if(Principal.rec[this.recurso].instanciasDoRecurso == 0){
					this.recursoUtilizado++;
					if(this.recursoUtilizado == 5){
						Principal.tableProcesso.setValueAt("bloqueado", this.identificadorProcesso, 1);
						Processo.recursoAtual = true;
						Processo.para.acquire();
					}
					int r = processoAleatorio.nextInt(Recurso.contRecursos);
					getRecurso(r);
				}else{
					Thread.sleep(1000);
					tempo++;
					tempoAux++;
					this.recursoUtilizado = 0;
					solicitarRecurso();
					liberarRecurso();
					
				}
				Principal.tableRecurso.setValueAt(Principal.rec[this.recurso].instanciasDoRecurso, Principal.rec[this.recurso].identificadorRecurso, 2);
				int r = processoAleatorio.nextInt(Recurso.contRecursos);
				getRecurso(r);
				
			} catch (InterruptedException e){
				e.printStackTrace();
			}
		}
	}
	
	public void statusProcesso(){
		System.out.println("Processo " + this.identificadorProcesso + " criado com TS = " + this.tempoDeSolicitacao + " e TU = " + this.tempoDeUtilizacao);
	}
	
	public void solicitarRecurso() throws InterruptedException{
		
		if(getTempo() % this.tempoDeSolicitacao == 0){
			if(Principal.rec[this.recurso].instanciasDoRecurso == 0){
				Principal.tableProcesso.setValueAt("bloqueado", this.identificadorProcesso, 1);
				System.out.println("-----------------------Processo " + this.identificadorProcesso + " está bloqueado-------------------!");
				Processo.recursoAtual = true;
				Processo.para.acquire();
			}else{
				Principal.tableProcesso.setValueAt("Rodando", this.identificadorProcesso, 1);
				Processo.mutex.acquire();
				Principal.rec[this.recurso].disponiveis.acquire();
				Principal.rec[this.recurso].instanciasDoRecurso--;
				System.out.println("Processo " + this.identificadorProcesso + " solicitou " + Principal.rec[this.recurso].nome);
				Processo.mutex.release();
			}
		}
	}
	
	public void liberarRecurso() throws InterruptedException{
		
		if(getTempoAux()%(this.tempoDeUtilizacao + this.tempoDeSolicitacao) == 0){
			Processo.mutex.acquire();
			Principal.rec[this.recurso].disponiveis.release();
			Principal.rec[this.recurso].instanciasDoRecurso++;
			System.out.println("Processo " + this.identificadorProcesso + " liberou " + Principal.rec[this.recurso].nome);
			Processo.mutex.release();
			setTempoAux(this.tempoDeSolicitacao);
		}
	}

	public int getTempo(){
		return tempo;
	}
	
	public int getTempoAux(){
		return tempoAux;
	}
	
	public void setTempoAux(int valor){
		 this.tempoAux = this.tempoAux - valor;
	}
	
	public boolean getSinal(){
		return sinal;
	}
	
	public void getRecurso(int r){
		this.recurso = r;
	}
	
	
	
}
