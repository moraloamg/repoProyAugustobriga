package com.example.paugustobriga.pMedio.Profesorado;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.paugustobriga.AcPrincipal;
import com.example.paugustobriga.R;

import java.util.ArrayList;

public class AcOrganizacion extends AppCompatActivity implements AsyncRespuesta {

    TableLayout tl;
    TextView pantallaCarga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizacion);

        tl= findViewById(R.id.tbTabla1);
        pantallaCarga = findViewById(R.id.txtCargando);


        //AccesoHtml ac=new AccesoHtml("https://iesaugustobriga.educarex.es/index.php/organizacion2","organizacion");
        LecturaCSV lc = new LecturaCSV("https://raw.githubusercontent.com/moraloamg/pruebaAugustobriga/main/equipo_directivo.csv","organizacion");
        lc.delegar = this;
        lc.execute();

    }

    @Override
    public void errorDeConexion(){
        AlertDialog.Builder dialogo1=new AlertDialog.Builder(AcOrganizacion.this);
        dialogo1.setTitle("Error");
        dialogo1.setMessage("Error de conexión");
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent i1=new Intent(getApplicationContext(), AcPrincipal.class);
                i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i1);
            }
        });
        dialogo1.show();

    }


    @Override
    public void onBackPressed() {
        Intent i=new Intent(getApplicationContext(), AcProfesorado.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    @Override
    public void procesoFinalizado(ArrayList<Object[]> salida) {
        for(Object[] x:salida){
            TableRow columna = new TableRow(this);
            tl.addView(columna);

            //puesto/cargo
            TextView tv = new TextView(this);
            tv.setText(x[0].toString()+"\t");
            tv.setTypeface(tv.getTypeface(), Typeface.BOLD);
            columna.addView(tv);
            //nombre
            TextView tv2 = new TextView(this);
            tv2.setText("\t"+x[1].toString());
            columna.addView(tv2);

        }
        pantallaCarga.setVisibility(View.INVISIBLE);
    }
}