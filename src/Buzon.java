
import java.util.ArrayList;
import java.util.List;

public class Buzon {

    public int capacidad;
    public ArrayList<Evento> eventos;

    public Buzon(int capacidad) {
        this.capacidad = capacidad;
        this.eventos = new ArrayList();
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public List getEventos() {
        return eventos;
    }

}
