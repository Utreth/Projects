package poo;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Instancia estudiante 1 ------------->");
        Estudiante e1 = new Estudiante();
        e1.setCodigo("56532");
        System.out.println("Código: " + e1.getCodigo());
        e1.setSemestre(0000);
        System.out.println("Semestre: " + e1.getSemestre());
        e1.setSemestre(1);
        System.out.println("activo: " + e1.getActivo());
        System.out.println(e1);
        // crear instancia e2
        // datos: nombre: Andres , codigo: 12345, Semestre: 7, Activo: false
        System.out.println("Instancia estudiante 2 ------------->");
        Estudiante e2 = new Estudiante();
        e2.setNombre("Andrés");

        System.out.println(e2.getNombre());

        e2.setCodigo("12345");
        System.out.println(e2.getCodigo());

        e2.setSemestre(7);
        System.out.println(e2.getSemestre());

        e2.setActivo(true);
        System.out.println(e2.getActivo());
        System.out.println(e2);

        System.out.println("Instancia estudiante 3 ------------->");
        // asignaturas []
        // Estudiante e3 = new Estudiante("987655", "Jorge", 4, false, asignaturas[0] );
        // System.out.println(e3);

        // crear 3 instancias de asignaturas usando el constructor parametrizado
        System.out.println("Instancia de asignaturas ----------------->");
        Asignatura a1 = new Asignatura("1001", "Calculo", 8, 3, true);
        Asignatura a2 = new Asignatura("1002", "Prog 2", 9, 3, false);
        Asignatura a3 = new Asignatura("1003", "Inglés", 5, 1, true);
        System.out.println(a1);
        System.out.println(a2);
        System.out.println(a3);
        // creando e5
        Estudiante e5 = new Estudiante("10365", "Luisa", 2, true, null);
        e5.setCodigo("2145");
        e5.setNombre("Luisa Garcia");
        e5.setSemestre(2);
        e5.setActivo(false);
        // crear un array de asignaturas
        Asignatura[] asignaturas = new Asignatura[2];
        asignaturas[0] = new Asignatura("A01", "POO", 5, 3, false);
        asignaturas[1] = new Asignatura("A02", "Cálculo", 3, 2, true);
        // asignar el array al estudiante
        e5.setAsignaturas(asignaturas);
        System.out.println(e5);
        // creando e6
        Estudiante e6 = new Estudiante("10900", "Andres ", 3, true, null);
        Asignatura[] asignaturas2 = new Asignatura[3];
        asignaturas2[0] = new Asignatura("A01", "POO", 5, 3, false);
        asignaturas2[1] = new Asignatura("A02", "Cálculo", 3, 2, true);
        asignaturas2[2] = new Asignatura("A03", "Ingles", 6, 1, false);
        e6.setAsignaturas(asignaturas2);
        System.out.println(e6);
        // creando e7
        Estudiante e7 = new Estudiante("0002334", "Jaider", 9, false, null);
        Asignatura[] asignaturas3 = new Asignatura[2];
        asignaturas3[0] = new Asignatura("A02", "Cálculo", 3, 2, true);
        asignaturas3[1] = new Asignatura("A03", "Ingles", 6, 1, false);
        e7.setAsignaturas(asignaturas3);
        System.out.println(e7);
    }
}
