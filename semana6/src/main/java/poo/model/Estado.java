package poo.model;

import java.time.LocalDateTime;

import org.json.JSONObject;

public class Estado {

    private LocalDateTime fecha;
    TipoEstado estadoEnvio;

    public JSONObject toJSONObject() {

        JSONObject estadoJson = new JSONObject();

        estadoJson.put("fecha", this.fecha);
        estadoJson.put("estadoEnvio", this.estadoEnvio);

        return estadoJson;              

    }
}
