package com.example.raidgoplanner;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class AmigoHolder extends RecyclerView.ViewHolder {
    TextView nombre;


    public AmigoHolder(@NonNull View itemView) {
        super(itemView);
        nombre = itemView.findViewById(R.id.friend_name);
    }
}
