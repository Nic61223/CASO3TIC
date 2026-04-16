
public class Clasificador extends Thread {

    private final MonitorNormales monitorNormales;

    public Clasificador(MonitorNormales monitorNormales) {
        this.monitorNormales = monitorNormales;
    }

    public void run() {
        while (true) {
            Evento evento = monitorNormales.consumir();
            if (!monitorNormales.faltan && evento == null && monitorNormales.eventosNormales.estaVacio()) {
                break;
            }
        }

    }
}
