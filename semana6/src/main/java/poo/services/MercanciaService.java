package poo.services;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import poo.helpers.Utils;
import poo.model.Cliente;
import poo.model.Mercancia;

public class MercanciaService implements Service<Mercancia> {

    private List<Mercancia> list;
    private final String fileName;
    private final Service<Cliente> clientes;

    public MercanciaService(Service<Cliente> clientes) throws Exception {

        this.clientes = clientes;
        fileName = Utils.PATH + "Mercancia.json";

        if (Utils.fileExists(fileName)) {
            load();
        } else {
            list = new ArrayList<>();
        }
    }

    @Override
    public JSONObject add(String strJson) throws Exception {

        Mercancia mercancia = dataToAddOk(strJson);

        if (list.contains(mercancia)) {
            throw new ArrayStoreException(String.format("La mercancia %s ya existe", mercancia.getId()));
        }

        if (list.add(mercancia)) {
            Utils.writeJSON(list, fileName);
        }

        return new JSONObject().put("message", "ok").put("data", mercancia.toJSONObject());

    }

    @Override
    public JSONObject get(int index) {
        return list.get(index).toJSONObject();
    }

    @Override
    public JSONObject get(String id) throws Exception {
        int i = list.indexOf(new Mercancia(id));
        return i > -1 ? get(i) : null;
        
    }

    @Override
    public Mercancia getItem(String id) throws Exception {
        int i = list.indexOf(new Mercancia(id));
        return i > -1 ? list.get(i) : null;         
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

    @Override
    public List<Mercancia> load() throws Exception {
        list = new ArrayList<>();
        JSONArray jsonArr = new JSONArray(Utils.readText(fileName));

        for (int i = 0; i < jsonArr.length(); i++) {
            JSONObject jsonObj = jsonArr.getJSONObject(i);
            list.add(new Mercancia(jsonObj));
        }

        return list;
    }

    @Override
    public JSONObject update(String id, String strJson) throws Exception {
        JSONObject json = new JSONObject(strJson);
        // Buscar el cliente que se debe actualizar
        Mercancia mercancia = getItem(id);
        int i = list.indexOf(mercancia);

        if (mercancia == null) {
            throw new NullPointerException("No se encontro el cliente" + id);
        }

        JSONObject aux = mercancia.toJSONObject();
        JSONArray propiedades = json.names();

        for (int k = 0; k < propiedades.length(); k++) {

            String propiedad = propiedades.getString(k);
            Object valor = json.get(propiedad);
            aux.put(propiedad, valor);

        }

        mercancia = getUpdated(aux, mercancia);
        list.set(i, mercancia);

        Utils.writeJSON(list, fileName);

        return new JSONObject().put("message", "ok").put("data", mercancia.toJSONObject());
    }

    @Override
    public JSONObject remove(String id) throws Exception {
        JSONObject mercancia = get(id);
        if (mercancia == null) {
            throw new NoSuchElementException("No existe una mercancia con la identificación " + id);
        }

        Mercancia c = new Mercancia(mercancia);
        exists(mercancia);

        if (!list.remove(c)) {
            throw new Exception(String.format("No se pudo eliminar la mercancia con identificación %s", id));
        }

        Utils.writeJSON(list, fileName);
        return new JSONObject().put("message", "ok").put("data", mercancia);
    }

    @Override
    public Class<Mercancia> getDataType() {
        return Mercancia.class;
    }

    @Override
    public Mercancia dataToAddOk(String strJson) throws Exception {
        JSONObject json = new JSONObject(strJson);

        if (!json.has("id")) {
            json.put("id", Utils.getRandomKey(8));
        }

        Utils.stringOk("id", 8, json);

        updateCliente(json);

        Utils.stringOk("contenido", 4, json);
        Utils.stringOk("bodega", 10, json);
        Utils.doubleOK("ancho", 0.1, 2.44, json);
        Utils.doubleOK("alto", 0.1, 2.59, json);
        Utils.doubleOK("largo", 0.1, 12.19, json);

        LocalDateTime ingreso = LocalDateTime.parse(json.getString("fechaHoraIngreso"));
        LocalDateTime salida = LocalDateTime.parse(json.getString("fechaHoraSalida"));

        if (!ingreso.isBefore(salida)) {
            throw new IllegalArgumentException("La fecha de ingreso debe ser inferior a la de salida");
        }

        Mercancia m = new Mercancia(json);
        if (list.contains(m)) {
            throw new ArrayStoreException(String.format("La mercancía %s ya existe", m.getId()));
        }

        return m;
    }

    private void updateCliente(JSONObject json) throws Exception {
        // buscar la instancia del cliente correspondiente al ID del cliente dado en la
        // petición
        String idCliente = json.getString("cliente");
        JSONObject jsonCliente = clientes.get(idCliente);
        // reemplazar el ID del cliente por el objeto JSON con la info completa de éste
        json.put("cliente", jsonCliente);
    }

    @Override
    public Mercancia getUpdated(JSONObject newData, Mercancia current) throws Exception {

        JSONObject update = new JSONObject(current);

        try {
            if (newData.has("cliente")) {
                
                updateCliente(newData);

            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al determinar el cliente propietario de la mercancía " + e);
        }

        if (newData.has("contenido")){

            update.put("contenido", Utils.stringOk("contenido", 4, newData));
        }

        if (newData.has("ancho")) {

            update.put("ancho", Utils.doubleOK("ancho", 0.1, 2.44, newData));
        }

        if (newData.has("alto")) {

            update.put("alto", Utils.doubleOK("alto", 0.1, 2.59, newData));
        }

        if (newData.has("largo")) {

            update.put("largo", Utils.doubleOK("largo", 0.1, 12.19, newData));
        }

        LocalDateTime ingreso = LocalDateTime.parse(update.getString("fechaHoraIngreso"));
        LocalDateTime salida = LocalDateTime.parse(update.getString("fechaHoraSalida"));

        if (!ingreso.isBefore(salida)) {
            throw new IllegalArgumentException("La fecha de ingreso debe ser inferior a la de salida");
        }

        if (newData.has("fechaHoraIngreso")) {
            update.put("fechaHoraIngreso", Utils.stringOk("fechaHoraIngreso", 16, newData));
        }
        if (newData.has("fechaHoraSalida")) {
            update.put("fechaHoraSalida", Utils.stringOk("fechaHoraSalida", 16, newData));
        }

        return new Mercancia(update);
    }

    private void exists(JSONObject mercancia) throws Exception {
        String id = mercancia.getString("id");

        // buscar el cliente en mercancias y si existe no permitir eliminarlo
        if (Utils.exists(Utils.PATH + "Mercancia", "cliente", mercancia)) {
            throw new Exception(String.format("No eliminado. El cliente %s tiene mercancías registradas", id));
        }

        // buscar el cliente en mercancia y si existe no permitir eliminarlo
        exists("cliente", mercancia);

    }

    private void exists(String filename, JSONObject mercancia) throws Exception {
        if (Utils.exists(Utils.PATH + filename, "cliente", mercancia)) {
            throw new Exception(String.format("No eliminado. El cliente %s está registrado en mercancias de tipo %s",
                    mercancia.getString("id"), filename));
        }
    }

    @Override
    public JSONObject size() {

        return new JSONObject().put("size", list.size());
    }

}
