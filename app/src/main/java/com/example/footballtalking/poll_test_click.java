package com.example.footballtalking;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class poll_test_click extends AppCompatActivity {

    private SeekBar seekBar1;
    private SeekBar seekBar2;
    private SeekBar seekBar3;

    TextView option1;
    TextView option2;
    TextView option3;

    TextView percent1;
    TextView percent2;
    TextView percent3;

    double count1 = 1 , count2 = 1 , count3 = 1;
    boolean flag1 = true , flag2 = true , flag3 = true;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll_test_click);


        /*seekBar1 = findViewById(R.id.seek_bar1);
        seekBar2 =  findViewById(R.id.seek_bar2);
        seekBar3 =  findViewById(R.id.seek_bar3);

        option1 =  findViewById(R.id.option1);
        option2 =  findViewById(R.id.option2);
        option3 =  findViewById(R.id.option3);

        percent1 = findViewById(R.id.percent1);
        percent2 = findViewById(R.id.percent2);
        percent3 = findViewById(R.id.percent3);*/

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
                if (flag2) {
                    // when flag1 is true:
                    count1 =1;
                    count2++;
                    count3 = 1;
                    flag1 = true;
                    flag2 = false;
                    flag3 = true;
                    // Calc percentage
                    calculatePercent();

                }

            }
        });




    }

    private void calculatePercent() {
        // calc total
        double total = count1 + count2 + count3;
        // calc percentage of all options
        double ppercent1 = (count1/total)*100;
        double ppercent2 = (count2/total)*100;
        double ppercent3 = (count3/total)*100;
        // set percent on textview
        percent1.setText(String.format("%.0f%%" , percent1));
        // set percent on seekbar
        seekBar1.setProgress((int) ppercent1);
        percent2.setText(String.format("%.0f%%" , percent2));
        // set percent on seekbar
        seekBar2.setProgress((int) ppercent2);
        percent3.setText(String.format("%.0f%%" , percent3));
        // set percent on seekbar
        seekBar3.setProgress((int) ppercent3);


    }
}