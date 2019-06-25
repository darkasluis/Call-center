package Dispatcher;

import Modelos.Cliente;
import Modelos.Director;
import Modelos.Operador;
import Modelos.Supervisor;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 *
 * @author Luis Fernando Tafur
 */
public class Llamada {
    private final int maximollamada;
    private final Semaphore cola;
    private List<Director> director = new ArrayList<>();
    private List<Supervisor> supervisor = new ArrayList<>();
    private List<Operador> operador = new ArrayList<>();
    private final LinkedList<Integer> llamada = new LinkedList<>();

    public Llamada(int maximollamada){
        this.maximollamada = maximollamada;
        this.cola = new Semaphore(maximollamada, true);
    }

    public void Iniciarllamadas() {
        director.add(new Director("Luis",cola, director));
        supervisor.add(new Supervisor("Danna", cola, supervisor));
        operador.add(new Operador("Melissa", cola, operador));
        operador.add(new Operador("Laura", cola, operador));
        operador.add(new Operador("Fernando", cola, operador));
        Dispatcher dispatcher = new Dispatcher(cola, operador, supervisor, director, llamada);
        dispatcher.dispatchCall();

        for (int i = 0; i < 10; i++) {
            Cliente cliente = new Cliente(i, llamada);
            cliente.start();
        }
    }

    public List<Director> getDirector() {
        return director;
    }

    public List<Supervisor> getSupervisor() {
        return supervisor;
    }

    public List<Operador> getOperador() {
        return operador;
    }

    public void setDirector(List<Director> director) {
        this.director = director;
    }

    public void setSupervisor(List<Supervisor> supervisor) {
        this.supervisor = supervisor;
    }

    public void setOperador(List<Operador> operador) {
        this.operador = operador;
    }

    public Semaphore getCola() {
        return cola;
    }

    public LinkedList<Integer> getLlamada() {
        return llamada;
    }




}
