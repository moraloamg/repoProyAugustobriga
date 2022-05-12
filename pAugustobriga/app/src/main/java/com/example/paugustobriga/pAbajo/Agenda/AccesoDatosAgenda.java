package com.example.paugustobriga.pAbajo.Agenda;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AccesoDatosAgenda {

    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat formatoFinal = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private Context contexto;
    TareasBD miBD;

    public AccesoDatosAgenda(Context context){
        this.contexto=context;
        miBD = new TareasBD(context);
    }

    public void borrar(int id){
        //poner parametros mas adelante
        SQLiteDatabase accesoLectura = miBD.getReadableDatabase();
        String[] argumentos=new String[]{String.valueOf(id)};
        accesoLectura.delete("tareas", "id = ?", argumentos);
        accesoLectura.close();
    }

    public void modificarDescripcion(int id, String descripcion){
        SQLiteDatabase accesoLectura = miBD.getReadableDatabase();
        String[] argumentos=new String[]{String.valueOf(id)};
        ContentValues registro=new ContentValues();
        registro.put("descripcion",descripcion);
        accesoLectura.update("tareas", registro, "id = ?", argumentos);
        accesoLectura.close();
    }

    public void realizarTarea(int id){
        SQLiteDatabase accesoLectura = miBD.getReadableDatabase();
        String[] argumentos=new String[]{String.valueOf(id)};
        ContentValues registro=new ContentValues();
        registro.put("realizado",true);
        accesoLectura.update("tareas", registro, "id = ?", argumentos);
        accesoLectura.close();
    }

    public void modificarTipo(int id, String tipo){
        SQLiteDatabase accesoLectura = miBD.getReadableDatabase();
        String[] argumentos=new String[]{String.valueOf(id)};
        ContentValues registro=new ContentValues();
        registro.put("tipo",tipo);
        accesoLectura.update("tareas", registro, "id = ?", argumentos);
        accesoLectura.close();
    }

    public void modificarNotificacion(int id, String fecha){
        SQLiteDatabase accesoLectura = miBD.getReadableDatabase();
        String[] argumentos=new String[]{String.valueOf(id)};
        ContentValues registro=new ContentValues();
        registro.put("notificacion",fecha);
        accesoLectura.update("tareas", registro, "id = ?", argumentos);
        accesoLectura.close();
    }

    public String obtenerDescripcion(String id){
        String resultado="";
        String[] campos =new String[] {"descripcion"};
        String[] argumentos=new String[]{id};
        SQLiteDatabase accesoLectura = miBD.getReadableDatabase();

        Cursor cursor = accesoLectura.query("tareas",campos,"id = ?",argumentos,null,null,null);

        if(cursor.moveToNext()){
            resultado=cursor.getString(0);
        }
        accesoLectura.close();
        return resultado;
    }

    public String obtenerTipo(String id){
        String resultado="";
        String[] campos =new String[] {"tipo"};
        String[] argumentos=new String[]{id};
        SQLiteDatabase accesoLectura = miBD.getReadableDatabase();

        Cursor cursor = accesoLectura.query("tareas",campos,"id = ?",argumentos,null,null,null);

        if(cursor.moveToNext()){
            resultado=cursor.getString(0);
        }
        accesoLectura.close();
        return resultado;
    }

    public String obtenerNotificacion(String id){
        String resultado="";
        String[] campos =new String[] {"notificacion"};
        String[] argumentos=new String[]{id};
        SQLiteDatabase accesoLectura = miBD.getReadableDatabase();

        Cursor cursor = accesoLectura.query("tareas",campos,"id = ?",argumentos,null,null,null);

        if(cursor.moveToNext()){
            resultado=cursor.getString(0);
        }
        accesoLectura.close();
        return resultado;
    }

    public ArrayList<Tarea> obtenerTareasDia(String fecha){
        ArrayList<Tarea> resultado=new ArrayList<Tarea>();
        String[] campos =new String[] {"id","fecha","descripcion","notificacion","realizado","tipo"};
        String[] argumentos=new String[]{fecha};
        SQLiteDatabase accesoLectura = miBD.getReadableDatabase();

        Cursor cursor = accesoLectura.query("tareas",campos,"fecha = ?",argumentos,null,null,null);

        while(cursor.moveToNext()){
            Tarea t=new Tarea();
            try {
                t.setId(cursor.getInt(0));
                t.setFecha(formato.parse(cursor.getString(1)));
                t.setDescripcion(cursor.getString(2));
                if(cursor.getString(3)!=null){
                    t.setNotificacion(formatoFinal.parse(cursor.getString(3)));
                }


            } catch (ParseException e) {
                t.setNotificacion(null);
            }
            t.setRealizado(cursor.getInt(4) > 0);
            t.setTipo(cursor.getString(5));
            resultado.add(t);
        }
        accesoLectura.close();
        return resultado;
    }

    public ArrayList<Tarea> obtenerTareas(){
        ArrayList<Tarea> resultado=new ArrayList<Tarea>();
        String[] campos =new String[] {"id","fecha","descripcion","notificacion","realizado","tipo"};
        SQLiteDatabase accesoLectura = miBD.getReadableDatabase();
        Cursor cursor = accesoLectura.query("tareas",campos,null,null,null,null,null);

        while(cursor.moveToNext()){
            Tarea t=new Tarea();
            try {
                t.setId(cursor.getInt(0));
                t.setFecha(formato.parse(cursor.getString(1)));
                t.setDescripcion(cursor.getString(2));
                if (cursor.getString(3) != null) {
                    t.setNotificacion(formatoFinal.parse(cursor.getString(3)));
                }


            } catch (ParseException e) {
                t.setNotificacion(null);
            }
            t.setRealizado(cursor.getInt(4) > 0);
            t.setTipo(cursor.getString(5));
            resultado.add(t);
        }
        accesoLectura.close();
        return resultado;
    }

    public ArrayList<Tarea> buscarTarea(String busqueda){
        ArrayList<Tarea> resultado=new ArrayList<Tarea>();
        String[] campos =new String[] {"id","fecha","descripcion","notificacion","realizado","tipo"};
        String[] argumentos=new String[]{"%"+busqueda+"%"};
        SQLiteDatabase accesoLectura = miBD.getReadableDatabase();
        Cursor cursor = accesoLectura.query("tareas",campos,"descripcion like ?",argumentos,null,null,null);

        while(cursor.moveToNext()){
            Tarea t=new Tarea();
            try {
                t.setId(cursor.getInt(0));
                t.setFecha(formato.parse(cursor.getString(1)));
                t.setDescripcion(cursor.getString(2));
                if (cursor.getString(3) != null) {
                    t.setNotificacion(formatoFinal.parse(cursor.getString(3)));
                }

            } catch (ParseException e) {
                t.setNotificacion(null);
            }
            t.setRealizado(cursor.getInt(4) > 0);
            t.setTipo(cursor.getString(5));
            resultado.add(t);
        }
        accesoLectura.close();
        return resultado;
    }

    public ArrayList<Tarea> buscarTipo(String tipo){
        ArrayList<Tarea> resultado=new ArrayList<Tarea>();
        String[] campos =new String[] {"id","fecha","descripcion","notificacion","realizado","tipo"};
        String[] argumentos=new String[]{tipo};
        SQLiteDatabase accesoLectura = miBD.getReadableDatabase();
        Cursor cursor = accesoLectura.query("tareas",campos,"tipo = ?",argumentos,null,null,null);

        while(cursor.moveToNext()){
            Tarea t=new Tarea();
            try {
                t.setId(cursor.getInt(0));
                t.setFecha(formato.parse(cursor.getString(1)));
                t.setDescripcion(cursor.getString(2));
                if (cursor.getString(3) != null) {
                    t.setNotificacion(formatoFinal.parse(cursor.getString(3)));
                }

            } catch (ParseException e) {
                t.setNotificacion(null);
            }
            t.setRealizado(cursor.getInt(4) > 0);
            t.setTipo(cursor.getString(5));
            resultado.add(t);
        }
        accesoLectura.close();
        return resultado;
    }

    public ArrayList<Tarea> buscarRealizadas(){
        ArrayList<Tarea> resultado=new ArrayList<Tarea>();
        String[] campos =new String[] {"id","fecha","descripcion","notificacion","realizado","tipo"};
        SQLiteDatabase accesoLectura = miBD.getReadableDatabase();
        Cursor cursor = accesoLectura.query("tareas",campos,"realizado = true",null,null,null,null);

        while(cursor.moveToNext()){
            Tarea t=new Tarea();
            try {
                t.setId(cursor.getInt(0));
                t.setFecha(formato.parse(cursor.getString(1)));
                t.setDescripcion(cursor.getString(2));
                if (cursor.getString(3) != null) {
                    t.setNotificacion(formatoFinal.parse(cursor.getString(3)));
                }

            } catch (ParseException e) {
                t.setNotificacion(null);
            }
            t.setRealizado(cursor.getInt(4) > 0);
            t.setTipo(cursor.getString(5));
            resultado.add(t);
        }
        accesoLectura.close();
        return resultado;
    }

    public ArrayList<Tarea> buscarNoRealizadas(){
        ArrayList<Tarea> resultado=new ArrayList<Tarea>();
        String[] campos =new String[] {"id","fecha","descripcion","notificacion","realizado","tipo"};
        SQLiteDatabase accesoLectura = miBD.getReadableDatabase();
        Cursor cursor = accesoLectura.query("tareas",campos,"realizado = false",null,null,null,null);

        while(cursor.moveToNext()){
            Tarea t=new Tarea();
            try {
                t.setId(cursor.getInt(0));
                t.setFecha(formato.parse(cursor.getString(1)));
                t.setDescripcion(cursor.getString(2));
                if (cursor.getString(3) != null) {
                    t.setNotificacion(formatoFinal.parse(cursor.getString(3)));
                }

            } catch (ParseException e) {
                t.setNotificacion(null);
            }
            t.setRealizado(cursor.getInt(4) > 0);
            t.setTipo(cursor.getString(5));
            resultado.add(t);
        }
        accesoLectura.close();
        return resultado;
    }

    public void insertarTarea(Tarea t){
        SQLiteDatabase accesoEscritura = miBD.getWritableDatabase();

        ContentValues registro=new ContentValues();
        registro.put("fecha",formato.format(t.getFecha()));
        registro.put("descripcion",t.getDescripcion());
        if(t.getNotificacion()!=null){
            registro.put("notificacion",formatoFinal.format(t.getNotificacion()));
        }else{
            registro.put("notificacion","n/a");
        }
        registro.put("realizado",t.isRealizado());
        registro.put("tipo",t.getTipo());

        long resultado=accesoEscritura.insert("tareas",null,registro);
        if(resultado!=-1){
            Toast.makeText(contexto, "Se ha creado el registro con id "+resultado, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(contexto, "Error de inserción "+resultado, Toast.LENGTH_LONG).show();
        }
        accesoEscritura.close();
    }

    public boolean comprobarHecho(String seleccionado) {
        boolean resultado=false;
        String[] campos =new String[] {"realizado"};
        String[] argumentos=new String[]{seleccionado};
        SQLiteDatabase accesoLectura = miBD.getReadableDatabase();

        Cursor cursor = accesoLectura.query("tareas",campos,"id = ?",argumentos,null,null,null);

        if(cursor.moveToNext()){
            resultado = cursor.getInt(0) > 0;
        }
        accesoLectura.close();
        return resultado;
    }



    public String obtenerFecha(String seleccionado) {
        String resultado="";
        String[] campos =new String[] {"fecha"};
        String[] argumentos=new String[]{seleccionado};
        SQLiteDatabase accesoLectura = miBD.getReadableDatabase();

        Cursor cursor = accesoLectura.query("tareas",campos,"id = ?",argumentos,null,null,null);

        if(cursor.moveToNext()){
            resultado = cursor.getString(0);
        }
        accesoLectura.close();
        return resultado;
    }


    public ArrayList<Tarea> buscarConNotificaciones() {
        ArrayList<Tarea> resultado=new ArrayList<Tarea>();
        String[] campos =new String[] {"id","fecha","descripcion","notificacion","realizado","tipo"};
        SQLiteDatabase accesoLectura = miBD.getReadableDatabase();
        Cursor cursor = accesoLectura.query("tareas",campos,"notificacion != \"\"",null,null,null,null);

        while(cursor.moveToNext()){
            Tarea t=new Tarea();
            try {
                t.setId(cursor.getInt(0));
                t.setFecha(formato.parse(cursor.getString(1)));
                t.setDescripcion(cursor.getString(2));
                if (cursor.getString(3) != null) {
                    t.setNotificacion(formatoFinal.parse(cursor.getString(3)));
                }

            } catch (ParseException e) {
                t.setNotificacion(null);
            }
            t.setRealizado(cursor.getInt(4) > 0);
            t.setTipo(cursor.getString(5));
            resultado.add(t);
        }
        accesoLectura.close();
        return resultado;
    }
}
