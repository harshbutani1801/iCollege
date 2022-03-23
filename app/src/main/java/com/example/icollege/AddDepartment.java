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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddDepartment extends AppCompatActivity {

    ImageView arrow_back;
    Button add_department;
    TextInputLayout tv_dept_name;
    TextInputEditText dept_name;
    FirebaseFirestore mStore;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_department);

        dept_name = findViewById(R.id.et_dept_name);
        tv_dept_name = findViewById(R.id.tv_dept_name);
        add_department = findViewById(R.id.btn_add_department);
        arrow_back = findViewById(R.id.arrow_back);
        mStore = FirebaseFirestore.getInstance();

        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AdminDashboard.class));
            }
        });

        add_department.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _dept_name = dept_name.getText().toString();

                if(TextUtils.isEmpty(_dept_name)) {
                    tv_dept_name.setErrorEnabled(true);
                    tv_dept_name.setError("Please Enter Department Name");
                    tv_dept_name.requestFocus();
                    dept_name.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }
                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            tv_dept_name.setErrorEnabled(false);
                            tv_dept_name.setError(null);
                        }
                        @Override
                        public void afterTextChanged(Editable editable) {
                        }
                    });
                    return;
                }
                else{
                    progressDialog = new ProgressDialog(AddDepartment.this);
                    progressDialog.show();
                    progressDialog.setContentView(R.layout.progress_dialog);
                    progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Map<String, Object> dept_data = new HashMap<>();
                    dept_data.put("Department", _dept_name);

                    mStore.collection("Department").document(_dept_name).set(dept_data).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            dept_name.setText("");
                            Toast.makeText(AddDepartment.this, "Department Added Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), AdminDashboard.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            dept_name.setText("");
                            Toast.makeText(AddDepartment.this, "Failed to Add Department", Toast.LENGTH_SHORT).show();
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