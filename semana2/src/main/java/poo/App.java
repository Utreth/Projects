package poo;

public class App {
    public static void main(String[] args) {
        // se crea estudiante 1, y se imprime la informacion por defecto
        System.out.println("\n" + "Creando instancia Estudiante 1:");
        estudiante e1 = new estudiante();
        System.out.println(e1);

        // Se crea estudiante 2, y se imprime la informacion editada
        System.out.println("\n" + "Creando instancia Estudiante 2:");
        estudiante e2 = new estudiante();
        e2.setCodigo("45339");
        e2.setNombre("Antonio");
        e2.setSemestre(3);
        e2.setActivo(true);
        System.out.println(e2);

        // Se crea estudiante 3
        System.out.println("\n" + "Creando instancia Estudiante 3:");
        estudiante e3 = new estudiante();
        e3.setCodigo("67889");
        e3.setNombre("Ginger");
        e3.setSemestre(5);
        e3.setActivo(true);
        System.out.println(e3);

        // Se crea estudiante 4
        System.out.println("\n" + "Creando instancia Estudiante 4:");
        estudiante e4 = new estudiante();
        e4.setCodigo("76534");
        e4.setNombre("Maximo");
        e4.setSemestre(6);
        e4.setActivo(false);
        System.out.println(e3);

        // Se crea estudiante 5
        System.out.println("\n" + "Creando instancia estudiante 5");
        estudiante e5 = new estudiante();
        e5.setCodigo("76123");
        e5.setNombre("Isis");
        e5.setSemestre(2);
        e5.setActivo(true);
        System.out.println(e5);

        // Se crean instancais de asignaturas
        System.out.println("\n" + "Creando instancia Asignaturas:");
        asignatura a1 = new asignatura("A211", "Programacion 2", 5, 3, false);
        asignatura a2 = new asignatura("B644", "Fisica 1", 4, 2, true);
        asignatura a3 = new asignatura("C299", "Tecnicas de programacion", 3, 2, false);
        System.out.println(a1);
        System.out.println(a2);
        System.out.println(a3);

        // Array de asignaturas
        asignatura[] asignaturas = new asignatura[2];
        asignaturas[0] = new asignatura("A002", "Poo", 5, 3, false);
        asignaturas[1] = new asignatura("A002", "Calculo", 4, 2, false);

        // Se asigna el array al estudiante
        System.out.println("\n" + "Creando instancia Estudiante 4 con materias:");
        e4.setAsignaturas(asignaturas);
        System.out.println(e4);

        // Creando estudiante 6 con asignaturas
        System.out.println("\n" + "Creando instancia Estudiante 6 con materias: ");
        estudiante e6 = new estudiante("35779", "Simba", 9, false, null);
        asignatura[] asignaturas2 = new asignatura[2];
        asignaturas2[0] = new asignatura("A987", "Textos", 3, 2, true);
        asignaturas2[1] = new asignatura("A033", "Ingles", 2, 1, false);
        e6.setAsignaturas(asignaturas2);
        System.out.println(e6);

    }
}
