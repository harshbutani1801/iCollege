package com.example.icollege;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class StudentRcyItem extends AppCompatActivity {

    TextView stu_id, stu_name, stu_roll, stu_email, stu_mobile, stu_dept, stu_branch, stu_class, stu_sem;
    FirebaseFirestore mStore;
    String student_id;
    ImageView arrow_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_rcy_item);

        stu_id = findViewById(R.id.tv_stu_id);
        stu_name = findViewById(R.id.tv_stu_name);
        stu_roll = findViewById(R.id.tv_stu_roll);
        stu_email = findViewById(R.id.tv_stu_email);
        stu_mobile = findViewById(R.id.tv_stu_mobile);
        stu_dept = findViewById(R.id.tv_stu_dept);
        stu_branch = findViewById(R.id.tv_stu_branch);
        stu_class = findViewById(R.id.tv_stu_class);
        stu_sem = findViewById(R.id.tv_stu_sem);
        arrow_back = findViewById(R.id.arrow_back);
        mStore = FirebaseFirestore.getInstance();

        student_id = getIntent().getStringExtra("StudentID");

        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), StudentPage.class));
            }
        });

        mStore.collection("Students").whereEqualTo("ID", student_id).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                                stu_id.setText(queryDocumentSnapshot.getString("ID"));
                                stu_name.setText(queryDocumentSnapshot.getString("FullName"));
                                stu_roll.setText(queryDocumentSnapshot.getString("EnrollmentNumber"));
                                stu_email.setText(queryDocumentSnapshot.getString("Email"));
                                stu_mobile.setText(queryDocumentSnapshot.getString("MobileNumber"));
                                stu_dept.setText(queryDocumentSnapshot.getString("Department"));
                                stu_branch.setText(queryDocumentSnapshot.getString("Branch"));
                                stu_class.setText(queryDocumentSnapshot.getString("ClassRoom"));
                                stu_sem.setText(queryDocumentSnapshot.getString("Semester"));
                            }
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), StudentPage.class));
    }
}