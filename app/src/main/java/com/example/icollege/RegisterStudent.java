package com.example.icollege;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegisterStudent extends AppCompatActivity {

    FirebaseFirestore mStore;
    ImageView arrow_back;
    TextView classname;
    Spinner department, branch, class_name, semester;
    ArrayList<String> dept, branch_data, four_data, six_data, eight_data, class_room;
    ArrayAdapter<String> _dept, _branch,_sem, _class;
    String dept_value, branch_value, sem_value;
    Button add_class, next;

    TextView department2, branch2, semester2;
    TextInputLayout tv_classroom2;
    TextInputEditText classroom2;
    ImageView back_btn2;
    ArrayList<String> dept_data2, branch_data2, four_data2, six_data2, eight_data2;
    ArrayAdapter<String> _dept2, _branch2, _sem2;
    String dept_value2;
    Button add_class2;

    String get_dept, get_branch, get_sem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_student);

        department = findViewById(R.id.spn_dept_name);
        branch = findViewById(R.id.spn_branch_name);
        class_name = findViewById(R.id.spn_class);
        semester = findViewById(R.id.spn_semester);
        next = findViewById(R.id.btn_next);
        arrow_back = findViewById(R.id.arrow_back);
        add_class = findViewById(R.id.btn_add_class);
        mStore = FirebaseFirestore.getInstance();

        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AdminDashboard.class));
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
                        class_room.clear();
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

        add_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                department.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                        get_dept = adapterView.getSelectedItem().toString();
//                    }
//                    @Override
//                    public void onNothingSelected(AdapterView<?> adapterView) {
//                    }
//                });
//
//                branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                        get_branch = adapterView.getSelectedItem().toString();
//                    }
//                    @Override
//                    public void onNothingSelected(AdapterView<?> adapterView) {
//                    }
//                });
//
//                semester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                        get_sem = adapterView.getSelectedItem().toString();
//                    }
//                    @Override
//                    public void onNothingSelected(AdapterView<?> adapterView) {
//                    }
//                });
                get_dept = department.getSelectedItem().toString();
                get_branch = branch.getSelectedItem().toString();
                get_sem = semester.getSelectedItem().toString();
                addclass(get_dept, get_branch, get_sem);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dept_data = department.getSelectedItem().toString();
                String branch_data = branch.getSelectedItem().toString();
                String sem_data = semester.getSelectedItem().toString();
                String class_data = class_name.getSelectedItem().toString();

                if(dept_data.equals("Select Department")){
                    Toast.makeText(RegisterStudent.this, "Please Select Department", Toast.LENGTH_SHORT).show();
                }else if(branch_data.equals("Select Branch")){
                    Toast.makeText(RegisterStudent.this, "Please Select Branch", Toast.LENGTH_SHORT).show();
                }else if(sem_data.equals("Select Semester")){
                    Toast.makeText(RegisterStudent.this, "Please Select Semester", Toast.LENGTH_SHORT).show();
                }else if(class_data.equals("Select Class Room")){
                    Toast.makeText(RegisterStudent.this, "Please Select Class Room", Toast.LENGTH_SHORT).show();
                }else{
                    Intent i = new Intent(RegisterStudent.this, AddStudent.class);
                    i.putExtra("Dept", dept_data);
                    i.putExtra("Branch", branch_data);
                    i.putExtra("Semester", sem_data);
                    i.putExtra("Class", class_data);
                    startActivity(i);
                }
            }
        });
    }

    private void addclass(String get_dept, String get_branch, String get_sem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.add_class_window, null);

        department2 = view.findViewById(R.id.tv_dept_name);
        branch2 = view.findViewById(R.id.tv_branch_name);
        semester2 = view.findViewById(R.id.tv_sem_name);
        classroom2 = view.findViewById(R.id.et_class_name);
        add_class2 = view.findViewById(R.id.btn_add_class);
        tv_classroom2 = view.findViewById(R.id.tv_class_name);

        builder.setView(view);

        department2.setText(get_dept);
        branch2.setText(get_branch);
        semester2.setText(get_sem);

        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        alertDialog.show();

//        dept_data2 = new ArrayList<>();
//        mStore.collection("Department").addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                dept_data2.clear();
//                dept_data2.add("Select Department");
//                for(DocumentSnapshot snapshot : value){
//                    dept_data2.add(snapshot.getString("Department"));
//                }
//                _dept2.notifyDataSetChanged();
//            }
//        });
//        _dept2 = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, dept_data2);
//        department2.setAdapter(_dept2);
//
//        branch_data2 = new ArrayList<>();
//        department2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                branch_data2.clear();
//                branch_data2.add("Select Branch");
//                dept_value2 = adapterView.getSelectedItem().toString();
//                mStore.collection(dept_value2 + " BRANCH").addSnapshotListener(new EventListener<QuerySnapshot>() {
//                    @Override
//                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                        for (DocumentSnapshot snapshot : value) {
//                            branch_data2.add(snapshot.getString(dept_value2 + " BRANCH"));
//                        }
//                        _branch2.notifyDataSetChanged();
//                    }
//                });
//                _branch2 = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, branch_data2);
//                branch2.setAdapter(_branch2);
//
//                if (dept_value2.equals("MTECH") || dept_value2.equals("MSC") || dept_value2.equals("MCA")) {
//                    four_data2 = new ArrayList<>();
//                    four_data2.add("Select Semester");
//                    four_data2.add("1");
//                    four_data2.add("2");
//                    four_data2.add("3");
//                    four_data2.add("4");
//                    _sem2 = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, four_data2);
//
//                } else if (dept_value2.equals("DIPLOMA") || dept_value2.equals("BSC") || dept_value2.equals("BCA")) {
//                    six_data2 = new ArrayList<>();
//                    six_data2.add("Select Semester");
//                    six_data2.add("1");
//                    six_data2.add("2");
//                    six_data2.add("3");
//                    six_data2.add("4");
//                    six_data2.add("5");
//                    six_data2.add("6");
//                    _sem2 = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, six_data2);
//                } else {
//                    eight_data2 = new ArrayList<>();
//                    eight_data2.add("Select Semester");
//                    eight_data2.add("1");
//                    eight_data2.add("2");
//                    eight_data2.add("3");
//                    eight_data2.add("4");
//                    eight_data2.add("5");
//                    eight_data2.add("6");
//                    eight_data2.add("7");
//                    eight_data2.add("8");
//                    _sem2 = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, eight_data2);
//                }
//                semester2.setAdapter(_sem2);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//            }
//        });

        add_class2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _dept = department2.getText().toString();
                String _branch = branch2.getText().toString();
                String _sem = semester2.getText().toString();
                String _class = classroom2.getText().toString();

                if(TextUtils.isEmpty(_class)){
                    tv_classroom2.setErrorEnabled(true);
                    tv_classroom2.setError("Please Enter Class Name");
                    tv_classroom2.requestFocus();
                    classroom2.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }
                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            tv_classroom2.setErrorEnabled(false);
                            tv_classroom2.setError(null);
                        }
                        @Override
                        public void afterTextChanged(Editable editable) {
                        }
                    });
                    return;
                }
                else{
                    Map<String, Object> class_room = new HashMap<>();
                    class_room.put("Class", _class);

                    mStore.collection(_dept).document(_branch).collection(_sem).document(_class).set(class_room).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(RegisterStudent.this, "Class Added Successfully", Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegisterStudent.this, "Class Adding Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), AdminDashboard.class));
    }
}