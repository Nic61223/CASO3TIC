
import java.util.ArrayList;

public class Broker extends Thread {

    public MonitorSensores monitor;
    public final Anomalos buzonAnomalos;
    public ArrayList<Evento> eventos_normales;
    public ArrayList<Evento> eventos_anomalos;

    public Broker(MonitorSensores monitor, Anomalos buzonAnomalos) {
        this.monitor = monitor;
        this.buzonAnomalos = buzonAnomalos;
        this.eventos_normales = new ArrayList<>();
        this.eventos_anomalos = new ArrayList<>();
    }

    @Override
    public void run() {
        try {
            while (monitor.consumir(this)) {
                Thread.yield();
            }
        } catch (Exception e) {
            System.err.println("e");
        } finally {
            buzonAnomalos.finalizarProduccion();
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
            buzonAnomalos.producir(this, evento);
        } else {
            eventos_normales.add(evento);
        }

    }

    public synchronized void recibirReevaluado(Evento evento) {
        eventos_normales.add(evento);
        System.out.println("Broker recibio evento reevaluado: " + evento.identificador);
    }

}
