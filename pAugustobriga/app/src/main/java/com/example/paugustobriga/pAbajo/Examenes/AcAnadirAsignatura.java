package com.example.paugustobriga.pAbajo.Examenes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.paugustobriga.R;

public class AcAnadirAsignatura extends AppCompatActivity {

    Button btnAnadirAsignatura;
    EditText editEscribirAsignatura;
    Typeface fuenteNegrita, fuenteContenedores;
    TextView txtCabecera, txtMensaje;

    AccesoDatosExamenes ad;

    String nombre="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_asignatura);

        ad = new AccesoDatosExamenes(getApplicationContext());

        identificarElementos();
        importarFuentes();
        disponerFuentes();

        //mejorar esto
        try{
            nombre = recibirDatos();
        }catch (Exception nulException){ //en caso de que no exista la asignatura todavía

        }

        if(nombre.length()>0){
            editEscribirAsignatura.setText(nombre);
            editarAsignatura(nombre);
        }else{
            anadirAsignatura();
        }



    }


    private void editarAsignatura(String nombre) {
        btnAnadirAsignatura.setText("Editar asignatura");
        txtCabecera.setText("Editar asignatura");
        btnAnadirAsignatura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editEscribirAsignatura.getText().toString().length()>0){
                    if(!ad.modificarAsignatura(nombre,new Asignatura(editEscribirAsignatura.getText().toString(),null))){
                        Toast.makeText(getApplicationContext(), "Ha ocurrido un error al editar la asignatura", Toast.LENGTH_LONG).show();
                    }else{
                        Intent i=new Intent(getApplicationContext(), AcExamenes.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }
                }
            }
        });
    }

    private void anadirAsignatura(){
        btnAnadirAsignatura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editEscribirAsignatura.getText().toString().length()>0){
                    if(!ad.insertarAsignatura(new Asignatura(editEscribirAsignatura.getText().toString(),null))){
                        Toast.makeText(getApplicationContext(), "Ha ocurrido un error al crear la asignatura", Toast.LENGTH_LONG).show();
                    }else{
                        Intent i=new Intent(getApplicationContext(), AcExamenes.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }
                }
            }
        });

    }

    private String recibirDatos(){
        String resultado;
        Bundle extras = getIntent().getExtras();
        resultado = extras.getString("nombre");
        return resultado;
    }

    private void disponerFuentes(){
        txtCabecera.setTypeface(fuenteContenedores);
        btnAnadirAsignatura.setTypeface(fuenteContenedores);
        editEscribirAsignatura.setTypeface(fuenteContenedores);
        txtMensaje.setTypeface(fuenteContenedores);
    }

    private void importarFuentes(){
        fuenteNegrita = ResourcesCompat.getFont(this, R.font.ibm_plex_sans_thai_bold);
        fuenteContenedores = ResourcesCompat.getFont(this, R.font.clear_sans_thin);
    }

    private void identificarElementos(){
        btnAnadirAsignatura = findViewById(R.id.btnAnadirAsignatura);
        editEscribirAsignatura = findViewById(R.id.editTextAsignatura);
        txtCabecera = findViewById(R.id.textViewCabeceraAsignatura);
        txtMensaje = findViewById(R.id.textViewMensajeAsignatura);
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(getApplicationContext(), AcExamenes.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}