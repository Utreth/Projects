package poo.model;

import org.json.JSONArray;
import org.json.JSONObject;

public enum TypeLanguage {

    SPANISH("Idioma español"),
    ENGLISH("Idioma ingles"),
    FRENCH("Idioma frances");

    private final String value;

    private TypeLanguage(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static TypeLanguage getEnum(String value) {

        if (value == null) {
            throw new IllegalArgumentException("");
        }

        for (TypeLanguage u : values()) {
            if (value.equalsIgnoreCase(u.getValue())) {
                return u;
            }
        }
        throw new IllegalArgumentException("");

    }

    public static JSONObject getAll() {

        JSONArray jsonArray = new JSONArray();
        for (TypeLanguage vLanguage : values()) {
            jsonArray.put(new JSONObject().put("ordinal", vLanguage.ordinal()).put("key", vLanguage).put("value", vLanguage.value));
        }
        return new JSONObject().put("message", "ok").put("data", jsonArray);
    }
}