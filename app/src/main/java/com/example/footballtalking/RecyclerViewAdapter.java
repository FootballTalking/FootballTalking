package com.example.footballtalking;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.poll_item, parent , false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.seekBar1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        holder.option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.flag1) {
                    // when flag1 is true:
                    holder.count1++;
                    holder.count2 = 1;
                    holder.count3 = 1;
                    holder.flag1 = false;
                    holder.flag2 = true;
                    holder.flag3 = true;

                    // Calc percentage
                    calculatePercent();

                }
            }

            private void calculatePercent() {
                // calc total
                double total = holder.count1 + holder.count2 + holder.count3;
                // calc percentage of all options
                double ppercent1 = (holder.count1 / total) * 100;
                double ppercent2 = (holder.count2 / total) * 100;
                double ppercent3 = (holder.count3 / total) * 100;
                // set percent on textview
                holder.percent1.setText(String.format("%.0f%%", holder.percent1));
                // set percent on seekbar
                holder.seekBar1.setProgress((int) ppercent1);
                holder.percent2.setText(String.format("%.0f%%", holder.percent2));
                // set percent on seekbar
                holder.seekBar2.setProgress((int) ppercent2);
                holder.percent3.setText(String.format("%.0f%%", holder.percent3));
                // set percent on seekbar
                holder.seekBar3.setProgress((int) ppercent3);
            }
        });


    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private SeekBar seekBar1;
        private SeekBar seekBar2;
        private SeekBar seekBar3;

        TextView option1;
        TextView option2;
        TextView option3;

        TextView percent1;
        TextView percent2;
        TextView percent3;

        double count1 = 1, count2 = 1, count3 = 1;
        boolean flag1 = true, flag2 = true, flag3 = true;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            /*seekBar1 = itemView.findViewById(R.id.seek_bar1);
            seekBar2 = itemView.findViewById(R.id.seek_bar2);
            seekBar3 = itemView.findViewById(R.id.seek_bar3);
            option1 = itemView.findViewById(R.id.option1);
            option2 = itemView.findViewById(R.id.option2);
            option3 = itemView.findViewById(R.id.option3);
            percent1 = itemView.findViewById(R.id.percent1);
            percent2 = itemView.findViewById(R.id.percent2);
            percent3 = itemView.findViewById(R.id.percent3);*/
        }
    }
}
