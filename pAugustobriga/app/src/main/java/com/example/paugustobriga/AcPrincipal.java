package com.example.paugustobriga;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.paugustobriga.pAbajo.Agenda.AcCalendarioAgenda;
import com.example.paugustobriga.pArriba.Horarios.AcHorarios;
import com.example.paugustobriga.pMedio.Profesorado.AcProfesorado;
import com.example.paugustobriga.pMedio.Proyectos.AcProyectos;
import com.example.paugustobriga.pMedio.calendario.AcCalendario;

import java.util.ArrayList;
import java.util.Arrays;

public class AcPrincipal extends AppCompatActivity {

    //Texto de los contenedores
    TextView arribaUno, arribaDos, arribaTres, medioUno, medioDos, medioTres, abajoUno, abajoDos, abajoTres;
    //Texto de la cabecera
    TextView txtCab1, txtCab2;
    //Imágenes de iconos
    ImageView ico_arriba_uno, ico_arriba_dos, ico_arriba_tres, ico_medio_uno, ico_medio_dos, ico_medio_tres, ico_abajo_uno,
                ico_abajo_dos, ico_abajo_tres;
    //Animaciones
    Animation atg, atg2, atg3, atgCabecera, atgCabecera2;
    //contenedor
    LinearLayout cont1, cont2, cont3;
    //fuentes
    Typeface fuenteCuerpo;
    Typeface fuenteCabecera;
    //lista de texto
    ArrayList<TextView> lFuentesCuerpo;
    ArrayList<TextView> lFuentesCabecera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //se cargan los elementos de la actividad
        idetificarContenedores();
        identificarTexto();
        identificarIconos();
        cargarAnimaciones();
        prepararFuentes();
        importarFuentes();
        disponerFuentes(fuenteCuerpo, fuenteCabecera);
        disponerAnimaciones();

        //Listeners
        accionIcArribaUno();
        accionIcArribaDos();
        accionIcArribaTres();

        accionIcMedioUno();
        accionIcMedioDos();
        accionIcMedioTres();

