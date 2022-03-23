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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
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

public class CreateClass extends AppCompatActivity {

    Spinner department, branch, semester;
    TextInputLayout tv_classroom;
    TextInputEditText classroom;
    ImageView arrow_back;
    Button create_class;
    FirebaseFirestore mStore;
    ArrayList<String> dept_data, branch_data, four_data, six_data, eight_data;
    ArrayAdapter<String> _dept, _branch, _sem;
    String dept_value;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_class);

        department = findViewById(R.id.spn_dept_name);
        branch = findViewById(R.id.spn_branch_name);
        semester = findViewById(R.id.spn_semester);
        tv_classroom = findViewById(R.id.tv_class_name);
        classroom = findViewById(R.id.et_class_name);
        arrow_back = findViewById(R.id.arrow_back);
        create_class = findViewById(R.id.btn_create_class);
        mStore = FirebaseFirestore.getInstance();

        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AdminDashboard.class));
            }
        });

        dept_data = new ArrayList<>();
        mStore.collection("Department").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                dept_data.clear();
                dept_data.add("Select Department");
                for(DocumentSnapshot snapshot : value){
                    dept_data.add(snapshot.getString("Department"));
                }
                _dept.notifyDataSetChanged();
            }
        });
        _dept = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, dept_data);
        department.setAdapter(_dept);

        branch_data = new ArrayList<>();
        department.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                dept_value = adapterView.getSelectedItem().toString();
                mStore.collection( dept_value +" BRANCH").addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        branch_data.clear();
                        branch_data.add("Select Branch");
                        for(DocumentSnapshot snapshot : value){
                            branch_data.add(snapshot.getString(dept_value + " BRANCH"));
                        }
                        _branch.notifyDataSetChanged();
                    }
                });
                _branch = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, branch_data);
                branch.setAdapter(_branch);

                if(dept_value.equals("MTECH") || dept_value.equals("MSC") || dept_value.equals("MCA")){
                    four_data = new ArrayList<>();
                    four_data.add("Select Semester");
                    four_data.add("1");
                    four_data.add("2");
                    four_data.add("3");
                    four_data.add("4");
                    _sem = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, four_data);

                }else if(dept_value.equals("DIPLOMA") || dept_value.equals("BSC") || dept_value.equals("BCA")){
                    six_data = new ArrayList<>();
                    six_data.add("Select Semester");
                    six_data.add("1");
                    six_data.add("2");
                    six_data.add("3");
                    six_data.add("4");
                    six_data.add("5");
                    six_data.add("6");
                    _sem = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, six_data);
                }else{
                    eight_data = new ArrayList<>();
                    eight_data.add("Select Semester");
                    eight_data.add("1");
                    eight_data.add("2");
                    eight_data.add("3");
                    eight_data.add("4");
                    eight_data.add("5");
                    eight_data.add("6");
                    eight_data.add("7");
                    eight_data.add("8");
                    _sem = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, eight_data);
                }
                semester.setAdapter(_sem);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        create_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _dept = department.getSelectedItem().toString();
                String _branch = branch.getSelectedItem().toString();
                String _sem = semester.getSelectedItem().toString();
                String _class = classroom.getText().toString();

                if(_dept.equals("Select Department")){
                    Toast.makeText(CreateClass.this, "Please Select Department", Toast.LENGTH_SHORT).show();
                }
                else if(_branch.equals("Select Branch")){
                    Toast.makeText(CreateClass.this, "Please Select Branch", Toast.LENGTH_SHORT).show();
                }
                else if(_sem.equals("Select Semester")){
                    Toast.makeText(CreateClass.this, "Please Select Semester", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(_class)){
                    tv_classroom.setErrorEnabled(true);
                    tv_classroom.setError("Please Enter Class Name");
                    tv_classroom.requestFocus();
                    classroom.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }
                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            tv_classroom.setErrorEnabled(false);
                            tv_classroom.setError(null);
                        }
                        @Override
                        public void afterTextChanged(Editable editable) {
                        }
                    });
                    return;
                }
                else{
                    progressDialog = new ProgressDialog(CreateClass.this);
                    progressDialog.show();
                    progressDialog.setContentView(R.layout.progress_dialog);
                    progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Map<String, Object> class_room = new HashMap<>();
                    class_room.put("Class", _class);

                    mStore.collection(_dept).document(_branch).collection(_sem).document(_class).set(class_room).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(CreateClass.this, "Class Added Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), AdminDashboard.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(CreateClass.this, "Class Adding Failed", Toast.LENGTH_SHORT).show();
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