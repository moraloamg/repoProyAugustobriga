package com.example.paugustobriga;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.example.paugustobriga.pAbajo.Examenes.AcExamenes;
import com.example.paugustobriga.pAbajo.Examenes.AccesoDatosExamenes;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class AcEstadisticas extends AppCompatActivity {

    PieChart tarta, tarta2, tarta3, tarta4;
    AccesoDatosExamenes ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadisticas);
        ad = new AccesoDatosExamenes(getApplicationContext());

        identificarElementos();
        int suspensos = ad.obtenerSuspensos();
        int aprobados = ad.obtenerAprobados();
        suspensosYAprobadosGenerales(suspensos,aprobados);
        aprobadosPorTrimestre(ad.aprobadosPorTrimestre());
        aprobadosPorAsignatura(ad.aprobadosPorAsignatura());
        suspensosPorAsignatura(ad.suspensosPorAsignatura());


    }

    private void aprobadosPorAsignatura(ArrayList<Object[]> listaAprobados){
        ArrayList<PieEntry> notas = new ArrayList<PieEntry>();
        for(Object[] x:listaAprobados){
            notas.add(new PieEntry((int) x[1], (String) x[0]));
        }

        PieDataSet pieDataSet = new PieDataSet(notas, "Aprobados por Asignatura");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);

        PieData pieData = new PieData(pieDataSet);

        tarta3.setData(pieData);
        tarta3.getDescription().setEnabled(false);
        tarta3.setCenterText("Aprobados por Asignatura");
        tarta3.animate();


    }

    private void suspensosPorAsignatura(ArrayList<Object[]> listaSuspensos){
        ArrayList<PieEntry> notas = new ArrayList<PieEntry>();
        for(Object[] x:listaSuspensos){
            notas.add(new PieEntry((int) x[1], (String) x[0]));
        }

        PieDataSet pieDataSet = new PieDataSet(notas, "Suspensos por asignatura");
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);

        PieData pieData = new PieData(pieDataSet);

        tarta4.setData(pieData);
        tarta4.getDescription().setEnabled(false);
        tarta4.setCenterText("Suspensos por asignatura");
        tarta4.animate();
    }

    private void aprobadosPorTrimestre(ArrayList<Object[]> listaAprobados){
        ArrayList<PieEntry> notas = new ArrayList<PieEntry>();
        for(Object[] x:listaAprobados){
            notas.add(new PieEntry((int) x[1], (String) x[0]));
        }

        PieDataSet pieDataSet = new PieDataSet(notas, "Aprobados por trimestre");
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);

        PieData pieData = new PieData(pieDataSet);

        tarta2.setData(pieData);
        tarta2.getDescription().setEnabled(false);
        tarta2.setCenterText("Aprobados por trimestre");
        tarta2.animate();
    }

    private void suspensosYAprobadosGenerales(int totalSuspensos, int totalAprobados){
        ArrayList<PieEntry> notas = new ArrayList<PieEntry>();
        notas.add(new PieEntry(totalAprobados, "Aprobados"));
        notas.add(new PieEntry(totalSuspensos, "Suspensos"));

        PieDataSet pieDataSet = new PieDataSet(notas, "Notas del curso");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);

        PieData pieData = new PieData(pieDataSet);

        tarta.setData(pieData);
        tarta.getDescription().setEnabled(false);
        tarta.setCenterText("Notas");
        tarta.animate();
    }

    private void identificarElementos(){
        tarta = findViewById(R.id.tarta1);
        tarta2 = findViewById(R.id.tarta2);
        tarta3 = findViewById(R.id.tarta3);
        tarta4 = findViewById(R.id.tarta4);

    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(getApplicationContext(), AcExamenes.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}