        accionIcAbajoUno();
        accionIcAbajoDos();
        accionIcAbajoTres();






    }

    private void accionIcArribaUno(){
        ico_arriba_uno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void accionIcArribaDos(){
        ico_arriba_dos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cambiarActividad("arribaDos");
            }
        });
    }

    private void accionIcArribaTres(){
        ico_arriba_tres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cambiarActividad("arribaTres");
            }
        });
    }

    private void accionIcMedioUno(){
       ico_medio_uno.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
                cambiarActividad("medioUno");
           }
       });
    }

    private void accionIcMedioDos(){
        ico_medio_dos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cambiarActividad("medioDos");
            }
        });
    }

    private void accionIcMedioTres(){
        ico_medio_tres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cambiarActividad("medioTres");
            }
        });
    }

    private void accionIcAbajoUno(){
        ico_abajo_uno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cambiarActividad("abajoUno");
            }
        });
    }

    private void accionIcAbajoDos(){
        ico_abajo_dos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cambiarActividad("abajoDos");
            }
        });
    }

    private void accionIcAbajoTres(){
        ico_abajo_tres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void cambiarActividad(String actividad){

        switch (actividad){
            case "arribaUno":

                break;

            case "arribaDos":
                Intent i2=new Intent(this, AcHorarios.class);
                startActivity(i2);
                break;

            case "arribaTres":
                Intent irNovedades=new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://iesaugustobriga.educarex.es/"));
                startActivity(irNovedades);
                break;

            case "medioUno":
                Intent i3=new Intent(this, AcProfesorado.class);
                startActivity(i3);
                break;

            case "medioDos":
                Intent i4=new Intent(this, AcCalendario.class);
                startActivity(i4);
                break;

            case "medioTres":
                Intent i5=new Intent(this, AcProyectos.class);
                startActivity(i5);
                break;

            case "abajoUno":
                Intent irPlano=new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://raw.githubusercontent.com/moraloamg/pruebaAugustobriga/main/plano/plano.png"));
                startActivity(irPlano);
                break;

            case "abajoDos":
                Intent i6=new Intent(this, AcCalendarioAgenda.class);
                startActivity(i6);
                break;

            case "abajoTres":

                break;


        }
    }

    private void identificarIconos(){
        ico_arriba_uno = findViewById(R.id.ico_arriba_uno);
        ico_arriba_dos = findViewById(R.id.ico_arriba_dos);
        ico_arriba_tres = findViewById(R.id.ico_arriba_tres);
        ico_medio_uno = findViewById(R.id.ico_medio_uno);
        ico_medio_dos = findViewById(R.id.ico_medio_dos);
        ico_medio_tres = findViewById(R.id.ico_medio_tres);
        ico_abajo_uno = findViewById(R.id.ico_abajo_uno);
        ico_abajo_dos = findViewById(R.id.ico_abajo_dos);
        ico_abajo_tres = findViewById(R.id.ico_abajo_tres);
    }

    //aquí se hace uso de la lista de TextView para cambiar la fuente de los texto de manera automática
    private void disponerFuentes(Typeface f1, Typeface f2){
        for(TextView x: lFuentesCuerpo){
            x.setTypeface(f1,Typeface.NORMAL);
        }
        for(TextView x: lFuentesCabecera){
            x.setTypeface(f2,Typeface.NORMAL);
        }
    }

    //introducir los TextViews en una lista para posteriores usos, como cambiar la fuente
    private void prepararFuentes(){
        lFuentesCuerpo=new ArrayList<TextView>(Arrays.asList(arribaUno,
                arribaDos,arribaTres,medioUno,medioDos,medioTres,abajoUno,abajoDos,abajoTres));

        lFuentesCabecera=new ArrayList<>(Arrays.asList(txtCab1, txtCab2));
    }

    private void importarFuentes(){
        fuenteCuerpo = ResourcesCompat.getFont(this, R.font.sfppro_display_thin);
        fuenteCabecera = ResourcesCompat.getFont(this, R.font.clear_sans_thin);
    }

    private void cargarAnimaciones(){
        atg = AnimationUtils.loadAnimation(this,R.anim.atg);
        atg2 = AnimationUtils.loadAnimation(this,R.anim.atg2);
        atg3 = AnimationUtils.loadAnimation(this,R.anim.atg3);
        atgCabecera = AnimationUtils.loadAnimation(this,R.anim.atg_cabecera);
        atgCabecera2 = AnimationUtils.loadAnimation(this,R.anim.atg_cabecera2);
    }

    private void disponerAnimaciones(){
        cont1.startAnimation(atg);
        txtCab1.startAnimation(atgCabecera);
        txtCab2.startAnimation(atgCabecera2);
        cont2.startAnimation(atg2);
        cont3.startAnimation(atg3);
    }

    private void idetificarContenedores(){
        cont1 = findViewById(R.id.cont1);
        cont2 = findViewById(R.id.cont2);
        cont3 = findViewById(R.id.cont3);
    }

    private void identificarTexto(){
        //texto de los contenedores
        arribaUno = findViewById(R.id.txtArribaUno);
        arribaDos = findViewById(R.id.txtArribaDos);
        arribaTres = findViewById(R.id.txtArribaTres);
        medioUno = findViewById(R.id.txtMedioUno);
        medioDos = findViewById(R.id.txtMedioDos);
        medioTres = findViewById(R.id.txtMedioTres);
        abajoUno = findViewById(R.id.txtAbajoUno);
        abajoDos = findViewById(R.id.txtAbajoDos);
        abajoTres = findViewById(R.id.txtAbajoTres);

        //texto de la cabecera
        txtCab1 = findViewById(R.id.txtCabecera1);
        txtCab2 = findViewById(R.id.txtCabecera2);
    }
}