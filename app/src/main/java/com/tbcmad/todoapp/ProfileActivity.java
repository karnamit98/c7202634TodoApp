package com.tbcmad.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tbcmad.todoapp.model.EUser;
import com.tbcmad.todoapp.viewModel.TodoViewModel;
import com.tbcmad.todoapp.viewModel.UserViewModel;

public class ProfileActivity extends AppCompatActivity {
    TextView actProfile_txtFullName, actProfile_txtUsername, actProfile_txtPassword;
    Button actProfile_btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        actProfile_txtFullName = findViewById(R.id.actProfile_txtFullName);
        actProfile_txtUsername = findViewById(R.id.actProfile_txtUsername);
        actProfile_txtPassword = findViewById(R.id.actProfile_txtPassword);
        actProfile_btnBack = findViewById(R.id.actProfile_btnBack);
        SharedPreferences preferences1 = getApplicationContext().getSharedPreferences("todo_pref", 0);
        int userId = preferences1.getInt("userId", -1);

        UserViewModel viewModel = new ViewModelProvider(this).get(UserViewModel.class);
        EUser user = viewModel.getUserById(userId);
        actProfile_txtFullName.setText("Full Name: "+user.getFullname());
        actProfile_txtUsername.setText("Username: "+user.getUsername());
        actProfile_txtPassword.setText("Password: "+user.getPassword());

        actProfile_btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}