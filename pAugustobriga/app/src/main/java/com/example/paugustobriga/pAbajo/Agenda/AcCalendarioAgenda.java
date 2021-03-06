package com.example.paugustobriga.pAbajo.Agenda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.paugustobriga.AcPrincipal;
import com.example.paugustobriga.R;
import com.example.paugustobriga.pArriba.Horarios.AcCursos;
import com.example.paugustobriga.pArriba.Horarios.AcHorarios;
import com.example.paugustobriga.pMedio.Profesorado.AcOrganizacion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AcCalendarioAgenda extends AppCompatActivity {

    AccesoDatosAgenda ad;

    //elementos interfaz
    CalendarView calendario;
    TextView txtAgenda,txtContRojo,txtContAzul,txtContAmarillo,txtFecha;
    LinearLayout contResumen, linearCalendario;
    Button btnAnadir,btnVerTareas;
    //Variables
    SimpleDateFormat formato=new SimpleDateFormat("dd/MM/yyyy");
    Date fecha;

    Typeface fuenteContenedores, fuenteNegrita;
    Animation atgCabecera, atgPanel1, atgPanel2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ad=new AccesoDatosAgenda(this);
        setContentView(R.layout.activity_calendario_agenda);

        identificarElementos();
        seleccionarFecha();
        importarFuentes();
        disponerFuentes();
        cargarAnimaciones();
        disponerAnimaciones();

    }

    private void resumenDia(String fechaS){
        ArrayList<Tarea> listaTareas = ad.obtenerTareasDia(fechaS);
        txtFecha.setText("Tareas del "+fechaS);

        int contRojos = 0;
        int contAzul = 0;
        int contAmarillo = 0;
        for(Tarea t:listaTareas){
            if(t.getTipo().equalsIgnoreCase("EXAMEN")){
                contRojos++;
            }
            if(t.getTipo().equalsIgnoreCase("TAREA")){
                contAzul++;
            }
            if(t.getTipo().equalsIgnoreCase("OTRO")){
                contAmarillo++;
            }
        }
        txtContRojo.setText(String.valueOf(contRojos));
        txtContAzul.setText(String.valueOf(contAzul));
        txtContAmarillo.setText(String.valueOf(contAmarillo));
        txtContAmarillo.setTypeface(fuenteContenedores);
        txtContAzul.setTypeface(fuenteContenedores);
        txtContRojo.setTypeface(fuenteContenedores);
    }


    private void seleccionarFecha(){
        //por defecto seleccionaremos la fecha actual, en caso de darle al boton de seleccionar tarea
        fecha = new Date();
        AnadirTarea(fecha);
        verTareas(fecha);
        resumenDia(formato.format(fecha));
        //en caso de cambiar la fecha entra en escena el listener
        calendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                try {
                    //hay que sumarle +1 al mes
                    fecha=formato.parse(i2+"/"+(i1+1)+"/"+i);
                    if(fecha!=null) {
                        resumenDia(formato.format(fecha));
                        AnadirTarea(fecha);
                        verTareas(fecha);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }




    private void AnadirTarea(Date pFecha){
        btnAnadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fecha2 = formato.format(pFecha);
                Intent i=new Intent(getApplicationContext(), AcVerTareasDia.class);
                i.putExtra("datos",fecha2);
                startActivity(i);
            }
        });
    }

    private void verTareas(Date pFecha){
        btnVerTareas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fecha2 = formato.format(pFecha);
                Intent i=new Intent(getApplicationContext(), AcVerTareas.class);
                startActivity(i);
            }
        });
    }

    private void identificarElementos(){
        calendario = findViewById(R.id.calendario);
        txtAgenda = findViewById(R.id.txtCabeceraAgenda);
        btnAnadir = findViewById(R.id.btnAnadirTareas);
        btnVerTareas = findViewById(R.id.btnVerMisTareas);
        txtContRojo = findViewById(R.id.txtContRojo);
        txtContAzul = findViewById(R.id.txtContAzul);
        txtContAmarillo = findViewById(R.id.txtContAmarillo);
        txtFecha = findViewById(R.id.txtFecha);
        contResumen = findViewById(R.id.contResumen);
        linearCalendario = findViewById(R.id.linearLayoutCalendario);
    }

    private void importarFuentes(){
        fuenteContenedores = ResourcesCompat.getFont(this, R.font.clear_sans_thin);
        fuenteNegrita = ResourcesCompat.getFont(this,R.font.ibm_plex_sans_thai_bold);
    }

    private void disponerFuentes(){
        txtAgenda.setTypeface(fuenteContenedores);
        btnAnadir.setTypeface(fuenteNegrita);
        btnVerTareas.setTypeface(fuenteNegrita);
        txtFecha.setTypeface(fuenteContenedores);
        txtContAzul.setTypeface(fuenteContenedores);
        txtContAmarillo.setTypeface(fuenteContenedores);
        txtContRojo.setTypeface(fuenteContenedores);
    }

    private void cargarAnimaciones(){
        atgCabecera = AnimationUtils.loadAnimation(this,R.anim.atg_cabecera);
        atgPanel1 = AnimationUtils.loadAnimation(this,R.anim.atg_horarios1);
        atgPanel2 = AnimationUtils.loadAnimation(this,R.anim.atg_horarios2);
    }

    private void disponerAnimaciones(){

        txtAgenda.startAnimation(atgCabecera);
        contResumen.startAnimation(atgPanel1);
        linearCalendario.startAnimation(atgPanel2);
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(getApplicationContext(), AcPrincipal.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}