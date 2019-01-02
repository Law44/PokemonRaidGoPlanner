package com.example.raidgoplanner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class FriendsFragment extends Fragment {
    private PrincipalActivity application;
    private String clave, yo;
    private ArrayList<String> amigosRecibida = new ArrayList<>();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();



    public void setClave(String key) {
        this.clave = key;
    }

    public void setLista(ArrayList<String> lista ) { this.amigosRecibida = lista; }

    public void setApplication(PrincipalActivity application) {
        this.application = application;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_friends, container, false);


        myRef.child("users").child(clave).child("usuario").addListenerForSingleValueEvent (new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(final com.google.firebase.database.DataSnapshot dataSnapshot) {

                yo = dataSnapshot.getValue(String.class);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.friend_list);

        AdaptadorFirebase adaptadorFirebase = new AdaptadorFirebase(Amigo.class, R.layout.item_friend, AmigoHolder.class, myRef.child("usuarios").child(clave).child("amigos"), application);

        recyclerView.setAdapter(adaptadorFirebase);
        recyclerView.setLayoutManager(new LinearLayoutManager(application));
//        recyclerView.setLayoutManager(new LinearLayoutManager(application));
//
//        RecyclerAdapter RecyclerAdapter = new RecyclerAdapter(amigosRecibida);
//        recyclerView.setAdapter(RecyclerAdapter);



        view.findViewById(R.id.añadir).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddFriendActivity.class);
                intent.putExtra("clave", clave);
                intent.putExtra("amigos", amigosRecibida);
                intent.putExtra("yo", yo);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        myRef.child("users").child(clave).child("amigos").addListenerForSingleValueEvent (new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(final com.google.firebase.database.DataSnapshot dataSnapshot) {
                amigosRecibida.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    String amigo = postSnapshot.getValue(String.class);
                    if (!amigo.equals(" ")) {
                        amigosRecibida.add(amigo);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
