package poo.model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import poo.helpers.Utils;

public class Bulto extends Envio {

    public Bulto() {

    }

    public Bulto(String nroGuia, double peso, boolean fragil, String contenido, double valorDeclarado,
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

    public Bulto(double peso, boolean fragil, String contenido, double valorDeclarado,
    Cliente destinatario, Cliente remitente, ArrayList<Estado> estados){

        this(Utils.getRandomKey(5), peso, fragil, contenido, valorDeclarado, destinatario, remitente, estados);
    }

    public Bulto(JSONObject bultoJson){

          this.nroGuia = bultoJson.getString("nroGuia");
        this.peso = bultoJson.getDouble("peso");
        this.fragil = bultoJson.getBoolean("fragil");
        this.contenido = bultoJson.getString("contenido");
        this.valorDeclarado = bultoJson.getDouble("valorDeclarado");
        JSONObject remitenteJson = bultoJson.getJSONObject("remitente");
        this.remitente = new Cliente(remitenteJson);
        JSONObject destinatarioJson = bultoJson.getJSONObject("destinatario");
        this.destinatario = new Cliente(destinatarioJson);
        JSONArray listaEstados = bultoJson.getJSONArray("estados");

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
