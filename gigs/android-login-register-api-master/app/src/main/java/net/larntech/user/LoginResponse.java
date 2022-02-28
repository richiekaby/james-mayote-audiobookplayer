package net.larntech.user;

import java.io.Serializable;

public class LoginResponse implements Serializable {


    /**
     * nom_paciente : MASIS  OSCAR
     */

    private String nom_paciente;

    public String getNom_paciente() {
        return nom_paciente;
    }

    public void setNom_paciente(String nom_paciente) {
        this.nom_paciente = nom_paciente;
    }
}
