package com.example.icollege;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class OverallAttendance extends AppCompatActivity {

    Session Session;
    FirebaseFirestore mStore;
    Button next;
    ImageView arrow_back;
    TextView fix_dept, fix_branch;
    Spinner department, branch, class_name, semester, subject;
    ArrayList<String> dept, branch_data, four_data, six_data, eight_data, class_room;
    ArrayAdapter<String> _dept, _branch,_sem, _class;
    String dept_value, branch_value, sem_value, user_dept, user_branch, user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overall_attendance);

        Session = new Session(getApplicationContext());
        next = findViewById(R.id.btn_next);
        department = findViewById(R.id.spn_dept_name);
        branch = findViewById(R.id.spn_branch_name);
        class_name = findViewById(R.id.spn_class);
        semester = findViewById(R.id.spn_semester);
        subject = findViewById(R.id.spn_subject);
        arrow_back = findViewById(R.id.arrow_back);
        fix_dept = findViewById(R.id.tv_faculty_dept);
        fix_branch = findViewById(R.id.tv_faculty_branch);
        mStore = FirebaseFirestore.getInstance();

        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ViewStudentAttendance.class));
            }
        });


        user_id = Session.getuserid();
        mStore.collection("Faculties").whereEqualTo("UserID", user_id).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshots : queryDocumentSnapshots){
                    user_dept = documentSnapshots.getString("Department");
                    user_branch = documentSnapshots.getString("Branch");
                    //Log.d("Inside", user_dept + user_branch);

                    fix_dept.setText(user_dept);
                    fix_branch.setText(user_branch);

                    if(user_dept.equals("MTECH") || user_dept.equals("MSC") || user_dept.equals("MCA")){
                        four_data = new ArrayList<>();
                        four_data.add("Select Semester");
                        four_data.add("1");
                        four_data.add("2");
                        four_data.add("3");
                        four_data.add("4");
                        _sem = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, four_data);

                    }else if(user_dept.equals("DIPLOMA") || user_dept.equals("BSC") || user_dept.equals("BCA")){
                        six_data = new ArrayList<>();
                        six_data.add("Select Semester");
                        six_data.add("1");
                        six_data.add("2");
                        six_data.add("3");
                        six_data.add("4");
                        six_data.add("5");
                        six_data.add("6");
                        _sem = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, six_data);
                    }else{
                        eight_data = new ArrayList<>();
                        eight_data.add("Select Semester");
                        eight_data.add("1");
                        eight_data.add("2");
                        eight_data.add("3");
                        eight_data.add("4");
                        eight_data.add("5");
                        eight_data.add("6");
                        eight_data.add("7");
                        eight_data.add("8");
                        _sem = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, eight_data);
                    }
                    semester.setAdapter(_sem);

                    class_room = new ArrayList<>();
                    semester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            class_room.clear();
                            class_room.add("Select Class Room");
                            sem_value = adapterView.getSelectedItem().toString();
                            mStore.collection(user_dept).document(user_branch).collection(sem_value).addSnapshotListener(new EventListener<QuerySnapshot>() {
                                @Override
                                public void onEvent(@Nullable QuerySnapshot value2, @Nullable FirebaseFirestoreException error) {
                                    for(DocumentSnapshot snapshot : value2){
                                        class_room.add(snapshot.getString("Class"));
                                    }
                                    _class.notifyDataSetChanged();
                                }
                            });
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                    _class = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, class_room);
                    class_name.setAdapter(_class);
                }
            }
        });

