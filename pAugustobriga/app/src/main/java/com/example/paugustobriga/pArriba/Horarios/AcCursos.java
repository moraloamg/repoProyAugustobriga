package com.example.paugustobriga.pArriba.Horarios;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.paugustobriga.AcPrincipal;
import com.example.paugustobriga.R;
import com.example.paugustobriga.pArriba.NuestroCentro.AcNuestroCentro;

import java.util.ArrayList;

public class AcCursos extends AppCompatActivity{

    ArrayList<String> contenidoFichero;
    ListView lViewCursos;

    Typeface fuenteContenedores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cursos);

        lViewCursos=findViewById(R.id.listViewCursos);
        contenidoFichero = recibirDatos();
        try {
            contenidoFichero.remove(0);
        }catch (Exception ex){
            AlertDialog.Builder dialogo1=new AlertDialog.Builder(AcCursos.this);
            dialogo1.setTitle("Error");
            dialogo1.setMessage("Error de conexión");
            dialogo1.setCancelable(false);
            dialogo1.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent i1=new Intent(getApplicationContext(), AcPrincipal.class);
                    i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i1);
                }
            });
            dialogo1.show();
        }
        importarFuentes();
        lViewCursos.setAdapter(new Adaptador(this,contenidoFichero,fuenteContenedores));
        irHorario();

    }

    private void irHorario(){
        lViewCursos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String horario = lViewCursos.getItemAtPosition(i).toString();
                Intent irFoto=new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://raw.githubusercontent.com/moraloamg/pruebaAugustobriga/main/horarios/"+horario+".png"));
                startActivity(irFoto);
            }
        });
    }

    private ArrayList<String> recibirDatos(){
        ArrayList<String> resultado;
        Bundle extras = getIntent().getExtras();
        resultado = extras.getStringArrayList("datos");
        return resultado;
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(getApplicationContext(), AcHorarios.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    //usar?

    public ArrayList<String> getContenidoFichero() {
        return contenidoFichero;
    }

    public void setContenidoFichero(ArrayList<String> contenidoFichero) {
        this.contenidoFichero = contenidoFichero;
    }

    private void importarFuentes(){
        fuenteContenedores = ResourcesCompat.getFont(this, R.font.ibm_plex_sans_thai_bold);
    }
}