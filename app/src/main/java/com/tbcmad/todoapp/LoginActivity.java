package com.tbcmad.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tbcmad.todoapp.model.EUser;
import com.tbcmad.todoapp.viewModel.TodoViewModel;
import com.tbcmad.todoapp.viewModel.UserViewModel;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin, actLogin_btnRegister;
    EditText actLogin_txtUsername, actLogin_txtPassword;
    TextView actLogin_txtLoginFailed;
    public String TAG = "user test";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.login_activity_btn_login);
        actLogin_txtUsername = findViewById(R.id.activityLogin_txtUsername);
        actLogin_txtPassword = findViewById(R.id.activityLogin_txtPassword);
        actLogin_btnRegister = (Button) findViewById(R.id.actLogin_btnRegister);
        actLogin_txtLoginFailed = (TextView) findViewById(R.id.actLogin_txtLoginFailed);
        actLogin_txtLoginFailed.setText("");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean emptyUName = actLogin_txtUsername.getText().toString().trim().equals("");
                boolean emptyPassword = actLogin_txtPassword.getText().toString().trim().equals("");
                if (emptyUName || emptyPassword) {
                    if(emptyUName) {actLogin_txtUsername.setError("Enter Username!"); }
                    if(emptyPassword) {actLogin_txtPassword.setError("Enter Password!"); }
                    return;
                }

                logIn();

            }
        });

        actLogin_btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),"Opening SignUp Page",Toast.LENGTH_SHORT).show();
                Intent intent1= new Intent(LoginActivity.this,activity_register.class);
                startActivity(intent1);
                finish();
            }
        });
    }

    void logIn(){
        //Check valid user for login
        UserViewModel viewModel = new ViewModelProvider(this).get(UserViewModel.class);

        Toast.makeText(getApplicationContext(),"Username:"+actLogin_txtUsername.getText().toString()+" \nPassword:"
        +actLogin_txtPassword.getText().toString(), Toast.LENGTH_LONG).show();

        //EUser eUser = viewModel.getUserById(1);
        //actLogin_txtUsername.setText(eUser.getFullname());


        EUser eUser =  viewModel.userLogin(actLogin_txtUsername.getText().toString(), actLogin_txtPassword.getText().toString());

        //viewModel.insert(new EUser("Uzumaki Naruto", "naruto", "naruto"));
        //EUser eUser =  viewModel.userLogin("naruto", "naruto");

        //Log.d(TAG,eUser.getFullname());



        if(!(eUser==null)) {
            SharedPreferences preference = getApplicationContext().getSharedPreferences("todo_pref", 0);
            SharedPreferences.Editor editor = preference.edit();
            editor.putBoolean("authentication", true);
            editor.putInt("userId",eUser.getId());
            editor.putString("userFullName",eUser.getFullname());
            editor.commit();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);

            startActivity(intent);
            finish();
        }
        else {
            //display login failed
            actLogin_txtLoginFailed.setText("Login Failed!");
        }
    }
}