package com.example.footballtalking;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment implements RVInterface {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    List<Match> matchList = new ArrayList<Match>();


    private ImageView logOut;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_home_page, container, false);
    }


    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        recyclerView = (RecyclerView) getView().findViewById(R.id.matches_List);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new RVAdapter(matchList , getActivity() , this);
        recyclerView.setAdapter(mAdapter);

        fillMatchList();


        logOut = (ImageView) getView().findViewById(R.id.logOutBtn);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        final TextView username = (TextView) getView().findViewById(R.id.userName);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if (userProfile != null) {
                    String uName = userProfile.name;
                    username.setText(uName);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Something wrong happened!", Toast.LENGTH_LONG).show();

            }
        });


    }

    private void fillMatchList() {

        Team abha = new Team("Abha", "https://upload.wikimedia.org/wikipedia/en/a/af/Abha1.png");
        Team alahli = new Team("Al-Ahli", "https://upload.wikimedia.org/wikipedia/en/thumb/4/45/Al_Ahli_Saudi_FC_logo.svg/270px-Al_Ahli_Saudi_FC_logo.svg.png");
        Team albatin = new Team("Al-Batin", "https://upload.wikimedia.org/wikipedia/en/8/87/Al-Batin_F.C_logo.png");
        Team alettifaq = new Team("Al-Ettifaq", "https://upload.wikimedia.org/wikipedia/en/f/fd/Al-Ettifaq_%28logo%29.png");
        Team alfaisaly = new Team("Al-Faisaly", "https://upload.wikimedia.org/wikipedia/en/thumb/c/c9/Al-Faisaly_FC_New_Logo.png/240px-Al-Faisaly_FC_New_Logo.png");
        Team alfateh = new Team("Al-Fateh", "https://upload.wikimedia.org/wikipedia/en/f/fd/Al-Fateh_FC_logo.png");
        Team alfayha = new Team("Al-Fayha", "https://upload.wikimedia.org/wikipedia/en/9/9c/Al_Fayha_FC_logo.png");
        Team alhazem = new Team("Al-Hazem", "https://upload.wikimedia.org/wikipedia/en/thumb/2/22/Al_Hazem_new_logo.png/225px-Al_Hazem_new_logo.png");
        Team alhilal = new Team("Al-Hilal", "https://upload.wikimedia.org/wikipedia/en/f/f8/Al-Hilal_newlogo.png");
        Team alittihad = new Team("Al-Ittihad", "https://upload.wikimedia.org/wikipedia/en/thumb/d/d3/Ittihad_logo_2019.png/225px-Ittihad_logo_2019.png");
        Team alnassr = new Team("Al-Nassr", "https://upload.wikimedia.org/wikipedia/en/9/9d/Logo_Al-Nassr.png");
        Team alraed = new Team("Al-Raed", "https://upload.wikimedia.org/wikipedia/en/2/28/AlRaed_logo.png");
        Team alshabab = new Team("Al-Shabab", "https://upload.wikimedia.org/wikipedia/en/thumb/1/13/Al_Shabab_FC_%28Riyadh%29_logo.png/300px-Al_Shabab_FC_%28Riyadh%29_logo.png");
        Team altaawoun = new Team("Al-Taawoun", "https://upload.wikimedia.org/wikipedia/en/c/ca/AL_Taawoun_new_logo.png");
        Team altai = new Team("Al-Tai", "https://tai1381.com/files/2019/04/%D8%A7%D9%84%D8%B7%D8%A7%D8%A6%D9%8A.png");
        Team damac = new Team("Damac", "https://upload.wikimedia.org/wikipedia/en/7/7a/Damac_F.C._logo.png");


        Match m1 = new Match("2022-04-02", alfayha, alittihad);
        Match m2 = new Match("2022-04-02", abha, alshabab);
        Match m3 = new Match("2022-04-02", alraed, alfateh);
        Match m4 = new Match("2022-04-02", alhazem, altaawoun);
        Match m5 = new Match("2022-04-02", alnassr, damac);
        Match m6 = new Match("2022-04-02", alahli, alfaisaly);
        Match m7 = new Match("2022-04-02", alettifaq, albatin);
        Match m8 = new Match("2022-04-02", alhilal, altai);

        matchList.addAll(Arrays.asList(new Match[]{m1, m2, m3, m4, m5, m6, m7, m8}));


    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity() , chatroom.class);
        startActivity(intent);

    }
}
