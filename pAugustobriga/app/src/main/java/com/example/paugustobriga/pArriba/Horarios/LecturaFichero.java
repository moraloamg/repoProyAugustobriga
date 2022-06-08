package com.example.paugustobriga.pArriba.Horarios;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class LecturaFichero extends AsyncTask {
    private String url;
    private ArrayList<String> contenido;
    //No hay poliformismo, ya que sólo utilizamos una clase.
    public AcHorarios delegar = null;

    public LecturaFichero(String url) {
        this.url = url;
        contenido = new ArrayList<>();
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        Log.d("leerFichero: 2", String.valueOf(getContenido().size()));
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
                contenido.add(linea);
            }
            fichero.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("leerFichero: 1", String.valueOf(getContenido().size()));
        return true;
    }



    public ArrayList<String> getContenido() {
        return contenido;
    }
}
