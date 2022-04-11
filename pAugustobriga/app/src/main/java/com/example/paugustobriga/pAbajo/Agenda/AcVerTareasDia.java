package com.example.paugustobriga.pAbajo.Agenda;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.example.paugustobriga.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AcVerTareasDia extends AppCompatActivity {

    SimpleDateFormat formato=new SimpleDateFormat("dd/MM/yyyy");
    AccesoDatosAgenda ad=new AccesoDatosAgenda(this);

    TextView txtFecha, txtTareasCompletadas;
    Button btnAnadirTarea;
    CheckBox chkCompletada, chkPasada;
    ListView lsTareasDia;

    Typeface fuenteContenedores;

    /*
    boolean pasada,completada;
    int tareasCompletadas;
     */

    Date fechaDelDia;
    String fechaNoFormateada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_tareas_dia);

        identificarElementos();
        importarFuentes();
        fechaNoFormateada = recibirDatos();
        ArrayList<Tarea> listaTareas = ad.obtenerTareasDia(fechaNoFormateada);
        txtFecha.setText(fechaFormateada(fechaNoFormateada));
        lsTareasDia.setAdapter(new AdaptadorVerTareaDia(this,fuenteContenedores,listaTareas));

        int tareasCompletadas = 0;
        int totalTareas = 0;
        tareasCompletadas = contarTareasCompletadas(listaTareas);
        totalTareas = contarTotalTareas(listaTareas);
        txtTareasCompletadas.setText(tareasCompletadas+"/"+totalTareas);

        chkCompletada.setChecked(comprobarCompletada(totalTareas,tareasCompletadas));

        try {
            chkPasada.setChecked(comprobarPasada(formato.parse(fechaNoFormateada)));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        AnadirTarea();
        irTarea();
    }

    private void AnadirTarea(){
        btnAnadirTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(), AcAnadirTarea.class);
                i.putExtra("datos",fechaNoFormateada);
                startActivity(i);
            }
        });
    }

    private void irTarea(){
        lsTareasDia.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //AQUÍ SE EDITARÍA LA TAREA, se iria a la actividad de añadir tarea
                //CAMBIAR EL NOMBRE DE LOS DATOS DE ENVIO Y CAMBIAR EL TEXTO DEL BOTON
                //CAMBIAR ENTRE CREAR TAREA Y EDITAR TAREA EN EL TEXTO DEL BOTON
                //Intent i=new Intent(getApplicationContext(), AcVerTareasDia.class);
                //i.putExtra("datos",fecha2);
                //startActivity(i);
            }
        });
    }

    private int contarTotalTareas(ArrayList<Tarea> listaTareas){
        return listaTareas.size();
    }

    private int contarTareasCompletadas(ArrayList<Tarea> listaTareas){
        int resultado = 0;
        for(Tarea t:listaTareas){
            if(t.isRealizado()){
                resultado++;
            }
        }
        return resultado;
    }

    private boolean comprobarPasada(Date fecha){
        boolean resultado=false;
        if(new Date().after(fecha)){
            resultado=true;
        }
        return resultado;
    }

    private boolean comprobarCompletada(int cantidadTareas, int cantidadCompletada){
        boolean resultado = false;
        if(cantidadTareas==cantidadCompletada){
            resultado = true;
        }
        return resultado;
    }



    private void identificarElementos(){
        chkCompletada = findViewById(R.id.chkTareasCompletada);
        chkPasada = findViewById(R.id.chkTareasPasada);
        txtFecha = findViewById(R.id.txtFechaVerTareaDia);
        txtTareasCompletadas = findViewById(R.id.txtTareasCompletadas);
        btnAnadirTarea = findViewById(R.id.btnAnadirTarea);
        lsTareasDia = findViewById(R.id.lstVerTareasDia);
    }

    private String recibirDatos(){
        String resultado;
        Bundle extras = getIntent().getExtras();
        resultado = extras.getString("datos");
        return resultado;
    }

    private void importarFuentes(){
        fuenteContenedores = ResourcesCompat.getFont(this, R.font.ibm_plex_sans_thai_bold);
    }

    public String fechaFormateada(String fecha){
        String[] cadena = formato.format(fecha).split("/");
        String mes="";
        switch (cadena[1]){
            case "01":
                mes="enero";
                break;
            case "02":
                mes="febrero";
                break;
            case "03":
                mes="marzo";
                break;
            case "04":
                mes="abril";
                break;
            case "05":
                mes="mayo";
                break;
            case "06":
                mes="junio";
                break;
            case "07":
                mes="julio";
                break;
            case "08":
                mes="agosto";
                break;
            case "09":
                mes="septiembre";
                break;
            case "10":
                mes="octubre";
                break;
            case "11":
                mes="noviembre";
                break;
            case "12":
                mes="diciembre";
                break;
        }
        return cadena[0]+" de "+mes+" de "+cadena[2];
    }
}