package poo;

public class App {
    public static void main(String[] args) {
        Task task = new Task();

        task.performTask((int a, int b) -> {
            String result = String.format("%d = %d + %d", a + b, a, b);
            System.out.println(result);
        });

        task.performTask((int a, int b) -> {
            String result = String.format("%d = %d x %d", a * b, a, b);
            System.out.println(result);
        });

        task.performTask((int a, int b) -> {
            int min = Math.min(a, b);
            int max = Math.max(a, b);
            System.out.printf("Naturales entre %d y %d: [ ", min, max);
            for (int i = min; i <= max; i++) {
                System.out.printf("%d ", i);
            }
            System.out.println("]");
        });
    }
}
