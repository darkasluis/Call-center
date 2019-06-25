package Modelos;

import static java.lang.Thread.sleep;
import java.util.List;
import java.util.concurrent.Semaphore;
import javax.swing.JOptionPane;

/**
 *
 * @author Luis Fernando Tafur
 */
public class Director extends Thread implements Runnable{
    public String nombre;
    private final Semaphore cola;
    private final List<Director> empleado;

    public Director(String nombre, Semaphore cola, List<Director> empleado){
        this.nombre = nombre;
        this.cola = cola;
        this.empleado = empleado;
    }

    public void IniciarLlamada() {
        System.out.println("Director " + this.nombre + " Inicia la llamada");
    }

    public void FinalizarLlamada() {
        System.out.println("Director " + this.nombre + " Finaliza llamada");
    }

    @Override
    public void run() {
        int tiempollamada = (int) (Math.random() * (10 - 5)) + 5;
        this.IniciarLlamada();

        try {
            sleep(tiempollamada * 1000);
            this.FinalizarLlamada();
        } catch (InterruptedException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            cola.release();
            this.empleado.add(new Director(this.nombre, this.cola, this.empleado));
        }
    }
}
