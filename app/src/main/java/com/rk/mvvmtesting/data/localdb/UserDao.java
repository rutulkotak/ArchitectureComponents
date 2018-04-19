package com.rk.mvvmtesting.data.localdb;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by Rutul Kotak
 *
 * Perform CRUD(Create, Read, Update, Delete) operations
 */

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void bulkInsert(User... users);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM user ORDER BY id DESC")
    LiveData<List<User>> getAllUsers();

    @Query("SELECT * FROM user WHERE ID = :id")
    LiveData<User> getUserById(int id);
}