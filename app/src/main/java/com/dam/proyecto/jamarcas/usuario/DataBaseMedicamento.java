package com.dam.proyecto.jamarcas.usuario;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

/*
 * Autor: Javier Martín Castro
 * Ciclo Superior: DAM (Desarrollo de Aplicaciones Multiplataforma)
 * Centro: Florida
 * Fecha: 28 de Mayo de 2015
 */

//Clase SQLiteOpenHelper
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
    private static String DB_PATH = "/data/data/com.dam.proyecto.jamarcas.usuario/databases/";;
    private static String DB_NAME = "dbMedicamento";
    private static String Table_Name = "medicamento_table";
    private SQLiteDatabase myDataBase;

    public DataBaseMedicamento(Context contexto) //Constructor de la clase
    {
        super(contexto, DB_NAME, null, 3);
        this.myContext = contexto;
    }
    //Crea la base de datos
    public void createDataBase() throws IOException{

        boolean dbExist = checkDataBase(); //Comprueba si la base Existe

        if(dbExist){

        }else{
            this.getReadableDatabase();

            try {
                //Hacemos copia de la base de datos
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copiando base de datos");
            }
        }
    }

    private boolean checkDataBase(){

        SQLiteDatabase checkDB = null; //Definimos una SQLiteDataBase vacía

        try{
            String myPath = DB_PATH + DB_NAME; //Asigamos donde se localizará nuesta base de datos
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        }catch(SQLiteException e){}

        if(checkDB != null){ //Si no está vacía
            checkDB.close();
        }

        return checkDB != null ? true : false;
    }

    //Clase que realiza la copia de nuesta base de datos.
    private void copyDataBase() throws IOException{
        //Abre un flujo de entrada donde está nuestra base de datos almacenada en assets
        InputStream myInput = myContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        //Crea un flujo de salida creando un nuevo objeto de tipo OutputStream
        OutputStream myOutput = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }
        //Cerramos objetos
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    //Abrimos base de datos
    public void openDataBase() throws SQLException{
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public synchronized void close() {
        if(myDataBase != null)
            myDataBase.close();

        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //No hace nada ya que la base de datos es externa
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //no hace Upgrade
    }

    //Función para guardar en un string todos los datos del medicamento, almacenados en el registro
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
