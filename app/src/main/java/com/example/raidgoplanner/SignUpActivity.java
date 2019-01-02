package com.example.raidgoplanner;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Objects;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    public GoogleApiClient mGoogleApiClient;

    @Override
    protected void onStart() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {

            }
        });
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {

            }
        });
        FirebaseAuth.getInstance().signOut();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Intent intent = getIntent();

        final FirebaseUser user =(FirebaseUser) intent.getExtras().get("usuario");

        findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    TextView usuario = findViewById(R.id.editText);
                    String nombreUsuario = usuario.getText().toString();

                    String equipo = "";

                    RadioButton mystic = findViewById(R.id.mystic);
                    RadioButton valor = findViewById(R.id.valor);
                    RadioButton instinct = findViewById(R.id.instinct);


                    if (mystic.isChecked()){
                        equipo = "Mystic";
                    }
                    else if (valor.isChecked()){
                        equipo = "Valor";
                    }
                    else if (instinct.isChecked()){
                        equipo = "Instinct";
                    }

                    if (!nombreUsuario.equals("") && !equipo.equals("")) {
                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                        ArrayList<String> lista = new ArrayList<>();
                        lista.add(" ");
                        String key = FirebaseAuth.getInstance().getUid();
                        User newusuario = new User(nombreUsuario, equipo, user.getEmail(), lista, key);
                        mDatabase.child("users").child(key).setValue(newusuario);
                        Intent intent = new Intent(SignUpActivity.this, PrincipalActivity.class);
                        startActivity(intent);
                    }
                    else {
                        YoYo.with(Techniques.Tada)
                                .duration(700)
                                .repeat(0)
                                .playOn(findViewById(R.id.editText));
                        Toast.makeText(SignUpActivity.this, "El nombre de usuario y el equipo no pueden estar vacios!", Toast.LENGTH_LONG).show();
                    }


                }
            });
    }

}



