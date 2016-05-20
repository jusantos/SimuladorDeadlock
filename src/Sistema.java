

public class Sistema extends Thread {
	
	public int intervaloDeVerificacao;
	public int tempo;
	public static String status;
	public static boolean ActiveProcesso = true;
	

	Sistema(int intervalo){
		this.intervaloDeVerificacao = intervalo;
		statusSistema();
	}
	
	
	public void run(){
		ActiveProcesso = true;
		while(true){
			try{
				Thread.sleep(1000);
				tempo++;
				if(getTempo() % intervaloDeVerificacao == 0){
					if(Processo.numeroDeProcessos == 1){
						Principal.STATUS.setText("SISTEMA NORMAL");
					}else if(Processo.numeroDeProcessos!=1 && Processo.recursoAtual == true){
						Principal.STATUS.setText("DEADLOCK");
					}else{
						Principal.STATUS.setText("SISTEMA NORMAL");
					}
				}
					
				
			} catch (InterruptedException e){
				e.printStackTrace();
			}
		}
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
}
