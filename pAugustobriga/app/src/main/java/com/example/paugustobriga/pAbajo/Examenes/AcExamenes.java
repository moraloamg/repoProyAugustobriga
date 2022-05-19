package com.example.paugustobriga.pAbajo.Examenes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.work.WorkManager;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.paugustobriga.AcPrincipal;
import com.example.paugustobriga.R;
import com.example.paugustobriga.pAbajo.Agenda.AcVerTareas;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class AcExamenes extends AppCompatActivity {

    TabLayout pestanas;
    Typeface fuenteContenedores;
    AccesoDatosExamenes ad;
    ListView lsPestanas;
    Button btnAnadirGenerico, btnBuscarGenerico;
    EditText busqueda;

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
        buscarDatos();
        genericoExamen();

    }


    private void genericoExamen(){
        lsPestanas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String seleccionado="";
                if(btnAnadirGenerico.getText().toString().equals(" Añadir Asignaturas ")){
                    seleccionado = ((TextView) view.findViewById(R.id.txtAsignatura)).getText().toString();
                    Intent i1=new Intent(getApplicationContext(), AclGenerica.class);
                    i1.putExtra("datos", seleccionado);
                    i1.putExtra("tipo", "Asignatura");
                    startActivity(i1);
                }
                else if(btnAnadirGenerico.getText().toString().equals(" Añadir Trimestres ")){
                    seleccionado = ((TextView) view.findViewById(R.id.txtTrimestre)).getText().toString();
                    Intent i2=new Intent(getApplicationContext(), AclGenerica.class);
                    i2.putExtra("datos", seleccionado);
                    i2.putExtra("tipo", "Trimestre");
                    startActivity(i2);
                }
            }
        });
    }


    private void buscarDatos(){
        btnBuscarGenerico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(busqueda.getText().toString().length()>0){
                    switch (btnAnadirGenerico.getText().toString()){
                        case " Añadir Asignaturas ":
                            ArrayList<Asignatura> resultadoAsignatura = ad.buscarAsignatura(busqueda.getText().toString());
                            lsPestanas.setAdapter(new AdaptadorAsignatura(getApplicationContext(),fuenteContenedores,resultadoAsignatura));
                            break;
                        case " Añadir Trimestres ":
                            ArrayList<Trimestre> resultadoTrimestres = ad.buscarTrimestre(busqueda.getText().toString());
                            lsPestanas.setAdapter(new AdaptadorTrimestre(getApplicationContext(),fuenteContenedores,resultadoTrimestres));
                            break;

                        case " Añadir Exámenes ":
                            ArrayList<Examen> resultadoExamenes = ad.buscarExamenes(busqueda.getText().toString());
                            lsPestanas.setAdapter(new AdaptadorExamenes(getApplicationContext(),fuenteContenedores,resultadoExamenes));
                            break;
                    }
                }
            }
        });

    }

    private void opcionesMoficar(View view){
        String seleccionado;
        switch (btnAnadirGenerico.getText().toString()){
            case " Añadir Asignaturas ":
                seleccionado = ((TextView) view.findViewById(R.id.txtAsignatura)).getText().toString();
                Intent i=new Intent(getApplicationContext(), AcAnadirAsignatura.class);
                i.putExtra("nombre", seleccionado);
                startActivity(i);
                break;
            case " Añadir Trimestres ":
                seleccionado = ((TextView) view.findViewById(R.id.txtTrimestre)).getText().toString();
                Intent i2=new Intent(getApplicationContext(), AcAnadirTrimestre.class);
                i2.putExtra("nombre", seleccionado);
                startActivity(i2);
                break;

            case " Añadir Exámenes ":
                seleccionado = ((TextView) view.findViewById(R.id.txtIdExamen)).getText().toString();
                Intent i3=new Intent(getApplicationContext(), AcAnadirExamen.class);
                Examen exTmp = ad.obtenerExamen(seleccionado);
                String datos = exTmp.getId()+"##"+ exTmp.getNombre()+"##"+exTmp.getNota()+"##"+exTmp.getAsig().getNombre()+"##"+exTmp.getTri().getNombreTrimestre();
                i3.putExtra("examen",datos);
                startActivity(i3);
                break;
        }
    }

    private void opcionesSeleccion(){
        lsPestanas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view1, int i, long l) {
                final Dialog myDialog = new Dialog(AcExamenes.this);
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.setContentView(R.layout.dialog_examenes);
                myDialog.setTitle("Elige una opción"); //?? esto no se porqué no sale
                myDialog.setCancelable(true);

                Button editar = (Button) myDialog.findViewById(R.id.EditarGenerico);
                editar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        opcionesMoficar(view1);
                    }
                });
                Button borrar = (Button) myDialog.findViewById(R.id.borrarGenerico);
                borrar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        opcionBorrar(view1);
                    }
                });
                myDialog.show();
                return true;
            }
        });
    }

    private void opcionBorrar(View view){
        AlertDialog.Builder dialogo1=new AlertDialog.Builder(AcExamenes.this);
        dialogo1.setTitle("Borrar");
        dialogo1.setMessage("¿Está seguro que desea borrar?");
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Borrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String seleccionado;
                switch (btnAnadirGenerico.getText().toString()){
                    case " Añadir Asignaturas ":
                        seleccionado = ((TextView) view.findViewById(R.id.txtAsignatura)).getText().toString();
                        if(!ad.borrarAsignatura(seleccionado)){
                            Toast.makeText(getApplicationContext(),"Debes borrar los exámenes que tienen esta asignatura", Toast.LENGTH_LONG).show();
                        }

                        break;
                    case " Añadir Trimestres ":
                        seleccionado = ((TextView) view.findViewById(R.id.txtTrimestre)).getText().toString();
                        if(!ad.borrarTrimestre(seleccionado)){
                            Toast.makeText(getApplicationContext(),"Debes borrar los examenes que tienes estos trimestres", Toast.LENGTH_LONG).show();
                        }
                        break;

                    case " Añadir Exámenes ":
                        seleccionado = ((TextView) view.findViewById(R.id.txtIdExamen)).getText().toString();
                        ad.borrarExamen(Integer.parseInt(seleccionado));
                        break;
                }
                finish();
                startActivity(getIntent());

            }
        });
        dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int id){
                Toast.makeText(getApplicationContext(),"Cancelar", Toast.LENGTH_LONG).show();
            }
        });
        dialogo1.show();
    }

    private void anadirGenerico(){
        btnAnadirGenerico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        busqueda = findViewById(R.id.editTextBusquedaGenerica);
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