package com.example.paugustobriga.pAbajo.Agenda;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.DialogInterface;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AcVerTareas extends AppCompatActivity {
    AccesoDatosAgenda ad;
    Button btnBuscar;
    EditText editBuscar;
    TextView txtTotal, txtComp, txtPas;
    Spinner spVerTareas;
    ListView lstVerTareas;
    //Estas opciones serán únicas
    final String[] opciones=new String[]{"Todo","Exámenes","Tareas","Otros","Realizadas","No Realizadas","Pasadas"};
    ArrayList<Tarea> datos;
    Typeface fuenteContenedores;
    SimpleDateFormat formato=new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_tareas);
        ad=new AccesoDatosAgenda(getApplicationContext());

        identificarElementos();
        iniciarOpcionesSpinner();
        elegirOpcionSpinner();
        datos=ad.obtenerTareas();
        importarFuentes();
        lstVerTareas.setAdapter(new AdaptadorVerTareaDia(this,fuenteContenedores,datos));
        actualizarContadores(datos);

        buscarDatos();
        irTarea();
        lstVerTareas.setLongClickable(true);
        eliminarTarea();

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
                switch (opciones[i]){
                    case "Todo":
                        lstVerTareas.setAdapter(new AdaptadorVerTareaDia(getApplicationContext(),fuenteContenedores,datos));
                        actualizarContadores(datos);
                        break;
                    case "Exámenes":
                        ArrayList<Tarea> tipoExamenes = ad.buscarTipo("EXAMEN");
                        lstVerTareas.setAdapter(new AdaptadorVerTareaDia(getApplicationContext(),fuenteContenedores,tipoExamenes));
                        actualizarContadores(tipoExamenes);
                        break;
                    case "Tareas":
                        ArrayList<Tarea> tipoTarea = ad.buscarTipo("TAREA");
                        lstVerTareas.setAdapter(new AdaptadorVerTareaDia(getApplicationContext(),fuenteContenedores,tipoTarea));
                        actualizarContadores(tipoTarea);
                        break;
                    case "Otros":
                        ArrayList<Tarea> tipoOtro = ad.buscarTipo("OTRO");
                        lstVerTareas.setAdapter(new AdaptadorVerTareaDia(getApplicationContext(),fuenteContenedores,tipoOtro));
                        actualizarContadores(tipoOtro);
                        break;
                    case "Realizadas":
                        ArrayList<Tarea> realizadas = ad.buscarRealizadas();
                        lstVerTareas.setAdapter(new AdaptadorVerTareaDia(getApplicationContext(),fuenteContenedores,realizadas));
                        actualizarContadores(realizadas);
                        break;
                    case "No Realizadas":
                        ArrayList<Tarea> noRealizadas = ad.buscarNoRealizadas();
                        lstVerTareas.setAdapter(new AdaptadorVerTareaDia(getApplicationContext(),fuenteContenedores,noRealizadas));
                        actualizarContadores(noRealizadas);
                        break;
                    case "Pasadas":
                        ArrayList<Tarea> listaPasadas=new ArrayList<>();
                        for(Tarea t:datos){
                            try {
                                if(t.getFecha().before(formato.parse(formato.format(new Date())))){
                                    listaPasadas.add(t);
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        lstVerTareas.setAdapter(new AdaptadorVerTareaDia(getApplicationContext(),fuenteContenedores,listaPasadas));
                        actualizarContadores(listaPasadas);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void irTarea(){
        lstVerTareas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String seleccionado = ((TextView) view.findViewById(R.id.idTarea)).getText().toString();
                if(!ad.comprobarHecho(seleccionado)){
                    Intent i2=new Intent(getApplicationContext(), AcAnadirTarea.class);
                    i2.putExtra("datos",ad.obtenerFecha(seleccionado)+"P"+seleccionado+"V");
                    startActivity(i2);
                }else{
                    Toast.makeText(getApplicationContext(),"No puedes editar una tarea realizada", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void eliminarTarea(){
        lstVerTareas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder dialogo1=new AlertDialog.Builder(AcVerTareas.this);
                dialogo1.setTitle("Borrar tarea");
                dialogo1.setMessage("¿Está seguro que desea borrar la tarea?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Borrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String seleccionado = ((TextView) view.findViewById(R.id.idTarea)).getText().toString();
                        ad.borrar(Integer.parseInt(seleccionado));
                        Toast.makeText(getApplicationContext(),"Has borrado la tarea", Toast.LENGTH_LONG).show();
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
                return true;
            }
        });
    }



    private void buscarDatos(){
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editBuscar.getText().toString().trim().length() > 0){
                    String busqueda = editBuscar.getText().toString();
                    ArrayList<Tarea> resultado = ad.buscarTarea(busqueda);
                    lstVerTareas.setAdapter(new AdaptadorVerTareaDia(getApplicationContext(),fuenteContenedores,resultado));
                    actualizarContadores(resultado);
                }
            }
        });
    }

    private void actualizarContadores(ArrayList<Tarea> t){
        txtTotal.setText("Total   "+String.valueOf(t.size()));
        int contPasadas=0;
        int contCompletadas=0;
        for(Tarea ta:t){
            if(ta.isRealizado()){
                contCompletadas++;
            }
            try {
                if(formato.parse(formato.format(new Date())).after(ta.getFecha())){
                    contPasadas++;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        txtComp.setText("Completadas   "+String.valueOf(contCompletadas));
        txtPas.setText("Pasadas   "+String.valueOf(contPasadas));
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