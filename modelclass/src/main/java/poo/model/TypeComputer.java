package poo.model;

import org.json.JSONArray;
import org.json.JSONObject;

public enum TypeComputer {

    LAPTOP("Computador tipo laptop"),
    DESKTOP("Computador tipo escritorio");

    private final String value;

    private TypeComputer(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static TypeComputer getEnumComputer(String value) {

        if (value == null) {
            throw new IllegalArgumentException("");
        }

        for (TypeComputer typeComputer : values()) {
            if (value.equalsIgnoreCase(typeComputer.getValue())) {
                return typeComputer;
            }
        }
        throw new IllegalArgumentException("");
    }

     public static JSONObject getAll() {

        JSONArray jsonArrayComputer = new JSONArray();
        for (TypeComputer vComputer : values()) {
            jsonArrayComputer.put(new JSONObject().put("ordinal", vComputer.ordinal()).put("key", vComputer).put("value", vComputer.value));
        }
        return new JSONObject().put("message", "ok").put("data", jsonArrayComputer);
    }
}
