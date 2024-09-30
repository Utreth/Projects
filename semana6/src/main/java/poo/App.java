package poo;

public class App {
    public static void main(String[] args) {
        Enviable sobre = new Sobre();
        Enviable paquete = new Paquete();
        Enviable[] enviables = { sobre, paquete };

        for (Enviable enviable : enviables) {
            System.out.println(enviable.getInfo());
            System.out.println(enviable.getCosto());
            System.out.println("-".repeat(20));
        }
    }
}
