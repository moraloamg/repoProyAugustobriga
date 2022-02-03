package com.example.paugustobriga.pArribaDos.Profesorado;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.paugustobriga.R;
import com.example.paugustobriga.pArribaDos.Horarios.AsyncRespuesta;

import org.jsoup.nodes.Document;

import java.util.ArrayList;

public class AcProfesorado extends AppCompatActivity implements AsyncRespuestaHtml {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profesorado);
        //AccesoHtml ac=new AccesoHtml("https://iesaugustobriga.educarex.es/index.php/organizacion2","organizacion");
        AccesoHtml ac=new AccesoHtml("https://iesaugustobriga.educarex.es/index.php/tutores","tutorias");
        ac.delegar = this;
        ac.execute();
    }


    @Override
    public void procesoFinalizado(ArrayList<Object []> salida) {
        /*
        for(Object[] x:salida){

            Log.d("salida cargo", x[0].toString());
            Log.d("salida nombre", x[1].toString());
        }

        Toast.makeText(this, "parseoTerminado", Toast.LENGTH_LONG).show();
         */

        for(Object[] x:salida){
            if(x.length==4){
                Log.d("curso", x[0].toString());
                Log.d("grupo", x[1].toString());
                Log.d("tutor", x[2].toString());
                Log.d("Hora", x[3].toString());
            }else{
                Log.d("grupo", x[0].toString());
                Log.d("tutor", x[1].toString());
                Log.d("Hora", x[2].toString());
            }
        }
    }


}