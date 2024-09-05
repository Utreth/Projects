package poo;

public class Asignatura {
    private String codigo = "0000";
    private String nombre = "x";
    private int horasSemana = 0;
    private int creditos = 21;
    private boolean habilitable = false;

    public Asignatura(String codigo, String nombre, int horasSemana, int creditos, boolean habilitable) {
        setCodigo(codigo);
        setNombre(nombre);
        setHorasSemana(horasSemana);
        setCreditos(creditos);
        setHabilitable(habilitable);
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getHorasSemana() {
        return horasSemana;
    }

    public void setHorasSemana(int horasSemana) {
        this.horasSemana = horasSemana;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public boolean isHabilitable() {
        return habilitable;
    }

    public void setHabilitable(boolean habilitable) {
        this.habilitable = habilitable;
    }

    @Override
    public String toString() {

        String strHabilitable = habilitable ? "SI" : "NO";
        return "\nNombre: " + nombre +
                " \nCodigo: " + codigo +
                " \nHorasSemana" + horasSemana +
                "\nCreditos: " + creditos +
                "\nHabilitable: " + strHabilitable + "\n";
    }

}
