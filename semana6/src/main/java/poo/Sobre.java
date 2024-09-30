package poo;

public class Sobre implements Enviable {

    @Override
    public double getCosto() {
        
        return Math.random() * 100;
    }

    @Override
    public String getInfo() {
        
        return "Esto es el envio de un sobre de NN para MM";
    }

    
    
}
