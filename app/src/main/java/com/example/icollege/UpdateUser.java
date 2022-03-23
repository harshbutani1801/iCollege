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
import android.widget.TextView;
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

public class UpdateUser extends AppCompatActivity {

    TextView userid, def_type, def_dept, def_branch, def_desg;
    TextInputLayout tv_name, tv_email, tv_mobile;
    TextInputEditText name, email, mobile;
    ImageView arrow_back;
    Button update, change_type, change_dept, change_branch, change_desg;
    FirebaseFirestore mStore;
    String uid;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        mStore = FirebaseFirestore.getInstance();
        userid = findViewById(R.id.tv_userid);
        name = findViewById(R.id.et_name);
        email = findViewById(R.id.et_email);
        mobile = findViewById(R.id.et_mobile);
        def_type = findViewById(R.id.tv_def_usertype);
        def_dept = findViewById(R.id.tv_def_dept);
        def_branch = findViewById(R.id.tv_def_branch);
        def_desg = findViewById(R.id.tv_def_desg);
        change_type = findViewById(R.id.btn_change_user_type);
        change_dept = findViewById(R.id.btn_change_user_dept);
        change_branch = findViewById(R.id.btn_change_user_branch);
        change_desg = findViewById(R.id.btn_change_user_desg);
        update = findViewById(R.id.btn_user_update);
        arrow_back = findViewById(R.id.arrow_back);
        tv_name = findViewById(R.id.tv_name);
        tv_email = findViewById(R.id.tv_email);
        tv_mobile = findViewById(R.id.tv_mobile);

        uid = getIntent().getStringExtra("Userid");

        mStore.collection("Faculties").whereEqualTo("UserID", uid).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                        userid.setText(queryDocumentSnapshot.getString("UserID"));
                        name.setText(queryDocumentSnapshot.getString("FullName"));
                        email.setText(queryDocumentSnapshot.getString("Email"));
                        mobile.setText(queryDocumentSnapshot.getString("MobileNumber"));
                        def_type.setText(queryDocumentSnapshot.getString("Position"));
                        def_dept.setText(queryDocumentSnapshot.getString("Department"));
                        def_branch.setText(queryDocumentSnapshot.getString("Branch"));
                        def_desg.setText(queryDocumentSnapshot.getString("Designation"));
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

        change_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //usertypewin();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _name = name.getText().toString();
                String _email = email.getText().toString();
                String _email_patten = "[a-zA-Z0-9_-]+@[a-z]+\\.+[a-z]+";
                String _mobile = mobile.getText().toString();
                String _dept = def_dept.getText().toString();
                String _branch = def_branch.getText().toString();

