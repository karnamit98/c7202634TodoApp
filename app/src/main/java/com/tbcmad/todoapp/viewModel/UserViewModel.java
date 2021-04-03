package com.tbcmad.todoapp.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tbcmad.todoapp.data.TodoRepository;
import com.tbcmad.todoapp.data.UserRepository;
import com.tbcmad.todoapp.model.ETodo;
import com.tbcmad.todoapp.model.EUser;

import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private UserRepository mUserRepository;
    private LiveData<List<ETodo>> allTodos;
    public UserViewModel(@NonNull Application application){
        super(application);
        //super();
        mUserRepository=new UserRepository(application);
       // allTodos = mUserRepository.getAllUserList();
    }

   // public LiveData<List<ETodo>> getAllTodos() {
    //    return allTodos;
  //  }

    public void insert(EUser eUser) {
        mUserRepository.insert(eUser);
    }

   // public  void deleteById(EUser user){
       // mUserRepository.delete(user);
   // }

    public EUser getUserById(int id) {
        return mUserRepository.getUserById(id);
    }

    public EUser userLogin(String username, String Password) {
        return mUserRepository.userLogin(username, Password);
    }

   // public void update(EUser user){
     //   mUserRepository.update(user);
   // }

   // public void deleteAll() {mUserRepository.deleteAll();}

}
