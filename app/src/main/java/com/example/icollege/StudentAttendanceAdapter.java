package com.example.icollege;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class StudentAttendanceAdapter extends FirestoreRecyclerAdapter<StudentAttendanceModel, StudentAttendanceAdapter.AttendanceViewHolder> {

    String date_value, dept_value, branch_value, sem_value, class_value, subject_value;
    FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    public StudentAttendanceAdapter(@NonNull FirestoreRecyclerOptions<StudentAttendanceModel> options, String date,
                                    String dept, String branch, String sem, String classroom, String subject) {
        super(options);
        date_value = date;
        dept_value = dept;
        branch_value = branch;
        sem_value = sem;
        class_value = classroom;
        subject_value = subject;
    }

    @Override
    protected void onBindViewHolder(@NonNull AttendanceViewHolder holder, int position, @NonNull StudentAttendanceModel model) {
        holder.check_roll.setText(model.getEnrollmentNumber());
        holder.check_roll.setChecked(true);

//        if(date_value.equals(model.getDate())){
            if(subject_value.equals("MachineLearning")){

                if(holder.check_roll.isChecked()){
                    holder.print.setText("Present");

                    float TA = Integer.parseInt(model.getMLA()) + 1;
                    float TD = Integer.parseInt(model.getMLD()) + 1;

                    float percentage_value = (TA/TD)*100;
                    String percentage = String.valueOf((int)percentage_value);
                    String _mla = String.valueOf((int)TA);
                    String _mld = String.valueOf((int)TD);

                    Map<String, Object> update_value = new HashMap<>();
                    update_value.put("Department", model.getDepartment());
                    update_value.put("Branch", model.getBranch());
                    update_value.put("Semester", model.getSemester());
                    update_value.put("ClassRoom", model.getClassRoom());
                    update_value.put("ID", model.getID());
                    update_value.put("FullName", model.getFullName());
                    update_value.put("Date", date_value);
                    update_value.put("DNetA", model.getDNetA());
                    update_value.put("DNetD", model.getDNetD());
                    update_value.put("MLA", _mla);
                    update_value.put("MLD", _mld);

                    mStore.collection("UpdateAttendance").document(model.getID()).set(update_value)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    //Attendance Value Updated
                                }
                            });

                    Map<String, Object> attendance = new HashMap<>();
                    attendance.put("ID", model.getID());
                    attendance.put("FullName", model.getFullName());
                    attendance.put("EnrollmentNumber", model.getEnrollmentNumber());
                    attendance.put("Department", model.getDepartment());
                    attendance.put("Branch", model.getBranch());
                    attendance.put("Attendance", "Present");
                    attendance.put("MLPercentage", percentage);

                    mStore.collection("Attendance").document(date_value).collection(dept_value).document(branch_value).collection(sem_value)
                            .document(class_value).collection(subject_value).document(model.getID()).set(attendance)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    //Attendance Taken
                                }
                            });

                    Map<String, Object> attendance2 = new HashMap<>();
                    attendance2.put("ID", model.getID());
                    attendance2.put("FullName", model.getFullName());
                    attendance2.put("EnrollmentNumber", model.getEnrollmentNumber());
                    attendance2.put("Department", model.getDepartment());
                    attendance2.put("Branch", model.getBranch());
                    attendance2.put("Attendance", "Present");
                    attendance2.put("TotalAttendance", _mla);
                    attendance2.put("TotalDay", _mld);
                    attendance2.put("MLPercentage", percentage);

                    mStore.collection("Attendance").document(dept_value).collection(branch_value).document(sem_value).collection(class_value)
                            .document(subject_value).collection("Students").document(model.getID()).set(attendance2)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    //Attendance Taken
                                }
                            });

                    Map<String, Object> outside_attendance = new HashMap<>();
//                    outside_attendance.put("ID", model.getID());
//                    outside_attendance.put("FullName", model.getFullName());
//                    outside_attendance.put("EnrollmentNumber", model.getEnrollmentNumber());
//                    outside_attendance.put("Department", model.getDepartment());
//                    outside_attendance.put("Branch", model.getBranch());
                    outside_attendance.put("MLPercentage", percentage);

                    mStore.collection("Attendance").document(dept_value).collection(branch_value).document(sem_value).collection(class_value)
                            .document(model.getID()).update(outside_attendance)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    //Attendance Outside Taken
                                }
                            });

                    holder.check_roll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if(isChecked){
                                holder.print.setText("Present");

                                float TA = Integer.parseInt(model.getMLA()) + 1;
                                float TD = Integer.parseInt(model.getMLD()) + 1;

                                float percentage_value = (TA/TD)*100;
                                String percentage = String.valueOf((int)percentage_value);
                                String _mla = String.valueOf((int)TA);
                                String _mld = String.valueOf((int)TD);

                                Map<String, Object> update_value = new HashMap<>();
                                update_value.put("Department", model.getDepartment());
                                update_value.put("Branch", model.getBranch());
                                update_value.put("Semester", model.getSemester());
                                update_value.put("ClassRoom", model.getClassRoom());
                                update_value.put("ID", model.getID());
                                update_value.put("FullName", model.getFullName());
                                update_value.put("Date", date_value);
                                update_value.put("DNetA", model.getDNetA());
                                update_value.put("DNetD", model.getDNetD());
                                update_value.put("MLA", _mla);
                                update_value.put("MLD", _mld);

                                mStore.collection("UpdateAttendance").document(model.getID()).set(update_value)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                //Attendance Value Updated
                                            }
                                        });

                                Map<String, Object> attendance = new HashMap<>();
                                attendance.put("ID", model.getID());
                                attendance.put("FullName", model.getFullName());
                                attendance.put("EnrollmentNumber", model.getEnrollmentNumber());
                                attendance.put("Department", model.getDepartment());
                                attendance.put("Branch", model.getBranch());
                                attendance.put("Attendance", "Present");
                                attendance.put("MLPercentage", percentage);

                                mStore.collection("Attendance").document(date_value).collection(dept_value).document(branch_value).collection(sem_value)
                                        .document(class_value).collection(subject_value).document(model.getID()).set(attendance)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                //Attendance Taken
                                            }
                                        });

                                Map<String, Object> attendance2 = new HashMap<>();
                                attendance2.put("ID", model.getID());
                                attendance2.put("FullName", model.getFullName());
                                attendance2.put("EnrollmentNumber", model.getEnrollmentNumber());
                                attendance2.put("Department", model.getDepartment());
                                attendance2.put("Branch", model.getBranch());
                                attendance2.put("Attendance", "Present");
                                attendance2.put("TotalAttendance", _mla);
                                attendance2.put("TotalDay", _mld);
                                attendance2.put("MLPercentage", percentage);

                                mStore.collection("Attendance").document(dept_value).collection(branch_value).document(sem_value).collection(class_value)
                                        .document(subject_value).collection("Students").document(model.getID()).set(attendance2)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                //Attendance Taken
                                            }
                                        });

                                Map<String, Object> outside_attendance = new HashMap<>();
