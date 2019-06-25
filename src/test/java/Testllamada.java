import Dispatcher.Dispatcher;
import Dispatcher.Llamada;
import Modelos.Cliente;
import Modelos.Director;
import Modelos.Operador;
import Modelos.Supervisor;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
/**
 *
 * @author Luis Fernando Tafur
 */
public class Testllamada {
    
    @Test
    public void testDispatch10llamadas(){
        Llamada llama = new Llamada(10);
        List<Director> director = new ArrayList<>();
        List<Supervisor> supervisor = new ArrayList<>();
        List<Operador> operador = new ArrayList<>();

        director.add(new Director("Luis", llama.getCola(), director));
        supervisor.add(new Supervisor("Danna", llama.getCola(), supervisor));
        operador.add(new Operador("Melissa", llama.getCola(), operador));
        operador.add(new Operador("Laura", llama.getCola(), operador));
        operador.add(new Operador("Fernando", llama.getCola(), operador));

        llama.setDirector(director);
        llama.setOperador(operador);
        llama.setSupervisor(supervisor);

        Dispatcher dispatcher = new Dispatcher(llama.getCola(), operador, supervisor, director, llama.getLlamada());
        dispatcher.dispatchCall();
        Assert.assertEquals(10, llama.getCola().availablePermits());
        
        for (int i = 0; i < 14; i++) {
            Cliente cliente = new Cliente(i, llama.getLlamada());
            cliente.start();
        }
        // the available resouce on the semaphore has to be 0. 10 calls are taken at the same time.
        Assert.assertEquals(0, llama.getCola().availablePermits());
    }

    @Test
    public void testllamadaoperador(){
        Llamada llama = new Llamada(2);
        List<Director> director = new ArrayList<>();
        List<Supervisor> supervisor = new ArrayList<>();
        List<Operador> operador = new ArrayList<>();

        director.add(new Director("Anna", llama.getCola(), director));
        supervisor.add(new Supervisor("Danna", llama.getCola(), supervisor));
        operador.add(new Operador("Melissa", llama.getCola(), operador));
        operador.add(new Operador("Laura", llama.getCola(), operador));
        operador.add(new Operador("Fernando", llama.getCola(), operador));

        llama.setDirector(director);
        llama.setOperador(operador);
        llama.setSupervisor(supervisor);

        Dispatcher dispatcher = new Dispatcher(llama.getCola(), operador, supervisor, director, llama.getLlamada());
        dispatcher.dispatchCall();
        
        Assert.assertEquals(2, llama.getCola().availablePermits());

        for (int i = 0; i < 2; i++) {
            Cliente cliente = new Cliente(i, llama.getLlamada());
            cliente.start();
        }

        // calls were not assigned to directors or supervisors
        Assert.assertEquals(1, llama.getDirector().size());
        Assert.assertEquals(1, llama.getSupervisor().size());
    }

    @Test
    public void testclienteenespera(){
        Llamada llama = new Llamada(2);

        List<Director> director = new ArrayList<>();
        List<Supervisor> supervisor = new ArrayList<>();
        List<Operador> operador = new ArrayList<>();

        operador.add(new Operador("Melissa", llama.getCola(), operador));

        llama.setDirector(director);
        llama.setOperador(operador);
        llama.setSupervisor(supervisor);

        Dispatcher dispatcher = new Dispatcher(llama.getCola(), operador, supervisor, director, llama.getLlamada());
        dispatcher.dispatchCall();

        Assert.assertEquals(2, llama.getCola().availablePermits());

        for (int i = 0; i < 5; i++) {
            Cliente cliente = new Cliente(i, llama.getLlamada());
            cliente.start();
        }

        Assert.assertTrue(llama.getLlamada().size()>0);

    }
}
