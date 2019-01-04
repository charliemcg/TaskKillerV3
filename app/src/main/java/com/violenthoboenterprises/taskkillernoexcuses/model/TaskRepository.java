package com.violenthoboenterprises.taskkillernoexcuses.model;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.violenthoboenterprises.taskkillernoexcuses.presenter.TaskDao;
import com.violenthoboenterprises.taskkillernoexcuses.presenter.TaskDatabase;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class TaskRepository {

    private static String TAG = "TaskRepository";

    private TaskDao taskDao;
    private LiveData<List<Task>> allTasks;

    TaskRepository(Application application) {
        TaskDatabase taskDatabase = TaskDatabase.getInstance(application);
        taskDao = taskDatabase.taskDao();
        allTasks = taskDao.getAllTasks();
    }

    void insert(Task task) {
        new InsertTaskAsyncTask(taskDao).execute(task);
    }

    void update(Task task) {
        new UpdateTaskAsyncTask(taskDao).execute(task);
    }

    public void delete(Task task) {
        new DeleteTaskAsyncTask(taskDao).execute(task);
    }

    LiveData<List<Task>> getAllTasks(){
        return allTasks;
    }

    public List<Integer> getAllTimestamps() {
        AsyncTask<Void, Void, List<Integer>> result = new GetAllTimestampsAsyncTask(taskDao).execute();
        List<Integer> allTimestamps;
        try {
            allTimestamps = result.get();
            return allTimestamps;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getTaskIdByName(String taskName) {
        AsyncTask<String, Void, Integer> result = new GetTaskIdByNameAsyncTask(taskDao).execute(taskName);
        try {
            return result.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getDuesSet() {
        AsyncTask<Void, Void, Integer> result = new GetDuesSetAsyncTask(taskDao).execute();
        try{
            return result.get();
        }catch (InterruptedException | ExecutionException e){
            e.printStackTrace();
        }
        return 0;
    }

    public Task getTaskById(int id) {
        AsyncTask<Integer, Void, Task> result = new GetTaskByIdAsyncTask(taskDao).execute(id);
        try{
            return result.get();
        }catch (InterruptedException | ExecutionException e){
            e.printStackTrace();
        }
        return null;
    }

    public List<Task> getAllTasksAsTasks() {
        AsyncTask<Void, Void, List<Task>> result = new GetAllTasksAsTasksAsyncTask(taskDao).execute();
        try{
            return result.get();
        }catch (InterruptedException | ExecutionException e){
            e.printStackTrace();
        }
        return null;
    }

    //Performing these tasks off of the UI thread
    private static class InsertTaskAsyncTask extends AsyncTask<Task, Void, Void> {
        private TaskDao taskDao;

        InsertTaskAsyncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            taskDao.insert(tasks[0]);
            return null;
        }
    }

    private static class UpdateTaskAsyncTask extends AsyncTask<Task, Void, Void> {
        private TaskDao taskDao;

        UpdateTaskAsyncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            taskDao.update(tasks[0]);
            return null;
        }
    }

    private static class DeleteTaskAsyncTask extends AsyncTask<Task, Void, Void> {
        private TaskDao taskDao;

        DeleteTaskAsyncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            taskDao.delete(tasks[0]);
            return null;
        }
    }

    private static class GetAllTimestampsAsyncTask extends AsyncTask<Void, Void, List<Integer>> {
        private TaskDao taskDao;

        GetAllTimestampsAsyncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected List<Integer> doInBackground(Void... voids) {
            return taskDao.getAllTimestamps();
        }
    }

    private static class GetTaskIdByNameAsyncTask extends AsyncTask<String, Void, Integer> {
        private TaskDao taskDao;
        GetTaskIdByNameAsyncTask(TaskDao taskDao) {this.taskDao = taskDao;}

        @Override
        protected Integer doInBackground(String... strings) {
            return taskDao.getTaskIdByName(strings[0]);
        }
    }

    private static class GetDuesSetAsyncTask extends AsyncTask<Void, Void, Integer> {
        private TaskDao taskDao;
        GetDuesSetAsyncTask(TaskDao taskDao) {this.taskDao = taskDao;}

        @Override
        protected Integer doInBackground(Void... voids) {
            return taskDao.getDuesSet();
        }
    }

    private static class GetTaskByIdAsyncTask extends AsyncTask<Integer, Void, Task> {
        private TaskDao taskDao;
        GetTaskByIdAsyncTask(TaskDao taskDao) {this.taskDao = taskDao;}

        @Override
        protected Task doInBackground(Integer... integers) {
            return taskDao.getTaskById(integers[0]);
        }
    }

    private static class GetAllTasksAsTasksAsyncTask extends AsyncTask<Void, Void, List<Task>>{
        private TaskDao taskDao;
        GetAllTasksAsTasksAsyncTask(TaskDao taskDao) {this.taskDao = taskDao;}

        @Override
        protected List<Task> doInBackground(Void... voids) {
            return taskDao.getAllTasksAsTasks();
        }
    }

}
