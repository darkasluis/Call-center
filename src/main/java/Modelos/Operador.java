package Modelos;

import static java.lang.Thread.sleep;
import java.util.List;
import java.util.concurrent.Semaphore;
import javax.swing.JOptionPane;

/**
 *
 * @author Luis Fernando Tafur
 */
public class Operador extends Thread implements Runnable{
    public String nombre;
    private final Semaphore cola;
    private final List<Operador> empleado;

    public Operador(String nombre, Semaphore cola, List<Operador> empleado){
        this.nombre = nombre;
        this.cola = cola;
        this.empleado = empleado;
    }


    public void IniciarLlamada() {
        System.out.println("Operador " + this.nombre + " Inicia la llamada");
    }

    public void FinalizarLamada() {
        System.out.println("Operador " + this.nombre + " Finaliza Llamada");
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
                this.empleado.add(new Operador(this.nombre, this.cola, this.empleado));
            }
    }
}
