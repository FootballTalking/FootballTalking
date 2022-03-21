package com.example.footballtalking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class TeamPage extends AppCompatActivity {

    private RecyclerView mFirestoreList;

    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_page);

        firebaseFirestore = FirebaseFirestore.getInstance();
        mFirestoreList = (RecyclerView) findViewById(R.id.firestore_list);



        // query
        Query query = firebaseFirestore.collection("poll_questions");

        // Recycler options
        FirestoreRecyclerOptions<poll_questions> options = new FirestoreRecyclerOptions.Builder<poll_questions>()
                .setQuery(query , poll_questions.class).build();

        adapter = new FirestoreRecyclerAdapter<poll_questions, QuestionsViewHolder>(options) {
            @NonNull
            @Override
            public QuestionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.poll_item, parent, false);
                return new QuestionsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull QuestionsViewHolder holder, int position, @NonNull poll_questions model) {

                holder.list_ques.setText(model.getQuestion());

            }
        };

        mFirestoreList.setHasFixedSize(true);
        mFirestoreList.setLayoutManager(new LinearLayoutManager(this));
        mFirestoreList.setAdapter(adapter);






        }

    private class QuestionsViewHolder extends RecyclerView.ViewHolder {

        private TextView list_ques;


        public QuestionsViewHolder(@NonNull View itemView) {
            super(itemView);

            list_ques = itemView.findViewById(R.id.list_question);


        }
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }





}