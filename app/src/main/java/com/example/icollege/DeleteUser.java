package com.example.icollege;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class DeleteUser extends AppCompatActivity {

    Session Session;
    String uid;
    ImageView arrow_back;
    TextView user_id, user_name, user_position, user_email, user_mobile, user_dept, user_branch, user_desg;
    FirebaseFirestore mStore;
    Button user_delete;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user);

        Session = new Session(getApplicationContext());
        mStore = FirebaseFirestore.getInstance();
        user_id = findViewById(R.id.tv_user_id);
        user_name = findViewById(R.id.tv_user_name);
        user_position = findViewById(R.id.tv_user_position);
        user_email = findViewById(R.id.tv_user_email);
        user_mobile = findViewById(R.id.tv_user_mobile);
        user_dept = findViewById(R.id.tv_user_dept);
        user_branch = findViewById(R.id.tv_user_branch);
        user_desg = findViewById(R.id.tv_user_desg);
        arrow_back = findViewById(R.id.arrow_back);
        user_delete = findViewById(R.id.btn_user_delete);

        uid = getIntent().getStringExtra("Userid");

        mStore.collection("Faculties").whereEqualTo("UserID", uid).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                        user_id.setText(queryDocumentSnapshot.getString("UserID"));
                        user_name.setText(queryDocumentSnapshot.getString("FullName"));
                        user_position.setText(queryDocumentSnapshot.getString("Position"));
                        user_email.setText(queryDocumentSnapshot.getString("Email"));
                        user_mobile.setText(queryDocumentSnapshot.getString("MobileNumber"));
                        user_dept.setText(queryDocumentSnapshot.getString("Department"));
                        user_branch.setText(queryDocumentSnapshot.getString("Branch"));
                        user_desg.setText(queryDocumentSnapshot.getString("Designation"));
                    }
                }
            }
        });

        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AdminDashboard.class));
            }
        });

        user_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _user_dept = user_dept.getText().toString();
                String _user_branch = user_branch.getText().toString();

                new AlertDialog.Builder(DeleteUser.this, R.style.AlertDialog)
                        .setIcon(R.drawable.delete)
                        .setTitle("Delete User")
                        .setMessage("Are you sure you want to delete UserID: " + uid)
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                progressDialog = new ProgressDialog(DeleteUser.this);
                                progressDialog.show();
                                progressDialog.setContentView(R.layout.progress_dialog);
                                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                                mStore.collection("Faculties").document(uid).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        mStore.collection(_user_dept).document(_user_branch).collection("Faculties").document(uid).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                mStore.collection("Login").document(uid).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Toast.makeText(DeleteUser.this, "User Deleted Successfully", Toast.LENGTH_SHORT).show();
                                                        startActivity(new Intent(getApplicationContext(), AdminDashboard.class));
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(DeleteUser.this, "User Deletion Failed", Toast.LENGTH_SHORT).show();
                                                        progressDialog.dismiss();
                                                    }
                                                });
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(DeleteUser.this, "User Deletion Failed", Toast.LENGTH_SHORT).show();
                                                progressDialog.dismiss();
                                            }
                                        });
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(DeleteUser.this, "User Deletion Failed", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    }
                                });
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Do Nothing
                    }
                }).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), AdminDashboard.class));
    }
}