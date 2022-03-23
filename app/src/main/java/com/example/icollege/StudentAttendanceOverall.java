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

public class StudentAttendanceOverall extends AppCompatActivity {

    Session Session;
    RecyclerView student_overall_data;
    FirebaseFirestore mStore;
    String view_dept, view_branch, view_sem, view_class, user_type;
    StudentAttendanceOverallAdapter adapter;
    ImageView arrow_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_attendance_overall);

        student_overall_data = findViewById(R.id.student_overall_attendance);
        arrow_back = findViewById(R.id.arrow_back);
        Session = new Session(getApplicationContext());
        mStore = FirebaseFirestore.getInstance();

        view_dept = getIntent().getStringExtra("Dept");
        view_branch = getIntent().getStringExtra("Branch");
        view_sem = getIntent().getStringExtra("Semester");
        view_class = getIntent().getStringExtra("Class");

        user_type = Session.getusertype();

        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user_type.equals("Faculty") || user_type.equals("Head")){
                    startActivity(new Intent(getApplicationContext(), OverallAttendance.class));
                }else {
                    startActivity(new Intent(getApplicationContext(), OverallAttendance2.class));
                }
            }
        });

        student_overall_data.setLayoutManager(new LinearLayoutManager(this));

        Query query = mStore.collection("Attendance").document(view_dept).collection(view_branch).document(view_sem)
                .collection(view_class);
        FirestoreRecyclerOptions<StudentAttendanceOverallModel> options = new FirestoreRecyclerOptions.Builder<StudentAttendanceOverallModel>()
                .setQuery(query, StudentAttendanceOverallModel.class)
                .build();

        adapter = new StudentAttendanceOverallAdapter(options);
        student_overall_data.setAdapter(adapter);
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
        if(user_type.equals("Faculty") || user_type.equals("Head")){
            startActivity(new Intent(getApplicationContext(), OverallAttendance.class));
        }else {
            startActivity(new Intent(getApplicationContext(), OverallAttendance2.class));
        }
    }
}