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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.concurrent.TimeUnit;

public class OTPVerify extends AppCompatActivity {

    EditText otp1, otp2, otp3, otp4, otp5, otp6;
    Button verify;
    TextView mobile_no, resend_otp;
    String get_otp, user_mobile, get_user_id;
    FirebaseFirestore mStore;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p_verify);

        otp1 = findViewById(R.id.et_opt1);
        otp2 = findViewById(R.id.et_opt2);
        otp3 = findViewById(R.id.et_opt3);
        otp4 = findViewById(R.id.et_opt4);
        otp5 = findViewById(R.id.et_opt5);
        otp6 = findViewById(R.id.et_opt6);
        mobile_no = findViewById(R.id.tv_mobile_no);
        resend_otp = findViewById(R.id.tv_resend_otp);
        verify = findViewById(R.id.btn_verify_otp);
        mStore = FirebaseFirestore.getInstance();

        mobile_no.setText(getIntent().getStringExtra("MobileNo"));
        get_otp = getIntent().getStringExtra("OTP_Code");
        get_user_id = getIntent().getStringExtra("UserID");

        mStore.collection("Faculties").whereEqualTo("UserID", get_user_id)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                        user_mobile = queryDocumentSnapshot.getString("MobileNumber");
                    }
                }
            }
        });

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otp1_data = otp1.getText().toString();
                String otp2_data = otp2.getText().toString();
                String otp3_data = otp3.getText().toString();
                String otp4_data = otp4.getText().toString();
                String otp5_data = otp5.getText().toString();
                String otp6_data = otp6.getText().toString();

                if(TextUtils.isEmpty(otp1_data) || TextUtils.isEmpty(otp2_data) || TextUtils.isEmpty(otp3_data) ||
                        TextUtils.isEmpty(otp4_data) || TextUtils.isEmpty(otp5_data) || TextUtils.isEmpty(otp6_data)) {
                    Toast.makeText(OTPVerify.this, "Please Enter OTP Code", Toast.LENGTH_SHORT).show();
                }else {
                    String otp_code = otp1_data + otp2_data + otp3_data + otp4_data + otp5_data + otp6_data;

                    if(get_otp != null){
                        progressDialog = new ProgressDialog(OTPVerify.this);
                        progressDialog.show();
                        progressDialog.setContentView(R.layout.progress_dialog);
                        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                                get_otp, otp_code
                        );
                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.dismiss();

                                if(task.isSuccessful()){
                                    Intent i = new Intent(OTPVerify.this, CreateNewPassword.class);
                                    i.putExtra("UserID", get_user_id);
                                    startActivity(i);
                                }else{
                                    Toast.makeText(OTPVerify.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
                                    otp1.setText("");
                                    otp2.setText("");
                                    otp3.setText("");
                                    otp4.setText("");
                                    otp5.setText("");
                                    otp6.setText("");
                                }
                            }
                        });
                    }
                }
            }
        });

        numberfocus();

        resend_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthProvider.verifyPhoneNumber(
                        PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
                                .setActivity(OTPVerify.this)
                                .setPhoneNumber(user_mobile)
                                .setTimeout(60L, TimeUnit.SECONDS)
                                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {
                                        Toast.makeText(OTPVerify.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        get_otp = s;
                                        Toast.makeText(OTPVerify.this, "OTP Resend Successfully", Toast.LENGTH_SHORT).show();
                                    }
                                }).build()
                );
                Toast.makeText(OTPVerify.this, "OTP Resend Successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void numberfocus() {
        otp1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().isEmpty()){
                    otp2.requestFocus();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        otp2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().isEmpty()){
                    otp3.requestFocus();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        otp3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().isEmpty()){
                    otp4.requestFocus();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        otp4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().isEmpty()){
                    otp5.requestFocus();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        otp5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().isEmpty()){
                    otp6.requestFocus();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public void onBackPressed() {
    }
}