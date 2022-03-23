package com.example.icollege;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.textservice.TextInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class UpdateStudent extends AppCompatActivity {

    TextInputLayout tv_stu_name, tv_stu_email, tv_stu_mobile;
    TextInputEditText stu_name, stu_email, stu_mobile;
    TextView stu_id, stu_roll,  stu_dept, stu_branch, stu_sem, stu_class;
    ImageView arrow_back;
    Button update;
    FirebaseFirestore mStore;
    String _stuid;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_student);

        stu_name = findViewById(R.id.et_stu_name);
        stu_email = findViewById(R.id.et_stu_email);
        stu_mobile = findViewById(R.id.et_stu_mobile);
        tv_stu_name = findViewById(R.id.tv_stu_name);
        tv_stu_email = findViewById(R.id.tv_stu_email);
        tv_stu_mobile = findViewById(R.id.tv_stu_mobile);

        stu_id = findViewById(R.id.tv_stu_id);
        stu_roll = findViewById(R.id.tv_stu_roll);
        stu_dept = findViewById(R.id.tv_stu_dept);
        stu_branch = findViewById(R.id.tv_stu_branch);
        stu_sem = findViewById(R.id.tv_stu_sem);
        stu_class = findViewById(R.id.tv_stu_class);
        update = findViewById(R.id.btn_student_update);
        arrow_back = findViewById(R.id.arrow_back);
        mStore = FirebaseFirestore.getInstance();

        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AdminDashboard.class));
            }
        });

        _stuid = getIntent().getStringExtra("ID");

        mStore.collection("Students").whereEqualTo("ID", _stuid).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _id = stu_id.getText().toString();
                String _name = stu_name.getText().toString();
                String _email = stu_email.getText().toString();
                String _email_patten = "[a-zA-Z0-9_-]+@[a-z]+\\.+[a-z]+";
                String _mobile = stu_mobile.getText().toString();
                String _dept = stu_dept.getText().toString();
                String _branch = stu_branch.getText().toString();
                String _sem = stu_sem.getText().toString();
                String _class = stu_class.getText().toString();

//                if(TextUtils.isEmpty(_name)) {
//                    stu_name.setError("Please Enter FullName");
//                    stu_name.requestFocus();
//                    return;
//                }else if(TextUtils.isEmpty(_email)) {
//                    stu_email.setError("Please Enter Email");
//                    stu_email.requestFocus();
//                    return;
//                }else if(TextUtils.isEmpty(_mobile)) {
//                    stu_mobile.setError("Please Enter Mobile No.");
//                    stu_mobile.requestFocus();
//                    return;
//                }

                if(TextUtils.isEmpty(_name)){
                    tv_stu_name.setErrorEnabled(true);
                    tv_stu_name.setError("Please Enter FullName");
                    tv_stu_name.requestFocus();
                    stu_name.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }
                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            tv_stu_name.setErrorEnabled(false);
                            tv_stu_name.setError(null);
                        }
                        @Override
                        public void afterTextChanged(Editable editable) {
                        }
                    });
                    return;
                }
                else if(TextUtils.isEmpty(_email)){
                    tv_stu_email.setErrorEnabled(true);
                    tv_stu_email.setError("Please Enter Email Address");
                    tv_stu_email.requestFocus();
                    stu_email.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }
                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            tv_stu_email.setErrorEnabled(false);
                            tv_stu_email.setError(null);
                        }
                        @Override
                        public void afterTextChanged(Editable editable) {
                        }
                    });
                    return;
                }
                else if(!_email.matches(_email_patten)){
                    tv_stu_email.setErrorEnabled(true);
                    tv_stu_email.setError("Invalid Email Address");
                    tv_stu_email.requestFocus();
                    stu_email.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }
                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            tv_stu_email.setErrorEnabled(false);
                            tv_stu_email.setError(null);
                        }
                        @Override
                        public void afterTextChanged(Editable editable) {
                        }
                    });
                    return;
                }
                else if(TextUtils.isEmpty(_mobile)){
                    tv_stu_mobile.setErrorEnabled(true);
                    tv_stu_mobile.setError("Please Enter Mobile Number");
                    tv_stu_mobile.requestFocus();
                    stu_mobile.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }
                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            tv_stu_mobile.setErrorEnabled(false);
                            tv_stu_mobile.setError(null);
                        }
                        @Override
                        public void afterTextChanged(Editable editable) {
                        }
                    });
                    return;
                }
                else if(stu_mobile.length() != 13){
                    tv_stu_mobile.setError("10 Digits Required");
                    tv_stu_mobile.requestFocus();
                    stu_mobile.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }
                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            tv_stu_mobile.setErrorEnabled(false);
                            tv_stu_mobile.setError(null);
                        }
                        @Override
                        public void afterTextChanged(Editable editable) {
                        }
                    });
                    return;
                }
                else {
                    progressDialog = new ProgressDialog(UpdateStudent.this);
                    progressDialog.show();
                    progressDialog.setContentView(R.layout.progress_dialog);
                    progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Map<String, Object> upd_student = new HashMap<>();
                    upd_student.put("FullName", _name);
                    upd_student.put("Email", _email);
                    upd_student.put("MobileNumber", _mobile);

                    mStore.collection(_dept).document(_branch).collection(_sem).document(_class)
                            .collection("Students").document(_stuid).update(upd_student)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    mStore.collection("Students").document(_stuid).update(upd_student)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(UpdateStudent.this, "Student Information Updated", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(getApplicationContext(), AdminDashboard.class));
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(UpdateStudent.this, "Student Updating Failed", Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();
                                        }
                                    });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UpdateStudent.this, "Student Updating Failed", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), AdminDashboard.class));
    }
}