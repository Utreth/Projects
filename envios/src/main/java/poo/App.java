package poo;

import java.util.Locale;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.javalin.Javalin;
import io.javalin.http.Context;
import poo.helpers.Controller;
import poo.helpers.Utils;
import poo.model.Bulto;
import poo.model.Caja;
import poo.model.Cliente;
import poo.model.Envio;
import poo.model.Mercancia;
import poo.model.Paquete;
import poo.model.Sobre;
import poo.model.TipoEstado;
import poo.services.ClienteService;
import poo.services.EnvioService;
import poo.services.MercanciaService;
import poo.services.Service;
import poo.services.SobreService;

public final class App {

    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws Exception {
        int port = 7070;
        String message = String.format(
                "%sIniciando la API Rest de Envios y bodegaje. Use Ctrl+C para detener la ejecución%s", Utils.CYAN,
                Utils.RESET);
        LOG.info(message);

        Utils.trace = true;
        int length = args.length;
        if (length > 0) {
            Utils.trace = Boolean.parseBoolean(args[0]);
            if (length >= 2) {
                port = Integer.parseInt(args[1]);
            }
        }

        if (Utils.trace) {
            LOG.info(String.format("%sHabilitada la traza de errores%s", Utils.YELLOW, Utils.RESET));
        } else {
            LOG.info(String.format("%sEnvíe un argumento true|false para habilitar|deshabilitar la traza de errores%s",
                    Utils.YELLOW, Utils.RESET));
        }

        Locale.setDefault(Locale.of("es_CO"));

        Service<Cliente> clienteService = new ClienteService();
        Service<Mercancia> mercanciaService = new MercanciaService(clienteService);
        Service<Envio> paqueteService = new EnvioService(Paquete.class, clienteService);
        Service<Envio> cajaService = new EnvioService(Caja.class, clienteService);
        Service<Envio> bultoService = new EnvioService(Bulto.class, clienteService);
        Service<Envio> sobreService = new SobreService(Sobre.class, clienteService);

        Javalin app = Javalin
                .create(config -> {
                    config.http.defaultContentType = "application/json";
                    config.bundledPlugins.enableCors(cors -> cors.addRule(it -> it.anyHost()));

                    config.router.apiBuilder(() -> {
                        new Controller<>(clienteService).info();
                        new Controller<>(mercanciaService).info();
                        new Controller<>(paqueteService).info();
                        new Controller<>(cajaService).info();
                        new Controller<>(bultoService).info();
                        new Controller<>(sobreService).info();
                    });
                });

        app.start(port)
                .get("/", ctx -> ctx.json("{ \"data\": \"Bienvenido al servicio de ventas\", \"message\": \"ok\" }"))
                .get("/envio/estados", ctx -> ctx.json(TipoEstado.getAll().toString()))
                .exception(
                        Exception.class,
                        (e, ctx) -> {
                            Utils.printStackTrace(e);
                            String error = Utils.keyValueToStrJson("message", e.getMessage(), "request", ctx.fullUrl());
                            ctx.json(error).status(400);
                        });

        app.afterMatched(ctx -> updateClients(ctx));

        Runtime.getRuntime().addShutdownHook(
                new Thread(() -> {
                    LOG.info(String.format("%sEl servidor Jetty de Javalin ha sido detenido%s%n", Utils.RED,
                            Utils.RESET));
                }));

    }

    private static void updateClients(@NotNull Context ctx) throws Exception{

        if (ctx.path().contains("cliente") && ctx.method().toString().equals("PATCH")) {

            JSONObject json = new JSONObject(ctx.result());

            if (json.getString("message").equals("ok")) {
                JSONObject clienteActualizado = json.getJSONObject("data");
                Utils.existsCliente("Mercancia", "cliente", clienteActualizado);
                Utils.existsCliente("Paquete", "remitente", clienteActualizado);
                Utils.existsCliente("Paquete", "destinatario", clienteActualizado);
                Utils.existsCliente("Sobre", "remitente", clienteActualizado);
                Utils.existsCliente("Sobre", "destinatario", clienteActualizado);
                Utils.existsCliente("Bulto", "remitente", clienteActualizado);
                Utils.existsCliente("Bulto", "destinatario", clienteActualizado);
                Utils.existsCliente("Caja", "remitente", clienteActualizado);
                Utils.existsCliente("Caja", "destinatario", clienteActualizado);

            }

        }
    }

}