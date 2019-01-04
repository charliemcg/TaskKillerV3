package com.violenthoboenterprises.taskkillernoexcuses.presenter;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.violenthoboenterprises.taskkillernoexcuses.model.Reminder;
import com.violenthoboenterprises.taskkillernoexcuses.model.Subtask;
import com.violenthoboenterprises.taskkillernoexcuses.model.Task;

import java.util.Calendar;
import java.util.GregorianCalendar;

/*
 * This is where the database is built
 */
@Database(entities = {Task.class, Subtask.class, Reminder.class}, version = 1)
public abstract class TaskDatabase extends RoomDatabase{

    private static String TAG = "TaskDatabase";
    private static TaskDatabase instance;

    public abstract TaskDao taskDao();
    public abstract SubtaskDao subtaskDao();
    public abstract ReminderDao reminderDao();

    //Instance
    public static synchronized TaskDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    TaskDatabase.class, "task_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    //Callback
    public static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    //Populating the task table with tutorial tasks
    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private TaskDao taskDao;
        private SubtaskDao subtaskDao;
        private PopulateDbAsyncTask(TaskDatabase db){
            taskDao = db.taskDao();
            subtaskDao = db.subtaskDao();
        }
        @Override
        protected Void doInBackground(Void... voids){
            Calendar calendar = new GregorianCalendar().getInstance();
            taskDao.insert(new Task(null, 0, "This is a task",
                    null, calendar.getTimeInMillis(), 0));
            taskDao.insert(new Task(null, 0, "Swipe left or right to delete a task",
                    null, calendar.getTimeInMillis(), 0));
            taskDao.insert(new Task(null, 0, "Press '+' to add a new task",
                    null, calendar.getTimeInMillis(), 0));
            return null;
        }

    }

}
