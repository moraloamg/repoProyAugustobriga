package com.example.paugustobriga.pArribaDos.Profesorado;

import android.os.AsyncTask;
import android.util.Log;

import com.example.paugustobriga.pArribaDos.Horarios.AsyncRespuesta;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class AccesoHtml extends AsyncTask {

    private String url;
    private Document html;
    public AsyncRespuestaHtml delegar = null;
    ArrayList<Directivo> datos=new ArrayList<>();

    public AccesoHtml(String url){
        this.url = url;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        delegar.procesoFinalizado(datos);
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        conectarUrl();
        obtenerElementos();
        return true;
    }

    private void conectarUrl(){
        try {
            html = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void obtenerElementos(){

        Element tabla = html.select("table").get(2);
        Elements filas = tabla.select("tr");
        for(int i=0;i< filas.size();i++){
            Element fila = filas.get(i);
            Elements columna = fila.select("td");

            Directivo d=new Directivo();
            d.setCargo(columna.get(0).text());
            d.setNombre(columna.get(1).text());
            datos.add(d);
        }
    }
}
