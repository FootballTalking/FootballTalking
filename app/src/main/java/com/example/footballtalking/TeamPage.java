package com.example.footballtalking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TeamPage extends AppCompatActivity {

    private EditText questionTextView;
    private EditText option1TextView;
    private EditText option2TextView;
    private EditText option3TextView;
    private Button questionBtn;

    ProgressDialog pd;

    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_page);


        questionTextView = (EditText) findViewById(R.id.QuestionTextView);
        option1TextView = (EditText) findViewById(R.id.option1);
        option2TextView = (EditText) findViewById(R.id.option2);
        option3TextView = (EditText) findViewById(R.id.option3);


        questionBtn = (Button) findViewById(R.id.QuestionBtn);

        pd = new ProgressDialog(this);

        db = FirebaseFirestore.getInstance();

        questionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // input Data:
                String question = questionTextView.getText().toString().trim();

                String option1 = option1TextView.getText().toString().trim();

                String option2 = option2TextView.getText().toString().trim();

                String option3 = option3TextView.getText().toString().trim();
                uploadData(question, option1, option2, option3);


            }
        });


    }

    private void uploadData(String question, String option1, String option2, String option3) {
        pd.setTitle("Adding Question to Poll Page!");
        pd.show();
        String id = UUID.randomUUID().toString();
        Map<String, Object> doc = new HashMap<>();
        doc.put("question", question);
        doc.put("option1", option1);
        doc.put("option2", option2);
        doc.put("option3", option3);
        doc.put("id" , id);
        doc.put("votersOption1" , 0);
        doc.put("votersOption2" , 0);
        doc.put("votersOption3" , 0);



        db.collection("poll_questions").document(id).set(doc).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                // this will be called when Data is added successfully
                pd.dismiss();
                Toast.makeText(TeamPage.this, "Added", Toast.LENGTH_LONG).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // this will be called when there is an error
                pd.dismiss();
                Toast.makeText(TeamPage.this, "Error", Toast.LENGTH_LONG).show();

            }
        });

    }


}