package com.example.footballtalking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TeamPage extends AppCompatActivity {

    private EditText question;
    private Button buttonQ;
    static String q;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_page);

        question = (EditText) findViewById(R.id.question);


        buttonQ = (Button) findViewById(R.id.buttonQ);
        buttonQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                q = question.getText().toString().trim();

            }
        });


    }
}