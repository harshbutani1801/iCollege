package com.example.icollege;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class FacultyPage extends AppCompatActivity {

    Session Session;
    RecyclerView faculty_list;
    FirebaseFirestore mStore;
    FacultyRcyAdapter adapter;
    String user_type;
    ImageView arrow_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_page);

        Session = new Session(getApplicationContext());
        faculty_list = findViewById(R.id.rcy_view_faculty);
        arrow_back = findViewById(R.id.arrow_back);
        mStore = FirebaseFirestore.getInstance();

        user_type = Session.getusertype();

        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user_type.equals("Admin")){
                    startActivity(new Intent(getApplicationContext(), AdminDashboard.class));
                }else if(user_type.equals("Faculty")){
                    startActivity(new Intent(getApplicationContext(), FacultyDashboard.class));
                }else if(user_type.equals("Head")){
                    startActivity(new Intent(getApplicationContext(), HeadDashboard.class));
                }else {
                    startActivity(new Intent(getApplicationContext(), PrincipalDashboard.class));
                }
            }
        });

        faculty_list.setLayoutManager(new LinearLayoutManager(this));

        Query query = mStore.collection("Faculties");
        FirestoreRecyclerOptions<View_Faculty_Model> options = new FirestoreRecyclerOptions.Builder<View_Faculty_Model>()
                .setQuery(query, View_Faculty_Model.class)
                .build();

        adapter = new FacultyRcyAdapter(options);
        faculty_list.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(user_type.equals("Admin")){
            startActivity(new Intent(getApplicationContext(), AdminDashboard.class));
        }else if(user_type.equals("Faculty")){
            startActivity(new Intent(getApplicationContext(), FacultyDashboard.class));
        }else if(user_type.equals("Head")){
            startActivity(new Intent(getApplicationContext(), HeadDashboard.class));
        }else {
            startActivity(new Intent(getApplicationContext(), PrincipalDashboard.class));
        }
    }
}