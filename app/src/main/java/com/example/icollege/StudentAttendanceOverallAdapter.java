package com.example.icollege;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class StudentAttendanceOverallAdapter extends FirestoreRecyclerAdapter<StudentAttendanceOverallModel, StudentAttendanceOverallAdapter.AttendanceOverallViewHolder> {

    public StudentAttendanceOverallAdapter(@NonNull FirestoreRecyclerOptions<StudentAttendanceOverallModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull AttendanceOverallViewHolder holder, int position, @NonNull StudentAttendanceOverallModel model) {
        holder.name.setText(model.getFullName());
        holder.branch.setText(model.getBranch());
        holder.dept.setText(model.getDepartment());

        float ta = Integer.parseInt(model.getMLPercentage());
        float td = Integer.parseInt(model.getDNetPercentage());

        float percentage_value = (ta+td)/2;
        String percentage = String.valueOf((int)percentage_value);

        holder.percentage.setText(percentage+"%");
    }

    @NonNull
    @Override
    public AttendanceOverallViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_attendance_overall_design, parent, false);
        return new AttendanceOverallViewHolder(view);
    }

    class AttendanceOverallViewHolder extends RecyclerView.ViewHolder{

        TextView name, branch, dept, percentage;
        public AttendanceOverallViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tv_stu_name);
            branch = itemView.findViewById(R.id.tv_stu_branch);
            dept = itemView.findViewById(R.id.tv_stu_dept);
            percentage = itemView.findViewById(R.id.tv_stu_percentage);
        }
    }
}