//                            outside_attendance.put("ID", model.getID());
//                            outside_attendance.put("FullName", model.getFullName());
//                            outside_attendance.put("EnrollmentNumber", model.getEnrollmentNumber());
//                            outside_attendance.put("Department", model.getDepartment());
//                            outside_attendance.put("Branch", model.getBranch());
                                outside_attendance.put("MLPercentage", percentage);

                                mStore.collection("Attendance").document(dept_value).collection(branch_value).document(sem_value).collection(class_value)
                                        .document(model.getID()).update(outside_attendance)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                //Attendance Outside Taken
                                            }
                                        });

                            }
                            else{
                                holder.print.setText("Absent");

                                float TA = Integer.parseInt(model.getMLA());
                                float TD = Integer.parseInt(model.getMLD()) + 1;

                                float percentage_value = (TA/TD)*100;
                                String percentage = String.valueOf((int)percentage_value);
                                String _mla = String.valueOf((int)TA);
                                String _mld = String.valueOf((int)TD);

                                Map<String, Object> update_value = new HashMap<>();
                                update_value.put("Department", model.getDepartment());
                                update_value.put("Branch", model.getBranch());
                                update_value.put("Semester", model.getSemester());
                                update_value.put("ClassRoom", model.getClassRoom());
                                update_value.put("ID", model.getID());
                                update_value.put("FullName", model.getFullName());
                                update_value.put("Date", date_value);
                                update_value.put("DNetA", model.getDNetA());
                                update_value.put("DNetD", model.getDNetD());
                                update_value.put("MLA", _mla);
                                update_value.put("MLD", _mld);

                                mStore.collection("UpdateAttendance").document(model.getID()).set(update_value)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                //Attendance Value Updated
                                            }
                                        });

                                Map<String, Object> attendance = new HashMap<>();
                                attendance.put("ID", model.getID());
                                attendance.put("FullName", model.getFullName());
                                attendance.put("EnrollmentNumber", model.getEnrollmentNumber());
                                attendance.put("Department", model.getDepartment());
                                attendance.put("Branch", model.getBranch());
                                attendance.put("Attendance", "Absent");
                                attendance.put("MLPercentage", percentage);

                                mStore.collection("Attendance").document(date_value).collection(dept_value).document(branch_value).collection(sem_value)
                                        .document(class_value).collection(subject_value).document(model.getID()).set(attendance)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                //Attendance Taken
                                            }
                                        });

                                Map<String, Object> attendance2 = new HashMap<>();
                                attendance2.put("ID", model.getID());
                                attendance2.put("FullName", model.getFullName());
                                attendance2.put("EnrollmentNumber", model.getEnrollmentNumber());
                                attendance2.put("Department", model.getDepartment());
                                attendance2.put("Branch", model.getBranch());
                                attendance2.put("Attendance", "Present");
                                attendance2.put("TotalAttendance", _mla);
                                attendance2.put("TotalDay", _mld);
                                attendance2.put("MLPercentage", percentage);

                                mStore.collection("Attendance").document(dept_value).collection(branch_value).document(sem_value).collection(class_value)
                                        .document(subject_value).collection("Students").document(model.getID()).set(attendance2)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                //Attendance Taken
                                            }
                                        });

                                Map<String, Object> outside_attendance = new HashMap<>();
