package poo;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.javalin.Javalin;
import poo.helpers.Controller;
import poo.helpers.Utils;
import poo.model.Cliente;
import poo.model.Mercancia;
import poo.model.TipoEstado;
import poo.services.ClienteService;
import poo.services.MercanciaService;
import poo.services.Service;

public final class App {

    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws Exception {
        int port = 7070;
        // https://javalin.io/tutorials/javalin-logging#using-any-of-those-loggers
        String message = String.format(
                "%sIniciando la API Rest de Envios y bodegaje. Use Ctrl+C para detener la ejecución%s", Utils.CYAN,
                Utils.RESET);
        LOG.info(message);

        Utils.trace = true; // no deshabilite la traza de errores hasta terminar completamente la aplicación
        int length = args.length;
        if (length > 0) {
            Utils.trace = Boolean.parseBoolean(args[0]);
            if (length >= 2) {
                port = Integer.parseInt(args[1]);
            }
        }

        if (Utils.trace) {
            // ver para tiempo de desarrollo: ./.vscode/launch.json
            LOG.info(String.format("%sHabilitada la traza de errores%s", Utils.YELLOW, Utils.RESET));
        } else {
            LOG.info(String.format("%sEnvíe un argumento true|false para habilitar|deshabilitar la traza de errores%s",
                    Utils.YELLOW, Utils.RESET));
        }

        // esencial para estandarizar el formato monetario con separador de punto
        // decimal, no con coma
        Locale.setDefault(Locale.of("es_CO"));

        Service<Cliente> clienteService = new ClienteService();
        Service<Mercancia> mercanciaService = new MercanciaService(clienteService);

        Javalin
                .create(config -> {
                    config.http.defaultContentType = "application/json";
                    // ver https://javalin.io/plugins/cors#getting-started
                    config.bundledPlugins.enableCors(cors -> cors.addRule(it -> it.anyHost()));

                    config.router.apiBuilder(() -> {
                        new Controller<>(clienteService).info();
                        new Controller<>(mercanciaService).info();
                    });
                })
                .start(port)
                .get("/", ctx -> ctx.json("{ \"data\": \"Bienvenido al servicio de ventas\", \"message\": \"ok\" }"))
                .get("/envio/estados", ctx -> ctx.json(TipoEstado.getAll().toString()))
                .exception(
                        Exception.class,
                        (e, ctx) -> {
                            Utils.printStackTrace(e);
                            String error = Utils.keyValueToStrJson("message", e.getMessage(), "request", ctx.fullUrl());
                            ctx.json(error).status(400);
                        });
        Runtime
                .getRuntime()
                .addShutdownHook(
                        new Thread(() -> {
                            LOG.info(String.format("%sEl servidor Jetty de Javalin ha sido detenido%s%n", Utils.RED,
                                    Utils.RESET));
                        }));
    }
}
