package Modelos;

import static java.lang.Thread.sleep;
import java.util.List;
import java.util.concurrent.Semaphore;
import javax.swing.JOptionPane;

/**
 *
 * @author Luis Fernando Tafur
 */
public class Supervisor extends Thread implements Runnable{
    public String nombre;
    private final Semaphore cola;
    private final List<Supervisor> empleado;

    public Supervisor(String nombre, Semaphore cola, List<Supervisor> empleado){
        this.nombre = nombre;
        this.cola = cola;
        this.empleado = empleado;
    }

    public void IniciarLlamada() {
        System.out.println("Supervisor " + this.nombre + " Inicia la llamada");
    }

    public void FinalizarLamada() {
        System.out.println("Supervisor " + this.nombre + " Finalizar llamada");
    }


    @Override
    public void run() {
        int tiempollamada = (int) (Math.random() * (10 - 5)) + 5;
        this.IniciarLlamada();

        try {
            sleep(tiempollamada * 1000);
            this.FinalizarLamada();
        } catch (InterruptedException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            cola.release();
            this.empleado.add(new Supervisor(this.nombre, this.cola, this.empleado));
        }

    }
}
