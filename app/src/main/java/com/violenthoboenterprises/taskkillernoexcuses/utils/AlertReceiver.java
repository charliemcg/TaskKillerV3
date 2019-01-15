package com.violenthoboenterprises.taskkillernoexcuses.utils;

import android.app.AlarmManager;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;

import android.util.Log;
import android.widget.RemoteViews;

import com.violenthoboenterprises.taskkillernoexcuses.Database;
import com.violenthoboenterprises.taskkillernoexcuses.activities.MainActivity;
import com.violenthoboenterprises.taskkillernoexcuses.R;
import com.violenthoboenterprises.taskkillernoexcuses.model.Task;
import com.violenthoboenterprises.taskkillernoexcuses.model.TaskRepository;
import com.violenthoboenterprises.taskkillernoexcuses.model.TaskViewModel;
import com.violenthoboenterprises.taskkillernoexcuses.presenter.ReminderDao;
import com.violenthoboenterprises.taskkillernoexcuses.presenter.SubtaskDao;
import com.violenthoboenterprises.taskkillernoexcuses.presenter.TaskDao;
import com.violenthoboenterprises.taskkillernoexcuses.presenter.TaskDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AlertReceiver extends BroadcastReceiver {

    String TAG = this.getClass().getSimpleName();
    private Task task;
//    private String taskTitle;
//    private String taskRepeatInterval;
//    private long taskTimestamp;
//    private TaskViewModel tvm;
//    private Application app;
    private Context context;
    private static TaskDatabase instance;
    SharedPreferences preferences;

    @Override
    public void onReceive(Context context, Intent intent) {

        //Initialising variables for holding values from database
        boolean boolSnoozeStatus = intent.getBooleanExtra("snoozeStatus", false);
        preferences = context.getSharedPreferences("com.violenthoboenterprises.taskkiller",
                Context.MODE_PRIVATE);
        this.context = context;
        int id = intent.getIntExtra("task", 0);
//        this.task = (Task) intent.getSerializableExtra("task");

        this.task = getTaskById(id);
        Log.d(TAG, "task: " + task.getTask());
//        this.task = MainActivity.taskViewModel.getTask(id);

//        Serializable serializedTask = intent.getSerializableExtra("task");
//        CharSequence sequenceViewModel = intent.getCharSequenceExtra("viewModel");
//        byte[] bytes = new byte[0];
//        try {
//            bytes = sequenceViewModel.toString().getBytes("UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
//        ObjectInputStream is = null;
//        try {
//            is = new ObjectInputStream(in);
//            task = (Task) is.readObject();
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        tvm = (TaskViewModel) sequenceViewModel;
//        this.task = (Task) blah;
//        Serializable blah = intent.getSerializableExtra("viewModel");
//        Log.d(TAG, "blah: " + blah);
//        Serializable serializedViewModel = intent.getSerializableExtra("viewModel");
//        Parcelable serializedViewModel = intent.getParcelableExtra("viewModel");

//        Log.d(TAG, "blah: " + blah);
//        https://github.com/commonsguy/cw-omnibus/tree/v8.12/Parcelable/Marshall/app/src/main/java/com/commonsware/android/parcelable/marshall

//        https://stackoverflow.com/questions/3736058/java-object-to-byte-and-byte-to-object-converter-for-tokyo-cabinet/3736247#3736247

//        int parsedId = 0;
//        JSONObject jsonTask = null;
//        try {
//            jsonTask = new JSONObject((String) serializedTask);
//            parsedId = jsonTask.getInt("id");
//            taskTitle = jsonTask.getString("task");
//            taskRepeatInterval = jsonTask.getString("repeatInterval");
//            taskTimestamp = jsonTask.getLong("timestamp");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        //updating the task in case any of the values have changed
//        if(MainActivity.taskViewModel != null) {
//            task = MainActivity.taskViewModel.getTask(parsedId);
//            taskTitle = task.getTask();
//            taskRepeatInterval = task.getRepeatInterval();
//            taskTimestamp = task.getTimestamp();
//        }

//        tvm = ViewModelProviders.of((FragmentActivity) context).get(TaskViewModel.class);

        //retrieving task properties necessary for setting notification
        createNotification(context, "", boolSnoozeStatus);

    }

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

    public static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
//            new TaskDatabase.PopulateDbAsyncTask(instance).execute();
        }
    };

    void update(Task task) {
        TaskDatabase taskDatabase = getInstance(context);
        TaskDao taskDao = taskDatabase.taskDao();
        new UpdateTaskAsyncTask(taskDao).execute(task);
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

    public Task getTaskById(int id) {
        TaskDatabase taskDatabase = getInstance(context);
        TaskDao taskDao = taskDatabase.taskDao();
        AsyncTask<Integer, Void, Task> result = new AlertReceiver.GetTaskByIdAsyncTask(taskDao).execute(id);
        try{
            return result.get();
        }catch (InterruptedException | ExecutionException e){
            e.printStackTrace();
        }
        return null;
    }

    public List<Integer> getAllTimestamps() {
        TaskDatabase taskDatabase = getInstance(context);
        TaskDao taskDao = taskDatabase.taskDao();
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

    private static class GetTaskByIdAsyncTask extends AsyncTask<Integer, Void, Task> {
        private TaskDao taskDao;
        GetTaskByIdAsyncTask(TaskDao taskDao) {this.taskDao = taskDao;}

        @Override
        protected Task doInBackground(Integer... integers) {
            return taskDao.getTaskById(integers[0]);
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

    public void createNotification(Context context,
                                   String msgAlert, boolean snoozeStatus) {

        //defining intent and action to perform
        PendingIntent notificIntent = PendingIntent.getActivity(context, 1,
                new Intent(context, MainActivity.class), 0);

        NotificationCompat.Builder builder;
        RemoteViews remoteViews;

        //allows for notifications
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        //Setting values to custom notification view
        remoteViews = new RemoteViews(context.getPackageName(), R.layout.notification);
//        remoteViews.setTextViewText(R.id.notif_title, task.getTask());
        remoteViews.setTextViewText(R.id.notif_title, task.getTask());

        //Setting up notification channel for Oreo
        final String notificChannelId = "notification_channel";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    notificChannelId, "notifications",
                    NotificationManager.IMPORTANCE_DEFAULT);

            notificationChannel.setDescription("Notifications about task being due");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.parseColor(preferences.getString(StringConstants.HIGHLIGHT_COLOR_KEY, "#ff34ff00")));
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        //Building the notification
        builder = new NotificationCompat.Builder(context, notificChannelId)
                .setSmallIcon(R.drawable.small_notific_icon)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher_og))
                .setTicker(msgAlert)
                .setWhen(0)
//                .setContentText(task.getTask())
                .setContentText(task.getTask())
                .setStyle(new NotificationCompat.BigTextStyle())
                .setColorized(true)
                .setColor(context.getResources().getColor(R.color.lightGreen))
                .setCustomContentView(remoteViews)
                .setLights(66666666, 500, 2000)
                .setDefaults(NotificationCompat.DEFAULT_SOUND)
                .setContentIntent(notificIntent)
                .setAutoCancel(true);

        Log.d(TAG, "repeat: " + task.getRepeatInterval());

        //Can only show notification if user has feature enabled. Non repeating tasks need
        //no further processing than notify()
        if (task.getRepeatInterval() == null) {
//        if(taskRepeatInterval == null){

            notificationManager.notify(1, builder.build());

        //need to set up next notification for repeating task
        } else {

            //updating the task in case any of the values have changed
//            task = MainActivity.taskViewModel.getTask(task.getId());

            notificationManager.notify(1, builder.build());

            //snoozed notifications cannot corrupt regular repeating notifications
            if (task.getRepeatInterval().equals("day") && !snoozeStatus) {
//            if (taskRepeatInterval.equals("day") && !snoozeStatus){

                //App crashes if exact duplicate of timestamp is saved in database. Attempting to
                // detect duplicates and then adjusting the timestamp on the millisecond level
                long futureStamp = task.getTimestamp() + AlarmManager.INTERVAL_DAY;
//                long futureStamp = taskTimestamp + AlarmManager.INTERVAL_DAY;
                futureStamp = getFutureStamp(futureStamp);
                task.setTimestamp(futureStamp);
//                MainActivity.taskViewModel.update(task);
                update(task);

                Intent alertIntent = new Intent(context, AlertReceiver.class);
                alertIntent.putExtra
                        ("snoozeStatus", false);
//                alertIntent.putExtra("task", task);
                alertIntent.putExtra("task", task.getId());

                //Setting alarm
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        context, task.getId(), alertIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

                Calendar alarmCalendar = Calendar.getInstance();
                Long diff;

                //Setting a repeat alarm
                Calendar currentCal = Calendar.getInstance();
                Calendar futureCal = Calendar.getInstance();
                futureCal.setTimeInMillis(futureStamp);
                diff = futureCal.getTimeInMillis() - currentCal.getTimeInMillis();
                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                //checking if timestamp has been updated or not
                if (diff < 86400000) {
                    /*MainActivity.*/alarmManager.set(AlarmManager.RTC, futureStamp, pendingIntent);
                } else {
                    long daysOut = diff / 86400000;
                    /*MainActivity.*/alarmManager.set(AlarmManager.RTC, (futureStamp - (86400000 * daysOut)),
                            pendingIntent);
                }

                alarmCalendar.setTimeInMillis(futureStamp - AlarmManager.INTERVAL_DAY);

                Calendar dayCal = Calendar.getInstance();
                dayCal.setTimeInMillis(task.getTimestamp());
                int alarmDay = dayCal.get(Calendar.DAY_OF_MONTH);

                //alarm data is already updated if user marked task as done
                if ((alarmDay != currentCal.get(Calendar.DAY_OF_MONTH))) {//TODO found out if this conditional is still needed
                    diff = futureStamp - AlarmManager.INTERVAL_DAY - currentCal.getTimeInMillis();

                    if (diff > 0) {
                        long daysOut = diff / 86400000;
                        futureStamp = futureStamp - (86400000 * (daysOut + 1));
                        alarmCalendar.setTimeInMillis(futureStamp
                                - AlarmManager.INTERVAL_DAY);
                    }

//                    MainActivity.taskViewModel.update(task);
                    update(task);

                } else {
                    diff = futureStamp - AlarmManager.INTERVAL_DAY - currentCal.getTimeInMillis();

                    if (diff > 0) {
                        long daysOut = diff / 86400000;
                        futureStamp = futureStamp - (86400000 * (daysOut + 1));
                        alarmCalendar.setTimeInMillis(futureStamp
                                - AlarmManager.INTERVAL_DAY);
                    }

//                    MainActivity.taskViewModel.update(task);
                    update(task);
                }

            } else if (task.getRepeatInterval().equals("week") && !snoozeStatus) {

                //App crashes if exact duplicate of timestamp is saved in database. Attempting to
                // detect duplicates and then adjusting the timestamp on the millisecond level
                long futureStamp = task.getTimestamp() + (AlarmManager.INTERVAL_DAY * 7);
                futureStamp = getFutureStamp(futureStamp);
                task.setTimestamp(futureStamp);
                MainActivity.taskViewModel.update(task);

                Intent alertIntent = new Intent(context, AlertReceiver.class);
                alertIntent.putExtra
                        ("snoozeStatus", false);
                alertIntent.putExtra("task", task);

                //Setting alarm
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        context, task.getId(), alertIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

                Calendar alarmCalendar = Calendar.getInstance();
                Long diff;

                Calendar currentCal = Calendar.getInstance();
                Calendar futureCal = Calendar.getInstance();
                futureCal.setTimeInMillis(futureStamp);
                diff = futureCal.getTimeInMillis() - currentCal.getTimeInMillis();
                //checking if timestamp has been updated or not
                if (diff < (86400000 * 7)) {
                    MainActivity.alarmManager.set(AlarmManager.RTC, futureStamp, pendingIntent);
                } else {
                    long daysOut = diff / (86400000 * 7);
                    MainActivity.alarmManager.set(AlarmManager.RTC, (futureStamp - ((86400000 * 7) * daysOut)),
                            pendingIntent);
                }

                alarmCalendar.setTimeInMillis(futureStamp - (AlarmManager.INTERVAL_DAY * 7));

                Calendar dayCal = Calendar.getInstance();
                dayCal.setTimeInMillis(task.getTimestamp());
                int alarmDay = dayCal.get(Calendar.DAY_OF_MONTH);

                //alarm data is already updated if user marked task as done
                if ((alarmDay != currentCal.get(Calendar.DAY_OF_MONTH))) {
                    diff = futureStamp - (AlarmManager.INTERVAL_DAY * 7) - currentCal.getTimeInMillis();

                    if (diff > 0) {
                        long daysOut = diff / (86400000 * 7);
                        futureStamp = futureStamp - ((86400000 * 7) * (daysOut + 1));
                        alarmCalendar.setTimeInMillis(futureStamp
                                - (AlarmManager.INTERVAL_DAY * 7));
                    }

                    MainActivity.taskViewModel.update(task);

                } else {
                    diff = futureStamp - (AlarmManager.INTERVAL_DAY * 7) - currentCal.getTimeInMillis();

                    if (diff > 0) {
                        long daysOut = diff / (86400000 * 7);
                        futureStamp = futureStamp - ((86400000 * 7) * (daysOut + 1));
                        alarmCalendar.setTimeInMillis(futureStamp
                                - (AlarmManager.INTERVAL_DAY * 7));
                    }

                    MainActivity.taskViewModel.update(task);
                }

            } else if (task.getRepeatInterval().equals("month") && !snoozeStatus) {

                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(task.getTimestamp());
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int originalDay = task.getOriginalDay();
                //Determining if it's a leap year
                int leapYear = 0;
                if (year % 4 == 0) {
                    leapYear = 1;
                }
                int multiplier;
                //months that have 31 days
                if (month == 0 || month == 2 || month == 4 || month == 6
                        || month == 7 || month == 9 || month == 11) {
                    //if due on 31st day and following month doesn't have 31 days set to last day of following month
                    if (originalDay == 31 && month != 6 && month != 11 && month != 0) {
                        multiplier = 30;
                        //if due on 31st and following month is February set to last day of February
                    } else if (originalDay == 31 && month == 0) {
                        multiplier = 28 + leapYear;
                        //if due on 30th and following month is February set to last day of February
                    } else if (originalDay == 30 && month == 0) {
                        multiplier = 29 + leapYear;
                        //if due on 29th and following month is February set to last day of February
                    } else if (originalDay == 29 && month == 0) {
                        multiplier = 30 + leapYear;
                    } else {
                        multiplier = 31;
                    }
                    //February
                } else if (month == 1) {
                    //if original due day is 31 then set to 31st of following month
                    if (originalDay == 31) {
                        multiplier = 31;
                        //if original due day is 30 then set to 30th of following month
                    } else if (originalDay == 30) {
                        multiplier = 30;
                        //if original due day is 39 then set to 39th of following month
                    } else if (originalDay == 29) {
                        multiplier = 29;
                    } else {
                        multiplier = 28 + leapYear;
                    }
                    //months that have 30 days
                } else {
                    //if original due day is 31 then set to 31st of following month
                    if (originalDay == 31) {
                        multiplier = 31;
                    } else {
                        multiplier = 30;
                    }
                }

                //App crashes if exact duplicate of timestamp is saved in database. Attempting to
                // detect duplicates and then adjusting the timestamp on the millisecond level
                long futureStamp = task.getTimestamp() + (AlarmManager.INTERVAL_DAY * multiplier);
                futureStamp = getFutureStamp(futureStamp);
                task.setTimestamp(futureStamp);
                MainActivity.taskViewModel.update(task);

                Intent alertIntent = new Intent(context, AlertReceiver.class);
                alertIntent.putExtra
                        ("snoozeStatus", false);
                alertIntent.putExtra("task", task);

                //Setting alarm
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        context, task.getId(), alertIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

                Calendar alarmCalendar = Calendar.getInstance();
                Long diff;

                Calendar currentCal = Calendar.getInstance();
                Calendar futureCal = Calendar.getInstance();
                futureCal.setTimeInMillis(futureStamp);

                MainActivity.alarmManager.set(AlarmManager.RTC, futureStamp, pendingIntent);

                alarmCalendar.setTimeInMillis(futureStamp - (AlarmManager.INTERVAL_DAY * multiplier));

                Calendar dayCal = Calendar.getInstance();
                dayCal.setTimeInMillis(task.getTimestamp());
                int alarmDay = dayCal.get(Calendar.DAY_OF_MONTH);

                //alarm data is already updated if user marked task as done
                if ((alarmDay != currentCal.get(Calendar.DAY_OF_MONTH))) {//TODO found out if these conditions do anything
                    diff = futureStamp - (AlarmManager.INTERVAL_DAY * multiplier) - currentCal.getTimeInMillis();

                    if (diff > 0) {
                        long daysOut = diff / (86400000 * multiplier);
                        futureStamp = futureStamp - ((86400000 * multiplier) * (daysOut + 1));
                        alarmCalendar.setTimeInMillis(futureStamp
                                - (AlarmManager.INTERVAL_DAY * multiplier));
                    }

                    MainActivity.taskViewModel.update(task);

                } else {
                    diff = futureStamp - (AlarmManager.INTERVAL_DAY * multiplier) - currentCal.getTimeInMillis();

                    if (diff > 0) {
                        long daysOut = diff / (86400000 * multiplier);
                        futureStamp = futureStamp - ((86400000 * multiplier) * (daysOut + 1));
                        alarmCalendar.setTimeInMillis(futureStamp
                                - (AlarmManager.INTERVAL_DAY * multiplier));
                    }

                    MainActivity.taskViewModel.update(task);
                }

            }
        }
    }

    private long getFutureStamp(long futureStamp) {

//        List<Integer> timestamps = MainActivity.taskViewModel.getAllTimestamps();
        List<Integer> timestamps = getAllTimestamps();

        //ensuring that saved timestamp is unique
        for (int i = 0; i < timestamps.size(); i++) {
            if (timestamps.get(i) == futureStamp) {
                futureStamp++;
                i = 0;
            }
        }

        return futureStamp;
    }

}
