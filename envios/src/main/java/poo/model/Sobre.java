package poo.model;

import java.util.ArrayList;

import org.json.JSONObject;

import poo.helpers.Utils;

public class Sobre extends Envio {

    private boolean certificado;

    public Sobre() {

    }

    public Sobre(boolean certificado, String nroGuia, int peso, boolean fragil, String contenido,
            int valorDeclarado,
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

    public Sobre(boolean certificado, int peso, boolean fragil, String contenido, int valorDeclarado,
            Cliente destinatario, Cliente remitente, ArrayList<Estado> estados) {

        this(certificado, Utils.getRandomKey(5), peso, fragil, contenido, valorDeclarado, destinatario, remitente,
                estados);
    }

    public Sobre(JSONObject sobreJson) {

        super(sobreJson);
    }

    public void setCertificado(boolean certificado) {
        this.certificado = certificado;
    }

    @Override
    public double getCosto() {

        double smlv = 1300000;
        double costo = 0;

        if (certificado = true) {
            costo = Math.round(smlv / 1000) * 2;
            costo += (costo * 10) / 100;

        } else {
            costo = Math.round((smlv / 1000) * 2);
        }

        return costo;
    }

    @Override
    public String toJSON() {

        return (new JSONObject(this)).toString(2);
    }

    @Override
    public JSONObject toJSONObject() {
        return new JSONObject(this);
    }

    public boolean isCertificado() {
        return certificado;
    }

    

    

}
