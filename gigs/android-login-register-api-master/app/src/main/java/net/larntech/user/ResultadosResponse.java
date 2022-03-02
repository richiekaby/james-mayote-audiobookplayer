package net.larntech.user;

import java.io.Serializable;

public class ResultadosResponse implements Serializable {


    /**
     * num_solicitud : 26012200011
     * fec_registro : 26/1/2022 10:07:21
     * laboratorio : Laboratorio Clinico Quebradilla
     * estado_orden : finalizada
     */

    private String num_solicitud;
    private String fec_registro;
    private String laboratorio;
    private String estado_orden;
    private String img_qr_link;

    public String getNum_solicitud() {
        return num_solicitud;
    }

    public void setNum_solicitud(String num_solicitud) {
        this.num_solicitud = num_solicitud;
    }

    public String getFec_registro() {
        return fec_registro;
    }

    public void setFec_registro(String fec_registro) {
        this.fec_registro = fec_registro;
    }

    public String getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
    }

    public String getEstado_orden() {
        return estado_orden;
    }

    public void setEstado_orden(String estado_orden) {
        this.estado_orden = estado_orden;
    }

    public String getImg_qr_link() {
        return img_qr_link;
    }

    public void setImg_qr_link(String img_qr_link) {
        this.img_qr_link = img_qr_link;
    }
}
