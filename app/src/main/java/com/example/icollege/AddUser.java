package com.example.icollege;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddUser extends AppCompatActivity {

    TextInputLayout tv_id, tv_name, tv_email, tv_password, tv_mobile;
    TextInputEditText id, name, email, password, mobile;
    Spinner position, faculty_dept, faculty_branch, faculty_desg;
    ImageView arrow_back;
    String userid, dept_value;
    FirebaseFirestore mStore;
    ArrayList<String> dept, branch, designation;
    ArrayAdapter<String> _dept, _branch, _desg;
    ProgressDialog progressDialog;
    Button add_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        id = findViewById(R.id.et_userid);
        name = findViewById(R.id.et_full_name);
        email = findViewById(R.id.et_email);
        password = findViewById(R.id.et_password);
        mobile = findViewById(R.id.et_mobile);
        position = findViewById(R.id.spn_usertype);
        faculty_dept = findViewById(R.id.spn_faculty_dept);
        faculty_branch = findViewById(R.id.spn_faculty_branch);
        faculty_desg = findViewById(R.id.spn_faculty_desg);
        add_user = findViewById(R.id.btn_add_user);

        tv_id = findViewById(R.id.tv_userid);
        tv_name = findViewById(R.id.tv_full_name);
        tv_email = findViewById(R.id.tv_email);
        tv_password = findViewById(R.id.tv_password);
        tv_mobile = findViewById(R.id.tv_mobile);
        arrow_back = findViewById(R.id.arrow_back);
        mStore = FirebaseFirestore.getInstance();

        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AdminDashboard.class));
            }
        });

        id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.equals("")){
                    mStore.collection("Faculties").whereEqualTo("UserID", id.getText().toString()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for(QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                                    userid = queryDocumentSnapshot.getString("UserID");
                                    Log.d("User ID Checking:", userid);
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

        dept = new ArrayList<>();
        dept.clear();
        dept.add("Select Department");
        mStore.collection("Department").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for(DocumentSnapshot snapshot : value){
                    dept.add(snapshot.getString("Department"));
                }
                _dept.notifyDataSetChanged();
            }
        });
        _dept = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, dept);
        faculty_dept.setAdapter(_dept);

        branch = new ArrayList<>();
        faculty_dept.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                branch.clear();
                branch.add("Select Branch");
                dept_value = adapterView.getSelectedItem().toString();
                mStore.collection(dept_value +  " BRANCH").addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for(DocumentSnapshot snapshot : value){
                            branch.add(snapshot.getString(dept_value +  " BRANCH"));
                        }
                        _branch.notifyDataSetChanged();
                    }
                });
                _branch = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, branch);
                faculty_branch.setAdapter(_branch);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        designation = new ArrayList<>();
        designation.clear();
        designation.add("Select Designation");
        mStore.collection("Designation").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for(DocumentSnapshot snapshot : value){
                    designation.add(snapshot.getString("Designation"));
                }
                _desg.notifyDataSetChanged();
            }
        });
        _desg = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, designation);
        faculty_desg.setAdapter(_desg);

        add_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _userid = id.getText().toString();
                String _name = name.getText().toString();
                String _email = email.getText().toString();
                String _email_patten = "[a-zA-Z0-9_-]+@[a-z]+\\.+[a-z]+";
                String _password = password.getText().toString().trim();
                String _mobile = mobile.getText().toString();
                String _position = position.getSelectedItem().toString();
                String _fdept = faculty_dept.getSelectedItem().toString();
                String _fbranch = faculty_branch.getSelectedItem().toString();
                String _fdesg = faculty_desg.getSelectedItem().toString();

                Log.d("User ID:", _userid);
                if(TextUtils.isEmpty(_userid)) {
                    tv_id.setErrorEnabled(true);
                    tv_id.setError("Please Enter UserID");
                    id.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }
                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            tv_id.setErrorEnabled(false);
                            tv_id.setError(null);
                        }
                        @Override
                        public void afterTextChanged(Editable editable) {
                        }
                    });
                    return;
                }
                else if(TextUtils.isEmpty(_name)){
                    tv_name.setErrorEnabled(true);
                    tv_name.setError("Please Enter FullName");
                    tv_name.requestFocus();
                    name.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }
                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            tv_name.setErrorEnabled(false);
                            tv_name.setError(null);
                        }
                        @Override
                        public void afterTextChanged(Editable editable) {
                        }
                    });
                    return;
                }
                else if(TextUtils.isEmpty(_email)){
                    tv_email.setErrorEnabled(true);
                    tv_email.setError("Please Enter Email");
                    tv_email.requestFocus();
                    email.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }
                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            tv_email.setErrorEnabled(false);
                            tv_email.setError(null);
                        }
                        @Override
                        public void afterTextChanged(Editable editable) {
                        }
                    });
                    return;
                }
                else if(!_email.matches(_email_patten)){
                    tv_email.setErrorEnabled(true);
                    tv_email.setError("Invalid Email Address");
                    tv_email.requestFocus();
                    email.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }
                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            tv_email.setErrorEnabled(false);
                            tv_email.setError(null);
                        }
                        @Override
                        public void afterTextChanged(Editable editable) {
                        }
                    });
                    return;
                }
                else if(TextUtils.isEmpty(_password)){
                    tv_password.setError("Please Enter Password");
                    tv_password.requestFocus();
                    password.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }
                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            tv_password.setErrorEnabled(false);
                            tv_password.setError(null);
                        }
                        @Override
                        public void afterTextChanged(Editable editable) {
                        }
                    });
                    return;
                }
                else if(password.length() < 8){
                    tv_password.setError("Minimum 8 Characters Required");
                    tv_password.requestFocus();
                    password.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }
                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            tv_password.setErrorEnabled(false);
                            tv_password.setError(null);
                        }
                        @Override
                        public void afterTextChanged(Editable editable) {
                        }
                    });
                    return;
                }
                else if(TextUtils.isEmpty(_mobile)){
                    tv_mobile.setError("Please Enter Mobile Number");
                    tv_mobile.requestFocus();
                    mobile.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }
                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            tv_mobile.setErrorEnabled(false);
                            tv_mobile.setError(null);
                        }
                        @Override
                        public void afterTextChanged(Editable editable) {
                        }
                    });
                    return;
                }
                else if(mobile.length() != 10){
                    tv_mobile.setError("10 Digits Required");
                    tv_mobile.requestFocus();
                    mobile.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }
                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            tv_mobile.setErrorEnabled(false);
                            tv_mobile.setError(null);
                        }
                        @Override
                        public void afterTextChanged(Editable editable) {
                        }
                    });
                    return;
                }
                else{
                    if(_position.equals("Select Position")){
                        Toast.makeText(AddUser.this, "Please Select Position", Toast.LENGTH_SHORT).show();
                    }
                    else if(_fdept.equals("Select Department")){
                        Toast.makeText(AddUser.this, "Please Select Department", Toast.LENGTH_SHORT).show();
                    }
                    else if(_fbranch.equals("Select Branch")){
                        Toast.makeText(AddUser.this, "Please Select Branch", Toast.LENGTH_SHORT).show();
                    }
                    else if(_fdesg.equals("Select Designation")){
                        Toast.makeText(AddUser.this, "Please Select Designation", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        if(_userid.equals(userid)){
                            tv_id.setErrorEnabled(true);
                            tv_id.setError("UserID Already Exists");
                            tv_id.requestFocus();
                            id.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                }
                                @Override
                                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                    tv_id.setErrorEnabled(false);
                                    tv_id.setError(null);
                                }
                                @Override
                                public void afterTextChanged(Editable editable) {
                                }
                            });
                            return;
                        }
                        else if(_userid.equals("0001")){
                            tv_id.setErrorEnabled(true);
                            tv_id.setError("UserID Already Exists");
                            tv_id.requestFocus();
                            id.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                }
                                @Override
                                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                    tv_id.setErrorEnabled(false);
                                    tv_id.setError(null);
                                }
                                @Override
                                public void afterTextChanged(Editable editable) {
                                }
                            });
                            return;
                        }
                        else{
                            progressDialog = new ProgressDialog(AddUser.this);
                            progressDialog.show();
                            progressDialog.setContentView(R.layout.progress_dialog);
                            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                            String _gethash = Hashing.encrpyt(_password);
                            Map<String, Object> user = new HashMap<>();
                            user.put("UserID", _userid);
                            user.put("FullName", _name);
                            user.put("Email", _email);
                            user.put("MobileNumber", "+91"+_mobile);
                            user.put("Position", _position);
                            user.put("Department", _fdept);
                            user.put("Branch", _fbranch);
                            user.put("Designation", _fdesg);

                            Map<String, Object> login = new HashMap<>();
                            login.put("UserID", _userid);
                            login.put("Password", _gethash);
                            login.put("Position", _position);

                            mStore.collection(_fdept).document(_fbranch).collection("Faculties").document(_userid).set(user)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            mStore.collection("Faculties").document(_userid).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    mStore.collection("Login").document(_userid).set(login).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            id.setText("");
                                                            name.setText("");
                                                            email.setText("");
                                                            password.setText("");
                                                            mobile.setText("");
                                                            Toast.makeText(AddUser.this, "User Added Successfully", Toast.LENGTH_SHORT).show();
                                                            startActivity(new Intent(getApplicationContext(), AdminDashboard.class));
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(AddUser.this, "Failed to Add User", Toast.LENGTH_SHORT).show();
                                                            progressDialog.dismiss();
                                                        }
                                                    });
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(AddUser.this, "Failed to Add User", Toast.LENGTH_SHORT).show();
                                                    progressDialog.dismiss();
                                                }
                                            });
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(AddUser.this, "Failed to Add User", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            });
                        }
                    }
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