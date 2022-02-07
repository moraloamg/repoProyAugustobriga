package com.example.paugustobriga.pMedio.Profesorado;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

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