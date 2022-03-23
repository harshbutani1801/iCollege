package com.example.icollege;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class StudentAttedance extends AppCompatActivity {

    Session Session;
    FirebaseFirestore mStore;
    RecyclerView student_roll;
    String user_type, att_dept, att_branch, att_sem, att_class, att_subject, att_date, att_time;
    StudentAttendanceAdapter adapter;
    Button submit;
    ImageView arrow_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_attedance);

        Session = new Session(getApplicationContext());
        student_roll = findViewById(R.id.rcy_student_roll);
        submit = findViewById(R.id.btn_submit_roll);
        arrow_back = findViewById(R.id.arrow_back);
        mStore = FirebaseFirestore.getInstance();

        user_type = Session.getusertype();

        att_date = getIntent().getStringExtra("Date");
        att_time = getIntent().getStringExtra("Time");
        att_dept = getIntent().getStringExtra("Dept");
        att_branch = getIntent().getStringExtra("Branch");
        att_sem = getIntent().getStringExtra("Semester");
        att_class = getIntent().getStringExtra("Class");
        att_subject = getIntent().getStringExtra("Subject");

        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), TakeAttendance.class));
            }
        });

        student_roll.setLayoutManager(new LinearLayoutManager(this));

        Query query = mStore.collection(att_dept).document(att_branch).collection(att_sem).document(att_class).collection("Students");
        FirestoreRecyclerOptions<StudentAttendanceModel> options = new FirestoreRecyclerOptions.Builder<StudentAttendanceModel>()
                .setQuery(query, StudentAttendanceModel.class)
                .build();

        adapter = new StudentAttendanceAdapter(options, att_date, att_dept, att_branch, att_sem, att_class, att_subject);
        student_roll.setAdapter(adapter);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), BlankActivity.class));
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
        startActivity(new Intent(getApplicationContext(), TakeAttendance.class));
    }
}