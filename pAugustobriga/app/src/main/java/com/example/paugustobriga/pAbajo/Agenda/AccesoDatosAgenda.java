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
    private Context contexto;
    TareasBD miBD;

    public AccesoDatosAgenda(Context context){
        this.contexto=context;
        miBD = new TareasBD(context);
    }

    public void borrar(int id){
        //poner parametros mas adelante
        SQLiteDatabase accesoLectura = miBD.getReadableDatabase();
        accesoLectura.execSQL("delete from tareas where id='"+id+"'");
        accesoLectura.close();
    }

    public void modificarDescripcion(int id, String descripcion){
        SQLiteDatabase accesoLectura = miBD.getReadableDatabase();
        accesoLectura.execSQL("update tareas set descripcion='"+descripcion+"' where id='"+id+"'");
        accesoLectura.close();
    }

    public void realizarTarea(int id){
        SQLiteDatabase accesoLectura = miBD.getReadableDatabase();
        accesoLectura.execSQL("update tareas set realizado=true where id='"+id+"'");
        accesoLectura.close();
    }

    public void modificarTipo(int id, String tipo){
        SQLiteDatabase accesoLectura = miBD.getReadableDatabase();
        accesoLectura.execSQL("update tareas set tipo='"+tipo+"' where id='"+id+"'");
        accesoLectura.close();
    }

    public void modificarNotificacion(int id, String fecha){
        SQLiteDatabase accesoLectura = miBD.getReadableDatabase();
        accesoLectura.execSQL("update tareas set notificacion='"+fecha+"' where id='"+id+"'");
        accesoLectura.close();
    }

    public ArrayList<Tarea> obtenerTareasDia(String fecha){
        ArrayList<Tarea> resultado=new ArrayList<Tarea>();
        String[] campos =new String[] {"id","fecha","descripcion","notificacion","realizado","tipo"};
        SQLiteDatabase accesoLectura = miBD.getReadableDatabase();

        Cursor cursor = accesoLectura.query("tareas",campos,"fecha like '%"+fecha+"%'",null,null,null,null);

        while(cursor.moveToNext()){
            Tarea t=new Tarea();
            try {
                t.setId(cursor.getInt(0));
                //si no funciona con un int cambiar el campo fecha por un text, también modificar lo de arriba
                //probar también con el método getLong
                t.setFecha(formato.parse(cursor.getString(1)));
                t.setDescripcion(cursor.getString(2));
                t.setNotificacion(formato.parse(cursor.getString(3)));

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
            t.setId(cursor.getInt(0));
            try {
                t.setFecha(formato.parse(cursor.getString(1)));
                t.setDescripcion(cursor.getString(2));
                t.setNotificacion(formato.parse(cursor.getString(3)));
                t.setRealizado(cursor.getInt(4) > 0);
                t.setTipo(cursor.getString(5));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            resultado.add(t);
        }
        accesoLectura.close();
        return resultado;
    }

    public ArrayList<Tarea> buscarTarea(String busqueda){
        ArrayList<Tarea> resultado=new ArrayList<Tarea>();
        String[] campos =new String[] {"id","fecha","descripcion","notificacion","realizado","tipo"};
        SQLiteDatabase accesoLectura = miBD.getReadableDatabase();

        Cursor cursor = accesoLectura.query("tareas",campos,"descripcion like '%"+busqueda+"%'",null,null,null,"id desc");

        int registros=cursor.getCount(); //ver registros que saca

        while(cursor.moveToNext()){
            Tarea t=new Tarea();
            t.setId(cursor.getInt(0));
            //si no funciona con un int cambiar el campo fecha por un text, también modificar lo de arriba
            //probar también con el método getLong
            try {
                t.setFecha(formato.parse(cursor.getString(1)));
                t.setDescripcion(cursor.getString(2));
                t.setNotificacion(formato.parse(cursor.getString(3)));
                //los booleanos se cogen así
                t.setRealizado(cursor.getInt(4) > 0);
                t.setTipo(cursor.getString(5));
            } catch (ParseException e) {
                e.printStackTrace();
            }
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
            registro.put("notificacion",formato.format(t.getNotificacion()));
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

    //PONER EL RESTO DE MÉTODOS AQUÍ ABAJO CUANDO EL RESTO FUNCIONE


}
