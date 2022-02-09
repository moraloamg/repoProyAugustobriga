package com.example.paugustobriga.pMedio.Profesorado;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.paugustobriga.AcPrincipal;
import com.example.paugustobriga.R;

import java.util.ArrayList;

public class AcOrganizacion extends AppCompatActivity implements AsyncRespuestaHtml{

    TableLayout tl;
    TextView pantallaCarga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizacion);

        tl= findViewById(R.id.tbTabla1);
        pantallaCarga = findViewById(R.id.txtCargando);


        AccesoHtml ac=new AccesoHtml("https://iesaugustobriga.educarex.es/index.php/organizacion2","organizacion");
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
        for(Object[] x:salida){
            TableRow columna = new TableRow(this);
            columna.setBackgroundColor(Color.parseColor("#3070F0"));
            tl.addView(columna);
            TextView tv = new TextView(this);
            tv.setText(x[0].toString()+"\t");
            tv.setTextColor(Color.WHITE);
            columna.addView(tv);


            TextView tv2 = new TextView(this);
            tv2.setText("\t"+x[1].toString());
            columna.addView(tv2);

        }
        pantallaCarga.setVisibility(View.INVISIBLE);
    }
}