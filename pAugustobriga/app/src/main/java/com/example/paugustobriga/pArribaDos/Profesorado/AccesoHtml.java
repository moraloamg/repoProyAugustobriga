package com.example.paugustobriga.pArribaDos.Profesorado;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class AccesoHtml extends AsyncTask {

    private String url;
    private Document html;
    private String tipo;
    public AsyncRespuestaHtml delegar = null;

    ArrayList<Object[]> datos=new ArrayList<>();


    public AccesoHtml(String url, String tipo)
    {
        this.url = url;
        this.tipo = tipo;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        delegar.procesoFinalizado(datos);
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        conectarUrl();
        sacarTipo(tipo);
        return true;
    }

    private void conectarUrl(){
        try {
            html = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sacarTipo(String tipo){
        switch (tipo){
            case "organizacion":
                organizacion();
                break;
            case "departamentos":

                break;
            case "tutorias":
                tutorias();
                break;
        }
    }

    private void organizacion(){

        Element tabla = html.select("table").get(2);
        Elements filas = tabla.select("tr");
        for(int i=0;i< filas.size();i++){
            Element fila = filas.get(i);
            Elements columna = fila.select("td");


            Object[] o = {columna.get(0).text(), columna.get(1).text()};
            datos.add(o);
        }
    }

    private void tutorias(){
        Element tabla = html.select("table").get(1);
        Elements filas = tabla.select("tr");
        for(int i=1;i<filas.size();i++){ //i en uno por formato de la tabla
            Element fila =filas.get(i);
            Elements columna = fila.select("td");

            Object[] o = {};
            if(columna.size()==4){
                o = new Object[]{columna.get(0).text(), columna.get(1).text(), columna.get(2).text(), columna.get(3).text()};
            }else{
                o =new Object[]{columna.get(0).text(),columna.get(1).text(),columna.get(2).text()};
            }
            datos.add(o);
        }
    }
}
