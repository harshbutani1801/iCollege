package com.example.icollege;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class UpdateProfile extends AppCompatActivity {

    Session Session;
    TextView userid, username, usertype, userdept, userbranch, userdesg;
    TextInputLayout tv_useremail, tv_usermobile;
    TextInputEditText useremail, usermobile;
    ImageView arrow_back;
    String uid, user_type, user0 = "Admin", user1 = "Head", user2 = "Faculty", user3 = "Principal";
    FirebaseFirestore mStore;
    Button update_profile;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        Session = new Session(getApplicationContext());
        userid = findViewById(R.id.tv_userid);
        username = findViewById(R.id.tv_username);
        usertype = findViewById(R.id.tv_usertype);
        useremail = findViewById(R.id.et_useremail);
        usermobile = findViewById(R.id.et_usermobile);
        tv_useremail = findViewById(R.id.tv_useremail);
        tv_usermobile = findViewById(R.id.tv_usermobile);
        userdept = findViewById(R.id.tv_userdept);
        userbranch = findViewById(R.id.tv_userbranch);
        userdesg = findViewById(R.id.tv_userdesg);
        update_profile = findViewById(R.id.btn_update_profile);
        arrow_back = findViewById(R.id.arrow_back);
        mStore = FirebaseFirestore.getInstance();

        uid = Session.getuserid();

        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ProfilePage.class));
            }
        });

        mStore.collection("Faculties").whereEqualTo("UserID", uid).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                        userid.setText(queryDocumentSnapshot.getString("UserID"));
                        username.setText(queryDocumentSnapshot.getString("FullName"));
                        usertype.setText(queryDocumentSnapshot.getString("Position"));
                        useremail.setText(queryDocumentSnapshot.getString("Email"));
                        usermobile.setText(queryDocumentSnapshot.getString("MobileNumber"));
                        userdept.setText(queryDocumentSnapshot.getString("Department"));
                        userbranch.setText(queryDocumentSnapshot.getString("Branch"));
                        userdesg.setText(queryDocumentSnapshot.getString("Designation"));
                    }
                }
            }
        });

        update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _email = useremail.getText().toString();
                String _email_patten = "[a-zA-Z0-9_-]+@[a-z]+\\.+[a-z]";
                String _mobile = usermobile.getText().toString();
                String _dept = userdept.getText().toString();
                String _branch = userbranch.getText().toString();

                if(TextUtils.isEmpty(_email)){
                    tv_useremail.setErrorEnabled(true);
                    tv_useremail.setError("Please Enter User Email");
                    tv_useremail.requestFocus();
                    useremail.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }
                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            tv_useremail.setErrorEnabled(false);
                            tv_useremail.setError(null);
                        }
                        @Override
                        public void afterTextChanged(Editable editable) {
                        }
                    });
                    return;
                }
                else if(_email.matches(_email_patten)){
                    tv_useremail.setErrorEnabled(true);
                    tv_useremail.setError("Invalid Email Address");
                    tv_useremail.requestFocus();
                    useremail.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }
                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            tv_useremail.setErrorEnabled(false);
                            tv_useremail.setError(null);
                        }
                        @Override
                        public void afterTextChanged(Editable editable) {
                        }
                    });
                    return;
                }
                else if(TextUtils.isEmpty(_mobile)){
                    tv_usermobile.setErrorEnabled(true);
                    tv_usermobile.setError("Please Enter Mobile Number");
                    tv_usermobile.requestFocus();
                    usermobile.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }
                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            tv_usermobile.setErrorEnabled(false);
                            tv_usermobile.setError(null);
                        }
                        @Override
                        public void afterTextChanged(Editable editable) {
                        }
                    });
                    return;
                }
                else if(_mobile.length() != 13){
                    tv_usermobile.setErrorEnabled(true);
                    tv_usermobile.setError("10 Digits Required");
                    tv_usermobile.requestFocus();
                    usermobile.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }
                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            tv_usermobile.setErrorEnabled(false);
                            tv_usermobile.setError(null);
                        }
                        @Override
                        public void afterTextChanged(Editable editable) {
                        }
                    });
                    return;
                }
                else{
                    progressDialog = new ProgressDialog(UpdateProfile.this);
                    progressDialog.show();
                    progressDialog.setContentView(R.layout.progress_dialog);
                    progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Map<String, Object> user = new HashMap<>();
                    user.put("Email", _email);
                    user.put("MobileNumber", _mobile);

                    mStore.collection("Faculties").document(uid).update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            mStore.collection(_dept).document(_branch).collection("Faculties").document(uid).update(user)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(UpdateProfile.this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(), ProfilePage.class));
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(UpdateProfile.this, "Profile Updating Failed", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UpdateProfile.this, "Profile Updating Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), ProfilePage.class));
    }
}