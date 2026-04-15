
public class Sensor extends Thread {

    public int identificador;
    public MonitorSensores monitor;
    public int faltan;
    public int num_eventos;

    public Sensor(int identificador, MonitorSensores monitor, int faltan) {
        this.identificador = identificador;
        this.monitor = monitor;
        this.faltan = faltan * identificador;
        this.num_eventos = 0;
    }

    @Override
    public void run() {
        while (faltan > 0) {
            if (!monitor.producir(this)) {
                break;
            }
            Thread.yield();
        }
    }

    public int getIdentificador() {
        return identificador;
    }

    public void setFaltan(int faltan) {
        this.faltan = faltan;
    }

    public int getNum_eventos() {
        return num_eventos;
    }
}
