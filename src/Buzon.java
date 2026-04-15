
import java.util.ArrayList;
import java.util.List;

public abstract class Buzon {

    public ArrayList<Evento> eventos;

    public Buzon() {
        this.eventos = new ArrayList<>();
    }

    public List<Evento> getEventos() {
        return eventos;
    }

}
