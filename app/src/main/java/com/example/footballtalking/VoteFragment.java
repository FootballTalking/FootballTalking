package com.example.footballtalking;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class VoteFragment extends Fragment {


    private RecyclerView mFirestoreList;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_vote, container, false);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        firebaseFirestore = FirebaseFirestore.getInstance();

        mFirestoreList = (RecyclerView) getView().findViewById(R.id.firestore_list);


        // query
        Query query = firebaseFirestore.collection("poll_questions");

        // Recycler options
        FirestoreRecyclerOptions<poll_questions> options = new FirestoreRecyclerOptions.Builder<poll_questions>()
                .setQuery(query, poll_questions.class).build();

        adapter = new FirestoreRecyclerAdapter<poll_questions, QuestionsViewHolder>(options) {
            @NonNull
            @Override
            public QuestionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.poll_item, parent, false);
                QuestionsViewHolder holder = new QuestionsViewHolder(v);
                return holder;
            }

            @Override
            protected void onBindViewHolder(@NonNull QuestionsViewHolder holder, int position, @NonNull poll_questions model) {


                holder.list_ques.setText(model.getQuestion());
                holder.persent1.setText(String.valueOf(model.getVotersOption1()));
                holder.persent2.setText(String.valueOf(model.getVotersOption2()));
                holder.persent3.setText(String.valueOf(model.getVotersOption3()));
                holder.radioButton1.setText(model.getOption1());
                holder.radioButton2.setText(model.getOption2());
                holder.radioButton3.setText(model.getOption3());
                holder.votersOption1 = model.getVotersOption1();
                holder.votersOption2 = model.getVotersOption2();
                holder.votersOption3 = model.getVotersOption3();
                holder.numOfVoters.setText("Voters: " + (model.getVotersOption1() + model.getVotersOption2() + model.getVotersOption3()));
                holder.id = model.getId();


                holder.sendBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int radioID = holder.radioGroup.getCheckedRadioButtonId();
                        if (holder.radioButton1.getId() == radioID) {
                            holder.persent1.setText(String.valueOf(holder.votersOption1++));
                            model.setVotersOption1(holder.votersOption1);
                        } else if (holder.radioButton2.getId() == radioID) {
                            holder.persent2.setText(String.valueOf(holder.votersOption2++));
                            model.setVotersOption2(holder.votersOption2);

                        } else {
                            holder.persent3.setText(String.valueOf(holder.votersOption3++));
                            model.setVotersOption3(holder.votersOption3);

                        }

                        uploadData(model.getVotersOption1(), model.getVotersOption2(), model.getVotersOption3(), holder.id);
                        //holder.sendBtn.setEnabled(false);


                    }


                });


            }
        };


        mFirestoreList.setHasFixedSize(true);
        mFirestoreList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new RecyclerViewAdapter();
        mFirestoreList.setAdapter(adapter);


    }

    private void uploadData(int votersOption1, int votersOption2, int votersOption3, String id) {
        firebaseFirestore.collection("poll_questions").document(id).update("votersOption1", votersOption1);
        firebaseFirestore.collection("poll_questions").document(id).update("votersOption2", votersOption2);
        firebaseFirestore.collection("poll_questions").document(id).update("votersOption3", votersOption3);

    }


    private class QuestionsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        int percent;
        private TextView list_ques;
        private RadioGroup radioGroup;
        private RadioButton radioButton1;
        private RadioButton radioButton2;
        private RadioButton radioButton3;
        private Button sendBtn;
        private TextView persent1;
        private TextView persent2;
        private TextView persent3;
        private TextView numOfVoters;

        private int votersOption1;
        private int votersOption2;
        private int votersOption3;

        private String id;


        public QuestionsViewHolder(@NonNull View itemView) {
            super(itemView);


            list_ques = itemView.findViewById(R.id.list_question);
            radioGroup = itemView.findViewById(R.id.radioGroup);
            sendBtn = itemView.findViewById(R.id.SendAnswer);
            radioButton1 = itemView.findViewById(R.id.radio_1);
            radioButton2 = itemView.findViewById(R.id.radio_2);
            radioButton3 = itemView.findViewById(R.id.radio_3);
            persent1 = itemView.findViewById(R.id.persent1);
            persent2 = itemView.findViewById(R.id.persent2);
            persent3 = itemView.findViewById(R.id.persent3);
            numOfVoters = itemView.findViewById(R.id.numOfVoters);

            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {

            //Toast.makeText(view.getContext(), "position = " + getLayoutPosition(), Toast.LENGTH_SHORT).show();


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
