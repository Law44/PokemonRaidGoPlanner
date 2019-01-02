package com.example.raidgoplanner;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class MapaFragment extends Fragment {

    MapView mMapView;
    private GoogleMap gMap;
    String value, value2, value3, equipo;
    MarkerOptions markerCreado;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    int sumaMysticMarker1 = 0;
    int sumaValorMarker1 = 0;
    int sumaInstinctMarker1 = 0;
    boolean clickMarker1 = true;

    int sumaMysticMarker2 = 0;
    int sumaValorMarker2 = 0;
    int sumaInstinctMarker2 = 0;
    boolean clickMarker2 = true;

    int sumaMysticMarker3 = 0;
    int sumaValorMarker3 = 0;
    int sumaInstinctMarker3 = 0;
    boolean clickMarker3 = true;

    int sumaMysticMarker4 = 0;
    int sumaValorMarker4 = 0;
    int sumaInstinctMarker4 = 0;
    boolean clickMarker4 = true;


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mapa, container, false);

        myRef.child("users").child(FirebaseAuth.getInstance().getUid()).child("usuario").addListenerForSingleValueEvent (new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(final com.google.firebase.database.DataSnapshot dataSnapshot) {

                value3 = dataSnapshot.getValue(String.class);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        myRef.child("users").child(FirebaseAuth.getInstance().getUid()).child("equipo").addListenerForSingleValueEvent (new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(final com.google.firebase.database.DataSnapshot dataSnapshot) {

                equipo = dataSnapshot.getValue(String.class);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mMapView = view.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                gMap = googleMap;

                if (ActivityCompat.checkSelfPermission(MapaFragment.this.getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapaFragment.this.getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MapaFragment.this.getActivity(),new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                }else{
                    if(!gMap.isMyLocationEnabled())
                        gMap.setMyLocationEnabled(true);
                    LocationManager lm = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
                    Location myLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                    if (myLocation == null) {
                        Criteria criteria = new Criteria();
                        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
                        String provider = lm.getBestProvider(criteria, true);
                        myLocation = lm.getLastKnownLocation(provider);
                    }

                    if(myLocation!=null){
                        LatLng userLocation = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
                        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 14), 1500, null);
                    }
                }

                final Marker marker1 = gMap.addMarker(new MarkerOptions()
                        .position( new LatLng(41.456079114539, 2.200275734066963))
                        .title("Pokemon de Raid: Moltres")
                        .snippet("Hora: 11.00-11.15"));

                final String idMarker1 = marker1.getId();

                final Marker marker2 = gMap.addMarker(new MarkerOptions()
                        .position( new LatLng(41.455005398177015,2.201831750571728))
                        .title("Pokemon de Raid: Machamp")
                        .snippet("Hora: 13.15"));

                final String idMarker2 = marker2.getId();

                final Marker marker3 = gMap.addMarker(new MarkerOptions()
                        .position( new LatLng(41.457039488886814,2.202354110777378))
                        .title("Pokemon de Raid: Giratina")
                        .snippet("Hora: 16:30-16:50"));

                final String idMarker3 = marker3.getId();

                final Marker marker4 = gMap.addMarker(new MarkerOptions()
                        .position( new LatLng(41.453700229665074,2.1985004469752307))
                        .title("Pokemon de Raid: Charmander")
                        .snippet("Hora: 19:45-20.00"));

                final String idMarker4 = marker4.getId();

                gMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                    @Override
                    public View getInfoWindow(Marker marker) {
                        return null;
                    }


                    @Override
                    public View getInfoContents(final Marker marker) {

                        if (marker.getId().equals(idMarker1)) {

                            LinearLayout info = new LinearLayout(getContext());
                            info.setOrientation(LinearLayout.VERTICAL);

                            TextView title = new TextView(getContext());
                            title.setTextColor(Color.BLACK);
                            title.setGravity(Gravity.CENTER);
                            title.setTypeface(null, Typeface.BOLD);
                            title.setText(marker.getTitle());

                            TextView snippet = new TextView(getContext());
                            snippet.setTextColor(Color.GRAY);
                            snippet.setText(marker.getSnippet());

                            LinearLayout layout = new LinearLayout(getContext());
                            layout.setOrientation(LinearLayout.HORIZONTAL);

                            TextView snippet2 = new TextView(getContext());
                            snippet2.setTextColor(Color.GRAY);
                            snippet2.setText("Creador: Cerkine");
                            layout.addView(snippet2);

                            ImageView icono = new ImageView(getContext());
                            icono.setImageResource(R.drawable.iconovalor);



                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(55, 55);
                            icono.setLayoutParams(layoutParams);
                            layout.addView(icono);


                            LinearLayout layoutEquipo1 = new LinearLayout(getContext());
                            layoutEquipo1.setOrientation(LinearLayout.HORIZONTAL);
                            ImageView icono2 = new ImageView(getContext());



                            icono2.setImageResource(R.drawable.iconomystic);


                            LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(55, 55);
                            icono2.setLayoutParams(layoutParams2);
                            layoutEquipo1.addView(icono2);

                            ProgressBar pb = new ProgressBar(getContext(),
                                    null,
                                    android.R.attr.progressBarStyleHorizontal);

                            pb.setMax(10);
                            pb.setProgress(8 + sumaMysticMarker1);

                            layoutEquipo1.addView(pb);

                            TextView numero = new TextView(getContext());
                            numero.setText(String.valueOf(pb.getProgress()));
                            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            lp.setMargins(10, 0, 0, 0);
                            numero.setLayoutParams(lp);
                            layoutEquipo1.addView(numero);

                            LinearLayout layoutEquipo2 = new LinearLayout(getContext());
                            layoutEquipo2.setOrientation(LinearLayout.HORIZONTAL);
                            ImageView icono3 = new ImageView(getContext());

                            icono3.setImageResource(R.drawable.iconoinstinct);

                            LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(55, 55);
                            icono3.setLayoutParams(layoutParams3);
                            layoutEquipo2.addView(icono3);

                            ProgressBar pb2 = new ProgressBar(getContext(),
                                    null,
                                    android.R.attr.progressBarStyleHorizontal);

                            pb2.setMax(10);
                            pb2.setProgress(6 + sumaInstinctMarker1);

                            layoutEquipo2.addView(pb2);

                            TextView numero2 = new TextView(getContext());
                            numero2.setText(String.valueOf(pb2.getProgress()));
                            LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            lp2.setMargins(10, 0, 0, 0);
                            numero2.setLayoutParams(lp2);
                            layoutEquipo2.addView(numero2);

                            LinearLayout layoutEquipo3 = new LinearLayout(getContext());
                            layoutEquipo3.setOrientation(LinearLayout.HORIZONTAL);
                            ImageView icono4 = new ImageView(getContext());

                            icono4.setImageResource(R.drawable.iconovalor);


                            LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(55, 55);
                            icono4.setLayoutParams(layoutParams4);
                            layoutEquipo3.addView(icono4);

                            ProgressBar pb3 = new ProgressBar(getContext(),
                                    null,
                                    android.R.attr.progressBarStyleHorizontal);

                            pb3.setMax(10);
                            pb3.setProgress(3 + sumaValorMarker1);

                            layoutEquipo3.addView(pb3);

                            TextView numero3 = new TextView(getContext());
                            numero3.setText(String.valueOf(pb3.getProgress()));
                            LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            lp3.setMargins(10, 0, 0, 0);
                            numero3.setLayoutParams(lp3);
                            layoutEquipo3.addView(numero3);

                            Button boton = new Button(getContext());
                            if (clickMarker1) {
                                boton.setText("Unirse");
                            }
                            else {
                                boton.setText("Desapuntarse");
                            }

                            info.addView(title);
                            info.addView(snippet);
                            info.addView(layout);
                            info.addView(layoutEquipo1);
                            info.addView(layoutEquipo2);
                            info.addView(layoutEquipo3);
                            info.addView(boton);

                            return info;
                        }

                        else if (marker.getId().equals(idMarker2)) {

                            LinearLayout info = new LinearLayout(getContext());
                            info.setOrientation(LinearLayout.VERTICAL);

                            TextView title = new TextView(getContext());
                            title.setTextColor(Color.BLACK);
                            title.setGravity(Gravity.CENTER);
                            title.setTypeface(null, Typeface.BOLD);
                            title.setText(marker.getTitle());

                            TextView snippet = new TextView(getContext());
                            snippet.setTextColor(Color.GRAY);
                            snippet.setText(marker.getSnippet());

                            LinearLayout layout = new LinearLayout(getContext());
                            layout.setOrientation(LinearLayout.HORIZONTAL);

                            TextView snippet2 = new TextView(getContext());
                            snippet2.setTextColor(Color.GRAY);
                            snippet2.setText("Creador: DobleDe");
                            layout.addView(snippet2);

                            ImageView icono = new ImageView(getContext());
                            icono.setImageResource(R.drawable.iconoinstinct);



                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(55, 55);
                            icono.setLayoutParams(layoutParams);
                            layout.addView(icono);


                            LinearLayout layoutEquipo1 = new LinearLayout(getContext());
                            layoutEquipo1.setOrientation(LinearLayout.HORIZONTAL);
                            ImageView icono2 = new ImageView(getContext());



                            icono2.setImageResource(R.drawable.iconovalor);


                            LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(55, 55);
                            icono2.setLayoutParams(layoutParams2);
                            layoutEquipo1.addView(icono2);

                            ProgressBar pb = new ProgressBar(getContext(),
                                    null,
                                    android.R.attr.progressBarStyleHorizontal);

                            pb.setMax(10);
                            pb.setProgress(9 + sumaValorMarker2);

                            layoutEquipo1.addView(pb);

                            TextView numero = new TextView(getContext());
                            numero.setText(String.valueOf(pb.getProgress()));
                            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            lp.setMargins(10, 0, 0, 0);
                            numero.setLayoutParams(lp);
                            layoutEquipo1.addView(numero);

                            LinearLayout layoutEquipo2 = new LinearLayout(getContext());
                            layoutEquipo2.setOrientation(LinearLayout.HORIZONTAL);
                            ImageView icono3 = new ImageView(getContext());

                            icono3.setImageResource(R.drawable.iconoinstinct);

                            LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(55, 55);
                            icono3.setLayoutParams(layoutParams3);
                            layoutEquipo2.addView(icono3);

                            ProgressBar pb2 = new ProgressBar(getContext(),
                                    null,
                                    android.R.attr.progressBarStyleHorizontal);

                            pb2.setMax(10);
                            pb2.setProgress(7 + sumaInstinctMarker2);

                            layoutEquipo2.addView(pb2);

                            TextView numero2 = new TextView(getContext());
                            numero2.setText(String.valueOf(pb2.getProgress()));
                            LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            lp2.setMargins(10, 0, 0, 0);
                            numero2.setLayoutParams(lp2);
                            layoutEquipo2.addView(numero2);

                            LinearLayout layoutEquipo3 = new LinearLayout(getContext());
                            layoutEquipo3.setOrientation(LinearLayout.HORIZONTAL);
                            ImageView icono4 = new ImageView(getContext());

                            icono4.setImageResource(R.drawable.iconomystic);


                            LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(55, 55);
                            icono4.setLayoutParams(layoutParams4);
                            layoutEquipo3.addView(icono4);

                            ProgressBar pb3 = new ProgressBar(getContext(),
                                    null,
                                    android.R.attr.progressBarStyleHorizontal);

                            pb3.setMax(10);
                            pb3.setProgress(5 + sumaMysticMarker2);

                            layoutEquipo3.addView(pb3);

                            TextView numero3 = new TextView(getContext());
                            numero3.setText(String.valueOf(pb3.getProgress()));
                            LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            lp3.setMargins(10, 0, 0, 0);
                            numero3.setLayoutParams(lp3);
                            layoutEquipo3.addView(numero3);

                            Button boton = new Button(getContext());
                            if (clickMarker2) {
                                boton.setText("Unirse");
                            }
                            else {
                                boton.setText("Desapuntarse");
                            }

                            info.addView(title);
                            info.addView(snippet);
                            info.addView(layout);
                            info.addView(layoutEquipo1);
                            info.addView(layoutEquipo2);
                            info.addView(layoutEquipo3);
                            info.addView(boton);

                            return info;
                        } else if (marker.getId().equals(idMarker3)) {

                            LinearLayout info = new LinearLayout(getContext());
                            info.setOrientation(LinearLayout.VERTICAL);

                            TextView title = new TextView(getContext());
                            title.setTextColor(Color.BLACK);
                            title.setGravity(Gravity.CENTER);
                            title.setTypeface(null, Typeface.BOLD);
                            title.setText(marker.getTitle());

                            TextView snippet = new TextView(getContext());
                            snippet.setTextColor(Color.GRAY);
                            snippet.setText(marker.getSnippet());

                            LinearLayout layout = new LinearLayout(getContext());
                            layout.setOrientation(LinearLayout.HORIZONTAL);

                            TextView snippet2 = new TextView(getContext());
                            snippet2.setTextColor(Color.GRAY);
                            snippet2.setText("Creador: Vairo");
                            layout.addView(snippet2);

                            ImageView icono = new ImageView(getContext());
                            icono.setImageResource(R.drawable.iconomystic);



                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(55, 55);
                            icono.setLayoutParams(layoutParams);
                            layout.addView(icono);


                            LinearLayout layoutEquipo1 = new LinearLayout(getContext());
                            layoutEquipo1.setOrientation(LinearLayout.HORIZONTAL);
                            ImageView icono2 = new ImageView(getContext());



                            icono2.setImageResource(R.drawable.iconoinstinct);


                            LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(55, 55);
                            icono2.setLayoutParams(layoutParams2);
                            layoutEquipo1.addView(icono2);

                            ProgressBar pb = new ProgressBar(getContext(),
                                    null,
                                    android.R.attr.progressBarStyleHorizontal);

                            pb.setMax(10);
                            pb.setProgress(7 + sumaInstinctMarker3);

                            layoutEquipo1.addView(pb);

                            TextView numero = new TextView(getContext());
                            numero.setText(String.valueOf(pb.getProgress()));
                            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            lp.setMargins(10, 0, 0, 0);
                            numero.setLayoutParams(lp);
                            layoutEquipo1.addView(numero);

                            LinearLayout layoutEquipo2 = new LinearLayout(getContext());
                            layoutEquipo2.setOrientation(LinearLayout.HORIZONTAL);
                            ImageView icono3 = new ImageView(getContext());

                            icono3.setImageResource(R.drawable.iconomystic);

                            LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(55, 55);
                            icono3.setLayoutParams(layoutParams3);
                            layoutEquipo2.addView(icono3);

                            ProgressBar pb2 = new ProgressBar(getContext(),
                                    null,
                                    android.R.attr.progressBarStyleHorizontal);

                            pb2.setMax(10);
                            pb2.setProgress(5 + sumaMysticMarker3);

                            layoutEquipo2.addView(pb2);

                            TextView numero2 = new TextView(getContext());
                            numero2.setText(String.valueOf(pb2.getProgress()));
                            LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            lp2.setMargins(10, 0, 0, 0);
                            numero2.setLayoutParams(lp2);
                            layoutEquipo2.addView(numero2);

                            LinearLayout layoutEquipo3 = new LinearLayout(getContext());
                            layoutEquipo3.setOrientation(LinearLayout.HORIZONTAL);
                            ImageView icono4 = new ImageView(getContext());

                            icono4.setImageResource(R.drawable.iconovalor);


                            LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(55, 55);
                            icono4.setLayoutParams(layoutParams4);
                            layoutEquipo3.addView(icono4);

                            ProgressBar pb3 = new ProgressBar(getContext(),
                                    null,
                                    android.R.attr.progressBarStyleHorizontal);

                            pb3.setMax(10);
                            pb3.setProgress(2 + sumaValorMarker3);

                            layoutEquipo3.addView(pb3);

                            TextView numero3 = new TextView(getContext());
                            numero3.setText(String.valueOf(pb3.getProgress()));
                            LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            lp3.setMargins(10, 0, 0, 0);
                            numero3.setLayoutParams(lp3);
                            layoutEquipo3.addView(numero3);

                            Button boton = new Button(getContext());
                            if (clickMarker3) {
                                boton.setText("Unirse");
                            }
                            else {
                                boton.setText("Desapuntarse");
                            }


                            info.addView(title);
                            info.addView(snippet);
                            info.addView(layout);
                            info.addView(layoutEquipo1);
                            info.addView(layoutEquipo2);
                            info.addView(layoutEquipo3);
                            info.addView(boton);

                            return info;
                        }
                        else if (marker.getId().equals(idMarker4)) {

                            LinearLayout info = new LinearLayout(getContext());
                            info.setOrientation(LinearLayout.VERTICAL);

                            TextView title = new TextView(getContext());
                            title.setTextColor(Color.BLACK);
                            title.setGravity(Gravity.CENTER);
                            title.setTypeface(null, Typeface.BOLD);
                            title.setText(marker.getTitle());

                            TextView snippet = new TextView(getContext());
                            snippet.setTextColor(Color.GRAY);
                            snippet.setText(marker.getSnippet());

                            LinearLayout layout = new LinearLayout(getContext());
                            layout.setOrientation(LinearLayout.HORIZONTAL);

                            TextView snippet2 = new TextView(getContext());
                            snippet2.setTextColor(Color.GRAY);
                            snippet2.setText("Creador: Duom");
                            layout.addView(snippet2);

                            ImageView icono = new ImageView(getContext());
                            icono.setImageResource(R.drawable.iconoinstinct);



                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(55, 55);
                            icono.setLayoutParams(layoutParams);
                            layout.addView(icono);


                            LinearLayout layoutEquipo1 = new LinearLayout(getContext());
                            layoutEquipo1.setOrientation(LinearLayout.HORIZONTAL);
                            ImageView icono2 = new ImageView(getContext());



                            icono2.setImageResource(R.drawable.iconomystic);


                            LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(55, 55);
                            icono2.setLayoutParams(layoutParams2);
                            layoutEquipo1.addView(icono2);

                            ProgressBar pb = new ProgressBar(getContext(),
                                    null,
                                    android.R.attr.progressBarStyleHorizontal);

                            pb.setMax(10);
                            pb.setProgress(9 + sumaMysticMarker4);

                            layoutEquipo1.addView(pb);

                            TextView numero = new TextView(getContext());
                            numero.setText(String.valueOf(pb.getProgress()));
                            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            lp.setMargins(10, 0, 0, 0);
                            numero.setLayoutParams(lp);
                            layoutEquipo1.addView(numero);

                            LinearLayout layoutEquipo2 = new LinearLayout(getContext());
                            layoutEquipo2.setOrientation(LinearLayout.HORIZONTAL);
                            ImageView icono3 = new ImageView(getContext());

                            icono3.setImageResource(R.drawable.iconovalor);

                            LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(55, 55);
                            icono3.setLayoutParams(layoutParams3);
                            layoutEquipo2.addView(icono3);

                            ProgressBar pb2 = new ProgressBar(getContext(),
                                    null,
                                    android.R.attr.progressBarStyleHorizontal);

                            pb2.setMax(10);
                            pb2.setProgress(8 + sumaValorMarker4);

                            layoutEquipo2.addView(pb2);

                            TextView numero2 = new TextView(getContext());
                            numero2.setText(String.valueOf(pb2.getProgress()));
                            LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            lp2.setMargins(10, 0, 0, 0);
                            numero2.setLayoutParams(lp2);
                            layoutEquipo2.addView(numero2);

                            LinearLayout layoutEquipo3 = new LinearLayout(getContext());
                            layoutEquipo3.setOrientation(LinearLayout.HORIZONTAL);
                            ImageView icono4 = new ImageView(getContext());

                            icono4.setImageResource(R.drawable.iconoinstinct);


                            LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(55, 55);
                            icono4.setLayoutParams(layoutParams4);
                            layoutEquipo3.addView(icono4);

                            ProgressBar pb3 = new ProgressBar(getContext(),
                                    null,
                                    android.R.attr.progressBarStyleHorizontal);

                            pb3.setMax(10);
                            pb3.setProgress(1 + sumaInstinctMarker4);

                            layoutEquipo3.addView(pb3);

                            TextView numero3 = new TextView(getContext());
                            numero3.setText(String.valueOf(pb3.getProgress()));
                            LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            lp3.setMargins(10, 0, 0, 0);
                            numero3.setLayoutParams(lp3);
                            layoutEquipo3.addView(numero3);

                            Button boton = new Button(getContext());
                            if (clickMarker4) {
                                boton.setText("Unirse");
                            }
                            else {
                                boton.setText("Desapuntarse");
                            }

                            info.addView(title);
                            info.addView(snippet);
                            info.addView(layout);
                            info.addView(layoutEquipo1);
                            info.addView(layoutEquipo2);
                            info.addView(layoutEquipo3);
                            info.addView(boton);

                            return info;
                        }
                        else {
                            return null;
                        }
                    }

                });


                gMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onMapLongClick(final LatLng latLng) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(getContext(), R.style.añadirRaid);
                        LinearLayout layout = new LinearLayout(getContext());
                        layout.setOrientation(LinearLayout.VERTICAL);
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                        lp.setMargins(50, 20, 50, 10);
                        alert.setTitle("Añadir Raid");

                        final TextView pokemon = new TextView(getContext());
                        pokemon.setText("Pokemon de Raid:");
                        pokemon.setLayoutParams(lp);
                        pokemon.setGravity(android.view.Gravity.TOP|android.view.Gravity.LEFT);
                        layout.addView(pokemon);

                        final EditText input = new EditText(getContext());
                        input.setInputType(InputType.TYPE_CLASS_TEXT);
                        input.setLayoutParams(lp);
                        input.setGravity(android.view.Gravity.TOP|android.view.Gravity.LEFT);
                        layout.addView(input);

                        final TextView hora = new TextView(getContext());
                        hora.setText("Hora:");
                        hora.setLayoutParams(lp);
                        hora.setGravity(android.view.Gravity.TOP|android.view.Gravity.LEFT);
                        layout.addView(hora);

                        final EditText input2 = new EditText(getContext());
                        input2.setInputType(InputType.TYPE_CLASS_TEXT);
                        input2.setLayoutParams(lp);
                        input2.setGravity(android.view.Gravity.TOP|android.view.Gravity.LEFT);
                        layout.addView(input2);



                        alert.setView(layout);

                        alert.setPositiveButton("Añadir", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                value = input.getText().toString();
                                value2 = input2.getText().toString();
                                if (!value.equals("") && !value2.equals("")) {
                                    gMap.addMarker(markerCreado = new MarkerOptions()
                                            .position(latLng)
                                            .title("Pokemon de Raid: " + value)
                                            .snippet("Hora: " + value2));
                                    gMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                                        @Override
                                        public View getInfoWindow(Marker marker) {
                                            return null;
                                        }


                                        @Override
                                        public View getInfoContents(final Marker marker) {

                                            if (marker.getId().equals(idMarker1)) {

                                                LinearLayout info = new LinearLayout(getContext());
                                                info.setOrientation(LinearLayout.VERTICAL);

                                                TextView title = new TextView(getContext());
                                                title.setTextColor(Color.BLACK);
                                                title.setGravity(Gravity.CENTER);
                                                title.setTypeface(null, Typeface.BOLD);
                                                title.setText(marker.getTitle());

                                                TextView snippet = new TextView(getContext());
                                                snippet.setTextColor(Color.GRAY);
                                                snippet.setText(marker.getSnippet());

                                                LinearLayout layout = new LinearLayout(getContext());
                                                layout.setOrientation(LinearLayout.HORIZONTAL);

                                                TextView snippet2 = new TextView(getContext());
                                                snippet2.setTextColor(Color.GRAY);
                                                snippet2.setText("Creador: Cerkine");
                                                layout.addView(snippet2);

                                                ImageView icono = new ImageView(getContext());
                                                icono.setImageResource(R.drawable.iconovalor);



                                                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(55, 55);
                                                icono.setLayoutParams(layoutParams);
                                                layout.addView(icono);


                                                LinearLayout layoutEquipo1 = new LinearLayout(getContext());
                                                layoutEquipo1.setOrientation(LinearLayout.HORIZONTAL);
                                                ImageView icono2 = new ImageView(getContext());



                                                icono2.setImageResource(R.drawable.iconomystic);


                                                LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(55, 55);
                                                icono2.setLayoutParams(layoutParams2);
                                                layoutEquipo1.addView(icono2);

                                                ProgressBar pb = new ProgressBar(getContext(),
                                                        null,
                                                        android.R.attr.progressBarStyleHorizontal);

                                                pb.setMax(10);
                                                pb.setProgress(8 + sumaMysticMarker1);

                                                layoutEquipo1.addView(pb);

                                                TextView numero = new TextView(getContext());
                                                numero.setText(String.valueOf(pb.getProgress()));
                                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                                lp.setMargins(10, 0, 0, 0);
                                                numero.setLayoutParams(lp);
                                                layoutEquipo1.addView(numero);

                                                LinearLayout layoutEquipo2 = new LinearLayout(getContext());
                                                layoutEquipo2.setOrientation(LinearLayout.HORIZONTAL);
                                                ImageView icono3 = new ImageView(getContext());

                                                icono3.setImageResource(R.drawable.iconoinstinct);

                                                LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(55, 55);
                                                icono3.setLayoutParams(layoutParams3);
                                                layoutEquipo2.addView(icono3);

                                                ProgressBar pb2 = new ProgressBar(getContext(),
                                                        null,
                                                        android.R.attr.progressBarStyleHorizontal);

                                                pb2.setMax(10);
                                                pb2.setProgress(6 + sumaInstinctMarker1);

                                                layoutEquipo2.addView(pb2);

                                                TextView numero2 = new TextView(getContext());
                                                numero2.setText(String.valueOf(pb2.getProgress()));
                                                LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                                lp2.setMargins(10, 0, 0, 0);
                                                numero2.setLayoutParams(lp2);
                                                layoutEquipo2.addView(numero2);

                                                LinearLayout layoutEquipo3 = new LinearLayout(getContext());
                                                layoutEquipo3.setOrientation(LinearLayout.HORIZONTAL);
                                                ImageView icono4 = new ImageView(getContext());

                                                icono4.setImageResource(R.drawable.iconovalor);


                                                LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(55, 55);
                                                icono4.setLayoutParams(layoutParams4);
                                                layoutEquipo3.addView(icono4);

                                                ProgressBar pb3 = new ProgressBar(getContext(),
                                                        null,
                                                        android.R.attr.progressBarStyleHorizontal);

                                                pb3.setMax(10);
                                                pb3.setProgress(3 + sumaValorMarker1);

                                                layoutEquipo3.addView(pb3);

                                                TextView numero3 = new TextView(getContext());
                                                numero3.setText(String.valueOf(pb3.getProgress()));
                                                LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                                lp3.setMargins(10, 0, 0, 0);
                                                numero3.setLayoutParams(lp3);
                                                layoutEquipo3.addView(numero3);

                                                Button boton = new Button(getContext());
                                                if (clickMarker1) {
                                                    boton.setText("Unirse");
                                                }
                                                else {
                                                    boton.setText("Desapuntarse");
                                                }

                                                info.addView(title);
                                                info.addView(snippet);
                                                info.addView(layout);
                                                info.addView(layoutEquipo1);
                                                info.addView(layoutEquipo2);
                                                info.addView(layoutEquipo3);
                                                info.addView(boton);

                                                return info;
                                            } else if (marker.getId().equals(idMarker2)) {

                                                LinearLayout info = new LinearLayout(getContext());
                                                info.setOrientation(LinearLayout.VERTICAL);

                                                TextView title = new TextView(getContext());
                                                title.setTextColor(Color.BLACK);
                                                title.setGravity(Gravity.CENTER);
                                                title.setTypeface(null, Typeface.BOLD);
                                                title.setText(marker.getTitle());

                                                TextView snippet = new TextView(getContext());
                                                snippet.setTextColor(Color.GRAY);
                                                snippet.setText(marker.getSnippet());

                                                LinearLayout layout = new LinearLayout(getContext());
                                                layout.setOrientation(LinearLayout.HORIZONTAL);

                                                TextView snippet2 = new TextView(getContext());
                                                snippet2.setTextColor(Color.GRAY);
                                                snippet2.setText("Creador: DobleDe");
                                                layout.addView(snippet2);

                                                ImageView icono = new ImageView(getContext());
                                                icono.setImageResource(R.drawable.iconoinstinct);



                                                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(55, 55);
                                                icono.setLayoutParams(layoutParams);
                                                layout.addView(icono);


                                                LinearLayout layoutEquipo1 = new LinearLayout(getContext());
                                                layoutEquipo1.setOrientation(LinearLayout.HORIZONTAL);
                                                ImageView icono2 = new ImageView(getContext());



                                                icono2.setImageResource(R.drawable.iconovalor);


                                                LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(55, 55);
                                                icono2.setLayoutParams(layoutParams2);
                                                layoutEquipo1.addView(icono2);

                                                ProgressBar pb = new ProgressBar(getContext(),
                                                        null,
                                                        android.R.attr.progressBarStyleHorizontal);

                                                pb.setMax(10);
                                                pb.setProgress(9 + sumaValorMarker2);

                                                layoutEquipo1.addView(pb);

                                                TextView numero = new TextView(getContext());
                                                numero.setText(String.valueOf(pb.getProgress()));
                                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                                lp.setMargins(10, 0, 0, 0);
                                                numero.setLayoutParams(lp);
                                                layoutEquipo1.addView(numero);

                                                LinearLayout layoutEquipo2 = new LinearLayout(getContext());
                                                layoutEquipo2.setOrientation(LinearLayout.HORIZONTAL);
                                                ImageView icono3 = new ImageView(getContext());

                                                icono3.setImageResource(R.drawable.iconoinstinct);

                                                LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(55, 55);
                                                icono3.setLayoutParams(layoutParams3);
                                                layoutEquipo2.addView(icono3);

                                                ProgressBar pb2 = new ProgressBar(getContext(),
                                                        null,
                                                        android.R.attr.progressBarStyleHorizontal);

                                                pb2.setMax(10);
                                                pb2.setProgress(7 + sumaInstinctMarker2);

                                                layoutEquipo2.addView(pb2);

                                                TextView numero2 = new TextView(getContext());
                                                numero2.setText(String.valueOf(pb2.getProgress()));
                                                LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                                lp2.setMargins(10, 0, 0, 0);
                                                numero2.setLayoutParams(lp2);
                                                layoutEquipo2.addView(numero2);

                                                LinearLayout layoutEquipo3 = new LinearLayout(getContext());
                                                layoutEquipo3.setOrientation(LinearLayout.HORIZONTAL);
                                                ImageView icono4 = new ImageView(getContext());

                                                icono4.setImageResource(R.drawable.iconomystic);


                                                LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(55, 55);
                                                icono4.setLayoutParams(layoutParams4);
                                                layoutEquipo3.addView(icono4);

                                                ProgressBar pb3 = new ProgressBar(getContext(),
                                                        null,
                                                        android.R.attr.progressBarStyleHorizontal);

                                                pb3.setMax(10);
                                                pb3.setProgress(5 + sumaMysticMarker2);

                                                layoutEquipo3.addView(pb3);

                                                TextView numero3 = new TextView(getContext());
                                                numero3.setText(String.valueOf(pb3.getProgress()));
                                                LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                                lp3.setMargins(10, 0, 0, 0);
                                                numero3.setLayoutParams(lp3);
                                                layoutEquipo3.addView(numero3);

                                                Button boton = new Button(getContext());
                                                if (clickMarker2) {
                                                    boton.setText("Unirse");
                                                }
                                                else {
                                                    boton.setText("Desapuntarse");
                                                }

                                                info.addView(title);
                                                info.addView(snippet);
                                                info.addView(layout);
                                                info.addView(layoutEquipo1);
                                                info.addView(layoutEquipo2);
                                                info.addView(layoutEquipo3);
                                                info.addView(boton);

                                                return info;
                                            } else if (marker.getId().equals(idMarker3)) {

                                                LinearLayout info = new LinearLayout(getContext());
                                                info.setOrientation(LinearLayout.VERTICAL);

                                                TextView title = new TextView(getContext());
                                                title.setTextColor(Color.BLACK);
                                                title.setGravity(Gravity.CENTER);
                                                title.setTypeface(null, Typeface.BOLD);
                                                title.setText(marker.getTitle());

                                                TextView snippet = new TextView(getContext());
                                                snippet.setTextColor(Color.GRAY);
                                                snippet.setText(marker.getSnippet());

                                                LinearLayout layout = new LinearLayout(getContext());
                                                layout.setOrientation(LinearLayout.HORIZONTAL);

                                                TextView snippet2 = new TextView(getContext());
                                                snippet2.setTextColor(Color.GRAY);
                                                snippet2.setText("Creador: Vairo");
                                                layout.addView(snippet2);

                                                ImageView icono = new ImageView(getContext());
                                                icono.setImageResource(R.drawable.iconomystic);



                                                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(55, 55);
                                                icono.setLayoutParams(layoutParams);
                                                layout.addView(icono);


                                                LinearLayout layoutEquipo1 = new LinearLayout(getContext());
                                                layoutEquipo1.setOrientation(LinearLayout.HORIZONTAL);
                                                ImageView icono2 = new ImageView(getContext());



                                                icono2.setImageResource(R.drawable.iconoinstinct);


                                                LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(55, 55);
                                                icono2.setLayoutParams(layoutParams2);
                                                layoutEquipo1.addView(icono2);

                                                ProgressBar pb = new ProgressBar(getContext(),
                                                        null,
                                                        android.R.attr.progressBarStyleHorizontal);

                                                pb.setMax(10);
                                                pb.setProgress(7 + sumaInstinctMarker3);

                                                layoutEquipo1.addView(pb);

                                                TextView numero = new TextView(getContext());
                                                numero.setText(String.valueOf(pb.getProgress()));
                                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                                lp.setMargins(10, 0, 0, 0);
                                                numero.setLayoutParams(lp);
                                                layoutEquipo1.addView(numero);

                                                LinearLayout layoutEquipo2 = new LinearLayout(getContext());
                                                layoutEquipo2.setOrientation(LinearLayout.HORIZONTAL);
                                                ImageView icono3 = new ImageView(getContext());

                                                icono3.setImageResource(R.drawable.iconomystic);

                                                LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(55, 55);
                                                icono3.setLayoutParams(layoutParams3);
                                                layoutEquipo2.addView(icono3);

                                                ProgressBar pb2 = new ProgressBar(getContext(),
                                                        null,
                                                        android.R.attr.progressBarStyleHorizontal);

                                                pb2.setMax(10);
                                                pb2.setProgress(5 + sumaMysticMarker3);

                                                layoutEquipo2.addView(pb2);

                                                TextView numero2 = new TextView(getContext());
                                                numero2.setText(String.valueOf(pb2.getProgress()));
                                                LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                                lp2.setMargins(10, 0, 0, 0);
                                                numero2.setLayoutParams(lp2);
                                                layoutEquipo2.addView(numero2);

                                                LinearLayout layoutEquipo3 = new LinearLayout(getContext());
                                                layoutEquipo3.setOrientation(LinearLayout.HORIZONTAL);
                                                ImageView icono4 = new ImageView(getContext());

                                                icono4.setImageResource(R.drawable.iconovalor);


                                                LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(55, 55);
                                                icono4.setLayoutParams(layoutParams4);
                                                layoutEquipo3.addView(icono4);

                                                ProgressBar pb3 = new ProgressBar(getContext(),
                                                        null,
                                                        android.R.attr.progressBarStyleHorizontal);

                                                pb3.setMax(10);
                                                pb3.setProgress(2 + sumaValorMarker3);

                                                layoutEquipo3.addView(pb3);

                                                TextView numero3 = new TextView(getContext());
                                                numero3.setText(String.valueOf(pb3.getProgress()));
                                                LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                                lp3.setMargins(10, 0, 0, 0);
                                                numero3.setLayoutParams(lp3);
                                                layoutEquipo3.addView(numero3);

                                                Button boton = new Button(getContext());
                                                if (clickMarker3) {
                                                    boton.setText("Unirse");
                                                }
                                                else {
                                                    boton.setText("Desapuntarse");
                                                }

                                                info.addView(title);
                                                info.addView(snippet);
                                                info.addView(layout);
                                                info.addView(layoutEquipo1);
                                                info.addView(layoutEquipo2);
                                                info.addView(layoutEquipo3);
                                                info.addView(boton);

                                                return info;
                                            }
                                            else if (marker.getId().equals(idMarker4)) {

                                                LinearLayout info = new LinearLayout(getContext());
                                                info.setOrientation(LinearLayout.VERTICAL);

                                                TextView title = new TextView(getContext());
                                                title.setTextColor(Color.BLACK);
                                                title.setGravity(Gravity.CENTER);
                                                title.setTypeface(null, Typeface.BOLD);
                                                title.setText(marker.getTitle());

                                                TextView snippet = new TextView(getContext());
                                                snippet.setTextColor(Color.GRAY);
                                                snippet.setText(marker.getSnippet());

                                                LinearLayout layout = new LinearLayout(getContext());
                                                layout.setOrientation(LinearLayout.HORIZONTAL);

                                                TextView snippet2 = new TextView(getContext());
                                                snippet2.setTextColor(Color.GRAY);
                                                snippet2.setText("Creador: Duom");
                                                layout.addView(snippet2);

                                                ImageView icono = new ImageView(getContext());
                                                icono.setImageResource(R.drawable.iconoinstinct);



                                                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(55, 55);
                                                icono.setLayoutParams(layoutParams);
                                                layout.addView(icono);


                                                LinearLayout layoutEquipo1 = new LinearLayout(getContext());
                                                layoutEquipo1.setOrientation(LinearLayout.HORIZONTAL);
                                                ImageView icono2 = new ImageView(getContext());



                                                icono2.setImageResource(R.drawable.iconomystic);


                                                LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(55, 55);
                                                icono2.setLayoutParams(layoutParams2);
                                                layoutEquipo1.addView(icono2);

                                                ProgressBar pb = new ProgressBar(getContext(),
                                                        null,
                                                        android.R.attr.progressBarStyleHorizontal);

                                                pb.setMax(10);
                                                pb.setProgress(9 + sumaMysticMarker4);

                                                layoutEquipo1.addView(pb);

                                                TextView numero = new TextView(getContext());
                                                numero.setText(String.valueOf(pb.getProgress()));
                                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                                lp.setMargins(10, 0, 0, 0);
                                                numero.setLayoutParams(lp);
                                                layoutEquipo1.addView(numero);

                                                LinearLayout layoutEquipo2 = new LinearLayout(getContext());
                                                layoutEquipo2.setOrientation(LinearLayout.HORIZONTAL);
                                                ImageView icono3 = new ImageView(getContext());

                                                icono3.setImageResource(R.drawable.iconovalor);

                                                LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(55, 55);
                                                icono3.setLayoutParams(layoutParams3);
                                                layoutEquipo2.addView(icono3);

                                                ProgressBar pb2 = new ProgressBar(getContext(),
                                                        null,
                                                        android.R.attr.progressBarStyleHorizontal);

                                                pb2.setMax(10);
                                                pb2.setProgress(8 + sumaValorMarker4);

                                                layoutEquipo2.addView(pb2);

                                                TextView numero2 = new TextView(getContext());
                                                numero2.setText(String.valueOf(pb2.getProgress()));
                                                LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                                lp2.setMargins(10, 0, 0, 0);
                                                numero2.setLayoutParams(lp2);
                                                layoutEquipo2.addView(numero2);

                                                LinearLayout layoutEquipo3 = new LinearLayout(getContext());
                                                layoutEquipo3.setOrientation(LinearLayout.HORIZONTAL);
                                                ImageView icono4 = new ImageView(getContext());

                                                icono4.setImageResource(R.drawable.iconoinstinct);


                                                LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(55, 55);
                                                icono4.setLayoutParams(layoutParams4);
                                                layoutEquipo3.addView(icono4);

                                                ProgressBar pb3 = new ProgressBar(getContext(),
                                                        null,
                                                        android.R.attr.progressBarStyleHorizontal);

                                                pb3.setMax(10);
                                                pb3.setProgress(1 + sumaInstinctMarker4);

                                                layoutEquipo3.addView(pb3);

                                                TextView numero3 = new TextView(getContext());
                                                numero3.setText(String.valueOf(pb3.getProgress()));
                                                LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                                lp3.setMargins(10, 0, 0, 0);
                                                numero3.setLayoutParams(lp3);
                                                layoutEquipo3.addView(numero3);

                                                Button boton = new Button(getContext());
                                                if (clickMarker4) {
                                                    boton.setText("Unirse");
                                                }
                                                else {
                                                    boton.setText("Desapuntarse");
                                                }

                                                info.addView(title);
                                                info.addView(snippet);
                                                info.addView(layout);
                                                info.addView(layoutEquipo1);
                                                info.addView(layoutEquipo2);
                                                info.addView(layoutEquipo3);
                                                info.addView(boton);

                                                return info;
                                            }
                                            else {

                                                LinearLayout info = new LinearLayout(getContext());
                                                info.setOrientation(LinearLayout.VERTICAL);

                                                TextView title = new TextView(getContext());
                                                title.setTextColor(Color.BLACK);
                                                title.setGravity(Gravity.CENTER);
                                                title.setTypeface(null, Typeface.BOLD);
                                                title.setText(marker.getTitle());

                                                TextView snippet = new TextView(getContext());
                                                snippet.setTextColor(Color.GRAY);
                                                snippet.setText(marker.getSnippet());

                                                LinearLayout layout = new LinearLayout(getContext());
                                                layout.setOrientation(LinearLayout.HORIZONTAL);

                                                TextView snippet2 = new TextView(getContext());
                                                snippet2.setTextColor(Color.GRAY);
                                                snippet2.setText("Creador: " + value3);
                                                layout.addView(snippet2);

                                                ImageView icono = new ImageView(getContext());

                                                if (equipo.equals("Instinct")) {
                                                    icono.setImageResource(R.drawable.iconoinstinct);
                                                } else if (equipo.equals("Mystic")) {
                                                    icono.setImageResource(R.drawable.iconomystic);
                                                } else if (equipo.equals("Valor")) {
                                                    icono.setImageResource(R.drawable.iconovalor);
                                                }


                                                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(55, 55);
                                                icono.setLayoutParams(layoutParams);
                                                layout.addView(icono);


                                                LinearLayout layoutEquipo1 = new LinearLayout(getContext());
                                                layoutEquipo1.setOrientation(LinearLayout.HORIZONTAL);
                                                ImageView icono2 = new ImageView(getContext());

                                                boolean mystic = false;
                                                boolean valor = false;
                                                boolean instinct = false;

                                                if (equipo.equals("Instinct")) {
                                                    icono2.setImageResource(R.drawable.iconoinstinct);
                                                    instinct = true;
                                                } else if (equipo.equals("Mystic")) {
                                                    icono2.setImageResource(R.drawable.iconomystic);
                                                    mystic = true;
                                                } else if (equipo.equals("Valor")) {
                                                    icono2.setImageResource(R.drawable.iconovalor);
                                                    valor = true;
                                                }


                                                LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(55, 55);
                                                icono2.setLayoutParams(layoutParams2);
                                                layoutEquipo1.addView(icono2);

                                                ProgressBar pb = new ProgressBar(getContext(),
                                                        null,
                                                        android.R.attr.progressBarStyleHorizontal);

                                                pb.setMax(10);
                                                pb.setProgress(1);

                                                layoutEquipo1.addView(pb);

                                                TextView numero = new TextView(getContext());
                                                numero.setText(String.valueOf(pb.getProgress()));
                                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                                lp.setMargins(10, 0, 0, 0);
                                                numero.setLayoutParams(lp);
                                                layoutEquipo1.addView(numero);

                                                LinearLayout layoutEquipo2 = new LinearLayout(getContext());
                                                layoutEquipo2.setOrientation(LinearLayout.HORIZONTAL);
                                                ImageView icono3 = new ImageView(getContext());

                                                if (!instinct) {
                                                    icono3.setImageResource(R.drawable.iconoinstinct);
                                                    instinct = true;
                                                } else if (!mystic) {
                                                    icono3.setImageResource(R.drawable.iconomystic);
                                                    mystic = true;
                                                } else if (!valor) {
                                                    icono3.setImageResource(R.drawable.iconovalor);
                                                    valor = true;
                                                }

                                                LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(55, 55);
                                                icono3.setLayoutParams(layoutParams3);
                                                layoutEquipo2.addView(icono3);

                                                ProgressBar pb2 = new ProgressBar(getContext(),
                                                        null,
                                                        android.R.attr.progressBarStyleHorizontal);

                                                pb2.setMax(10);
                                                pb2.setProgress(0);

                                                layoutEquipo2.addView(pb2);

                                                TextView numero2 = new TextView(getContext());
                                                numero2.setText(String.valueOf(pb2.getProgress()));
                                                LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                                lp2.setMargins(10, 0, 0, 0);
                                                numero2.setLayoutParams(lp2);
                                                layoutEquipo2.addView(numero2);

                                                LinearLayout layoutEquipo3 = new LinearLayout(getContext());
                                                layoutEquipo3.setOrientation(LinearLayout.HORIZONTAL);
                                                ImageView icono4 = new ImageView(getContext());

                                                if (!instinct) {
                                                    icono4.setImageResource(R.drawable.iconoinstinct);
                                                    instinct = true;
                                                } else if (!mystic) {
                                                    icono4.setImageResource(R.drawable.iconomystic);
                                                    mystic = true;
                                                } else if (!valor) {
                                                    icono4.setImageResource(R.drawable.iconovalor);
                                                    valor = true;
                                                }

                                                LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(55, 55);
                                                icono4.setLayoutParams(layoutParams4);
                                                layoutEquipo3.addView(icono4);

                                                ProgressBar pb3 = new ProgressBar(getContext(),
                                                        null,
                                                        android.R.attr.progressBarStyleHorizontal);

                                                pb3.setMax(10);
                                                pb3.setProgress(0);

                                                layoutEquipo3.addView(pb3);

                                                TextView numero3 = new TextView(getContext());
                                                numero3.setText(String.valueOf(pb3.getProgress()));
                                                LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                                lp3.setMargins(10, 0, 0, 0);
                                                numero3.setLayoutParams(lp3);
                                                layoutEquipo3.addView(numero3);

                                                Button boton = new Button(getContext());
                                                boton.setText("Eliminar");

                                                info.addView(title);
                                                info.addView(snippet);
                                                info.addView(layout);
                                                info.addView(layoutEquipo1);
                                                info.addView(layoutEquipo2);
                                                info.addView(layoutEquipo3);
                                                info.addView(boton);

                                                return info;
                                            }
                                        }

                                    });
                                }
                                return;
                            }
                        });

                        alert.setNegativeButton("Cancelar",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // TODO Auto-generated method stub
                                        return;
                                    }
                                });
                        alert.show();


                    }

                });
                gMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(final Marker marker) {
                        if (marker.getId().equals(idMarker1)) {
                            if (clickMarker1) {
                                AlertDialog.Builder alert = new AlertDialog.Builder(getContext(), R.style.añadirRaid);
                                LinearLayout layout = new LinearLayout(getContext());
                                layout.setOrientation(LinearLayout.VERTICAL);
                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                lp.setMargins(50, 50, 50, 50);
                                alert.setTitle("Quieres unirte a este evento de Raid?");

                                alert.setView(layout);

                                alert.setPositiveButton("Unirse", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        if (equipo.equals("Mystic")) {
                                            sumaMysticMarker1 = 1;
                                        } else if (equipo.equals("Instinct")) {
                                            sumaInstinctMarker1 = 1;
                                        } else if (equipo.equals("Valor")) {
                                            sumaValorMarker1 = 1;
                                        }
                                        clickMarker1 = false;
                                        marker.hideInfoWindow();
                                        return;
                                    }
                                });

                                alert.setNegativeButton("Cancelar",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // TODO Auto-generated method stub
                                                return;
                                            }
                                        });
                                alert.show();

                            }
                            else {
                                AlertDialog.Builder alert = new AlertDialog.Builder(getContext(), R.style.añadirRaid);
                                LinearLayout layout = new LinearLayout(getContext());
                                layout.setOrientation(LinearLayout.VERTICAL);
                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                lp.setMargins(50, 50, 50, 50);
                                alert.setTitle("Quieres desapuntarte de este evento de Raid?");

                                alert.setView(layout);

                                alert.setPositiveButton("Desapuntarse", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        if (equipo.equals("Mystic")) {
                                            sumaMysticMarker1 = 0;
                                        } else if (equipo.equals("Instinct")) {
                                            sumaInstinctMarker1 = 0;
                                        } else if (equipo.equals("Valor")) {
                                            sumaValorMarker1 = 0;
                                        }
                                        clickMarker1 = true;
                                        marker.hideInfoWindow();
                                    }
                                });

                                alert.setNegativeButton("Cancelar",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // TODO Auto-generated method stub
                                                return;
                                            }
                                        });
                                alert.show();
                            }
                        } else if (marker.getId().equals(idMarker2)) {
                            if (clickMarker2) {
                                AlertDialog.Builder alert = new AlertDialog.Builder(getContext(), R.style.añadirRaid);
                                LinearLayout layout = new LinearLayout(getContext());
                                layout.setOrientation(LinearLayout.VERTICAL);
                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                lp.setMargins(50, 50, 50, 50);
                                alert.setTitle("Quieres unirte a este evento de Raid?");

                                alert.setView(layout);

                                alert.setPositiveButton("Unirse", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        if (equipo.equals("Mystic")) {
                                            sumaMysticMarker2 = 1;
                                        } else if (equipo.equals("Instinct")) {
                                            sumaInstinctMarker2 = 1;
                                        } else if (equipo.equals("Valor")) {
                                            sumaValorMarker2 = 1;
                                        }
                                        clickMarker2 = false;
                                        marker.hideInfoWindow();
                                        return;
                                    }
                                });

                                alert.setNegativeButton("Cancelar",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // TODO Auto-generated method stub
                                                return;
                                            }
                                        });
                                alert.show();

                            }
                            else {
                                AlertDialog.Builder alert = new AlertDialog.Builder(getContext(), R.style.añadirRaid);
                                LinearLayout layout = new LinearLayout(getContext());
                                layout.setOrientation(LinearLayout.VERTICAL);
                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                lp.setMargins(50, 50, 50, 50);
                                alert.setTitle("Quieres desapuntarte de este evento de Raid?");

                                alert.setView(layout);

                                alert.setPositiveButton("Desapuntarse", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        if (equipo.equals("Mystic")) {
                                            sumaMysticMarker2 = 0;
                                        } else if (equipo.equals("Instinct")) {
                                            sumaInstinctMarker2 = 0;
                                        } else if (equipo.equals("Valor")) {
                                            sumaValorMarker2 = 0;
                                        }
                                        clickMarker2 = true;
                                        marker.hideInfoWindow();
                                    }
                                });

                                alert.setNegativeButton("Cancelar",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // TODO Auto-generated method stub
                                                return;
                                            }
                                        });
                                alert.show();
                            }
                        } else if (marker.getId().equals(idMarker3)) {
                            if (clickMarker3) {
                                AlertDialog.Builder alert = new AlertDialog.Builder(getContext(), R.style.añadirRaid);
                                LinearLayout layout = new LinearLayout(getContext());
                                layout.setOrientation(LinearLayout.VERTICAL);
                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                lp.setMargins(50, 50, 50, 50);
                                alert.setTitle("Quieres unirte a este evento de Raid?");

                                alert.setView(layout);

                                alert.setPositiveButton("Unirse", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        if (equipo.equals("Mystic")) {
                                            sumaMysticMarker3 = 1;
                                        } else if (equipo.equals("Instinct")) {
                                            sumaInstinctMarker3 = 1;
                                        } else if (equipo.equals("Valor")) {
                                            sumaValorMarker3 = 1;
                                        }
                                        clickMarker3 = false;
                                        marker.hideInfoWindow();
                                        return;
                                    }
                                });

                                alert.setNegativeButton("Cancelar",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // TODO Auto-generated method stub
                                                return;
                                            }
                                        });
                                alert.show();

                            }
                            else {
                                AlertDialog.Builder alert = new AlertDialog.Builder(getContext(), R.style.añadirRaid);
                                LinearLayout layout = new LinearLayout(getContext());
                                layout.setOrientation(LinearLayout.VERTICAL);
                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                lp.setMargins(50, 50, 50, 50);
                                alert.setTitle("Quieres desapuntarte de este evento de Raid?");

                                alert.setView(layout);

                                alert.setPositiveButton("Desapuntarse", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        if (equipo.equals("Mystic")) {
                                            sumaMysticMarker3 = 0;
                                        } else if (equipo.equals("Instinct")) {
                                            sumaInstinctMarker3 = 0;
                                        } else if (equipo.equals("Valor")) {
                                            sumaValorMarker3 = 0;
                                        }
                                        clickMarker3 = true;
                                        marker.hideInfoWindow();
                                    }
                                });

                                alert.setNegativeButton("Cancelar",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // TODO Auto-generated method stub
                                                return;
                                            }
                                        });
                                alert.show();
                            }

                        } else if (marker.getId().equals(idMarker4)) {
                            if (clickMarker4) {
                                AlertDialog.Builder alert = new AlertDialog.Builder(getContext(), R.style.añadirRaid);
                                LinearLayout layout = new LinearLayout(getContext());
                                layout.setOrientation(LinearLayout.VERTICAL);
                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                lp.setMargins(50, 50, 50, 50);
                                alert.setTitle("Quieres unirte a este evento de Raid?");

                                alert.setView(layout);

                                alert.setPositiveButton("Unirse", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        if (equipo.equals("Mystic")) {
                                            sumaMysticMarker4 = 1;
                                        } else if (equipo.equals("Instinct")) {
                                            sumaInstinctMarker4 = 1;
                                        } else if (equipo.equals("Valor")) {
                                            sumaValorMarker4 = 1;
                                        }
                                        clickMarker4 = false;
                                        marker.hideInfoWindow();
                                        return;
                                    }
                                });

                                alert.setNegativeButton("Cancelar",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // TODO Auto-generated method stub
                                                return;
                                            }
                                        });
                                alert.show();

                            }
                            else {
                                AlertDialog.Builder alert = new AlertDialog.Builder(getContext(), R.style.añadirRaid);
                                LinearLayout layout = new LinearLayout(getContext());
                                layout.setOrientation(LinearLayout.VERTICAL);
                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                lp.setMargins(50, 50, 50, 50);
                                alert.setTitle("Quieres desapuntarte de este evento de Raid?");

                                alert.setView(layout);

                                alert.setPositiveButton("Desapuntarse", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        if (equipo.equals("Mystic")) {
                                            sumaMysticMarker4 = 0;
                                        } else if (equipo.equals("Instinct")) {
                                            sumaInstinctMarker4 = 0;
                                        } else if (equipo.equals("Valor")) {
                                            sumaValorMarker4 = 0;
                                        }
                                        clickMarker4 = true;
                                        marker.hideInfoWindow();
                                    }
                                });

                                alert.setNegativeButton("Cancelar",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // TODO Auto-generated method stub
                                                return;
                                            }
                                        });
                                alert.show();
                            }

                        } else {

                        AlertDialog.Builder alert = new AlertDialog.Builder(getContext(), R.style.añadirRaid);
                        LinearLayout layout = new LinearLayout(getContext());
                        layout.setOrientation(LinearLayout.VERTICAL);
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        lp.setMargins(50, 50, 50, 50);
                        alert.setTitle("Quieres eliminar el evento de Raid?");

                        alert.setView(layout);

                        alert.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                marker.remove();
                                return;
                            }
                        });

                        alert.setNegativeButton("Cancelar",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // TODO Auto-generated method stub
                                        return;
                                    }
                                });
                        alert.show();
                    }
                        }

                });

            }

        });

        return view;
    }



    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}