package poo;

import java.time.Duration;
import java.time.LocalDate;

public class Pelicula {

    private String nombre;
    private Duration duracion;
    private LocalDate fechaEstreno;
    private Genero genero;
    private double recaudo;

    // Por defecto
    public Pelicula() {

        this("", Duration.ZERO, LocalDate.MIN, Genero.DESCONOCIDO, Double.NaN);
    }

    /**
     * constructor parametrizado
     * 
     * @param nombre       Una cadena de texto
     * @param duracion     Ejemplo: Duration.parse("PT2H10M")
     * @param fechaEstreno Ejemplo: LocalDate.parse("1995-12-23")
     * @param genero       Ejemplo: Genero.Comedia
     * @param recaudo      Un valor real
     */

    // parametrizado
    public Pelicula(String nombre, Duration duracion, LocalDate fechasEstreno, Genero genero, double recaudo) {

        setDuracion(duracion);
        setFechaEstreno(fechasEstreno);
        setGenero(genero);
        setNombre(nombre);
        setRecaudo(recaudo);

    }

    public Duration getDuracion() {
        return duracion;
    }

    public LocalDate getFechaEstreno() {
        return fechaEstreno;
    }

    public Genero getGenero() {
        return genero;
    }

    public String getNombre() {
        return nombre;
    }

    public double getRecaudo() {
        return recaudo;
    }

    public void setDuracion(Duration duracion) {
        this.duracion = duracion;
    }

    public void setFechaEstreno(LocalDate fechaEstreno) {
        this.fechaEstreno = fechaEstreno;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setRecaudo(double recaudo) {
        this.recaudo = recaudo;
    }

    public String strDuracion() {

        long hh = duracion.toHours();
        long mm = duracion.toMinutesPart();
        return String.format("%02d:%02d", hh, mm);
    }

    @Override
    public String toString() {

        return String.format("%-30s %-6s %s %-18s %13.2f", nombre, strDuracion(), fechaEstreno.toString(), genero,
                recaudo);
    }

}