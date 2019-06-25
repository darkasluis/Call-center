package Modelos;

import static java.lang.Thread.sleep;
import java.util.LinkedList;
import javax.swing.JOptionPane;

/**
 *
 * @author Luis Fernando Tafur
 */
public class Cliente extends Thread{
    private final int id;
    private final LinkedList<Integer> llamada;

    public Cliente(Integer id, LinkedList<Integer> llamada){
        this.id = id;
        this.llamada = llamada;
    }

    @Override
    @SuppressWarnings("SleepWhileInLoop")
    public void run() {
        int tiempo = (int) (Math.random() * (15 - 9)) + 9;

        while(true){
            if(!llamada.contains(this.id)){
                System.out.println("Cliente "+ this.id +" Haciendo llamada");
                llamada.add(this.id);
                try {
                    sleep(tiempo * 1000);
                } catch (InterruptedException e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        }
    }
}
