package com.tbcmad.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tbcmad.todoapp.model.ETodo;
import com.tbcmad.todoapp.viewModel.TodoViewModel;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    Fragment fragment;
    FloatingActionButton floatingActionButton;
    TodoViewModel todoViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        fragment = new ListTodoFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.list_activity_container, fragment)
                .commit();

        todoViewModel = new ViewModelProvider(this).get(TodoViewModel.class);


        floatingActionButton = findViewById(R.id.floatingActionButton);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                startActivity(intent);
            }
        });

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("todo_pref", 0);
        int userId = preferences.getInt("userId", -1);
        String userFullName = preferences.getString("userFullName", "");

        if(userId != -1){       //Logged In
            setTitle("Todo App: "+userFullName);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnu_delete_all:
                Toast.makeText(getApplicationContext(),"Deleted All Todos!", Toast.LENGTH_LONG).show();
                todoViewModel.deleteAll();
                break;
            case R.id.mnu_delete_cpmpleted:
                Toast.makeText(getApplicationContext(),"Deleted Completed Todos!", Toast.LENGTH_LONG).show();
                todoViewModel.deleteCompleted();
                break;
            case R.id.mnu_userProfile:
                Toast.makeText(getApplicationContext(),"Opening User Profile!", Toast.LENGTH_LONG).show();
                //todoViewModel.deleteCompleted();
//                SharedPreferences preferences1 = getApplicationContext().getSharedPreferences("todo_pref", 0);
//                int userId = preferences1.getInt("userId", -1);
                Intent intent1= new Intent(this, ProfileActivity.class);
                //intent1.putExtra("userId", userId);
                startActivity(intent1);
                finish();
                break;
            case R.id.mnu_logout:
                SharedPreferences preferences = getApplicationContext().getSharedPreferences("todo_pref", 0);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();
                setTitle("Todo App");
                Intent intent= new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}