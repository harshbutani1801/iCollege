package com.example.icollege;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class AdminDashboard extends AppCompatActivity {

    Session Session;
    ImageView profileBtn;
    TextInputLayout tv_userid, tv_stuid;
    TextInputEditText student_id;
    EditText userid;
    FirebaseFirestore mStore;
    ChipGroup chipGroup;
    CardView add_user, update_user, delete_user, view_users, register_student, delete_student, update_student,
            view_students, dept, branch, rm_dept, rm_branch, add_class, add_subject, add_desg, del_desg;
    LinearLayout activity_view, activity_view2, activity_view3, activity_view4, activity_view5;
    Chip manageUser, manageStudent, createClass, manageDepartment, manageDesignation;
    LayoutInflater layoutInflater;
    String uid, stu_id;
    Button stu_submit, user_submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        Session = new Session(getApplicationContext());
        profileBtn = findViewById(R.id.btn_profile);
        chipGroup = findViewById(R.id.chip_g);

        manageUser = findViewById(R.id.mg_user_chip);
        manageStudent = findViewById(R.id.mg_stu_chip);
        manageDepartment = findViewById(R.id.mg_dept_chip);
        createClass = findViewById(R.id.mg_class_chip);
        manageDesignation = findViewById(R.id.mg_desg_chip);

        activity_view = findViewById(R.id.activity_view);
        activity_view2 = findViewById(R.id.activity_view2);
        activity_view3 = findViewById(R.id.activity_view3);
        activity_view4 = findViewById(R.id.activity_view4);
        activity_view5 = findViewById(R.id.activity_view5);
        mStore = FirebaseFirestore.getInstance();

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ProfilePage.class));
            }
        });

        manageUser.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
//                manageUser.setBackgroundColor(getColor(R.color.check_backcolor));
//                manageStudent.setBackgroundColor(getColor(R.color.white));
//                manageDepartment.setBackgroundColor(getColor(R.color.white));
//                createClass.setBackgroundColor(getColor(R.color.white));
//                manageDesignation.setBackgroundColor(getColor(R.color.white));
                manageUser.setTextColor(getColor(R.color.white));
                manageUser.setChipBackgroundColor(getResources().getColorStateList(R.color.check_backcolor2));
                manageDesignation.setTextColor(getColor(R.color.black));
                manageDesignation.setChipBackgroundColor(getResources().getColorStateList(R.color.uncheck_backcolor));
                manageDepartment.setTextColor(getColor(R.color.black));
                manageDepartment.setChipBackgroundColor(getResources().getColorStateList(R.color.uncheck_backcolor));
                createClass.setTextColor(getColor(R.color.black));
                createClass.setChipBackgroundColor(getResources().getColorStateList(R.color.uncheck_backcolor));
                manageStudent.setTextColor(getColor(R.color.black));
                manageStudent.setChipBackgroundColor(getResources().getColorStateList(R.color.uncheck_backcolor));

                activity_view.setVisibility(View.VISIBLE);
                activity_view2.setVisibility(View.INVISIBLE);
                activity_view3.setVisibility(View.INVISIBLE);
                activity_view4.setVisibility(View.INVISIBLE);
                activity_view5.setVisibility(View.INVISIBLE);

                layoutInflater = getLayoutInflater();
                View activity = layoutInflater.inflate(R.layout.manage_users, null, false);
                add_user = activity.findViewById(R.id.btn_add_users);
                update_user = activity.findViewById(R.id.btn_update_users);
                delete_user = activity.findViewById(R.id.btn_delete_users);
                view_users = activity.findViewById(R.id.btn_view_users);

                add_user.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), AddUser.class));
                    }
                });

                update_user.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String operator = "Update";
                        selectwin(operator);
                    }
                });

                delete_user.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String operator = "Delete";
                        selectwin(operator);
                    }
                });

                view_users.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), FacultyPage.class));
                    }
                });

                activity_view.addView(activity);
            }
        });

        manageStudent.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
