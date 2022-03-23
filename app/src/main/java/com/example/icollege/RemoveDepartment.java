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

public class RemoveDepartment extends AppCompatActivity {

    Spinner department;
    ImageView arrow_back;
    Button delete_department;
    FirebaseFirestore mStore;
    ArrayList<String> dept;
    ArrayAdapter<String> _dept;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_department);

        department = findViewById(R.id.spn_dept);
        delete_department = findViewById(R.id.btn_delete_department);
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

        delete_department.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _dept_data = department.getSelectedItem().toString();

                if(_dept_data.equals("Select Department")){
                    Toast.makeText(RemoveDepartment.this, "Please Select Department", Toast.LENGTH_SHORT).show();
                }
                else{
                    new AlertDialog.Builder(RemoveDepartment.this, R.style.AlertDialog)
                            .setIcon(R.drawable.delete)
                            .setTitle("Delete Department")
                            .setMessage("Are you sure you want to delete " + _dept_data + " Department")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    progressDialog = new ProgressDialog(RemoveDepartment.this);
                                    progressDialog.show();
                                    progressDialog.setContentView(R.layout.progress_dialog);
                                    progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                                    mStore.collection("Department").document(_dept_data).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(RemoveDepartment.this, "Department Removed Successfully", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(), AdminDashboard.class));
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(RemoveDepartment.this, "Failed to Remove Department", Toast.LENGTH_SHORT).show();
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