//                            outside_attendance.put("ID", model.getID());
//                            outside_attendance.put("FullName", model.getFullName());
//                            outside_attendance.put("EnrollmentNumber", model.getEnrollmentNumber());
//                            outside_attendance.put("Department", model.getDepartment());
//                            outside_attendance.put("Branch", model.getBranch());
                                outside_attendance.put("MLPercentage", percentage);

                                mStore.collection("Attendance").document(dept_value).collection(branch_value).document(sem_value).collection(class_value)
                                        .document(model.getID()).update(outside_attendance)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                //Attendance Outside Taken
                                            }
                                        });
                            }
                        }
                    });
                }
            }
            else{
                if(holder.check_roll.isChecked()){
                    holder.print.setText("Present");

                    float TA = Integer.parseInt(model.getDNetA()) + 1;
                    float TD = Integer.parseInt(model.getDNetD()) + 1;

                    float percentage_value = (TA/TD)*100;
                    String percentage = String.valueOf((int)percentage_value);
                    String _dneta = String.valueOf((int)TA);
                    String _dnetd = String.valueOf((int)TD);

                    Map<String, Object> update_value = new HashMap<>();
                    update_value.put("Department", model.getDepartment());
                    update_value.put("Branch", model.getBranch());
                    update_value.put("Semester", model.getSemester());
                    update_value.put("ClassRoom", model.getClassRoom());
                    update_value.put("ID", model.getID());
                    update_value.put("FullName", model.getFullName());
                    update_value.put("Date", date_value);
                    update_value.put("MLA", model.getMLA());
                    update_value.put("MLD", model.getMLD());
                    update_value.put("DNetA", _dneta);
                    update_value.put("DNetD", _dnetd);

                    mStore.collection("UpdateAttendance").document(model.getID()).set(update_value)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    //Attendance Value Updated
                                }
                            });

                    Map<String, Object> attendance = new HashMap<>();
                    attendance.put("ID", model.getID());
                    attendance.put("FullName", model.getFullName());
                    attendance.put("EnrollmentNumber", model.getEnrollmentNumber());
                    attendance.put("Department", model.getDepartment());
                    attendance.put("Branch", model.getBranch());
                    attendance.put("Attendance", "Present");
                    attendance.put("DNetPercentage", percentage);

                    mStore.collection("Attendance").document(date_value).collection(dept_value).document(branch_value).collection(sem_value)
                            .document(class_value).collection(subject_value).document(model.getID()).set(attendance)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    //Attendance Taken
                                }
                            });

                    Map<String, Object> attendance2 = new HashMap<>();
                    attendance2.put("ID", model.getID());
                    attendance2.put("FullName", model.getFullName());
                    attendance2.put("EnrollmentNumber", model.getEnrollmentNumber());
                    attendance2.put("Department", model.getDepartment());
                    attendance2.put("Branch", model.getBranch());
                    attendance2.put("Attendance", "Present");
                    attendance2.put("TotalAttendance", _dneta);
                    attendance2.put("TotalDay", _dnetd);
                    attendance2.put("DNetPercentage", percentage);

                    mStore.collection("Attendance").document(dept_value).collection(branch_value).document(sem_value).collection(class_value)
                            .document(subject_value).collection("Students").document(model.getID()).set(attendance2)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    //Attendance Taken
                                }
                            });

                    Map<String, Object> outside_attendance = new HashMap<>();
//                    outside_attendance.put("ID", model.getID());
//                    outside_attendance.put("FullName", model.getFullName());
//                    outside_attendance.put("EnrollmentNumber", model.getEnrollmentNumber());
//                    outside_attendance.put("Department", model.getDepartment());
//                    outside_attendance.put("Branch", model.getBranch());
                    outside_attendance.put("DNetPercentage", percentage);

                    mStore.collection("Attendance").document(dept_value).collection(branch_value).document(sem_value).collection(class_value)
                            .document(model.getID()).update(outside_attendance)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    //Attendance Outside Taken
                                }
                            });

                    holder.check_roll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if(isChecked){
                                holder.print.setText("Present");

                                float TA = Integer.parseInt(model.getDNetA()) + 1;
                                float TD = Integer.parseInt(model.getDNetD()) + 1;

                                float percentage_value = (TA/TD)*100;
                                String percentage = String.valueOf((int)percentage_value);
                                String _dneta = String.valueOf((int)TA);
                                String _dnetd = String.valueOf((int)TD);

                                Map<String, Object> update_value = new HashMap<>();
                                update_value.put("Department", model.getDepartment());
                                update_value.put("Branch", model.getBranch());
                                update_value.put("Semester", model.getSemester());
                                update_value.put("ClassRoom", model.getClassRoom());
                                update_value.put("ID", model.getID());
                                update_value.put("FullName", model.getFullName());
                                update_value.put("Date", date_value);
                                update_value.put("MLA", model.getMLA());
                                update_value.put("MLD", model.getMLD());
                                update_value.put("DNetA", _dneta);
                                update_value.put("DNetD", _dnetd);

                                mStore.collection("UpdateAttendance").document(model.getID()).set(update_value)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                //Attendance Value Updated
                                            }
                                        });

                                Map<String, Object> attendance = new HashMap<>();
                                attendance.put("ID", model.getID());
                                attendance.put("FullName", model.getFullName());
                                attendance.put("EnrollmentNumber", model.getEnrollmentNumber());
                                attendance.put("Department", model.getDepartment());
                                attendance.put("Branch", model.getBranch());
                                attendance.put("Attendance", "Present");
                                attendance.put("MLPercentage", percentage);

                                mStore.collection("Attendance").document(date_value).collection(dept_value).document(branch_value).collection(sem_value)
                                        .document(class_value).collection(subject_value).document(model.getID()).set(attendance)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                //Attendance Taken
                                            }
                                        });

                                Map<String, Object> attendance2 = new HashMap<>();
                                attendance2.put("ID", model.getID());
                                attendance2.put("FullName", model.getFullName());
                                attendance2.put("EnrollmentNumber", model.getEnrollmentNumber());
                                attendance2.put("Department", model.getDepartment());
                                attendance2.put("Branch", model.getBranch());
                                attendance2.put("Attendance", "Present");
                                attendance2.put("TotalAttendance", _dneta);
                                attendance2.put("TotalDay", _dnetd);
                                attendance2.put("DNetPercentage", percentage);

                                mStore.collection("Attendance").document(dept_value).collection(branch_value).document(sem_value).collection(class_value)
                                        .document(subject_value).collection("Students").document(model.getID()).set(attendance2)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                //Attendance Taken
                                            }
                                        });

                                Map<String, Object> outside_attendance = new HashMap<>();
