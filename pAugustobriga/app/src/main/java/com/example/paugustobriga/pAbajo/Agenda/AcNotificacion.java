package com.example.paugustobriga.pAbajo.Agenda;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.work.Data;
import androidx.work.WorkManager;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.paugustobriga.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AcNotificacion extends AppCompatActivity {

    SimpleDateFormat formato=new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat formatoFinal=new SimpleDateFormat("dd/MM/yyyy HH:mm");
    String id;
    String claseOrigen;

    Button btnGuardarNot, btnBorrarNot, btnElegirDia, btnElegirHora;
    TextView txtHora, txtDia;

    Calendar actual = Calendar.getInstance();
    Calendar calendar = Calendar.getInstance();

    Typeface fuenteContenedores, fuenteNegrita;

    private int minutos, hora, dia, mes, anio;

    AccesoDatosAgenda ad;

    String fechaRecibida;

    String tipoTarea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacion);

        ad=new AccesoDatosAgenda(this);

        identificarElementos();
        importarFuentes();
        disponerFuentes();
        String datos = recibirDatos();
        id=datos.split("@")[0];
        claseOrigen = datos.split("@")[1];

        tipoTarea = recibirTipo();
        fechaRecibida = recibirFecha();
        if(fechaRecibida!=null){
            disponerFechaRecibida(fechaRecibida);
            borrarAlarma(id);
        }else{
            disponerFechaActual();
            btnBorrarNot.setVisibility(View.GONE);
        }

        seleccionarDia();
        seleccionarHora();

        guardarAlarma();

    }



    private void disponerFuentes(){
        txtHora.setTypeface(fuenteContenedores);
        btnGuardarNot.setTypeface(fuenteContenedores);
        btnBorrarNot.setTypeface(fuenteContenedores);
        btnElegirHora.setTypeface(fuenteContenedores);
        btnElegirDia.setTypeface(fuenteContenedores);
        txtDia.setTypeface(fuenteContenedores);
    }

    private void importarFuentes(){
        fuenteContenedores = ResourcesCompat.getFont(this, R.font.clear_sans_thin);
        fuenteNegrita = ResourcesCompat.getFont(this, R.font.ibm_plex_sans_thai_bold);
    }

    private String recibirTipo() {
        String resultado;
        Bundle extras = getIntent().getExtras();
        resultado = extras.getString("tipo");
        return resultado;
    }

    private void disponerFechaActual() {
        txtDia.setText(actual.get(Calendar.DAY_OF_MONTH)+"/"+(actual.get(Calendar.MONTH)+1)+"/"+actual.get(Calendar.YEAR));
        txtHora.setText(actual.get(Calendar.HOUR_OF_DAY)+":"+actual.get(Calendar.MINUTE));
    }

    private void disponerFechaRecibida(String f) {
        try {
            calendar.setTime(formatoFinal.parse(f));
            txtDia.setText(f.split(" ")[0]);
            txtHora.setText(f.split(" ")[1]);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private String recibirFecha() {
        String resultado;
        Bundle extras = getIntent().getExtras();
        resultado = extras.getString("editar");
        return resultado;
    }

    private void seleccionarDia(){
        btnElegirDia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                anio = actual.get(Calendar.YEAR);
                mes = actual.get(Calendar.MONTH);
                dia = actual.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendar.set(Calendar.DAY_OF_MONTH, i2);
                        calendar.set(Calendar.MONTH,i1);
                        calendar.set(Calendar.YEAR,i);

                        String strDate = formato.format(calendar.getTime());
                        try {
                            //no se puede comprobar con el comprobarFecha() ya que ahí se utiliza junto con la hora
                            if(formato.parse(strDate).before(formato.parse(formato.format(new Date())))){
                                txtDia.setText(strDate);
                            }else{
                                txtDia.setText(strDate);
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                },anio,mes,dia);
                //le cambiamos el fondo
                datePickerDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.fondo_calendario));
                datePickerDialog.show();
            }
        });
    }

    private void seleccionarHora(){
        btnElegirHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtDia.getText().toString().length()>0) {
                    hora = actual.get(Calendar.HOUR_OF_DAY);
                    minutos = actual.get(Calendar.MINUTE);

                    TimePickerDialog timePickerDialog = new TimePickerDialog(view.getContext(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int i, int i1) {
                            calendar.set(Calendar.HOUR_OF_DAY, i);
                            calendar.set(Calendar.MINUTE, i1);
                            calendar.set(Calendar.SECOND,0);
                            calendar.set(Calendar.MILLISECOND,0);

                            if (comprobarFecha(calendar)) {
                                txtHora.setText(String.format("%02d:%02d", i, i1));
                            } else {
                                txtHora.setText(String.format("%02d:%02d", i, i1));
                            }

                        }
                    }, hora, minutos, true);
                    timePickerDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.fondo_calendario));
                    timePickerDialog.show();
                }else {
                    Toast.makeText(getApplicationContext(), "Debes de elegir un día",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void guardarAlarma(){
        btnGuardarNot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tag = id;
                Long alerta = calendar.getTimeInMillis() - System.currentTimeMillis();

                if(comprobarFecha(calendar)){
                    int tmp=Integer.parseInt(id);
                    Data data = guardarDatos("Agenda de InfoAugustóbriga", "Recordatorio: "+tipoTarea.toLowerCase()+" por hacer",tmp);
                    EjecutarNotificacion.almacenarNotificacion(alerta,data,tag);
                    if(fechaRecibida!=null){
                        borrarAlarma(id);
                    }
                    if(!ad.modificarNotificacion(Integer.parseInt(id),formatoFinal.format(calendar.getTime()))){
                            Toast.makeText(getApplicationContext(), "Error al guardar la notificacion", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "Alarma guardada", Toast.LENGTH_SHORT).show();
                    }
                    volver();
                }else{
                    Toast.makeText(getApplicationContext(), "No puedes poner una alarma anterior a hoy",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void borrarAlarma(String tag){
        btnBorrarNot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WorkManager.getInstance(getApplicationContext()).cancelAllWorkByTag(tag);
                if(!ad.modificarNotificacion(Integer.parseInt(id),null)){
                    Toast.makeText(getApplicationContext(), "Error al borrar la notificación", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Alarma eliminada", Toast.LENGTH_SHORT).show();
                }
                volver();

            }
        });
    }


    private Data guardarDatos(String titulo, String detalle, int idNoti){
        return new Data.Builder()
                .putString("titulo",titulo)
                .putString("detalle",detalle)
                .putInt("idnoti",idNoti).build();

    }

    private boolean comprobarFecha(Calendar c){
        boolean resultado=false;
        try {
            Date fechaE =formatoFinal.parse(formatoFinal.format(c.getTime()));
            Date fechaActual = formatoFinal.parse(formatoFinal.format(new Date()));
            String temp = formatoFinal.format(new Date());
            if(fechaE.after(fechaActual)){
                resultado = true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    private void identificarElementos(){
        btnBorrarNot = findViewById(R.id.btnBorrarNot);
        btnGuardarNot = findViewById(R.id.btnGuardarNot);
        btnElegirDia = findViewById(R.id.btnNotDia);
        btnElegirHora = findViewById(R.id.btnNotHora);
        txtHora = findViewById(R.id.txtNotHora);
        txtDia = findViewById(R.id.txtNotDia);
    }


    private String recibirDatos(){
        String resultado;
        Bundle extras = getIntent().getExtras();
        resultado = extras.getString("datos");
        return resultado;
    }


    @Override
    public void onBackPressed() {
        volver();
    }

    private void volver(){
        if(claseOrigen.equalsIgnoreCase("AcVerTareas")){
            Intent i=new Intent(getApplicationContext(), AcVerTareas.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
        if(claseOrigen.equalsIgnoreCase("AcVerTareasDia")){
            Intent i=new Intent(getApplicationContext(), AcVerTareasDia.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.putExtra("datos",ad.obtenerFecha(id));
            startActivity(i);
        }
    }



}