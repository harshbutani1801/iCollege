 package com.example.icollege;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {

    Session Session;
    TextInputLayout tv_id, tv_password;
    TextInputEditText userid, password;
    TextView forgot;
    //Button login;
    MaterialButton login;
    String uid, pass, position, user0 = "Admin", user1 = "Faculty", user2 = "Head", user3 = "Principal";
    FirebaseFirestore mStore;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Session = new Session(getApplicationContext());
        userid = findViewById(R.id.et_userid);
        password = findViewById(R.id.et_password);
        forgot = findViewById(R.id.tv_forgot);
        login = findViewById(R.id.btn_login);
        tv_id = findViewById(R.id.tv_userid);
        tv_password = findViewById(R.id.tv_password);
        mStore = FirebaseFirestore.getInstance();

        userid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.equals("")){
                    mStore.collection("Login").whereEqualTo("UserID", userid.getText().toString())
                            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for(QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                                    uid = queryDocumentSnapshot.getString("UserID");
                                    pass = queryDocumentSnapshot.getString("Password");
                                    position = queryDocumentSnapshot.getString("Position");
                                }
                            }
                        }
                    });
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), OTPActivity.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _userid = userid.getText().toString();
                String _password = password.getText().toString();

                if(TextUtils.isEmpty(_userid)){
                    tv_id.setErrorEnabled(true);
                    tv_id.setError("Please Enter EMP ID");
                    tv_id.requestFocus();
                    userid.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }
                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            tv_id.setErrorEnabled(false);
                            tv_id.setError(null);
                        }
                        @Override
                        public void afterTextChanged(Editable editable) {
                        }
                    });
                    return;

                }
                else if(TextUtils.isEmpty(_password)){
                    tv_password.setErrorEnabled(true);
                    tv_password.setError("Please Enter Password");
                    tv_password.requestFocus();
                    password.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }
                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            tv_password.setErrorEnabled(false);
                            tv_password.setError(null);
                        }
                        @Override
                        public void afterTextChanged(Editable editable) {
                        }
                    });
                    return;
                }
//                else if(password.length() < 8){
//                    tv_password.setErrorEnabled(true);
//                    tv_password.setError("Minimum 8 Characters Required");
//                    tv_password.requestFocus();
//                    password.addTextChangedListener(new TextWatcher() {
//                        @Override
//                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                        }
//                        @Override
//                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                            tv_password.setErrorEnabled(false);
//                            tv_password.setError(null);
//                        }
//                        @Override
//                        public void afterTextChanged(Editable editable) {
//                        }
//                    });
//                    return;
//                }
                else{
                    progressDialog = new ProgressDialog(LoginActivity.this);
                    progressDialog.show();
                    progressDialog.setContentView(R.layout.progress_dialog);
                    progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    String hashpassword = Hashing.encrpyt(_password);
                    if(_userid.equals("0001") && _password.equals("Admin@01") && user0.equals("Admin")){
                        userid.setText("");
                        password.setText("");
                        Session.setlogin(true);
                        Session.setuserid(_userid);
                        Session.setusertype(user0);
                        //Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), AdminDashboard.class));
                    }else if(_userid.equals(uid) && hashpassword.equals(pass) && user1.equals(position)){
                        userid.setText("");
                        password.setText("");
                        Session.setlogin(true);
                        Session.setuserid(_userid);
                        Session.setusertype(user1);
                        //Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), FacultyDashboard.class));
                    }else if(_userid.equals(uid) && hashpassword.equals(pass) && user2.equals(position)){
                        userid.setText("");
                        password.setText("");
                        Session.setlogin(true);
                        Session.setuserid(_userid);
                        Session.setusertype(user2);
                        //Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), HeadDashboard.class));
                    }else if(_userid.equals(uid) && hashpassword.equals(pass) && user3.equals(position)){
                        userid.setText("");
                        password.setText("");
                        Session.setlogin(true);
                        Session.setuserid(_userid);
                        Session.setusertype(user3);
                        //Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), PrincipalDashboard.class));
                    }else{
                        progressDialog.dismiss();

                        userid.setText("");
                        password.setText("");
                        tv_id.setErrorEnabled(true);
                        tv_id.setError(" ");
                        tv_password.setErrorEnabled(true);
                        tv_password.setError("Invalid EMP ID or Password");
//                        userid.setHint("EMP ID");
//                        userid.setHintTextColor(Color.RED);
//                        password.setHint("PASSWORD");
//                        password.setHintTextColor(Color.RED);
//                        Toast.makeText(LoginActivity.this, "Invalid UserID or Password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        if(Session.getlogin() && Session.getusertype().equals(user0)){
            startActivity(new Intent(getApplicationContext(), AdminDashboard.class));
        }else if(Session.getlogin() && Session.getusertype().equals(user1)){
            startActivity(new Intent(getApplicationContext(), FacultyDashboard.class));
        }else if(Session.getlogin() && Session.getusertype().equals(user2)){
            startActivity(new Intent(getApplicationContext(), HeadDashboard.class));
        }else if(Session.getlogin() && Session.getusertype().equals(user3)){
            startActivity(new Intent(getApplicationContext(), PrincipalDashboard.class));
        }else{

        }
    }
}