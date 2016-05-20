import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Sistema extends Thread {
	
	public ArrayList<Processo> processos = new ArrayList<Processo>();
	public ArrayList<Recurso> recursos = new ArrayList<Recurso>();
    public Semaphore mutex = new Semaphore(1);
	
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

    // TODO Melhorar esse método
    public int getIndiceDeRecurso(int processIndex) {
        Random random = new Random();

		int randomNum;
		int aux;
		int contador = 0;

		do {
			aux = 0;
			randomNum = random.nextInt(this.recursos.size());

			for (Processo processo : processos) {
				if(processo.pid == processIndex){
					for (Recurso recurso : processo.getRecursosAlocados()) {
						if(recurso.nome == recursos.get(randomNum).nome){
							aux++;
						}
					}
				}
			}

			contador++;
		}while(contador < 100 && aux < recursos.get(randomNum).maxInstancias);

		if(contador == 100){
			return -1;
		}

        return randomNum;
    }

    public void downMutex() {
        try {
            this.mutex.acquire();
        } catch (InterruptedException e) {
        }
    }

    public void upMutex() {
        this.mutex.release();
    }

}
