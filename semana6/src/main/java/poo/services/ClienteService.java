package poo.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.javalin.http.Context;
import poo.helpers.Utils;
import poo.model.Cliente;

public class ClienteService implements Service<Cliente> {

    private List<Cliente> list;
    private final String fileName;

    public ClienteService() throws Exception {
        fileName = Utils.PATH + "Cliente.json";

        if (Utils.fileExists(fileName)) {
            load();
        } else {
            list = new ArrayList<>();
        }
    }

    @Override
    public JSONObject add(String strJson) throws Exception {

        Cliente c = dataToAddOk(strJson);

        if (list.add(c)) {
            Utils.writeJSON(list, fileName);
        }
        return new JSONObject().put("message", "ok").put("data", c.toJSONObject());
    }

    @Override
    public JSONObject get(int index) {
        return list.get(index).toJSONObject();
    }

    @Override
    public JSONObject get(String id) throws Exception {

        Cliente c = getItem(id);

        if (c == null) {
            throw new NoSuchElementException(String.format("No se encontro un cliente con ID %s", id));
        }
        return c.toJSONObject();
    }

    @Override
    public Cliente getItem(String id) throws Exception {
        int i = list.indexOf(new Cliente(id));
        return i > -1 ? list.get(i) : null;
    }

    @Override
    public JSONObject getAll() {
        try {
            JSONArray data = new JSONArray(Utils.readText(fileName));
            return new JSONObject().put("message", "ok").put("data", data).put("lenght", data.length());
        } catch (IOException | JSONException e) {
            Utils.printStackTrace(e);
            return Utils.keyValueToJson("message", "Sin acceso a datos de clientes", "error", e.getMessage());
        }
    }

    @Override
    public final List<Cliente> load() throws Exception {
        list = new ArrayList<>();
        JSONArray jsonArr = new JSONArray(Utils.readText(fileName));

        for (int i = 0; i < jsonArr.length(); i++) {
            JSONObject jsonObj = jsonArr.getJSONObject(i);
            list.add(new Cliente(jsonObj));
        }

        return list;
    }

    @Override
    public JSONObject update(String id, String strJson) throws Exception {
        // crear un JSONObject con las claves y valores que hay que actualizar
        JSONObject newData = new JSONObject(strJson);

        // buscar el cliente que se debe actualizar y recordar la posición
        Cliente cliente = getItem(id);

        if (cliente == null) {
            throw new NullPointerException("No se encontró el cliente " + id);
        }
        int i = list.indexOf(cliente);

        cliente = getUpdated(newData, cliente);

        // buscar la posición del cliente en la lista y actualizarlo
        list.set(i, cliente);

        // actualizar el archivo de clientes
        Utils.writeJSON(list, fileName);

        // devolver el cliente con los cambios realizados
        return new JSONObject().put("message", "ok").put("data", cliente.toJSONObject());
    }

    @Override
    public JSONObject remove(String id) throws Exception {
        JSONObject cliente = get(id);
        if (cliente == null) {
            throw new NoSuchElementException("No existe un cliente con la identificación " + id);
        }

        Cliente c = new Cliente(cliente);
        exists(cliente);

        if (!list.remove(c)) {
            throw new Exception(String.format("No se pudo eliminar el cliente con identificación %s", id));
        }

        Utils.writeJSON(list, fileName);
        return new JSONObject().put("message", "ok").put("data", cliente);
    }

    @Override
    public Class<Cliente> getDataType() {

        return Cliente.class;
    }

    public Cliente dataToAddOk(String strJson) throws Exception {

        JSONObject json = new JSONObject(strJson);
        Cliente cliente = new Cliente();

        if (!json.has("id")) {
            json.put("id", Utils.getRandomKey(5));
        }

        String id = Utils.stringOk("id", 5, json);
        String nombre = Utils.stringOk("nombre", 1, json);
        String direccion = Utils.stringOk("direccion", 10, json);
        String telefono = Utils.stringOk("telefono", 10, json);
        String ciudad = Utils.stringOk("ciudad", 4, json);

        cliente.setId(id);
        cliente.setNombre(nombre);
        cliente.setDireccion(direccion);
        cliente.setTelefono(telefono);
        cliente.setCiudad(ciudad);

        if (list.contains(cliente)) {
            throw new ArrayStoreException(
                    String.format("El cliente %s - %s ya existe", cliente.getId(), cliente.getNombre()));
        }

        return new Cliente(id, nombre, direccion, ciudad, telefono);
    }

    public Cliente getUpdated(JSONObject newData, Cliente current) {

        JSONObject update = new JSONObject(current);

        if (newData.has("nombre")) {

            update.put("nombre", Utils.stringOk("nombre", 0, newData));
        }

        if (newData.has("direccion")) {

            update.put("direccion", Utils.stringOk("direccion", 10, newData));
        }

        if (newData.has("telefono")) {

            update.put("telefono", Utils.stringOk("telefono", 10, newData));
        }

        if (newData.has("ciudad")) {

            update.put("ciudad", Utils.stringOk("ciudad", 4, newData));
        }

        return new Cliente(update);
    }

    private void exists(JSONObject cliente) throws Exception {
        String id = cliente.getString("id");

        // buscar el cliente en mercancias y si existe no permitir eliminarlo
        if (Utils.exists(Utils.PATH + "Mercancia", "cliente", cliente)) {
            throw new Exception(String.format("No eliminado. El cliente %s tiene mercancías registradas", id));
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
                    cliente.getString("id"), filename));
        }
    }

    @Override
    public JSONObject size() {

        return new JSONObject().put("size", list.size());
    }

    


}
