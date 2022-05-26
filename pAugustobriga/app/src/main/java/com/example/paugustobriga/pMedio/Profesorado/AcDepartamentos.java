package com.example.paugustobriga.pMedio.Profesorado;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
    public void errorDeConexion(){
        AlertDialog.Builder dialogo1=new AlertDialog.Builder(AcDepartamentos.this);
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
    public void procesoFinalizado(ArrayList<Object[]> salida) {
        tl.setStretchAllColumns(true); //encogemos y estiramos las filas
        tl.setShrinkAllColumns(true);
        for(Object[] x: salida){ //cada objeto de la lista contendrá información de un departamento
            for(int i=0;i<x.length;i++){   //el indice cero siempre será el nombre del departamento, el resto el personal
                if(i==0){
                    //En esta parte se dispone el nombre del departamento
                    TableRow columnaDepartamentos = new TableRow(this);

                    TextView tv = new TextView(this);
                    tv.setText(x[0].toString());   //añadimos el nombre del departamento
                    tv.setTextColor(Color.WHITE); //damos color a la letra

                    TableRow.LayoutParams params = new TableRow.LayoutParams();
                    params.gravity = Gravity.CENTER; //centramos el texto
                    //añadimos propiedades a la columna
                    columnaDepartamentos.addView(tv,params);
                    columnaDepartamentos.setBackgroundColor(Color.parseColor("#3070F0"));
                    columnaDepartamentos.setAlpha(0.7f);

                    tl.addView(columnaDepartamentos); //añadimos la fila a la tabla
                }else{
                    //aquí disponemos el personal del departamento
                    TableRow columna2 = new TableRow(this);
                    tl.addView(columna2);

                    TextView tv2 = new TextView(this);
                    tv2.setText(x[i].toString()); //añadimos el nombre del personal

                    TableRow.LayoutParams params = new TableRow.LayoutParams();
                    params.gravity = Gravity.CENTER; //centramos el texto

                    columna2.addView(tv2,params); //añadimos la fila a la tabla

                }
            }
        }
        pantallaCarga.setVisibility(View.INVISIBLE); //hacemos invisible el proceso cuando se ha completado
    }
}