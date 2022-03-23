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

public class StudentAttendanceData extends AppCompatActivity {

    Session Session;
    RecyclerView student_attendance_data;
    FirebaseFirestore mStore;
    String view_dept, view_branch, view_sem, view_class, view_subject, user_type;
    StudentAttendanceDataAdapter adapter;
    ImageView arrow_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_attendance_data);

        student_attendance_data = findViewById(R.id.student_attendance_data);
        arrow_back = findViewById(R.id.arrow_back);
        Session = new Session(getApplicationContext());
        mStore = FirebaseFirestore.getInstance();

        view_dept = getIntent().getStringExtra("Dept");
        view_branch = getIntent().getStringExtra("Branch");
        view_sem = getIntent().getStringExtra("Semester");
        view_class = getIntent().getStringExtra("Class");
        view_subject = getIntent().getStringExtra("Subject");

        user_type = Session.getusertype();

        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user_type.equals("Faculty") || user_type.equals("Head")){
                    startActivity(new Intent(getApplicationContext(), SubjectAttendance.class));
                }else {
                    startActivity(new Intent(getApplicationContext(), SubjectAttendance2.class));
                }
            }
        });

        student_attendance_data.setLayoutManager(new LinearLayoutManager(this));

        Query query = mStore.collection("Attendance").document(view_dept).collection(view_branch).document(view_sem)
                .collection(view_class).document(view_subject).collection("Students");
        FirestoreRecyclerOptions<StudentAttendanceDataModel> options = new FirestoreRecyclerOptions.Builder<StudentAttendanceDataModel>()
                .setQuery(query, StudentAttendanceDataModel.class)
                .build();

        adapter = new StudentAttendanceDataAdapter(options);
        student_attendance_data.setAdapter(adapter);
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
            startActivity(new Intent(getApplicationContext(), SubjectAttendance.class));
        }else {
            startActivity(new Intent(getApplicationContext(), SubjectAttendance2.class));
        }
    }
}