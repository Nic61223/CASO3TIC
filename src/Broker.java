
import java.util.ArrayList;

public class Broker extends Thread {

    public MonitorSensores monitor;
    public final Anomalos buzonAnomalos;
    public ArrayList<Evento> eventos_normales;
    public ArrayList<Evento> eventos_anomalos;
    public MonitorNormales monitorNormales;
    public boolean faltan;

    public Broker(MonitorSensores monitor, Anomalos buzonAnomalos, MonitorNormales monitorNormales) {
        this.monitor = monitor;
        this.buzonAnomalos = buzonAnomalos;
        this.eventos_normales = new ArrayList<>();
        this.eventos_anomalos = new ArrayList<>();
        this.monitorNormales = monitorNormales;
        this.faltan = true;
    }

    @Override
    public void run() {
        try {
            while (monitor.consumir(this)) {
                Thread.yield();

            }
        } catch (Exception e) {
            System.err.println("Error en Broker: " + e.getMessage());
            e.printStackTrace();
        } finally {
            buzonAnomalos.finalizarProduccion();
            monitorNormales.finalizarProduccion();
        }
    }

    public ArrayList<Evento> getEventos_normales() {
        return eventos_normales;
    }

    public void setEventos_normales(ArrayList<Evento> eventos_normales) {
        this.eventos_normales = eventos_normales;
    }

    public synchronized void procesarEvento(Evento evento) {
        if (evento.isEs_anomalo()) {
            eventos_anomalos.add(evento);
            buzonAnomalos.producir(evento);
        } else {
            eventos_normales.add(evento);
            monitorNormales.producir(evento);
        }

    }

    public synchronized void recibirReevaluado(Evento evento) {
        eventos_normales.add(evento);
        monitorNormales.producir(evento);
        System.out.println("Broker recibio evento reevaluado: " + evento.identificador);
    }

}
