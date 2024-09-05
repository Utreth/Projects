package poo;

public class asignatura {

    private String codigo = "****";
    private String nombre = "none";
    private int horasSemana = 0;
    private int creditos = 0;
    private boolean habilitable = false;

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

    public void setHabilitable(boolean habilitable) {
        this.habilitable = habilitable;
    }

    public boolean isHabilitable() {
        return habilitable;
    }

    // Por defecto

    public asignatura() {

        this("*****", "none", 0, 0, false);
    }

    // Parametrizado

    public asignatura(String codigo, String nombre, int horasSemana, int creditos, boolean habilitable) {

        setCodigo(codigo);
        setCreditos(creditos);
        setHabilitable(habilitable);
        setHorasSemana(horasSemana);
        setNombre(nombre);

    }

    @Override
    public String toString() {

        String strHabilitable = habilitable ? "SI" : "NO";
        return "\n" + "\nAsignatura: " + nombre +
                " \nCodigo: " + codigo +
                " \nHorasSemana: " + horasSemana +
                "\nCreditos: " + creditos +
                "\nHabilitable: " + strHabilitable;
    }
}
