package poo.services;

import poo.helpers.Utils;
import poo.model.Cliente;
import poo.model.Envio;
import poo.model.TipoEstado;
import poo.model.Estado;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.io.IOException;
import java.time.LocalDateTime;

public class EnvioService implements Service<Envio> {

    private final Class<? extends Envio> subclase;
    private final Service<Cliente> clientes;
    private final String fileName;
    protected List<Envio> list;

    public EnvioService(Class<? extends Envio> subclase, Service<Cliente> clientes) throws Exception {
        this.subclase = subclase;
        this.clientes = clientes;
        fileName = Utils.PATH + subclase.getSimpleName() + ".json";

        if (Utils.fileExists(fileName)) {
            load();
        } else {
            list = new ArrayList<>();
        }
    }

    @Override
    public JSONObject add(String strJson) throws Exception {

        Envio envio = dataToAddOk(strJson);

        if (list.contains(envio)) {
            throw new ArrayStoreException(String.format("El envio %s ya existe", envio.getNroGuia()));
        }

        if (list.add(envio)) {
            Utils.writeJSON(list, fileName);
        }

        return new JSONObject().put("message", "ok").put("data", envio.toJSONObject());

    }

    @Override
    public Envio dataToAddOk(String strJson) throws Exception {
        JSONObject json = new JSONObject(strJson);
        if (!json.has("nroGuia") || json.getString("nroGuia").isBlank()) {
            json.put("nroGuia", Utils.getRandomKey(8));
        }

        Utils.stringOk("nroGuia", 8, json);

        if (!(json.has("estados"))) {
            Estado estado = new Estado(TipoEstado.EN_PREPARACION, LocalDateTime.now().withNano(0));
            JSONArray estados = new JSONArray();
            estados.put(estado.toJSONObject());
            json.put("estados", estados);
        }

        if (!(json.has("fragil"))) {

            json.put("fragil", false);
        }

        if (!(json.has("certificado"))) {

            json.put("certificado", false);
        }

        updateCliente(json, "remitente");
        updateCliente(json, "destinatario");
        Utils.stringOk("contenido", 3, json);
        Utils.doubleOk("peso", 0.0, 500, json);
        Utils.doubleOk("valorDeclarado", 0, 10000000, json);
        Envio envio = subclase.getConstructor(JSONObject.class).newInstance(json);
        String idRemitente = json.getJSONObject("remitente").getString("id");
        String idDestinatario = json.getJSONObject("destinatario").getString("id");

        if (idRemitente.equals(idDestinatario)) {
            throw new IllegalArgumentException("Se espera un destinatario diferente al remitente : id" + idRemitente);
        }

        if (list.contains(envio)) {
            throw new ArrayStoreException(String.format("El envio %s ya existe", envio.getNroGuia()));
        }

        return envio;
    }

    private void updateCliente(JSONObject json, String cliente) throws Exception {

        String idCliente = json.getString(cliente);
        JSONObject jsonCliente = clientes.get(idCliente);
        json.put(cliente, jsonCliente);
    }

    @Override
    public JSONObject get(int index) {
        return list.get(index).toJSONObject();
    }

    @Override
    public JSONObject get(String nroGuia) throws Exception {

        Envio e = getItem(nroGuia);

        if (e == null) {
            throw new NoSuchElementException(String.format("No se encontro un envio con nro de guia " + nroGuia));
        }
        return e.toJSONObject();
    }

