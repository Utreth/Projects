package poo;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) {
        ArrayList<Pelicula> pelis = new ArrayList<>();

        pelis.add(new Pelicula("Spider Man 1", Duration.parse("PT2H30M"), LocalDate.parse("2000-04-25"), Genero.ACCION,
                100000000));
        pelis.add(new Pelicula("Spider Man 2", Duration.parse("PT2H50M"), LocalDate.parse("2005-06-10"), Genero.ACCION,
                960000000));
        pelis.add(new Pelicula("Spider Man 3", Duration.parse("PT2H10M"), LocalDate.parse("2009-08-19"), Genero.ACCION,
                78000000));
        pelis.add(new Pelicula("Titanic", Duration.parse("PT2H30M"), LocalDate.parse("2013-04-25"), Genero.DRAMA,
                5000000));
        pelis.add(new Pelicula("UP", Duration.parse("PT1H30M"), LocalDate.parse("2015-03-14"), Genero.AVENTURA,
                1000000000));

        for (Pelicula i : pelis) {
            System.out.println(i);
        }

    }
}