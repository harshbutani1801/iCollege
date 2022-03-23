package com.example.icollege;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class FacultyRcyAdapter extends FirestoreRecyclerAdapter<View_Faculty_Model, FacultyRcyAdapter.View_Holder> {

    public FacultyRcyAdapter(FirestoreRecyclerOptions<View_Faculty_Model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull View_Holder holder, int position, @NonNull View_Faculty_Model model) {
        holder.id.setText(model.getUserID());
        holder.name.setText(model.getFullName());
        holder.dept.setText(model.getDepartment());
        holder.desg.setText(model.getDesignation());
        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), FacultyRcyItem.class);
                i.putExtra("UserID", model.getUserID());
                view.getContext().startActivity(i);
            }
        });
    }

    @NonNull
    @Override
    public View_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcy_faculty_design, parent, false);
        return new View_Holder(view);
    }

    class View_Holder extends RecyclerView.ViewHolder{

        TextView id, name, dept, desg;
        View v;
        public View_Holder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.tv_id);
            name = itemView.findViewById(R.id.tv_name);
            dept = itemView.findViewById(R.id.tv_dept);
            desg = itemView.findViewById(R.id.tv_desg);
            v = itemView;
        }
    }
}
