package com.tbcmad.todoapp.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.tbcmad.todoapp.model.ETodo;
import com.tbcmad.todoapp.model.EUser;
import com.tbcmad.todoapp.model.TodoRoomDatabase;

import java.util.List;

public class UserRepository {

    private UserDAO  mUserDAO; // mTodoDAO;
    private LiveData<List<EUser>> allTodoList;

    public UserRepository(Application application){
        TodoRoomDatabase database = TodoRoomDatabase.getDatabase(application);
        mUserDAO = database.mUserDao();
        //allTodoList = mUserDao.getAllTodos();
    }

    public void insert(EUser eUser){
        new UserRepository.insertUserAysncTask(mUserDAO).execute(eUser);
    }

    public EUser getUserById(int id){
        return  mUserDAO.getUserById(id);
    }

    public EUser userLogin(String uesrname, String password){
        return  mUserDAO.userLogin(uesrname, password);
    }

//    public EUser userLogin(String uesrname, String password){
//        return  mUserDAO.userLogin(uesrname, password);
//    }


    private static class insertUserAysncTask extends AsyncTask<EUser, Void, Void>{
        private UserDAO mUserDao;
        private insertUserAysncTask(UserDAO userDAO){
            mUserDao=userDAO;
        }

        @Override
        protected Void doInBackground(EUser... eUsers) {
            mUserDao.insert(eUsers[0]);
            return null;
        }
    }




}
