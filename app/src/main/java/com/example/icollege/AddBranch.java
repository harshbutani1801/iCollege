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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddBranch extends AppCompatActivity {

    TextInputEditText branch_name;
    TextInputLayout tv_branch_name;
    ImageView arrow_back;
    Button add_branch;
    FirebaseFirestore mStore;
    Spinner department;
    ArrayList<String> dept;
    ArrayAdapter<String> _dept;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_branch);

        branch_name = findViewById(R.id.et_branch_name);
        tv_branch_name = findViewById(R.id.tv_branch_name);
        add_branch = findViewById(R.id.btn_add_branch);
        department = findViewById(R.id.spn_dept);
        arrow_back = findViewById(R.id.arrow_back);
        mStore = FirebaseFirestore.getInstance();

        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AdminDashboard.class));
            }
        });

        dept = new ArrayList<>();
        mStore.collection("Department").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                dept.clear();
                dept.add("Select Department");
                for(DocumentSnapshot snapshot : value){
                    dept.add(snapshot.getString("Department"));
                }
                _dept.notifyDataSetChanged();
            }
        });
        _dept = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, dept);
        department.setAdapter(_dept);

        add_branch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _dept_data = department.getSelectedItem().toString();
                String _branch_name = branch_name.getText().toString();

                if(_dept_data.equals("Select Department")){
                    Toast.makeText(AddBranch.this, "Please Select Department", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(_branch_name)) {
                    tv_branch_name.setErrorEnabled(true);
                    tv_branch_name.setError("Please Enter Branch Name");
                    tv_branch_name.requestFocus();
                    branch_name.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }
                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            tv_branch_name.setErrorEnabled(false);
                            tv_branch_name.setError(null);
                        }
                        @Override
                        public void afterTextChanged(Editable editable) {
                        }
                    });
                    return;
                }
                else{
                    progressDialog = new ProgressDialog(AddBranch.this);
                    progressDialog.show();
                    progressDialog.setContentView(R.layout.progress_dialog);
                    progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Map<String, Object> branch_data = new HashMap<>();
                    branch_data.put(_dept_data + " BRANCH", _branch_name);

                    mStore.collection(_dept_data + " BRANCH").document(_branch_name).set(branch_data)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    branch_name.setText("");
                                    Toast.makeText(AddBranch.this, "Branch Added Successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), AdminDashboard.class));
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            branch_name.setText("");
                            Toast.makeText(AddBranch.this, "Failed to Add Branch", Toast.LENGTH_SHORT).show();
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