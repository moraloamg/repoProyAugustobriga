package com.example.paugustobriga.pAbajo.Examenes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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
    Button btnAnadirGenerico, btnBuscarGenerico;

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
        lAsignaturas = ad.obtenerAsignaturas();
        lsPestanas.setAdapter(new AdaptadorAsignatura(getApplicationContext(),fuenteContenedores,lAsignaturas));
        elegirPestana();
        anadirGenerico();
        opcionesSeleccion();


    }

    private void opcionesSeleccion(){
        lsPestanas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //averiguar el tipo, si es examen, tarea o trimestre
                //añadirAqui el borrar y el editar
            }
        });
    }

    private void anadirGenerico(){
        btnAnadirGenerico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tmp = btnAnadirGenerico.getText().toString();
                switch (btnAnadirGenerico.getText().toString()){
                    case " Añadir Asignaturas ":
                        Intent i=new Intent(getApplicationContext(), AcAnadirAsignatura.class);
                        startActivity(i);
                        break;
                    case " Añadir Trimestres ":
                        Intent i2=new Intent(getApplicationContext(), AcAnadirTrimestre.class);
                        startActivity(i2);
                        break;

                    case " Añadir Exámenes ":
                        Intent i3=new Intent(getApplicationContext(), AcAnadirExamen.class);
                        startActivity(i3);
                        break;
                }
            }
        });

    }

    private void elegirPestana(){
        pestanas.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        btnAnadirGenerico.setText(" Añadir Asignaturas ");
                        lAsignaturas = ad.obtenerAsignaturas();
                        lsPestanas.setAdapter(new AdaptadorAsignatura(getApplicationContext(),fuenteContenedores,lAsignaturas));
                        break;
                    case 1:
                        btnAnadirGenerico.setText(" Añadir Trimestres ");
                        lTrimestres = ad.obtenerTrimestres();
                        lsPestanas.setAdapter(new AdaptadorTrimestre(getApplicationContext(),fuenteContenedores,lTrimestres));
                        break;
                    case 2:
                        btnAnadirGenerico.setText(" Añadir Exámenes ");
                        lExamenes = ad.obtenerExamenes();
                        lsPestanas.setAdapter(new AdaptadorExamenes(getApplicationContext(),fuenteContenedores,lExamenes));
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
        btnAnadirGenerico = findViewById(R.id.btnAnadirGenerico);
        btnBuscarGenerico = findViewById(R.id.btnBuscarGenerico);
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