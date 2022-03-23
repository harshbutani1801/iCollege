package com.example.icollege;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class BlankActivity extends AppCompatActivity {

    Session Session;
    String user_type, mla_data, mld_data, dept_data, branch_data, sem_data, class_data, id_data, date_data, subject_data, dna_data, dnd_data;
    FirebaseFirestore mStore;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank);

        Session = new Session(getApplicationContext());
        mStore = FirebaseFirestore.getInstance();

        user_type = Session.getusertype();

        progressDialog = new ProgressDialog(BlankActivity.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        CollectionReference collectionReference = mStore.collection("UpdateAttendance");
        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    dept_data = documentSnapshot.getString("Department");
                    branch_data = documentSnapshot.getString("Branch");
                    sem_data = documentSnapshot.getString("Semester");
                    class_data = documentSnapshot.getString("ClassRoom");
                    id_data = documentSnapshot.getString("ID");
                    date_data = documentSnapshot.getString("Date");
                    mla_data = documentSnapshot.getString("MLA");
                    mld_data = documentSnapshot.getString("MLD");
                    dna_data = documentSnapshot.getString("DNetA");
                    dnd_data = documentSnapshot.getString("DNetD");

                    Map<String, Object> update_value = new HashMap<>();
                    update_value.put("Date", date_data);
                    update_value.put("MLA", mla_data);
                    update_value.put("MLD", mld_data);
                    update_value.put("DNetA", dna_data);
                    update_value.put("DNetD", dnd_data);

                    mStore.collection(dept_data).document(branch_data).collection(sem_data).document(class_data).collection("Students")
                            .document(id_data).update(update_value).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            //Value Updated
                        }
                    });

                    mStore.collection("Students").document(id_data).update(update_value).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            //Value Updated
                        }
                    });
                }
                if(user_type.equals("Faculty")){
                    startActivity(new Intent(getApplicationContext(), FacultyDashboard.class));
                }else if(user_type.equals("Head")){
                    startActivity(new Intent(getApplicationContext(), HeadDashboard.class));
                }else {
                    startActivity(new Intent(getApplicationContext(), PrincipalDashboard.class));
                }
            }
        });
    }
}