package com.example.paugustobriga.pAbajo.Examenes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.paugustobriga.AcPrincipal;
import com.example.paugustobriga.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class AcExamenes extends AppCompatActivity {

    TabLayout pestanas;
    Typeface fuenteContenedores;
    AccesoDatosExamenes ad;
    ListView lsPestanas;

    ArrayList<Trimestre> lTrimestres = new ArrayList<>();
    ArrayList<Examen> lExamenes = new ArrayList<>();
    ArrayList<Asignatura> lAsignaturas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examenes);

        ad=new AccesoDatosExamenes(getApplicationContext());

        identificarElementos();
        importarFuentes();
        elegirPestana();
        irSeleccion();


    }

    private void irSeleccion(){
        lsPestanas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //averiguar el tipo, si es examen, tarea o trimestre
            }
        });
    }

    private void elegirPestana(){
        pestanas.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        lAsignaturas = ad.obtenerAsignaturas();
                        lsPestanas.setAdapter(new AdaptadorAsignatura(getApplicationContext(),fuenteContenedores,lAsignaturas));
                        break;
                    case 1:
                        lTrimestres = ad.obtenerTrimestres();
                        lsPestanas.setAdapter(new AdaptadorTrimestre(getApplicationContext(),fuenteContenedores,lAsignaturas));
                        break;
                    case 2:
                        lExamenes = ad.obtenerExamenes();
                        lsPestanas.setAdapter(new AdaptadorExamenes(getApplicationContext(),fuenteContenedores,lAsignaturas));
                        break;
                }

            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void identificarElementos(){
        lsPestanas = findViewById(R.id.lstPestanas);
        pestanas = findViewById(R.id.tabLayout);
    }

    private void importarFuentes(){
        fuenteContenedores = ResourcesCompat.getFont(this, R.font.ibm_plex_sans_thai_bold);
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(getApplicationContext(), AcPrincipal.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}