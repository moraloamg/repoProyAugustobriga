package com.example.paugustobriga.pAbajo.Agenda;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.paugustobriga.AcPrincipal;
import com.example.paugustobriga.R;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class EjecutarNotificacion extends Worker{

    AccesoDatosAgenda ad=new AccesoDatosAgenda(getApplicationContext());

    public EjecutarNotificacion(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    public static void almacenarNotificacion(long duracion, Data data, String tag){
        OneTimeWorkRequest notificacion = new OneTimeWorkRequest.Builder(EjecutarNotificacion.class)
                .setInitialDelay(duracion, TimeUnit.MILLISECONDS).addTag(tag)
                .setInputData(data).build();
        WorkManager instance = WorkManager.getInstance();
        instance.enqueue(notificacion);
    }

    @NonNull
    @Override
    public Result doWork() {
        String titulo = getInputData().getString("titulo");
        String detalle = getInputData().getString("detalle");
        int id = (int) getInputData().getLong("idnoti", 0);
        int idTarea = getInputData().getInt("idnoti",0);

        formatoNotificacion(idTarea, titulo,detalle);

        return Result.success();
    }

    private void formatoNotificacion(int idTarea, String t, String d){
        String id ="message";
        NotificationManager nm = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),id);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel nc = new NotificationChannel(id,"nuevo",NotificationManager.IMPORTANCE_HIGH);
            nc.setDescription("Notification FCM");
            nc.setShowBadge(true);
            nc.setVibrationPattern(new long[] {2000});
            nc.enableVibration(true);
            nc.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            assert nm != null;
            nm.createNotificationChannel(nc);
        }

        Intent intent = new Intent(getApplicationContext(), AcVerTareasDia.class);
        intent.putExtra("datos",ad.obtenerFecha(String.valueOf(idTarea)));

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0,intent,PendingIntent.FLAG_ONE_SHOT);

        builder.setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setContentTitle(t)
                .setTicker("Nueva notificacion")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(d)
                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                .setContentIntent(pendingIntent)
                .setContentInfo("nuevo");

        Random random = new Random();
        int idNotify = random.nextInt(8000);

        assert  nm != null;
        nm.notify(idNotify,builder.build());


    }
}
