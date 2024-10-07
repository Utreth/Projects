package poo.model;

import org.json.JSONObject;

import poo.helpers.Utils;

public abstract class Envio implements Costeable, Exportable {

    protected String nroGuia;
    protected double peso;
    protected boolean fragil;
    protected String contenido;
    protected double valorDeclarado;
    protected Cliente destinatario;
    protected Cliente remitente;

    public Envio() {

    }

    public Envio(String nroGuia, double peso, boolean fragil, String contenido, double valorDeclarado,
            Cliente destinatario, Cliente remitente) {

        this.nroGuia = nroGuia;
        this.peso = peso;
        this.fragil = fragil;
        this.contenido = contenido;
        this.valorDeclarado = valorDeclarado;
        this.destinatario = destinatario;
        this.remitente = remitente;

    }

    public Envio(Envio e) {

        this(e.nroGuia, e.peso, e.fragil, e.contenido, e.valorDeclarado, e.destinatario, e.remitente);
    }

    public Envio(String nroGuia) {

        this();
        setNroGuia(nroGuia);

    }

    public Envio(double peso, boolean fragil, String contenido, double valorDeclarado,
            Cliente destinatario, Cliente remitente) {

        this(Utils.getRandomKey(5), peso, fragil, contenido, valorDeclarado, destinatario, remitente);

    }

    public Envio(JSONObject envioJson) {

        this(envioJson.getString("nroGuia"), envioJson.getDouble("peso"), envioJson.getBoolean("fragil"), envioJson);

    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getContenido() {
        return contenido;
    }

    public void setDestinatario(Cliente destinatario) {
        this.destinatario = destinatario;
    }

    public Cliente getDestinatario() {
        return destinatario;
    }

    public void setFragil(boolean fragil) {
        this.fragil = fragil;
    }

    public void setNroGuia(String nroGuia) {
        this.nroGuia = nroGuia;
    }

    public String getNroGuia() {
        return nroGuia;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getPeso() {
        return peso;
    }

    public void setRemitente(Cliente remitente) {
        this.remitente = remitente;
    }

    public void setValorDeclarado(double valorDeclarado) {
        this.valorDeclarado = valorDeclarado;
    }

    public Cliente getRemitente() {
        return remitente;
    }

    public double getValorDeclarado() {
        return valorDeclarado;
    }
}
