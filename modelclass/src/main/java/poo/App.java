package poo;

import poo.model.Computer;
import poo.model.FlashMemory;
import poo.model.HardDiskDrive;
import poo.model.Keyboard;
import poo.model.Language;
import poo.model.Monitor;
import poo.model.Mouse;
import poo.model.SolidStateDrive;
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
        comp1.getStorageDevice().add(new SolidStateDrive("ADATA", false, 200, 300, 100, "SOLIDO"));
        double full = Computer.getFullCapacity(comp1, comp1.getStorageDevice());
        System.out.println(comp1);
        System.out.println("La capacidad completa de memoria del computador 1 es: " + full + " GB");
        double freeCa = Computer.getFullFreeCapacity(comp1, comp1.getStorageDevice());
        System.out.println("La capacidad libre de memoria del computador 1 es: " + freeCa + " GB");
        double used = Computer.getFullUsedCapacity(comp1, comp1.getStorageDevice());
        System.out.println("La capacidad usada de memoria del computador 1 es: " + used + " GB");

        Computer comp2 = new Computer();
        comp2.setId("2001");
        comp2.setType(TypeComputer.LAPTOP);
        comp2.getComputerDevice().add(new Keyboard("HP", false, true, true, Language.SPANISH));
        comp2.getComputerDevice().add(new Monitor("HP", false, 20));
        comp2.getComputerDevice().add(new Mouse("HP", true, 150, 8));
        comp2.getStorageDevice().add(new HardDiskDrive("ADATA", false, 200, 800, 120, 5400, 2.5));
        double full2 = Computer.getFullCapacity(comp2, comp2.getStorageDevice());
        System.out.println(comp2);
        System.out.println("La capacidad completa de memoria del computador 2 es: " + full2 + " GB");
        double freeCa2 = Computer.getFullFreeCapacity(comp2, comp2.getStorageDevice());
        System.out.println("La capacidad libre de memoria del computador 2 es: " + freeCa2 + " GB");
        double used2= Computer.getFullUsedCapacity(comp2, comp2.getStorageDevice());
        System.out.println("La capacidad libre de memoria del computador 2 es: " + used2 + " GB");

    }
}
