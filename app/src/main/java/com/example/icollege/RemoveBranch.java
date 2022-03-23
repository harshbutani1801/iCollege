package com.example.icollege;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class RemoveBranch extends AppCompatActivity {

    Spinner department, branch;
    ImageView arrow_back;
    Button delete_branch;
    FirebaseFirestore mStore;
    ArrayList<String> dept, branch_spn;
    ArrayAdapter<String> _dept, _branch;
    String dept_value;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_branch);

        department = findViewById(R.id.spn_dept);
        branch = findViewById(R.id.spn_branch);
        delete_branch = findViewById(R.id.btn_delete_branch);
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

        branch_spn = new ArrayList<>();
        department.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                dept_value = adapterView.getSelectedItem().toString();

                mStore.collection(dept_value + " BRANCH").addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        branch_spn.clear();
                        branch_spn.add("Select Branch");
                        for(DocumentSnapshot snapshot : value){
                            branch_spn.add(snapshot.getString(dept_value + " BRANCH"));
                        }
                        _branch.notifyDataSetChanged();
                    }
                });
                _branch = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, branch_spn);
                branch.setAdapter(_branch);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        delete_branch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _dept_branch = department.getSelectedItem().toString();
                String _branch_data = branch.getSelectedItem().toString();

                if(_dept_branch.equals("Select Department")){
                    Toast.makeText(RemoveBranch.this, "Please Select Department", Toast.LENGTH_SHORT).show();
                }
                else if(_branch_data.equals("Select Branch")){
                    Toast.makeText(RemoveBranch.this, "Please Select Branch", Toast.LENGTH_SHORT).show();
                }
                else{
                    new AlertDialog.Builder(RemoveBranch.this, R.style.AlertDialog)
                            .setIcon(R.drawable.delete)
                            .setTitle("Delete Branch")
                            .setMessage("Are you sure you want to delete " + _branch_data + " Branch")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    progressDialog = new ProgressDialog(RemoveBranch.this);
                                    progressDialog.show();
                                    progressDialog.setContentView(R.layout.progress_dialog);
                                    progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                                    mStore.collection(_dept_branch + " BRANCH").document(_branch_data).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(RemoveBranch.this, "Branch Removed Successfully", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(), AdminDashboard.class));
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(RemoveBranch.this, "Failed to Remove Branch", Toast.LENGTH_SHORT).show();
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
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), AdminDashboard.class));
    }
}