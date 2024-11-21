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
    private Cliente cliente;

    public Mercancia() {
        this(Utils.getRandomKey(8), new Cliente(), "", 0, 0, 0, LocalDateTime.now().minusYears(100),
                LocalDateTime.now(), "");
    }

    public Mercancia(String id, Cliente cliente, String contenido, double ancho, double alto, double largo,
            LocalDateTime fechaHoraIngreso, LocalDateTime fechaHoraSalida, String bodega) {
        setId(id);
        setCliente(cliente);
        setContenido(contenido);
        setAncho(ancho);
        setAlto(alto);
        setLargo(largo);
        setFechaHoraIngreso(fechaHoraIngreso);
        setFechaHoraSalida(fechaHoraSalida);
        setBodega(bodega);
    }

    public Mercancia(String id) {
        this(id, new Cliente(), "", 10, 10, 10, LocalDateTime.now().minusYears(100), LocalDateTime.now(), "");
    }

    public Mercancia(Cliente cliente, String contenido, double ancho, double alto, double largo,
            LocalDateTime fechaHoraIngreso, LocalDateTime fechaHoraSalida, String bodega) {
        this(Utils.getRandomKey(8), new Cliente(), "", 0, 0, 0, LocalDateTime.now().minusYears(100),
                LocalDateTime.now(), "");
    }

    public Mercancia(Mercancia m) {
        this(m.id, m.cliente, m.contenido, m.ancho, m.alto, m.largo, m.fechaHoraIngreso, m.fechaHoraSalida, m.bodega);
    }

    public Mercancia(JSONObject json) {
        this(json.getString("id"), new Cliente(json.getJSONObject("cliente")), json.getString("contenido"),
                json.getDouble("ancho"), json.getDouble("alto"), json.getDouble("largo"),
                LocalDateTime.parse(json.getString("fechaHoraIngreso")),
                LocalDateTime.parse(json.getString("fechaHoraSalida")), json.getString("bodega"));
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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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
                ", Cliente=" + cliente +
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

    @Override
    public double getCosto() {
        double ope = ancho * alto * largo;
        double volumen = Math.round(ope * 1000.0) / 1000.0;
        
        if (volumen < 0.125) {
            volumen = 0.125; 
        }

        int dias = getDias();

        return volumen * 10000 * dias;
    }

    public int getDias() {
        if (fechaHoraIngreso == null || fechaHoraSalida == null) {
            return 0; 
        }
        return Math.max(1, (int) java.time.Duration.between(fechaHoraIngreso, fechaHoraSalida).toDays());
    }

    public double getVolumen() {
        
        double ope = ancho * alto * largo;
        double volumen = Math.round(ope * 1000.0) / 1000.0;

        return volumen;
    }
}
