package com.example.icollege;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class StudentPage extends AppCompatActivity {

    Session Session;
    RecyclerView student_list;
    FirebaseFirestore mStore;
    String user_type;
    StudentRcyAdapter adapter;
    ImageView arrow_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_page);

        Session = new Session(getApplicationContext());
        student_list = findViewById(R.id.rcy_view_student);
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

        student_list.setLayoutManager(new LinearLayoutManager(this));

        Query query = mStore.collection("Students");
        FirestoreRecyclerOptions<View_Student_Model> options = new FirestoreRecyclerOptions.Builder<View_Student_Model>()
                .setQuery(query, View_Student_Model.class)
                .build();

        adapter = new StudentRcyAdapter(options);
        student_list.setAdapter(adapter);
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