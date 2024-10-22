package poo.model;

public enum Genero {

    ACCION("Accion"),
    AVENTURA("Aventura"),
    CIENCIA_FICCION("Ciencia_ficcion"),
    COMEDIA("Comedia"),
    DRAMA("Drama"),
    FANTASIA("Fantasia"),
    MUSICAL("Musical"),
    NO_FICCION("No_ficcion / documental"),
    SUSPENSO("Suspenso"),
    TERROR("Terror"),
    DESCONOCIDO("Desconocido");

    private final String value;

    private Genero(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Genero getEnum(String value) {

        if (value == null) {
            throw new IllegalArgumentException("");
        }

        for (Genero u : values()) {
            if (value.equalsIgnoreCase(u.getValue())) {
                return u;
            }
        }
        throw new IllegalArgumentException("");

    }


}