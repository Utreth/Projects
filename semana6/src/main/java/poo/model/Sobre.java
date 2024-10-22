package poo.model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import poo.helpers.Utils;

public class Sobre extends Envio {

    private boolean certificado;

    public Sobre() {

    }

    public Sobre(boolean certificado, String nroGuia, double peso, boolean fragil, String contenido,
            double valorDeclarado,
            Cliente destinatario, Cliente remitente, ArrayList<Estado> estados) {

        this.certificado = certificado;
        this.nroGuia = nroGuia;
        this.peso = peso;
        this.fragil = fragil;
        this.contenido = contenido;
        this.valorDeclarado = valorDeclarado;
        this.destinatario = destinatario;
        this.remitente = remitente;
        this.estados = estados;
    }

    public Sobre(Sobre copiaSobre) {

        this(copiaSobre.certificado, copiaSobre.nroGuia, copiaSobre.peso, copiaSobre.fragil, copiaSobre.contenido,
                copiaSobre.valorDeclarado, copiaSobre.destinatario, copiaSobre.remitente, copiaSobre.estados);

    }

    public Sobre(String nroGuia) {

        this();
        setNroGuia(nroGuia);
    }

    public Sobre(boolean certificado, double peso, boolean fragil, String contenido, double valorDeclarado,
            Cliente destinatario, Cliente remitente, ArrayList<Estado> estados) {

        this(certificado, Utils.getRandomKey(5), peso, fragil, contenido, valorDeclarado, destinatario, remitente,
                estados);
    }

    public Sobre(JSONObject sobreJson) {

        this.certificado = sobreJson.getBoolean("certificado");
        this.nroGuia = sobreJson.getString("nroGuia");
        this.peso = sobreJson.getDouble("peso");
        this.fragil = sobreJson.getBoolean("fragil");
        this.contenido = sobreJson.getString("contenido");
        this.valorDeclarado = sobreJson.getDouble("valorDeclarado");
        JSONObject remitenteJson = sobreJson.getJSONObject("remitente");
        this.remitente = new Cliente(remitenteJson);
        JSONObject destinatarioJson = sobreJson.getJSONObject("destinatario");
        this.destinatario = new Cliente(destinatarioJson);
        JSONArray listaEstados = sobreJson.getJSONArray("estados");

        for (int i = 0; i < listaEstados.length(); i++) {

            this.estados.add(new Estado(listaEstados.getJSONObject(i)));

        }
    }

    public void setCertificado(boolean certificado) {
        this.certificado = certificado;
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
