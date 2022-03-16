package com.example.footballtalking;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class VoteFragment extends Fragment {
    SeekBar seekBar1;
    SeekBar seekBar2;
    SeekBar seekBar3;
    SeekBar seekBar4;

    TextView question;

    TextView option1;
    TextView option2;
    TextView option3;
    TextView option4;

    TextView percent1;
    TextView percent2;
    TextView percent3;
    TextView percent4;

    TeamPage teamPage;

    double count1 = 1 , count2 = 1 , count3 = 1 , count4 =1;
    boolean flag1 = true , flag2 = true , flag3 = true , flag4 = true;


    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_vote, container, false);

        seekBar1 = (SeekBar) view.findViewById(R.id.seek_bar1);
        seekBar2 = (SeekBar) view.findViewById(R.id.seek_bar2);
        seekBar3 = (SeekBar) view.findViewById(R.id.seek_bar3);
        seekBar4 = (SeekBar) view.findViewById(R.id.seek_bar4);

        option1 = (TextView) view.findViewById(R.id.option1);
        option2 = (TextView) view.findViewById(R.id.option2);
        option3 = (TextView) view.findViewById(R.id.option3);
        option4 = (TextView) view.findViewById(R.id.option4);

        percent1 = (TextView) view.findViewById(R.id.percent1);
        percent2 = (TextView) view.findViewById(R.id.percent2);
        percent3 = (TextView) view.findViewById(R.id.percent3);
        percent4 = (TextView) view.findViewById(R.id.percent4);

        question = (TextView) view.findViewById(R.id.question);
        question.setText(teamPage.q);

        seekBar1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check condition
                if (flag1) {
                    // when flag1 is true:
                    count1++;
                    count2 =1;
                    count3 = 1;
                    count4 = 1;
                    flag1 = false;
                    flag2 = true;
                    flag3 = true;
                    flag4 = true;
                    // Calc percentage
                    calculatePercent();

                }

            }
        });


        return view;
    }

    private void calculatePercent() {
        // calc total
        double total = count1 + count2 + count3 + count4;
        // calc percentage of all options
        double ppercent1 = (count1/total)*100;
        double ppercent2 = (count2/total)*100;
        double ppercent3 = (count3/total)*100;
        double ppercent4 = (count4/total)*100;
        // set percent on textview
        percent1.setText(String.format("%.0f%%" , percent1));
        // set percent on seekbar
        seekBar1.setProgress((int) ppercent1);


    }
}
