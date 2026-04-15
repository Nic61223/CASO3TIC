
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        int ni = 4;
        int num_eventos = 10;
        int ns = 3;

        System.out.println("=== Iniciando Simulacion Caso 3: Comunicacion Sensores-Broker ===\n");

        Buzon buzonEntrada = new BuzonSinLimite();

        int totalEventosEsperados = 0;
        for (int i = 1; i <= ni; i++) {
            totalEventosEsperados += (num_eventos * i);
        }

        System.out.println("Esperando un total de " + totalEventosEsperados + " eventos.\n");

        MonitorSensores monitor = new MonitorSensores(buzonEntrada, ns, totalEventosEsperados);
        Anomalos buzonAnomalos = new Anomalos();
        Broker broker = new Broker(monitor, buzonAnomalos);
        Administrador administrador = new Administrador(buzonAnomalos, broker);
        administrador.start();
        broker.start();

        ArrayList<Sensor> sensores = new ArrayList<>();
        for (int i = 1; i <= ni; i++) {
            Sensor newSensor = new Sensor(i, monitor, num_eventos);
            sensores.add(newSensor);
            newSensor.start();
        }

        System.out.println("Hilos lanzados exitosamente...");

        try {
            for (Sensor sensor : sensores) {
                sensor.join();
            }
            broker.join();
            administrador.join();
            System.out.println("\n=== Simulacion completada ===");
            System.out.println("Total de eventos procesados: "
                    + (broker.eventos_normales.size() + broker.eventos_anomalos.size()));
            System.out.println("Total de anomalias gestionadas por Administrador: "
                    + administrador.totalAnomaliasGestionadas());
        } catch (InterruptedException e) {
            System.err.println("Error esperando al Broker: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
