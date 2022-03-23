package com.example.icollege;

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

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ModifyAttendance extends AppCompatActivity {

    Session Session;
    TextView view_date, view_time;
    FirebaseFirestore mStore;
    Button next, date, time;
    ImageView arrow_back;
    Spinner department, branch, class_name, semester, subject;
    ArrayList<String> dept, branch_data, four_data, six_data, eight_data, class_room, sub;
    ArrayAdapter<String> _dept, _branch,_sem, _class, _subject;
    String user_type, dept_value, branch_value, sem_value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_attendance);

        Session = new Session(getApplicationContext());
        view_date = findViewById(R.id.tv_date);
        date = findViewById(R.id.btn_date);
        //view_time = findViewById(R.id.tv_time);
        //time = findViewById(R.id.btn_time);
        next = findViewById(R.id.btn_next);
        department = findViewById(R.id.spn_dept_name);
        branch = findViewById(R.id.spn_branch_name);
        class_name = findViewById(R.id.spn_class);
        semester = findViewById(R.id.spn_semester);
        subject = findViewById(R.id.spn_subject);
        arrow_back = findViewById(R.id.arrow_back);
        mStore = FirebaseFirestore.getInstance();

        user_type = Session.getusertype();

        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user_type.equals("Faculty")){
                    startActivity(new Intent(getApplicationContext(), FacultyDashboard.class));
                }else if(user_type.equals("Head")){
                    startActivity(new Intent(getApplicationContext(), HeadDashboard.class));
                }else{
                    startActivity(new Intent(getApplicationContext(), PrincipalDashboard.class));
                }
            }
        });

        MaterialDatePicker.Builder date_build = MaterialDatePicker.Builder.datePicker();
        date_build.setTitleText("Select Date");

        final MaterialDatePicker date_pick = date_build.build();
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date_pick.show(getSupportFragmentManager(), "Date_Pick");
            }
        });

        date_pick.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                view_date.setText(date_pick.getHeaderText());
            }
        });

        dept = new ArrayList<>();
        mStore.collection("Department").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                dept.clear();
                dept.add("Select Department");
                for(DocumentSnapshot snapshot : value){
                    dept.add(snapshot.getString("Department"));
                }
                _dept.notifyDataSetChanged();
            }
        });
        _dept = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, dept);
        department.setAdapter(_dept);

        branch_data = new ArrayList<>();
        department.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                branch_data.clear();
                branch_data.add("Select Branch");
                dept_value = adapterView.getSelectedItem().toString();
                mStore.collection( dept_value +" BRANCH").addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for(DocumentSnapshot snapshot : value){
                            branch_data.add(snapshot.getString(dept_value + " BRANCH"));
                        }
                        _branch.notifyDataSetChanged();
                    }
                });
                _branch = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, branch_data);
                branch.setAdapter(_branch);

                if(dept_value.equals("MTECH") || dept_value.equals("MSC") || dept_value.equals("MCA")){
                    four_data = new ArrayList<>();
                    four_data.add("Select Semester");
                    four_data.add("1");
                    four_data.add("2");
                    four_data.add("3");
                    four_data.add("4");
                    _sem = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, four_data);

                }else if(dept_value.equals("DIPLOMA") || dept_value.equals("BSC") || dept_value.equals("BCA")){
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
                branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        branch_value = adapterView.getSelectedItem().toString();
                        semester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                class_room.clear();
                                class_room.add("Select Class Room");
                                sem_value = adapterView.getSelectedItem().toString();
                                mStore.collection(dept_value).document(branch_value).collection(sem_value).addSnapshotListener(new EventListener<QuerySnapshot>() {
                                    @Override
                                    public void onEvent(@Nullable QuerySnapshot value2, @Nullable FirebaseFirestoreException error) {
                                        for(DocumentSnapshot snapshot : value2){
                                            class_room.add(snapshot.getString("Class"));
                                        }
                                        _class.notifyDataSetChanged();
                                    }
                                });

                                sub = new ArrayList<>();
                                sub.clear();
                                sub.add("Select Subject");

                                mStore.collection(dept_value).document(branch_value).collection(sem_value).document("Semester_Subjects")
                                        .collection("Subject").addSnapshotListener(new EventListener<QuerySnapshot>() {
                                    @Override
                                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                        for(DocumentSnapshot snapshot : value){
                                            sub.add(snapshot.getString("Subject"));
                                        }
                                        _subject.notifyDataSetChanged();
                                    }
                                });
                                _subject = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, sub);
                                subject.setAdapter(_subject);
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {
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
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date_data = view_date.getText().toString();
                //String time_data = view_time.getText().toString();
                String dept_data = department.getSelectedItem().toString();
                String branch_data = branch.getSelectedItem().toString();
                String sem_data = semester.getSelectedItem().toString();
                String class_data = class_name.getSelectedItem().toString();
                String subject_data = subject.getSelectedItem().toString();

                if(date_data.isEmpty()){
                    Toast.makeText(ModifyAttendance.this, "Please Select Date", Toast.LENGTH_SHORT).show();
                }
//                else if(time_data.isEmpty()){
//                    Toast.makeText(ModifyAttendance.this, "Please Select Time", Toast.LENGTH_SHORT).show();
//                }
                else if(dept_data.equals("Select Department")){
                    Toast.makeText(ModifyAttendance.this, "Please Select Department", Toast.LENGTH_SHORT).show();
                }else if(branch_data.equals("Select Branch")){
                    Toast.makeText(ModifyAttendance.this, "Please Select Branch", Toast.LENGTH_SHORT).show();
                }else if(sem_data.equals("Select Semester")){
                    Toast.makeText(ModifyAttendance.this, "Please Select Semester", Toast.LENGTH_SHORT).show();
                }else if(class_data.equals("Select Class Room")){
                    Toast.makeText(ModifyAttendance.this, "Please Select Class Room", Toast.LENGTH_SHORT).show();
                }else if(subject_data.equals("Select Subject")){
                    Toast.makeText(ModifyAttendance.this, "Please Select Subject", Toast.LENGTH_SHORT).show();
                }else{
                    Intent i = new Intent(ModifyAttendance.this, StudentAttendanceModify.class);
                    i.putExtra("Date", date_data);
                    //i.putExtra("Time", time_data);
                    i.putExtra("Dept", dept_data);
                    i.putExtra("Branch", branch_data);
                    i.putExtra("Semester", sem_data);
                    i.putExtra("Class", class_data);
                    i.putExtra("Subject", subject_data);
                    startActivity(i);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(user_type.equals("Faculty")){
            startActivity(new Intent(getApplicationContext(), FacultyDashboard.class));
        }else if(user_type.equals("Head")){
            startActivity(new Intent(getApplicationContext(), HeadDashboard.class));
        }else{
            startActivity(new Intent(getApplicationContext(), PrincipalDashboard.class));
        }
    }
}