package poo;

import java.util.ArrayList;

import poo.model.Computer;
import poo.model.FlashMemory;
import poo.model.HardDiskDrive;
import poo.model.Keyboard;
import poo.model.TypeLanguage;
import poo.model.Monitor;
import poo.model.Mouse;
import poo.model.SolidStateDrive;
import poo.model.TypeComputer;

public class App {
    public static void main(String[] args) {

        Computer comp1 = new Computer();

        comp1.setId("1001");
        comp1.setType(TypeComputer.DESKTOP);
        comp1.getComputerDevice().add(new Keyboard("ASUS", false, false, false, TypeLanguage.ENGLISH));
        comp1.getComputerDevice().add(new Keyboard("ASUS", false, false, false, TypeLanguage.ENGLISH));
        comp1.getComputerDevice().add(new Monitor("ASUS", false, 15));
        comp1.getComputerDevice().add(new Monitor("SAMSUNG", false, 30));
        comp1.getComputerDevice().add(new Monitor("DELL", false, 30));
        comp1.getComputerDevice().add(new Mouse("ASUS", false, 100, 4));
        comp1.getComputerDevice().add(new Mouse("DELL", true, 200, 2));
        comp1.getComputerDevice().add(new FlashMemory("Kingstown", false, 50, 100, 10, "USB1"));
        comp1.getComputerDevice().add(new SolidStateDrive("HGST", false, 200, 300, 100, "SOLIDO"));
        comp1.getComputerDevice().add(new SolidStateDrive("HGST2", false, 200, 700, 100, "SOLIDO"));
        comp1.getComputerDevice().add(new SolidStateDrive("HGST3", false, 200, 200, 100, "SOLIDO"));
        comp1.getComputerDevice().add(new SolidStateDrive("HGST3", false, 200, 1800, 100, "SOLIDO"));

        Computer comp2 = new Computer();
        comp2.setId("6700");
        comp2.setType(TypeComputer.LAPTOP);
        comp2.getComputerDevice().add(new Keyboard("HP", false, true, true, TypeLanguage.SPANISH));
        comp2.getComputerDevice().add(new Monitor("HP", false, 20));
        comp2.getComputerDevice().add(new Mouse("HP", true, 150, 8));
        comp2.getComputerDevice().add(new HardDiskDrive("ADATA", false, 200, 800, 120, 5400, 2.5));

        Computer comp3 = new Computer();
        comp3.setId("6754");
        comp3.setType(TypeComputer.DESKTOP);
        comp3.getComputerDevice().add(new Keyboard("LENOVO", false, true, true, TypeLanguage.SPANISH));
        comp3.getComputerDevice().add(new Monitor("LENOVO", false, 20));
        comp3.getComputerDevice().add(new Mouse("LENOVO", true, 150, 8));
        comp3.getComputerDevice().add(new HardDiskDrive("ADATA", false, 200, 800, 120, 5400, 2.5));
        comp3.getComputerDevice().add(new FlashMemory("ATOMOS", false, 60, 100, 90, "USB2"));

        Computer comp4 = new Computer();
        comp4.setId("5657");
        comp4.setType(TypeComputer.LAPTOP);
        comp4.getComputerDevice().add(new Keyboard("SAMSUNG", false, true, true, TypeLanguage.SPANISH));
        comp4.getComputerDevice().add(new Monitor("SAMSUNG", false, 20));
        comp4.getComputerDevice().add(new Mouse("SAMSUNG", true, 150, 8));
        comp4.getComputerDevice().add(new HardDiskDrive("WESTERN", false, 200, 800, 120, 5400, 2.5));

        Computer comp5 = new Computer();
        comp5.setId("4675");
        comp5.setType(TypeComputer.LAPTOP);
        comp5.getComputerDevice().add(new Keyboard("DELL", false, true, true, TypeLanguage.SPANISH));
        comp5.getComputerDevice().add(new Monitor("DELL", false, 20));
        comp5.getComputerDevice().add(new Mouse("SAMSUNG", false, 100, 4));
        comp5.getComputerDevice().add(new HardDiskDrive("ADATA", false, 200, 800, 120, 5400, 2.5));
        comp5.getComputerDevice().add(new SolidStateDrive("ATOMOS", false, 600, 1400, 500, "SOLIDO"));

        Computer comp6 = new Computer();
        comp6.setId("0919");
        comp6.setType(TypeComputer.DESKTOP);
        comp6.getComputerDevice().add(new Monitor("ASUS", false, 15));
        comp6.getComputerDevice().add(new FlashMemory("HAMA", false, 50, 100, 10, "USB1"));
        comp6.getComputerDevice().add(new SolidStateDrive("SAMSUNG", false, 300, 1700, 100, "SOLIDO"));

        ArrayList<Computer> computers = new ArrayList<>();
        ArrayList<Computer> computersOk = new ArrayList<>();

        computers.add(comp1);
        computers.add(comp2);
        computers.add(comp3);
        computers.add(comp4);
        computers.add(comp5);
        computers.add(comp6);

        for (Computer computer : computers) {
            if (computer.okRestriccions()) {

                computersOk.add(computer);

            }

            if (computer.isComputer()) {

            }
        }

        System.out.println(computersOk);

    }
}
