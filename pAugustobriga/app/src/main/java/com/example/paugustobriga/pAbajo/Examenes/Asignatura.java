package com.example.paugustobriga.pAbajo.Examenes;

import java.util.ArrayList;

public class Asignatura {

    private String nombre;
    private ArrayList<Examen> examenes=new ArrayList<>();

    public Asignatura(){

    }

    public Asignatura( String nombre, ArrayList<Examen> examenes) {
        this.nombre = nombre;
        this.examenes = examenes;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Examen> getExamenes() {
        return examenes;
    }

    public void setExamenes(ArrayList<Examen> examenes) {
        this.examenes = examenes;
    }
}
