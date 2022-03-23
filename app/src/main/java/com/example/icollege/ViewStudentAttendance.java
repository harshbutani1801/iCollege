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

public class ViewStudentAttendance extends AppCompatActivity {

    Button overall_attendance, subject_attendance;
    ImageView arrow_back;
    Session Session;
    String user_type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student_attendance);

        arrow_back = findViewById(R.id.arrow_back);
        Session = new Session(getApplicationContext());
        overall_attendance = findViewById(R.id.btn_overall_attendance);
        subject_attendance = findViewById(R.id.btn_subject_attendance);

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

        overall_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user_type.equals("Faculty") || user_type.equals("Head")){
                    startActivity(new Intent(getApplicationContext(), OverallAttendance.class));
                }else{
                    startActivity(new Intent(getApplicationContext(), OverallAttendance2.class));
                }
            }
        });

        subject_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user_type.equals("Faculty") || user_type.equals("Head")){
                    startActivity(new Intent(getApplicationContext(), SubjectAttendance.class));
                }else{
                    startActivity(new Intent(getApplicationContext(), SubjectAttendance2.class));
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