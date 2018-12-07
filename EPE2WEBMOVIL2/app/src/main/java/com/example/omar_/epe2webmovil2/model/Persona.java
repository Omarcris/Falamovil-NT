package com.example.omar_.epe2webmovil2.model;

public class Persona {

    private String IDP;
    private String Nombre;
    private String Apellido;
    private String Correo;
    private  String Contraseña;
    private String CuentaNT;
    public Persona() {

    }

    public String getCuentaNT() {
        return CuentaNT;
    }

    public void setCuentaNT(String cuentaNT) {
        CuentaNT = cuentaNT;
    }

    public String getContraseña() {
        return Contraseña;
    }

    public void setContraseña(String contraseña) {
        Contraseña = contraseña;
    }

    public String getIDP() {
        return IDP;
    }

    public void setIDP(String IDP) {
        this.IDP = IDP;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String apellido) {
        Apellido = apellido;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    @Override
    public String toString() {
        return Correo;
    }
}
