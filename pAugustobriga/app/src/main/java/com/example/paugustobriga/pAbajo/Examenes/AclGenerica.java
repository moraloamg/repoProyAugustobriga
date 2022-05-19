package com.example.paugustobriga.pAbajo.Examenes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.paugustobriga.AcPrincipal;
import com.example.paugustobriga.R;

import java.util.ArrayList;

public class AclGenerica extends AppCompatActivity {
    AccesoDatosExamenes ad;
    ListView lGenerica;
    Typeface fuenteContenedores;

    String tabla, tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lgenerica);
        ad=new AccesoDatosExamenes(getApplicationContext());

        lGenerica=findViewById(R.id.listViewGenerico);
        importarFuentes();
        recibirDatos();
        comprobarTabla(tabla);
        irExamen();
        borrarExamen();

    }



    private void irExamen(){
        lGenerica.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String seleccionado = ((TextView) view.findViewById(R.id.txtIdExamen)).getText().toString();
                Intent i3=new Intent(getApplicationContext(), AcAnadirExamen.class);
                Examen exTmp = ad.obtenerExamen(seleccionado);
                String datos = exTmp.getId()+"##"+ exTmp.getNombre()+"##"+exTmp.getNota()+"##"+exTmp.getAsig().getNombre()+"##"+exTmp.getTri().getNombreTrimestre();
                i3.putExtra("examen",datos);
                startActivity(i3);
            }
        });
    }

    private void borrarExamen(){
        lGenerica.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder dialogo1=new AlertDialog.Builder(AclGenerica.this);
                dialogo1.setTitle("Borrar");
                dialogo1.setMessage("¿Está seguro que desea borrar?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Borrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String seleccionado = ((TextView) view.findViewById(R.id.txtIdExamen)).getText().toString();
                        ad.borrarExamen(Integer.parseInt(seleccionado));

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

    private void comprobarTabla(String tabla){
        if(tipo.equals("Asignatura")){
            ArrayList<Examen> listaExAsig = ad.obtenerExamenAsig(tabla);
            lGenerica.setAdapter(new AdaptadorExamenes(getApplicationContext(),fuenteContenedores,listaExAsig));
        }
        else if(tipo.equals("Trimestre")){
            ArrayList<Examen> listaExTri = ad.obtenerExamenTri(tabla);
            lGenerica.setAdapter(new AdaptadorExamenes(getApplicationContext(),fuenteContenedores,listaExTri));
        }
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(getApplicationContext(), AcExamenes.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    private void recibirDatos(){
        Bundle extras = getIntent().getExtras();
        tabla = extras.getString("datos");
        tipo = extras.getString("tipo");
    }

    private void importarFuentes(){
        fuenteContenedores = ResourcesCompat.getFont(this, R.font.ibm_plex_sans_thai_bold);
    }
}