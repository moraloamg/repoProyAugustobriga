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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.paugustobriga.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AcVerTareas extends AppCompatActivity {
    AccesoDatosAgenda ad;
    Button btnBuscar;
    EditText editBuscar;
    TextView txtTotal, txtComp, txtPas;
    Spinner spVerTareas;
    ListView lstVerTareas;
    //Estas opciones serán únicas
    final String[] opciones=new String[]{"Todo","Exámenes","Tareas","Otros","Realizadas","No Realizadas","Pasadas","Con Notificación"};
    ArrayList<Tarea> datos;
    Typeface fuenteContenedores, fuenteNegrita;
    SimpleDateFormat formato=new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_tareas);
        ad=new AccesoDatosAgenda(getApplicationContext());

        identificarElementos();
        iniciarOpcionesSpinner();
        elegirOpcionSpinner();
        importarFuentes();
        disponerFuentes();
        datos=ad.obtenerTareas();
        limpiarNotificacionesPasadas(datos);
        lstVerTareas.setAdapter(new AdaptadorVerTareaDia(this,fuenteNegrita,datos));
        actualizarContadores(datos);

        buscarDatos();
        cambioTexto();
        irTarea();
        lstVerTareas.setLongClickable(true);
        opcionesTarea();

    }

    private void identificarElementos(){
        btnBuscar = findViewById(R.id.btnBuscar);
        editBuscar = findViewById(R.id.editBuscarDesc);
        txtTotal = findViewById(R.id.txtTotalVerTarea);
        txtComp = findViewById(R.id.txtCompletadasVerTarea);
        txtPas = findViewById(R.id.txtPasadasVerTarea);
        spVerTareas = findViewById(R.id.spVerTareas);
        lstVerTareas = findViewById(R.id.lstTareas);
    }

    //método que comprueba que al estar vacío el EditText no se quede vacía la lista
    private void cambioTexto(){
        editBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                comprobarEditTextVacio();
            }
        });
    }

    //método que comprueba que al estar vacío el EditText no se quede vacía la lista
    private void comprobarEditTextVacio(){
        if(editBuscar.getText().toString().length()==0){
            lstVerTareas.setAdapter(new AdaptadorVerTareaDia(getApplicationContext(),fuenteNegrita,datos));
            actualizarContadores(datos);
        }
    }

    private void elegirOpcionSpinner(){
        spVerTareas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (opciones[i]){
                    case "Todo":
                        lstVerTareas.setAdapter(new AdaptadorVerTareaDia(getApplicationContext(),fuenteNegrita,datos));
                        actualizarContadores(datos);
                        break;
                    case "Exámenes":
                        ArrayList<Tarea> tipoExamenes = ad.buscarTipo("EXAMEN");
                        lstVerTareas.setAdapter(new AdaptadorVerTareaDia(getApplicationContext(),fuenteNegrita,tipoExamenes));
                        actualizarContadores(tipoExamenes);
                        break;
                    case "Tareas":
                        ArrayList<Tarea> tipoTarea = ad.buscarTipo("TAREA");
                        lstVerTareas.setAdapter(new AdaptadorVerTareaDia(getApplicationContext(),fuenteNegrita,tipoTarea));
                        actualizarContadores(tipoTarea);
                        break;
                    case "Otros":
                        ArrayList<Tarea> tipoOtro = ad.buscarTipo("OTRO");
                        lstVerTareas.setAdapter(new AdaptadorVerTareaDia(getApplicationContext(),fuenteNegrita,tipoOtro));
                        actualizarContadores(tipoOtro);
                        break;
                    case "Realizadas":
                        ArrayList<Tarea> realizadas = ad.buscarRealizadas();
                        lstVerTareas.setAdapter(new AdaptadorVerTareaDia(getApplicationContext(),fuenteNegrita,realizadas));
                        actualizarContadores(realizadas);
                        break;
                    case "No Realizadas":
                        ArrayList<Tarea> noRealizadas = ad.buscarNoRealizadas();
                        lstVerTareas.setAdapter(new AdaptadorVerTareaDia(getApplicationContext(),fuenteNegrita,noRealizadas));
                        actualizarContadores(noRealizadas);
                        break;
                    case "Con Notificación":
                        ArrayList<Tarea> lsNotif = ad.buscarConNotificaciones();
                        lstVerTareas.setAdapter(new AdaptadorVerTareaDia(getApplicationContext(),fuenteNegrita,lsNotif));
                        actualizarContadores(lsNotif);
                        break;
                    case "Pasadas":
                        ArrayList<Tarea> listaPasadas=new ArrayList<>();
                        for(Tarea t:datos){
                            try {
                                if(t.getFecha().before(formato.parse(formato.format(new Date())))){
                                    listaPasadas.add(t);
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        lstVerTareas.setAdapter(new AdaptadorVerTareaDia(getApplicationContext(),fuenteNegrita,listaPasadas));
                        actualizarContadores(listaPasadas);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void irTarea(){
        lstVerTareas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String seleccionado = ((TextView) view.findViewById(R.id.idTarea)).getText().toString();
                if(!ad.comprobarHecho(seleccionado)){
                    Intent i2=new Intent(getApplicationContext(), AcAnadirTarea.class);
                    i2.putExtra("datos",ad.obtenerFecha(seleccionado)+"P"+seleccionado+"V");
                    startActivity(i2);
                }else{
                    Toast.makeText(getApplicationContext(),"No puedes editar una tarea realizada", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void buscarDatos(){
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editBuscar.getText().toString().trim().length() > 0){
                    String busqueda = editBuscar.getText().toString();
                    ArrayList<Tarea> resultado = ad.buscarTarea(busqueda);
                    lstVerTareas.setAdapter(new AdaptadorVerTareaDia(getApplicationContext(),fuenteNegrita,resultado));
                    actualizarContadores(resultado);
                }
            }
        });
    }

    private void actualizarContadores(ArrayList<Tarea> t){
        txtTotal.setText("Total   "+String.valueOf(t.size()));
        int contPasadas=0;
        int contCompletadas=0;
        for(Tarea ta:t){
            if(ta.isRealizado()){
                contCompletadas++;
            }
            try {
                if(formato.parse(formato.format(new Date())).after(ta.getFecha())){
                    contPasadas++;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        txtComp.setText("Completadas   "+String.valueOf(contCompletadas));
        txtPas.setText("Pasadas   "+String.valueOf(contPasadas));
    }



    private void iniciarOpcionesSpinner(){
        ArrayAdapter<String> adaptador =
                new ArrayAdapter<String>(getApplicationContext(),
                        R.layout.formato_spinner,opciones);
        adaptador.setDropDownViewResource(R.layout.formato_spinner);
        spVerTareas.setAdapter(adaptador);
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(getApplicationContext(), AcCalendarioAgenda.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    private void disponerFuentes(){
        txtTotal.setTypeface(fuenteContenedores);
        txtPas.setTypeface(fuenteContenedores);
        txtComp.setTypeface(fuenteContenedores);
        btnBuscar.setTypeface(fuenteNegrita);
        editBuscar.setTypeface(fuenteContenedores);
    }

    private void importarFuentes(){
        fuenteContenedores = ResourcesCompat.getFont(this, R.font.clear_sans_thin);
        fuenteNegrita = ResourcesCompat.getFont(this, R.font.ibm_plex_sans_thai_bold);
    }

    //-------------------------- NOTIFICACIONES ---------------------------------------

    private void opcionesTarea(){
        lstVerTareas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                final Dialog myDialog = new Dialog(AcVerTareas.this);
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.setContentView(R.layout.dialog_tareas);
                myDialog.setCancelable(true);

                Button irNotificacion = (Button) myDialog.findViewById(R.id.irNotificacion);
                irNotificacion.setTypeface(fuenteNegrita);


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
                irBorrar.setTypeface(fuenteNegrita);
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
            i2.putExtra("datos", id + "@AcVerTareas");
            i2.putExtra("tipo", ad.obtenerTipo(id));
            startActivity(i2);
        }else if(!hecha && !pasada && notif.equalsIgnoreCase("Con notif")){
            Intent i2 = new Intent(getApplicationContext(), AcNotificacion.class);
            i2.putExtra("datos", id + "@AcVerTareas");
            i2.putExtra("editar", ad.obtenerNotificacion(id));
            i2.putExtra("tipo", ad.obtenerTipo(id));
            startActivity(i2);
        }else{
            Toast.makeText(getApplicationContext(),"No puedes añadir una notificación a una tarea atrasada o completada", Toast.LENGTH_LONG).show();
        }
    }

    private void opcionBorrarTarea(View view){
        AlertDialog.Builder dialogo1=new AlertDialog.Builder(AcVerTareas.this);
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


                    if(!ad.modificarNotificacion(ta.getId(),null)){
                        Toast.makeText(getApplicationContext(),"Error al modificar la notificacion", Toast.LENGTH_LONG).show();

                    }
                    WorkManager.getInstance(getApplicationContext()).cancelAllWorkByTag(String.valueOf(ta.getId()));
                    t.get(t.indexOf(ta)).setNotificacion(null);
                }

            }
        }
    }
}