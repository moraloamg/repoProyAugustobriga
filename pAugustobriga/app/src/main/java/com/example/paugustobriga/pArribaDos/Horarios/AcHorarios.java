package com.example.paugustobriga.pArribaDos.Horarios;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.paugustobriga.AcPrincipal;
import com.example.paugustobriga.R;

import java.util.ArrayList;
import java.util.Arrays;

public class AcHorarios extends AppCompatActivity implements AsyncRespuesta{

    //contenedores
    LinearLayout contEso, contFp, contBachillerato;
    //texto
    TextView txtCabeceraHorarios, txtEso, txtFp, txtBachillerato;
    //fuentes
    Typeface fuenteCabeceraHorarios, fuenteContenedores;
    //listas de texto
    ArrayList<TextView> lFuentesContHorarios;
    ArrayList<TextView> lFuentesCabeceraHorarios;
    //Animaciones
    Animation atgHorario1,atgHorario2,atgHorario3, atgCabecera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horarios);

        identificarContenedores();
        identificarTexto();
        importarFuentes();
        cargarAnimaciones();
        prepararFuentes();
        disponerFuentes(fuenteCabeceraHorarios, fuenteContenedores);
        disponerAnimaciones();

        accionIrFp();
        accionIrBachillerato();
        accionIrEso();

    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(getApplicationContext(), AcPrincipal.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    private void accionIrFp(){
        contFp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtenerDatosFichero(txtFp.getText().toString());
            }
        });
    }

    private  void accionIrBachillerato(){
        contBachillerato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtenerDatosFichero(txtBachillerato.getText().toString());
            }
        });
    }

    private void accionIrEso(){
        contEso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtenerDatosFichero(txtEso.getText().toString());
            }
        });
    }

    //-------------------------------------------------------

    private void obtenerDatosFichero(String tipoCurso){
        String url = crearUrl(tipoCurso);
        LecturaFichero lector=new LecturaFichero(url);
        lector.delegar = this;
        lector.execute();

    }

    @Override
    public void procesoFinalizado(ArrayList<String> salida) {
        Log.d("procesoFinalizado", String.valueOf(salida.size()));
        //una vez conseguidos todos los datos se cambia a la actividad
        Intent i=new Intent(this, AcCursos.class);
        i.putExtra("datos",salida);
        startActivity(i);
    }


    private String crearUrl(String datos) {
        String resultado = "https://raw.githubusercontent.com/moraloamg/pruebaAugustobriga/main/";
        switch (datos){
            case "Formación Profesional":
                resultado=resultado + "cursosFp.txt";
                break;
            case "Bachillerato":
                resultado=resultado + "cursosBachillerato.txt";
                break;
            case "Educación Secundaria":
                resultado=resultado + "cursosEso.txt";
                break;

        }
        Log.d("seleccionarCurso", resultado);
        return resultado;
    }

    //------------------------------------------------------

    private void cargarAnimaciones(){
        atgHorario1 = AnimationUtils.loadAnimation(this,R.anim.atg_horarios1);
        atgHorario2 = AnimationUtils.loadAnimation(this,R.anim.atg_horarios2);
        atgHorario3 = AnimationUtils.loadAnimation(this,R.anim.atg_horarios3);
        atgCabecera = AnimationUtils.loadAnimation(this,R.anim.atg_cabecera);
    }

    private void disponerAnimaciones(){
        contFp.startAnimation(atgHorario1);
        contBachillerato.startAnimation(atgHorario2);
        contEso.startAnimation(atgHorario3);
        txtCabeceraHorarios.startAnimation(atgCabecera);
    }


    private void identificarContenedores(){
        contEso = findViewById(R.id.contEso);
        contFp = findViewById(R.id.contFp);
        contBachillerato = findViewById(R.id.contBachillerato);
    }

    private void identificarTexto(){
        txtCabeceraHorarios = findViewById(R.id.txtCabeceraHorarios);
        txtEso = findViewById(R.id.txtEso);
        txtFp = findViewById(R.id.txtFp);
        txtBachillerato = findViewById(R.id.txtBachillerato);
    }

    private void importarFuentes(){
        fuenteContenedores = ResourcesCompat.getFont(this, R.font.ibm_plex_sans_thai_bold);
        fuenteCabeceraHorarios = ResourcesCompat.getFont(this, R.font.clear_sans_thin);
    }

    private void disponerFuentes(Typeface f1, Typeface f2){
        for(TextView x: lFuentesCabeceraHorarios){
            x.setTypeface(f1,Typeface.NORMAL);
        }
        for(TextView x: lFuentesContHorarios){
            x.setTypeface(f2,Typeface.NORMAL);
        }
    }

    private void prepararFuentes(){
        lFuentesContHorarios=new ArrayList<TextView>(Arrays.asList(txtEso,txtFp,txtBachillerato));
        lFuentesCabeceraHorarios=new ArrayList<>(Arrays.asList(txtCabeceraHorarios));
    }

}