//                            outside_attendance.put("ID", model.getID());
//                            outside_attendance.put("FullName", model.getFullName());
//                            outside_attendance.put("EnrollmentNumber", model.getEnrollmentNumber());
//                            outside_attendance.put("Department", model.getDepartment());
//                            outside_attendance.put("Branch", model.getBranch());
                                outside_attendance.put("DNetPercentage", percentage);

                                mStore.collection("Attendance").document(dept_value).collection(branch_value).document(sem_value).collection(class_value)
                                        .document(model.getID()).update(outside_attendance)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                //Attendance Outside Taken
                                            }
                                        });

                            }
                            else{
                                holder.print.setText("Absent");

                                float TA = Integer.parseInt(model.getDNetA());
                                float TD = Integer.parseInt(model.getDNetD()) + 1;

                                float percentage_value = (TA/TD)*100;
                                String percentage = String.valueOf((int)percentage_value);
                                String _dneta = String.valueOf((int)TA);
                                String _dnetd = String.valueOf((int)TD);

                                Map<String, Object> update_value = new HashMap<>();
                                update_value.put("Department", model.getDepartment());
                                update_value.put("Branch", model.getBranch());
                                update_value.put("Semester", model.getSemester());
                                update_value.put("ClassRoom", model.getClassRoom());
                                update_value.put("ID", model.getID());
                                update_value.put("FullName", model.getFullName());
                                update_value.put("Date", date_value);
                                update_value.put("MLA", model.getMLA());
                                update_value.put("MLD", model.getMLD());
                                update_value.put("DNetA", _dneta);
                                update_value.put("DNetD", _dnetd);

                                mStore.collection("UpdateAttendance").document(model.getID()).set(update_value)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                //Attendance Value Updated
                                            }
                                        });

                                Map<String, Object> attendance = new HashMap<>();
                                attendance.put("ID", model.getID());
                                attendance.put("FullName", model.getFullName());
                                attendance.put("EnrollmentNumber", model.getEnrollmentNumber());
                                attendance.put("Department", model.getDepartment());
                                attendance.put("Branch", model.getBranch());
                                attendance.put("Attendance", "Absent");
                                attendance.put("DNetPercentage", percentage);

                                mStore.collection("Attendance").document(date_value).collection(dept_value).document(branch_value).collection(sem_value)
                                        .document(class_value).collection(subject_value).document(model.getID()).set(attendance)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                //Attendance Taken
                                            }
                                        });

                                Map<String, Object> attendance2 = new HashMap<>();
                                attendance2.put("ID", model.getID());
                                attendance2.put("FullName", model.getFullName());
                                attendance2.put("EnrollmentNumber", model.getEnrollmentNumber());
                                attendance2.put("Department", model.getDepartment());
                                attendance2.put("Branch", model.getBranch());
                                attendance2.put("Attendance", "Present");
                                attendance2.put("TotalAttendance", _dneta);
                                attendance2.put("TotalDay", _dnetd);
                                attendance2.put("DNetPercentage", percentage);

                                mStore.collection("Attendance").document(dept_value).collection(branch_value).document(sem_value).collection(class_value)
                                        .document(subject_value).collection("Students").document(model.getID()).set(attendance2)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                //Attendance Taken
                                            }
                                        });

                                Map<String, Object> outside_attendance = new HashMap<>();
//                            outside_attendance.put("ID", model.getID());
//                            outside_attendance.put("FullName", model.getFullName());
//                            outside_attendance.put("EnrollmentNumber", model.getEnrollmentNumber());
//                            outside_attendance.put("Department", model.getDepartment());
//                            outside_attendance.put("Branch", model.getBranch());
                                outside_attendance.put("DNetPercentage", percentage);

                                mStore.collection("Attendance").document(dept_value).collection(branch_value).document(sem_value).collection(class_value)
                                        .document(model.getID()).update(outside_attendance)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                //Attendance Outside Taken
                                            }
                                        });
                            }
                        }
                    });
                }
            }
        }
