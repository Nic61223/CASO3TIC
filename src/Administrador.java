
import java.util.ArrayList;
import java.util.random.RandomGenerator;

public class Administrador extends Thread {

    private final Broker broker;
    private final Anomalos buzonAnomalos;
    private final ArrayList<Evento> anomaliasGestionadas;

    public Administrador(Anomalos buzonAnomalos, Broker broker) {
        this.buzonAnomalos = buzonAnomalos;
        this.broker = broker;
        this.anomaliasGestionadas = new ArrayList<>();
    }

    @Override
    public void run() {
        while (true) {
            Evento evento = buzonAnomalos.consumir();
            if (evento != null) {
                gestionarAnomalia(evento);
            }

            if (buzonAnomalos.isProduccionFinalizada() && !buzonAnomalos.hayPendientes()) {
                break;
            }
            Thread.yield();
        }
    }

    private void gestionarAnomalia(Evento evento) {
        int rand_num = RandomGenerator.getDefault().nextInt(0, 20);
        if (rand_num % 4 == 0) {
            evento.setEs_anomalo(false);
            broker.recibirReevaluado(evento);

        } else {
            System.out.println("Administrador no pudo gestionar anomalia: " + evento.identificador);
            return;
        }
        anomaliasGestionadas.add(evento);
        System.out.println("Administrador gestiono anomalia: " + evento.identificador);
    }

    public int totalAnomaliasGestionadas() {
        return anomaliasGestionadas.size();
    }

}
