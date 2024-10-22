package poo.helpers;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import static io.javalin.apibuilder.ApiBuilder.delete;
import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.patch;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;
import io.javalin.http.Context;
import poo.services.Service;

public class Controller<T> {
    private final String currentPath;

    public Controller(final Service<T> service) {
        currentPath = service.getDataType().getSimpleName();

        path(
                currentPath.toLowerCase(),
                () -> {
                    get("", ctx -> response(ctx, service.getAll()));

                    get(
                            "/{param}",
                            ctx -> {
                                String arg = ctx.pathParam("param");
                                if (arg.matches("-?\\d+")) {
                                    // si es un número en base 10, buscar por índice
                                    int i = Integer.parseInt(arg, 10);
                                    response(ctx, service.get(i));
                                } else {
                                    response(ctx, service.get(arg));
                                }
                            });

                    post("", ctx -> response(ctx, service.add(ctx.body())));

                    patch( // también hubiera podido ser put
                            "/{param}",
                            ctx -> {
                                String id = ctx.pathParam("param");
                                String newData = ctx.body();
                                response(ctx, service.update(id, newData));
                            });

                    delete("/{param}", ctx -> response(ctx, service.remove(ctx.pathParam("param"))));
                });

    }

    private Context response(@NotNull Context ctx, JSONObject json) {

        if (json == null) {
            ctx.status(404);
            json = new JSONObject().put("request", ctx.fullUrl()).put("error",
                    "La solicitud ha producido 'null' como respuesta");
        } else if (json.has("error")) {
            ctx.status(404);
            json.put("request", ctx.fullUrl());
        }

        return ctx.json(json.toString());

    }

    public void info() {
        System.out.println(
                String.format("%s>> Creados los endpoints para %sService%s", Utils.BLUE, currentPath, Utils.RESET));
    }

}