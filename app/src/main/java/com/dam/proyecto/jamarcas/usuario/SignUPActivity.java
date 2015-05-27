package com.dam.proyecto.jamarcas.usuario;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLException;

/*
 * Autor: Javier Martín Castro
 * Ciclo Superior: DAM (Desarrollo de Aplicaciones Multiplataforma)
 * Centro: Florida
 * Fecha: 28 de Mayo de 2015
 */

//Activity que muestra el popup para acceder a la aplicación.
public class SignUPActivity extends Activity
{
    //Variables de los controles
    // Nombre, Apellidos, DNI, SIP, Teléfono, Email, Password
    EditText etNombre, etApell, etDNI, etSIP, etTelf, etEmail, etPass, etConfirmPass;
    Button btnCrearRegistro, btnModificar;
    String nombre, apellidos, dni, sip, telf, email, password, confirmPassword;

    LoginDataBaseAdapter loginDataBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        // Instanciar el Adaptador de la base de datos
        loginDataBaseAdapter=new LoginDataBaseAdapter(this);
        try {
            loginDataBaseAdapter=loginDataBaseAdapter.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Instancia los controles de los EditText
        //Nombre, Apellidos, DNI, SIP, Teléfono, Email, Password
        etNombre=(EditText)findViewById(R.id.editTextNombre);
        etApell=(EditText)findViewById(R.id.editTextApellidos);
        etDNI=(EditText)findViewById(R.id.editTextDNI);
        etSIP=(EditText)findViewById(R.id.editTextSIP);
        etTelf=(EditText)findViewById(R.id.editTextTelf);
        etEmail=(EditText)findViewById(R.id.editTextEmail);
        etPass=(EditText)findViewById(R.id.editTextPassword);
        etConfirmPass=(EditText)findViewById(R.id.editTextConfirmPassword);

        btnCrearRegistro=(Button)findViewById(R.id.buttonCrearRegistro);
        btnCrearRegistro.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                btnModificar.setVisibility(View.INVISIBLE);
                nombre = etNombre.getText().toString();
                apellidos = etApell.getText().toString();
                dni = etDNI.getText().toString();
                sip = etSIP.getText().toString();
                telf = etTelf.getText().toString();
                email = etEmail.getText().toString();
                password = etPass.getText().toString();
                confirmPassword = etConfirmPass.getText().toString();

                //Si algún campo está vacío avisar
                if(nombre.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Campo Nombre vacío", Toast.LENGTH_LONG).show();
                    etNombre.requestFocus();
                    return;
                }
                if(apellidos.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Campo Apellidos vacío", Toast.LENGTH_LONG).show();
                    etApell.requestFocus();
                    return;
                }
                if(dni.equals("")) {
                    Toast.makeText(getApplicationContext(), "Campo NIF/NIE vacío", Toast.LENGTH_LONG).show();
                    etDNI.requestFocus();
                    return;
                }
                if(sip.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Campo SIP vacío", Toast.LENGTH_LONG).show();
                    etSIP.requestFocus();
                    return;
                }
                if(telf.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Campo Teléfono vacío", Toast.LENGTH_LONG).show();
                    etTelf.requestFocus();
                    return;
                }
                if(email.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Campo Email vacío", Toast.LENGTH_LONG).show();
                    etEmail.requestFocus();
                    return;
                }
                //Comprueba si las contraseñas coinciden
                if(!password.equals(confirmPassword))
                {
                    Toast.makeText(getApplicationContext(), "La contraseña no coincide", Toast.LENGTH_LONG).show();
                    etPass.requestFocus();
                    return;
                }
                else
                {
                    char letra = comprobar(dni);
                    dni += letra;
                    alertaInsertar(letra, nombre, apellidos, dni, sip);
                }
            }
        });

        btnModificar = (Button)findViewById(R.id.buttonModificar);
        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnCrearRegistro.setVisibility(View.INVISIBLE);
                nombre = etNombre.getText().toString();
                apellidos = etApell.getText().toString();
                dni = etDNI.getText().toString();
                sip = etSIP.getText().toString();
                telf = etTelf.getText().toString();
                email = etEmail.getText().toString();
                password = etPass.getText().toString();
                confirmPassword = etConfirmPass.getText().toString();

                //Si algún campo está vacío avisar
                if(nombre.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Campo Nombre vacío", Toast.LENGTH_LONG).show();
                    etNombre.requestFocus();
                    return;
                }
                if(apellidos.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Campo Apellidos vacío", Toast.LENGTH_LONG).show();
                    etApell.requestFocus();
                    return;
                }
                if(dni.equals("")) {
                    Toast.makeText(getApplicationContext(), "Campo NIF/NIE vacío", Toast.LENGTH_LONG).show();
                    etDNI.requestFocus();
                    return;
                }
                if(sip.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Campo SIP vacío", Toast.LENGTH_LONG).show();
                    etSIP.requestFocus();
                    return;
                }
                if(telf.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Campo Teléfono vacío", Toast.LENGTH_LONG).show();
                    etTelf.requestFocus();
                    return;
                }
                if(email.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Campo Email vacío", Toast.LENGTH_LONG).show();
                    etEmail.requestFocus();
                    return;
                }
                //Comprueba si las contraseñas coinciden
                if(!password.equals(confirmPassword))
                {
                    Toast.makeText(getApplicationContext(), "La contraseña no coincide", Toast.LENGTH_LONG).show();
                    etPass.requestFocus();
                    return;
                }
                else
                {
                    char letra = comprobar(dni);
                    dni += letra;
                    alertaModificar(letra, nombre, apellidos, dni, sip);
                }
            }
        });
    }
    //Añadimos un Dialogo para comprobar si el DNI introducido es correcto
    private void alertaInsertar(char letra, final String nombre, final String apell, final String dni, final String sip) {

        AlertDialog.Builder dialogoAlerta = new AlertDialog.Builder(this);
        dialogoAlerta.setTitle("Información");
        dialogoAlerta.setMessage("¿Su letra correspondiente es la : " + letra + "?");
        dialogoAlerta.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Su letra es correcta", Toast.LENGTH_SHORT).show();
                //Guarda los datos en la Base de Datos
                //Nombre, Apellidos, DNI, SIP, Teléfono, Email, Password
                loginDataBaseAdapter.insertEntry(nombre, apellidos, dni, sip, telf, email, password);
                Toast.makeText(getApplicationContext(), "Insertado un nuevo Registro", Toast.LENGTH_LONG).show();
                //Volvemos al Activity Principar para acceder a nuestro Usuario
                Intent i = new Intent(SignUPActivity.this, Principal_Activity.class);
                startActivity(i);
            }
        });
        dialogoAlerta.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Su letra no es correcta", Toast.LENGTH_SHORT).show();
                etDNI.requestFocus();
            }
        });
        AlertDialog alerta = dialogoAlerta.create();
        alerta.show();
    }

    //Añadimos un Dialogo para comprobar si el DNI introducido es correcto
    private void alertaModificar(char letra, final String nombre, final String apell, final String dni, final String sip) {

        AlertDialog.Builder dialogoAlerta = new AlertDialog.Builder(this);
        dialogoAlerta.setTitle("Información");
        dialogoAlerta.setMessage("¿Su letra correspondiente es la : " + letra + "?");
        dialogoAlerta.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Su letra es correcta", Toast.LENGTH_SHORT).show();
                //Modifica los datos en la Base de Datos
                //Nombre, Apellidos, DNI, SIP, Teléfono, Email, Password
                loginDataBaseAdapter.updateEntry(nombre, apellidos, dni, sip, telf, email, password);
                Toast.makeText(getApplicationContext(), "Registro Modificado correctamente", Toast.LENGTH_LONG).show();
                //Volvemos al Activity Principar para acceder a nuestro Usuario
                Intent i = new Intent(SignUPActivity.this, Principal_Activity.class);
                i.putExtra("dni", dni);
                startActivity(i);
            }
        });
        dialogoAlerta.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Su letra no es correcta", Toast.LENGTH_SHORT).show();
                etDNI.requestFocus();
            }
        });
        AlertDialog alerta = dialogoAlerta.create();
        alerta.show();
    }

    //Función que calcula la letra de nuestro documento
    private char comprobar(String dni) {
        int dniInteger = Integer.parseInt(dni);
        String juegoCaracteres="TRWAGMYFPDXBNJZSQVHLCKET";
        int modulo= dniInteger % 23;
        char letra = juegoCaracteres.charAt(modulo);
        return letra;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginDataBaseAdapter.close();
    }
}
