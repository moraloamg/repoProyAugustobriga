package com.example.paugustobriga.pMedio.Profesorado;

import android.os.AsyncTask;

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
    //Aquí si hay poliformismo, por ende se usa una interfaz que se aplica a varias clases
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
                departamentos();
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
            if(columna.size()==4){ //este if diferenciará la fila que incluye el curso de la que no
                o = new Object[]{columna.get(0).text(), columna.get(1).text(), columna.get(2).text(), columna.get(3).text()};
            }else{
                o =new Object[]{columna.get(0).text(),columna.get(1).text(),columna.get(2).text()};
            }
            datos.add(o);
        }
    }

    private void departamentos(){
        Elements nombresContenedores = html.getElementsByClass("jwts_toggleControlTitle");
        Elements contenidoContenedores = html.getElementsByClass("jwts_content");


        for(int i=0;i<nombresContenedores.size();i++){
            String departamento = nombresContenedores.get(i).text();
            //de cada contenedor, se obtienen los nodos que contienen los nombres de los profesores
            Element nombres = contenidoContenedores.get(i);
            Elements lineas = nombres.select("p");
            //Se eliminan aquellos nodos que no tengan texto
            for (Element elemento : lineas.select("*")) {
                if (!elemento.hasText() && elemento.isBlock()) {
                    lineas.remove(elemento);
                }
            }
            Object[] o=new Object[lineas.size()+1]; //los nombres de los profesores mas el nombre del departamento
            o[0]=departamento;
            //se recorren los nodos
            //dado que el primer lugar lo ocupa el nombre del departamento, se le suma +1 al contador
            for(int j=0;j<lineas.size();j++){
                o[j+1]=lineas.get(j).text();
            }

            datos.add(o);
        }

    }
}
