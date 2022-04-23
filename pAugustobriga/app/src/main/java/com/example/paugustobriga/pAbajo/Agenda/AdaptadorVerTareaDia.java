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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.paugustobriga.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AdaptadorVerTareaDia extends BaseAdapter {
    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

    ArrayList<Tarea> tareas;
    Context contexto;
    Typeface fuente;

    public AdaptadorVerTareaDia(Context c,Typeface fuenteContenedores, ArrayList<Tarea> datos){
        this.tareas = datos;
        this.contexto = c;
        this.fuente = fuenteContenedores;

        //se utiliza un for y no un foreach debido a que este ultimo no coge los datos en orden
        /*
        for(int i=0;i<datos.size();i++){
            tareas.add(datos.get(i));
        }

         */
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
        View list=inflar.inflate(R.layout.lista_ver_tareas_dia, viewGroup, false);

        //disponemos las variables de los elementos gráficos
        TextView desc = (TextView) list.findViewById(R.id.txtDescTarea);
        TextView idTarea = (TextView) list.findViewById(R.id.idTarea);
        TextView fechaNotif = (TextView) list.findViewById(R.id.txtFecha);
        TextView fecha = (TextView) list.findViewById(R.id.txtFechaTarea);
        CheckBox hecha = (CheckBox) list.findViewById(R.id.chkTareaCompletada);
        CheckBox pasada = (CheckBox) list.findViewById(R.id.chkTareaPasada);
        LinearLayout ly = (LinearLayout) list.findViewById(R.id.lyTarea);

        idTarea.setText(String.valueOf(tareas.get(i).getId()));
        desc.setText(tareas.get(i).getDescripcion());
        fecha.setText(formato.format(tareas.get(i).getFecha()));
        if(tareas.get(i).getNotificacion()==null){
            fechaNotif.setText("No notif");
        }else{
            fechaNotif.setText(formato.format(tareas.get(i).getNotificacion()));
        }
        hecha.setChecked(tareas.get(i).isRealizado());
        try {
            if(formato.parse(formato.format(new Date())).after(tareas.get(i).getFecha())){
                pasada.setChecked(true);
            }else{
                pasada.setChecked(false);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        switch (tareas.get(i).getTipo()){
            case "EXAMEN":
                //no se si setBackgroundResource es lo adecuado
                ly.setBackgroundResource(R.drawable.fondo_tarea_ex);
                break;
            case "TAREA":
                ly.setBackgroundResource(R.drawable.fondo_tarea_tar);
                break;
            case "OTRO":
                ly.setBackgroundResource(R.drawable.fondo_tarea_otr);
                break;
        }

        //se añade la fuente (puede ser opcional)
        if(fuente!=null){
            desc.setTypeface(fuente);
            fechaNotif.setTypeface(fuente);
        }

        //Añadimos una animacion
        Animation animacion=null;
        animacion = AnimationUtils.loadAnimation(contexto,R.anim.atg_lista_deslizar_izq);
        list.startAnimation(animacion);

        return list;
    }
}
