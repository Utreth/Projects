package poo.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.json.JSONObject;
import poo.helpers.Utils;

public class Mercancia implements Costeable, Exportable {

    private String id;
    private String contenido;
    private double ancho;
    private double alto;
    private double largo;
    private LocalDateTime fechaHoraIngreso;
    private LocalDateTime fechaHoraSalida;
    private String bodega;
    private Cliente usuario;

    public Mercancia() {

    }

    public Mercancia(String id, String contenido, double ancho, double alto, double largo,
            LocalDateTime fechaHoraIngreso, LocalDateTime fechaHoraSalida, String bodega, Cliente usuario) {
        this.id = id;
        this.contenido = contenido;
        this.ancho = ancho;
        this.alto = alto;
        this.largo = largo;
        this.fechaHoraIngreso = fechaHoraIngreso;
        this.fechaHoraSalida = fechaHoraSalida;
        this.bodega = bodega;
        this.usuario = usuario;
    }

    public Mercancia(Mercancia m) {

        this(m.id, m.contenido, m.ancho, m.alto, m.largo, m.fechaHoraIngreso, m.fechaHoraSalida, m.bodega, m.usuario);
    }

    public Mercancia(String id) {
        this();
        setId(id);
    }

    public Mercancia(String contenido, double ancho, double alto, double largo, LocalDateTime fechaHoraIngreso,
            LocalDateTime fechaHoraSalida, String bodega, Cliente usuario) {
        this(Utils.getRandomKey(5), contenido, ancho, alto, largo, fechaHoraIngreso, fechaHoraSalida, bodega, usuario);

    }

    public Mercancia(JSONObject mercanciaJson) {

        this.id = mercanciaJson.getString("id");
        this.contenido = mercanciaJson.getString("contenido");
        this.ancho = mercanciaJson.getDouble("ancho");
        this.alto = mercanciaJson.getDouble("alto");
        this.largo = mercanciaJson.getDouble("largo");
        this.bodega = mercanciaJson.getString("bodega");

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String fechaIngresoStr = mercanciaJson.getString("fechaHoraIngreso");
        this.fechaHoraIngreso = LocalDateTime.parse(fechaIngresoStr, formato);
        String fechaSalidaStr = mercanciaJson.getString("fechaHoraSalida");
        this.fechaHoraSalida = LocalDateTime.parse(fechaSalidaStr, formato);

        JSONObject clienteJson = mercanciaJson.getJSONObject("usuario");
        this.usuario = new Cliente(clienteJson);
    }

    @Override
    public double getCosto() {

        return 0;
    }

    @Override
    public String toJSON() {

        return null;
    }

    @Override
    public JSONObject toJSONObject() {

        JSONObject mercaJson = new JSONObject();

        mercaJson.put("id", this.id);
        mercaJson.put("contenido", this.contenido);
        mercaJson.put("ancho", this.ancho);
        mercaJson.put("alto", this.alto);
        mercaJson.put("largo", this.largo);
        mercaJson.put("fechaHoraIngreso", this.fechaHoraIngreso);
        mercaJson.put("fechaHoraSalida", this.fechaHoraSalida);
        mercaJson.put("bodega", this.bodega);
        mercaJson.put("usuario", this.usuario.toJSONObject());

        return mercaJson;

    }

    public double getAlto() {
        return alto;
    }

    public void setAlto(double alto) {
        this.alto = alto;
    }

    public double getAncho() {
        return ancho;
    }

    public void setAncho(double ancho) {
        this.ancho = ancho;
    }

    public String getBodega() {
        return bodega;
    }

    public void setBodega(String bodega) {
        this.bodega = bodega;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public LocalDateTime getFechaHoraIngreso() {
        return fechaHoraIngreso;
    }

    public void setFechaHoraIngreso(LocalDateTime fechaHoraIngreso) {
        this.fechaHoraIngreso = fechaHoraIngreso;
    }

    public LocalDateTime getFechaHoraSalida() {
        return fechaHoraSalida;
    }

    public void setFechaHoraSalida(LocalDateTime fechaHoraSalida) {
        this.fechaHoraSalida = fechaHoraSalida;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLargo() {
        return largo;
    }

    public void setLargo(double largo) {
        this.largo = largo;
    }

    public Cliente getUsuario() {
        return usuario;
    }

    public void setUsuario(Cliente usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return (new JSONObject(this)).toString(2);
    }
}
