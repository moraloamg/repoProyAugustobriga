package com.example.paugustobriga.pAbajo.Agenda;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

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
        accesoLectura.execSQL("delete from tarea where id='"+id+"'");
        accesoLectura.close();
    }

    public void modificarDescripcion(int id, String descripcion){
        SQLiteDatabase accesoLectura = miBD.getReadableDatabase();
        accesoLectura.execSQL("update tarea set descripcion='"+descripcion+"' where id='"+id+"'");
        accesoLectura.close();
    }

    public void realizarTarea(int id){
        SQLiteDatabase accesoLectura = miBD.getReadableDatabase();
        accesoLectura.execSQL("update tarea set realizado=true where id='"+id+"'");
        accesoLectura.close();
    }

    public void modificarTipo(int id, String tipo){
        SQLiteDatabase accesoLectura = miBD.getReadableDatabase();
        accesoLectura.execSQL("update tarea set tipo='"+tipo+"' where id='"+id+"'");
        accesoLectura.close();
    }

    public void modificarNotificacion(int id, int fecha){
        SQLiteDatabase accesoLectura = miBD.getReadableDatabase();
        accesoLectura.execSQL("update tarea set notificacion='"+fecha+"' where id='"+id+"'");
        accesoLectura.close();
    }

    public ArrayList<Tarea> obtenerTareas(){
        ArrayList<Tarea> resultado=new ArrayList<Tarea>();
        String[] campos =new String[] {"id","fecha","descripcion","notificacion","realizado","tipo"};
        SQLiteDatabase accesoLectura = miBD.getReadableDatabase();

        Cursor cursor = accesoLectura.query("tarea",campos,null,null,null,null,null);

        while(cursor.moveToNext()){
            Tarea t=new Tarea();
            t.setId(cursor.getInt(0));
            //si no funciona con un int cambiar el campo fecha por un text, también modificar lo de arriba
            //probar también con el método getLong
            t.setFecha(new Date(String.valueOf(cursor.getInt(1))));
            t.setDescripcion(cursor.getString(2));
            t.setNotificacion(new Date(String.valueOf(cursor.getInt(3))));
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
        SQLiteDatabase accesoLectura = miBD.getReadableDatabase();

        Cursor cursor = accesoLectura.query("tarea",campos,"descripcion like '%"+busqueda+"%'",null,null,null,"id desc");

        int registros=cursor.getCount(); //ver registros que saca

        while(cursor.moveToNext()){
            Tarea t=new Tarea();
            t.setId(cursor.getInt(0));
            //si no funciona con un int cambiar el campo fecha por un text, también modificar lo de arriba
            //probar también con el método getLong
            t.setFecha(new Date(String.valueOf(cursor.getInt(1))));
            t.setDescripcion(cursor.getString(2));
            t.setNotificacion(new Date(String.valueOf(cursor.getInt(3))));
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
        //probar con texto si no funciona con un long
        registro.put("fecha",t.getFecha().getTime());
        registro.put("descripcion",t.getDescripcion());
        //probar con texto si no funciona con un long
        registro.put("notificacion",t.getNotificacion().getTime());
        registro.put("realizado",t.isRealizado());
        registro.put("tipo",t.getTipo());

        long resultado=accesoEscritura.insert("nota",null,registro);
        if(resultado!=-1){
            Toast.makeText(contexto, "Se ha creado el registro con id "+resultado, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(contexto, "Error de inserción "+resultado, Toast.LENGTH_LONG).show();
        }
        accesoEscritura.close();
    }

    //PONER EL RESTO DE MÉTODOS AQUÍ ABAJO CUANDO EL RESTO FUNCIONE


}
