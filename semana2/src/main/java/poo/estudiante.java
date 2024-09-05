package poo;

public class estudiante {

    private String codigo = "*****";
    private String nombre = "none";
    private int semestre = 0;
    private boolean activo = false;
    private asignatura asignaturas[];

    // Por defecto

    public estudiante() {

        this("****", "none", 0, false, new asignatura[2]);

    }

    // Parametrizado

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public void setAsignaturas(asignatura[] asignaturas) {
        this.asignaturas = asignaturas;
    }

    public asignatura[] getAsignaturas() {
        return asignaturas;
    }

    public estudiante(String codigo, String nombre, int semestre, boolean activo, asignatura asignaturas[]) {

        this.codigo = codigo;
        setActivo(activo);
        setAsignaturas(asignaturas);
        setCodigo(codigo);
        setNombre(nombre);

    }

    private String listarAsignaturas() {
        String aux = "Materias : ";
        for (asignatura a : asignaturas) {
            if (a != null) {
                aux += a + "\n";
            }
        }
        return aux;
    }

    @Override
    public String toString() {
        String strActivo = activo ? "SI" : "NO";
        return "\nEstudiante: " + nombre +
                " \nCodigo: " + codigo +
                "\nsemestre: " + semestre +
                "\nactivo: " + strActivo + "\n" +
                listarAsignaturas();
    }

}