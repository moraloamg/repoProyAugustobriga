package com.example.paugustobriga.pAbajo.Examenes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.paugustobriga.pAbajo.Agenda.Tarea;

import java.text.ParseException;
import java.util.ArrayList;

public class AccesoDatosExamenes {

    private Context contexto;
    ExamenesBD examenesBD;

    public AccesoDatosExamenes(Context context){
        this.contexto = context;
        examenesBD = new ExamenesBD(context);

    }

    public ArrayList<Asignatura> obtenerAsignaturas() {
        ArrayList<Asignatura> resultado=new ArrayList<Asignatura>();
        String[] campos =new String[] {"nombre"};
        SQLiteDatabase accesoLectura = examenesBD.getReadableDatabase();
        Cursor cursor = accesoLectura.query("Asignatura",campos,null,null,null,null,null);

        while(cursor.moveToNext()){
            Asignatura asig=new Asignatura();
            asig.setNombre(cursor.getString(0));
            resultado.add(asig);
        }
        accesoLectura.close();
        return resultado;
    }

    public ArrayList<Trimestre> obtenerTrimestres() {
        ArrayList<Trimestre> resultado=new ArrayList<Trimestre>();
        String[] campos =new String[] {"nombre"};
        SQLiteDatabase accesoLectura = examenesBD.getReadableDatabase();
        Cursor cursor = accesoLectura.query("Trimestre",campos,null,null,null,null,null);

        while(cursor.moveToNext()){
            Trimestre tri=new Trimestre();
            tri.setNombreTrimestre(cursor.getString(0));
            resultado.add(tri);
        }
        accesoLectura.close();
        return resultado;
    }

    public ArrayList<Examen> obtenerExamenes() {
        ArrayList<Examen> resultado=new ArrayList<Examen>();
        String[] campos =new String[] {"id","idAsignatura","idTrimestre","nota","nombre"};
        SQLiteDatabase accesoLectura = examenesBD.getReadableDatabase();
        Cursor cursor = accesoLectura.query("Examen",campos,null,null,null,null,null);

        while(cursor.moveToNext()){
            Examen ex = new Examen();
            ex.setId(cursor.getInt(0));
            ex.setAsig(new Asignatura(cursor.getString(1),null));
            ex.setTri(new Trimestre(cursor.getString(2),null));
            ex.setNota(cursor.getFloat(3));
            ex.setNombre(cursor.getString(4));
            resultado.add(ex);
        }
        accesoLectura.close();
        return resultado;
    }

    public boolean insertarExamen(Examen ex) {
        boolean devolucion = false;
        SQLiteDatabase accesoEscritura = examenesBD.getWritableDatabase();

        ContentValues registro=new ContentValues();
        registro.put("idAsignatura",ex.getAsig().getNombre());
        registro.put("idTrimestre",ex.getTri().getNombreTrimestre());
        registro.put("nota",ex.getNota());
        registro.put("nombre",ex.getNombre());

        long resultado=accesoEscritura.insert("Examen",null,registro);
        if(resultado!=-1){
            Toast.makeText(contexto, "Se ha creado el registro con id "+resultado, Toast.LENGTH_SHORT).show();
            devolucion = true;
        }else{
            Toast.makeText(contexto, "Error de inserción "+resultado, Toast.LENGTH_LONG).show();
        }
        accesoEscritura.close();
        return  devolucion;
    }

    public boolean insertarTrimestre(Trimestre tri) {
        boolean devolucion = false;
        SQLiteDatabase accesoEscritura = examenesBD.getWritableDatabase();

        ContentValues registro=new ContentValues();
        registro.put("nombre",tri.getNombreTrimestre());


        long resultado=accesoEscritura.insert("Trimestre",null,registro);
        if(resultado!=-1){
            Toast.makeText(contexto, "Se ha creado el registro con id "+resultado, Toast.LENGTH_SHORT).show();
            devolucion = true;
        }else{
            Toast.makeText(contexto, "Error de inserción "+resultado, Toast.LENGTH_LONG).show();
        }
        accesoEscritura.close();
        return  devolucion;
    }

    public boolean insertarAsignatura(Asignatura aisg) {
        boolean devolucion = false;
        SQLiteDatabase accesoEscritura = examenesBD.getWritableDatabase();

        ContentValues registro=new ContentValues();
        registro.put("nombre",aisg.getNombre());


        long resultado=accesoEscritura.insert("Asignatura",null,registro);
        if(resultado!=-1){
            Toast.makeText(contexto, "Se ha creado el registro con id "+resultado, Toast.LENGTH_SHORT).show();
            devolucion = true;
        }else{
            Toast.makeText(contexto, "Error de inserción "+resultado, Toast.LENGTH_LONG).show();
        }
        accesoEscritura.close();
        return  devolucion;
    }


}
