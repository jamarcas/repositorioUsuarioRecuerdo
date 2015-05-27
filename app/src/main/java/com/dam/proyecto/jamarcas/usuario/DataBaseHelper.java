package com.dam.proyecto.jamarcas.usuario;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by JaMarCas on 08/05/2015.
 */
public class DataBaseHelper extends SQLiteOpenHelper
{
    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    }
    // Crea la base de datos
    @Override
    public void onCreate(SQLiteDatabase _db)
    {
        _db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE);

    }
    // Actualiza la Base de Datos si existe una.
    @Override
    public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion)
    {
        //_db.execSQL("DROP TABLE IF EXISTS " + LoginDataBaseAdapter.TABLE_NAME);
        // Crea nueva base de datos
        onCreate(_db);
    }
}