                if(TextUtils.isEmpty(_name)){
                    tv_name.setErrorEnabled(true);
                    tv_name.setError("Please Enter FullName");
                    tv_name.requestFocus();
                    name.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }
                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            tv_name.setErrorEnabled(false);
                            tv_name.setError(null);
                        }
                        @Override
                        public void afterTextChanged(Editable editable) {
                        }
                    });
                    return;
                }
                else if(TextUtils.isEmpty(_email)){
                    tv_email.setErrorEnabled(true);
                    tv_email.setError("Please Enter Email Address");
                    tv_email.requestFocus();
                    email.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }
                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            tv_email.setErrorEnabled(false);
                            tv_email.setError(null);
                        }
                        @Override
                        public void afterTextChanged(Editable editable) {
                        }
                    });
                    return;
                }
                else if(!_email.matches(_email_patten)){
                    tv_email.setErrorEnabled(true);
                    tv_email.setError("Invalid Email Address");
                    tv_email.requestFocus();
                    email.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }
                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            tv_email.setErrorEnabled(false);
                            tv_email.setError(null);
                        }
                        @Override
                        public void afterTextChanged(Editable editable) {
                        }
                    });
                    return;
                }
                else if(TextUtils.isEmpty(_mobile)){
                    tv_mobile.setErrorEnabled(true);
                    tv_mobile.setError("Please Enter Mobile Number");
                    tv_mobile.requestFocus();
                    mobile.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }
                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            tv_mobile.setErrorEnabled(false);
                            tv_mobile.setError(null);
                        }
                        @Override
                        public void afterTextChanged(Editable editable) {
                        }
                    });
                    return;
                }
                else if(mobile.length() != 13){
                    tv_mobile.setError("10 Digits Required");
                    tv_mobile.requestFocus();
                    mobile.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }
                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            tv_mobile.setErrorEnabled(false);
                            tv_mobile.setError(null);
                        }
                        @Override
                        public void afterTextChanged(Editable editable) {
                        }
                    });
                    return;
                }
                else{
                    progressDialog = new ProgressDialog(UpdateUser.this);
                    progressDialog.show();
                    progressDialog.setContentView(R.layout.progress_dialog);
                    progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    Map<String, Object> user = new HashMap<>();
                    user.put("FullName", _name);
                    user.put("Email", _email);
                    user.put("MobileNumber", _mobile);

                    mStore.collection("Faculties").document(uid).update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            mStore.collection(_dept).document(_branch).collection("Faculties").document(uid).update(user)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(UpdateUser.this, "User Updated Successfully", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(), AdminDashboard.class));
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(UpdateUser.this, "Updating User Failed", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UpdateUser.this, "Updating User Failed", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
                }
            }
        });
    }

//        private void usertypewin() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        LayoutInflater inflater = getLayoutInflater();
//        View view = inflater.inflate(R.layout.activity_user_window, null);
//
//        type = view.findViewById(R.id.spn_type);
//        upd_type = view.findViewById(R.id.btn_upd_type);
//
//        builder.setView(view);
//
//        AlertDialog alertDialog = builder.create();
//        alertDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        alertDialog.show();
//        //alertDialog.getWindow().setLayout(1000, 800);
//
//        upd_type.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String item = type.getSelectedItem().toString();
//
//
//                Map<String, Object> winuser = new HashMap<>();
//                winuser.put("User Type", item);
//
//                DocumentReference documentReference = mStore.collection(usertype).document(uid);
//                documentReference.update(winuser).addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        DocumentReference documentReference1 = mStore.collection("Login").document(uid);
//                        documentReference1.update(winuser).addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                DocumentReference old_data = mStore.collection(usertype).document(uid);
//                                DocumentReference new_data = mStore.collection(item).document(uid);
//
//                                Map<String, Object> move_data = new HashMap<>();
//                                move_data.put("UserID", uid);
//                                move_data.put("Full Name", name.getText().toString());
//                                move_data.put("Email", email.getText().toString());
//                                move_data.put("Mobile Number", mobile.getText().toString());
//                                move_data.put("User Type", item);
//
//                                new_data.set(move_data).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void aVoid) {
//                                        old_data.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
//                                            @Override
//                                            public void onSuccess(Void aVoid) {
//                                                Toast.makeText(UpdateUserActivity.this, "UserType Updated", Toast.LENGTH_SHORT).show();
//                                                startActivity(new Intent(getApplicationContext(), Manage_Member.class));
//                                            }
//                                        }).addOnFailureListener(new OnFailureListener() {
//                                            @Override
//                                            public void onFailure(@NonNull Exception e) {
//                                                Toast.makeText(UpdateUserActivity.this, "UserType Updating Failed", Toast.LENGTH_SHORT).show();
//                                            }
//                                        });
//                                    }
//                                }).addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//                                        Toast.makeText(UpdateUserActivity.this, "UserType Updating Failed", Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Toast.makeText(UpdateUserActivity.this, "Failed to Update UserType", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(UpdateUserActivity.this, "Failed to Update UserType", Toast.LENGTH_SHORT).show();
//                    }
//                });
//                alertDialog.dismiss();
//            }
//        });
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), AdminDashboard.class));
    }
}