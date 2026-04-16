
public class MonitorNormales {

    public final BuzonConLimite eventosNormales;
    public boolean faltan;

    public MonitorNormales(BuzonConLimite eventosNormales) {
        this.eventosNormales = eventosNormales;
        this.faltan = true;
    }

    public void producir(Evento evento) {
        while (true) {
            synchronized (this) {
                if (!eventosNormales.estaLleno()) {
                    eventosNormales.agragarEvento(evento);
                    notifyAll();
                    return;
                }
            }
            Thread.yield();
        }
    }

    public synchronized Evento consumir() {
        while (eventosNormales.estaVacio()) {
            if (!faltan) {
                return null;
            }
            try {
                wait();
            } catch (InterruptedException e) {
                System.err.println("error desde monitorNormales" + e.getMessage());
            }
        }
        Evento evento = eventosNormales.getEventos().remove(0);
        return evento;
    }

    public synchronized void finalizarProduccion() {
        faltan = false;
        notifyAll();

    }
}
