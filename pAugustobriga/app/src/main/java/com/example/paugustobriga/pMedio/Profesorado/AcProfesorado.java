package com.example.paugustobriga.pMedio.Profesorado;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.paugustobriga.R;

public class AcProfesorado extends AppCompatActivity {

    LinearLayout organizacion, tutorias, departamentos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profesorado);
        organizacion = findViewById(R.id.contOrganizacion);
        tutorias = findViewById(R.id.contTutorias);

        organizacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(), AcOrganizacion.class);
                startActivity(i);
            }
        });

        tutorias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2=new Intent(getApplicationContext(), AcTutorias.class);
                startActivity(i2);
            }
        });



    }



}