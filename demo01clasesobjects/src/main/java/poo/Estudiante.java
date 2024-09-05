package poo;

public class Estudiante {
    // atributos
    private String codigo; // cc, ti, pasaporte, ...
    private String nombre; // nombre estudiante
    private int semestre;
    private boolean activo;
    private Asignatura asignaturas[];

    // constructor por defecto (no recibe parámetros)
    public Estudiante() {
        this("0000", "NN", 0, false, new Asignatura[2]);
    }

    // constructor parametrizado
    public Estudiante(String codigo, String nombre, int semestre, boolean activo, Asignatura asignaturas[]) {
        // this.codigo=codigo;
        // this.nombre=nombre;
        // this.semestre=semestre;
        // this.activo=activo;
        // en el caso de usar this. no es necesario añadir la palabra reservada final en
        // los metodos Set
        setCodigo(codigo);
        setNombre(nombre);
        setSemestre(semestre);
        setActivo(activo);
        setAsignaturas(asignaturas);
    }

    // accesores (get) y mutadores (set)
    public String getCodigo() {
        return codigo;
    }

    public final void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public final void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getSemestre() {
        return semestre;
    }

    public final void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public boolean getActivo() {
        return activo;
    }

    public final void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Asignatura[] getAsignaturas() {
        return asignaturas;
    }

    public void setAsignaturas(Asignatura[] asignaturas) {
        this.asignaturas = asignaturas;
    }

    private String listarAsignaturas() {
        String aux = "Asignaturas : ";
        for (Asignatura a : asignaturas) {
            if (a != null) {
                aux += a + "\n";
            }
        }
        return aux;
    }

    @Override
    public String toString() {
        String strActivo = activo ? "SI" : "NO";
        return "\nNombre: " + nombre +
                " \nCodigo: " + codigo +
                "\nsemestre: " + semestre +
                "\nactivo: " + strActivo + "\n" +
                listarAsignaturas();
    }

}
