package poo.model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import poo.helpers.Utils;

public class Bulto extends Envio {

    public Bulto() {

    }

    public Bulto(String nroGuia, int peso, boolean fragil, String contenido, int valorDeclarado,
            Cliente destinatario, Cliente remitente, ArrayList<Estado> estados) {

        this.nroGuia = nroGuia;
        this.peso = peso;
        this.fragil = fragil;
        this.contenido = contenido;
        this.valorDeclarado = valorDeclarado;
        this.destinatario = destinatario;
        this.remitente = remitente;
        this.estados = estados;

    }

    public Bulto(Bulto copiaBulto) {

        this(copiaBulto.nroGuia, copiaBulto.peso, copiaBulto.fragil, copiaBulto.contenido, copiaBulto.valorDeclarado,
                copiaBulto.destinatario, copiaBulto.remitente, copiaBulto.estados);

    }

    public Bulto(String nroGuia) {

        this();
        setNroGuia(nroGuia);
    }

    public Bulto(int peso, boolean fragil, String contenido, int valorDeclarado,
            Cliente destinatario, Cliente remitente, ArrayList<Estado> estados) {

        this(Utils.getRandomKey(5), peso, fragil, contenido, valorDeclarado, destinatario, remitente, estados);
    }

    public Bulto(JSONObject bultoJson) {

        super(bultoJson);
    }

    @Override
    public double getCosto() {
        double costoPorKilo = 1000; 
        return peso * costoPorKilo; 
    }

    @Override
    public String toJSON() {

        return (new JSONObject(this)).toString(2);
    }

    @Override
    public JSONObject toJSONObject() {

        return new JSONObject(this);
    }

}
