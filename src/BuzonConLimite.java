
public class BuzonConLimite extends Buzon {

    public int capacidad;

    public BuzonConLimite(int capacidad) {
        super();
        this.capacidad = capacidad;
    }

    public boolean estaLleno() {
        return eventos.size() >= capacidad;
    }

    public void agragarEvento(Evento evento) {
        eventos.add(evento);
    }

    public boolean estaVacio() {
        return eventos.isEmpty();
    }

}
