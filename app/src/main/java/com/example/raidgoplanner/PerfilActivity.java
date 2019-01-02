package com.example.raidgoplanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class PerfilActivity extends AppCompatActivity {

    String nombre = "";
    String equipo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);


        TextView usuario = findViewById(R.id.usuario);
        usuario.setText(getIntent().getStringExtra("nombre"));
        equipo = getIntent().getStringExtra("equipo");
        ImageView icono = findViewById(R.id.equipo);


        if (equipo.equals("Instinct")){
            icono.setImageResource(R.drawable.iconoinstinct);
        }
        else if (equipo.equals("Mystic")){
            icono.setImageResource(R.drawable.iconomystic);
        }
        else if (equipo.equals("Valor")){
            icono.setImageResource(R.drawable.iconovalor);

        }

    }
}
