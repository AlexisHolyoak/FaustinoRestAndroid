package com.alexisholyoak.faustinorest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Faustino {

    @SerializedName("id_usuario")
    @Expose
    private String idUsuario;
    @SerializedName("contrasena")
    @Expose
    private String contrasena;
    @SerializedName("f_registro")
    @Expose
    private String fRegistro;
    @SerializedName("estado")
    @Expose
    private Integer estado;

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getFRegistro() {
        return fRegistro;
    }

    public void setFRegistro(String fRegistro) {
        this.fRegistro = fRegistro;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

}