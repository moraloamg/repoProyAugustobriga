package com.example.paugustobriga.pMedio.Profesorado;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.paugustobriga.R;

import java.util.ArrayList;

public class AcTutorias extends AppCompatActivity implements AsyncRespuestaHtml{

    TableLayout tl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorias);

        tl= findViewById(R.id.tbTabla1);

        AccesoHtml ac=new AccesoHtml("https://iesaugustobriga.educarex.es/index.php/tutores","tutorias");
        ac.delegar = this;
        ac.execute();

    }

    @Override
    public void procesoFinalizado(ArrayList<Object[]> salida) {
        for(Object[] x:salida){
            if(x.length==4){

                TableRow columnaCurso = new TableRow(this);
                tl.addView(columnaCurso);

                TextView tv = new TextView(this);
                tv.setText(x[0].toString());   //el nombre del curso
                columnaCurso.addView(tv);

                TableRow columnaDatos = new TableRow(this);
                tl.addView(columnaDatos);  //?

                TextView tv2 = new TextView(this);
                tv2.setText(x[1].toString());
                columnaDatos.addView(tv2);

                TextView tv3 = new TextView(this);
                tv3.setText(x[2].toString());
                columnaDatos.addView(tv3);

                TextView tv4 = new TextView(this);
                tv4.setText(x[3].toString());
                columnaDatos.addView(tv4);

                /*
                Log.d("curso", x[0].toString());
                Log.d("grupo", x[1].toString());
                Log.d("tutor", x[2].toString());
                Log.d("Hora", x[3].toString());

                 */
            }else{
                TableRow columna2 = new TableRow(this);
                tl.addView(columna2);

                TextView tv5 = new TextView(this);
                tv5.setText(x[0].toString());
                columna2.addView(tv5);

                TextView tv6 = new TextView(this);
                tv6.setText(x[1].toString());
                columna2.addView(tv6);

                TextView tv7 = new TextView(this);
                tv7.setText(x[2].toString());
                columna2.addView(tv7);

                /*
                Log.d("grupo", x[0].toString());
                Log.d("tutor", x[1].toString());
                Log.d("Hora", x[2].toString());

                 */
            }
        }
    }
}