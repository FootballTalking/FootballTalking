package com.example.footballtalking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TeamPage extends AppCompatActivity {

    private EditText questionTextView;
    private EditText option1TextView;
    private EditText option2TextView;
    private EditText option3TextView;
    private Button questionBtn;

    ProgressDialog pd;
    private Button btnAddOption;
    private LinearLayout layout;
    FirebaseFirestore db;
    public ArrayList<String> arr = new ArrayList<String>();

    List<EditText> allEds = new ArrayList<EditText>();
    EditText t;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_page);


        questionTextView = (EditText) findViewById(R.id.QuestionTextView);
        btnAddOption = (Button) findViewById(R.id.btn_addOption);
        layout = (LinearLayout) findViewById(R.id.layout);

        btnAddOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t = new EditText(TeamPage.this);
                allEds.add(t);
                t.setTextSize(15);
                t.setGravity(Gravity.CENTER);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                t.setLayoutParams(params);
                arr.add(t.getText().toString());
                // Log.d("Option: ", t.getText().toString());
                layout.addView(t);
            }
        });


        questionBtn = (Button) findViewById(R.id.QuestionBtn);

        pd = new ProgressDialog(this);

        db = FirebaseFirestore.getInstance();

        questionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // input Data:
                String question = questionTextView.getText().toString().trim();

                String[] strings = new String[allEds.size()];

                for (int i = 0; i < allEds.size(); i++) {
                    strings[i] = allEds.get(i).getText().toString();
                }

                ArrayList<String> rl = new ArrayList<>(Arrays.asList(strings));


                uploadData(question, rl);


            }
        });


    }

    public void uploadData(String question, ArrayList<String> rl) {
        pd.setTitle("Adding Question to Poll Page!");
        pd.show();
        String id = UUID.randomUUID().toString();
        Map<String, Object> doc = new HashMap<>();
        doc.put("question", question);



        ArrayList<Integer> voters = new ArrayList<>();
        for (int i = 0; i < rl.size(); i++) {
            doc.put("voteOption" + i, 0);
            voters.add(0);
        }

        doc.put("options", rl);
        doc.put("voters", voters);


        doc.put("id", id);


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