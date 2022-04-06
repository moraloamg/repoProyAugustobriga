package com.example.paugustobriga.pAbajo.Agenda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.paugustobriga.AcPrincipal;
import com.example.paugustobriga.R;
import com.example.paugustobriga.pMedio.Profesorado.AcOrganizacion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AcCalendarioAgenda extends AppCompatActivity {

    //elementos interfaz
    CalendarView calendario;
    TextView txtAgenda,txtContRojo,txtContAzul,txtContAmarillo,txtFecha;
    Button btnAnadir,btnVerTareas;
    //Variables
    SimpleDateFormat formato=new SimpleDateFormat("dd/MM/yyyy");
    Date fecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario_agenda);

        identificarElementos();
        seleccionarFecha();

    }

    private void seleccionarFecha(){
        //por defecto seleccionaremos la fecha actual
        fecha = new Date();
        AnadirTarea(fecha);
        verTareas(fecha);
        //en caso de cambiar la fecha entra en escena el listener
        calendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                try {
                    //hay que sumarle +1 al mes
                    fecha=formato.parse(i2+"/"+(i1+1)+"/"+i);
                    if(fecha!=null) {
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
                Toast.makeText(getApplicationContext(), "Añadir tarea al "+fecha2, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void verTareas(Date pFecha){
        btnVerTareas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fecha2 = formato.format(pFecha);
                Toast.makeText(getApplicationContext(), "Ver tareas del "+fecha2, Toast.LENGTH_SHORT).show();
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
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(getApplicationContext(), AcPrincipal.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}