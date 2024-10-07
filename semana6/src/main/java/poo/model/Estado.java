package poo.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Estado {
    
    private LocalDateTime fecha;
    TipoEstado estado;
    ArrayList<Envio> envios = new ArrayList<>();
}
