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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class VoteFragment extends Fragment {

    private RecyclerView mFirestoreList;

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
                .setQuery(query , poll_questions.class).build();

        adapter = new FirestoreRecyclerAdapter<poll_questions, QuestionsViewHolder>(options) {
            @NonNull
            @Override
            public QuestionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.poll_item, parent, false);
                return new QuestionsViewHolder(v);
            }

            @Override
            protected void onBindViewHolder(@NonNull QuestionsViewHolder holder, int position, @NonNull poll_questions model) {

                holder.list_ques.setText(model.getQuestion());

            }
        };

        mFirestoreList.setHasFixedSize(true);
        mFirestoreList.setLayoutManager(new LinearLayoutManager(getActivity()));
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
