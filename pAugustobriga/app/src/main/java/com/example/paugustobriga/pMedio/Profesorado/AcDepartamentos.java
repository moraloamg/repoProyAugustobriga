package com.example.paugustobriga.pMedio.Profesorado;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.paugustobriga.AcPrincipal;
import com.example.paugustobriga.R;

import java.util.ArrayList;

public class AcDepartamentos extends AppCompatActivity implements AsyncRespuestaHtml{

    TableLayout tl;
    TextView pantallaCarga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departamentos);

        tl= findViewById(R.id.tbTablaDepartamentos);
        pantallaCarga = findViewById(R.id.txtCargando);


        AccesoHtml ac=new AccesoHtml("https://iesaugustobriga.educarex.es/index.php/profesorado-78","departamentos");
        ac.delegar = this;
        ac.execute();
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(getApplicationContext(), AcProfesorado.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    @Override
    public void procesoFinalizado(ArrayList<Object[]> salida) {
        tl.setStretchAllColumns(true);
        tl.setShrinkAllColumns(true);
        for(Object[] x: salida){
            for(int i=0;i<x.length;i++){
                if(i==0){
                    TableRow columnaDepartamentos = new TableRow(this);
                    columnaDepartamentos.setGravity(Gravity.CENTER_HORIZONTAL);

                    TextView tv = new TextView(this);
                    tv.setText(x[0].toString());   //el nombre del departamento
                    tv.setTextColor(Color.WHITE);

                    TableRow.LayoutParams params = new TableRow.LayoutParams();
                    params.gravity = Gravity.CENTER;

                    columnaDepartamentos.addView(tv,params);
                    columnaDepartamentos.setBackgroundColor(Color.parseColor("#3070F0"));
                    columnaDepartamentos.setAlpha(0.7f);

                    tl.addView(columnaDepartamentos);
                }else{
                    TableRow columna2 = new TableRow(this);
                    tl.addView(columna2);

                    TextView tv2 = new TextView(this);
                    tv2.setText(x[i].toString());

                    TableRow.LayoutParams params = new TableRow.LayoutParams();
                    params.gravity = Gravity.CENTER;

                    columna2.addView(tv2,params);

                }
            }
        }
        pantallaCarga.setVisibility(View.INVISIBLE);
    }
}