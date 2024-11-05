package poo.services;

import poo.model.Envio;
import poo.helpers.Utils;
import poo.model.Cliente;
import poo.model.Estado;
import poo.model.TipoEstado;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
            throw new ArrayStoreException(String.format("La envio %s ya existe", envio.getNroGuia()));
        }

        if (list.add(envio)) {
            Utils.writeJSON(list, fileName);
        }

        return new JSONObject().put("message", "ok").put("data", envio.toJSONObject());

    }

    @Override
    public Envio dataToAddOk(String strJson) throws Exception {
        JSONObject json = new JSONObject(strJson);
        if (!(json.has("nroGuia"))) {
            json.put("nroGuia", Utils.getRandomKey(8));
        }

        if (!(json.has("estados"))) {
            Estado estado = new Estado(TipoEstado.RECIBIDO, LocalDateTime.now().withNano(0));
            JSONArray estados = new JSONArray();
            estados.put(estado.toJSONObject());
            json.put("estados", estados);
        }
        if (!(json.has("fragil"))) {

            json.put("fragil", false);
        }
        updateCliente(json, "remitente");
        updateCliente(json, "destinatario");

        Utils.stringOk("contenido", 3, json);
        Utils.doubleOK("peso", 0.0, 500, json);
        Utils.doubleOK("valorDeclarado", 0, 10000000, json);
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

        if (newData.has("estados")) {
            
        }

        return null;
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
    public JSONObject remove(String id) throws Exception {
        // TODO Auto-generated method stub
        return null;
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
}