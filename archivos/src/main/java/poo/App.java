package poo;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import poo.helpers.Keyboard;
import poo.helpers.Utils;
import poo.model.Genero;
import poo.model.Pelicula;

public class App {
    public static void main(String[] args) {
        menu();
    }

    private static void menu() {

        try {
            inicializar();
        } catch (Exception e) {
            e.printStackTrace();
        }

        do {
            try {
                int opcion = leerOpcion();
                switch (opcion) {
                    case 1:
                        testFileExists();
                        break;
                    case 2:
                        testPathExists();
                        break;
                    case 3:
                        testCreateFolderIfNotExist();
                        break;
                    case 4:
                        testGetPath();
                        break;
                    case 5:
                        testInitPath();
                        break;
                    case 6:
                        testWriteText();
                        break;
                    case 7:
                        testReadText();
                        break;
                    case 8:
                        testWriteCSV();
                        break;
                    case 9:
                        csvToJSON();
                        break;
                    case 10:
                        csvToJSON2();
                        break;
                    case 11:
                        csvToJSONArray();
                        break;
                    case 12:
                        csvToJSONFile();
                        break;
                    case 0:
                        salir();
                        break;
                    default:
                        System.out.println("Opción inválida");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (true);
    }

    private static void inicializar() {
        System.out.print("\033[H\033[2J"); // limpiar la consola
    }

    private static void salir() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.exit(0);
    }

    static int leerOpcion() {
        String opciones = String.format("\n%sMenú de opciones:%s\n", Utils.GREEN, Utils.RESET) +
                "  1 - testFileExist                7 -  testReadText\n" +
                "  2 - testPathExists               8 -  testWriteCSV\n" +
                "  3 - testCreateFolderIfNotExist   9 -  cvsToJSON\n" +
                "  4 - testGetPath                  10 - cvsToJSON2\n" +
                "  5 - testInitPath                 11 - cvsToJSONArray\n" +
                "  6 - testWriteText                12 - cvsToJSONFile\n" +
                String.format("  %s0 - Salir%s\n", Utils.RED, Utils.RESET) +
                String.format("\nElija una opción (%s0 para salir%s) > ", Utils.RED, Utils.RESET);

        int opcion = Keyboard.readInt(opciones);
        return opcion;
    }

    private static void testFileExists() {

        String filePath = "./src/main/java/poo/model/Pelicula.java";
        boolean existe = Utils.fileExists(filePath);
        System.out.println("¿Existe Pelicula.java? = " + existe);
        filePath = "./src/main/java/poo/helpers/Keyboard.java";
        existe = Utils.fileExists(filePath);
        System.out.println("¿Existe Keyboard.java? = " + existe);
        filePath = "./src/main/java/poo/helpers/";
        existe = Utils.fileExists(filePath);
        System.out.println("¿Existe helpers? = " + existe);
    }

    private static void testPathExists() {

        String filePath = "./src/main/java/poo/model/Pelicula.java";
        boolean existe = Utils.pathExists(filePath);
        filePath = "./src/main/java/poo/helpers/";
        existe = Utils.pathExists(filePath);
        System.out.println("¿Existe helpers? = " + existe);
    }

    private static void testCreateFolderIfNotExist() throws Exception {
        String folder = String.format("./data/%s", Utils.getRandomKey(5));
        Utils.createFolderIfNotExist(folder);
        System.out.printf("Carpeta %s creada y lista para ser usada", folder);
    }

    private static void testGetPath() {
        String ruta = Utils.getPath("./src/main/java/poo/model/Genero.java");
        System.out.println("La ruta padre es: " + ruta);
        ruta = Utils.getPath("./src/main/java/poo/model/");
        System.out.println("La ruta padre es: " + ruta);
    }

    private static void testInitPath() throws Exception {
        Utils.initPath("./data/varios/prueba.txt");
        Utils.initPath("./data/trazas/prueba.txt");
    }

    private static void testWriteText() throws Exception {
        ArrayList<String> jugadores = new ArrayList<>();
        jugadores.add("Diego Armando Maradona");
        jugadores.add("Lionel Messi");
        jugadores.add("Cristiano Ronaldo");
        jugadores.add("Johan Cruyff");
        jugadores.add("Franz Beckenbauer");
        Utils.writeText(jugadores, "./data/futbolistas.txt");
        ArrayList<Pelicula> peliculas = new ArrayList<>();
        peliculas.add(
                new Pelicula(
                        "Harry el sucio",
                        Duration.parse("PT1H50M"),
                        LocalDate.parse("1971-12-23"),
                        Genero.COMEDIA, 932989142));
        peliculas.add(
                new Pelicula(
                        "El padrino",
                        Duration.parse("PT2H10M"),
                        LocalDate.parse("1972-03-24"),
                        Genero.ACCION, 32989142));
        peliculas.add(
                new Pelicula(
                        "Matrix I",
                        Duration.parse("PT1H50M"),
                        LocalDate.parse("1999-05-21"),
                        Genero.CIENCIA_FICCION, 1632989142));
        peliculas.add(
                new Pelicula(
                        "El señor de los anillos",
                        Duration.parse("PT2H30M"),
                        LocalDate.parse("2001-12-25"),
                        Genero.FANTASIA, 1532989000));
        // ––– línea de inserción –––
        Utils.writeText(peliculas, "./data/peliculas.txt");

        String nombre;
        Duration duracion;
        LocalDate fecha;
        Genero generoSeleccion;
        double recaudo;

        do {
            nombre = Keyboard.readString("Nombre de la película (Intro termina): ");
            if (!nombre.isEmpty()) {

                peliculas.add(
                        new Pelicula(
                                nombre,
                                duracion = Keyboard.readDuration("Duración (HH:MM): "),
                                fecha = Keyboard.readDate("Fecha de estreno (año-mes-dia): "),
                                generoSeleccion = Keyboard.readEnum(Genero.class, "Géneros para seleccionar uno"),
                                recaudo = Keyboard.readDouble("Recaudo en dólares: ")

                        ));
                System.out.println("Película agregada con éxito");
            }

        } while (!nombre.isEmpty());
    }

    private static void testReadText() throws Exception {
        String filePath = "./data/futbolistas.txt";
        String texto = Utils.readText(filePath);
        System.out.println(texto);
    }

    private static void testWriteCSV() throws Exception {
        ArrayList<Pelicula> peliculas = new ArrayList<>();
        peliculas.add(
                new Pelicula(
                        "Harry el sucio",
                        Duration.parse("PT1H50M"),
                        LocalDate.parse("1971-12-23"),
                        Genero.COMEDIA, 932989142));
        peliculas.add(
                new Pelicula(
                        "El padrino",
                        Duration.parse("PT2H10M"),
                        LocalDate.parse("1972-03-24"),
                        Genero.ACCION, 32989142));
        peliculas.add(
                new Pelicula(
                        "Matrix I",
                        Duration.parse("PT1H50M"),
                        LocalDate.parse("1999-05-21"),
                        Genero.CIENCIA_FICCION, 1632989142));
        peliculas.add(
                new Pelicula(
                        "El señor de los anillos",
                        Duration.parse("PT2H30M"),
                        LocalDate.parse("2001-12-25"),
                        Genero.FANTASIA, 1532989000));
        Utils.writeCSV(peliculas, "./data/peliculas.csv");
    }

    private static void csvToJSON() throws IOException {
        String texto = Utils.readText("./data/peliculas.csv");
        try (Scanner sc = new Scanner(texto).useDelimiter(";|[\n]+|[\r\n]+")) {
            while (sc.hasNext()) {
                String nombre = sc.next();
                Duration duracion = Duration.parse(sc.next());
                LocalDate fechaEstreno = LocalDate.parse(sc.next());
                Genero genero = Genero.getEnum(sc.next());
                double recaudo = sc.nextDouble();
                Pelicula p = new Pelicula(nombre, duracion, fechaEstreno, genero, recaudo);
                System.out.println(p.toJSONObject().toString(2));
            }
        }
    }

    private static void csvToJSON2() throws IOException {
        String texto = Utils.readText("./data/peliculas.csv");
        try (Scanner sc = new Scanner(texto).useDelimiter(";|[\n]+|[\r\n]+")) {
            while (sc.hasNext()) {
                String nombre = sc.next();
                Duration duracion = Duration.parse(sc.next());
                LocalDate fechaEstreno = LocalDate.parse(sc.next());
                Genero genero = Genero.getEnum(sc.next());
                double recaudo = sc.nextDouble();
                JSONObject json = new JSONObject()
                        .put("nombre", nombre)
                        .put("duracion", duracion)
                        .put("fechaEstreno", fechaEstreno)
                        .put("genero", genero)
                        .put("recaudo", recaudo);
                System.out.println(json.toString(2));
            }
        }
    }

    private static void csvToJSONArray() throws IOException {
        JSONArray array = new JSONArray();
        String texto = Utils.readText("./data/peliculas.csv");
        try (Scanner sc = new Scanner(texto).useDelimiter(";|[\n]+|[\r\n]+")) {
            while (sc.hasNext()) {
                String nombre = sc.next();
                Duration duracion = Duration.parse(sc.next());
                LocalDate fechaEstreno = LocalDate.parse(sc.next());
                Genero genero = Genero.getEnum(sc.next());
                double recaudo = sc.nextDouble();
                Pelicula p = new Pelicula(nombre, duracion, fechaEstreno, genero, recaudo);
                array.put(p.toJSONObject());
            }
        }
        for (int i = 0; i < array.length(); i++) {
            System.out.println(array.get(i));
        }
    }

    private static void csvToJSONFile() throws IOException {
        ArrayList<Pelicula> peliculas = new ArrayList<>();
        String texto = Utils.readText("./data/peliculas.csv");
        try (Scanner sc = new Scanner(texto).useDelimiter(";|[\n]+|[\r\n]+")) {
            while (sc.hasNext()) {
                String nombre = sc.next();
                Duration duracion = Duration.parse(sc.next());
                LocalDate fechaEstreno = LocalDate.parse(sc.next());
                Genero genero = Genero.getEnum(sc.next());
                double recaudo = sc.nextDouble();
                peliculas.add(new Pelicula(nombre, duracion, fechaEstreno, genero, recaudo));
            }
        }
        Utils.writeJSON(peliculas, "./data/peliculas.json");
    }
}
