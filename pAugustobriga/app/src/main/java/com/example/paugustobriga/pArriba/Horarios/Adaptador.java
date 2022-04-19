package com.example.paugustobriga.pArriba.Horarios;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.paugustobriga.R;

import java.util.ArrayList;

public class Adaptador extends BaseAdapter {

    ArrayList<String> filas;
    Context contexto;
    Typeface fuente;

    public Adaptador(Context c,ArrayList<String> datos, Typeface fuenteContenedores){
        contexto=c;
        filas=new ArrayList<>();
        fuente = fuenteContenedores;

        //se utiliza un for y no un foreach debido a que este ultimo no coge los datos en orden
        for(int i=0;i<datos.size();i++){
            filas.add(datos.get(i));
        }

    }

    @Override
    public int getCount() {
        return filas.size();
    }

    @Override
    public Object getItem(int i) {
        return filas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //preparamos los elementos visuales de la lista
        LayoutInflater inflar=(LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View list=inflar.inflate(R.layout.lista_cursos, viewGroup, false);

        //disponemos las variables de los elementos gráficos
        TextView curso = (TextView) list.findViewById(R.id.txtCurso);
        LinearLayout contenedor = (LinearLayout) list.findViewById(R.id.contCurso);

        //rellenamos las variables con los datos de la lista
        curso.setText(filas.get(i).toString());
        //se añade la fuente (puede ser opcional)
        if(fuente!=null){
            curso.setTypeface(fuente);
        }

        //Añadimos una animacion
        Animation animacion=null;
        animacion = AnimationUtils.loadAnimation(contexto,R.anim.atg_lista_deslizar_izq);
        list.startAnimation(animacion);

        return list;
    }




}
