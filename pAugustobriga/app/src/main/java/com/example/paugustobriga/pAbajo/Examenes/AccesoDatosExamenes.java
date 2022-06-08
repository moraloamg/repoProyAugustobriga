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
        try {
            SQLiteDatabase accesoEscritura = examenesBD.getWritableDatabase();

            ContentValues registro = new ContentValues();
            registro.put("idAsignatura", ex.getAsig().getNombre());
            registro.put("idTrimestre", ex.getTri().getNombreTrimestre());
            registro.put("nota", ex.getNota());
            registro.put("nombre", ex.getNombre());

            long resultado = accesoEscritura.insert("Examen", null, registro);
            accesoEscritura.close();
            if (resultado != -1) {
                return true;
            } else {
                return false;
            }
        }catch (Exception exc){
            return false;
        }
    }

    public boolean insertarTrimestre(Trimestre tri) {
        try{
            SQLiteDatabase accesoEscritura = examenesBD.getWritableDatabase();

            ContentValues registro=new ContentValues();
            registro.put("nombre",tri.getNombreTrimestre());


            long resultado=accesoEscritura.insert("Trimestre",null,registro);
            accesoEscritura.close();
            if(resultado!=-1){
                return true;
            }else {
                return false;
            }
        }catch (Exception exc){
            return false;
        }
    }

    public boolean insertarAsignatura(Asignatura aisg) {
        try{
            SQLiteDatabase accesoEscritura = examenesBD.getWritableDatabase();

            ContentValues registro=new ContentValues();
            registro.put("nombre",aisg.getNombre());


            long resultado=accesoEscritura.insert("Asignatura",null,registro);
            accesoEscritura.close();
            if(resultado!=-1){
                return true;
            }else{
                return false;
            }
        }catch (Exception exc){
            return false;
        }


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

    public boolean borrarExamen(int id) {
        Boolean resultado = true;
        try{
            SQLiteDatabase accesoLectura = examenesBD.getReadableDatabase();
            String[] argumentos=new String[]{String.valueOf(id)};
            accesoLectura.delete("Examen", "id = ?", argumentos);
            accesoLectura.close();
        }catch (Exception ex){
            resultado=false;
        }
        return resultado;
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
        Boolean resultado = true;
        try{
            SQLiteDatabase accesoLectura = examenesBD.getReadableDatabase();
            String[] argumentos=new String[]{id};
            ContentValues registro=new ContentValues();
            registro.put("nombre", asignatura.getNombre());
            accesoLectura.update("Asignatura", registro, "nombre = ?", argumentos);
            accesoLectura.close();
        }catch (Exception ex){
            resultado=false;
        }
        return resultado;
    }

    public boolean editarTrimestre(String id, Trimestre trimestre) {
        Boolean resultado = true;
        try{
            SQLiteDatabase accesoLectura = examenesBD.getReadableDatabase();
            String[] argumentos=new String[]{id};
            ContentValues registro=new ContentValues();
            registro.put("nombre",trimestre.getNombreTrimestre());
            accesoLectura.update("Trimestre", registro, "nombre = ?", argumentos);
            accesoLectura.close();
        }catch (Exception ex){
            resultado=false;
        }
        return resultado;
    }

    public boolean editarExamen(int id, Examen ex) {
        Boolean resultado = true;
        try{
            SQLiteDatabase accesoLectura = examenesBD.getReadableDatabase();
            String[] argumentos=new String[]{String.valueOf(id)};
            ContentValues registro=new ContentValues();
            registro.put("nombre",ex.getNombre());
            registro.put("nota",ex.getNota());
            registro.put("idAsignatura",ex.getAsig().getNombre());
            registro.put("idTrimestre",ex.getTri().getNombreTrimestre());
            accesoLectura.update("Examen", registro, "id = ?", argumentos);
            accesoLectura.close();
        }catch (Exception exc){
            resultado=false;
        }
        return resultado;
    }


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

    public int obtenerSuspensos() {
        int cantidad = 0;
        Cursor cursor = examenesBD.getReadableDatabase().rawQuery("select count(*) from Examen where nota < 5 ",null);
        if(cursor.moveToNext()){
            cantidad = cursor.getInt(0);
        }
        cursor.close();
        return cantidad;
    }

    public int obtenerAprobados() {
        int cantidad = 0;
        Cursor cursor = examenesBD.getReadableDatabase().rawQuery("select count(*) from Examen where nota >= 5 ",null);
        if(cursor.moveToNext()){
            cantidad = cursor.getInt(0);
        }
        cursor.close();
        return cantidad;
    }

    public ArrayList<Object[]> aprobadosPorTrimestre(){
        ArrayList<Object[]> listaTrimestres = new ArrayList<Object[]>();
        Cursor cursor = examenesBD.getReadableDatabase().rawQuery(
                "select t.nombre, count(*) from Trimestre as t " +
                        "inner join Examen as e on t.nombre=e.idTrimestre where nota >= 5 group by t.nombre",null);
        while(cursor.moveToNext()){
            String trimestre=cursor.getString(0);
            int cantidad = cursor.getInt(1);
            listaTrimestres.add(new Object[]{trimestre,cantidad});
        }
        cursor.close();
        return listaTrimestres;
    }

    public ArrayList<Object[]> aprobadosPorAsignatura() {
        ArrayList<Object[]> aprobadosAsignatura = new ArrayList<Object[]>();
        Cursor cursor = examenesBD.getReadableDatabase().rawQuery(
                "select asig.nombre, count(*) from Asignatura as asig " +
                        "inner join Examen as e on asig.nombre=e.idAsignatura where nota >= 5 group by asig.nombre",null);
        while(cursor.moveToNext()){
            String asignatura=cursor.getString(0);
            int cantidad = cursor.getInt(1);
            aprobadosAsignatura.add(new Object[]{asignatura,cantidad});
        }
        cursor.close();
        return aprobadosAsignatura;

    }

    public ArrayList<Object[]> suspensosPorAsignatura() {
        ArrayList<Object[]> suspensosAsignatura = new ArrayList<Object[]>();
        Cursor cursor = examenesBD.getReadableDatabase().rawQuery(
                "select asig.nombre, count(*) from Asignatura as asig " +
                        "inner join Examen as e on asig.nombre=e.idAsignatura where nota < 5 group by asig.nombre",null);
        while(cursor.moveToNext()){
            String asignatura=cursor.getString(0);
            int cantidad = cursor.getInt(1);
            suspensosAsignatura.add(new Object[]{asignatura,cantidad});
        }
        cursor.close();
        return suspensosAsignatura;
    }
}
