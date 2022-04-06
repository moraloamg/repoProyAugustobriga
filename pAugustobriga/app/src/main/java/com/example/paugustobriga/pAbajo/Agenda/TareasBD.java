package com.example.paugustobriga.pAbajo.Agenda;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class TareasBD extends SQLiteOpenHelper {

    Context contexto;

    static String createBDSQL ="CREATE TABLE tareas (id integer primary key autoincrement," +
                                                    " fecha int," +
                                                    " descripcion text," +
                                                    " notificacion int," +
                                                    " realizado bool," +
                                                    " tipo text)";

    public TareasBD(Context context){
        super(context.getApplicationContext(),"TareasBD",null,1 );
        contexto=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            sqLiteDatabase.execSQL(createBDSQL);
        }catch (SQLException e){
            Toast.makeText(contexto, "Error al crear la base de datos "+e, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        try {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tareas");
            onCreate((sqLiteDatabase));
        }catch (SQLException e){
            Toast.makeText(contexto, "Error al actualizar la base de datos "+e,Toast.LENGTH_SHORT).show();
        }
    }
}
