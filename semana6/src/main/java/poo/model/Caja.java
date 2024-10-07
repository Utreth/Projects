package poo.model;

import java.util.ArrayList;

import org.json.JSONObject;

public class Caja extends Envio {

    private double largo;
    private double ancho;
    private double alto;

    public Caja() {

    }

    public Caja(double largo, double ancho, double alto, String nroGuia, double peso, boolean fragil, String contenido,
            double valorDeclarado, Cliente destinatario, Cliente remitente, ArrayList<Estado> estados) {

        this.largo = largo;
        this.ancho = ancho;
        this.alto = alto;
        this.nroGuia = nroGuia;
        this.peso = peso;
        this.fragil = fragil;
        this.contenido = contenido;
        this.valorDeclarado = valorDeclarado;
        this.destinatario = destinatario;
        this.remitente = remitente;
        this.estados = estados;

    }

    public Caja(String nroGuia){


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

        return null;
    }

}
