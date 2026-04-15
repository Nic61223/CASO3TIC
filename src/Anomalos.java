
import java.util.ArrayList;

public class Anomalos {

    private final ArrayList<Evento> cola;
    private boolean produccionFinalizada;
    public ArrayList<Evento> eventosGestionados;

    public Anomalos() {
        this.cola = new ArrayList<>();
        this.produccionFinalizada = false;
        this.eventosGestionados = new ArrayList<>();
    }

    public synchronized void producir(Broker broker, Evento evento) {
        cola.add(evento);
        Thread.yield();

    }

    public Evento consumir() {
        while (true) {
            synchronized (this) {
                if (!cola.isEmpty()) {
                    return cola.remove(0);
                }
                if (produccionFinalizada) {
                    return null;
                }
            }
            Thread.yield();
        }
    }

    public synchronized boolean hayPendientes() {
        return !cola.isEmpty();
    }

    public synchronized void finalizarProduccion() {
        produccionFinalizada = true;
    }

    public synchronized boolean isProduccionFinalizada() {
        return produccionFinalizada;
    }

    public synchronized void reevaluar(Evento evento) {
        eventosGestionados.add(evento);
    }

    public ArrayList<Evento> getEventosGestionados() {
        return eventosGestionados;
    }

    public synchronized Evento sacarReevaluado() {
        if (eventosGestionados.isEmpty()) {
            return null;
        }
        return eventosGestionados.remove(0);
    }

    public synchronized void recibirReevaluado(Evento evento) {
        eventosGestionados.add(evento);
        System.out.println("Broker recibio evento reevaluado como normal: " + evento.identificador);
    }
}