//                manageStudent.setBackgroundColor(getColor(R.color.check_backcolor));
//                manageUser.setBackgroundColor(getColor(R.color.white));
//                manageDepartment.setBackgroundColor(getColor(R.color.white));
//                createClass.setBackgroundColor(getColor(R.color.white));
//                manageDesignation.setBackgroundColor(getColor(R.color.white));
                manageStudent.setTextColor(getColor(R.color.white));
                manageStudent.setChipBackgroundColor(getResources().getColorStateList(R.color.check_backcolor2));
                manageUser.setTextColor(getColor(R.color.black));
                manageUser.setChipBackgroundColor(getResources().getColorStateList(R.color.uncheck_backcolor));
                manageDesignation.setTextColor(getColor(R.color.black));
                manageDesignation.setChipBackgroundColor(getResources().getColorStateList(R.color.uncheck_backcolor));
                manageDepartment.setTextColor(getColor(R.color.black));
                manageDepartment.setChipBackgroundColor(getResources().getColorStateList(R.color.uncheck_backcolor));
                createClass.setTextColor(getColor(R.color.black));
                createClass.setChipBackgroundColor(getResources().getColorStateList(R.color.uncheck_backcolor));

                activity_view2.setVisibility(View.VISIBLE);
                activity_view.setVisibility(View.INVISIBLE);
                activity_view3.setVisibility(View.INVISIBLE);
                activity_view4.setVisibility(View.INVISIBLE);
                activity_view5.setVisibility(View.INVISIBLE);

                layoutInflater = getLayoutInflater();
                View activity2 = layoutInflater.inflate(R.layout.manage_students, null, false);
                register_student = activity2.findViewById(R.id.btn_add_student);
                delete_student = activity2.findViewById(R.id.btn_delete_student);
                update_student = activity2.findViewById(R.id.btn_update_student);
                view_students = activity2.findViewById(R.id.btn_view_student);

                register_student.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), RegisterStudent.class));
                    }
                });

                delete_student.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String way = "Delete";
                        studentwin(way);
                    }
                });

                update_student.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String way = "Update";
                        studentwin(way);
                    }
                });

                view_students.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), StudentPage.class));
                    }
                });

                activity_view2.addView(activity2);
            }
        });

        manageDepartment.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
//                manageDepartment.setBackgroundColor(getColor(R.color.check_backcolor));
//                manageUser.setBackgroundColor(getColor(R.color.white));
//                manageStudent.setBackgroundColor(getColor(R.color.white));
//                createClass.setBackgroundColor(getColor(R.color.white));
//                manageDesignation.setBackgroundColor(getColor(R.color.white));
                manageStudent.setTextColor(getColor(R.color.black));
                manageStudent.setChipBackgroundColor(getResources().getColorStateList(R.color.uncheck_backcolor));
                manageUser.setTextColor(getColor(R.color.black));
                manageUser.setChipBackgroundColor(getResources().getColorStateList(R.color.uncheck_backcolor));
                manageDesignation.setTextColor(getColor(R.color.black));
                manageDesignation.setChipBackgroundColor(getResources().getColorStateList(R.color.uncheck_backcolor));
                manageDepartment.setTextColor(getColor(R.color.white));
                manageDepartment.setChipBackgroundColor(getResources().getColorStateList(R.color.check_backcolor2));
                createClass.setTextColor(getColor(R.color.black));
                createClass.setChipBackgroundColor(getResources().getColorStateList(R.color.uncheck_backcolor));

                activity_view3.setVisibility(View.VISIBLE);
                activity_view.setVisibility(View.INVISIBLE);
                activity_view2.setVisibility(View.INVISIBLE);
                activity_view4.setVisibility(View.INVISIBLE);
                activity_view5.setVisibility(View.INVISIBLE);

                layoutInflater = getLayoutInflater();
                View activity3 = layoutInflater.inflate(R.layout.manage_department, null, false);
                dept = activity3.findViewById(R.id.btn_dept);
                rm_dept = activity3.findViewById(R.id.btn_remove_department);
                branch = activity3.findViewById(R.id.btn_branch);
                rm_branch = activity3.findViewById(R.id.btn_remove_branch);

                dept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), AddDepartment.class));
                    }
                });

                rm_dept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), RemoveDepartment.class));
                    }
                });

                branch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), AddBranch.class));
                    }
                });

                rm_branch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), RemoveBranch.class));
                    }
                });
                activity_view3.addView(activity3);
            }
        });

        createClass.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
