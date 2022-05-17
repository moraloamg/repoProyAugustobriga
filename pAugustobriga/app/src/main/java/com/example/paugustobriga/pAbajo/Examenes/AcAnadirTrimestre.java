package com.example.paugustobriga.pAbajo.Examenes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.paugustobriga.R;

public class AcAnadirTrimestre extends AppCompatActivity {

    Button btnAnadirTrimestre;
    EditText editEscribirTrimestre;
    Typeface fuenteContenedores;
    AccesoDatosExamenes ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_trimestre);

        ad = new AccesoDatosExamenes(getApplicationContext());

        identificarElementos();
        importarFuentes();
        anadirTrimestre();

    }

    private void anadirTrimestre(){
        btnAnadirTrimestre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editEscribirTrimestre.getText().toString().length()>0){
                    if(!ad.insertarTrimestre(new Trimestre(editEscribirTrimestre.getText().toString(),null))){
                        Toast.makeText(getApplicationContext(), "Ha ocurrido un error al crear el trimestre", Toast.LENGTH_LONG).show();
                    }else{
                        Intent i=new Intent(getApplicationContext(), AcExamenes.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }
                }
            }
        });

    }

    private void importarFuentes(){
        fuenteContenedores = ResourcesCompat.getFont(this, R.font.ibm_plex_sans_thai_bold);
    }

    private void identificarElementos(){
        btnAnadirTrimestre = findViewById(R.id.btnAnadirTrimestre);
        editEscribirTrimestre = findViewById(R.id.editTextTrimestre);
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(getApplicationContext(), AcExamenes.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}