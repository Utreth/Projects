package poo.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import poo.helpers.Utils;

public class Caja extends Envio {

    private double largo;
    private double ancho;
    private double alto;

    public Caja() {

    }

    public Caja(double largo, double ancho, double alto, String nroGuia, int peso, boolean fragil, String contenido,
            int valorDeclarado, Cliente destinatario, Cliente remitente, ArrayList<Estado> estados) {

        setLargo(largo);
        setAncho(ancho);
        setAlto(alto);
        setNroGuia(nroGuia);
        setPeso(peso);
        setFragil(fragil);
        setContenido(contenido);
        setValorDeclarado(valorDeclarado);
        setDestinatario(destinatario);
        setRemitente(remitente);
        setEstados(estados);

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

    public Caja(double largo, double ancho, double alto, int peso, boolean fragil, String contenido,
            int valorDeclarado, Cliente destinatario, Cliente remitente, ArrayList<Estado> estados) {

        this(largo, ancho, alto, Utils.getRandomKey(5), peso, fragil, contenido, valorDeclarado, destinatario,
                remitente, estados);

    }

    public Caja(JSONObject json) {

        this.nroGuia = json.getString("nroGuia");
        this.contenido = json.getString("contenido");
        this.fragil = json.getBoolean("fragil");
        this.peso = json.getInt("peso");
        this.valorDeclarado = json.getInt("valorDeclarado");
        this.alto = json.getDouble("alto");
        this.ancho = json.getDouble("ancho");
        this.largo = json.getDouble("largo");
        JSONObject remitenteJson = json.getJSONObject("remitente");
        this.remitente = new Cliente(remitenteJson);
        JSONObject destinatarioJson = json.getJSONObject("destinatario");
        this.destinatario = new Cliente(destinatarioJson);
        JSONArray jsonArray = json.getJSONArray("estados");
        for (int i = 0; i < jsonArray.length(); i++) {
            this.estados.add(new Estado(jsonArray.getJSONObject(i)));
        }
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
        double volumen = getAncho() * getLargo() * getAlto();
        double costo = 0f;
        if (volumen <= 0.5) {
            costo = (10000 + (500 * getPeso()));
        }
        if (volumen <= 1.0 && volumen > 0.5) {
            costo = (12000 + (500 * getPeso()));
        }
        if (volumen <= 3.0 && volumen > 1.0) {
            costo = (15000 + (500 * getPeso()));
        }
        if (volumen <= 6.0 && volumen > 3.0) {
            costo = (25000 + (500 * getPeso()));
        }
        if (volumen <= 10.0 && volumen > 6.0) {
            costo = (30000 + (500 * getPeso()));
        }
        if (volumen > 10.0) {
            costo = (10000 * (volumen / 10) + (500 * getPeso()));
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

}
