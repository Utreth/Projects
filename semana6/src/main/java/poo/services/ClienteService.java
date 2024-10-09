package poo.services;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import poo.helpers.Utils;
import poo.model.Cliente;

public class ClienteService implements Service<Cliente> {

    private List<Cliente> list;
    private final String fileName;

    public ClienteService() throws Exception {

        fileName = Utils.PATH + "Cliente.json";

        if (Utils.fileExists(fileName)) {
            // load():
        } else {
            list = new ArrayList<>();
        }
    }

    @Override
    public JSONObject add(String strJson) throws Exception {

        JSONObject json = new JSONObject(strJson);

        if (!json.has("id")) {
            json.put("id", Utils.getRandomKey(5));
        }

        Cliente cs = new Cliente(json);

        if (list.contains(cs)) {
            throw new ArrayStoreException(String.format("El cliente %s - %s ya existe", cs.getId(), cs.getNombre()));
        }
        if (list.add(cs)) {

            Utils.writeJSON(list, fileName);
        }

        return new JSONObject().put("message:", "ok").put("data:", cs.toJSONObject());
    }

    @Override
    public JSONObject get(int index) {

        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    @Override
    public JSONObject get(String id) throws Exception {

        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    @Override
    public Cliente getItem(String id) throws Exception {

        throw new UnsupportedOperationException("Unimplemented method 'getItem'");
    }

    @Override
    public JSONObject getAll() {

        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }

    @Override
    public List<Cliente> load() throws Exception {

        throw new UnsupportedOperationException("Unimplemented method 'load'");
    }

    @Override
    public JSONObject update(String id, String strJson) throws Exception {

        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public JSONObject remove(String id) throws Exception {

        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    @Override
    public Class<Cliente> getDataType() {

        throw new UnsupportedOperationException("Unimplemented method 'getDataType'");
    }

}
