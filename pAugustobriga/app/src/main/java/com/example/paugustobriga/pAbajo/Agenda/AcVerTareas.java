package com.example.paugustobriga.pAbajo.Agenda;

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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.paugustobriga.AcPrincipal;
import com.example.paugustobriga.R;

import java.util.ArrayList;
import java.util.Date;

public class AcVerTareas extends AppCompatActivity {
    Button btnBuscar;
    EditText editBuscar;
    TextView txtTotal, txtComp, txtPas;
    Spinner spVerTareas;
    ListView lstVerTareas;
    //Estas opciones serán únicas
    final String[] opciones=new String[]{"Todo","Exámenes","Tareas","Otros"};
    ArrayList<TareasDia> datos;
    Typeface fuenteContenedores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_tareas);

        identificarElementos();
        iniciarOpcionesSpinner();
        elegirOpcionSpinner();
        datos=obtenerDatos(recibirDatos());
        importarFuentes();
        lstVerTareas.setAdapter(new AdaptadorVerTareas(this,datos,fuenteContenedores));
        actualizarContadores();

        buscarDatos();



    }

    private void identificarElementos(){
        btnBuscar = findViewById(R.id.btnBuscar);
        editBuscar = findViewById(R.id.editBuscarDesc);
        txtTotal = findViewById(R.id.txtTotalVerTarea);
        txtComp = findViewById(R.id.txtCompletadasVerTarea);
        txtPas = findViewById(R.id.txtPasadasVerTarea);
        spVerTareas = findViewById(R.id.spVerTareas);
        lstVerTareas = findViewById(R.id.lstTareas);
    }

    private void elegirOpcionSpinner(){
        spVerTareas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //PRUEBA DE DEPURACION
                Toast.makeText(getApplicationContext(), opciones[i], Toast.LENGTH_SHORT).show();
                //actualizarBusqueda();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private ArrayList<TareasDia> obtenerDatos(Date fecha){
        ArrayList<TareasDia> resultado=null;
        //POR HACER
        return resultado;
    }

    private void buscarDatos(){
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editBuscar.getText().toString().trim().length() > 0){
                    String resultado = editBuscar.getText().toString();

                    //PRUEBA DE DEPURACION
                    Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_SHORT).show();
                    //actualizarBusqueda();
                }
            }
        });
    }

    private void actualizarContadores(){

    }

    private void actualizarBusqueda(){

    }

    private Date recibirDatos(){
        return null;
    }

    private void iniciarOpcionesSpinner(){
        ArrayAdapter<String> adaptador =
                new ArrayAdapter<String>(getApplicationContext(),
                        R.layout.formato_spinner,opciones);
        adaptador.setDropDownViewResource(R.layout.formato_spinner);
        spVerTareas.setAdapter(adaptador);
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(getApplicationContext(), AcCalendarioAgenda.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    private void importarFuentes(){
        fuenteContenedores = ResourcesCompat.getFont(this, R.font.ibm_plex_sans_thai_bold);
    }
}