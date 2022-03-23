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

public class AddDesignation extends AppCompatActivity {

    TextInputLayout tv_desg_name;
    TextInputEditText desg_name;
    ImageView arrow_back;
    Button add_designation;
    FirebaseFirestore mStore;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_designation);

        tv_desg_name = findViewById(R.id.tv_desg_name);
        desg_name = findViewById(R.id.et_desg_name);
        add_designation = findViewById(R.id.btn_add_desg);
        arrow_back = findViewById(R.id.arrow_back);
        mStore = FirebaseFirestore.getInstance();

        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AdminDashboard.class));
            }
        });

        add_designation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _desg = desg_name.getText().toString();

                if(TextUtils.isEmpty(_desg)){
                    tv_desg_name.setErrorEnabled(true);
                    tv_desg_name.setError("Please Enter Designation Name");
                    tv_desg_name.requestFocus();
                    desg_name.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }
                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            tv_desg_name.setErrorEnabled(false);
                            tv_desg_name.setError(null);
                        }
                        @Override
                        public void afterTextChanged(Editable editable) {
                        }
                    });
                    return;
                }
                else{
                    progressDialog = new ProgressDialog(AddDesignation.this);
                    progressDialog.show();
                    progressDialog.setContentView(R.layout.progress_dialog);
                    progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Map<String, Object> desg_data = new HashMap<>();
                    desg_data.put("Designation", _desg);

                    mStore.collection("Designation").document(_desg).set(desg_data).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            desg_name.setText("");
                            Toast.makeText(AddDesignation.this, "Designation Added Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), AdminDashboard.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            desg_name.setText("");
                            Toast.makeText(AddDesignation.this, "Failed to Add Designation", Toast.LENGTH_SHORT).show();
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