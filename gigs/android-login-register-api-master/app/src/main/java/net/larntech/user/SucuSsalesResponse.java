package net.larntech.user;

import java.io.Serializable;

public class SucuSsalesResponse implements Serializable {


    /**
     * nom_sucursal : Laboratorio Clinico SERVISALUD
     * dsc_horario : Lunes a viernes de 6:00 a.m. a 5:00 p.m.rnSábados y domingos de 7:00 a.m. a 12:00 m.d.
     * dsc_direccion : Comercial El Castillo
     * num_telefono : 22253230
     * dsc_correo : siasa@ice.co.cr
     * num_latitud : 9.930847
     * num_longitud : -84.131477
     */

    private String nom_sucursal;
    private String dsc_horario;
    private String dsc_direccion;
    private String num_telefono;
    private String dsc_correo;
    private String num_latitud;
    private String num_longitud;

    public String getNom_sucursal() {
        return nom_sucursal;
    }

    public void setNom_sucursal(String nom_sucursal) {
        this.nom_sucursal = nom_sucursal;
    }

    public String getDsc_horario() {
        return dsc_horario;
    }

    public void setDsc_horario(String dsc_horario) {
        this.dsc_horario = dsc_horario;
    }

    public String getDsc_direccion() {
        return dsc_direccion;
    }

    public void setDsc_direccion(String dsc_direccion) {
        this.dsc_direccion = dsc_direccion;
    }

    public String getNum_telefono() {
        return num_telefono;
    }

    public void setNum_telefono(String num_telefono) {
        this.num_telefono = num_telefono;
    }

    public String getDsc_correo() {
        return dsc_correo;
    }

    public void setDsc_correo(String dsc_correo) {
        this.dsc_correo = dsc_correo;
    }

    public String getNum_latitud() {
        return num_latitud;
    }

    public void setNum_latitud(String num_latitud) {
        this.num_latitud = num_latitud;
    }

    public String getNum_longitud() {
        return num_longitud;
    }

    public void setNum_longitud(String num_longitud) {
        this.num_longitud = num_longitud;
    }
}