//        dept = new ArrayList<>();
//        mStore.collection("Department").addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                dept.clear();
//                dept.add("Select Department");
//                for(DocumentSnapshot snapshot : value){
//                    dept.add(snapshot.getString("Department"));
//                }
//                _dept.notifyDataSetChanged();
//            }
//        });
//        _dept = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, dept);
//        department.setAdapter(_dept);
//
//        branch_data = new ArrayList<>();
//        department.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                branch_data.clear();
//                branch_data.add("Select Branch");
//                dept_value = adapterView.getSelectedItem().toString();
//                mStore.collection( dept_value +" BRANCH").addSnapshotListener(new EventListener<QuerySnapshot>() {
//                    @Override
//                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                        for(DocumentSnapshot snapshot : value){
//                            branch_data.add(snapshot.getString(dept_value + " BRANCH"));
//                        }
//                        _branch.notifyDataSetChanged();
//                    }
//                });
//                _branch = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, branch_data);
//                branch.setAdapter(_branch);
//
//                if(dept_value.equals("MTECH") || dept_value.equals("MSC") || dept_value.equals("MCA")){
//                    four_data = new ArrayList<>();
//                    four_data.add("Select Semester");
//                    four_data.add("1");
//                    four_data.add("2");
//                    four_data.add("3");
//                    four_data.add("4");
//                    _sem = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, four_data);
//
//                }else if(dept_value.equals("DIPLOMA") || dept_value.equals("BSC") || dept_value.equals("BCA")){
//                    six_data = new ArrayList<>();
//                    six_data.add("Select Semester");
//                    six_data.add("1");
//                    six_data.add("2");
//                    six_data.add("3");
//                    six_data.add("4");
//                    six_data.add("5");
//                    six_data.add("6");
//                    _sem = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, six_data);
//                }else{
//                    eight_data = new ArrayList<>();
//                    eight_data.add("Select Semester");
//                    eight_data.add("1");
//                    eight_data.add("2");
//                    eight_data.add("3");
//                    eight_data.add("4");
//                    eight_data.add("5");
//                    eight_data.add("6");
//                    eight_data.add("7");
//                    eight_data.add("8");
//                    _sem = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, eight_data);
//                }
//                semester.setAdapter(_sem);
//
//                class_room = new ArrayList<>();
//                branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                        branch_value = adapterView.getSelectedItem().toString();
//                        semester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                            @Override
//                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                                class_room.clear();
//                                class_room.add("Select Class Room");
//                                sem_value = adapterView.getSelectedItem().toString();
//                                mStore.collection(dept_value).document(branch_value).collection(sem_value).addSnapshotListener(new EventListener<QuerySnapshot>() {
//                                    @Override
//                                    public void onEvent(@Nullable QuerySnapshot value2, @Nullable FirebaseFirestoreException error) {
//                                        for(DocumentSnapshot snapshot : value2){
//                                            class_room.add(snapshot.getString("Class"));
//                                        }
//                                        _class.notifyDataSetChanged();
//                                    }
//                                });
//                            }
//                            @Override
//                            public void onNothingSelected(AdapterView<?> adapterView) {
//                            }
//                        });
//                    }
//                    @Override
//                    public void onNothingSelected(AdapterView<?> adapterView) {
//                    }
//                });
//                _class = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, class_room);
//                class_name.setAdapter(_class);
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//            }
//        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dept_data = user_dept;
                String branch_data = user_branch;
                String sem_data = semester.getSelectedItem().toString();
                String class_data = class_name.getSelectedItem().toString();

//                if (dept_data.equals("Select Department")) {
//                    Toast.makeText(OverallAttendance.this, "Please Select Department", Toast.LENGTH_SHORT).show();
//                } else if (branch_data.equals("Select Branch")) {
//                    Toast.makeText(OverallAttendance.this, "Please Select Branch", Toast.LENGTH_SHORT).show();
//                } else
                if (sem_data.equals("Select Semester")) {
                    Toast.makeText(OverallAttendance.this, "Please Select Semester", Toast.LENGTH_SHORT).show();
                } else if (class_data.equals("Select Class Room")) {
                    Toast.makeText(OverallAttendance.this, "Please Select Class Room", Toast.LENGTH_SHORT).show();
                } else {
                    Intent i = new Intent(OverallAttendance.this, StudentAttendanceOverall.class);
                    i.putExtra("Dept", dept_data);
                    i.putExtra("Branch", branch_data);
                    i.putExtra("Semester", sem_data);
                    i.putExtra("Class", class_data);
                    startActivity(i);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), ViewStudentAttendance.class));
    }
}