//                createClass.setBackgroundColor(getColor(R.color.check_backcolor));
//                manageUser.setBackgroundColor(getColor(R.color.white));
//                manageStudent.setBackgroundColor(getColor(R.color.white));
//                manageDepartment.setBackgroundColor(getColor(R.color.white));
//                manageDesignation.setBackgroundColor(getColor(R.color.white));
                manageStudent.setTextColor(getColor(R.color.black));
                manageStudent.setChipBackgroundColor(getResources().getColorStateList(R.color.uncheck_backcolor));
                manageUser.setTextColor(getColor(R.color.black));
                manageUser.setChipBackgroundColor(getResources().getColorStateList(R.color.uncheck_backcolor));
                manageDesignation.setTextColor(getColor(R.color.black));
                manageDesignation.setChipBackgroundColor(getResources().getColorStateList(R.color.uncheck_backcolor));
                manageDepartment.setTextColor(getColor(R.color.black));
                manageDepartment.setChipBackgroundColor(getResources().getColorStateList(R.color.uncheck_backcolor));
                createClass.setTextColor(getColor(R.color.white));
                createClass.setChipBackgroundColor(getResources().getColorStateList(R.color.check_backcolor2));

                activity_view4.setVisibility(View.VISIBLE);
                activity_view.setVisibility(View.INVISIBLE);
                activity_view2.setVisibility(View.INVISIBLE);
                activity_view3.setVisibility(View.INVISIBLE);
                activity_view5.setVisibility(View.INVISIBLE);

                layoutInflater = getLayoutInflater();
                View activity4 = layoutInflater.inflate(R.layout.create_class, null, false);
                add_class = activity4.findViewById(R.id.btn_add_classroom);
                add_subject = activity4.findViewById(R.id.btn_add_subject);

                add_class.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), CreateClass.class));
                    }
                });

                add_subject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(), AddSubject.class));
                    }
                });
                activity_view4.addView(activity4);
            }
        });

        manageDesignation.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
//                manageDesignation.setBackgroundColor(getColor(R.color.check_backcolor));
//                manageUser.setBackgroundColor(getColor(R.color.white));
//                manageStudent.setBackgroundColor(getColor(R.color.white));
//                manageDepartment.setBackgroundColor(getColor(R.color.white));
//                createClass.setBackgroundColor(getColor(R.color.white));
                manageStudent.setTextColor(getColor(R.color.black));
                manageStudent.setChipBackgroundColor(getResources().getColorStateList(R.color.uncheck_backcolor));
                manageUser.setTextColor(getColor(R.color.black));
                manageUser.setChipBackgroundColor(getResources().getColorStateList(R.color.uncheck_backcolor));
                manageDesignation.setTextColor(getColor(R.color.white));
                manageDesignation.setChipBackgroundColor(getResources().getColorStateList(R.color.check_backcolor2));
                manageDepartment.setTextColor(getColor(R.color.black));
                manageDepartment.setChipBackgroundColor(getResources().getColorStateList(R.color.uncheck_backcolor));
                createClass.setTextColor(getColor(R.color.black));
                createClass.setChipBackgroundColor(getResources().getColorStateList(R.color.uncheck_backcolor));

                activity_view5.setVisibility(View.VISIBLE);
                activity_view.setVisibility(View.INVISIBLE);
                activity_view2.setVisibility(View.INVISIBLE);
                activity_view3.setVisibility(View.INVISIBLE);
                activity_view4.setVisibility(View.INVISIBLE);

                layoutInflater = getLayoutInflater();
                View activity5 = layoutInflater.inflate(R.layout.manage_designation, null, false);
                add_desg = activity5.findViewById(R.id.btn_add_desg);
                del_desg = activity5.findViewById(R.id.btn_del_desg);

                add_desg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), AddDesignation.class));
                    }
                });

                del_desg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), RemoveDesignation.class));
                    }
                });
                activity_view5.addView(activity5);
            }
        });

    }

    private void selectwin(String operator) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_select_window, null);

        tv_userid = view.findViewById(R.id.tv_userid);
        userid = view.findViewById(R.id.et_userid);
        user_submit = view.findViewById(R.id.btn_submit);

        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        alertDialog.show();

        userid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.equals("")){
                    mStore.collection("Faculties").whereEqualTo("UserID", userid.getText().toString()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for(QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                                    uid = queryDocumentSnapshot.getString("UserID");
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

        user_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _userid = userid.getText().toString();

                Intent i;
                if(TextUtils.isEmpty(_userid)){
                    tv_userid.setErrorEnabled(true);
                    tv_userid.setError("Please Enter UserID");
                    tv_userid.requestFocus();
                    userid.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }
                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            tv_userid.setErrorEnabled(false);
                            tv_userid.setError(null);
                        }
                        @Override
                        public void afterTextChanged(Editable editable) {
                        }
                    });
                    return;
                }else{
                    if(_userid.equals(uid)){
                        if(operator.equals("Update")){
                            i = new Intent(AdminDashboard.this, UpdateUser.class);
                        }else{
                            i = new Intent(AdminDashboard.this, DeleteUser.class);
                        }
                        i.putExtra("Userid", _userid);
                        userid.setText("");
                        Toast.makeText(AdminDashboard.this, "User Found", Toast.LENGTH_SHORT).show();
                        startActivity(i);
                        alertDialog.dismiss();
                    }else{
                        userid.setText("");
                        tv_userid.setErrorEnabled(true);
                        tv_userid.setError("No User Found");
                        userid.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            }
                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                tv_userid.setErrorEnabled(false);
                                tv_userid.setError(null);
                            }
                            @Override
                            public void afterTextChanged(Editable editable) {
                            }
                        });
                        //Toast.makeText(ManageUsers.this, "No User Found", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void studentwin(String way){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_student_window, null);

        tv_stuid = view.findViewById(R.id.tv_stu_id);
        student_id = view.findViewById(R.id.et_stu_id);
        stu_submit = view.findViewById(R.id.btn_submit);

        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        alertDialog.show();

        student_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mStore.collection("Students").whereEqualTo("ID", student_id.getText().toString()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                                stu_id = queryDocumentSnapshot.getString("ID");
                            }
                        }
                    }
                });
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        stu_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _stu_id = student_id.getText().toString();

                Intent i;
                if(TextUtils.isEmpty(_stu_id)){
                    tv_stuid.setErrorEnabled(true);
                    tv_stuid.setError("Please Enter Student ID");
                    tv_stuid.requestFocus();
                    student_id.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }
                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            tv_stuid.setErrorEnabled(false);
                            tv_stuid.setError(null);
                        }
                        @Override
                        public void afterTextChanged(Editable editable) {
                        }
                    });
                    return;
                }else {
                    if(_stu_id.equals(stu_id)){
                        if(way.equals("Update")) {
                            i = new Intent(AdminDashboard.this, UpdateStudent.class);
                        }else {
                            i = new Intent(AdminDashboard.this, DeleteStudent.class);
                        }
                        i.putExtra("ID", _stu_id);
                        student_id.setText("");
                        Toast.makeText(AdminDashboard.this, "Student Found", Toast.LENGTH_SHORT).show();
                        startActivity(i);
                        alertDialog.dismiss();
                    }else {
                        student_id.setText("");
                        tv_stuid.setErrorEnabled(true);
                        tv_stuid.setError("Student Not Found");
                        tv_stuid.requestFocus();
                        student_id.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            }
                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                tv_stuid.setErrorEnabled(false);
                                tv_stuid.setError(null);
                            }
                            @Override
                            public void afterTextChanged(Editable editable) {
                            }
                        });
                        //Toast.makeText(ManageStudent.this, "Student Not Found!", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
