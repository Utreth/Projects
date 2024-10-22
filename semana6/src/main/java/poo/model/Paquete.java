package poo.model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import poo.helpers.Utils;

public class Paquete extends Envio {

    public Paquete() {

    }

    public Paquete(String nroGuia, double peso, boolean fragil, String contenido, double valorDeclarado,
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

    public Paquete(Paquete copiaPaquete) {

        this(copiaPaquete.nroGuia, copiaPaquete.peso, copiaPaquete.fragil, copiaPaquete.contenido,
                copiaPaquete.valorDeclarado,
                copiaPaquete.destinatario, copiaPaquete.remitente, copiaPaquete.estados);
    }

    public Paquete(String nroGuia) {

        this();
        setNroGuia(nroGuia);
    }

    public Paquete(double peso, boolean fragil, String contenido, double valorDeclarado,
            Cliente destinatario, Cliente remitente, ArrayList<Estado> estados) {

        this(Utils.getRandomKey(5), peso, fragil, contenido, valorDeclarado, destinatario, remitente, estados);
    }

    public Paquete(JSONObject paqueteJson){

        this.nroGuia = paqueteJson.getString("nroGuia");
        this.peso = paqueteJson.getDouble("peso");
        this.fragil = paqueteJson.getBoolean("fragil");
        this.contenido = paqueteJson.getString("contenido");
        this.valorDeclarado = paqueteJson.getDouble("valorDeclarado");
        JSONObject remitenteJson = paqueteJson.getJSONObject("remitente");
        this.remitente = new Cliente(remitenteJson);
        JSONObject destinatarioJson = paqueteJson.getJSONObject("destinatario");
        this.destinatario = new Cliente(destinatarioJson);
        JSONArray listaEstados = paqueteJson.getJSONArray("estados");

        for (int i = 0; i < listaEstados.length(); i++) {

            this.estados.add(new Estado(listaEstados.getJSONObject(i)));

        }
    }

    @Override
    public double getCosto() {

        throw new UnsupportedOperationException("Unimplemented method 'getCosto'");
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
