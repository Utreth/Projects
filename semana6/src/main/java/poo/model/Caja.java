package poo.model;

import java.util.ArrayList;

import org.json.JSONObject;

import poo.helpers.Utils;

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

    public Caja(Caja copiaCaja) {

        this(copiaCaja.largo, copiaCaja.ancho, copiaCaja.alto, copiaCaja.nroGuia, copiaCaja.peso, copiaCaja.fragil,
                copiaCaja.contenido, copiaCaja.valorDeclarado, copiaCaja.destinatario, copiaCaja.remitente,
                copiaCaja.estados);

    }

    public Caja(String nroGuia) {

        this();
        setNroGuia(nroGuia);
    }

    public Caja(double largo, double ancho, double alto, double peso, boolean fragil, String contenido,
            double valorDeclarado, Cliente destinatario, Cliente remitente, ArrayList<Estado> estados) {

        this(largo, ancho, alto, Utils.getRandomKey(5), peso, fragil, contenido, valorDeclarado, destinatario,
                remitente, estados);

    }

    public double getLargo() {
        return largo;
    }

    public void setLargo(double largo) {
        this.largo = largo;
    }

    public double getAncho() {
        return ancho;
    }

    public void setAncho(double ancho) {
        this.ancho = ancho;
    }

    public double getAlto() {
        return alto;
    }

    public void setAlto(double alto) {
        this.alto = alto;
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
