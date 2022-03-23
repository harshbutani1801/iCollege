package com.example.icollege;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class StudentAttendanceModify extends AppCompatActivity {

    Session session;
    RecyclerView student_attendance_modify;
    FirebaseFirestore mStore;
    String modify_date, modify_dept, modify_branch, modify_sem, modify_class, modify_subject;
    StudentAttendanceModifyAdapter adapter;
    Button submit;
    String user_type;
    ImageView arrow_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_attendance_modify);

        session = new Session(getApplicationContext());
        student_attendance_modify = findViewById(R.id.student_attendance_modify);
        mStore = FirebaseFirestore.getInstance();
        arrow_back = findViewById(R.id.arrow_back);
        submit = findViewById(R.id.btn_submit_roll);

        user_type = session.getusertype();

        modify_date = getIntent().getStringExtra("Date");
        modify_dept = getIntent().getStringExtra("Dept");
        modify_branch = getIntent().getStringExtra("Branch");
        modify_sem = getIntent().getStringExtra("Semester");
        modify_class = getIntent().getStringExtra("Class");
        modify_subject = getIntent().getStringExtra("Subject");

        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ModifyAttendance.class));
            }
        });

        student_attendance_modify.setLayoutManager(new LinearLayoutManager(this));

        Query query = mStore.collection("Attendance").document(modify_date).collection(modify_dept).document(modify_branch)
                .collection(modify_sem).document(modify_class).collection(modify_subject);
        FirestoreRecyclerOptions<StudentAttendanceModifyModel> options = new FirestoreRecyclerOptions.Builder<StudentAttendanceModifyModel>()
                .setQuery(query, StudentAttendanceModifyModel.class)
                .build();

        adapter = new StudentAttendanceModifyAdapter(options, modify_date, modify_dept, modify_branch, modify_sem, modify_class, modify_subject);
        student_attendance_modify.setAdapter(adapter);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(StudentAttendanceModify.this, "Attendance Modified Successfully", Toast.LENGTH_SHORT).show();
                if(user_type.equals("Faculty")){
                    startActivity(new Intent(getApplicationContext(), FacultyDashboard.class));
                }else if(user_type.equals("Head")){
                    startActivity(new Intent(getApplicationContext(), HeadDashboard.class));
                }else {
                    startActivity(new Intent(getApplicationContext(), PrincipalDashboard.class));
                }
            }
        });
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
        startActivity(new Intent(getApplicationContext(), ModifyAttendance.class));
    }
}