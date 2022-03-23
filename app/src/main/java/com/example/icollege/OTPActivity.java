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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.concurrent.TimeUnit;

public class OTPActivity extends AppCompatActivity {

    TextInputEditText user_id;
    Button get_otp;
    FirebaseFirestore mStore;
    String user_mobile, userid;
    ProgressDialog progressDialog;
    ImageView arrow_back;
    TextInputLayout tv_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p);

        user_id = findViewById(R.id.et_user_id);
        get_otp = findViewById(R.id.btn_get_otp);
        arrow_back = findViewById(R.id.arrow_back);
        tv_id = findViewById(R.id.tv_user_id);
        mStore = FirebaseFirestore.getInstance();

        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

        user_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.equals("")){
                    mStore.collection("Faculties").whereEqualTo("UserID", user_id.getText().toString())
                            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for(QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                                    userid = queryDocumentSnapshot.getString("UserID");
                                    user_mobile = queryDocumentSnapshot.getString("MobileNumber");
                                }
                            }
                        }
                    });
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        get_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _user_id = user_id.getText().toString();

                if(TextUtils.isEmpty(_user_id)){
                    tv_id.setErrorEnabled(true);
                    tv_id.setError("Please Enter Employee ID");
                    tv_id.requestFocus();
                    user_id.addTextChangedListener(new TextWatcher() {
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
                }else{
                    if(_user_id.equals(userid)){
                        progressDialog = new ProgressDialog(OTPActivity.this);
                        progressDialog.show();
                        progressDialog.setContentView(R.layout.progress_dialog);
                        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                        PhoneAuthProvider.verifyPhoneNumber(
                                PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
                                        .setActivity(OTPActivity.this)
                                        .setPhoneNumber(user_mobile)
                                        .setTimeout(60L, TimeUnit.SECONDS)
                                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                            @Override
                                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                                            }

                                            @Override
                                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                                progressDialog.dismiss();
                                                Toast.makeText(OTPActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }

                                            @Override
                                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                                progressDialog.dismiss();
                                                get_otp.setVisibility(View.VISIBLE);

                                                Intent otp = new Intent(OTPActivity.this, OTPVerify.class);
                                                otp.putExtra("MobileNo", user_mobile);
                                                otp.putExtra("OTP_Code", s);
                                                otp.putExtra("UserID", _user_id);
                                                user_id.setText("");
                                                startActivity(otp);
                                            }
                                        }).build()
                        );
                    }else{
                        Toast.makeText(OTPActivity.this, "Invalid EMP ID", Toast.LENGTH_SHORT).show();
                        user_id.setText("");
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }
}