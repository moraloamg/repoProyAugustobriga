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

import java.util.ArrayList;

public class AdaptadorExamenes extends BaseAdapter {

    ArrayList<Examen> examenes;
    Context contexto;
    Typeface fuente;

    public AdaptadorExamenes(Context c, Typeface fuenteContenedores, ArrayList<Examen> lExamenenes) {
        this.examenes = lExamenenes;
        this.contexto = c;
        this.fuente = fuenteContenedores;
    }

    @Override
    public int getCount() {
        return examenes.size();

    }

    @Override
    public Object getItem(int i) {
        return examenes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //preparamos los elementos visuales de la lista
        LayoutInflater inflar=(LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View list=inflar.inflate(R.layout.lista_examenes, viewGroup, false);

        TextView desc = (TextView) list.findViewById(R.id.txtExamen);
        TextView id = (TextView) list.findViewById(R.id.txtIdExamen); //quitar esto??
        TextView calif = (TextView) list.findViewById(R.id.txtNotaExamen);
        TextView refAsig = (TextView) list.findViewById(R.id.txtRefAsignatura);
        TextView refTri = (TextView) list.findViewById(R.id.txtRefTrimestre);
        id.setText(String.valueOf(examenes.get(i).getId())); //quitar esto???
        desc.setText(examenes.get(i).getNombre());
        calif.setText(String.valueOf(examenes.get(i).getNota()));
        refAsig.setText(examenes.get(i).getAsig().getNombre());
        refTri.setText(examenes.get(i).getTri().getNombreTrimestre());

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
