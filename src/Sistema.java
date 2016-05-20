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

    public Principal principal;

	public Sistema(int intervalo, Principal principal){
		this.intervaloDeVerificacao = intervalo;
        this.principal = principal;
	}
	
	
	public void run(){
		ActiveProcesso = true;
		while(true){
			try{
				Thread.sleep(1000);
				tempo++;
				if(getTempo() % intervaloDeVerificacao == 0){
					detectarDeadLock();
				}
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
					int recursoRequisitado = recursosRequisitados.get(i);
					if (recursoRequisitado == -1 || recursosDisponiveis.get(recursoRequisitado) != 0){
						processosSemRodar.remove(Integer.valueOf(i));
						reiniciarAnalise = true;
						for (int j = 0; j < processos.get(i).numeroDeInstancias.length; j++) {
							recursosDisponiveis.set(j,recursosDisponiveis.get(j) + processos.get(i).numeroDeInstancias[j]);
						}
					}
				}
			}
		}while (reiniciarAnalise);

		if (processosSemRodar.size() > 0){
			principal.log("DeadLock detectado entre os processos:");
			for (Integer integer : processosSemRodar) {
				principal.log(processos.get(integer).pid + " ");
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
//		System.out.println("O sistema verifica deadlock a cada "+ this.intervaloDeVerificacao +"s");
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
		}while(contador < 100 && aux == recursos.get(randomNum).maxInstancias);

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
