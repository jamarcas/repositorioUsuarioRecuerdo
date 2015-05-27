package com.dam.proyecto.jamarcas.usuario;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

/*
 * Autor: Javier Martín Castro
 * Ciclo Superior: DAM (Desarrollo de Aplicaciones Multiplataforma)
 * Centro: Florida
 * Fecha: 28 de Mayo de 2015
 */

//Activity que mostrará las notificaciones.
public class MostrarNotificacion extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int notif = getIntent().getExtras().getInt("Notificacion");

        Intent i = new Intent("com.dam.proyecto.jamarcas.MostrarNotificacion");

        i.putExtra("Notificacion", notif);

        PendingIntent iPendiente = PendingIntent.getActivity(this, 0, i, 0);

        NotificationManager notificacionManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        //Imagen Notificacion
        Notification notificacion  = new Notification(R.drawable.pastilla,
                "Tomar Medicación", System.currentTimeMillis());
        //Cancelar notificación
        notificacion.flags = Notification.FLAG_AUTO_CANCEL;

        CharSequence from = "Notificación - Medicación";
        CharSequence mensaje =  "Recuerdo que tiene que tomarse el medicamento";

        notificacion.setLatestEventInfo(this, from, mensaje, iPendiente);

        notificacion.vibrate = new long[]{100,250,100,500};
        notificacionManager.notify(notif, notificacion);

        finish();
    }
}
