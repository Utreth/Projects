package poo.model;

import java.util.ArrayList;

import org.json.JSONObject;

public class Computer {
    private String id;
    private TypeComputer type;
    private ArrayList<ComputerDevice> computerDevices = new ArrayList<>();

    public Computer() {
        
    }

    public Computer(String id, TypeComputer type, ArrayList<ComputerDevice> computerDevices) {

        this.id = id;
        this.type = type;
        this.computerDevices = computerDevices;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TypeComputer getType() {
        return type;
    }

    public void setType(TypeComputer type) {
        this.type = type;
    }

    public ArrayList<ComputerDevice> getComputerDevice() {
        return computerDevices;
    }

    public void setComputerDevice(ArrayList<ComputerDevice> computerDevices) {
        this.computerDevices = computerDevices;
    }

    public double getFreeCap() {

        double fullFree = 0F;

        for (ComputerDevice i : computerDevices) {

            if (i instanceof StorageDevice) {

                double freeCap = ((StorageDevice) i).getFreeCapacity();
                fullFree += freeCap;
            }

        }
        return fullFree;
    }

    public double getUsedCap() {

        double fullUsed = 0F;

        for (ComputerDevice j : computerDevices) {

            if (j instanceof StorageDevice) {

                double usedCap = ((StorageDevice) j).getUsedCapacity();
                fullUsed += usedCap;
            }

        }
        return fullUsed;
    }

    public double getFullcap() {

        return getFreeCap() + getUsedCap();
    }

    public boolean okRestriccions() {

        int monitor = 0;
        int mouse = 0;
        int flashMem = 0;
        int ssd = 0;
        boolean comprobar = false;

        for (ComputerDevice devices : computerDevices) {

            if (devices instanceof Monitor) {
                monitor += 1;
            }
            if (devices instanceof Mouse) {
                mouse += 1;
            }
            if (devices instanceof FlashMemory) {
                flashMem += 1;
            }
            if (devices instanceof SolidStateDrive) {
                ssd += 1;
            }

        }
        if (monitor >= 1 && mouse == 0 && flashMem == 1 && ssd == 1) {

            comprobar = true;

        }

        return comprobar;

    }

    public boolean isComputer() {

        int mouse = 0;
        int monitor = 0;
        int flashMem = 0;
        int hardDisk = 0;
        int solidDrive = 0;
        int keyboard = 0;
        boolean comprobar2 = false;
        String errores = "";

        for (ComputerDevice devices : computerDevices) {

            if (devices instanceof Mouse) {
                mouse += 1;
            }
            if (devices instanceof Monitor) {
                monitor += 1;
            }
            if (devices instanceof FlashMemory) {
                flashMem += 1;
            }
            if (devices instanceof HardDiskDrive) {
                hardDisk += 1;
            }
            if (devices instanceof SolidStateDrive) {
                solidDrive += 1;
            }
            if (devices instanceof Keyboard) {
                keyboard += 1;
            }
        }

        if (mouse <= 1 && monitor <= 2
                && (flashMem <= 4 || hardDisk <= 4 || solidDrive <= 4 || flashMem + hardDisk + solidDrive <= 4)
                && keyboard <= 1) {
            comprobar2 = true;
        }

        if (mouse > 1) {
            errores += "\nEl computador no puede tener mas de 1 mouse.\n";
        }

        if (monitor > 2) {
            errores += "El computador no puede tener mas de 2 monitores.\n";
        }

        if (flashMem > 4 || hardDisk > 4 || solidDrive > 4 || flashMem + hardDisk + solidDrive > 4) {
            errores += "El computador no puede tener mas de 4 dispositivos de memoria.\n";
        }

        if (keyboard > 1) {
            errores += "El computador no puede tener mas de 1 teclado.\n";
        }
        if (!errores.isEmpty()) {
            throw new IllegalArgumentException(errores);
        }

        return comprobar2;

    }

    @Override
    public String toString() {
        return (new JSONObject(this)).toString(2);
    }

}
