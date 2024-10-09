package poo;

import java.util.Locale;

import org.json.JSONObject;

import io.javalin.Javalin;
import poo.helpers.Utils;
import poo.model.Cliente;
import poo.services.ClienteService;
import poo.services.Service;

public class AppPruebasHTTP {
    public static void main(String[] args) throws Exception {
        int port = 7070;

        // esencial para estandarizar el formato monetario con separador de punto decimal, no con coma
        Locale.setDefault(Locale.of("es_CO"));

        Service<Cliente> clienteService = new ClienteService();

        Javalin
                .create(/*config*/)
                // Agregar clientes (Create)
                .post(
                        "/cliente",
                        ctx -> {
                            JSONObject response = clienteService.add(ctx.body());
                            ctx.json(response.toString());
                        })
                // Listar clientes (Read)
                .get(
                        "/cliente",
                        ctx -> {
                            JSONObject response = clienteService.getAll();
                            ctx.json(response.toString());
                        })
                // Obtener los datos de un cliente por posición (Read)
                .get(
                        "/cliente/{param}",
                        ctx -> {
                            String arg = ctx.pathParam("param");
                            JSONObject response;

                            if (arg.matches("-?\\d+")) {
                                // si es un número en base 10, buscar por posición en la lista
                                int i = Integer.parseInt(arg, 10);
                                response = clienteService.get(i);
                            } else {
                                // en caso contrario, buscar por ID
                                response = clienteService.get(arg);
                            }
                            ctx.json(response.toString());
                        })
                // Actualizar los datos de un cliente, dado su ID (Update)
                .patch(
                        "/cliente/{param}",
                        ctx -> {
                            String id = ctx.pathParam("param");
                            String newData = ctx.body();
                            ctx.json(clienteService.update(id, newData).toString());
                        })
                // Eliminar un cliente
                .delete(
                        "/cliente/{param}",
                        ctx -> ctx.json(clienteService.remove(ctx.pathParam("param"))))
                .exception(
                        Exception.class,
                        (e, ctx) -> {
                            Utils.printStackTrace(e);
                            String error = Utils.keyValueToStrJson("message", e.getMessage(), "request", ctx.fullUrl());
                            ctx.json(error).status(400);
                        })
                .start(port);

    }
}
