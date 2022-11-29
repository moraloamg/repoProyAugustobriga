package com.example.paugustobriga.pMedio.Profesorado;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.paugustobriga.AcPrincipal;
import com.example.paugustobriga.R;

import java.util.ArrayList;

public class AcTutorias extends AppCompatActivity implements AsyncRespuesta {

    TableLayout tl;
    TextView pantallaCarga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorias);

        tl= findViewById(R.id.tbTablaTutorias);
        pantallaCarga = findViewById(R.id.txtCargando);

        //AccesoHtml ac=new AccesoHtml("https://iesaugustobriga.educarex.es/index.php/tutores","tutorias");
        LecturaCSV lc = new LecturaCSV("https://raw.githubusercontent.com/moraloamg/pruebaAugustobriga/main/tutores.csv","tutorias");
        lc.delegar = this;
        lc.execute();

    }

    @Override
    public void errorDeConexion(){
        AlertDialog.Builder dialogo1=new AlertDialog.Builder(AcTutorias.this);
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
        tl.setStretchAllColumns(true); //encogemos y estiramos las filas
        tl.setShrinkAllColumns(true);
        boolean filaColumnas = true;
        //cada objeto de la lista tiene información sobre un curso
        for(Object[] x:salida){
            //por formato de la tabla del HTML, la primera fila de cada grupo contiene 4 elementos, nombre del curso
            //grupo, tutor, y horario
            //el resto de filas contiene 3 elementos; grupo, tutor y horario
            if(filaColumnas){
                /*
                //--------fila con el nombre del curso----------
                TableRow columnaCurso = new TableRow(this);
                columnaCurso.setGravity(Gravity.CENTER_HORIZONTAL);

                TextView tv = new TextView(this);
                tv.setText(x[0].toString());   //el nombre del curso
                tv.setTextColor(Color.WHITE);
                TableRow.LayoutParams params = new TableRow.LayoutParams();
                params.span = 2;
                params.gravity = Gravity.CENTER;

                columnaCurso.addView(tv,params);
                columnaCurso.setBackgroundColor(Color.parseColor("#3070F0"));
                columnaCurso.setAlpha(0.7f);

                tl.addView(columnaCurso);
                 */
                TableRow columnaDatos = new TableRow(this);
                tl.addView(columnaDatos);

                columnaDatos.setGravity(Gravity.CENTER_HORIZONTAL);
                columnaDatos.setBackgroundColor(Color.parseColor("#3070F0"));
                columnaDatos.setAlpha(0.7f);

                //TableRow.LayoutParams params = new TableRow.LayoutParams();
                //params.span = 2;
                //params.gravity = Gravity.CENTER;

                //---------------------------------------
                TextView tv1 = new TextView(this);
                tv1.setText(x[0].toString());
                tv1.setTextColor(Color.WHITE);
                columnaDatos.addView(tv1);
                //grupo -------------------
                TextView tv2 = new TextView(this);
                tv2.setText(x[1].toString());
                tv2.setTextColor(Color.WHITE);
                columnaDatos.addView(tv2);
                //tutor -------------------
                TextView tv3 = new TextView(this);
                tv3.setText(x[2].toString());
                tv3.setTextColor(Color.WHITE);
                columnaDatos.addView(tv3);
                //horario -----------------
                TextView tv4 = new TextView(this);
                tv4.setText(x[3].toString());
                tv4.setTextColor(Color.WHITE);
                columnaDatos.addView(tv4);

                filaColumnas = false;
            }else{
                //-------// fila con el resto de los datos
                TableRow columnaDatos = new TableRow(this);
                tl.addView(columnaDatos);

                TextView tv1 = new TextView(this);
                tv1.setText(x[0].toString());
                columnaDatos.addView(tv1);
                //grupo
                TextView tv2 = new TextView(this);
                tv2.setText(x[1].toString());
                columnaDatos.addView(tv2);
                //tutor
                TextView tv3 = new TextView(this);
                tv3.setText(x[2].toString());
                columnaDatos.addView(tv3);
                //horario
                TextView tv4 = new TextView(this);
                tv4.setText(x[3].toString());
                columnaDatos.addView(tv4);
            }
        }
        pantallaCarga.setVisibility(View.INVISIBLE);
    }


}