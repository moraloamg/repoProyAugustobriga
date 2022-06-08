package com.example.paugustobriga.pAbajo.Agenda;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.work.WorkManager;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.paugustobriga.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AcVerTareasDia extends AppCompatActivity {

    SimpleDateFormat formato=new SimpleDateFormat("dd/MM/yyyy");
    AccesoDatosAgenda ad;

    TextView txtFecha, txtTareasCompletadas;
    Button btnAnadirTarea;
    CheckBox chkCompletada, chkPasada;
    ListView lsTareasDia;

    Typeface fuenteContenedores, fuenteNegrita;

    //no formateada se refiere sin el nombre del mes, solo con el número
    String fechaNoFormateada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_tareas_dia);
        ad=new AccesoDatosAgenda(getApplicationContext());

        identificarElementos();
        importarFuentes();
        disponerFuentes();
        fechaNoFormateada = recibirDatos();
        ArrayList<Tarea> listaTareas = ad.obtenerTareasDia(fechaNoFormateada);
        txtFecha.setText(fechaFormateada(fechaNoFormateada));
        limpiarNotificacionesPasadas(listaTareas);
        lsTareasDia.setAdapter(new AdaptadorVerTareaDia(this,fuenteNegrita,listaTareas));

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
        lsTareasDia.setLongClickable(true);
        opcionesTarea();

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

                String seleccionado = ((TextView) view.findViewById(R.id.idTarea)).getText().toString();
                if(!ad.comprobarHecho(seleccionado)){
                    Intent i2=new Intent(getApplicationContext(), AcAnadirTarea.class);
                    i2.putExtra("datos",fechaNoFormateada+"P"+seleccionado);
                    startActivity(i2);
                }else{
                    Toast.makeText(getApplicationContext(),"No puedes editar una tarea realizada", Toast.LENGTH_LONG).show();
                }

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
        try {
            if(formato.parse(formato.format(new Date())).after(fecha)){
                resultado=true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    private boolean comprobarCompletada(int cantidadTareas, int cantidadCompletada){
        boolean resultado = false;
        if(cantidadTareas==cantidadCompletada && (cantidadCompletada != 0 && cantidadTareas != 0)){
            resultado = true;
        }
        return resultado;
    }

    private void disponerFuentes(){
        txtFecha.setTypeface(fuenteContenedores);
        txtTareasCompletadas.setTypeface(fuenteContenedores);
        btnAnadirTarea.setTypeface(fuenteNegrita);
        chkCompletada.setTypeface(fuenteContenedores);
        chkPasada.setTypeface(fuenteContenedores);
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
        fuenteContenedores = ResourcesCompat.getFont(this, R.font.clear_sans_thin);
        fuenteNegrita = ResourcesCompat.getFont(this,R.font.ibm_plex_sans_thai_bold);
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(getApplicationContext(), AcCalendarioAgenda.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    public String fechaFormateada(String fecha){
        String[] cadena = fecha.split("/");
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

    //-------------------------- NOTIFICACIONES ---------------------------------------

    //MEJORAR ESTA PARTE
    private void opcionesTarea(){
        lsTareasDia.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                final Dialog myDialog = new Dialog(AcVerTareasDia.this);
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.setContentView(R.layout.dialog_tareas);
                myDialog.setTitle("Elige una opción");
                myDialog.setCancelable(true);

                Button irNotificacion = (Button) myDialog.findViewById(R.id.irNotificacion);


                String compNoti = ((TextView) view.findViewById(R.id.txtNotificacion)).getText().toString();
                if(compNoti.equalsIgnoreCase("Con notif")){
                    irNotificacion.setText("Editar notificacion");
                }

                irNotificacion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view1) {
                        opcionNotificacion(view);
                    }
                });

                Button irBorrar= (Button) myDialog.findViewById(R.id.irBorrarTarea);
                irBorrar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view2) {
                        opcionBorrarTarea(view);
                    }
                });

                myDialog.show();
                return true;
            }
        });
    }

    private void opcionNotificacion(View view){
        String id = ((TextView) view.findViewById(R.id.idTarea)).getText().toString();
        boolean hecha = ((CheckBox) view.findViewById(R.id.chkTareaCompletada)).isChecked();
        boolean pasada = ((CheckBox) view.findViewById(R.id.chkTareaPasada)).isChecked();
        String notif = ((TextView) view.findViewById(R.id.txtNotificacion)).getText().toString();

        if(!hecha && !pasada && notif.equalsIgnoreCase("No notif")) {
            Intent i2 = new Intent(getApplicationContext(), AcNotificacion.class);
            i2.putExtra("datos", id + "@AcVerTareasDia");
            i2.putExtra("tipo", ad.obtenerTipo(id));
            startActivity(i2);
        }else if(!hecha && !pasada && notif.equalsIgnoreCase("Con notif")){
            Intent i2 = new Intent(getApplicationContext(), AcNotificacion.class);
            i2.putExtra("datos", id + "@AcVerTareasDia");
            i2.putExtra("editar", ad.obtenerNotificacion(id));
            i2.putExtra("tipo", ad.obtenerTipo(id));
            startActivity(i2);
        }else{
            Toast.makeText(getApplicationContext(),"No puedes añadir una notificación a una tarea atrasada o completada", Toast.LENGTH_LONG).show();
        }
    }

    private void opcionBorrarTarea(View view){
        AlertDialog.Builder dialogo1=new AlertDialog.Builder(AcVerTareasDia.this);
        dialogo1.setTitle("Borrar tarea");
        dialogo1.setMessage("¿Está seguro que desea borrar la tarea?");
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Borrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String seleccionado = ((TextView) view.findViewById(R.id.idTarea)).getText().toString();
                if(!ad.borrar(Integer.parseInt(seleccionado))){
                    Toast.makeText(getApplicationContext(),"Error al borrar la tarea", Toast.LENGTH_LONG).show();

                }
                WorkManager.getInstance(getApplicationContext()).cancelAllWorkByTag(seleccionado);

                if(!ad.modificarNotificacion(Integer.parseInt(seleccionado),null)){
                    Toast.makeText(getApplicationContext(),"Error al modificar la notificacion", Toast.LENGTH_LONG).show();
                }

                Toast.makeText(getApplicationContext(),"Has borrado la tarea", Toast.LENGTH_LONG).show();
                finish();
                startActivity(getIntent());

            }
        });
        dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int id){
                Toast.makeText(getApplicationContext(),"Cancelar", Toast.LENGTH_LONG).show();
            }
        });
        dialogo1.show();
    }


    private void limpiarNotificacionesPasadas(ArrayList<Tarea> t){
        for(Tarea ta:t){
            if(ta.getNotificacion()!=null){
                if(ta.isRealizado() || ta.getNotificacion().before(new Date())){


                    if(ad.modificarNotificacion(ta.getId(),null)){
                        //Toast.makeText(getApplicationContext(),"Error al modificar la notificacion", Toast.LENGTH_LONG).show();
                    }
                    WorkManager.getInstance(getApplicationContext()).cancelAllWorkByTag(String.valueOf(ta.getId()));
                    t.get(t.indexOf(ta)).setNotificacion(null);
                }

            }
        }
    }
}