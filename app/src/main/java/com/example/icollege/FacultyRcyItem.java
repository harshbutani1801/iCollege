package com.example.icollege;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class FacultyRcyItem extends AppCompatActivity {

    TextView id, name, email, mobile, position, dept, branch, desg;
    FirebaseFirestore mStore;
    String userid;
    ImageView arrow_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_rcy_item);

        id = findViewById(R.id.tv_userid);
        name = findViewById(R.id.tv_username);
        email = findViewById(R.id.tv_useremail);
        mobile = findViewById(R.id.tv_usermobile);
        position = findViewById(R.id.tv_userposition);
        dept = findViewById(R.id.tv_userdept);
        branch = findViewById(R.id.tv_userbranch);
        desg = findViewById(R.id.tv_userdesg);
        arrow_back = findViewById(R.id.arrow_back);
        mStore = FirebaseFirestore.getInstance();

        userid = getIntent().getStringExtra("UserID");

        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), FacultyPage.class));
            }
        });

        mStore.collection("Faculties").whereEqualTo("UserID", userid).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                                id.setText(queryDocumentSnapshot.getString("UserID"));
                                name.setText(queryDocumentSnapshot.getString("FullName"));
                                email.setText(queryDocumentSnapshot.getString("Email"));
                                mobile.setText(queryDocumentSnapshot.getString("MobileNumber"));
                                position.setText(queryDocumentSnapshot.getString("Position"));
                                dept.setText(queryDocumentSnapshot.getString("Department"));
                                branch.setText(queryDocumentSnapshot.getString("Branch"));
                                desg.setText(queryDocumentSnapshot.getString("Designation"));
                            }
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), FacultyPage.class));
    }
}