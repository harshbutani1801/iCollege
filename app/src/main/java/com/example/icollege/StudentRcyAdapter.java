package com.example.icollege;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dinuscxj.progressbar.CircleProgressBar;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class StudentRcyAdapter extends FirestoreRecyclerAdapter<View_Student_Model, StudentRcyAdapter.View_Holder> {

    public StudentRcyAdapter(@NonNull FirestoreRecyclerOptions<View_Student_Model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull View_Holder holder, int position, @NonNull View_Student_Model model) {
        holder.id.setText(model.getID());
        holder.name.setText(model.getFullName());
        holder.branch.setText(model.getBranch());
        holder.dept.setText(model.getDepartment());
        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), StudentRcyItem.class);
                i.putExtra("StudentID", model.getID());
                view.getContext().startActivity(i);
            }
        });
    }

    @NonNull
    @Override
    public View_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcy_student_design, parent, false);
        return new View_Holder(view);
    }

    class View_Holder extends RecyclerView.ViewHolder{

        TextView id, name, branch, dept;
        View v;
        public View_Holder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.tv_stu_id);
            name = itemView.findViewById(R.id.tv_stu_name);
            branch = itemView.findViewById(R.id.tv_stu_branch);
            dept = itemView.findViewById(R.id.tv_stu_dept);
            v = itemView;
        }
    }
}
