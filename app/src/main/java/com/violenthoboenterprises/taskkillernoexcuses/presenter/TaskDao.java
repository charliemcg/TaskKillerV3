package com.violenthoboenterprises.taskkillernoexcuses.presenter;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.violenthoboenterprises.taskkillernoexcuses.model.Task;

import java.util.List;

/*
 * These are the database calls on the task table
 */
@Dao
public interface TaskDao {
    @Insert
    void insert(Task task);

    @Update
    void update(Task task);

    @Delete
    void delete(Task task);

    //    @Query("SELECT * FROM task_table WHERE timestamp=0 UNION SELECT * FROM task_table WHERE timestamp>0 ORDER BY timestamp ASC, timeCreated DESC")
    @Query("SELECT * FROM task_table WHERE displayedTimestamp=0 UNION SELECT * FROM task_table WHERE displayedTimestamp>0 ORDER BY displayedTimestamp ASC, timeCreated DESC")
    LiveData<List<Task>> getAllTasks();

    @Query("SELECT timestamp FROM task_table")
    List<Integer> getAllTimestamps();

    @Query("SELECT id FROM task_table WHERE task= :taskName")
    int getTaskIdByName(String taskName);

    @Query("SELECT COUNT() FROM task_table WHERE timestamp!=0")
    int getDuesSet();

    @Query("SELECT * FROM task_table WHERE id= :id")
    Task getTaskById(Integer id);

    @Query("SELECT * FROM task_table")
    List<Task> getAllTasksAsTasks();

}
