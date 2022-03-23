package com.example.icollege;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class StudentAttendanceModifyAdapter extends FirestoreRecyclerAdapter<StudentAttendanceModifyModel, StudentAttendanceModifyAdapter.AttendanceModifyViewHolder> {

    String date_value, dept_value, branch_value, sem_value, class_value, subject_value;
    FirebaseFirestore mStore = FirebaseFirestore.getInstance();

    public StudentAttendanceModifyAdapter(@NonNull FirestoreRecyclerOptions<StudentAttendanceModifyModel> options, String date,
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
    protected void onBindViewHolder(@NonNull AttendanceModifyViewHolder holder, int position, @NonNull StudentAttendanceModifyModel model) {
        holder.check_roll.setText(model.getEnrollmentNumber());

        if(model.getAttendance().equals("Present")){
            holder.print_attendance.setText("Present");
            holder.check_roll.setChecked(true);
        }else {
            holder.print_attendance.setText("Absent");
            holder.check_roll.setChecked(false);
        }

        holder.check_roll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //Toast.makeText(v.getContext(), "Present" + model.getEnrollmentNumber(), Toast.LENGTH_SHORT).show();
                    holder.print_attendance.setText("Present");

                    Map<String, Object> modify_attendance = new HashMap<>();
                    modify_attendance.put("Attendance", "Present");

                    mStore.collection("Attendance").document(date_value).collection(dept_value).document(branch_value).collection(sem_value)
                            .document(class_value).collection(subject_value).document(model.getID()).update(modify_attendance)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    //Toast.makeText(holder.v.getContext(), "Success" + date_value + dept_value + branch_value + sem_value + class_value + subject_value, Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //Toast.makeText(holder.v.getContext(), "Fail" + date_value + dept_value + branch_value + sem_value + class_value + subject_value, Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    //Toast.makeText(v.getContext(), "Absent" + model.getEnrollmentNumber(), Toast.LENGTH_SHORT).show();
                    holder.print_attendance.setText("Absent");

                    Map<String, Object> attendance = new HashMap<>();
                    attendance.put("Attendance", "Absent");

                    mStore.collection("Attendance").document(date_value).collection(dept_value).document(branch_value)
                            .collection(sem_value).document(class_value).collection(subject_value).document(model.getID()).update(attendance)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    //Toast.makeText(holder.v.getContext(), "Success" + date_value + dept_value + branch_value + sem_value + class_value + subject_value, Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //Toast.makeText(holder.v.getContext(), "Fail" + date_value + dept_value + branch_value + sem_value + class_value + subject_value, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    @NonNull
    @Override
    public AttendanceModifyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_attendance_design, parent, false);
        return new AttendanceModifyViewHolder(view);
    }

    class AttendanceModifyViewHolder extends RecyclerView.ViewHolder{

        TextView print_attendance;
        CheckBox check_roll;
        View v;
        public AttendanceModifyViewHolder(@NonNull View itemView) {
            super(itemView);

            print_attendance = itemView.findViewById(R.id.print_attendance);
            check_roll = itemView.findViewById(R.id.check_roll);
            v = itemView;
        }
    }
}
