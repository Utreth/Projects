package poo;

import org.json.JSONObject;

import poo.model.Envio;
import poo.model.Paquete;
import poo.model.Sobre;

public class Pruebas {
    public static void main(String[] args) throws Exception {
        create(Paquete.class, getJsonPaquete());
        create(Sobre.class, getJsonSobre());
    }

    private static void create(Class<? extends Envio> subclase, JSONObject json) throws Exception {
        Envio instancia = subclase.getConstructor(JSONObject.class).newInstance(json);
        System.out.println("Tipo: " + instancia.getClass().getSimpleName());
        System.out.println("Guía: " + instancia.getNroGuia());
        System.out.println("Contenido: " + instancia.getContenido());
        System.out.println("Remitente: " + instancia.getRemitente().getNombre());
        System.out.println("Destinatario: " + instancia.getDestinatario().getNombre());
        System.out.println();
    }

    private static JSONObject getJsonSobre() {
        JSONObject json = new JSONObject("""
                              {
                  "contenido": "Documentos notariales",
                  "nroGuia": "1BECQX7N",
                  "peso": 0,
                  "fragil": false,
                  "remitente": {
                    "ciudad": "Manizales",
                    "direccion": "Mercaldas La Sultana",
                    "id": "0F7SD",
                    "telefono": "3115550002",
                    "nombre": "David Andrés García"
                  },
                  "valorDeclarado": 0,
                  "certificado": false,
                  "destinatario": {
                    "ciudad": "Manizales",
                    "direccion": "Edificio del parque, piso 2, Ucaldas",
                    "id": "C0001",
                    "telefono": "3115551234",
                    "nombre": "Carlos Cuesta Iglesias"
                  },
                  "estados": [{
                    "estado": "RECIBIDO",
                    "fechaHora": "2024-10-13T18:37:45"
                  }]
                }
                              """);
        return json;
    }

    private static JSONObject getJsonPaquete() {
        JSONObject json = new JSONObject("""
                {
                  "contenido": "Componentes eléctricos",
                  "nroGuia": "AFOQJW4R",
                  "peso": 1500,
                  "fragil": true,
                  "remitente": {
                    "ciudad": "Manizales",
                    "direccion": "Mercaldas La Sultana",
                    "id": "0F7SD",
                    "telefono": "3115550002",
                    "nombre": "David Andrés García"
                  },
                  "valorDeclarado": 200000,
                  "destinatario": {
                    "ciudad": "Manizales",
                    "direccion": "Edificio del parque, piso 2, Ucaldas",
                    "id": "C0001",
                    "telefono": "3115551234",
                    "nombre": "Carlos Cuesta Iglesias"
                  },
                  "estados": [{
                    "estado": "RECIBIDO",
                    "fechaHora": "2024-10-08T19:29:44"
                  }]
                }
                """);
        return json;
    }

}
