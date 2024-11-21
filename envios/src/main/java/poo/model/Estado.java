package poo.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.json.JSONObject;

public class Estado {

    private LocalDateTime fecha;
    private TipoEstado tipoEstado;

    public Estado() {

    }

    public Estado(TipoEstado tipoEstado, LocalDateTime fecha) {

        setTipoEstado(tipoEstado);
        setFecha(fecha);
    }

    public Estado(Estado estado) {
        this(estado.tipoEstado, estado.fecha);
    }

    public Estado(JSONObject json) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String fechaStr = json.getString("fecha");
        this.fecha = LocalDateTime.parse(fechaStr, formato);
        this.tipoEstado = json.getEnum(TipoEstado.class, "tipoEstado");
    }

    public TipoEstado getTipoEstado() {
        return tipoEstado;
    }

    public void setTipoEstado(TipoEstado tipoEstado) {
        this.tipoEstado = tipoEstado;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public JSONObject toJSONObject() {

        return new JSONObject(this);

    }

    public String toJSON() {
        return (new JSONObject(this)).toString(8);
    }

    public int hashCode() {
        return super.hashCode();
    }

    public String getIdEstado() {
        return String.valueOf(super.hashCode());
    }

}
