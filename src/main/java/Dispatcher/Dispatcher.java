package Dispatcher;

import Modelos.Director;
import Modelos.Operador;
import Modelos.Supervisor;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;
import javax.swing.JOptionPane;

/**
 *
 * @author Luis Fernando Tafur
 */
public class Dispatcher extends Thread{
    private static final Semaphore mutex = new Semaphore(1);
    private List<Director> director = new ArrayList<>();
    private List<Supervisor> supervisor = new ArrayList<>();
    private List<Operador> operador = new ArrayList<>();
    private final Semaphore cola;
    private LinkedList<Integer> llamada = new LinkedList<>();

    public Dispatcher(Semaphore cola , List<Operador> operador,
            List<Supervisor> supervisor, List<Director> director, LinkedList<Integer> llamada){
        this.cola = cola;
        this.llamada = llamada;
        this.director = director;
        this.operador = operador;
        this.supervisor = supervisor;
    }

    public void dispatchCall() {
        System.out.println("Iniciando Envio...");
        this.start();
    }

    @Override
    public void run() {
        try {
            while (true) {
                mutex.acquire();
                if(llamada.size()>0){
                    cola.acquire();
 
                    if(!operador.isEmpty() || !supervisor.isEmpty() || !director.isEmpty()){
                        Integer clientId = llamada.remove();
                        System.out.println("Tomando llamada del cliente " + clientId);
                        
                        if (!operador.isEmpty()) {
                            Operador oper = operador.remove(0);
                            System.out.println("Asignando llamada de: " + clientId + " a: " + oper.nombre);
                            oper.start();
                        } else if (!supervisor.isEmpty()) {
                            Supervisor sup = supervisor.remove(0);
                            System.out.println("Asignando llamada de: " + clientId + " a: "+ sup.nombre);
                            sup.start();
                        } else if(!director.isEmpty()){
                            Director dir = director.remove(0);
                            System.out.println("Asignando llamada de: " + clientId + " a: "+ dir.nombre);
                            dir.start();
                        }
                    }else {
                        cola.release();
                    }
                }
                mutex.release();
            }

        } catch (InterruptedException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
}
