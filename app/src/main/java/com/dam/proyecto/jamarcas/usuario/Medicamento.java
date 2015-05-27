package com.dam.proyecto.jamarcas.usuario;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by JaMarCas on 27/05/2015.
 */

public class Medicamento extends Activity {
    TextView tvMedic, tvComp, tvProp, tvIndic, tvPos, tvContra, tvEfec, tvInter, tvIntox, tvPresent;
    Intent iMedicamento;
    String nomMedic, comp, prop, indic, pos, contra, efec, inter, intox, present;
    String [] datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medicamento);

        DataBaseMedicamento db = new DataBaseMedicamento(Medicamento.this);
        try {
            db.createDataBase();
            db.openDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Instanciar controles
        tvMedic = (TextView)findViewById(R.id.tvMostrarMedicamento);
        tvComp = (TextView)findViewById(R.id.tvMostrarComposicion);
        tvProp = (TextView)findViewById(R.id.tvMostrarPropiedades);
        tvIndic = (TextView)findViewById(R.id.tvMostrarIndicaciones);
        tvPos = (TextView)findViewById(R.id.tvMostrarPosologia);
        tvContra = (TextView)findViewById(R.id.tvMostrarContraindicaciones);
        tvEfec = (TextView)findViewById(R.id.tvMostrarEfectos);
        tvInter = (TextView)findViewById(R.id.tvMostrarInteracciones);
        tvIntox = (TextView)findViewById(R.id.tvMostrarIntoxicaciones);
        tvPresent = (TextView)findViewById(R.id.tvMostrarPresentacion);

        iMedicamento = getIntent();
        nomMedic = iMedicamento.getStringExtra("Medicamento");
        datos = db.getEntryMedicamento(nomMedic);

        //medicamento (pos 0), composición (pos 1), propiedades (pos 2), indicaciones (pos 3),
        //posología (pos 4), contraindicaciones (pos 5), efectos (pos 6), interacciones (pos 7), intoxicaciones (pos 8),
        //presentación (pos 9)
        nomMedic = datos[0];
        comp = datos[1];
        prop = datos[2];
        indic = datos[3];
        pos = datos[4];
        contra = datos[5];
        efec = datos[6];
        inter = datos[7];
        intox = datos[8];
        present = datos[9];
        //Asignamos los datos de nuestra tabla.
        tvMedic.setText(nomMedic);
        tvComp.setText(comp);
        tvProp.setText(prop);
        tvIndic.setText(indic);
        tvPos.setText(pos);
        tvContra.setText(contra);
        tvEfec.setText(efec);
        tvInter.setText(inter);
        tvIntox.setText(intox);
        tvPresent.setText(present);
    }
}
