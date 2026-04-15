
import java.util.ArrayList;

public class Broker extends Thread {

    public MonitorSensores monitor;
    public ArrayList<Evento> eventos_normales;
    public ArrayList<Evento> eventos_anomalos;

    public Broker(MonitorSensores monitor) {
        this.monitor = monitor;
        this.eventos_normales = new ArrayList();
        this.eventos_anomalos = new ArrayList();
    }

    @Override
    public void run() {
        try {

            while (true) {
                if (monitor.total_eventos == monitor.procesados) {
                    break;
                }
                monitor.consumir(this);

            }

        } catch (Exception e) {
            System.err.println("e");
        }

    }

    public ArrayList<Evento> getEventos_normales() {
        return eventos_normales;
    }

    public void setEventos_normales(ArrayList<Evento> eventos_normales) {
        this.eventos_normales = eventos_normales;
    }

    public void procesarEvento(Evento evento) {
        if (evento.isEs_anomalo()) {
            eventos_anomalos.add(evento);
        } else {
            eventos_normales.add(evento);
        }
    }
}
