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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateNewPassword extends AppCompatActivity {

    TextInputLayout tv_new_pass, tv_new_re_pass;
    TextInputEditText new_pass, re_new_pass;
    Button chg_pass;
    FirebaseFirestore mStore;
    String get_uid;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_password);

        tv_new_pass = findViewById(R.id.tv_new_pass);
        new_pass = findViewById(R.id.et_new_pass);
        tv_new_re_pass = findViewById(R.id.tv_new_re_pass);
        re_new_pass = findViewById(R.id.et_new_re_pass);
        chg_pass = findViewById(R.id.btn_new_pass);
        mStore = FirebaseFirestore.getInstance();

        get_uid = getIntent().getStringExtra("UserID");

        chg_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _new_pass = new_pass.getText().toString();
                String _re_new_pass = re_new_pass.getText().toString();
                String _re_hash_pass = Hashing.encrpyt(_re_new_pass);

                if(TextUtils.isEmpty(_new_pass)){
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
                }else if(_new_pass.length() < 8){
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
                }else if(TextUtils.isEmpty(_re_new_pass)){
                    tv_new_re_pass.setErrorEnabled(true);
                    tv_new_re_pass.setError("Please Re-Enter New Password");
                    tv_new_re_pass.requestFocus();
                    re_new_pass.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }
                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            tv_new_re_pass.setErrorEnabled(false);
                            tv_new_re_pass.setError(null);
                        }
                        @Override
                        public void afterTextChanged(Editable editable) {
                        }
                    });
                    return;
                }else if(_re_new_pass.length() < 8){
                    tv_new_re_pass.setErrorEnabled(true);
                    tv_new_re_pass.setError("Minimum 8 Characters Required");
                    tv_new_re_pass.requestFocus();
                    re_new_pass.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }
                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            tv_new_re_pass.setErrorEnabled(false);
                            tv_new_re_pass.setError(null);
                        }
                        @Override
                        public void afterTextChanged(Editable editable) {
                        }
                    });
                    return;
                }else{
                    progressDialog = new ProgressDialog(CreateNewPassword.this);
                    progressDialog.show();
                    progressDialog.setContentView(R.layout.progress_dialog);
                    progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Map<String, Object> new_pass = new HashMap<>();
                    new_pass.put("Password", _re_hash_pass);

                    mStore.collection("Login").document(get_uid).update(new_pass).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(CreateNewPassword.this, "Password Changed Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(CreateNewPassword.this, "Password Changed Unsuccessfully", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
    }
}