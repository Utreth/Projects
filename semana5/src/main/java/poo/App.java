package poo;
import poo.Locomotive;

public class App {
    public static void main(String[] args) {

        Train train1=new Train();
        train1.setId("1010");
        train1.getRailVehicles().add(new Locomotive("HYQ100", "Glacier Express", 8, TypeCoupler.AUTOMATICO, TypeEngine.DIESEL));
        train1.getRailVehicles().add(new GoodWagons("GYT898", "Glacier Express", 8, TypeCoupler.MANUAL, TypeSpeed.A, TypeLoad.ABIERTO));
        System.out.println(train1);

    }
}
