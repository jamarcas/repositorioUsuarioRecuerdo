package com.dam.proyecto.jamarcas.usuario;

import android.app.Activity;
import android.app.NotificationManager;
import android.os.Bundle;

/*
 * Autor: Javier Martín Castro
 * Ciclo Superior: DAM (Desarrollo de Aplicaciones Multiplataforma)
 * Centro: Florida
 * Fecha: 28 de Mayo de 2015
 */


public class AlarmaDetalle extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detallealarma); //muestra layout

        //Configura la notificación con NotificationManager
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //Para poder cancelar la notificación
        notificationManager.cancel(getIntent().getExtras().getInt("Notificacion"));
    }
}
