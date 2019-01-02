package com.example.raidgoplanner;

import android.content.Context;
import android.content.Intent;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "SignUpActivity";
    private FirebaseAuth mAuth;
    public boolean registrado = false;
    public boolean comprobacion = false;
    public int registro;
    public GoogleApiClient mGoogleSignInClient = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Intent i = new Intent(MainActivity.this, PrincipalActivity.class);
            startActivity(i);
        }

        registro = 0;

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.signInGoogle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sign = Auth.GoogleSignInApi.getSignInIntent(mGoogleSignInClient);
                registro = 1;
                startActivityForResult(sign, RC_SIGN_IN);
            }
        });

        findViewById(R.id.signUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sign = Auth.GoogleSignInApi.getSignInIntent(mGoogleSignInClient);
                registro = 2;
                startActivityForResult(sign, RC_SIGN_IN);
            }
        });



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
            }
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        if (registro == 1) {

            final AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                                ValueEventListener value = new ValueEventListener() {

                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        FirebaseUser user = mAuth.getCurrentUser();

                                        boolean existe = false;

                                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                            User usuario = ds.getValue(User.class);
                                            if (usuario.email.equals(user.getEmail())) {
                                                existe = true;
                                                Intent intent = new Intent(MainActivity.this, PrincipalActivity.class);
                                                startActivity(intent);

                                            }
                                        }
                                        if (!existe) {
                                            lanzaToast("No hay ningun usuario con ese email asociado");
                                            Auth.GoogleSignInApi.signOut(mGoogleSignInClient).setResultCallback(new ResultCallback<Status>() {
                                                @Override
                                                public void onResult(@NonNull Status status) {

                                                }

                                            });
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                };
                                mDatabase.child("users").addListenerForSingleValueEvent (value);
//
                            } else {

                            }

                        }
                    });
        }
        else if (registro == 2) {
            final AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                                ValueEventListener value = new ValueEventListener() {

                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        FirebaseUser user = mAuth.getCurrentUser();


                                        boolean existe = false;

                                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                            User usuario = ds.getValue(User.class);
                                            if (user == null){
                                                continue;
                                            }
                                            else if (usuario == null){
                                                continue;
                                            }
                                            else if (usuario.email.equals(user.getEmail())) {
                                                existe = true;
                                                if (!comprobacion) {
                                                    lanzaToast("Ya hay un usuario con ese email asociado");
                                                    Auth.GoogleSignInApi.signOut(mGoogleSignInClient).setResultCallback(new ResultCallback<Status>() {
                                                        @Override
                                                        public void onResult(@NonNull Status status) {

                                                        }
                                                    });
                                                }
                                            }
                                        }
                                        if (!existe) {
                                            Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                                            intent.putExtra("usuario", user);
                                            comprobacion = true;
                                            startActivity(intent);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                };
                                mDatabase.child("users").addListenerForSingleValueEvent (value);
//
                            } else {

                            }

                        }
                    });
        }
    }

    @Override
    public void onBackPressed() {
        this.finishAffinity();
    }

    public void lanzaToast(String mensaje) {
        Context context = getApplicationContext();
        CharSequence text = mensaje;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
