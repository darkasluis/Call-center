package Vista;

import Dispatcher.Llamada;

/**
 *
 * @author Luis Fernando Tafur
 */
public class Iniciador {
    public static void main(String[] args) {
        Llamada llama = new Llamada(8);
        llama.Iniciarllamadas();
    }
}
