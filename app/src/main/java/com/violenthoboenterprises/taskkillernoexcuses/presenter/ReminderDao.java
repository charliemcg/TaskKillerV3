package com.violenthoboenterprises.taskkillernoexcuses.presenter;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.violenthoboenterprises.taskkillernoexcuses.model.Reminder;

@Dao
public interface ReminderDao {

    @Insert
    void insert(Reminder reminder);

    @Update
    void update(Reminder reminder);

    @Delete
    void delete(Reminder reminder);

    @Query("SELECT * FROM reminder_table WHERE parentId = :parentId")
    Reminder getReminderByParent(int parentId);

}
