package com.dam.proyecto.jamarcas.usuario;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

/**
 * Created by JaMarCas on 26/05/2015.
 */
public class DataBaseMedicamento extends SQLiteOpenHelper {

    /*
        CREATE TABLE `medicamento_table` (
                                            `_id`	INTEGER PRIMARY KEY AUTOINCREMENT,
                                            `Medicamento`	TEXT,
                                            `Composición`	TEXT,
                                            `Propiedades`	TEXT,
                                            `Indicaciones`	TEXT,
                                            `Posología`	TEXT,
                                            `Contraindicaciones`	TEXT,
                                            `Efectos Secundarios`	TEXT,
                                            `Interacciones`	TEXT,
                                            `Intoxicaciones y Tratamientos`	TEXT,
                                            `Presentación`	TEXT
                                        );
     */

    private final Context myContext;
    private static String DB_PATH;
    private static String DB_NAME = "dbMedicamento";
    private static String Table_Name = "medicamento_table";
    private SQLiteDatabase myDataBase;

    public DataBaseMedicamento(Context contexto)
    {
        super(contexto, DB_NAME, null, 1);
        this.myContext = contexto;
        DB_PATH = "/data/data/" + contexto.getPackageName() + "/databases";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();

        if (dbExist) {
            // Si existe, no haemos nada!
        } else {
            // Llamando a este método se crea la base de datos vacía en la ruta
            // por defecto del sistema de nuestra aplicación por lo que
            // podremos sobreescribirla con nuestra base de datos.
            this.getReadableDatabase();

            try {
                copyDataBase();
            } catch (IOException e) {

                throw new Error("Error copiando database");
            }
        }
    }

    private boolean checkDataBase() {

        SQLiteDatabase checkDB = null;

        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        } catch (SQLiteException e) {
            // Base de datos no creada todavia
        }

        if (checkDB != null) {

            checkDB.close();
        }

        return checkDB != null ? true : false;

    }

    public void openDataBase() throws SQLException {
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public synchronized void close() {
        if (myDataBase != null)
            myDataBase.close();
        super.close();
    }

    private void copyDataBase() throws IOException {

        OutputStream databaseOutputStream = new FileOutputStream(DB_PATH + DB_NAME);
        InputStream databaseInputStream;

        byte[] buffer = new byte[1024];
        int length;

        databaseInputStream = myContext.getAssets().open("dbMedicamento");
        while ((length = databaseInputStream.read(buffer)) > 0) {
            databaseOutputStream.write(buffer);
        }

        databaseInputStream.close();
        databaseOutputStream.flush();
        databaseOutputStream.close();
    }
    public String [] getEntryMedicamento(String nombreMedicamento)
    {
        Cursor cursor = myDataBase.query(Table_Name, null, " Medicamento=?", new String[]{nombreMedicamento}, null, null, null);
        //No existe el usuario
        if(cursor.getCount()<1)
        {
            cursor.close();
            return null;
        }
        cursor.moveToFirst();
        //medicamento (pos 0), composición (pos 1), propiedades (pos 2), indicaciones (pos 3),
        //posología (pos 4), contraindicaciones (pos 5), efectos (pos 6), interacciones (pos 7), intoxicaciones (pos 8),
        //presentación (pos 9)
        String medicamento = cursor.getString(cursor.getColumnIndex("Medicamento"));
        String composicion = cursor.getString(cursor.getColumnIndex("Composicion"));
        String propiedades = cursor.getString(cursor.getColumnIndex("Propiedades"));
        String indicaciones = cursor.getString(cursor.getColumnIndex("Indicaciones"));
        String posologia = cursor.getString(cursor.getColumnIndex("Posologia"));
        String contraindicaciones = cursor.getString(cursor.getColumnIndex("Contraindicaciones"));
        String efectos = cursor.getString(cursor.getColumnIndex("Efectos Secundarios"));
        String interacciones = cursor.getString(cursor.getColumnIndex("Interacciones"));
        String intoxicaciones = cursor.getString(cursor.getColumnIndex("Intoxicaciones y Tratamientos"));
        String presentacion = cursor.getString(cursor.getColumnIndex("Presentacion"));

        String [] resultado = new String[]{medicamento, composicion, propiedades, indicaciones, posologia,
        contraindicaciones, efectos, interacciones, intoxicaciones, presentacion};
        cursor.close();
        return resultado;
    }
}
