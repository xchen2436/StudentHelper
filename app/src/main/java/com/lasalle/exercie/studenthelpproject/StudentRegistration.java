package com.lasalle.exercie.studenthelpproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lasalle.exercie.studenthelpproject.model.Student;
import com.lasalle.exercie.studenthelpproject.model.User;

public class StudentRegistration extends AppCompatActivity implements View.OnClickListener {

    EditText edFirstName, edLastName, edGender, edEmail, edDOB,edUsername,edPassword,edStudentId;
    Button btnRegister,btnBackFromRegisterStudent;

    DatabaseReference studentHelpDatabaseStudent,studentHelpDatabaseUser;
    private static int count = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_registration);
        initialize();
    }

    private void initialize() {
        edFirstName =  findViewById(R.id.edFirstName);
        edLastName = findViewById(R.id.edLastName);
        edGender = findViewById(R.id.edGender);
        edEmail = findViewById(R.id.edEmail);
        edDOB = findViewById(R.id.edDOB);
        edUsername = findViewById(R.id.edUserName);
        edPassword = findViewById(R.id.edPassword);
        edStudentId = findViewById(R.id.edStudentId);
        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);
        btnBackFromRegisterStudent = findViewById(R.id.btnBackFromRegisterStudent);
        btnBackFromRegisterStudent.setOnClickListener(this);

        studentHelpDatabaseStudent = FirebaseDatabase.getInstance().getReference("Student");
        studentHelpDatabaseUser = FirebaseDatabase.getInstance().getReference("Users");
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch(id){
            case R.id.btnRegister:
                register();
                break;
            case R.id.btnBackFromRegisterStudent:
                BackToMain();
                    break;
        }
    }
    private void BackToMain() {
        Intent i = new Intent(this ,MainActivity.class);
        startActivity(i);
        this.finish();

    }

    private void register() {
        try{

            String dateOfBirth = edDOB.getText().toString();
            String email = edEmail.getText().toString();
            String firstName = edFirstName.getText().toString();
            String lastName = edLastName.getText().toString();
            String gender = edGender.getText().toString();
            String username = edUsername.getText().toString();
            String password = edPassword.getText().toString();
            int studentId = Integer.valueOf(edStudentId.getText().toString());
            int userid = studentId;
            String title = "student";

            boolean validity = false;

            if(dateOfBirth.equals("")){
                validity = true;
                Toast.makeText(getApplicationContext(), "Please enter Date of Birth", Toast.LENGTH_SHORT).show();
            }else if(email.equals("")){
                validity = true;
                Toast.makeText(getApplicationContext(), "Please enter Email", Toast.LENGTH_SHORT).show();
            }else if(firstName.equals("")){
                validity = true;
                Toast.makeText(getApplicationContext(), "Please enter firstName", Toast.LENGTH_SHORT).show();
            }
            else if(lastName.equals("")){
                validity = true;
                Toast.makeText(getApplicationContext(), "Please enter last name", Toast.LENGTH_SHORT).show();
            }
            else if(gender.equals("")){
                validity = true;
                Toast.makeText(getApplicationContext(), "Please enter gender", Toast.LENGTH_SHORT).show();
            }
            else if(username.equals("")){
                validity = true;
                Toast.makeText(getApplicationContext(), "Please enter username", Toast.LENGTH_SHORT).show();
            }
            else if(password.equals("")){
                validity = true;
                Toast.makeText(getApplicationContext(), "Please enter password", Toast.LENGTH_SHORT).show();
            }
            else if(studentId==0 || studentId<0){
                validity = true;
                Toast.makeText(getApplicationContext(), "Please enter Student Id", Toast.LENGTH_SHORT).show();
            }


            if(validity == false) {

                Student oneStudent = new Student(studentId, firstName, lastName, email, gender, dateOfBirth);
                User oneUser = new User(userid, username, password, title);
                studentHelpDatabaseStudent.child(String.valueOf(studentId)).setValue(oneStudent);
                studentHelpDatabaseUser.child(username).setValue(oneUser);
                Toast.makeText(this, "Registered successfully!", Toast.LENGTH_LONG).show();
                clearWidgets();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                this.finish();
            }
        }catch(Exception e){
            Toast.makeText(this,"You need to fill all the tables ",Toast.LENGTH_LONG).show();
        }




    }

    private void clearWidgets() {
        edFirstName.setText(null);
        edLastName.setText(null);
        edDOB.setText(null);
        edEmail.setText(null);
        edGender.setText(null);
        edUsername.setText(null);
        edPassword.setText(null);
        edStudentId.setText(null);
    }
}