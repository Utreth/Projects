package poo.model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import poo.helpers.Utils;

public abstract class Envio implements Costeable, Exportable {
    protected String nroGuia;
    protected String contenido;
    protected boolean fragil;
    protected int peso;
    protected int valorDeclarado;
    protected Cliente destinatario;
    protected Cliente remitente;
    protected ArrayList<Estado> estados = new ArrayList<>();

    // constructor por defecto
    public Envio() {
    }

    // constructor parametrizado
    public Envio(String nroGuia, String contenido, boolean fragil, int peso, int valorDeclarado, Cliente destinatario,
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

    // constructor copia
    public Envio(Envio envio) {
        this(envio.nroGuia, envio.contenido, envio.fragil, envio.peso, envio.valorDeclarado, envio.destinatario,
                envio.remitente, envio.estados);
    }

    // constructor que recibe un JSONObject
    public Envio(JSONObject json) {
        this.nroGuia = json.getString("nroGuia");
        this.contenido = json.getString("contenido");
        this.fragil = json.getBoolean("fragil");
        this.peso = json.getInt("peso");
        this.valorDeclarado = json.getInt("valorDeclarado");
        

        JSONObject remitenteJson = json.getJSONObject("remitente");
        this.remitente = new Cliente(remitenteJson);

        JSONObject destinatarioJson = json.getJSONObject("destinatario");
        this.destinatario = new Cliente(destinatarioJson);
        JSONArray jsonArray = json.getJSONArray("estados");
        for (int i = 0; i < jsonArray.length(); i++) {
            this.estados.add(new Estado(jsonArray.getJSONObject(i)));
        }
    }

    // constructor que recibe unicamente el numero de guía
    public Envio(String Envio) {
        this();
        setNroGuia(nroGuia);
    }

    // constructor con valor aleatorio para nroGuia
    public Envio(String contenido, boolean fragil, int peso, int valorDeclarado, Cliente destinatario,
            Cliente remitente, ArrayList<Estado> estados) {
        this(Utils.getRandomKey(5), contenido, fragil, peso, valorDeclarado, destinatario, remitente, estados);
    }

    public String getNroGuia() {
        return nroGuia;
    }

    public void setNroGuia(String nroGuia) {
        this.nroGuia = nroGuia;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public boolean isFragil() {
        return fragil;
    }

    public void setFragil(boolean fragil) {
        this.fragil = fragil;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public int getValorDeclarado() {
        return valorDeclarado;
    }

    public void setValorDeclarado(int valorDeclarado) {
        this.valorDeclarado = valorDeclarado;
    }

    public Cliente getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(Cliente destinatario) {
        this.destinatario = destinatario;
    }

    public Cliente getRemitente() {
        return remitente;
    }

    public void setRemitente(Cliente remitente) {
        this.remitente = remitente;
    }

    public ArrayList<Estado> getEstados() {
        return estados;
    }

    public void setEstados(ArrayList<Estado> estados) {
        this.estados = estados;
    }

    public String getTipo() {
        return this.getClass().getSimpleName();
    }

    @Override
    public double getCosto() {
        throw new UnsupportedOperationException("Unimplemented method 'getCosto'");
    }

    @Override
    public JSONObject toJSONObject() {
        return new JSONObject(this);

    }

    @Override
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
        Envio envio = (Envio) object;
        return this.nroGuia != null && this.nroGuia.equals(envio.nroGuia);
    }

    public String getIdEnvio() {
        return this.nroGuia;
    }
}