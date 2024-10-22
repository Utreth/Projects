package poo.model;

import java.time.Duration;
import java.time.LocalDate;
import org.json.JSONObject;

public class Pelicula implements IFormatCSV {
    private String nombre;
    private Duration duracion;
    private LocalDate fechaEstreno;
    private Genero genero;
    private double recaudo;

    public Pelicula() {
        this("", Duration.ZERO, LocalDate.MIN, Genero.DESCONOCIDO, Double.NaN);
    }

    public Pelicula(
            String nombre, Duration duracion, LocalDate fechaEstreno,
            Genero genero, double recaudo) {
        this.nombre = nombre;
        this.duracion = duracion;
        this.fechaEstreno = fechaEstreno;
        this.genero = genero;
        this.recaudo = recaudo;
    }

    public Pelicula(JSONObject json) {
        this(
                json.getString("nombre"),
                Duration.parse(json.getString("duracion")),
                LocalDate.parse(json.getString("fechaEstreno")),
                json.getEnum(Genero.class, "genero"),
                json.getDouble("recaudo"));
    }

    public JSONObject toJSONObject() {
        return new JSONObject(this);
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Duration getDuracion() {
        return duracion;
    }

    public void setDuracion(Duration duracion) {
        this.duracion = duracion;
    }

    public String strDuracion() {
        long hh = duracion.toHours();
        long mm = duracion.toMinutesPart();
        return String.format("%02d:%02d", hh, mm);
    }

    public LocalDate getFechaEstreno() {
        return fechaEstreno;
    }

    public void setFechaEstreno(LocalDate fechaEstreno) {
        this.fechaEstreno = fechaEstreno;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public double getRecaudo() {
        return recaudo;
    }

    public void setRecaudo(double recaudo) {
        this.recaudo = recaudo;
    }

    @Override
    public String toString() {
        return String.format(
                "%-30s %-6s %s %-18s %13.2f",
                nombre, strDuracion(), fechaEstreno.toString(), genero, recaudo);
    }

    @Override
    public String toCSV() {
        return String.format(
                "%s;%s;%s;%s;%f%n",
                nombre, duracion, fechaEstreno, genero, recaudo);
    }
}