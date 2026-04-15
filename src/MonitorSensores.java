
import java.util.random.RandomGenerator;

public class MonitorSensores {

    public Buzon buzon;
    public boolean cambioPendiente;
    public int procesados;
    public int ns;
    public int total_eventos;

    public MonitorSensores(Buzon buzon, int ns, int total_eventos) {
        this.buzon = buzon;
        this.cambioPendiente = true;
        this.procesados = 0;
        this.ns = ns;
        this.total_eventos = total_eventos;
    }

    public synchronized void consumir(Broker broker) {
        try {
            if (total_eventos == 0) {
                return;
            }
            while (buzon.getEventos().isEmpty()) {
                wait();
            }
        } catch (Exception e) {
            System.err.println("e");
        }

        int min = 0;
        int max = 200;
        int n = (int) (Math.random() * (max - min + 1)) + min;

        Evento evento = (Evento) buzon.getEventos().removeFirst();
        if (n % 8 == 0) {
            evento.setEs_anomalo(true);
        }
        broker.procesarEvento(evento);
        procesados++;

        notifyAll();

    }

    public synchronized void producir(Sensor sensor) throws InterruptedException {
        int rand_num = RandomGenerator.getDefault().nextInt(1, ns);
        Evento evento = new Evento(this, sensor.getIdentificador() + "-" + sensor.getNum_eventos(), rand_num);
        sensor.setFaltan(sensor.faltan - 1);
        sensor.num_eventos++;
        total_eventos++;
        buzon.getEventos().add(evento);
        notifyAll();

    }

    public Buzon getBuzon() {
        return buzon;
    }

    public void setBuzon(Buzon buzon) {
        this.buzon = buzon;
    }

    public boolean isCambioPendiente() {
        return cambioPendiente;
    }

    public void setCambioPendiente(boolean cambioPendiente) {
        this.cambioPendiente = cambioPendiente;
    }

}
