package com.example.paugustobriga.pAbajo.Examenes;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.paugustobriga.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class AdaptadorAsignatura extends BaseAdapter {

    ArrayList<Asignatura> asignaturas;
    Context contexto;
    Typeface fuente;

    public AdaptadorAsignatura(Context c, Typeface fuenteContenedores, ArrayList<Asignatura> lAsignaturas) {
        this.asignaturas = lAsignaturas;
        this.contexto = c;
        this.fuente = fuenteContenedores;

    }

    @Override
    public int getCount() {
        return asignaturas.size();
    }

    @Override
    public Object getItem(int i) {
        return asignaturas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        //preparamos los elementos visuales de la lista
        LayoutInflater inflar=(LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View list=inflar.inflate(R.layout.lista_asignaturas, viewGroup, false);

        TextView desc = (TextView) list.findViewById(R.id.txtAsignatura);
        desc.setText(asignaturas.get(i).getNombre());

        //se añade la fuente (puede ser opcional)
        if(fuente!=null){
            desc.setTypeface(fuente);
        }

        //Añadimos una animacion
        Animation animacion=null;
        animacion = AnimationUtils.loadAnimation(contexto,R.anim.atg_lista_deslizar_izq);
        list.startAnimation(animacion);

        return list;


    }
}
