package poo.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
        int i = list.indexOf(new Cliente(id));
        return i > -1 ? get(i) : null;
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
            return new JSONObject().put("message", "ok").put("data", data);
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

        JSONObject json = new JSONObject(strJson);
        // Buscar el cliente que se debe actualizar
        Cliente cliente = getItem(id);
        int i = list.indexOf(cliente);

        if (cliente == null) {
            throw new NullPointerException("No se encontro el cliente" + id);
        }

        // Crear un objeto JSON con las propiedades del objeto a actualizar
        JSONObject aux = cliente.toJSONObject();
        JSONArray propiedades = json.names();

        for (int k = 0; k < propiedades.length(); k++) {

            // Asignar a un aux los nuevos valores de las propieades dadas
            String propiedad = propiedades.getString(k);
            Object valor = json.get(propiedad);
            aux.put(propiedad, valor);

        }

        // Utilizar aux para actualizar el cliente
        cliente = new Cliente(aux);
        list.set(i, cliente);
        // Actualizar el archivo de clientes
        Utils.writeJSON(list, fileName);
        // Devolver el cliente con los cambios realizados
        return new JSONObject().put("message", "ok").put("data", cliente.toJSONObject());

    }

    @Override
    public JSONObject remove(String id) throws Exception {

        // si un cliente está registrado en mercancías o en envíos, no se puede borrar
        throw new UnsupportedOperationException("¡Peligro! operación de eliminación aún no soportada por seguridad");
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
}
