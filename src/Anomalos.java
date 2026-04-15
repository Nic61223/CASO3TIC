
import java.util.ArrayList;

public class Anomalos {

    private final ArrayList<Evento> cola;
    private boolean produccionFinalizada;

    public Anomalos() {
        this.cola = new ArrayList<>();
        this.produccionFinalizada = false;
    }

    public synchronized void producir(Evento evento) {
        cola.add(evento);

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

}
