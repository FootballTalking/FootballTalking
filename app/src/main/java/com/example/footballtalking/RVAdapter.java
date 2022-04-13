package com.example.footballtalking;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.MyViewHolder> {
    RVInterface rvInterface;
    List<Match> matchList;
    Context context;

    public RVAdapter(List<Match> matchList, Context context, RVInterface rvInterface) {
        this.matchList = matchList;
        this.context = context;
        this.rvInterface = rvInterface;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.match_item , parent , false);
        MyViewHolder holder = new MyViewHolder(view , rvInterface);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
       Glide.with(this.context).load(matchList.get(position).getFirstTeam().getImageURL()).into(holder.iv_homeTeam);
       Glide.with(this.context).load(matchList.get(position).getSecondTeam().getImageURL()).into(holder.iv_awayTeam);



    }

    @Override
    public int getItemCount() {
        return matchList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_homeTeam;
        ImageView iv_awayTeam;


        public MyViewHolder(@NonNull View itemView , RVInterface rvInterface) {
            super(itemView);
            iv_homeTeam = itemView.findViewById(R.id.homeTeam);
            iv_awayTeam = itemView.findViewById(R.id.awayTeam);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (rvInterface != null) {
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION) {
                            rvInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}
