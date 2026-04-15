
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        // --- PARÁMETROS DE CONFIGURACIÓN (Basados en el enunciado) ---
        int ni = 4;           // Número de sensores
        int num_eventos = 10; // Valor base para la generación de eventos
        int ns = 3;           // Número de servidores (para el tipo de evento)
        // Capacidad buzón consolidación

        System.out.println("=== Iniciando Simulación Caso 3: Comunicación Sensores-Broker ===\n");

        // 1. Inicializar el Buzón de Entrada (Ilimitado -> Capacidad -1)
        Buzon buzonEntrada = new Buzon(-1);

        // 2. Calcular el total de eventos según la guía: Sumatoria de (base * id)
        // Esto es necesario para que el Broker sepa cuándo detenerse
        int totalEventosEsperados = 0;
        for (int i = 1; i <= ni; i++) {
            totalEventosEsperados += (num_eventos * i);
        }
        System.out.println("Esperando un total de " + totalEventosEsperados + " eventos.\n");

        // 3. Crear el Monitor que coordina la producción y el consumo
        MonitorSensores monitor = new MonitorSensores(buzonEntrada, ns, totalEventosEsperados);

        // 4. Crear e iniciar el hilo del Broker
        Broker broker = new Broker(monitor);
        broker.start();

        // 5. Crear e iniciar los hilos de los Sensores
        ArrayList<Sensor> sensores = new ArrayList<>();
        for (int i = 1; i <= ni; i++) {
            // Se le pasa i como identificador y num_eventos como base
            Sensor newSensor = new Sensor(i, monitor, num_eventos);
            sensores.add(newSensor);
            newSensor.start();
        }

        // El Main termina su ejecución, pero los hilos Sensor y Broker continúan
        System.out.println("Hilos lanzados exitosamente...");
    }
}
