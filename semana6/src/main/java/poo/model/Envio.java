package poo.model;

import java.util.ArrayList;

import org.json.JSONArray;
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
    protected ArrayList<Estado> estados = new ArrayList<>();

    public Envio() {

    }

    public Envio(String nroGuia, double peso, boolean fragil, String contenido, double valorDeclarado,
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

    public Envio(Envio e) {

        this(e.nroGuia, e.peso, e.fragil, e.contenido, e.valorDeclarado, e.destinatario, e.remitente, e.estados);
    }

    public Envio(String nroGuia) {

        this();
        setNroGuia(nroGuia);

    }

    public Envio(double peso, boolean fragil, String contenido, double valorDeclarado,
            Cliente destinatario, Cliente remitente, ArrayList<Estado> estados) {

        this(Utils.getRandomKey(5), peso, fragil, contenido, valorDeclarado, destinatario, remitente, estados);

    }

    public Envio(JSONObject envioJson) {

        this.nroGuia = envioJson.getString("nroGuia");
        this.peso = envioJson.getDouble("peso");
        this.fragil = envioJson.getBoolean("fragil");
        this.contenido = envioJson.getString("contenido");
        this.valorDeclarado = envioJson.getDouble("valorDeclarado");
        JSONObject remitenteJson = envioJson.getJSONObject("remitente");
        this.remitente = new Cliente(remitenteJson);
        JSONObject destinatarioJson = envioJson.getJSONObject("destinatario");
        this.destinatario = new Cliente(destinatarioJson);
        JSONArray listaEstados = envioJson.getJSONArray("estados");

        for (int i = 0; i < listaEstados.length(); i++) {

            this.estados.add(new Estado(listaEstados.getJSONObject(i)));

        }

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

    public String getTipo() {

        return this.getClass().getSimpleName();
    }

    @Override
    public JSONObject toJSONObject() {

        JSONObject jsonEnvio = new JSONObject();
        JSONArray jsonList = new JSONArray(estados);
        jsonEnvio.put("nroGuia", this.nroGuia);
        jsonEnvio.put("peso", this.peso);
        jsonEnvio.put("fragil", this.fragil);
        jsonEnvio.put("contenido", this.contenido);
        jsonEnvio.put("valorDeclarado", this.valorDeclarado);
        jsonEnvio.put("destinatario", this.destinatario.toJSONObject());
        jsonEnvio.put("remitente", this.remitente.toJSONObject());
        jsonEnvio.put("estados", jsonList);

        return jsonEnvio;
    }

    public String toJSON() {

        return (new JSONObject(this)).toString(2);

    }
}
