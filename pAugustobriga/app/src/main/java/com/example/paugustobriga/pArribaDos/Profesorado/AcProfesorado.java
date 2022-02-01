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
        AccesoHtml ac=new AccesoHtml("https://iesaugustobriga.educarex.es/index.php/organizacion2");
        ac.delegar = this;
        ac.execute();
    }


    @Override
    public void procesoFinalizado(ArrayList<Directivo> salida) {
        for(Directivo x:salida){
            Log.d("salida cargo", x.getCargo());
            Log.d("salida nombre", x.getNombre());
        }

        Toast.makeText(this, "parseoTerminado", Toast.LENGTH_LONG).show();
    }
}