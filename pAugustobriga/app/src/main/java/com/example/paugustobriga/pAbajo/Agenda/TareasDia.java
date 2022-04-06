package com.example.paugustobriga.pAbajo.Agenda;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Esta clase hace funciones estadísticas sobre las tareas asignadas a un día
 */
public class TareasDia {
    static SimpleDateFormat formato=new SimpleDateFormat("dd/mm/yyyy");

    private Date fecha;

    //estos atributos se rellenarán en función de los atributos de las tareas
    private int tareasCompletadas;
    private boolean todasTareasCompletadas;
    private boolean tareasDiaPasada;

    ArrayList<Tarea> listaTareas = new ArrayList<>();

    //este atributo dispondrá a partir de la fecha
    private String fechaFormateada;

    public TareasDia(){

    }

    public TareasDia(Date fecha, ArrayList<Tarea> listaTareas) {
        //importante poner estas líneas en este orden
        this.fecha = fecha;
        this.listaTareas = listaTareas;
        this.fechaFormateada = fechaFormateada();
        this.tareasCompletadas = comprobarTareasCompletadas();
        this.todasTareasCompletadas = comprobarTodasTareasCompletadas();
        this.tareasDiaPasada = comprobarTareaPasada();
    }

    public Date getFecha() {
        return fecha;
    }

    public int getTareasCompletadas() {
        return tareasCompletadas;
    }

    public boolean isTodasTareasCompletadas() {
        return todasTareasCompletadas;
    }

    public boolean isTareasDiaPasada() {
        return tareasDiaPasada;
    }

    public ArrayList<Tarea> getListaTareas() {
        return listaTareas;
    }

    public String getFechaFormateada() {
        return fechaFormateada;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
        this.fechaFormateada = fechaFormateada();
    }

    public void setListaTareas(ArrayList<Tarea> listaTareas) {
        this.listaTareas = listaTareas;
    }

    public int comprobarTareasCompletadas(){
        int contTareas=0;
        for(Tarea t:listaTareas){
            if(t.isRealizado()){
                contTareas++;
            }
        }
        return contTareas;
    }

    public boolean comprobarTodasTareasCompletadas(){
        boolean resultado=false;
        if(this.tareasCompletadas == listaTareas.size()){
            resultado=true;
        }
        return  resultado;
    }

    public boolean comprobarTareaPasada(){
        boolean resultado=false;
        if(new Date().after(this.fecha)){
            resultado=true;
        }
        return  resultado;
    }

    public String fechaFormateada(){
        String[] cadena = formato.format(this.fecha).split("/");
        String mes="";
        switch (cadena[1]){
            case "01":
                mes="enero";
                break;
            case "02":
                mes="febrero";
                break;
            case "03":
                mes="marzo";
                break;
            case "04":
                mes="abril";
                break;
            case "05":
                mes="mayo";
                break;
            case "06":
                mes="junio";
                break;
            case "07":
                mes="julio";
                break;
            case "08":
                mes="agosto";
                break;
            case "09":
                mes="septiembre";
                break;
            case "10":
                mes="octubre";
                break;
            case "11":
                mes="noviembre";
                break;
            case "12":
                mes="diciembre";
                break;
        }
        return cadena[0]+" de "+mes+" de "+cadena[2];
    }
}
