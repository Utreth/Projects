package poo.model;

import java.util.ArrayList;

public class Computer {
    private String id;
    private TypeComputer type;
    private ArrayList<StorageDevice> storageDevice=new ArrayList<>();
    private ArrayList<ComputerDevice> computerDevice=new ArrayList<>();

    public Computer() {

    }

    public Computer(String id, TypeComputer type, ArrayList<StorageDevice> storageDevice,
            ArrayList<ComputerDevice> computerDevice) {

        this.id = id;
        this.type = type;
        this.storageDevice = storageDevice;
        this.computerDevice = computerDevice;

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
        return computerDevice;
    }

    public void setComputerDevice(ArrayList<ComputerDevice> computerDevice) {
        this.computerDevice = computerDevice;
    }

    public ArrayList<StorageDevice> getStorageDevice() {
        return storageDevice;
    }

    public void setStorageDevice(ArrayList<StorageDevice> storageDevice) {
        this.storageDevice = storageDevice;
    }

    public static ArrayList<ComputerDevice> getDevices(Computer c) {

        return c.getComputerDevice();

    }

    public static ArrayList<StorageDevice> getStorage(Computer c) {

        return c.getStorageDevice();
    }

    public static double getFullFreeCapacity(Computer c, ArrayList<StorageDevice> storageDl) {

        double fullCapacity = 0;

        for (int i = 0; i < storageDl.size(); i++) {

            double usedCapacity = c.getStorageDevice().get(i).getUsedCapacity();
            double freeCapacity = c.getStorageDevice().get(i).getFreeCapacity();
            double suma = usedCapacity + freeCapacity;
            fullCapacity = fullCapacity + suma;

        }
        return fullCapacity;

    }

    @Override
    public String toString() {
        return "Computer [id=" + id + ", type=" + type + ", storageDevice=" + storageDevice + ", computerDevice="
                + computerDevice + "]";
    }

  



}
