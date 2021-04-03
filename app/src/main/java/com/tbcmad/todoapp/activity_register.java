package com.tbcmad.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tbcmad.todoapp.model.EUser;
import com.tbcmad.todoapp.viewModel.TodoViewModel;
import com.tbcmad.todoapp.viewModel.UserViewModel;

public class activity_register extends AppCompatActivity {
    Button actRegister_btnSignUp, actRegister_btnLogin;
    int userId = -1; //-1
    EditText actRegister_txtFullName,actRegister_txtUsername, actRegister_txtPassword, actRegister_txtConfirmPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        actRegister_txtFullName = (EditText) findViewById(R.id.activityRegister_txtFullName);
        actRegister_txtUsername = (EditText) findViewById(R.id.activityRegister_txtUsername);
        actRegister_txtPassword = (EditText) findViewById(R.id.activityRegister_txtPassword);
        actRegister_txtConfirmPassword = (EditText) findViewById(R.id.activityRegister_txtConfirmPassword);

        actRegister_btnSignUp = (Button) findViewById(R.id.actRegister_btnSignUp);
        actRegister_btnLogin = (Button)findViewById(R.id.actRegister_btnLogin);

        actRegister_btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean emptyFName = actRegister_txtFullName.getText().toString().trim().equals("");
                boolean emptyUName = actRegister_txtUsername.getText().toString().trim().equals("");
                boolean emptyPassword = actRegister_txtPassword.getText().toString().trim().equals("");
                boolean emptyCPassword = actRegister_txtConfirmPassword.getText().toString().trim().equals("");

                if(emptyCPassword || emptyFName || emptyPassword || emptyUName)
                {
                    if(emptyFName) actRegister_txtFullName.setError("Enter Full Name!");
                    if(emptyUName) actRegister_txtUsername.setError("Enter Username!");
                    if(emptyPassword) actRegister_txtPassword.setError("Enter Password!");
                    if(emptyCPassword) actRegister_txtConfirmPassword.setError("Enter Confirm Password!");

                    return;
                }
                if(!actRegister_txtPassword.getText().toString().equals(actRegister_txtConfirmPassword.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(),"pw: "+actRegister_txtPassword.getText().toString()+" cnfPw: "+actRegister_txtConfirmPassword.getText().toString(),Toast.LENGTH_LONG).show();
                    actRegister_txtConfirmPassword.setError("Passwords didn't match!");
                    return;
                }

                saveUser();

            }
        });

        actRegister_btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(activity_register.this,LoginActivity.class);
                startActivity(intent1);
                finish();
            }
        });
    }

    void saveUser(){
        EUser eUser = new EUser();
        eUser.setFullname(actRegister_txtFullName.getText().toString());
        eUser.setUsername(actRegister_txtUsername.getText().toString());
        eUser.setPassword(actRegister_txtPassword.getText().toString());

        UserViewModel viewModel  = new ViewModelProvider(this).get(UserViewModel.class);

        if(userId!= -1){
            eUser.setId(userId);
           // viewModel.update(eUser);
        } else {
            viewModel.insert(eUser);
            Toast.makeText(getApplicationContext(), "User Registered Successfully! ", Toast.LENGTH_LONG).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent1 = new Intent(activity_register.this,LoginActivity.class);
                    startActivity(intent1);
                    finish();
                }
            },3000);

        }

    }
}