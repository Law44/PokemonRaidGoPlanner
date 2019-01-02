package com.example.raidgoplanner;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.FriendViewHolder>{

    ArrayList<String> list;

    RecyclerAdapter(ArrayList<String> list){
        this.list = list;
    }

    @Override
    public FriendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemPoem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend, parent, false);
        return new FriendViewHolder(itemPoem);
    }

    @Override
    public void onBindViewHolder(FriendViewHolder holder, int position) {
        String nombre = list.get(position);
        holder.friendName.setText(nombre);

    }

    @Override
    public int getItemCount() {
        return (list != null ? list.size() : 0);
    }

    class FriendViewHolder extends RecyclerView.ViewHolder {
        private TextView friendName;

        FriendViewHolder(View friend) {
            super(friend);
            friendName = friend.findViewById(R.id.friend_name);
        }
    }
}
