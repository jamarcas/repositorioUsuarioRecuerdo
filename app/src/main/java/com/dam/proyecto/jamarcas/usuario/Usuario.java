package com.dam.proyecto.jamarcas.usuario;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.Calendar;

/**
 * Created by JaMarCas on 08/05/2015.
 */

public class Usuario extends Activity {
    //Variables de los controles
    TextView tvNombre, tvDNI, tvSIP;
    EditText etRecordatorio, etTratamiento;
    String nombre, dni, sip;
    Button btnModificarUsuario, btnEliminarUsuario, btnTratamientoUsuario, btnRecordatorioUsuario, btnFinTrat;
    TimePicker time;

    LoginDataBaseAdapter loginDataBaseAdapter;
    DataBaseMedicamento dataBaseMedicamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario);

        // Instanciar el Adaptador de la base de datos
        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        try {
            loginDataBaseAdapter = loginDataBaseAdapter.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Instanciar controles
        tvNombre = (TextView)findViewById(R.id.textViewNombreCompleto);
        tvDNI = (TextView)findViewById(R.id.textViewDNI);
        tvSIP = (TextView)findViewById(R.id.textViewSIP);
        etRecordatorio = (EditText)findViewById(R.id.editTextFrecuencia);
        etTratamiento = (EditText)findViewById(R.id.editTextTratamiento);
        btnModificarUsuario = (Button)findViewById(R.id.buttonModificarUsuario);
        btnEliminarUsuario = (Button)findViewById(R.id.buttonEliminarUsuario);
        btnRecordatorioUsuario = (Button)findViewById(R.id.buttonRecordatorio);
        btnTratamientoUsuario = (Button)findViewById(R.id.buttonTratamiento);
        btnFinTrat = (Button)findViewById(R.id.buttonFin);

        Intent iUsuario = getIntent();
        dni = iUsuario.getStringExtra("dniLogin");
        //Obtenemos las información guardada de la base de datos
        String [] storedDates = loginDataBaseAdapter.getEntryUser(dni);

        nombre = storedDates[0]+ " " + storedDates[1];
        sip = storedDates[3];

        //Asignamos al los TextView la información facilitada
        tvNombre.setText(nombre);
        tvDNI.setText(dni);
        tvSIP.setText(sip);

        Intent i = new Intent("com.dam.proyecto.jamarcas.MostrarNotificacion");
        i.putExtra("Notificacion", 1);

        final PendingIntent iPendiente = PendingIntent.getActivity(getBaseContext(), 0, i,0);

        //Al hacer click en el botón llamamos de nuevo al layout de insertar datos
        btnModificarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iModificar = new Intent(Usuario.this, SignUPActivity.class);
                startActivity(iModificar);
            }
        });

        //Eliminamos el usuario de la aplicación.
        btnEliminarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginDataBaseAdapter.deleteEntry(dni);
                finish();
            }
        });

        //Recordatorio del tratamiento del paciente
        btnRecordatorioUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time = (TimePicker)findViewById(R.id.timePicker);
                String frecuencia =  etRecordatorio.getText().toString();
                /*
                 * Hay que cambiar a horas, multiplicando por 3600
                 */
                long frec = Long.parseLong(frecuencia)*1000; //Transformamos las horas introducidas a segundos

                AlarmManager alarma = (AlarmManager)getSystemService(ALARM_SERVICE);

                Calendar calendar = Calendar.getInstance();
                //HH:MM comienzo notificación
                calendar.set(Calendar.HOUR_OF_DAY, time.getCurrentHour());
                calendar.set(Calendar.MINUTE, time.getCurrentMinute());
                calendar.set(Calendar.SECOND, 0);
                //Repetición de la notificación.
                alarma.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), frec, iPendiente);
                Toast.makeText(Usuario.this, "Cada "+frecuencia+" horas tiene que tomarse el medicamento", Toast.LENGTH_SHORT).show();
            }
        });

        btnFinTrat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
                manager.cancel(iPendiente);
                Toast.makeText(Usuario.this, "Alarma Cancelada", Toast.LENGTH_SHORT).show();
            }
        });

        btnTratamientoUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trat = etTratamiento.getText().toString();
                String storedDates[] = dataBaseMedicamento.getEntryMedicamento(trat);
                String storedTrat = storedDates[0];

                if(storedTrat.equals(trat)){
                    Toast.makeText(Usuario.this, "Tratamiento Encontrado", Toast.LENGTH_SHORT).show();
                    Intent iMedicamento = new Intent(Usuario.this, Medicamento.class);
                    iMedicamento.putExtra("Medicamento", trat);
                    startActivity(iMedicamento);
                }
                else{
                    Toast.makeText(Usuario.this, "No existe el tratamiento introducido",Toast.LENGTH_SHORT).show();
                    etTratamiento.requestFocus();
                }
            }
        });
    }
}
