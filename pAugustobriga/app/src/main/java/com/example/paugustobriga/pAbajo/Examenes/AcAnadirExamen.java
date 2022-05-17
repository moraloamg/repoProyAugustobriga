package com.example.paugustobriga.pAbajo.Examenes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.paugustobriga.R;
import com.example.paugustobriga.pAbajo.Agenda.AdaptadorVerTareaDia;
import com.example.paugustobriga.pAbajo.Agenda.Tarea;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class AcAnadirExamen extends AppCompatActivity {

    Button btnAnadirExamen;
    EditText editCalificacion, editNombreExamen;
    Spinner spTrimestre, spAsignatura;
    Typeface fuenteContenedores;
    AccesoDatosExamenes ad;

    ArrayList<Trimestre> listaTrimestes;
    ArrayList<Asignatura> listaAsignaturas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_examen);

        ad = new AccesoDatosExamenes(getApplicationContext());

        identificarElementos();
        importarFuentes();

        iniciarOpcionesSpinnerAsignatura(ad.obtenerAsignaturas());
        iniciarOpcionesSpinnerTrimestre(ad.obtenerTrimestres());
        anadir();


    }

    private void anadir(){
        btnAnadirExamen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editNombreExamen.getText().toString().length()>0){
                    if(editCalificacion.getText().toString().length()>0){
                        if(spAsignatura.getAdapter().getCount() > 0 && spTrimestre.getAdapter().getCount() > 0){
                            Examen ex=new Examen();
                            ex.setNombre(editNombreExamen.getText().toString());
                            ex.setNota(Float.parseFloat(editCalificacion.getText().toString()));
                            ex.setAsig(new Asignatura(spAsignatura.getSelectedItem().toString(),null));
                            ex.setTri(new Trimestre(spTrimestre.getSelectedItem().toString(), null));
                            if(!ad.insertarExamen(ex)){
                                Toast.makeText(getApplicationContext(), "Ha ocurrido un error al crear el examen", Toast.LENGTH_LONG).show();
                            }else{
                                Intent i=new Intent(getApplicationContext(), AcExamenes.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "debes tener asignaturas o trimestres creados", Toast.LENGTH_LONG).show();
                        }
                        String text = spAsignatura.getSelectedItem().toString();
                    }else{
                        Toast.makeText(getApplicationContext(), "no has añadido ninguna nota", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "no has añadido ningun nombre", Toast.LENGTH_LONG).show();
                }
            }
        });


    }


    private void importarFuentes(){
        fuenteContenedores = ResourcesCompat.getFont(this, R.font.ibm_plex_sans_thai_bold);
    }

    private void identificarElementos(){
        btnAnadirExamen = findViewById(R.id.btnAnadirExamen);
        editCalificacion = findViewById(R.id.editTextCalificacion);
        editNombreExamen = findViewById(R.id.editTextExamen);
        spAsignatura = findViewById(R.id.spAsignatura);
        spTrimestre = findViewById(R.id.spTrimestre);
    }

    private void iniciarOpcionesSpinnerTrimestre(ArrayList<Trimestre> trimestres){
        ArrayList<String> nombreTrimestres = new ArrayList<>();
        for(Trimestre t:trimestres){
            nombreTrimestres.add(t.getNombreTrimestre());
        }
        ArrayAdapter<String> adaptador =
                new ArrayAdapter<String>(getApplicationContext(),
                        R.layout.formato_spinner,nombreTrimestres);
        adaptador.setDropDownViewResource(R.layout.formato_spinner);
        spTrimestre.setAdapter(adaptador);
    }

    private void iniciarOpcionesSpinnerAsignatura(ArrayList<Asignatura> asignaturas){
        ArrayList<String> nombreAsignaturas = new ArrayList<>();
        for(Asignatura a:asignaturas){
            nombreAsignaturas.add(a.getNombre());
        }
        ArrayAdapter<String> adaptador =
                new ArrayAdapter<String>(getApplicationContext(),
                        R.layout.formato_spinner,nombreAsignaturas);
        adaptador.setDropDownViewResource(R.layout.formato_spinner);
        spAsignatura.setAdapter(adaptador);
    }

    /*
    private void elegirOpcionSpinnerTrimestre(){
        spTrimestre.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }

     */

    /*
    private void elegirOpcionSpinnerAsignatura(){
        spAsignatura.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }

     */

    @Override
    public void onBackPressed() {
        Intent i=new Intent(getApplicationContext(), AcExamenes.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}