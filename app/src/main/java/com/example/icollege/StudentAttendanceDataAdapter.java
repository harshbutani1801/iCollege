package com.example.icollege;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class StudentAttendanceDataAdapter extends FirestoreRecyclerAdapter<StudentAttendanceDataModel, StudentAttendanceDataAdapter.AttendanceDataViewHolder> {

    public StudentAttendanceDataAdapter(@NonNull FirestoreRecyclerOptions<StudentAttendanceDataModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull AttendanceDataViewHolder holder, int position, @NonNull StudentAttendanceDataModel model) {
        holder.name.setText(model.getFullName());
        holder.branch.setText(model.getBranch());
        holder.dept.setText(model.getDepartment());

        float ta = Integer.parseInt(model.getTotalAttendance());
        float td = Integer.parseInt(model.getTotalDay());

        float percentage_value = (ta/td)*100;
        String percentage = String.valueOf((int)percentage_value);

        holder.percentage.setText(percentage+"%");
    }

    @NonNull
    @Override
    public AttendanceDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_attendance_data_design, parent, false);
        return new AttendanceDataViewHolder(view);
    }

    class AttendanceDataViewHolder extends RecyclerView.ViewHolder{

        TextView name, branch, dept, percentage;
        public AttendanceDataViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tv_stu_name);
            branch = itemView.findViewById(R.id.tv_stu_branch);
            dept = itemView.findViewById(R.id.tv_stu_dept);
            percentage = itemView.findViewById(R.id.tv_stu_percentage);
        }
    }
}
