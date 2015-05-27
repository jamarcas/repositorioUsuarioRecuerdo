package com.dam.proyecto.jamarcas.usuario;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLException;


public class Principal_Activity extends Activity {

    Button btnIdentificar, btnRegistrar, btnIdentLogin, btnSalirLogin;
    LoginDataBaseAdapter loginDataBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_);

        // create a instance of SQLite Database
        loginDataBaseAdapter=new LoginDataBaseAdapter(this);
        try {
            loginDataBaseAdapter=loginDataBaseAdapter.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        btnIdentificar=(Button)findViewById(R.id.buttonIdentificarPrincipal);
        btnRegistrar=(Button)findViewById(R.id.buttonRegistrarPrincipal);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //getApplicationContext()
                Intent intentSignUP=new Intent(Principal_Activity.this,SignUPActivity.class);
                startActivity(intentSignUP);
            }
        });
    }

    public void signIn(View V)
    {
        final Dialog dialog = new Dialog(Principal_Activity.this);
        dialog.setContentView(R.layout.login);
        dialog.setTitle("Inicio de Sesión");

        // get the Refferences of views
        final EditText etDNI = (EditText)dialog.findViewById(R.id.editTextDNIToLogin);
        final  EditText etPassword = (EditText)dialog.findViewById(R.id.editTextPasswordToLogin);

        btnIdentLogin = (Button)dialog.findViewById(R.id.buttonIdentificar);
        //Creamos la función que
        btnIdentLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //Obtenemos los datos introducidos en el EditText
                String dni = etDNI.getText().toString();
                String password = etPassword.getText().toString();

                //Obtenemos el password para el DNI introducido
                String storedDates[] = loginDataBaseAdapter.getEntryUser(dni);
                String storedDNI = storedDates[2];
                String storedPassword = storedDates[4];

                //Si las contraseñas son correctas
                if(password.equals(storedPassword) && dni.equals(storedDNI))
                {
                    Toast.makeText(Principal_Activity.this, "Inicio de sesión correcto", Toast.LENGTH_LONG).show();
                    dialog.dismiss();

                    Intent iUsuario ;
                    iUsuario = new Intent(Principal_Activity.this, Usuario.class);
                    iUsuario.putExtra("dniLogin", storedDates[2]);
                    startActivity(iUsuario);
                }
                else
                {
                    Toast.makeText(Principal_Activity.this, "NIF/NIE o Contraseña incorrecta", Toast.LENGTH_LONG).show();
                    etPassword.requestFocus();
                }
            }
        });
        dialog.show(); //Mostramos Diálogo
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close The Database
        loginDataBaseAdapter.close();
    }
}
