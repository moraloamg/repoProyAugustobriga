package com.example.paugustobriga.pAbajo.Examenes;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class ExamenesBD extends SQLiteOpenHelper {

    Context contexto;

    static String tablaAsignatura = "CREATE TABLE Asignatura (nombre text primary key)";

    static String tablaTrimestre = "CREATE TABlE Trimestre (nombre text primary key)";

    static String tablaExamen = "CREATE TABLE Examen (id integer primary key autoincrement," +
                                                    " idAsignatura text," +
                                                    " idTrimestre text," +
                                                    " nota real," +
                                                    " nombre text," +
                                                    " FOREIGN KEY (\"idAsignatura\") REFERENCES \"Asignatura\"(\"nombre\") ON UPDATE CASCADE ON DELETE RESTRICT," +
                                                    " FOREIGN KEY (\"idTrimestre\") REFERENCES \"Trimestre\"(\"nombre\") ON UPDATE CASCADE ON DELETE RESTRICT)";



    public ExamenesBD(Context context){
        super(context.getApplicationContext(),"ExamenesBD",null,1 );
        contexto=context;
    }

    @Override
    public void onOpen(SQLiteDatabase db){
        super.onOpen(db);
        db.execSQL("PRAGMA foreign_keys=ON");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            sqLiteDatabase.execSQL(tablaAsignatura);
            sqLiteDatabase.execSQL(tablaTrimestre);
            sqLiteDatabase.execSQL(tablaExamen);
            sqLiteDatabase.execSQL("PRAGMA foreign_keys = ON");
        }catch (SQLException e){
            Toast.makeText(contexto, "Error al crear la base de datos "+e, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        try {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Asignatura");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Trimestre");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Examen");
            onCreate((sqLiteDatabase));
        }catch (SQLException e){
            Toast.makeText(contexto, "Error al actualizar la base de datos "+e,Toast.LENGTH_SHORT).show();
        }
    }
}
