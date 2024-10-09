package poo.model;

import java.time.LocalDateTime;

import org.json.JSONObject;

public class Estado {

    private LocalDateTime fecha;
    TipoEstado estadoEnvio;

    public Estado() {

    }

    public Estado(LocalDateTime fecha, TipoEstado estadoEnvio) {

        this.estadoEnvio = estadoEnvio;
        this.fecha = fecha;
    }

    public Estado(JSONObject estadoJson){

        
    }

    public TipoEstado getEstadoEnvio() {
        return estadoEnvio;
    }

    public void setEstadoEnvio(TipoEstado estadoEnvio) {
        this.estadoEnvio = estadoEnvio;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public JSONObject toJSONObject() {

        JSONObject estadoJson = new JSONObject();

        estadoJson.put("fecha", this.fecha);
        estadoJson.put("estadoEnvio", this.estadoEnvio);

        return estadoJson;

    }
}
