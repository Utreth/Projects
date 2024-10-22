package poo;

import java.util.Random;

public class Task {
    public void performTask(Callback callback) {
        int x = 0;
        int y = 0;
        // Simular una tarea que tarda tiempo
        try {
            Thread.sleep(2000);
            System.out.println("Procesando. Por favor espere...");
            Random random = new Random();
            int min = 1;
            int max = 20;
            x = random.nextInt(max - min + 1) + min;
            y = random.nextInt(max - min + 1) + min;
        } catch (InterruptedException e) {
            e.printStackTrace(System.out);
        }

        // Llamar al callback con el resultado
        callback.onComplete(x, y);
    }
}
