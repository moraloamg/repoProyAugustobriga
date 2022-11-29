package com.example.paugustobriga.pMedio.Profesorado;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class LecturaCSV extends AsyncTask {

    private String url;
    private String tipo;
    private ArrayList<Object[]> datos = new ArrayList<>();
    boolean errorLectura = false;
    //No hay poliformismo, ya que sólo utilizamos una clase.
    public AsyncRespuesta delegar = null;

    public LecturaCSV(String url, String tipo){
        this.url = url;
        this.tipo = tipo;

    }

    private void sacarTipo(BufferedReader fichero){

        switch (tipo){
            case "organizacion":
                organizacion(fichero);
                break;
            case "departamentos":
                departamentos(fichero);
                break;
            case "tutorias":
                tutorias(fichero);
                break;
        }
    }

    private void tutorias(BufferedReader fichero) {
        try {
            String linea;
            while ((linea = fichero.readLine()) != null) {
                String[] campos = linea.split(";");
                Object[] o = {campos[0], campos[1], campos[2], campos[3]};
                datos.add(o);
            }
        }catch (IOException e){
            errorLectura = true;
        }
    }

    private void departamentos(BufferedReader fichero) {
        try {
            String linea;
            while ((linea = fichero.readLine()) != null) {
                Object[] campos = linea.split(";");
                datos.add(campos);
            }
        }catch (IOException e){
            errorLectura = true;
        }
    }

    private void organizacion(BufferedReader fichero) {
        try {
            String linea;
            while ((linea = fichero.readLine()) != null) {
                String[] campos = linea.split(";");
                Object[] o = {campos[0], campos[1]};
                datos.add(o);
            }
        }catch (Exception e){
            errorLectura = true;
        }
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if(errorLectura){
            delegar.errorDeConexion();
        }else{
            delegar.procesoFinalizado(datos);
        }
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        URL urlFichero = null;

        try {
            //leemos el fichero
            urlFichero = new URL(url);
            BufferedReader fichero = new BufferedReader(new InputStreamReader(urlFichero.openStream()));
            sacarTipo(fichero);
            fichero.close();
            Log.d("leerFichero: 1", String.valueOf(getDatos().size()));
        } catch (Exception e) {
            e.printStackTrace();
            errorLectura=true;
        }finally {
            return true;
        }
    }

    public ArrayList<Object[]> getDatos() {
        return datos;
    }
}
