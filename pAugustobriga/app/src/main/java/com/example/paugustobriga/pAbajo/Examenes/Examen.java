package com.example.paugustobriga.pAbajo.Examenes;

public class Examen {

    private int id;
    private Trimestre tri;
    private Asignatura asig;
    private String nombre;
    private float nota;

    public Examen(){

    }

    public Examen(int id, Trimestre tri, Asignatura asig, String nombre, float nota) {
        this.id = id;
        this.tri = tri;
        this.asig = asig;
        this.nombre = nombre;
        this.nota = nota;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Trimestre getTri() {
        return tri;
    }

    public void setTri(Trimestre tri) {
        this.tri = tri;
    }

    public Asignatura getAsig() {
        return asig;
    }

    public void setAsig(Asignatura asig) {
        this.asig = asig;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getNota() {
        return nota;
    }

    public void setNota(float nota) {
        this.nota = nota;
    }
}
