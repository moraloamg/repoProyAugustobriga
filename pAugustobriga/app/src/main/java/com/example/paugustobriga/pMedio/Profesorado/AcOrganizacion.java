package com.example.paugustobriga.pMedio.Profesorado;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.paugustobriga.R;

import java.util.ArrayList;

public class AcOrganizacion extends AppCompatActivity implements AsyncRespuestaHtml{

    TableLayout tl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizacion);

        tl= findViewById(R.id.tbTabla1);


        AccesoHtml ac=new AccesoHtml("https://iesaugustobriga.educarex.es/index.php/organizacion2","organizacion");
        ac.delegar = this;
        ac.execute();
    }

    @Override
    public void procesoFinalizado(ArrayList<Object[]> salida) {
        for(Object[] x:salida){
            TableRow columna = new TableRow(this);
            tl.addView(columna);
            TextView tv = new TextView(this);
            tv.setText(x[0].toString()+"\t");
            columna.addView(tv);


            TextView tv2 = new TextView(this);
            tv2.setText("\t"+x[1].toString());
            columna.addView(tv2);

        }
    }
}