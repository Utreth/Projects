package poo;

import java.util.Arrays;

import io.javalin.Javalin;

public class App {
    public static void main(String[] args) {
        Javalin.create(/* config */)
                .get("/{param}",
                        ctx -> {
                            String param = ctx.pathParam("param");
                            ctx.result("Hola " + param);
                        })
                .get(
                        "/suma/{param}",
                        ctx -> {
                            String param = ctx.pathParam("param");
                            int limite = Integer.parseInt(param, 10);
                            ctx.result("Suma de los " + limite + " primeros naturales: " + sumatoria(limite));
                        })
                .get("/factorial/{param}",
                        ctx -> {
                            String param = ctx.pathParam("param");
                            int limFa = Integer.parseInt(param);
                            ctx.result("El factorial de " + param + " es -> " + factorial(limFa));
                        })
                .get("/fibonacci/{param}", ctx -> {
                    String param = ctx.pathParam("param");
                    int limFibo = Integer.parseInt(param);
                    ctx.result(
                            "La secuencia fibonacci para " + param + " es: " + Arrays.toString(fibonacci(limFibo, 1)));

                })

                .start(7070);
    }

    static int sumatoria(int limite) {
        if (limite < 1) {
            throw new IllegalArgumentException("El limite debe ser mayor o igual a 1");
        }
        int suma = 0;
        for (int i = 1; i <= limite; i++) {
            suma += i;
        }
        return suma;
    }

    static int factorial(int limFa) {
        // throw new IllegalArgumentException("El límite debe ser mayor a 0");
        if (limFa <= 0) {
            throw new IllegalArgumentException("El limite debe ser superior a 0");
        } else if (limFa > 44) {
            throw new IllegalArgumentException("El limite debe ser inferior a 44");
        }
        int num = limFa;
        int resultado = 1;
        for (int i = 1; i <= num; i++) {
            resultado = resultado * i;
        }
        return resultado;
    }

    static int[] fibonacci(int limFibo, int num1) {
        if (limFibo < 1) {
            throw new IllegalArgumentException("El limite debe ser mayor a 1");
        }
        int fibonacci[] = new int[limFibo];
        int fibo = 1;
        int num2 = 0;
        for (int i = 0; i < fibonacci.length; i++) {

            fibonacci[i] = num2;
            fibo = num1 + num2;
            num1 = num2;
            num2 = fibo;
        }
        return fibonacci;
    }
}