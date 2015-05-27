package com.dam.proyecto.jamarcas.usuario;

import android.app.Activity;
import android.app.NotificationManager;
import android.os.Bundle;

/**
 * Created by JaMarCas on 09/05/2015.
 */

public class AlarmaDetalle extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detallealarma);

        //Configura la notificación con NotificationManager
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //Para poder cancelar la notificación
        notificationManager.cancel(getIntent().getExtras().getInt("Notificacion"));
    }
}
