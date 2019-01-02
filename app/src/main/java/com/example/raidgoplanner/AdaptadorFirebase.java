package com.example.raidgoplanner;

import  android.content.Context;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

public class AdaptadorFirebase extends FirebaseRecyclerAdapter<Amigo, AmigoHolder> {

    Context context;

    public AdaptadorFirebase (Class<Amigo> modelClass, int modelLayout, Class<AmigoHolder> viewHolderClass, DatabaseReference ref, Context c){
        super(modelClass, modelLayout, viewHolderClass, ref);
        context = c;
    }

    @Override
    protected void populateViewHolder(AmigoHolder viewHolder, Amigo model, int position) {
        viewHolder.nombre.setText(model.getAmigo());
    }
}
