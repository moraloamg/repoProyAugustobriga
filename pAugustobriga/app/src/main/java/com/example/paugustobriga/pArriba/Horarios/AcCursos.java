package com.example.paugustobriga.pArriba.Horarios;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.paugustobriga.R;

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
        int fondo = seleccionarFondo(contenidoFichero.get(0).toString());
        contenidoFichero.remove(0);
        importarFuentes();
        lViewCursos.setAdapter(new Adaptador(this,contenidoFichero,fondo,fuenteContenedores));
        irHorario();

    }

    private void irHorario(){
        lViewCursos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String horario = lViewCursos.getItemAtPosition(i).toString();
                Intent irFoto=new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://raw.githubusercontent.com/moraloamg/pruebaAugustobriga/main/horarios/"+horario+".PNG"));
                startActivity(irFoto);
            }
        });
    }

    /**
     * Este metodo carga el color del fondo el función de la elección de la actividad anterior
     * @param tipo
     * @return
     */

    private int seleccionarFondo(String tipo) {
        int resultado=0;
        switch (tipo){
            case "Eso":
                resultado = R.drawable.generico_eso;
                break;
            case "Bachillerato":
                resultado = R.drawable.generico_bachillerato;
                break;
            case "Fp":
                resultado = R.drawable.generico_fp;
                break;
        }
        return resultado;
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