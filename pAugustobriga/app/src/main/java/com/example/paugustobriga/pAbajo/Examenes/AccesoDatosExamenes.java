package com.example.paugustobriga.pAbajo.Examenes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
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


    public ArrayList<Asignatura> buscarAsignatura(String busqueda) {
        ArrayList<Asignatura> resultado=new ArrayList<Asignatura>();
        String[] campos =new String[] {"nombre"};
        String[] argumentos=new String[]{"%"+busqueda+"%"};
        SQLiteDatabase accesoLectura = examenesBD.getReadableDatabase();
        Cursor cursor = accesoLectura.query("Asignatura",campos,"nombre like ?",argumentos,null,null,null);

        while(cursor.moveToNext()){
            Asignatura asig=new Asignatura();
            asig.setNombre(cursor.getString(0));
            resultado.add(asig);
        }
        accesoLectura.close();
        return resultado;

    }

    public ArrayList<Trimestre> buscarTrimestre(String busqueda) {
        ArrayList<Trimestre> resultado=new ArrayList<Trimestre>();
        String[] campos =new String[] {"nombre"};
        String[] argumentos=new String[]{"%"+busqueda+"%"};
        SQLiteDatabase accesoLectura = examenesBD.getReadableDatabase();
        Cursor cursor = accesoLectura.query("Trimestre",campos,"nombre like ?",argumentos,null,null,null);

        while(cursor.moveToNext()){
            Trimestre tri=new Trimestre();
            tri.setNombreTrimestre(cursor.getString(0));
            resultado.add(tri);
        }
        accesoLectura.close();
        return resultado;

    }

    public ArrayList<Examen> buscarExamenes(String busqueda) {
        ArrayList<Examen> resultado=new ArrayList<Examen>();
        String[] campos =new String[] {"id","idAsignatura","idTrimestre","nota","nombre"};
        String[] argumentos=new String[]{"%"+busqueda+"%"};
        SQLiteDatabase accesoLectura = examenesBD.getReadableDatabase();
        Cursor cursor = accesoLectura.query("Examen",campos,"nombre like ?",argumentos,null,null,null);

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

    public boolean borrarAsignatura(String id) {
        boolean resultado=true;
        //poner parametros mas adelante
        try{
            SQLiteDatabase accesoLectura = examenesBD.getReadableDatabase();
            String[] argumentos=new String[]{String.valueOf(id)};
            accesoLectura.delete("Asignatura", "nombre = ?", argumentos);
            accesoLectura.close();
        }catch (SQLiteConstraintException ex){
            resultado=false;
        }
        return resultado;
    }

    public boolean borrarTrimestre(String id) {
        boolean resultado=true;
        //poner parametros mas adelante
        try{
            SQLiteDatabase accesoLectura = examenesBD.getReadableDatabase();
            String[] argumentos=new String[]{String.valueOf(id)};
            accesoLectura.delete("Trimestre", "nombre = ?", argumentos);
            accesoLectura.close();
        }catch (SQLiteConstraintException ex){
            resultado=false;
        }
        return resultado;
    }

    public void borrarExamen(int id) {
        //poner parametros mas adelante
        SQLiteDatabase accesoLectura = examenesBD.getReadableDatabase();
        String[] argumentos=new String[]{String.valueOf(id)};
        accesoLectura.delete("Examen", "id = ?", argumentos);
        accesoLectura.close();
    }

    public Examen obtenerExamen(String seleccionado) {
        Examen ex=new Examen();
        String[] campos =new String[] {"id","idAsignatura","idTrimestre","nota","nombre"};
        String[] argumentos=new String[]{seleccionado};
        SQLiteDatabase accesoLectura = examenesBD.getReadableDatabase();
        Cursor cursor = accesoLectura.query("Examen",campos,"id = ?",argumentos,null,null,null);

        if(cursor.moveToNext()){
            ex.setId(cursor.getInt(0));
            ex.setAsig(new Asignatura(cursor.getString(1),null));
            ex.setTri(new Trimestre(cursor.getString(2),null));
            ex.setNota(cursor.getFloat(3));
            ex.setNombre(cursor.getString(4));
        }
        accesoLectura.close();
        return ex;
    }

    public boolean modificarAsignatura(String id, Asignatura asignatura) {
        SQLiteDatabase accesoLectura = examenesBD.getReadableDatabase();
        String[] argumentos=new String[]{id};
        ContentValues registro=new ContentValues();
        registro.put("nombre", asignatura.getNombre());
        accesoLectura.update("Asignatura", registro, "nombre = ?", argumentos);
        accesoLectura.close();
        return  true;
    }

    public boolean editarTrimestre(String id, Trimestre trimestre) {
        SQLiteDatabase accesoLectura = examenesBD.getReadableDatabase();
        String[] argumentos=new String[]{id};
        ContentValues registro=new ContentValues();
        registro.put("nombre",trimestre.getNombreTrimestre());
        accesoLectura.update("Trimestre", registro, "nombre = ?", argumentos);
        accesoLectura.close();
        return true;
    }

    public boolean editarExamen(int id, Examen ex) {
        SQLiteDatabase accesoLectura = examenesBD.getReadableDatabase();
        String[] argumentos=new String[]{String.valueOf(id)};
        ContentValues registro=new ContentValues();
        registro.put("nombre",ex.getNombre());
        registro.put("nota",ex.getNota());
        registro.put("idAsignatura",ex.getAsig().getNombre());
        registro.put("idTrimestre",ex.getTri().getNombreTrimestre());
        accesoLectura.update("Examen", registro, "id = ?", argumentos);
        accesoLectura.close();
        return true;
    }


    //estos dos métodos se pueden juntar en uno sólo???

    public ArrayList<Examen> obtenerExamenAsig(String tabla) {
        ArrayList<Examen> resultado=new ArrayList<Examen>();
        String[] campos =new String[] {"id","idAsignatura","idTrimestre","nota","nombre"};
        String[] argumentos=new String[]{tabla};
        SQLiteDatabase accesoLectura = examenesBD.getReadableDatabase();

        Cursor cursor = accesoLectura.query("Examen",campos,"idAsignatura = ?",argumentos,null,null,null);

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

    public ArrayList<Examen> obtenerExamenTri(String tabla) {
        ArrayList<Examen> resultado=new ArrayList<Examen>();
        String[] campos =new String[] {"id","idAsignatura","idTrimestre","nota","nombre"};
        String[] argumentos=new String[]{tabla};
        SQLiteDatabase accesoLectura = examenesBD.getReadableDatabase();

        Cursor cursor = accesoLectura.query("Examen",campos,"idTrimestre = ?",argumentos,null,null,null);

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
}
