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

        id = "00000";
        contenido = "null";
        ancho = 0;
        alto = 0;
        largo = 0;
        fechaHoraIngreso = LocalDateTime.now();
        fechaHoraSalida = LocalDateTime.now().plusDays(2);
        bodega = "null";
        usuario = new Cliente();

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
    public double getCosto() {

        return 0;
    }

    @Override
    public String toJSON() {

        return (new JSONObject(this)).toString(2);
    }

    @Override
    public JSONObject toJSONObject() {

        return new JSONObject(this);

    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return "Mercancia{" +
                "ID='" + id + '\'' +
                ", Contenido='" + contenido + '\'' +
                ", Ancho=" + ancho + " cm" +
                ", Alto=" + alto + " cm" +
                ", Largo=" + largo + " cm" +
                ", Fecha de Ingreso=" + fechaHoraIngreso.format(formatter) +
                ", Fecha de Salida=" + fechaHoraSalida.format(formatter) +
                ", Bodega='" + bodega + '\'' +
                ", Cliente=" + usuario + 
                '}';
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (this.getClass() != obj.getClass()) {
            return false;
        }

        Mercancia m = (Mercancia) obj;
        return this.id.equals(m.id);

    }
}
