
public class MonitorServidores {

    public boolean faltan;
    public BuzonConLimite eventosPorClasificar;
    public int tam2;
    private int clasificadoresActivos;

    public MonitorServidores(int tam2, int nc) {
        this.eventosPorClasificar = new BuzonConLimite(tam2);
        this.faltan = true;
        this.clasificadoresActivos = nc;
    }

    public synchronized void producir(Evento evento) {
        while (eventosPorClasificar.estaLleno()) {
            try {
                wait();
            } catch (Exception e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        eventosPorClasificar.getEventos().add(evento);
        notifyAll();
    }

    public synchronized Evento consumir(Servidores servidor) {
        while (true) {
            while (eventosPorClasificar.estaVacio()) {
                if (!faltan) {
                    return null;
                }
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return null;
                }
            }

            Evento primero = eventosPorClasificar.getEventos().get(0);

            if (primero.getTipo_destino() == servidor.getIdentificador()) {
                Evento evento = eventosPorClasificar.getEventos().remove(0);
                notifyAll();
                return evento;
            } else {
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return null;
                }
            }

        }
    }

    public synchronized void registrarFinClasificador() {
        clasificadoresActivos--;
        if (clasificadoresActivos == 0) {
            faltan = false;
            notifyAll();
        }
    }
}
