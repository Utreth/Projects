package poo;

public class Paquete implements Enviable {

    @Override
    public double getCosto() {
        
        return Math.random() * 50 + 10;
    }

    @Override
    public String getInfo() {
        
        return "Esto es el envio de un paquete de AA para BB";
    }

    
}
