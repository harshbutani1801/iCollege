package com.example.icollege;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class ChangePassword extends AppCompatActivity {

    Session Session;
    ImageView arrow_back;
    TextInputLayout tv_current_pass, tv_new_pass, tv_re_pass;
    TextInputEditText current_pass, new_pass, re_pass;
    FirebaseFirestore mStore;
    String uid, usertype, _current_pass2;
    ProgressDialog progressDialog;
    Button change_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        Session = new Session(getApplicationContext());
        current_pass = findViewById(R.id.et_current_pass);
        new_pass = findViewById(R.id.et_new_pass);
        re_pass = findViewById(R.id.et_new_repass);
        tv_current_pass = findViewById(R.id.tv_current_pass);
        tv_new_pass = findViewById(R.id.tv_new_pass);
        tv_re_pass = findViewById(R.id.tv_new_repass);
        change_password = findViewById(R.id.btn_change_password);
        arrow_back = findViewById(R.id.arrow_back);
        mStore = FirebaseFirestore.getInstance();

        uid = Session.getuserid();
        usertype = Session.getusertype();

        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ProfilePage.class));
            }
        });

        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _current_pass = current_pass.getText().toString();
                String _new_pass = new_pass.getText().toString();
                String _re_pass = re_pass.getText().toString();

                String get_current_hash = Hashing.encrpyt(_current_pass);

                if (TextUtils.isEmpty(_current_pass)) {
                    tv_current_pass.setErrorEnabled(true);
                    tv_current_pass.setError("Please Enter Current Password");
                    tv_current_pass.requestFocus();
                    current_pass.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }
                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            tv_current_pass.setErrorEnabled(false);
                            tv_current_pass.setError(null);
                        }
                        @Override
                        public void afterTextChanged(Editable editable) {
                        }
                    });
                    return;
                }else if(_current_pass.length() < 8){
                    tv_current_pass.setErrorEnabled(true);
                    tv_current_pass.setError("Minimum 8 Characters Required");
                    tv_current_pass.requestFocus();
                    current_pass.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }
                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            tv_current_pass.setErrorEnabled(false);
                            tv_current_pass.setError(null);
                        }
                        @Override
                        public void afterTextChanged(Editable editable) {
                        }
                    });
                    return;
                }else if(TextUtils.isEmpty(_new_pass)){
                    tv_new_pass.setErrorEnabled(true);
                    tv_new_pass.setError("Please Enter New Password");
                    tv_new_pass.requestFocus();
                    new_pass.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }
                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            tv_new_pass.setErrorEnabled(false);
                            tv_new_pass.setError(null);
                        }
                        @Override
                        public void afterTextChanged(Editable editable) {
                        }
                    });
                    return;
                }else if(new_pass.length() < 8){
                    tv_new_pass.setErrorEnabled(true);
                    tv_new_pass.setError("Minimum 8 Characters Required");
                    tv_new_pass.requestFocus();
                    new_pass.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }
                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            tv_new_pass.setErrorEnabled(false);
                            tv_new_pass.setError(null);
                        }
                        @Override
                        public void afterTextChanged(Editable editable) {
                        }
                    });
                    return;
                }else if(TextUtils.isEmpty(_re_pass)){
                    tv_re_pass.setErrorEnabled(true);
                    tv_re_pass.setError("Please Re-Type Password");
                    tv_re_pass.requestFocus();
                    re_pass.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }
                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            tv_re_pass.setErrorEnabled(false);
                            tv_re_pass.setError(null);
                        }
                        @Override
                        public void afterTextChanged(Editable editable) {
                        }
                    });
                    return;
                }else if(re_pass.length() < 8){
                    tv_re_pass.setErrorEnabled(true);
                    tv_re_pass.setError("Minimum 8 Characters Required");
                    tv_re_pass.requestFocus();
                    re_pass.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }
                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            tv_re_pass.setErrorEnabled(false);
                            tv_re_pass.setError(null);
                        }
                        @Override
                        public void afterTextChanged(Editable editable) {
                        }
                    });
                    return;
                }else{
                    mStore.collection("Login").whereEqualTo("UserID", uid).get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if(task.isSuccessful()){
                                        for(QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                                            _current_pass2 = queryDocumentSnapshot.getString("Password");
                                        }
                                    }
                                }
                            });

                    if(get_current_hash.equals(_current_pass2)){
                        if(_new_pass.equals(_re_pass)){
                            progressDialog = new ProgressDialog(ChangePassword.this);
                            progressDialog.show();
                            progressDialog.setContentView(R.layout.progress_dialog);
                            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                            String get_re_hash = Hashing.encrpyt(_re_pass);

                            Map<String, Object> Password_change = new HashMap<>();
                            Password_change.put("Password", get_re_hash);

                            mStore.collection("Login").document(uid).update(Password_change).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(ChangePassword.this, "Password Changed Successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), ProfilePage.class));
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ChangePassword.this, "Failed to Change Password", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            });
                        }else{
                            Toast.makeText(ChangePassword.this, "New Password Not Matched!", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        current_pass.setError("Current Password Not Matched!");
                        current_pass.requestFocus();
                        return;
                    }
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