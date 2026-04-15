
public class Evento {

    public String identificador;
    public boolean es_anomalo;
    public int tipo_destino;

    public Evento(MonitorSensores monitor, String identificador, int rand_num) {
        this.identificador = identificador;
        this.es_anomalo = false;
        this.tipo_destino = rand_num;
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
