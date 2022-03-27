package com.example.paugustobriga.pAbajo.Agenda;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Tarea {
    static SimpleDateFormat formato = new SimpleDateFormat("dd/mm/yyyy");

    private Date fecha;
    private String descripcion;
    private Date notificacion;
    private boolean realizado;
    private String tipo;

    public Tarea(){

    }

    public Tarea(Date fecha, String descripcion, Date notificacion, boolean realizado, String tipo) {
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.notificacion = notificacion;
        this.realizado = realizado;
        this.tipo = tipo;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Date getNotificacion() {
        return notificacion;
    }

    public boolean isRealizado() {
        return realizado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setNotificacion(Date notificacion) {
        this.notificacion = notificacion;
    }

    public void setRealizado(boolean realizado) {
        this.realizado = realizado;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String fechaFormateada(){
        return formato.format(this.fecha);
    }
}
