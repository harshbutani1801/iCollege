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

public class UserProfile extends AppCompatActivity {

    Session Session;
    String uid, user_type;
    ImageView arrow_back;
    TextView user_id, user_name, user_position, user_email, user_mobile, user_dept, user_branch, user_desg;
    FirebaseFirestore mStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

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

        uid = Session.getuserid();
        user_type = Session.getusertype();

        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ProfilePage.class));
            }
        });

        if(user_type.equals("Admin")){
            user_id.setText(uid);
            user_name.setText(user_type);
        }else{
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
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), ProfilePage.class));
    }
}