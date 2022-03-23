package com.example.icollege;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class AddStudent extends AppCompatActivity {

    TextInputLayout tv_stu_id, tv_stu_name, tv_stu_rollno, tv_stu_email, tv_stu_mobile;
    TextInputEditText stu_id, stu_name, stu_rollno, stu_email, stu_mobile;
    ImageView arrow_back;
    Button add_student;
    FirebaseFirestore mStore;
    String dept, branch, sem, classroom, stuid;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        stu_id = findViewById(R.id.et_stu_id);
        stu_name = findViewById(R.id.et_stu_name);
        stu_rollno = findViewById(R.id.et_stu_roll);
        stu_email = findViewById(R.id.et_stu_email);
        stu_mobile = findViewById(R.id.et_stu_mobile);
        add_student = findViewById(R.id.btn_add_student);
        arrow_back = findViewById(R.id.arrow_back);
        tv_stu_id = findViewById(R.id.tv_stu_id);
        tv_stu_name = findViewById(R.id.tv_stu_name);
        tv_stu_rollno = findViewById(R.id.tv_stu_roll);
        tv_stu_email = findViewById(R.id.tv_stu_email);
        tv_stu_mobile = findViewById(R.id.tv_stu_mobile);
        mStore = FirebaseFirestore.getInstance();

        dept = getIntent().getStringExtra("Dept");
        branch = getIntent().getStringExtra("Branch");
        sem = getIntent().getStringExtra("Semester");
        classroom = getIntent().getStringExtra("Class");

        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterStudent.class));
            }
        });

        stu_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.equals("")){
                    mStore.collection("Students").whereEqualTo("ID", stu_id.getText().toString()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for(QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                                    stuid = queryDocumentSnapshot.getString("ID");
                                }
                            }
                        }
                    });
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        add_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _stuid = stu_id.getText().toString();
                String _stuname = stu_name.getText().toString();
                String _sturollno = stu_rollno.getText().toString();
                String _stuemail = stu_email.getText().toString();
                String _email_patten = "[a-zA-Z0-9_-]+@[a-z]+\\.+[a-z]+";
                String _stumobile = stu_mobile.getText().toString();

                if(TextUtils.isEmpty(_stuid)) {
                    tv_stu_id.setErrorEnabled(true);
                    tv_stu_id.setError("Please Enter StudentID");
                    tv_stu_id.requestFocus();
                    stu_id.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }
                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            tv_stu_id.setErrorEnabled(false);
                            tv_stu_id.setError(null);
                        }
                        @Override
                        public void afterTextChanged(Editable editable) {
                        }
                    });
                    return;
                }
                else if(TextUtils.isEmpty(_stuname)) {
                    tv_stu_name.setErrorEnabled(true);
                    tv_stu_name.setError("Please Enter Student FullName");
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
                else if(TextUtils.isEmpty(_sturollno)) {
                    tv_stu_rollno.setErrorEnabled(true);
                    tv_stu_rollno.setError("Please Enter Enrollment No.");
                    tv_stu_rollno.requestFocus();
                    stu_rollno.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }
                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            tv_stu_rollno.setErrorEnabled(false);
                            tv_stu_rollno.setError(null);
                        }
                        @Override
                        public void afterTextChanged(Editable editable) {
                        }
                    });
                    return;
                }
                else if(TextUtils.isEmpty(_stuemail)) {
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
                else if(!_stuemail.matches(_email_patten)){
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
                else if(TextUtils.isEmpty(_stumobile)) {
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
                else if(stu_mobile.length() != 10){
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
                    if(_stuid.equals(stuid)) {
                        tv_stu_id.setErrorEnabled(true);
                        tv_stu_id.setError("StudentID Already Exists");
                        tv_stu_id.requestFocus();
                        stu_id.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            }
                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                tv_stu_id.setErrorEnabled(false);
                                tv_stu_id.setError(null);
                            }
                            @Override
                            public void afterTextChanged(Editable editable) {
                            }
                        });
                        return;
                    }else {
                        progressDialog = new ProgressDialog(AddStudent.this);
                        progressDialog.show();
                        progressDialog.setContentView(R.layout.progress_dialog);
                        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

//                        Map<String, Object> create_attendance = new HashMap<>();
//                        create_attendance.put("ID", stu_id);
//                        create_attendance.put("FullName", _stuname);
//                        create_attendance.put("EnrollmentNumber", _sturollno);
//                        create_attendance.put("Department", dept);
//                        create_attendance.put("Branch", branch);
//                        create_attendance.put("Semester", sem);
//                        create_attendance.put("ClassRoom", classroom);
//                        create_attendance.put("MLPercentage", "0");
//                        create_attendance.put("DNetPercentage", "0");
//
//                        mStore.collection("Attendance").document(dept).collection(branch).document(sem).collection(classroom)
//                                .document(_stuid).set(create_attendance)
//                                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void aVoid) {
//                                        //Attendance Outside Taken
//                                    }
//                                });

                        Map<String, Object> student = new HashMap<>();
                        student.put("ID", _stuid);
                        student.put("FullName" , _stuname);
                        student.put("EnrollmentNumber" , _sturollno);
                        student.put("Email" ,_stuemail);
                        student.put("MobileNumber" , "+91"+_stumobile);
                        student.put("Department", dept);
                        student.put("Branch", branch);
                        student.put("Semester", sem);
                        student.put("ClassRoom", classroom);
                        student.put("Date", "0");
                        student.put("MLA", "0");
                        student.put("MLD", "0");
                        student.put("DNetA", "0");
                        student.put("DNetD", "0");

                        mStore.collection(dept).document(branch).collection(sem).document(classroom).collection("Students")
                                .document(_stuid).set(student).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                mStore.collection("Students").document( _stuid).set(student).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        stu_id.setText("");
                                        stu_name.setText("");
                                        stu_rollno.setText("");
                                        stu_email.setText("");
                                        stu_mobile.setText("");
                                        Toast.makeText(AddStudent.this, "Student Added Successfully", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), AdminDashboard.class));
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        stu_id.setText("");
                                        stu_name.setText("");
                                        stu_rollno.setText("");
                                        stu_email.setText("");
                                        stu_mobile.setText("");
                                        Toast.makeText(AddStudent.this, "Failed to Add Student", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AddStudent.this, "Failed to Add Student", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), RegisterStudent.class));
    }
}