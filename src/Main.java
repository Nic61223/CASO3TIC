
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        int ni = 4;
        int num_eventos = 10;
        int nc = 3;
        int ns = 3;
        int tam1 = 10;
        int tam2 = 20;

        System.out.println("=== Iniciando simulacion Caso 3 ===\n");

        Buzon buzonEntrada = new BuzonSinLimite();

        int totalEventosEsperados = 0;
        for (int i = 1; i <= ni; i++) {
            totalEventosEsperados += (num_eventos * i);
        }

        System.out.println("Esperando un total de " + totalEventosEsperados + " eventos.\n");

        MonitorSensores monitorSensores = new MonitorSensores(buzonEntrada, ns, totalEventosEsperados);
        Anomalos buzonAnomalos = new Anomalos();
        MonitorNormales monitorNormales = new MonitorNormales(new BuzonConLimite(tam1));
        MonitorServidores monitorServidores = new MonitorServidores(tam2, nc);
        Broker broker = new Broker(monitorSensores, buzonAnomalos, monitorNormales);
        Administrador administrador = new Administrador(buzonAnomalos, broker);

        ArrayList<Sensor> sensores = new ArrayList<>();
        for (int i = 1; i <= ni; i++) {
            sensores.add(new Sensor(i, monitorSensores, num_eventos));
        }

        ArrayList<Clasificador> clasificadores = new ArrayList<>();
        for (int i = 1; i <= nc; i++) {
            Clasificador c = new Clasificador(monitorNormales, monitorServidores);
            c.setName("Clasificador-" + i);
            clasificadores.add(c);
        }

        ArrayList<Servidores> servidores = new ArrayList<>();
        for (int i = 1; i <= ns; i++) {
            Servidores s = new Servidores(monitorServidores, i);
            s.setName("Servidor-" + i);
            servidores.add(s);
        }

        administrador.start();
        broker.start();

        for (Servidores s : servidores) {
            s.start();
        }

        for (Clasificador c : clasificadores) {
            c.start();
        }

        for (Sensor s : sensores) {
            s.start();
        }

        System.out.println("Hilos lanzados exitosamente...");

        try {
            for (Sensor s : sensores) {
                s.join();
            }

            broker.join();
            administrador.join();

            for (Clasificador c : clasificadores) {
                c.join();
            }

            for (Servidores s : servidores) {
                s.join();
            }

            System.out.println("\n=== Simulacion completada ===");
            System.out.println("Eventos inicialmente normales o normalizados: " + broker.eventos_normales.size());
            System.out.println("Eventos detectados inicialmente como anomalos: " + broker.eventos_anomalos.size());
            System.out.println("Anomalias gestionadas por Administrador: " + administrador.totalAnomaliasGestionadas());

        } catch (InterruptedException e) {
            System.err.println("Error en main: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
