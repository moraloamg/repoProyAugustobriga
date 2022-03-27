package com.example.paugustobriga.pAbajo.Agenda;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.paugustobriga.R;

import java.util.ArrayList;

public class AdaptadorVerTareas extends BaseAdapter {

    ArrayList<TareasDia> tareas;
    Context contexto;
    Typeface fuente;

    public AdaptadorVerTareas(Context c,ArrayList<TareasDia> datos, Typeface fuenteContenedores){
        contexto=c;
        tareas=new ArrayList<>();
        fuente = fuenteContenedores;

        //se utiliza un for y no un foreach debido a que este ultimo no coge los datos en orden
        for(int i=0;i<datos.size();i++){
            tareas.add(datos.get(i));
        }
    }

    @Override
    public int getCount() {
        return tareas.size();
    }

    @Override
    public Object getItem(int i) {
        return tareas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //preparamos los elementos visuales de la lista
        LayoutInflater inflar=(LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View list=inflar.inflate(R.layout.lista_ver_tareas, viewGroup, false);

        //disponemos las variables de los elementos gráficos
        TextView txtfechaTarea = (TextView) list.findViewById(R.id.txtFechaVerTarea);
        TextView txtContRojo = (TextView) list.findViewById(R.id.txtContRojo);
        TextView txtContAzul = (TextView) list.findViewById(R.id.txtContAzul);
        TextView txtContAmarillo = (TextView) list.findViewById(R.id.txtContAmarillo);
        CheckBox chkCompletada = (CheckBox) list.findViewById(R.id.chkCompletada);
        CheckBox chkPasada = (CheckBox) list.findViewById(R.id.chkPasada);

        //rellenamos las variables con los datos de la lista
        txtfechaTarea.setText(tareas.get(i).fechaFormateada());
        chkCompletada.setChecked(tareas.get(i).isTodasTareasCompletadas());
        chkPasada.setChecked(tareas.get(i).isTareasDiaPasada());
        txtContRojo.setText(String.valueOf(contarRojos(i)));
        txtContAzul.setText(String.valueOf(contarAzules(i)));
        txtContAmarillo.setText(String.valueOf(contarAmarillos(i)));

        //se añade la fuente (puede ser opcional)
        if(fuente!=null){
            txtfechaTarea.setTypeface(fuente);
        }

        //Añadimos una animacion
        Animation animacion=null;
        animacion = AnimationUtils.loadAnimation(contexto,R.anim.atg_lista_deslizar_izq);
        list.startAnimation(animacion);

        return list;
    }

    private int contarRojos(int i){
        int contRojos=0;
        for(Tarea t:this.tareas.get(i).getListaTareas()){
            if(t.getTipo().equalsIgnoreCase("EXAMEN")){
                contRojos++;
            }
        }
        return contRojos;
    }

    private int contarAzules(int i){
        int contAzules=0;
        for(Tarea t:this.tareas.get(i).getListaTareas()){
            if(t.getTipo().equalsIgnoreCase("TAREA")){
                contAzules++;
            }
        }
        return contAzules;
    }

    private int contarAmarillos(int i){
        int contAmarillo=0;
        for(Tarea t:this.tareas.get(i).getListaTareas()){
            if(t.getTipo().equalsIgnoreCase("OTRO")){
                contAmarillo++;
            }
        }
        return contAmarillo;
    }
}
