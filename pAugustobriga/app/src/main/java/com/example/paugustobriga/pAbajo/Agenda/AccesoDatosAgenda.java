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

    public boolean borrar(int id){
        try {
            SQLiteDatabase accesoLectura = miBD.getReadableDatabase();
            String[] argumentos = new String[]{String.valueOf(id)};
            accesoLectura.delete("tareas", "id = ?", argumentos);
            accesoLectura.close();
        }catch (Exception ex){
            return false;
        }
        return true;
    }

    public boolean modificarDescripcion(int id, String descripcion){
        try {
            SQLiteDatabase accesoLectura = miBD.getReadableDatabase();
            String[] argumentos=new String[]{String.valueOf(id)};
            ContentValues registro=new ContentValues();
            registro.put("descripcion",descripcion);
            accesoLectura.update("tareas", registro, "id = ?", argumentos);
            accesoLectura.close();
        }catch (Exception ex){
            return false;
        }
        return true;
    }

    public boolean realizarTarea(int id){
        try {
            SQLiteDatabase accesoLectura = miBD.getReadableDatabase();
            String[] argumentos=new String[]{String.valueOf(id)};
            ContentValues registro=new ContentValues();
            registro.put("realizado",true);
            accesoLectura.update("tareas", registro, "id = ?", argumentos);
            accesoLectura.close();
        }catch (Exception ex){
            return false;
        }
        return true;
    }

    public boolean modificarTipo(int id, String tipo){
        try {
            SQLiteDatabase accesoLectura = miBD.getReadableDatabase();
            String[] argumentos=new String[]{String.valueOf(id)};
            ContentValues registro=new ContentValues();
            registro.put("tipo",tipo);
            accesoLectura.update("tareas", registro, "id = ?", argumentos);
            accesoLectura.close();
        }catch (Exception ex){
            return false;
        }
        return true;
    }

    public boolean modificarNotificacion(int id, String fecha){
        try {
            SQLiteDatabase accesoLectura = miBD.getReadableDatabase();
            String[] argumentos=new String[]{String.valueOf(id)};
            ContentValues registro=new ContentValues();
            if(fecha == null){
                fecha="n_a";
            }
            registro.put("notificacion",fecha);
            accesoLectura.update("tareas", registro, "id = ?", argumentos);
            accesoLectura.close();
        }catch (Exception ex){
            return false;
        }
        return true;
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
                if (!cursor.getString(3).equals("n_a")) {
                    t.setNotificacion(formatoFinal.parse(cursor.getString(3)));
                }else{
                    t.setNotificacion(null);
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

    public boolean insertarTarea(Tarea t){
        try{
            SQLiteDatabase accesoEscritura = miBD.getWritableDatabase();

            ContentValues registro=new ContentValues();
            registro.put("fecha",formato.format(t.getFecha()));
            registro.put("descripcion",t.getDescripcion());
            if(t.getNotificacion()!=null){
                registro.put("notificacion",formatoFinal.format(t.getNotificacion()));
            }else{
                registro.put("notificacion","n_a");
            }
            registro.put("realizado",t.isRealizado());
            registro.put("tipo",t.getTipo());

            long codigo=accesoEscritura.insert("tareas",null,registro);
            accesoEscritura.close();
            if(codigo!=-1){
                return true;
            }else{
                return false;
            }

        }catch (Exception ex){
            return false;
        }
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
        Cursor cursor = accesoLectura.query("tareas",campos,"notificacion != 'n_a'",null,null,null,null);

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
