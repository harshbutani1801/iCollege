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

public class RemoveDesignation extends AppCompatActivity {

    Spinner designation;
    ImageView arrow_back;
    Button delete_designation;
    FirebaseFirestore mStore;
    ArrayList<String> desg;
    ArrayAdapter<String> _desg;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_designation);

        designation = findViewById(R.id.spn_desg);
        delete_designation = findViewById(R.id.btn_delete_designation);
        arrow_back = findViewById(R.id.arrow_back);
        mStore = FirebaseFirestore.getInstance();

        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AdminDashboard.class));
            }
        });

        desg = new ArrayList<>();
        mStore.collection("Designation").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                desg.clear();
                desg.add("Select Designation");
                for(DocumentSnapshot snapshot : value){
                    desg.add(snapshot.getString("Designation"));
                }
                _desg.notifyDataSetChanged();
            }
        });
        _desg = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, desg);
        designation.setAdapter(_desg);

        delete_designation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _desg_data = designation.getSelectedItem().toString();

                if(_desg_data.equals("Select Department")){
                    Toast.makeText(RemoveDesignation.this, "Please Select Department", Toast.LENGTH_SHORT).show();
                }
                else{
                    new AlertDialog.Builder(RemoveDesignation.this, R.style.AlertDialog)
                            .setIcon(R.drawable.delete)
                            .setTitle("Delete Designation")
                            .setMessage("Are you sure you want to delete " + _desg_data + " Designation")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    progressDialog = new ProgressDialog(RemoveDesignation.this);
                                    progressDialog.show();
                                    progressDialog.setContentView(R.layout.progress_dialog);
                                    progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                                    mStore.collection("Designation").document(_desg_data).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(RemoveDesignation.this, "Designation Removed Successfully", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(), AdminDashboard.class));
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(RemoveDesignation.this, "Failed to Removed Designation", Toast.LENGTH_SHORT).show();
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