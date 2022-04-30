package com.example.paugustobriga.pAbajo.Agenda;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.paugustobriga.AcPrincipal;
import com.example.paugustobriga.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AcAnadirTarea extends AppCompatActivity {
    //primera prueba con SourceTree
    AccesoDatosAgenda ad;
    SimpleDateFormat formato=new SimpleDateFormat("dd/MM/yyyy");
    //primera prueba: rama principal ej 1


    ImageView rojo,azul,amarillo;
    TextView txtFecha;
    CheckBox chkHecho;
    EditText editDescripcion;
    Button btnAnadirTarea;
    LinearLayout lyFondo;

    Typeface fuenteContenedores;

    String tipo="OTRO";
    String fecha;
    String id;

    boolean verTareas=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_tarea);
        ad=new AccesoDatosAgenda(this);

        identificarElementos();
        importarFuentes();
        fecha = recibirDatos();
        if(fecha.contains("P")){

            id=fecha.split("P")[1];
            fecha=fecha.split("P")[0];

            if(id.contains("V")){
                id = id.replace("V","");
                verTareas=true;  //implementar una interfaz para ver de que actividad viene la peticion??
            }

            txtFecha.setText(fechaFormateada(fecha));

            cambiarRojo();
            cambiarAzul();
            cambiarAmarillo();

            obtenerTipo(id);
            obtenerDescripcion(id);

            btnAnadirTarea.setText("Editar Tarea");
            avisoRealizarTarea();
            editarTarea();
        }else {
            txtFecha.setText(fechaFormateada(fecha));

            cambiarRojo();
            cambiarAzul();
            cambiarAmarillo();

            chkHecho.setVisibility(View.GONE);
            chkHecho.setEnabled(false);

            anadirTarea();
        }
    }

    private void identificarElementos() {
        rojo = findViewById(R.id.btnRojo);
        azul = findViewById(R.id.btnAzul);
        amarillo = findViewById(R.id.btnAmarillo);
        txtFecha = findViewById(R.id.txtFechaAnadirTarea);
        chkHecho = findViewById(R.id.chkHechoAnadirTarea);
        editDescripcion = findViewById(R.id.editTextAnadirTarea);
        btnAnadirTarea = findViewById(R.id.btnCrearTarea);
        lyFondo = findViewById(R.id.lyFondoCrearTarea);
    }

    private void obtenerTipo(String id){
        tipo=ad.obtenerTipo(id);
        switch (tipo){
            case "EXAMEN":
                lyFondo.setBackgroundResource(R.drawable.fondo_tarea_ex);
                break;
            case "TAREA":
                lyFondo.setBackgroundResource(R.drawable.fondo_tarea_tar);
                break;
            case "OTRO":
                lyFondo.setBackgroundResource(R.drawable.fondo_tarea_otr);
                break;
        }
    }

    private void obtenerDescripcion(String id){
        editDescripcion.setText(ad.obtenerDescripcion(id));
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

    @Override
    public void onBackPressed() {
        if(verTareas){
            Intent i=new Intent(getApplicationContext(), AcVerTareas.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }else{
            Intent i=new Intent(getApplicationContext(), AcVerTareasDia.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.putExtra("datos",fecha);
            startActivity(i);
        }

    }

    private void editarTarea(){
        btnAnadirTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editDescripcion.getText().toString().length()>0) {
                    ad.modificarTipo(Integer.parseInt(id),tipo);
                    if(chkHecho.isChecked()){
                        ad.realizarTarea(Integer.parseInt(id));
                    }

                    ad.modificarDescripcion(Integer.parseInt(id), editDescripcion.getText().toString());
                    onBackPressed();
                }else{
                    Toast.makeText(getApplicationContext(), "no has añadido ninguna descripción", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void avisoRealizarTarea(){
        chkHecho.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Toast.makeText(getApplicationContext(), "Recuerda que no podrás editar la tarea después de realizarla", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void anadirTarea(){
        btnAnadirTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editDescripcion.length()>0){
                    Tarea t=new Tarea();
                    t.setTipo(tipo);
                    t.setDescripcion(editDescripcion.getText().toString());
                    t.setRealizado(false);
                    try {
                        t.setFecha(formato.parse(fecha));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    ad.insertarTarea(t);
                    onBackPressed();
                }else{
                    Toast.makeText(getApplicationContext(), "no has añadido ninguna descripción", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void cambiarRojo(){
        rojo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lyFondo.setBackgroundResource(R.drawable.fondo_tarea_ex);
                tipo = "EXAMEN";
            }
        });
    }

    private void cambiarAzul(){
        azul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lyFondo.setBackgroundResource(R.drawable.fondo_tarea_tar);
                tipo = "TAREA";
            }
        });
    }

    private void cambiarAmarillo(){
        amarillo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lyFondo.setBackgroundResource(R.drawable.fondo_tarea_otr);
                tipo = "OTRO";
            }
        });
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
}