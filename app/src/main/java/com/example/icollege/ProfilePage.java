package com.example.icollege;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ProfilePage extends AppCompatActivity {

    Session Session;
    TextView name, userid, email, department, logout;
    Button profile, upd_profile, change_password;
    FirebaseFirestore mStore;
    String uid, usertype;
    ImageView arrow_back;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        Session = new Session(getApplicationContext());
        name = findViewById(R.id.tv_name);
        userid = findViewById(R.id.tv_userid);
        email = findViewById(R.id.tv_email);
        department = findViewById(R.id.tv_department);
        logout = findViewById(R.id.tv_logout);
        arrow_back = findViewById(R.id.arrow_back);
        profile = findViewById(R.id.btn_profile);
        upd_profile = findViewById(R.id.btn_update_profile);
        change_password = findViewById(R.id.btn_change_password);
        mStore = FirebaseFirestore.getInstance();

        uid = Session.getuserid();
        usertype = Session.getusertype();

        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(usertype.equals("Admin")){
                    startActivity(new Intent(getApplicationContext(), AdminDashboard.class));
                }else if(usertype.equals("Faculty")){
                    startActivity(new Intent(getApplicationContext(), FacultyDashboard.class ));
                }else if(usertype.equals("Head")){
                    startActivity(new Intent(getApplicationContext(), HeadDashboard.class ));
                }else {
                    startActivity(new Intent(getApplicationContext(), PrincipalDashboard.class ));
                }
            }
        });

        if(usertype.equals("Admin")){
            name.setText(usertype);
            userid.setText(uid);

            upd_profile.setVisibility(View.INVISIBLE);
            change_password.setVisibility(View.INVISIBLE);
        }else{
            mStore.collection("Faculties").whereEqualTo("UserID", uid).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for(QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                            name.setText(queryDocumentSnapshot.getString("FullName"));
                            userid.setText(queryDocumentSnapshot.getString("UserID"));
                            email.setText(queryDocumentSnapshot.getString("Email"));
                            department.setText(queryDocumentSnapshot.getString("Department"));
                        }
                    }
                }
            });
        }

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), UserProfile.class));
            }
        });

        upd_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), UpdateProfile.class));
            }
        });

        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ChangePassword.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Session.setlogin(false);
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(usertype.equals("Admin")){
            startActivity(new Intent(getApplicationContext(), AdminDashboard.class));
        }else if(usertype.equals("Faculty")){
            startActivity(new Intent(getApplicationContext(), FacultyDashboard.class ));
        }else if(usertype.equals("Head")){
            startActivity(new Intent(getApplicationContext(), HeadDashboard.class ));
        }else {
            startActivity(new Intent(getApplicationContext(), PrincipalDashboard.class ));
        }
    }
}