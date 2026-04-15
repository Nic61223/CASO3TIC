
public class Evento {

    public String identificador;
    public boolean es_anomalo;
    public int num_aleatoreo;

    public Evento(MonitorSensores monitor, String identificador, int num_aleaoreo) {
        this.identificador = identificador;
        this.es_anomalo = false;
        this.num_aleatoreo = num_aleaoreo;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public boolean isEs_anomalo() {
        return es_anomalo;
    }

    public void setEs_anomalo(boolean es_fin) {
        this.es_anomalo = es_fin;
    }

}
