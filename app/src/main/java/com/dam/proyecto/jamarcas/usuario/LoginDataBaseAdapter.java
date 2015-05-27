package com.dam.proyecto.jamarcas.usuario;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

/**
 * Created by JaMarCas on 08/05/2015.
 */
public class LoginDataBaseAdapter {
    static final String DATABASE_NAME = "Login.db";
    static final String TABLE_NAME = "TablaLogin";
    static final int DATABASE_VERSION = 2;
    //Declaración SQL para crear la base de datos
    //Nombre, Apellidos, DNI, SIP, Teléfono, Email, Password
    static final String DATABASE_CREATE = "CREATE TABLE "+TABLE_NAME+
            "( " +
            "ID INTEGER PRIMARY KEY AUTOINCREMENT,"+
            "NOMBRE  TEXT,"+
            "APELLIDOS TEXT,"+
            "DNI TEXT,"+
            "SIP INTEGER,"+
            "TELEFONO INTEGER,"+
            "EMAIL TEXT,"+
            "PASSWORD TEXT" +
            "); ";

    public SQLiteDatabase db;
    private final Context context;
    private DataBaseHelper dbHelper;
    //Constructor de la clase
    public  LoginDataBaseAdapter(Context _context)
    {
        context = _context;
        dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    //Abrimos Base de Datos
    public  LoginDataBaseAdapter open() throws SQLException
    {
        db = dbHelper.getWritableDatabase();
        return this;
    }
    //Cerramos Base de Datos
    public void close()
    {
        db.close();
    }

    public  SQLiteDatabase getDatabaseInstance()
    {
        return db;
    }
    //Nombre, Apellidos, DNI, SIP, Teléfono, Email, Password
    public void insertEntry(String nombre, String apellidos, String dni, String sip, String telf,
                            String email,String password)
    {
        ContentValues newValues = new ContentValues();
        //Asigna los valores de cada fila
        newValues.put("NOMBRE", nombre);
        newValues.put("APELLIDOS", apellidos);
        newValues.put("DNI", dni);
        newValues.put("SIP", sip);
        newValues.put("TELEFONO", telf);
        newValues.put("EMAIL", email);
        newValues.put("PASSWORD",password);

        //Añade una nueva fila en tu tabla
        db.insert(TABLE_NAME, null, newValues);
    }
    public int deleteEntry(String dni)
    {
        String where="DNI=?";
        int numberOFEntriesDeleted= db.delete(TABLE_NAME, where, new String[]{dni}) ;
        return numberOFEntriesDeleted;
    }
    public String [] getEntryUser(String dni)
    {
        Cursor cursor=db.query(TABLE_NAME, null, " DNI=?", new String[]{dni}, null, null, null);
        //No existe el usuario
        if(cursor.getCount()<1)
        {
            cursor.close();
            return null;
        }
        cursor.moveToFirst();
        //Nombre, Apellidos, DNI, SIP, Password
        String nombre = cursor.getString(cursor.getColumnIndex("NOMBRE")); //nombre posicion 0
        String apellidos = cursor.getString(cursor.getColumnIndex("APELLIDOS")); //apellidos posicion 1
        String dni1 = cursor.getString(cursor.getColumnIndex("DNI")); //dni posicion 2
        String sip = cursor.getString(cursor.getColumnIndex("SIP")); //sip posicion 3
        String password = cursor.getString(cursor.getColumnIndex("PASSWORD")); //password posicion 4
        String [] resultado = new String[]{nombre, apellidos, dni1, sip, password};
        cursor.close();
        return resultado;
    }
    //Nombre, Apellidos, DNI, SIP, Teléfono, Email, Password
    public void  updateEntry(String nombre, String apellidos, String dni, String sip, String telf,
                             String email,String password)
    {
        //Define el contenido de las actualizaciones de la fila
        ContentValues updatedValues = new ContentValues();
        // Assign values for each row.
        updatedValues.put("NOMBRE", nombre);
        updatedValues.put("APELLIDOS", apellidos);
        updatedValues.put("DNI", dni);
        updatedValues.put("SIP", sip);
        updatedValues.put("TELEFONO", telf);
        updatedValues.put("EMAIL", email);
        updatedValues.put("PASSWORD",password);

        String where="DNI = ?";
        db.update(TABLE_NAME,updatedValues, where, new String[]{dni});
    }
}
