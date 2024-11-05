package poo.model;

import java.util.ArrayList;

import org.json.JSONObject;

import poo.helpers.Utils;

public class Paquete extends Envio {
    // constructor por defecto
    public Paquete() {
    }

    // constructor parametrizado
    public Paquete(String nroGuia, String contenido, boolean fragil, int peso, int valorDeclarado, Cliente destinatario,
            Cliente remitente, ArrayList<Estado> estados) {

        this.nroGuia = nroGuia;
        this.contenido = contenido;
        this.fragil = fragil;
        this.peso = peso;
        this.valorDeclarado = valorDeclarado;
        this.destinatario = destinatario;
        this.remitente = remitente;
        this.estados = estados;
    }

    // constructor que recibe un JSONObject
    public Paquete(JSONObject json) {

        super(json);

    }

    // constructor que recibe unicamente el numero de guía
    public Paquete(String nroGuia) {
        super();
        setNroGuia(nroGuia);
    }

    // constructor copia
    public Paquete(Paquete paquete) {
        this(paquete.nroGuia, paquete.contenido, paquete.fragil, paquete.peso, paquete.valorDeclarado,
                paquete.destinatario,
                paquete.remitente, paquete.estados);
    }

    // constructor con valor aleatorio para nroGuia
    public Paquete(String contenido, boolean fragil, int peso, int valorDeclarado, Cliente destinatario,
            Cliente remitente, ArrayList<Estado> estados) {
        this(Utils.getRandomKey(8), contenido, fragil, peso, valorDeclarado, destinatario, remitente, estados);
    }

    @Override
    public double getCosto() {
        double costo = (1000 * (getPeso() / 10));
        return costo;
    }

    public String toJSON() {
        return (new JSONObject(this)).toString(8);
    }

    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (getClass() != (object.getClass())) {
            return false;
        }
        Paquete paquete = ((Paquete) object);
        return this.nroGuia.equals(paquete.nroGuia);
    }

    @Override
    public JSONObject toJSONObject() {
        return new JSONObject(this);

    }
}