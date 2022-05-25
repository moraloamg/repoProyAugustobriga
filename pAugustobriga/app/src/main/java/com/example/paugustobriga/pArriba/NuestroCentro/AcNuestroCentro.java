package com.example.paugustobriga.pArriba.NuestroCentro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.paugustobriga.R;
import com.example.paugustobriga.pArriba.Horarios.AcCursos;
import com.example.paugustobriga.pArriba.Horarios.LecturaFichero;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class AcNuestroCentro extends AppCompatActivity {

    TextView titulo, parrafo1, parrafo2, parrafo3;
    ImageView img1, img2, img3;

    Typeface fuenteContenedores;

    Animation atgCabecera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuestro_centro);

        identificarElementos();
        importarFuentes();
        cargarAnimaciones();
        disponerAnimaciones();
        cargarImagenes();
        obtenerDatosFichero("https://raw.githubusercontent.com/moraloamg/pruebaAugustobriga/main/Nuestro%20centro/texto.txt");
        disponerFuentes();



    }

    private void cargarImagenes(){
        Glide.with(this).load("https://raw.githubusercontent.com/moraloamg/pruebaAugustobriga/main/Nuestro%20centro/fondoCabecera.png").into(img1);
        Glide.with(this).load("https://raw.githubusercontent.com/moraloamg/pruebaAugustobriga/main/Nuestro%20centro/fotoClaustro.jpg").into(img2);
        Glide.with(this).load("https://raw.githubusercontent.com/moraloamg/pruebaAugustobriga/main/Nuestro%20centro/refLogotipo.jpg").into(img3);
    }

    private void obtenerDatosFichero(String url){
        LecturaFicheroTexto lector=new LecturaFicheroTexto(url);
        //creamos una referencia de esta objeto en la clase LecturaFichero
        lector.delegar = this;
        //una vez se ejecute el siguiente método, el hilo de la clase actual acabará y empezará el hilo de lectura
        lector.execute();
    }

    public void procesoFinalizado(String salida) {
        parrafo1.setText(salida.split("--")[0]);
        parrafo2.setText(salida.split("--")[1]);
        parrafo3.setText(salida.split("--")[2]);
    }

    private void disponerFuentes(){
        titulo.setTypeface(fuenteContenedores);
        parrafo1.setTypeface(fuenteContenedores);
        parrafo2.setTypeface(fuenteContenedores);
        parrafo3.setTypeface(fuenteContenedores);
    }

    private void importarFuentes(){
        fuenteContenedores = ResourcesCompat.getFont(this, R.font.clear_sans_thin);
    }

    private void cargarAnimaciones(){
        atgCabecera = AnimationUtils.loadAnimation(this,R.anim.atg_cabecera);
    }

    private void disponerAnimaciones(){
        titulo.startAnimation(atgCabecera);
    }

    private void identificarElementos(){
        titulo = findViewById(R.id.txtCabeceraNuestroCentro);
        parrafo1 = findViewById(R.id.txtParrafo1);
        parrafo2 = findViewById(R.id.txtParrafo2);
        parrafo3 = findViewById(R.id.txtParrafo3);
        img1 = findViewById(R.id.txtImagen1);
        img2 = findViewById(R.id.txtImagen2);
        img3 = findViewById(R.id.txtImagen3);
    }


}