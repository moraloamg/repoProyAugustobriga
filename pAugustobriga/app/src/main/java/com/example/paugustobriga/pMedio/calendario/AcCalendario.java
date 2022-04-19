package com.example.paugustobriga.pMedio.calendario;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
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

public class AcCalendario extends AppCompatActivity {

    //contenedores
    LinearLayout contEso, contFp, contBachillerato;
    //texto
    TextView txtCabeceraCalendario, txtEso, txtFp, txtBachillerato;
    //fuentes
    Typeface fuenteCabeceraHorarios, fuenteContenedores;
    //listas de texto
    ArrayList<TextView> lFuentesContCalendarios;
    ArrayList<TextView> lFuentesCabeceraCalendarios;
    //Animaciones
    Animation atgCalendario1,atgCalendario2,atgCalendario3, atgCabecera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);

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

    private void accionIrFp(){
        contFp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent irPdf=new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://raw.githubusercontent.com/moraloamg/pruebaAugustobriga/main/calendarios/fp.png"));
                startActivity(irPdf);
            }
        });
    }

    private  void accionIrBachillerato(){
        contBachillerato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent irPdf=new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://raw.githubusercontent.com/moraloamg/pruebaAugustobriga/main/calendarios/Bachillerato.png"));
                startActivity(irPdf);
            }
        });
    }

    private void accionIrEso(){
        contEso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent irPdf=new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://raw.githubusercontent.com/moraloamg/pruebaAugustobriga/main/calendarios/eso.png"));
                startActivity(irPdf);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(getApplicationContext(), AcPrincipal.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }


    //----------------------------------------------------

    private void cargarAnimaciones(){
        atgCalendario1 = AnimationUtils.loadAnimation(this,R.anim.atg_horarios1);
        atgCalendario2 = AnimationUtils.loadAnimation(this,R.anim.atg_horarios2);
        atgCalendario3 = AnimationUtils.loadAnimation(this,R.anim.atg_horarios3);
        atgCabecera = AnimationUtils.loadAnimation(this,R.anim.atg_cabecera);
    }

    private void disponerAnimaciones(){
        contFp.startAnimation(atgCalendario1);
        contBachillerato.startAnimation(atgCalendario2);
        contEso.startAnimation(atgCalendario3);
        txtCabeceraCalendario.startAnimation(atgCabecera);
    }


    private void identificarContenedores(){
        contEso = findViewById(R.id.contEsoCalendario);
        contFp = findViewById(R.id.contFpCalendario);
        contBachillerato = findViewById(R.id.contBachilleratoCalendario);
    }

    private void identificarTexto(){
        txtCabeceraCalendario = findViewById(R.id.txtCabeceraCalendarios);
        txtEso = findViewById(R.id.txtEsoCalendario);
        txtFp = findViewById(R.id.txtFpCalendario);
        txtBachillerato = findViewById(R.id.txtBachilleratoCalendario);
    }

    private void importarFuentes(){
        fuenteContenedores = ResourcesCompat.getFont(this, R.font.ibm_plex_sans_thai_bold);
        fuenteCabeceraHorarios = ResourcesCompat.getFont(this, R.font.clear_sans_thin);
    }

    private void disponerFuentes(Typeface f1, Typeface f2){
        for(TextView x: lFuentesCabeceraCalendarios){
            x.setTypeface(f1,Typeface.NORMAL);
        }
        for(TextView x: lFuentesContCalendarios){
            x.setTypeface(f2,Typeface.NORMAL);
        }
    }

    private void prepararFuentes(){
        lFuentesContCalendarios=new ArrayList<TextView>(Arrays.asList(txtEso,txtFp,txtBachillerato));
        lFuentesCabeceraCalendarios=new ArrayList<>(Arrays.asList(txtCabeceraCalendario));
    }
}