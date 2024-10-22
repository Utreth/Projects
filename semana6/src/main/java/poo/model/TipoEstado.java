package poo.model;

import org.json.JSONArray;
import org.json.JSONObject;

public enum TipoEstado {
    DEVUELTO("Envio devuelto"),
    EN_CAMINO("Envio en camino"),
    EN_PREPARACION("Envio en preparacion"),
    ENTREGADO("Envio entregado"),
    ENVIADO("Envio enviado"),
    EXTRAVIADO("Envio extraviado"),
    INDEFINIDO("Envio indefinido"),
    RECIBIDO("Envio recibido"),
    REENVIADO("Envio reenviado");

    private final String value;

    private TipoEstado(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static TipoEstado getEnum(String value) {
        if (value == null) {
            throw new IllegalStateException();
        }
        for (TipoEstado t : values()) {
            if (value.equalsIgnoreCase(t.getValue())) {
                return t;
            }
        }
        throw new IllegalStateException();
    }

    public static JSONObject getAll() {
        JSONArray jsonAr = new JSONArray();
        for (TipoEstado l : values()) {
            jsonAr.put(
                    new JSONObject()
                            .put("ordinal", l.ordinal())
                            .put("key", l)
                            .put("value", l.value));

        }
        return new JSONObject().put("message", "ok").put("data", jsonAr);
    }
}