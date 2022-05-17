package com.example.paugustobriga.pAbajo.Examenes;

import java.util.ArrayList;

public class Trimestre {

    private String nombreTrimestre;
    private ArrayList<Examen> examenes = new ArrayList<>();

    public Trimestre(){

    }

    public Trimestre( String nombTrimestre, ArrayList<Examen> examenes) {
        this.nombreTrimestre = nombTrimestre;
        this.examenes = examenes;
    }


    public String getNombreTrimestre() {
        return nombreTrimestre;
    }

    public void setNombreTrimestre(String nombreTrimestre) {
        this.nombreTrimestre = nombreTrimestre;
    }

    public ArrayList<Examen> getExamenes() {
        return examenes;
    }

    public void setExamenes(ArrayList<Examen> examenes) {
        this.examenes = examenes;
    }
}
