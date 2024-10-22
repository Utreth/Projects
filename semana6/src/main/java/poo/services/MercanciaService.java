package poo.services;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
            return new JSONObject().put("message", "ok").put("data", data);
        } catch (IOException | JSONException e) {
            Utils.printStackTrace(e);
            return Utils.keyValueToJson("message", "Sin acceso a datos de mercancias", "error", e.getMessage());
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

        mercancia = new Mercancia(aux);
        list.set(i, mercancia);

        Utils.writeJSON(list, fileName);

        return new JSONObject().put("message", "ok").put("data", mercancia.toJSONObject());
    }

    @Override
    public JSONObject remove(String id) throws Exception {
        // si un cliente está registrado en mercancías o en envíos, no se puede borrar
        throw new UnsupportedOperationException("¡Peligro! operación de eliminación aún no soportada por seguridad");
    }

    @Override
    public Class<Mercancia> getDataType() {
        return Mercancia.class;
    }

    public Mercancia dataToAddOk(String strJson) throws Exception {

        JSONObject json = new JSONObject(strJson);
        Mercancia mercancia = new Mercancia();

        if (!json.has("id")) {
            json.put("id", Utils.getRandomKey(5));
        }

        String id = Utils.stringOk("id", 5, json);
        String contenido = Utils.stringOk("contenido", 1, json);
        double ancho = Utils.doubleOK("ancho", 0.1, 500.0, json);
        double alto = Utils.doubleOK("alto", 0.1, 500.0, json);
        double largo = Utils.doubleOK("largo", 0.1, 500.0, json);
        LocalDateTime fechaHoraIngreso = LocalDateTime.parse(Utils.stringOk("fechaHoraIngreso", 1, json));
        LocalDateTime fechaHoraSalida = LocalDateTime.parse(Utils.stringOk("fechaHoraSalida", 1, json));
        String bodega = Utils.stringOk("bodega", 1, json);

        String clienteId = Utils.stringOk("cliente", 5, json);
        Cliente usuario = new Cliente();
        usuario.setId(clienteId);

        mercancia.setId(clienteId);
        mercancia.setContenido(contenido);
        mercancia.setAncho(ancho);
        mercancia.setAlto(alto);
        mercancia.setLargo(largo);
        mercancia.setFechaHoraIngreso(fechaHoraIngreso);
        mercancia.setFechaHoraSalida(fechaHoraSalida);
        mercancia.setBodega(bodega);
        mercancia.setUsuario(usuario);

        return new Mercancia(id, contenido, ancho, alto, largo, fechaHoraIngreso, fechaHoraSalida, bodega, usuario);

    }

}