//        else{
//            if(subject_value.equals("MachineLearning")){
//                if(holder.check_roll.isChecked()){
//                    holder.print.setText("Present");
//
//                    float TA = Integer.parseInt(model.getMLA()) + 1;
//                    float TD = Integer.parseInt(model.getMLD()) + 1;
//
//                    float percentage_value = (TA/TD)*100;
//                    String percentage = String.valueOf((int)percentage_value);
//                    String _mla = String.valueOf((int)TA);
//                    String _mld = String.valueOf((int)TD);
//
//                    Map<String, Object> update_value = new HashMap<>();
//                    update_value.put("Department", model.getDepartment());
//                    update_value.put("Branch", model.getBranch());
//                    update_value.put("Semester", model.getSemester());
//                    update_value.put("ClassRoom", model.getClassRoom());
//                    update_value.put("ID", model.getID());
//                    update_value.put("FullName", model.getFullName());
//                    update_value.put("Date", date_value);
//                    update_value.put("DNetA", model.getDNetA());
//                    update_value.put("DNetD", model.getDNetD());
//                    update_value.put("MLA", _mla);
//                    update_value.put("MLD", _mld);
//
//                    mStore.collection("UpdateAttendance").document(model.getID()).set(update_value)
//                            .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void aVoid) {
//                                    //Attendance Value Updated
//                                }
//                            });
//
//                    Map<String, Object> attendance = new HashMap<>();
//                    attendance.put("ID", model.getID());
//                    attendance.put("FullName", model.getFullName());
//                    attendance.put("EnrollmentNumber", model.getEnrollmentNumber());
//                    attendance.put("Department", model.getDepartment());
//                    attendance.put("Branch", model.getBranch());
//                    attendance.put("Attendance", "Present");
//                    attendance.put("MLPercentage", percentage);
//
//                    mStore.collection("Attendance").document(date_value).collection(dept_value).document(branch_value).collection(sem_value)
//                            .document(class_value).collection(subject_value).document(model.getID()).set(attendance)
//                            .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void aVoid) {
//                                    //Attendance Taken
//                                }
//                            });
//
//                    Map<String, Object> attendance2 = new HashMap<>();
//                    attendance2.put("ID", model.getID());
//                    attendance2.put("FullName", model.getFullName());
//                    attendance2.put("EnrollmentNumber", model.getEnrollmentNumber());
//                    attendance2.put("Department", model.getDepartment());
//                    attendance2.put("Branch", model.getBranch());
//                    attendance2.put("Attendance", "Present");
//                    attendance2.put("TotalAttendance", _mla);
//                    attendance2.put("TotalDay", _mld);
//                    attendance2.put("MLPercentage", percentage);
//
//                    mStore.collection("Attendance").document(dept_value).collection(branch_value).document(sem_value).collection(class_value)
//                            .document(subject_value).collection("Students").document(model.getID()).set(attendance2)
//                            .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void aVoid) {
//                                    //Attendance Taken
//                                }
//                            });
//
//                    Map<String, Object> outside_attendance = new HashMap<>();
////                    outside_attendance.put("ID", model.getID());
////                    outside_attendance.put("FullName", model.getFullName());
////                    outside_attendance.put("EnrollmentNumber", model.getEnrollmentNumber());
////                    outside_attendance.put("Department", model.getDepartment());
////                    outside_attendance.put("Branch", model.getBranch());
//                    outside_attendance.put("MLPercentage", percentage);
//
//                    mStore.collection("Attendance").document(dept_value).collection(branch_value).document(sem_value).collection(class_value)
//                            .document(model.getID()).update(outside_attendance)
//                            .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void aVoid) {
//                                    //Attendance Outside Taken
//                                }
//                            });
//                }
//
//                holder.check_roll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                        if(isChecked){
//                            holder.print.setText("Present");
//
//                            float TA = Integer.parseInt(model.getMLA()) + 1;
//                            float TD = Integer.parseInt(model.getMLD()) + 1;
//
//                            float percentage_value = (TA/TD)*100;
//                            String percentage = String.valueOf((int)percentage_value);
//                            String _mla = String.valueOf((int)TA);
//                            String _mld = String.valueOf((int)TD);
//
//                            Map<String, Object> update_value = new HashMap<>();
//                            update_value.put("Department", model.getDepartment());
//                            update_value.put("Branch", model.getBranch());
//                            update_value.put("Semester", model.getSemester());
//                            update_value.put("ClassRoom", model.getClassRoom());
//                            update_value.put("ID", model.getID());
//                            update_value.put("FullName", model.getFullName());
//                            update_value.put("Date", date_value);
//                            update_value.put("DNetA", model.getDNetA());
//                            update_value.put("DNetD", model.getDNetD());
//                            update_value.put("MLA", _mla);
//                            update_value.put("MLD", _mld);
//
//                            mStore.collection("UpdateAttendance").document(model.getID()).set(update_value)
//                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                        @Override
//                                        public void onSuccess(Void aVoid) {
//                                            //Attendance Value Updated
//                                        }
//                                    });
//
//                            Map<String, Object> attendance = new HashMap<>();
//                            attendance.put("ID", model.getID());
//                            attendance.put("FullName", model.getFullName());
//                            attendance.put("EnrollmentNumber", model.getEnrollmentNumber());
//                            attendance.put("Department", model.getDepartment());
//                            attendance.put("Branch", model.getBranch());
//                            attendance.put("Attendance", "Present");
//                            attendance.put("MLPercentage", percentage);
//
//                            mStore.collection("Attendance").document(date_value).collection(dept_value).document(branch_value).collection(sem_value)
//                                    .document(class_value).collection(subject_value).document(model.getID()).set(attendance)
//                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                        @Override
//                                        public void onSuccess(Void aVoid) {
//                                            //Attendance Taken
//                                        }
//                                    });
//
//                            Map<String, Object> attendance2 = new HashMap<>();
//                            attendance2.put("ID", model.getID());
//                            attendance2.put("FullName", model.getFullName());
//                            attendance2.put("EnrollmentNumber", model.getEnrollmentNumber());
//                            attendance2.put("Department", model.getDepartment());
//                            attendance2.put("Branch", model.getBranch());
//                            attendance2.put("Attendance", "Present");
//                            attendance2.put("TotalAttendance", _mla);
//                            attendance2.put("TotalDay", _mld);
//                            attendance2.put("MLPercentage", percentage);
//
//                            mStore.collection("Attendance").document(dept_value).collection(branch_value).document(sem_value).collection(class_value)
//                                    .document(subject_value).collection("Students").document(model.getID()).set(attendance2)
//                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                        @Override
//                                        public void onSuccess(Void aVoid) {
//                                            //Attendance Taken
//                                        }
//                                    });
//
//                            Map<String, Object> outside_attendance = new HashMap<>();
////                            outside_attendance.put("ID", model.getID());
////                            outside_attendance.put("FullName", model.getFullName());
////                            outside_attendance.put("EnrollmentNumber", model.getEnrollmentNumber());
////                            outside_attendance.put("Department", model.getDepartment());
////                            outside_attendance.put("Branch", model.getBranch());
//                            outside_attendance.put("MLPercentage", percentage);
//
//                            mStore.collection("Attendance").document(dept_value).collection(branch_value).document(sem_value).collection(class_value)
//                                    .document(model.getID()).update(outside_attendance)
//                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                        @Override
//                                        public void onSuccess(Void aVoid) {
//                                            //Attendance Outside Taken
//                                        }
//                                    });
//
//                        }
//                        else{
//                            holder.print.setText("Absent");
//
//                            float TA = Integer.parseInt(model.getMLA());
//                            float TD = Integer.parseInt(model.getMLD()) + 1;
//
//                            float percentage_value = (TA/TD)*100;
//                            String percentage = String.valueOf((int)percentage_value);
//                            String _mla = String.valueOf((int)TA);
//                            String _mld = String.valueOf((int)TD);
//
//                            Map<String, Object> update_value = new HashMap<>();
//                            update_value.put("Department", model.getDepartment());
//                            update_value.put("Branch", model.getBranch());
//                            update_value.put("Semester", model.getSemester());
//                            update_value.put("ClassRoom", model.getClassRoom());
//                            update_value.put("ID", model.getID());
//                            update_value.put("FullName", model.getFullName());
//                            update_value.put("Date", date_value);
//                            update_value.put("DNetA", model.getDNetA());
//                            update_value.put("DNetD", model.getDNetD());
//                            update_value.put("MLA", _mla);
//                            update_value.put("MLD", _mld);
//
//                            mStore.collection("UpdateAttendance").document(model.getID()).set(update_value)
//                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                        @Override
//                                        public void onSuccess(Void aVoid) {
//                                            //Attendance Value Updated
//                                        }
//                                    });
//
//                            Map<String, Object> attendance = new HashMap<>();
//                            attendance.put("ID", model.getID());
//                            attendance.put("FullName", model.getFullName());
//                            attendance.put("EnrollmentNumber", model.getEnrollmentNumber());
//                            attendance.put("Department", model.getDepartment());
//                            attendance.put("Branch", model.getBranch());
//                            attendance.put("Attendance", "Absent");
//                            attendance.put("MLPercentage", percentage);
//
//                            mStore.collection("Attendance").document(date_value).collection(dept_value).document(branch_value).collection(sem_value)
//                                    .document(class_value).collection(subject_value).document(model.getID()).set(attendance)
//                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                        @Override
//                                        public void onSuccess(Void aVoid) {
//                                            //Attendance Taken
//                                        }
//                                    });
//
//                            Map<String, Object> attendance2 = new HashMap<>();
//                            attendance2.put("ID", model.getID());
//                            attendance2.put("FullName", model.getFullName());
//                            attendance2.put("EnrollmentNumber", model.getEnrollmentNumber());
//                            attendance2.put("Department", model.getDepartment());
//                            attendance2.put("Branch", model.getBranch());
//                            attendance2.put("Attendance", "Present");
//                            attendance2.put("TotalAttendance", _mla);
//                            attendance2.put("TotalDay", _mld);
//                            attendance2.put("MLPercentage", percentage);
//
//                            mStore.collection("Attendance").document(dept_value).collection(branch_value).document(sem_value).collection(class_value)
//                                    .document(subject_value).collection("Students").document(model.getID()).set(attendance2)
//                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                        @Override
//                                        public void onSuccess(Void aVoid) {
//                                            //Attendance Taken
//                                        }
//                                    });
//
//                            Map<String, Object> outside_attendance = new HashMap<>();
////                            outside_attendance.put("ID", model.getID());
////                            outside_attendance.put("FullName", model.getFullName());
////                            outside_attendance.put("EnrollmentNumber", model.getEnrollmentNumber());
////                            outside_attendance.put("Department", model.getDepartment());
////                            outside_attendance.put("Branch", model.getBranch());
//                            outside_attendance.put("MLPercentage", percentage);
//
//                            mStore.collection("Attendance").document(dept_value).collection(branch_value).document(sem_value).collection(class_value)
//                                    .document(model.getID()).update(outside_attendance)
//                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                        @Override
//                                        public void onSuccess(Void aVoid) {
//                                            //Attendance Outside Taken
//                                        }
//                                    });
//                        }
//                    }
//                });
//            }
//            else{
//                if(holder.check_roll.isChecked()){
//                    holder.print.setText("Present");
//
//                    float TA = Integer.parseInt(model.getDNetA()) + 1;
//                    float TD = Integer.parseInt(model.getDNetD()) + 1;
//
//                    float percentage_value = (TA/TD)*100;
//                    String percentage = String.valueOf((int)percentage_value);
//                    String _dneta = String.valueOf((int)TA);
//                    String _dnetd = String.valueOf((int)TD);
//
//                    Map<String, Object> update_value = new HashMap<>();
//                    update_value.put("Department", model.getDepartment());
//                    update_value.put("Branch", model.getBranch());
//                    update_value.put("Semester", model.getSemester());
//                    update_value.put("ClassRoom", model.getClassRoom());
//                    update_value.put("ID", model.getID());
//                    update_value.put("FullName", model.getFullName());
//                    update_value.put("Date", date_value);
//                    update_value.put("MLA", model.getMLA());
//                    update_value.put("MLD", model.getMLD());
//                    update_value.put("DNetA", _dneta);
//                    update_value.put("DNetD", _dnetd);
//
//                    mStore.collection("UpdateAttendance").document(model.getID()).set(update_value)
//                            .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void aVoid) {
//                                    //Attendance Value Updated
//                                }
//                            });
//
//                    Map<String, Object> attendance = new HashMap<>();
//                    attendance.put("ID", model.getID());
//                    attendance.put("FullName", model.getFullName());
//                    attendance.put("EnrollmentNumber", model.getEnrollmentNumber());
//                    attendance.put("Department", model.getDepartment());
//                    attendance.put("Branch", model.getBranch());
//                    attendance.put("Attendance", "Present");
//                    attendance.put("DNetPercentage", percentage);
//
//                    mStore.collection("Attendance").document(date_value).collection(dept_value).document(branch_value).collection(sem_value)
//                            .document(class_value).collection(subject_value).document(model.getID()).set(attendance)
//                            .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void aVoid) {
//                                    //Attendance Taken
//                                }
//                            });
//
//                    Map<String, Object> attendance2 = new HashMap<>();
//                    attendance2.put("ID", model.getID());
//                    attendance2.put("FullName", model.getFullName());
//                    attendance2.put("EnrollmentNumber", model.getEnrollmentNumber());
//                    attendance2.put("Department", model.getDepartment());
//                    attendance2.put("Branch", model.getBranch());
//                    attendance2.put("Attendance", "Present");
//                    attendance2.put("TotalAttendance", _dneta);
//                    attendance2.put("TotalDay", _dnetd);
//                    attendance2.put("DNetPercentage", percentage);
//
//                    mStore.collection("Attendance").document(dept_value).collection(branch_value).document(sem_value).collection(class_value)
//                            .document(subject_value).collection("Students").document(model.getID()).set(attendance2)
//                            .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void aVoid) {
//                                    //Attendance Taken
//                                }
//                            });
//
//                    Map<String, Object> outside_attendance = new HashMap<>();
////                    outside_attendance.put("ID", model.getID());
////                    outside_attendance.put("FullName", model.getFullName());
////                    outside_attendance.put("EnrollmentNumber", model.getEnrollmentNumber());
////                    outside_attendance.put("Department", model.getDepartment());
////                    outside_attendance.put("Branch", model.getBranch());
//                    outside_attendance.put("DNetPercentage", percentage);
//
//                    mStore.collection("Attendance").document(dept_value).collection(branch_value).document(sem_value).collection(class_value)
//                            .document(model.getID()).update(outside_attendance)
//                            .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void aVoid) {
//                                    //Attendance Outside Taken
//                                }
//                            });
//
//                    holder.check_roll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                        @Override
//                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                            if(isChecked){
//                                holder.print.setText("Present");
//
//                                float TA = Integer.parseInt(model.getDNetA()) + 1;
//                                float TD = Integer.parseInt(model.getDNetD()) + 1;
//
//                                float percentage_value = (TA/TD)*100;
//                                String percentage = String.valueOf((int)percentage_value);
//                                String _dneta = String.valueOf((int)TA);
//                                String _dnetd = String.valueOf((int)TD);
//
//                                Map<String, Object> update_value = new HashMap<>();
//                                update_value.put("Department", model.getDepartment());
//                                update_value.put("Branch", model.getBranch());
//                                update_value.put("Semester", model.getSemester());
//                                update_value.put("ClassRoom", model.getClassRoom());
//                                update_value.put("ID", model.getID());
//                                update_value.put("FullName", model.getFullName());
//                                update_value.put("Date", date_value);
//                                update_value.put("MLA", model.getMLA());
//                                update_value.put("MLD", model.getMLD());
//                                update_value.put("DNetA", _dneta);
//                                update_value.put("DNetD", _dnetd);
//
//                                mStore.collection("UpdateAttendance").document(model.getID()).set(update_value)
//                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                            @Override
//                                            public void onSuccess(Void aVoid) {
//                                                //Attendance Value Updated
//                                            }
//                                        });
//
//                                Map<String, Object> attendance = new HashMap<>();
//                                attendance.put("ID", model.getID());
//                                attendance.put("FullName", model.getFullName());
//                                attendance.put("EnrollmentNumber", model.getEnrollmentNumber());
//                                attendance.put("Department", model.getDepartment());
//                                attendance.put("Branch", model.getBranch());
//                                attendance.put("Attendance", "Present");
//                                attendance.put("DNetPercentage", percentage);
//
//                                mStore.collection("Attendance").document(date_value).collection(dept_value).document(branch_value).collection(sem_value)
//                                        .document(class_value).collection(subject_value).document(model.getID()).set(attendance)
//                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                            @Override
//                                            public void onSuccess(Void aVoid) {
//                                                //Attendance Taken
//                                            }
//                                        });
//
//                                Map<String, Object> attendance2 = new HashMap<>();
//                                attendance2.put("ID", model.getID());
//                                attendance2.put("FullName", model.getFullName());
//                                attendance2.put("EnrollmentNumber", model.getEnrollmentNumber());
//                                attendance2.put("Department", model.getDepartment());
//                                attendance2.put("Branch", model.getBranch());
//                                attendance2.put("Attendance", "Present");
//                                attendance2.put("TotalAttendance", _dneta);
//                                attendance2.put("TotalDay", _dnetd);
//                                attendance2.put("DNetPercentage", percentage);
//
//                                mStore.collection("Attendance").document(dept_value).collection(branch_value).document(sem_value).collection(class_value)
//                                        .document(subject_value).collection("Students").document(model.getID()).set(attendance2)
//                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                            @Override
//                                            public void onSuccess(Void aVoid) {
//                                                //Attendance Taken
//                                            }
//                                        });
//
//                                Map<String, Object> outside_attendance = new HashMap<>();
////                            outside_attendance.put("ID", model.getID());
////                            outside_attendance.put("FullName", model.getFullName());
////                            outside_attendance.put("EnrollmentNumber", model.getEnrollmentNumber());
////                            outside_attendance.put("Department", model.getDepartment());
////                            outside_attendance.put("Branch", model.getBranch());
//                                outside_attendance.put("DNetPercentage", percentage);
//
//                                mStore.collection("Attendance").document(dept_value).collection(branch_value).document(sem_value).collection(class_value)
//                                        .document(model.getID()).update(outside_attendance)
//                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                            @Override
//                                            public void onSuccess(Void aVoid) {
//                                                //Attendance Outside Taken
//                                            }
//                                        });
//
//                            }
//                            else{
//                                holder.print.setText("Absent");
//
//                                float TA = Integer.parseInt(model.getDNetA());
//                                float TD = Integer.parseInt(model.getDNetD()) + 1;
//
//                                float percentage_value = (TA/TD)*100;
//                                String percentage = String.valueOf((int)percentage_value);
//                                String _dneta = String.valueOf((int)TA);
//                                String _dnetd = String.valueOf((int)TD);
//
//                                Map<String, Object> update_value = new HashMap<>();
//                                update_value.put("Department", model.getDepartment());
//                                update_value.put("Branch", model.getBranch());
//                                update_value.put("Semester", model.getSemester());
//                                update_value.put("ClassRoom", model.getClassRoom());
//                                update_value.put("ID", model.getID());
//                                update_value.put("FullName", model.getFullName());
//                                update_value.put("Date", date_value);
//                                update_value.put("MLA", model.getMLA());
//                                update_value.put("MLD", model.getMLD());
//                                update_value.put("DNetA", _dneta);
//                                update_value.put("DNetD", _dnetd);
//
//                                mStore.collection("UpdateAttendance").document(model.getID()).set(update_value)
//                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                            @Override
//                                            public void onSuccess(Void aVoid) {
//                                                //Attendance Value Updated
//                                            }
//                                        });
//
//                                Map<String, Object> attendance = new HashMap<>();
//                                attendance.put("ID", model.getID());
//                                attendance.put("FullName", model.getFullName());
//                                attendance.put("EnrollmentNumber", model.getEnrollmentNumber());
//                                attendance.put("Department", model.getDepartment());
//                                attendance.put("Branch", model.getBranch());
//                                attendance.put("Attendance", "Absent");
//                                attendance.put("DNetPercentage", percentage);
//
//                                mStore.collection("Attendance").document(date_value).collection(dept_value).document(branch_value).collection(sem_value)
//                                        .document(class_value).collection(subject_value).document(model.getID()).set(attendance)
//                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                            @Override
//                                            public void onSuccess(Void aVoid) {
//                                                //Attendance Taken
//                                            }
//                                        });
//
//                                Map<String, Object> attendance2 = new HashMap<>();
//                                attendance2.put("ID", model.getID());
//                                attendance2.put("FullName", model.getFullName());
//                                attendance2.put("EnrollmentNumber", model.getEnrollmentNumber());
//                                attendance2.put("Department", model.getDepartment());
//                                attendance2.put("Branch", model.getBranch());
//                                attendance2.put("Attendance", "Present");
//                                attendance2.put("TotalAttendance", _dneta);
//                                attendance2.put("TotalDay", _dnetd);
//                                attendance2.put("DNetPercentage", percentage);
//
//                                mStore.collection("Attendance").document(dept_value).collection(branch_value).document(sem_value).collection(class_value)
//                                        .document(subject_value).collection("Students").document(model.getID()).set(attendance2)
//                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                            @Override
//                                            public void onSuccess(Void aVoid) {
//                                                //Attendance Taken
//                                            }
//                                        });
//
//                                Map<String, Object> outside_attendance = new HashMap<>();
////                            outside_attendance.put("ID", model.getID());
////                            outside_attendance.put("FullName", model.getFullName());
////                            outside_attendance.put("EnrollmentNumber", model.getEnrollmentNumber());
////                            outside_attendance.put("Department", model.getDepartment());
////                            outside_attendance.put("Branch", model.getBranch());
//                                outside_attendance.put("DNetPercentage", percentage);
//
//                                mStore.collection("Attendance").document(dept_value).collection(branch_value).document(sem_value).collection(class_value)
//                                        .document(model.getID()).update(outside_attendance)
//                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                            @Override
//                                            public void onSuccess(Void aVoid) {
//                                                //Attendance Outside Taken
//                                            }
//                                        });
//                            }
//                        }
//                    });
//                }
//            }
     //   }
  //  }

    @NonNull
    @Override
    public AttendanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_attendance_design, parent, false);
        return new AttendanceViewHolder(view);
    }

    class AttendanceViewHolder extends RecyclerView.ViewHolder{

        TextView print;
        CheckBox check_roll;
        public AttendanceViewHolder(@NonNull View itemView) {
            super(itemView);
            print = itemView.findViewById(R.id.print_attendance);
            check_roll = itemView.findViewById(R.id.check_roll);
        }
    }
}

