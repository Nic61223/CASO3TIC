
import java.util.random.RandomGenerator;

public class Servidores extends Thread {

    public final MonitorServidores monitorServidores;
    public int identificador;

    public Servidores(MonitorServidores monitorServidores, int identificador) {
        this.monitorServidores = monitorServidores;
        this.identificador = identificador;
    }

    @Override
    public void run() {
        while (true) {
            Evento evento = monitorServidores.consumir(this);
            if (evento == null) {
                break;
            }
            leerEvento(evento);
            System.out.println("Servidor " + identificador + " procesó evento: " + evento.identificador);
        }
        System.out.println("Servidor " + identificador + " ha terminado de procesar eventos.");
    }

    public void leerEvento(Evento evento) {
        try {
            int rand_num = RandomGenerator.getDefault().nextInt(100, 1000);
            Thread.sleep(rand_num);
        } catch (InterruptedException e) {
            System.err.println("error desde servidor" + e.getMessage());
        }
    }

    public int getIdentificador() {
        return identificador;
    }

}
