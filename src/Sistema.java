import java.util.ArrayList;

public class Sistema extends Thread {
	
	public ArrayList<Processo> processos = new ArrayList<Processo>();
	public ArrayList<Recurso> recursos = new ArrayList<Recurso>();
	
	public int intervaloDeVerificacao;
	public int tempo;
	public static String status;
	public static boolean ActiveProcesso = true;
	

	public Sistema(int intervalo){
		this.intervaloDeVerificacao = intervalo;
	}
	
	
	public void run(){
		ActiveProcesso = true;
		while(true){
			try{
				Thread.sleep(this.intervaloDeVerificacao * 1000);
				System.out.println("Sistema verificando a cada " + this.intervaloDeVerificacao + " segundos");
//				tempo++;
//				if(getTempo() % intervaloDeVerificacao == 0){
//					if(Processo.numeroDeProcessos == 1){
//						Principal.STATUS.setText("SISTEMA NORMAL");
//					}else if(Processo.numeroDeProcessos!=1 && Processo.recursoAtual == true){
//						Principal.STATUS.setText("DEADLOCK");
//					}else{
//						Principal.STATUS.setText("SISTEMA NORMAL");
//					}
//				}
					
				
			} catch (InterruptedException e){
				e.printStackTrace();
			}
		}
	}
	
	public void adicionarProcesso(Processo processo) {
		this.processos.add(processo);
	}
	
	public void adicionarRecurso(Recurso recurso) {
		this.recursos.add(recurso);
	}
	
	public void statusSistema() {
		System.out.println("O sistema verifica deadlock a cada "+ this.intervaloDeVerificacao +"s");
	}
	
	public void checaDeadlock() {
		System.out.println("Sistema Operacional Normal");
	}

	public int getTempo(){
		return tempo;
	}
	
	public static String getStatus(){
		return status;
	}
	
	public void setIntervaloDeVerificacao(int intervalo) {
		this.intervaloDeVerificacao = intervalo;
	}
}
