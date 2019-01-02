package com.example.raidgoplanner;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;



public class AddFriendActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    boolean amigo;
    ArrayList<String> amigos = new ArrayList<>();
    String yo = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);


        amigos = getIntent().getStringArrayListExtra("amigos");
        yo = getIntent().getStringExtra("yo");

        findViewById(R.id.añadir).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView usuario = findViewById(R.id.editText);
                final String nombreUsuario = usuario.getText().toString();

                myRef.child("users").addListenerForSingleValueEvent (new com.google.firebase.database.ValueEventListener() {
                    @Override
                    public void onDataChange(final com.google.firebase.database.DataSnapshot dataSnapshot) {

                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            User usuario = postSnapshot.getValue(User.class);
                            if (usuario == null){
                                continue;
                            }
                            else if ( (nombreUsuario.equals(usuario.email) || nombreUsuario.equals(usuario.usuario)) && !nombreUsuario.equals("")){
                                if (!amigos.contains(usuario.usuario)) {
                                    if (!usuario.usuario.equals(yo)) {
                                        amigos.add(usuario.usuario);
                                        myRef.child("users").child(getIntent().getStringExtra("clave")).child("amigos").setValue(amigos);
                                        amigo = true;
                                        finish();
                                        break;
                                    }
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "Ya tienes a ese usuario agregado!", Toast.LENGTH_LONG).show();
                                    amigo = true;
                                    break;
                                }

                            }
                        }

                        if (!amigo) {
                            Toast.makeText(getApplicationContext(), "No hay ningun usuario con ese nombre o email", Toast.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

    }

}
