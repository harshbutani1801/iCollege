package com.example.icollege;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class DeleteStudent extends AppCompatActivity {

    TextView stu_id, stu_name, stu_email, stu_roll, stu_mobile, stu_dept, stu_branch, stu_sem, stu_class;
    ImageView arrow_back;
    FirebaseFirestore mStore;
    Button delete;
    String _stuid;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_student);

        stu_id = findViewById(R.id.tv_stu_id);
        stu_name = findViewById(R.id.tv_stu_name);
        stu_email = findViewById(R.id.tv_stu_email);
        stu_roll = findViewById(R.id.tv_stu_roll);
        stu_mobile = findViewById(R.id.tv_stu_mobile);
        stu_dept = findViewById(R.id.tv_stu_dept);
        stu_branch = findViewById(R.id.tv_stu_branch);
        stu_sem = findViewById(R.id.tv_stu_sem);
        stu_class = findViewById(R.id.tv_stu_class);
        arrow_back = findViewById(R.id.arrow_back);
        delete = findViewById(R.id.btn_student_delete);
        mStore = FirebaseFirestore.getInstance();

        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AdminDashboard.class));
            }
        });

        _stuid = getIntent().getStringExtra("ID");

        mStore.collection("Students").whereEqualTo("ID", _stuid).get()
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
                                stu_sem.setText(queryDocumentSnapshot.getString("Semester"));
                                stu_class.setText(queryDocumentSnapshot.getString("ClassRoom"));
                            }
                        }
                    }
                });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _dept = stu_dept.getText().toString();
                String _branch = stu_branch.getText().toString();
                String _sem = stu_sem.getText().toString();
                String _class = stu_class.getText().toString();

                new AlertDialog.Builder(DeleteStudent.this, R.style.AlertDialog)
                        .setIcon(R.drawable.delete)
                        .setTitle("Delete Student")
                        .setMessage("Are you sure you want to delete StudentID: " + _stuid)
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                progressDialog = new ProgressDialog(DeleteStudent.this);
                                progressDialog.show();
                                progressDialog.setContentView(R.layout.progress_dialog);
                                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                                mStore.collection(_dept).document(_branch).collection(_sem).document(_class)
                                        .collection("Students").document(_stuid).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        mStore.collection("Students").document(_stuid).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(DeleteStudent.this, "Student Deleted Successfully ", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(getApplicationContext(), AdminDashboard.class));
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(DeleteStudent.this, "Student Deletion Failed", Toast.LENGTH_SHORT).show();
                                                progressDialog.dismiss();
                                            }
                                        });
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(DeleteStudent.this, "Student Deletion Failed", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    }
                                });
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Do Nothing
                    }
                }).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), AdminDashboard.class));
    }
}