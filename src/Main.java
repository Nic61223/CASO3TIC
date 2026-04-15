
import java.util.ArrayList;

public class Main {

    public static void main(int ni, int num_eventos, int nc, int ns, int tam1, int tam2) {
        System.out.println("=== EJEMPLO DE ESPERA ACTIVA ===\n");
        //genero monitor para  empezar ejecucion
        Buzon buzon = new Buzon(-1);
        int totalEventosEsperados = 0;

        for (int i = 1; i <= ni; i++) {
            totalEventosEsperados += (num_eventos * i);
        }

        MonitorSensores monitor = new MonitorSensores(buzon, ns, totalEventosEsperados);
        Broker broker = new Broker(monitor);
        broker.start();

        // se generan todos los sensores con sus eventos
        ArrayList<Sensor> sensores = new ArrayList<Sensor>();
        for (int i = 0; i < ni; i++) {
            Sensor newSensor = new Sensor(i + 1, monitor, ns);
            sensores.add(newSensor);
            newSensor.start();
        }

        //se inicializa el broker y se ejecuta para que quede en wait()
    }
}
