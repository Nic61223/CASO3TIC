
import java.util.random.RandomGenerator;

public class MonitorSensores {

    public Buzon buzon;
    public int procesados;
    public int ns;
    public int total_eventos;
    private int generados;

    public MonitorSensores(Buzon buzon, int ns, int total_eventos) {
        this.buzon = buzon;
        this.procesados = 0;
        this.ns = ns;
        this.total_eventos = total_eventos;
        this.generados = 0;
    }

    public boolean consumir(Broker broker) {
        while (true) {
            Evento evento = null;
            synchronized (this) {
                if (procesados >= total_eventos) {
                    return false;
                }
                if (!buzon.getEventos().isEmpty()) {
                    evento = buzon.getEventos().removeFirst();
                    procesados++;
                }
            }

            if (evento == null) {
                Thread.yield();
                continue;
            }

            int min = 0;
            int max = 200;
            int n = (int) (Math.random() * (max - min + 1)) + min;
            if (n % 8 == 0) {
                evento.setEs_anomalo(true);
            }
            broker.procesarEvento(evento);
            return true;
        }
    }

    public synchronized boolean producir(Sensor sensor) {
        if (generados >= total_eventos) {
            return false;
        }
        int rand_num = RandomGenerator.getDefault().nextInt(1, ns);
        Evento evento = new Evento(this, sensor.getIdentificador() + "-" + sensor.getNum_eventos(), rand_num);
        sensor.setFaltan(sensor.faltan - 1);
        sensor.num_eventos++;
        generados++;
        buzon.getEventos().add(evento);
        return true;

    }

    public Buzon getBuzon() {
        return buzon;
    }

    public void setBuzon(Buzon buzon) {
        this.buzon = buzon;
    }

}
