package com.example.paugustobriga.pArriba.NuestroCentro;

import android.os.AsyncTask;
import android.util.Log;

import com.example.paugustobriga.pArriba.Horarios.AcHorarios;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class LecturaFicheroTexto extends AsyncTask {

    private String url;
    private String contenido;
    //No hay poliformismo, ya que sólo utilizamos una clase.
    public AcNuestroCentro delegar = null;

    public LecturaFicheroTexto(String url) {
        this.url = url;
        contenido = "";
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        delegar.procesoFinalizado(contenido);
    }



    @Override
    protected Object doInBackground(Object[] objects) {
        URL urlFichero = null;

        try {
            //leemos el fichero
            urlFichero = new URL(url);
            BufferedReader fichero = new BufferedReader(new InputStreamReader(urlFichero.openStream()));
            String linea;
            while((linea = fichero.readLine()) != null){
                contenido+=linea;
            }
            fichero.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }



    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
}
