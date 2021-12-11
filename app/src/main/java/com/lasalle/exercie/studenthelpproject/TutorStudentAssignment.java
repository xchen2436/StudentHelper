package com.lasalle.exercie.studenthelpproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lasalle.exercie.studenthelpproject.model.Tutor;
import com.lasalle.exercie.studenthelpproject.model.TutorAppointment;
import com.lasalle.exercie.studenthelpproject.model.TutorialAssignment;

import java.util.ArrayList;

public class TutorStudentAssignment extends AppCompatActivity implements ChildEventListener, View.OnClickListener, AdapterView.OnItemClickListener {

    Button btnRegister;
    EditText edTutorialdate, edTutorialDescription;
    TextView tvStudentId, tvTutorId;
    ListView lvTutorId;

    String studentid ;

    ArrayList<Tutor> listTutor;
    ArrayAdapter<Tutor> lvAdapter;
    DatabaseReference tutorDB, tutorChildDB;
    DatabaseReference tutorialAppointDB,tutorialAppointChild;
    DatabaseReference tutorialAssignmentDB,tutorialAssignmentChild;

    int count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_student_assignment);

        initialize();
    }

    private void initialize() {
        btnRegister = findViewById(R.id.btnRegister);
        edTutorialdate = findViewById(R.id.edTutorialDate);
        edTutorialDescription = findViewById(R.id.edTutorialDescription);
        tvStudentId = findViewById(R.id.tvStudenId);
        tvTutorId = findViewById(R.id.tvTutorId);
        lvTutorId = findViewById(R.id.lvListTutors);
        btnRegister.setOnClickListener(this);


        Intent  i = getIntent();
        studentid = i.getStringExtra("studentid");
        count  =Integer.valueOf( i.getStringExtra("count"));

        tvStudentId.setText(studentid);


        listTutor = new  ArrayList<Tutor>();
        lvAdapter = new ArrayAdapter<Tutor>(this, android.R.layout.simple_list_item_1,listTutor);
        tutorDB = FirebaseDatabase.getInstance().getReference("Tutor");
        tutorDB.addChildEventListener(this);
        lvTutorId.setAdapter(lvAdapter);

        lvTutorId.setOnItemClickListener(this);


        tutorialAppointDB = FirebaseDatabase.getInstance().getReference("TutorAppointment");
        tutorialAppointDB.addChildEventListener(this);
        tutorialAssignmentDB = FirebaseDatabase.getInstance().getReference("TutorialAssignment");
        tutorialAssignmentDB.addChildEventListener(this);



    }


    @Override
    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
        try {
            if (snapshot.exists()) {
                int id = Integer.parseInt(snapshot.child("tutorId").getValue().toString());
                String fname = snapshot.child("firstName").getValue().toString();
                String lname = snapshot.child("lastName").getValue().toString();
                String gender = snapshot.child("gender").getValue().toString();
                String bday = snapshot.child("dateOfBirth").getValue().toString();
                String email = snapshot.child("email").getValue().toString();
                String skill = snapshot.child("skill").getValue().toString();
                Tutor tutor = new Tutor(id, fname, lname, email, gender, bday,skill);
                listTutor.add(tutor);
                lvAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "there is a problem", Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            Log.d("ERROR",e.getMessage());
        }
    }

    @Override
    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

    }

    @Override
    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

    }

    @Override
    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }



    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        Tutor tutor = listTutor.get(i);

        tvTutorId.setText(String.valueOf(tutor.getTutorId()));


    }

    @Override
    public void onClick(View view) {
        try {
            if(edTutorialdate.getText().toString().length() > 0 && edTutorialDescription.getText().toString().length() > 0   ){
                TutorialAssignment tutoAssign = new TutorialAssignment();
                tutoAssign.setStudentId(Integer.valueOf(tvStudentId.getText().toString()));
                tutoAssign.setTutorId(Integer.valueOf(tvTutorId.getText().toString()));
                tutoAssign.setTutorialDate(edTutorialdate.getText().toString());
                tutoAssign.setTutorialDescription(edTutorialDescription.getText().toString());
                tutorialAssignmentChild = tutorialAssignmentDB.child(tvStudentId.getText().toString()).child(String.valueOf(count));
                tutorialAssignmentChild.setValue(tutoAssign);
                TutorAppointment tutorAppointment = new TutorAppointment();
                tutorAppointment.setStudentId(Integer.valueOf(tvStudentId.getText().toString()));
                tutorAppointment.setTutorId(Integer.valueOf(tvTutorId.getText().toString()));
                tutorAppointment.setTutorialDate(edTutorialdate.getText().toString());
                tutorAppointment.setTutorialDescription(edTutorialDescription.getText().toString());
                tutorialAppointChild = tutorialAppointDB.child(tvTutorId.getText().toString()).child(String.valueOf(count));
                tutorialAppointChild.setValue(tutorAppointment);
                count++;
                Intent i = new Intent(this, StudentProfilePage.class);
                i.putExtra("studentid",studentid);
                startActivity(i);
                this.finish();
            }else{
                Toast.makeText(this,"Please fill out all of the fields, try again.",Toast.LENGTH_LONG).show();
            }

        }catch (Exception e){

            Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_LONG).show();
        }

    }


}