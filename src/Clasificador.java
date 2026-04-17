
public class Clasificador extends Thread {

    private final MonitorNormales monitorNormales;
    public final MonitorServidores monitorServidores;

    public Clasificador(MonitorNormales monitorNormales, MonitorServidores monitorServidores) {
        this.monitorNormales = monitorNormales;
        this.monitorServidores = monitorServidores;
    }

    @Override
    public void run() {
        while (true) {
            Evento evento = monitorNormales.consumir();
            if (evento == null) {
                monitorServidores.registrarFinClasificador();
                break;
            }
            monitorServidores.producir(evento);
        }
    }
}
