package com.example.paugustobriga.pMedio.Profesorado;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.paugustobriga.AcPrincipal;
import com.example.paugustobriga.R;

import java.util.ArrayList;

public class AcDepartamentos extends AppCompatActivity implements AsyncRespuestaHtml{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departamentos);

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
        for(Object[] x: salida){
            for(int i=0;i<x.length;i++){
                if(i==0){
                    Log.d("Departamento: ",x[0].toString());
                }else{
                    Log.d("Profesores: ",x[i].toString());
                }
            }
        }
    }
}