package com.example.raidgoplanner;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SettingsFragment extends Fragment {

    private MediaPlayer ring;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    String nombre, equipo;
    String clave;

    public void setMedia(MediaPlayer ring) {
        this.ring = ring;
    }

    public void setClave(String key) {
        this.clave = key;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        myRef.child("users").child(clave).addListenerForSingleValueEvent (new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(final com.google.firebase.database.DataSnapshot dataSnapshot) {

                nombre = dataSnapshot.child("usuario").getValue().toString();

                equipo = dataSnapshot.child("equipo").getValue().toString();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        final AudioManager audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        SeekBar volControl = view.findViewById(R.id.sound);
        volControl.setMax(100);
        volControl.setProgress(40);
        ring.setVolume(40, 40);
        volControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar arg0) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar arg0) {
            }

            @Override
            public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
                float volume = (float) (1 - (Math.log(100 - arg1) / Math.log(100)));
                ring.setVolume(volume, volume);
                ring.start();
            }
        });

        view.findViewById(R.id.perfil).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PerfilActivity.class);
                intent.putExtra("nombre", nombre);
                intent.putExtra("equipo", equipo);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.ayuda).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AyudaActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}