    @Override
    public JSONObject getAll() {
        try {
            JSONArray data = new JSONArray(Utils.readText(fileName));
            return new JSONObject().put("message", "ok").put("data", data).put("lenght", data.length());
        } catch (IOException | JSONException e) {
            Utils.printStackTrace(e);
            return Utils.keyValueToJson("message", "Sin acceso a datos de envios", "error", e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<Envio> getDataType() {
        return (Class<Envio>) subclase;
    }

    @Override
    public Envio getItem(String nroGuia) throws Exception {
        Envio e = subclase.getConstructor(String.class).newInstance(nroGuia);
        int i = list.indexOf(e);
        return i > -1 ? list.get(i) : null;
    }

    @Override
    public Envio getUpdated(JSONObject newData, Envio current) throws Exception {

        TipoEstado estado = current.getEstados().getLast().getTipoEstado();

        if (!"DEVUELTO|EN_PREPARACION|INDEFINIDO|RECIBIDO".contains(estado.toString())) {
            throw new IllegalStateException(String.format("Un envio con estado %s no puede ser cambiado", estado));
        }

        // if (newData.has("estados")) {
        // System.out.println(String.format("Importante: no se permite modificar los
        // estados de un envio de tipo %s",
        // current.getTipo()));
        // newData.remove("estados");
        // }

        JSONObject updated = new JSONObject(current);

        try {
            if (newData.has("remitente")) {

                updateCliente(newData, "remitente");
                updated.put("remitente", newData.getJSONObject("remitente"));

            }
            if (newData.has("destinatario")) {
                updateCliente(newData, "destinatario");
                updated.put("destinatario", newData.getJSONObject("destinatario"));
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al determinar el cliente propietario del envio " + e);
        }

        if (newData.has("estados")) {
            updated.put("estados", newData.getJSONArray("estados"));
        }

        if (newData.has("peso")) {

            updated.put("peso", Utils.doubleOk("peso", 0, 1000, newData));
        }

        if (newData.has("fragil")) {
            boolean fragilValue = newData.getBoolean("fragil");
            updated.put("fragil", fragilValue);
        }

        if (newData.has("contenido")) {

            updated.put("contenido", Utils.stringOk("contenido", 4, newData));
        }

        if (newData.has("valorDeclarado")) {

            updated.put("valorDeclarado", Utils.doubleOk("valorDeclarado", 0, 1000000, newData));
        }

        if (newData.has("ancho")) {

            updated.put("ancho", Utils.doubleOk("ancho", 0.1, 2.44, newData));
        }

        if (newData.has("alto")) {

            updated.put("alto", Utils.doubleOk("alto", 0.1, 2.59, newData));
        }

        if (newData.has("largo")) {

            updated.put("largo", Utils.doubleOk("largo", 0.1, 12.19, newData));
        }

        if (newData.has("certificado")) {
            updated.put("certificado", newData.getBoolean("certificado"));
        }
        Envio envio = subclase.getConstructor(JSONObject.class).newInstance(updated);

        return envio;
    }

    @Override
    public List<Envio> load() throws Exception {

        list = new ArrayList<>();

        JSONArray jsonArr = new JSONArray(Utils.readText(fileName));

        for (int i = 0; i < jsonArr.length(); i++) {
            JSONObject jsonObj = jsonArr.getJSONObject(i);
            list.add(subclase.getConstructor(JSONObject.class).newInstance(jsonObj));
        }

        return list;
    }

    @Override
    public JSONObject remove(String nroGuia) throws Exception {
        JSONObject envio = get(nroGuia);
        if (envio == null) {
            throw new NoSuchElementException("No existe un envio con la identificación " + nroGuia);
        }

        Envio c = subclase.getConstructor(JSONObject.class).newInstance(envio);
        exists(envio);

        if (!list.remove(c)) {
            throw new Exception(String.format("No se pudo eliminar el envio con identificación %s", nroGuia));
        }

        Utils.writeJSON(list, fileName);
        return new JSONObject().put("message", "ok").put("data", envio);
    }

    @Override
    public JSONObject update(String nroGuia, String strJson) throws Exception {

        JSONObject newData = new JSONObject(strJson);

        Envio envio = subclase.cast(getItem(nroGuia));

        if (envio == null) {
            throw new NullPointerException("No se encontró el envío " + nroGuia);
        }

        int i = list.indexOf(envio);
        envio = getUpdated(newData, envio);
        list.set(i, envio);

        Utils.writeJSON(list, fileName);
        return new JSONObject().put("message", "ok").put("data", envio.toJSONObject());
    }

    @Override
    public JSONObject size() {

        return new JSONObject().put("size", list.size());
    }

    private void exists(JSONObject cliente) throws Exception {
        String nroGuia = cliente.getString("nroGuia");

        // buscar el cliente en mercancias y si existe no permitir eliminarlo
        if (Utils.exists(Utils.PATH + "Mercancia", "cliente", cliente)) {
            throw new Exception(String.format("No eliminado. El cliente %s tiene mercancías registradas", nroGuia));
        }

        // buscar el cliente en envíos y si existe no permitir eliminarlo
        exists("Paquete", cliente);
        exists("Sobre", cliente);
        exists("Caja", cliente);
        exists("Bulto", cliente);
    }

    private void exists(String filename, JSONObject cliente) throws Exception {
        if (Utils.exists(Utils.PATH + filename, "remitente", cliente)
                || Utils.exists(Utils.PATH + filename, "destinatario", cliente)) {
            throw new Exception(String.format("No eliminado. El cliente %s está registrado en envíos de tipo %s",
                    cliente.getString("nroGuia"), filename));
        }
    }
}