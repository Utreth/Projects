package poo;

import java.util.ArrayList;

public class App {
        public static void main(String[] args) {

                Train tr1 = new Train();
                tr1.setId("1010");
                tr1.getRailVehicles()
                                .add(new Locomotive("HYQ100", "Glacier Express", 16, TypeCoupler.AUTOMATICO,
                                                TypeEngine.DIESEL));
                tr1.getRailVehicles()
                                .add(new Locomotive("YTG900", "Glacier Express", 16, TypeCoupler.AUTOMATICO,
                                                TypeEngine.DIESEL));
                tr1.getRailVehicles()
                                .add(new GoodWagons("GYT898", "Glacier Express", 8, TypeCoupler.MANUAL, TypeSpeed.A,
                                                TypeLoad.ABIERTO));
                tr1.getRailVehicles()
                                .add(new PassangerCar("YUI543", "Passanger Glaciar", 8, TypeCoupler.AUTOMATICO,
                                                TypeSpeed.A, 10, 2));
                tr1.getRailVehicles()
                                .add(new PassangerCar("YUI543", "Passanger Glaciar", 8, TypeCoupler.AUTOMATICO,
                                                TypeSpeed.B, 20, 2));

                Train tr2 = new Train();
                tr2.setId("2020");
                tr2.getRailVehicles().add(new Locomotive("BNI899", "Euros Express", 10, TypeCoupler.AUTOMATICO,
                                TypeEngine.ELECTRICO));
                tr2.getRailVehicles().add(new GoodWagons("DFH765", "Euro Express", 10, TypeCoupler.AUTOMATICO,
                                TypeSpeed.A, TypeLoad.JAULA));
                tr2.getRailVehicles().add(new GoodWagons("CHV123", "Euro Express", 10, TypeCoupler.AUTOMATICO,
                                TypeSpeed.A, TypeLoad.CISTERNA));

                Train tr3 = new Train();
                tr3.setId("4578");
                tr3.getRailVehicles().add(new Locomotive("NBV876", "System Express", 12, TypeCoupler.SEMIPERMANENTE,
                                TypeEngine.VAPOR));
                tr3.getRailVehicles().add(new PassangerCar("LKO543", "System Express", 12, TypeCoupler.SEMIPERMANENTE,
                                TypeSpeed.D, 20, 4));
                tr3.getRailVehicles().add(new GoodWagons("FTE199", "System Express", 12, TypeCoupler.MANUAL,
                                TypeSpeed.N, TypeLoad.PLATAFORMA));
                tr3.getRailVehicles().add(new GoodWagons("QWE765", "System Express", 12, TypeCoupler.MANUAL,
                                TypeSpeed.B, TypeLoad.REFRIGERADO));
                                
                ArrayList<Train> trains = new ArrayList<>();
                ArrayList<Train> isTrains = new ArrayList<>();

                trains.add(tr1);
                trains.add(tr2);
                trains.add(tr3);

                for (Train train : trains) {

                        if (train.isTrain()) {
                                isTrains.add(train);
                        }

                }
                System.out.println(isTrains);

        }

}
