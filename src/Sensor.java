
import java.util.ArrayList;



public class Sensor extends Thread {
    public int identificador;
    public int num_eventos;
    public ArrayList<Evento> eventos;


    public Sensor(int identificador, int num_eventos, Broker broker){
        this.identificador = identificador;
        this.num_eventos = num_eventos;
    }
    

    @Override
    public synchronized void  run(){
        generarEventos(num_eventos);
    }

    public void generarEventos(int num_eventos){

        for (int i = 0; i< num_eventos; i++){
            Evento evento = new Evento(i+1,identificador);
            eventos.add(evento);}
    }


}

