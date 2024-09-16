package poo;

import poo.model.Computer;
import poo.model.FlashMemory;
import poo.model.Keyboard;
import poo.model.Language;
import poo.model.Monitor;
import poo.model.Mouse;
import poo.model.TypeComputer;

public class App {
    public static void main(String[] args) {

        Computer comp1 = new Computer();

        comp1.setId("1001");
        comp1.setType(TypeComputer.DESKTOP);
        comp1.getComputerDevice().add(new Keyboard("Asus", false, false, false, Language.ENGLISH));
        comp1.getComputerDevice().add(new Monitor("Asus", false, 15));
        comp1.getComputerDevice().add(new Mouse("Asus", false, 100, 4));
        comp1.getStorageDevice().add(new FlashMemory("Kingstown", false, 50, 100, 10, "USB1"));
        double full = Computer.getFullFreeCapacity(comp1, comp1.getStorageDevice());
        System.out.println(comp1);
        System.out.println("La capacidad completa es: " + full);



    }
}
