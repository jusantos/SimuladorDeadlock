import com.sun.org.apache.xalan.internal.xslt.Process;

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

	public void detectarDeadLock(){
		ArrayList<Integer> recursosDisponiveis = new ArrayList<>(recursos.size());
		ArrayList<Integer> recursosRequisitados = new ArrayList<>(processos.size());
		ArrayList<Integer> processosSemRodar     = new ArrayList<>(processos.size());

		for (Recurso recurso : recursos) {
			recursosDisponiveis.add(recurso.instancias);
		}

		for (int i = 0; i < processos.size(); i++) {
			recursosRequisitados.add(processos.get(i).requisicaoCorrente);
			processosSemRodar.add(i);
		}

		boolean reiniciarAnalise;
		do{
			reiniciarAnalise = false;
			for (int i = 0; i < recursosRequisitados.size(); i++) {
				if (processosSemRodar.contains(i)){
					if (recursosDisponiveis.get(recursosRequisitados.get(i)) != 0){
						processosSemRodar.remove(Integer.valueOf(i));
						reiniciarAnalise = true;
						for (int i1 = 0; i1 < processos.get(i).numeroDeInstancias.length; i1++) {
							recursosDisponiveis.set(i,recursosDisponiveis.get(i) + processos.get(i).numeroDeInstancias[i]);
						}
					}
				}
			}
		}while (reiniciarAnalise);

		if (processosSemRodar.size() < processos.size()){
			System.out.println("DeadLock detectado entre os processos:");
			for (Integer integer : processosSemRodar) {
				System.out.println(processos.get(integer).pid + " ");
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
    public int getIndiceDeRecurso() {
        Random random = new Random();
        return random.nextInt(this.recursos.size());
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
