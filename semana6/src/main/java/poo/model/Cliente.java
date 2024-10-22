package poo.model;

import org.json.JSONObject;

import poo.helpers.Utils;

public class Cliente implements Exportable {

    private String id;
    private String nombre;
    private String direccion;
    private String telefono;
    private String ciudad;


    public Cliente() {

        id = "00000";
        nombre = "Nn";
        direccion = "///";
        telefono = "12345";
        ciudad = "null";

    }

    public Cliente(String id, String nombre, String direccion, String ciudad, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.telefono = telefono;
    }

    public Cliente(Cliente c) {

        this(c.id, c.nombre, c.direccion, c.telefono, c.ciudad);
    }

    public Cliente(String id) {
        this();
        setId(id);
    }

    public Cliente(String nombre, String direccion, String ciudad, String telefono) {

        this(Utils.getRandomKey(5), nombre, direccion, ciudad, telefono);
    }

    public Cliente(JSONObject clienteJson) {

        this(clienteJson.getString("id"), clienteJson.getString("nombre"), clienteJson.getString("direccion"), clienteJson.getString("ciudad"),
                clienteJson.getString("telefono"));

    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public JSONObject toJSONObject() {

        return new JSONObject(this);

    }

    @Override
    public String toString() {

        return (new JSONObject(this)).toString(2);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (this.getClass() != obj.getClass()) {
            return false;
        }

        Cliente c = (Cliente) obj;
        return this.id.equals(c.id);

    }

}
