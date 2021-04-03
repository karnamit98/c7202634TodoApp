package com.tbcmad.todoapp.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.tbcmad.todoapp.model.EUser;


@Dao
public interface UserDAO {

    @Insert
    void insert(EUser user);


    @Query("SELECT * FROM user_table WHERE id=:id")
    EUser getUserById(int id);

//    @Query("SELECT * FROM user_table WHERE username=:username")
//    EUser getUserByUsername(String username);

    @Query("SELECT * FROM user_table WHERE username=:username AND password=:password")
    EUser userLogin(String username, String password);


//    @Query("DELETE FROM user_table")
//    void deleteAll();

//    @Delete
//    void deleteById(EUser user);


//    @Update(onConflict = OnConflictStrategy.REPLACE)
//    void update(EUser... user);

   // @Query("SELECT * FROM user_table ORDER BY todo_date, priority desc")
   // LiveData<List<ETodo>> getAllTodos();

    //@Query("DELETE FROM user_table WHERE is_completed = 1")
    //void deleteCompleted();
}
