
import java.util.ArrayList;
import java.util.List;

public abstract class Buzon {

    protected final ArrayList<Evento> eventos;

    public Buzon() {
        this.eventos = new ArrayList<>();
    }

    public List<Evento> getEventos() {
        return eventos;
    }

}