//        manageUser.setTextColor(getColor(R.color.white));
//        manageUser.setChipBackgroundColor(getResources().getColorStateList(R.color.check_backcolor2));
//        manageDesignation.setTextColor(getColor(R.color.black));
//        manageDesignation.setChipBackgroundColor(getResources().getColorStateList(R.color.uncheck_backcolor));
//        manageDepartment.setTextColor(getColor(R.color.black));
//        manageDepartment.setChipBackgroundColor(getResources().getColorStateList(R.color.uncheck_backcolor));
//        createClass.setTextColor(getColor(R.color.black));
//        createClass.setChipBackgroundColor(getResources().getColorStateList(R.color.uncheck_backcolor));
//        manageStudent.setTextColor(getColor(R.color.black));
//        manageStudent.setChipBackgroundColor(getResources().getColorStateList(R.color.uncheck_backcolor));

        activity_view.setVisibility(View.VISIBLE);
        activity_view2.setVisibility(View.INVISIBLE);
        activity_view3.setVisibility(View.INVISIBLE);
        activity_view4.setVisibility(View.INVISIBLE);
        activity_view5.setVisibility(View.INVISIBLE);

        layoutInflater = getLayoutInflater();
        View activity = layoutInflater.inflate(R.layout.manage_users, null, false);
        add_user = activity.findViewById(R.id.btn_add_users);
        update_user = activity.findViewById(R.id.btn_update_users);
        delete_user = activity.findViewById(R.id.btn_delete_users);
        view_users = activity.findViewById(R.id.btn_view_users);

        add_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddUser.class));
            }
        });

        update_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String operator = "Update";
                selectwin(operator);
            }
        });

        delete_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String operator = "Delete";
                selectwin(operator);
            }
        });

        view_users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), FacultyPage.class));
            }
        });

        activity_view.addView(activity);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AdminDashboard.this, R.style.AlertDialog);
        builder.setIcon(R.mipmap.exit)
                .setTitle("Exit Application")
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finishAffinity();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}