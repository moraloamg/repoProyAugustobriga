package com.example.paugustobriga.pAbajo.Examenes;

import java.util.ArrayList;

public class Trimestre {

    private int id;
    private int numTrimestre;
    private ArrayList<Examen> examenes = new ArrayList<>();

    public Trimestre(){

    }

    public Trimestre(int id, int numTrimestre, ArrayList<Examen> examenes) {
        this.id = id;
        this.numTrimestre = numTrimestre;
        this.examenes = examenes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumTrimestre() {
        return numTrimestre;
    }

    public void setNumTrimestre(int numTrimestre) {
        this.numTrimestre = numTrimestre;
    }

    public ArrayList<Examen> getExamenes() {
        return examenes;
    }

    public void setExamenes(ArrayList<Examen> examenes) {
        this.examenes = examenes;
    }
}
