package com.example.paugustobriga.pMedio.Profesorado;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.paugustobriga.AcPrincipal;
import com.example.paugustobriga.R;

import java.util.ArrayList;
import java.util.Arrays;

public class AcProfesorado extends AppCompatActivity {

    //contenedores
    LinearLayout organizacion, tutorias, departamentos;
    //fuentes
    Typeface fuenteCabeceraProfesorado, fuenteContenedores;
    //texto
    TextView txtCabeceraProfesorado, txtOrganizacion, txtTutorias, txtDepartamentos;
    //listas de texto
    ArrayList<TextView> lFuentesContProfesorado;
    ArrayList<TextView> lFuentesCabeceraProfesorado;
    //Animaciones
    Animation atgContenedor1, atgContenedor2, atgContenedor3, atgCabecera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profesorado);

        identificarContenedores();
        identificarTexto();
        importarFuentes();
        cargarAnimaciones();
        prepararFuentes();
        disponerFuentes(fuenteCabeceraProfesorado, fuenteContenedores);
        disponerAnimaciones();

        irOrganizacion();
        irTutorias();



    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(getApplicationContext(), AcPrincipal.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    private void disponerFuentes(Typeface f1, Typeface f2){
        for(TextView x: lFuentesCabeceraProfesorado){
            x.setTypeface(f1,Typeface.NORMAL);
        }
        for(TextView x: lFuentesContProfesorado){
            x.setTypeface(f2,Typeface.NORMAL);
        }
    }

    private void prepararFuentes() {
        lFuentesContProfesorado=new ArrayList<TextView>(Arrays.asList(txtOrganizacion,txtTutorias,txtDepartamentos));
        lFuentesCabeceraProfesorado=new ArrayList<>(Arrays.asList(txtCabeceraProfesorado));
    }

    private void identificarContenedores(){
        organizacion = findViewById(R.id.contOrganizacion);
        tutorias = findViewById(R.id.contTutorias);
        departamentos = findViewById(R.id.contDepartamentos);
    }

    private void identificarTexto(){
        txtCabeceraProfesorado = findViewById(R.id.txtCabeceraProfesorado);
        txtOrganizacion= findViewById(R.id.txtOrganizacion);
        txtTutorias = findViewById(R.id.txtTutorias);
        txtDepartamentos = findViewById(R.id.txtDepartamentos);
    }


    private void irOrganizacion(){
        organizacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(), AcOrganizacion.class);
                startActivity(i);
            }
        });
    }

    private void irTutorias(){
        tutorias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2=new Intent(getApplicationContext(), AcTutorias.class);
                startActivity(i2);
            }
        });
    }

    private void importarFuentes(){
        fuenteContenedores = ResourcesCompat.getFont(this, R.font.ibm_plex_sans_thai_bold);
        fuenteCabeceraProfesorado = ResourcesCompat.getFont(this, R.font.clear_sans_thin);
    }

    private void cargarAnimaciones(){
        atgContenedor1 = AnimationUtils.loadAnimation(this,R.anim.atg_horarios1);
        atgContenedor2 = AnimationUtils.loadAnimation(this,R.anim.atg_horarios2);
        atgContenedor3 = AnimationUtils.loadAnimation(this,R.anim.atg_horarios3);
        atgCabecera = AnimationUtils.loadAnimation(this,R.anim.atg_cabecera);
    }

    private void disponerAnimaciones(){
        organizacion.startAnimation(atgContenedor1);
        tutorias.startAnimation(atgContenedor2);
        departamentos.startAnimation(atgContenedor3);
        txtCabeceraProfesorado.startAnimation(atgCabecera);
    }



}