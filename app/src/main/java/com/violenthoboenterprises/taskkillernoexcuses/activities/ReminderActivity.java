package com.violenthoboenterprises.taskkillernoexcuses.activities;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.violenthoboenterprises.taskkillernoexcuses.R;
import com.violenthoboenterprises.taskkillernoexcuses.model.Reminder;
import com.violenthoboenterprises.taskkillernoexcuses.model.ReminderPresenterImpl;
import com.violenthoboenterprises.taskkillernoexcuses.model.ReminderViewModel;
import com.violenthoboenterprises.taskkillernoexcuses.model.Task;
import com.violenthoboenterprises.taskkillernoexcuses.model.TaskViewModel;
import com.violenthoboenterprises.taskkillernoexcuses.presenter.ReminderPresenter;
import com.violenthoboenterprises.taskkillernoexcuses.utils.AlertReceiver;

import java.util.Calendar;
import java.util.Collection;
import java.util.Locale;

public class ReminderActivity extends MainActivity {

//    static String TAG;
//    private Toolbar dueToolbar;
//    LinearLayout dateButton;
//    LinearLayout timeButton;
//    static LinearLayout darkRepeat;
//    LinearLayout lightRepeat;
//    static ImageView time, timeFadedDark, timeFadedLight, calendar,
//            calendarFadedDark, calendarFadedLight;
//    ImageView dailyDark, weeklyDark, monthlyDark, cancelRepeatDark,
//            dailyLight, weeklyLight, monthlyLight, cancelRepeatLight;
//    View pickerRoot;
//    TextView dateTextView;
//    TextView timeTextView;
//    TextView divTwo;
//    static TextView divThree;
//    String repeat;
//    static boolean setDue;
//    static String dbTaskId;
//    String dbTask;
//    static String dbDueTime;
//    static MenuItem killAlarm, trashAlarmOpen;
//    static boolean datePicked, timePicked;
//    static int screenSize;
//    String dbRepeatInterval;
private final String TAG = this.getClass().getSimpleName();
    private Toolbar tbReminder;
    private ImageView imgTime, imgTimeFaded, imgCalendar, imgCalendarFaded;
    private ImageView imgDailyFaded, imgWeeklyFaded, imgMonthlyFaded, imgCancelRepeatFaded,
            imgDaily, imgWeekly, imgMonthly, imgCancelRepeat;
    private TextView tvDate, tvTime;
    private static MenuItem miKillReminder, miKillReminderOpen;
    private static int intScreenSize;
    private static ReminderPresenter reminderPresenter;
    private static Task task;
    private String REPEAT_DAY = "day";
    private String REPEAT_WEEK = "week";
    private String REPEAT_MONTH = "month";
    private static int tempMinute;
    private static int tempHour;
    private static int tempDay;
    private static int tempMonth;
    private static int tempYear;
    //flags for determining if values have been set
    private static boolean boolRepeatSet;
    private static boolean boolDateSet;
    private static boolean boolTimeSet;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_reminder);

        tbReminder = findViewById(R.id.tbReminder);
        setSupportActionBar(tbReminder);

//        TAG = "ReminderActivity";
//        repeat = "none";
//        setDue = false;
//        datePicked = false;
//        timePicked = false;
//
//        time = findViewById(R.id.time);
//        timeFadedLight = findViewById(R.id.timeFadedLight);
//        timeFadedDark = findViewById(R.id.timeFadedDark);
//        calendar = findViewById(R.id.calendar);
//        calendarFadedLight = findViewById(R.id.calendarFadedLight);
//        calendarFadedDark = findViewById(R.id.calendarFadedDark);
//        dateButton = findViewById(R.id.dateBtn);
//        timeButton = findViewById(R.id.timeBtn);
//        darkRepeat = findViewById(R.id.darkRepeatLayout);
//        lightRepeat = findViewById(R.id.lightRepeatLayout);
//        pickerRoot = findViewById(R.id.pickerRoot);
//        dateTextView = findViewById(R.id.dateTextView);
//        timeTextView = findViewById(R.id.timeTextView);
//        dailyDark = findViewById(R.id.dailyDark);
//        weeklyDark = findViewById(R.id.weeklyDark);
//        monthlyDark = findViewById(R.id.monthlyDark);
//        cancelRepeatDark = findViewById(R.id.cancelRepeatDark);
//        dailyLight = findViewById(R.id.dailyLight);
//        weeklyLight = findViewById(R.id.weeklyLight);
//        monthlyLight = findViewById(R.id.monthlyLight);
//        cancelRepeatLight = findViewById(R.id.cancelRepeatLight);
//        divTwo = findViewById(R.id.divTwo);
//        divThree = findViewById(R.id.divThree);
//
//        screenSize = getResources().getConfiguration().screenLayout &
//                Configuration.SCREENLAYOUT_SIZE_MASK;
//
//        divTwo.setBackgroundColor(Color.parseColor(highlight));
//        divThree.setBackgroundColor(Color.parseColor(highlight));

        //getting task data
//        dbDueTime = "";
//        dbTaskId = "";
//        dbTask = "";
//        boolean dbRepeat = false;
//        boolean dbOverdue = false;
//        Cursor dbTaskResult = MainActivity.db.getUniversalData();
//        while (dbTaskResult.moveToNext()) {
//            dbTaskId = dbTaskResult.getString(4);
//        }
//        dbTaskResult = db.getData(Integer.parseInt(dbTaskId));
//        while (dbTaskResult.moveToNext()) {
//            dbDueTime = dbTaskResult.getString(3);
//            dbTask = dbTaskResult.getString(4);
//            dbRepeat = dbTaskResult.getInt(8) > 0;
//            dbOverdue = dbTaskResult.getInt(9) > 0;
//            dbRepeatInterval = dbTaskResult.getString(13);
//        }
//        dbTaskResult.close();

//        onCreateOptionsMenu(dueToolbar.getMenu());

        //Inform user that they can set an alarm
//        if(dbDueTime.equals("0")){
//            dateTextView.setText(R.string.addDate);
//            timeTextView.setText(R.string.addTime);
//
//            if(lightDark){
//                calendarFadedLight.setVisibility(View.VISIBLE);
//                timeFadedLight.setVisibility(View.VISIBLE);
//            }else{
//                calendarFadedDark.setVisibility(View.VISIBLE);
//                timeFadedDark.setVisibility(View.VISIBLE);
//            }
//
//        //Showing existing due date and time
//        }else{
//            //getting alarm data
//            Cursor alarmResult = MainActivity.db.getAlarmData
//                    (Integer.parseInt(dbTaskId));
//            String alarmHour = "";
//            String alarmMinute = "";
//            String alarmAmPm = "";
//            String alarmDay = "";
//            String alarmMonth = "";
//            while(alarmResult.moveToNext()){
//                alarmHour = alarmResult.getString(1);
//                alarmMinute = alarmResult.getString(2);
//                alarmAmPm = alarmResult.getString(3);
//                alarmDay = alarmResult.getString(4);
//                alarmMonth = alarmResult.getString(5);
//            }
//            alarmResult.close();
//
//            String formattedMonth = "";
//
//            int intMonth = Integer.valueOf(alarmMonth) + 1;
//            if(intMonth == 1){
//                formattedMonth = getString(R.string.jan);
//            }else if(intMonth == 2){
//                formattedMonth = getString(R.string.feb);
//            }else if(intMonth == 3){
//                formattedMonth = getString(R.string.mar);
//            }else if(intMonth == 4){
//                formattedMonth = getString(R.string.apr);
//            }else if(intMonth == 5){
//                formattedMonth = getString(R.string.may);
//            }else if(intMonth == 6){
//                formattedMonth = getString(R.string.jun);
//            }else if(intMonth == 7){
//                formattedMonth = getString(R.string.jul);
//            }else if(intMonth == 8){
//                formattedMonth = getString(R.string.aug);
//            }else if(intMonth == 9){
//                formattedMonth = getString(R.string.sep);
//            }else if(intMonth == 10){
//                formattedMonth = getString(R.string.oct);
//            }else if(intMonth == 11){
//                formattedMonth = getString(R.string.nov);
//            }else if(intMonth == 12){
//                formattedMonth = getString(R.string.dec);
//            }
//
//            String lang = String.valueOf(Locale.getDefault());
//            if(lang.equals("en_AS") || lang.equals("en_BM")
//                    || lang.equals("en_CA") || lang.equals("en_GU")
//                    || lang.equals("en_PH")
//                    || lang.equals("en_PR") || lang.equals("en_UM")
//                    || lang.equals("en_US") || lang.equals("en_VI")) {
//                dateTextView.setText(formattedMonth + " " + alarmDay);
//            }else{
//                dateTextView.setText(alarmDay + " " + formattedMonth);
//            }
//
//            String adjustedAmPm = String.valueOf(alarmAmPm)/*getString(R.string.am)*/;
//            String adjustedHour = String.valueOf(alarmHour);
//            String adjustedMinute = String.valueOf(alarmMinute);
//
//            if(Integer.parseInt(alarmHour) == 0) {
//                adjustedHour = String.valueOf(12);
//            }else if(Integer.parseInt(alarmHour) == 12){
//            }else if(Integer.parseInt(alarmHour) > 12){
//                adjustedHour = String.valueOf(Integer.parseInt(alarmHour) - 12);
//            }
//
//            if(Integer.parseInt(alarmMinute) < 10){
//                adjustedMinute = "0" + alarmMinute;
//            }
//
//            if(adjustedAmPm.equals("0")){
//                adjustedAmPm = "am";
//            }else{
//                adjustedAmPm = "pm";
//            }
//
//            timeTextView.setText(adjustedHour + ":" + adjustedMinute + adjustedAmPm);
//
//            calendar.setVisibility(View.VISIBLE);
//            time.setVisibility(View.VISIBLE);
//            if(lightDark){
//                calendarFadedLight.setVisibility(View.GONE);
//                timeFadedLight.setVisibility(View.GONE);
//            }else{
//                calendarFadedDark.setVisibility(View.GONE);
//                timeFadedDark.setVisibility(View.GONE);
//            }
//            datePicked = true;
//            timePicked = true;
//            if(screenSize == 3){
//                dateTextView.setTextSize(65);
//                timeTextView.setTextSize(65);
//            }else if(screenSize == 4){
//                dateTextView.setTextSize(85);
//                timeTextView.setTextSize(85);
//            }else {
//                dateTextView.setTextSize(25);
//                timeTextView.setTextSize(25);
//            }
//
//        }

//        if(!lightDark){
//            dueToolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
//            dueToolbar.setSubtitleTextColor(Color.parseColor("#AAAAAA"));
//            pickerRoot.setBackgroundColor(Color.parseColor("#333333"));
//            dueToolbar.setBackgroundColor(Color.parseColor("#333333"));
//            dateTextView.setTextColor(Color.parseColor("#AAAAAA"));
//            timeTextView.setTextColor(Color.parseColor("#AAAAAA"));
//            cancelRepeatDark.setBackgroundColor(Color.parseColor("#AAAAAA"));
//            dailyDark.setBackgroundColor(Color.parseColor("#AAAAAA"));
//            weeklyDark.setBackgroundColor(Color.parseColor("#AAAAAA"));
//            monthlyDark.setBackgroundColor(Color.parseColor("#AAAAAA"));
//            darkRepeat.setVisibility(View.VISIBLE);
//        }else{
//            dueToolbar.setTitleTextColor(Color.parseColor("#000000"));
//            dueToolbar.setSubtitleTextColor(Color.parseColor("#666666"));
//            pickerRoot.setBackgroundColor(Color.parseColor("#FFFFFF"));
//            dueToolbar.setBackgroundColor(Color.parseColor("#FFFFFF"));
//            dateTextView.setTextColor(Color.parseColor("#000000"));
//            timeTextView.setTextColor(Color.parseColor("#000000"));
//            cancelRepeatLight.setBackgroundColor(Color.parseColor("#000000"));
//            dailyLight.setBackgroundColor(Color.parseColor("#000000"));
//            weeklyLight.setBackgroundColor(Color.parseColor("#000000"));
//            monthlyLight.setBackgroundColor(Color.parseColor("#000000"));
//            lightRepeat.setVisibility(View.VISIBLE);
//        }

        //Highlight the repeat type or highlight No Repeat if none exists
//        if(!dbRepeat){
//            cancelRepeatDark.setBackgroundColor(Color.parseColor(highlight));
//            cancelRepeatLight.setBackgroundColor(Color.parseColor(highlight));
//        }else if(dbRepeatInterval.equals("day")){
//            dailyDark.setBackgroundColor(Color.parseColor(highlight));
//            dailyLight.setBackgroundColor(Color.parseColor(highlight));
//        }else if(dbRepeatInterval.equals("week")){
//            weeklyDark.setBackgroundColor(Color.parseColor(highlight));
//            weeklyLight.setBackgroundColor(Color.parseColor(highlight));
//        }else if(dbRepeatInterval.equals("month")){
//            monthlyDark.setBackgroundColor(Color.parseColor(highlight));
//            monthlyLight.setBackgroundColor(Color.parseColor(highlight));
//        }

        //getting the task to which this note is related
        task = (Task) getIntent().getSerializableExtra("task");
        ReminderViewModel reminderViewModel =
                ViewModelProviders.of(this).get(ReminderViewModel.class);
        //getting the instance of the reminder
        Reminder reminder = reminderViewModel.getReminderByParent(task.getId());
        TaskViewModel taskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
        reminderPresenter = new ReminderPresenterImpl(taskViewModel, reminderViewModel,
                task, reminder, getApplicationContext());

        imgTime = findViewById(R.id.imgTime);
        imgTimeFaded = findViewById(R.id.imgTimeFaded);
        imgCalendar = findViewById(R.id.imgCalendar);
        imgCalendarFaded = findViewById(R.id.imgCalendarFaded);
        View btnDate = findViewById(R.id.btnDate);
        View btnTime = findViewById(R.id.btnTime);
        tvDate = findViewById(R.id.tvDate);
        tvTime = findViewById(R.id.tvTime);
        imgDailyFaded = findViewById(R.id.imgDailyRepeatFaded);
//        imgDaily = findViewById(R.id.imgDailyRepeat);
        imgWeeklyFaded = findViewById(R.id.imgWeeklyRepeatFaded);
//        imgWeekly = findViewById(R.id.imgWeeklyRepeat);
        imgMonthlyFaded = findViewById(R.id.imgMonthlyRepeatFaded);
//        imgMonthly = findViewById(R.id.imgMonthlyRepeat);
        imgCancelRepeatFaded = findViewById(R.id.imgCancelRepeatFaded);
//        imgCancelRepeat = findViewById(R.id.imgCancelRepeat);
        preferences = this.getSharedPreferences("com.violenthoboenterprises.blistful",
                Context.MODE_PRIVATE);
        boolRepeatSet = false;
        boolDateSet = false;
        boolTimeSet = false;

        intScreenSize = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;

        onCreateOptionsMenu(tbReminder.getMenu());
        tempMinute = -1;
        tempHour = -1;
        tempDay = -1;
        tempMonth = -1;
        tempYear = -1;

        //Inform user that they can set an alarm
        if (reminderPresenter.getYear() == 0) {
            tvDate.setText(R.string.addDate);
        }
        if (reminderPresenter.getHour() == 0) {
            tvTime.setText(R.string.addTime);
        }

        if (reminderPresenter.getYear() != 0) {
            imgCalendarFaded.setVisibility(View.INVISIBLE);
            imgCalendar.setVisibility(View.VISIBLE);

            tvDate.setText(reminderPresenter.getFormattedDate());

            imgTimeFaded.setVisibility(View.INVISIBLE);
            imgTime.setVisibility(View.VISIBLE);

            tvTime.setText(reminderPresenter.getFormattedTime());

            if (intScreenSize == 3) {
                tvDate.setTextSize(65);
                tvTime.setTextSize(65);
            } else if (intScreenSize == 4) {
                tvDate.setTextSize(85);
                tvTime.setTextSize(85);
            } else {
                tvDate.setTextSize(25);
                tvTime.setTextSize(25);
            }

        }else{
            if (intScreenSize == 3) {
                tvDate.setTextSize(25);
                tvTime.setTextSize(25);
            } else if (intScreenSize == 4) {
                tvDate.setTextSize(35);
                tvTime.setTextSize(35);
            } else {
                tvDate.setTextSize(15);
                tvTime.setTextSize(15);
            }
        }

        //Highlight the repeat type or highlight "No Repeat" if none exists
        if (reminderPresenter.getRepeatInterval() == null) {
//            imgCancelRepeatFaded.setVisibility(View.INVISIBLE);
//            imgCancelRepeat.setVisibility(View.VISIBLE);
            imgCancelRepeatFaded.setBackgroundColor(getResources().getColor(R.color.lightGreen));
        } else if (reminderPresenter.getRepeatInterval().equals(REPEAT_DAY)) {
//            imgDailyFaded.setVisibility(View.INVISIBLE);
//            imgDaily.setVisibility(View.VISIBLE);
            imgDailyFaded.setBackgroundColor(getResources().getColor(R.color.lightGreen));
        } else if (reminderPresenter.getRepeatInterval().equals(REPEAT_WEEK)) {
//            imgWeeklyFaded.setVisibility(View.INVISIBLE);
//            imgWeekly.setVisibility(View.VISIBLE);
            imgWeeklyFaded.setBackgroundColor(getResources().getColor(R.color.lightGreen));
        } else if (reminderPresenter.getRepeatInterval().equals(REPEAT_MONTH)) {
//            imgMonthlyFaded.setVisibility(View.INVISIBLE);
//            imgMonthly.setVisibility(View.VISIBLE);
            imgMonthlyFaded.setBackgroundColor(getResources().getColor(R.color.lightGreen));
        }

        //Actions to occur when user selects to set/change date
        btnDate.setOnClickListener(v -> {

            vibrate.vibrate(50);

            DialogFragment dialogfragment = new DatePickerDialogFrag();

            dialogfragment.show(getFragmentManager(), "Date");

        });

        //Actions to occur when user selects to set/change time
        btnTime.setOnClickListener(v -> {

            vibrate.vibrate(50);

            DialogFragment dialogfragment = new TimePickerDialogFrag();

            dialogfragment.show(getFragmentManager(), "Time");

        });

//        //Actions to occur if user selects to set a daily recurring alarm
//        dailyDark.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                if(!mute && !repeat.equals("day")){
//                    blip.start();
//                }
//
//
//                vibrate.vibrate(50);
//
//                //Show user which button they selected by highlighting it
//                cancelRepeatDark.setBackgroundColor(Color.parseColor("#AAAAAA"));
//                dailyDark.setBackgroundColor(Color.parseColor(highlight));
//                weeklyDark.setBackgroundColor(Color.parseColor("#AAAAAA"));
//                monthlyDark.setBackgroundColor(Color.parseColor("#AAAAAA"));
//
//                repeatInterval = AlarmManager.INTERVAL_DAY;//TODO might not need this variable any more
//                db.updateRepeatIntervalTemp(String.valueOf(AlarmManager.INTERVAL_DAY));
//
//                repeat = "day";
//
//                repeating = true;
//
//                setDue = true;
//
//                killAlarm.setVisible(true);
//
//            }
//
//        });
//
//        //Actions to occur if user selects to set a daily recurring alarm
//        dailyLight.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                if(!mute && !repeat.equals("day")){
//                    blip.start();
//                }
//
//                vibrate.vibrate(50);
//
//                //Show user which button they selected by highlighting it
//                cancelRepeatLight.setBackgroundColor(Color.parseColor("#000000"));
//                dailyLight.setBackgroundColor(Color.parseColor(highlight));
//                weeklyLight.setBackgroundColor(Color.parseColor("#000000"));
//                monthlyLight.setBackgroundColor(Color.parseColor("#000000"));
//
//                repeatInterval = AlarmManager.INTERVAL_DAY;
//                db.updateRepeatIntervalTemp(String.valueOf(AlarmManager.INTERVAL_DAY));
//
//                repeat = "day";
//
//                repeating =true;
//
//                setDue = true;
//
//                killAlarm.setVisible(true);
//
//            }
//
//        });
//
//        //Actions to occur if user selects to set a weekly recurring alarm
//        weeklyDark.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                if(!mute && !repeat.equals("week")){
//                    blip.start();
//                }
//
//                vibrate.vibrate(50);
//
//                //Show user which button they selected by highlighting it
//                cancelRepeatDark.setBackgroundColor(Color.parseColor("#AAAAAA"));
//                dailyDark.setBackgroundColor(Color.parseColor("#AAAAAA"));
//                weeklyDark.setBackgroundColor(Color.parseColor(highlight));
//                monthlyDark.setBackgroundColor(Color.parseColor("#AAAAAA"));
//
//                repeatInterval = AlarmManager.INTERVAL_DAY * 7;
//
//                repeat = "week";
//
//                repeating =true;
//
//                setDue = true;
//
//                killAlarm.setVisible(true);
//
//            }
//
//        });
//
//        //Actions to occur if user selects to set a weekly recurring alarm
//        weeklyLight.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                if(!mute && !repeat.equals("week")){
//                    blip.start();
//                }
//
//                vibrate.vibrate(50);
//
//                //Show user which button they selected by highlighting it
//                cancelRepeatLight.setBackgroundColor(Color.parseColor("#000000"));
//                dailyLight.setBackgroundColor(Color.parseColor("#000000"));
//                weeklyLight.setBackgroundColor(Color.parseColor(highlight));
//                monthlyLight.setBackgroundColor(Color.parseColor("#000000"));
//
//                repeatInterval = AlarmManager.INTERVAL_DAY * 7;
//
//                repeat = "week";
//
//                repeating =true;
//
//                setDue = true;
//
//                killAlarm.setVisible(true);
//
//            }
//
//        });
//
//        //Actions to occur if user selects to set a monthly  recurring alarm
//        monthlyDark.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                if(!mute && !repeat.equals("month")){
//                    blip.start();
//                }
//
//                vibrate.vibrate(50);
//
//                //Show user which button they selected by highlighting it
//                cancelRepeatDark.setBackgroundColor(Color.parseColor("#AAAAAA"));
//                dailyDark.setBackgroundColor(Color.parseColor("#AAAAAA"));
//                weeklyDark.setBackgroundColor(Color.parseColor("#AAAAAA"));
//                monthlyDark.setBackgroundColor(Color.parseColor(highlight));
//
//                repeat = "month";
//
//                repeating =true;
//
//                setDue = true;
//
//                killAlarm.setVisible(true);
//
//            }
//
//        });
//
//        //Actions to occur if user selects to set a monthly  recurring alarm
//        monthlyLight.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                if(!mute && !repeat.equals("month")){
//                    blip.start();
//                }
//
//                vibrate.vibrate(50);
//
//                //Show user which button they selected by highlighting it
//                cancelRepeatLight.setBackgroundColor(Color.parseColor("#000000"));
//                dailyLight.setBackgroundColor(Color.parseColor("#000000"));
//                weeklyLight.setBackgroundColor(Color.parseColor("#000000"));
//                monthlyLight.setBackgroundColor(Color.parseColor(highlight));
//
//                repeat = "month";
//
//                repeating =true;
//
//                setDue = true;
//
//                killAlarm.setVisible(true);
//
//            }
//
//        });
//
//        //Actions to occur when user chooses to cancel the repeat
//        final boolean finalDbOverdue = dbOverdue;
//        cancelRepeatDark.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                if(!mute && !repeat.equals("none")){
//                    blip.start();
//                }
//
//                vibrate.vibrate(50);
//
//                cancelRepeatDark.setBackgroundColor(Color.parseColor(highlight));
//                dailyDark.setBackgroundColor(Color.parseColor("#AAAAAA"));
//                weeklyDark.setBackgroundColor(Color.parseColor("#AAAAAA"));
//                monthlyDark.setBackgroundColor(Color.parseColor("#AAAAAA"));
//
//                repeat = "none";
//
//                setDue = false;
//
//                if(!timePicked && !datePicked) {
//
//                    killAlarm.setVisible(false);
//
//                }
//
//                if(finalDbOverdue && dbRepeatInterval.equals("day")){
//                    db.updateTimestamp(dbTaskId, String.valueOf(Integer.parseInt(dbDueTime) - 86400));
//                }else if(finalDbOverdue && dbRepeatInterval.equals("week")){
//                    db.updateTimestamp(dbTaskId, String.valueOf(Long.parseLong(dbDueTime) - (86400 * 7)));
//                }else if(finalDbOverdue && dbRepeatInterval.equals("month")){
//
//                    int interval = 0;
//
//                    Cursor alarmResult = MainActivity.db.getAlarmData
//                            (Integer.parseInt(dbTaskId));
//                    String alarmDay = "";
//                    String alarmMonth = "";
//                    String alarmYear = "";
//                    while(alarmResult.moveToNext()){
//                        alarmDay = alarmResult.getString(4);
//                        alarmMonth = alarmResult.getString(5);
//                        alarmYear = alarmResult.getString(6);
//                    }
//
//                    alarmResult.close();
//
//                    int theYear = Integer.parseInt(alarmYear);
//                    int theMonth = Integer.parseInt(alarmMonth);
//                    int theDay = Integer.parseInt(alarmDay);
//
//                    //Month January and day is 29 non leap year 2592000
//                    if((theMonth == 0) && (theDay == 29) && (theYear % 4 != 0)){
//                        interval = 2592000;
//                        //Month January and day is 30 non leap year 2505600
//                    }else if((theMonth == 0) && (theDay == 30) && (theYear % 4 != 0)){
//                        interval = 2505600;
//                        //Month January and day is 31 non leap year 2419200
//                    }else if((theMonth == 0) && (theDay == 31) && (theYear % 4 != 0)){
//                        interval = 2419200;
//                        //Month January and day is 30 leap year 2592000
//                    }else if((theMonth == 0) && (theDay == 30)  && (theYear % 4 == 0)){
//                        interval = 2592000;
//                        //Month January and day is 31 leap year 2505600
//                    }else if((theMonth == 0) && (theDay == 31) && (theYear % 4 == 0)){
//                        interval = 2505600;
//                        //Month March||May||August||October and day is 31 2592000
//                    }else if(((theMonth == 2) || (theMonth == 4) || (theMonth == 7)
//                            || (theMonth == 9)) && (theDay == 31)){
//                        interval = 2592000;
//                        //Month January||March||May||July||August||October||December 2678400
//                    }else if((theMonth == 0) || (theMonth == 2) || (theMonth == 4)
//                            || (theMonth == 6) || (theMonth == 7) || (theMonth == 9)
//                            || (theMonth == 11)){
//                        interval = 2678400;
//                        //Month April||June||September||November 2592000
//                    }else if((theMonth == 3) || (theMonth == 5) || (theMonth == 8)
//                            || (theMonth == 10)){
//                        interval = 2592000;
//                        //Month February non leap year 2419200
//                    }else if((theMonth == 1) && (theYear % 4 != 0)){
//                        interval = 2419200;
//                        //Month February leap year 2505600
//                    }else if((theMonth == 1) && (theYear % 4 == 0)){
//                        interval = 2505600;
//                    }
//
//                    db.updateTimestamp(dbTaskId, String.valueOf(Integer.parseInt(dbDueTime) - interval));
//                }
//
//                db.updateRepeatInterval(dbTaskId, "");
//                db.updateRepeat(dbTaskId, false);
//
//            }
//
//        });
//
//        //Actions to occur when user chooses to cancel the repeat
//        cancelRepeatLight.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                if(!mute && !repeat.equals("none")){
//                    blip.start();
//                }
//
//                vibrate.vibrate(50);
//
//                cancelRepeatLight.setBackgroundColor(Color.parseColor(highlight));
//                dailyLight.setBackgroundColor(Color.parseColor("#000000"));
//                weeklyLight.setBackgroundColor(Color.parseColor("#000000"));
//                monthlyLight.setBackgroundColor(Color.parseColor("#000000"));
//
//                repeat = "none";
//
//                setDue = false;
//
//                if(!timePicked && !datePicked) {
//
//                    killAlarm.setVisible(false);
//
//                }
//
//                if(finalDbOverdue && dbRepeatInterval.equals("day")){
//                    db.updateTimestamp(dbTaskId, String.valueOf(Integer.parseInt(dbDueTime) - 86400));
//                }else if(finalDbOverdue && dbRepeatInterval.equals("week")){
//                    db.updateTimestamp(dbTaskId, String.valueOf(Long.parseLong(dbDueTime) - (86400 * 7)));
//                }else if(finalDbOverdue && dbRepeatInterval.equals("month")){
//
//                    int interval = 0;
//
//                    Cursor alarmResult = MainActivity.db.getAlarmData
//                            (Integer.parseInt(dbTaskId));
//                    String alarmDay = "";
//                    String alarmMonth = "";
//                    String alarmYear = "";
//                    while(alarmResult.moveToNext()){
//                        alarmDay = alarmResult.getString(4);
//                        alarmMonth = alarmResult.getString(5);
//                        alarmYear = alarmResult.getString(6);
//                    }
//
//                    alarmResult.close();
//
//                    int theYear = Integer.parseInt(alarmYear);
//                    int theMonth = Integer.parseInt(alarmMonth);
//                    int theDay = Integer.parseInt(alarmDay);
//
//                    //Month January and day is 29 non leap year 2592000
//                    if((theMonth == 0) && (theDay == 29) && (theYear % 4 != 0)){
//                        interval = 2592000;
//                        //Month January and day is 30 non leap year 2505600
//                    }else if((theMonth == 0) && (theDay == 30) && (theYear % 4 != 0)){
//                        interval = 2505600;
//                        //Month January and day is 31 non leap year 2419200
//                    }else if((theMonth == 0) && (theDay == 31) && (theYear % 4 != 0)){
//                        interval = 2419200;
//                        //Month January and day is 30 leap year 2592000
//                    }else if((theMonth == 0) && (theDay == 30)  && (theYear % 4 == 0)){
//                        interval = 2592000;
//                        //Month January and day is 31 leap year 2505600
//                    }else if((theMonth == 0) && (theDay == 31) && (theYear % 4 == 0)){
//                        interval = 2505600;
//                        //Month March||May||August||October and day is 31 2592000
//                    }else if(((theMonth == 2) || (theMonth == 4) || (theMonth == 7)
//                            || (theMonth == 9)) && (theDay == 31)){
//                        interval = 2592000;
//                        //Month January||March||May||July||August||October||December 2678400
//                    }else if((theMonth == 0) || (theMonth == 2) || (theMonth == 4)
//                            || (theMonth == 6) || (theMonth == 7) || (theMonth == 9)
//                            || (theMonth == 11)){
//                        interval = 2678400;
//                        //Month April||June||September||November 2592000
//                    }else if((theMonth == 3) || (theMonth == 5) || (theMonth == 8)
//                            || (theMonth == 10)){
//                        interval = 2592000;
//                        //Month February non leap year 2419200
//                    }else if((theMonth == 1) && (theYear % 4 != 0)){
//                        interval = 2419200;
//                        //Month February leap year 2505600
//                    }else if((theMonth == 1) && (theYear % 4 == 0)){
//                        interval = 2505600;
//                    }
//
//                    db.updateTimestamp(dbTaskId, String.valueOf(Integer.parseInt(dbDueTime) - interval));
//                }
//
//                db.updateRepeatInterval(dbTaskId, "");
//                db.updateRepeat(dbTaskId, false);
//
//            }
//
//        });

        //Actions to occur when user chooses to cancel the repeat
        imgCancelRepeatFaded.setOnClickListener(v -> {

            if (!boolMute && reminderPresenter.getRepeatInterval() == null) {
                mpBlip.start();
            }

            vibrate.vibrate(50);

//            imgCancelRepeatFaded.setVisibility(View.INVISIBLE);
//            imgCancelRepeat.setVisibility(View.VISIBLE);
//            imgDailyFaded.setVisibility(View.VISIBLE);
//            imgDaily.setVisibility(View.INVISIBLE);
//            imgWeeklyFaded.setVisibility(View.VISIBLE);
//            imgWeekly.setVisibility(View.INVISIBLE);
//            imgMonthlyFaded.setVisibility(View.VISIBLE);
//            imgMonthly.setVisibility(View.INVISIBLE);

            imgCancelRepeatFaded.setBackgroundColor(getResources().getColor(R.color.lightGreen));
            imgDailyFaded.setBackgroundColor(getResources().getColor(R.color.gray));
            imgWeeklyFaded.setBackgroundColor(getResources().getColor(R.color.gray));
            imgMonthlyFaded.setBackgroundColor(getResources().getColor(R.color.gray));

            if (reminderPresenter.getYear() == 0 && reminderPresenter.getHour() == 0) {

                miKillReminder.setVisible(false);

            }

            reminderPresenter.setRepeatInterval(null);
            boolRepeatSet = false;

        });

        //Actions to occur if user selects to set a daily recurring alarm
        imgDailyFaded.setOnClickListener(v -> {

            if (!boolMute && reminderPresenter.getRepeatInterval() != null) {
                if (!reminderPresenter.getRepeatInterval().equals(REPEAT_DAY)) {
                    mpBlip.start();
                }
            }

            vibrate.vibrate(50);

            //Show user which button they selected by highlighting it
//            imgCancelRepeatFaded.setVisibility(View.VISIBLE);
//            imgCancelRepeat.setVisibility(View.INVISIBLE);
//            imgDailyFaded.setVisibility(View.INVISIBLE);
//            imgDaily.setVisibility(View.VISIBLE);
//            imgWeeklyFaded.setVisibility(View.VISIBLE);
//            imgWeekly.setVisibility(View.INVISIBLE);
//            imgMonthlyFaded.setVisibility(View.VISIBLE);
//            imgMonthly.setVisibility(View.INVISIBLE);

            imgCancelRepeatFaded.setBackgroundColor(getResources().getColor(R.color.gray));
            imgDailyFaded.setBackgroundColor(getResources().getColor(R.color.lightGreen));
            imgWeeklyFaded.setBackgroundColor(getResources().getColor(R.color.gray));
            imgMonthlyFaded.setBackgroundColor(getResources().getColor(R.color.gray));

            miKillReminder.setVisible(true);

            reminderPresenter.setRepeatInterval(REPEAT_DAY);
            boolRepeatSet = true;

        });

        //Actions to occur if user selects to set a weekly recurring alarm
        imgWeeklyFaded.setOnClickListener(v -> {

            if (!boolMute && reminderPresenter.getRepeatInterval() != null) {
                if (!reminderPresenter.getRepeatInterval().equals(REPEAT_WEEK)) {
                    mpBlip.start();
                }
            }

            vibrate.vibrate(50);

            //Show user which button they selected by highlighting it
//            imgCancelRepeatFaded.setVisibility(View.VISIBLE);
//            imgCancelRepeat.setVisibility(View.INVISIBLE);
//            imgDailyFaded.setVisibility(View.VISIBLE);
//            imgDaily.setVisibility(View.INVISIBLE);
//            imgWeeklyFaded.setVisibility(View.INVISIBLE);
//            imgWeekly.setVisibility(View.VISIBLE);
//            imgMonthlyFaded.setVisibility(View.VISIBLE);
//            imgMonthly.setVisibility(View.INVISIBLE);

            imgCancelRepeatFaded.setBackgroundColor(getResources().getColor(R.color.gray));
            imgDailyFaded.setBackgroundColor(getResources().getColor(R.color.gray));
            imgWeeklyFaded.setBackgroundColor(getResources().getColor(R.color.lightGreen));
            imgMonthlyFaded.setBackgroundColor(getResources().getColor(R.color.gray));

            miKillReminder.setVisible(true);

            reminderPresenter.setRepeatInterval(REPEAT_WEEK);
            boolRepeatSet = true;

        });

        //Actions to occur if user selects to set a monthly  recurring alarm
        imgMonthlyFaded.setOnClickListener(v -> {

            if (!boolMute && reminderPresenter.getRepeatInterval() != null) {
                if (!reminderPresenter.getRepeatInterval().equals(REPEAT_MONTH)) {
                    mpBlip.start();
                }
            }

            vibrate.vibrate(50);

            //Show user which button they selected by highlighting it
//            imgCancelRepeatFaded.setVisibility(View.VISIBLE);
//            imgCancelRepeat.setVisibility(View.INVISIBLE);
//            imgDailyFaded.setVisibility(View.VISIBLE);
//            imgDaily.setVisibility(View.INVISIBLE);
//            imgWeeklyFaded.setVisibility(View.VISIBLE);
//            imgWeekly.setVisibility(View.INVISIBLE);
//            imgMonthlyFaded.setVisibility(View.INVISIBLE);
//            imgMonthly.setVisibility(View.VISIBLE);

            imgCancelRepeatFaded.setBackgroundColor(getResources().getColor(R.color.gray));
            imgDailyFaded.setBackgroundColor(getResources().getColor(R.color.gray));
            imgWeeklyFaded.setBackgroundColor(getResources().getColor(R.color.gray));
            imgMonthlyFaded.setBackgroundColor(getResources().getColor(R.color.lightGreen));

            miKillReminder.setVisible(true);

            reminderPresenter.setRepeatInterval(REPEAT_MONTH);
            boolRepeatSet = true;

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        if(!menu.hasVisibleItems()) {
//            getMenuInflater().inflate(R.menu.menu_reminder, dueToolbar.getMenu());
//            killAlarm = this.dueToolbar.getMenu().findItem(R.id.killAlarmItem);
//            trashAlarmOpen = this.dueToolbar.getMenu().findItem(R.id.trashAlarmOpen);
//            this.dueToolbar.setTitle(R.string.setDateTime);
//            this.dueToolbar.setSubtitle(dbTask);
//            if(Integer.parseInt(dbDueTime) == 0){
//                killAlarm.setVisible(false);
//            }else {
//                killAlarm.setVisible(true);
//            }
//            return true;
//        }else {
//            killAlarm.setEnabled(true);
//            return false;
//        }
        if (!menu.hasVisibleItems()) {
            getMenuInflater().inflate(R.menu.menu_reminder, tbReminder.getMenu());
            miKillReminder = this.tbReminder.getMenu().findItem(R.id.itemTrashReminder);
            miKillReminderOpen = this.tbReminder.getMenu().findItem(R.id.itemTrashReminderOpen);
            this.tbReminder.setTitle(R.string.setDateTime);
            this.tbReminder.setSubtitle(reminderPresenter.getTask());
            //if reminder already exists then delete icon should be present
            if (reminderPresenter.getYear() == 0) {
                miKillReminder.setVisible(false);
            } else {
                miKillReminder.setVisible(true);
            }
            return true;
        } else {
            miKillReminder.setEnabled(true);
            return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

//        int id = item.getItemId();
//
//        //Resetting alarm to off
//        //TODO find out if return statements are necessary
//        if ((id == R.id.killAlarmItem) && (timePicked || datePicked || !repeat.equals("none"))) {
//
//            final Handler handler = new Handler();
//
//            final Runnable runnable = new Runnable() {
//                public void run() {
//
//                    killAlarm.setVisible(false);
//                    trashAlarmOpen.setVisible(true);
//
//                    final Handler handler2 = new Handler();
//                    final Runnable runnable2 = new Runnable(){
//                        public void run(){
//                            killAlarm.setVisible(true);
//                            trashAlarmOpen.setVisible(false);
//                            final Handler handler3 = new Handler();
//                            final Runnable runnable3 = new Runnable() {
//                                @Override
//                                public void run() {
//
//                                    killAlarm.setVisible(false);
//
//                                    //getting task data
//                                    dbTaskId = "";
//                                    Cursor dbTaskResult = MainActivity.db.getUniversalData();
//                                    while (dbTaskResult.moveToNext()) {
//                                        dbTaskId = dbTaskResult.getString(4);
//                                    }
//                                    dbTaskResult.close();
//
//                                    vibrate.vibrate(50);
//
//                                    if(!mute) {
//                                        trash.start();
//                                    }
//
//                                    repeat = "none";
//
//                                    repeating = false;
//
//                                    db.updateDue(dbTaskId, false);
//
//                                    db.updateTimestamp(dbTaskId, "0");
//
//                                    db.updateRepeatInterval(dbTaskId, "");
//
//                                    db.updateRepeat(dbTaskId, false);
//
//                                    db.updateOverdue(dbTaskId, false);
//
//                                    db.updateSnooze(dbTaskId, false);
//
//                                    db.updateKilledEarly(dbTaskId, false);
//
//                                    db.updateManualKill(dbTaskId, false);
//
//                                    pendIntent = PendingIntent.getBroadcast(getApplicationContext(),
//                                            Integer.valueOf(dbTaskId),
//                                            alertIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//                                    alarmManager.cancel(MainActivity.pendIntent);
//
//                                    db.updateAlarmData(dbTaskId, "", "", "",
//                                            "", "", "");
//
//                                    db.updateSnoozeData(dbTaskId, "", "", "",
//                                            "", "", "");
//
//                                    setDue = false;
//                                    datePicked = false;
//                                    timePicked = false;
//
//                                    time.setVisibility(View.GONE);
//                                    calendar.setVisibility(View.GONE);
//                                    if(lightDark){
//                                        calendarFadedLight.setVisibility(View.VISIBLE);
//                                        timeFadedLight.setVisibility(View.VISIBLE);
//                                    }else{
//                                        calendarFadedDark.setVisibility(View.VISIBLE);
//                                        timeFadedDark.setVisibility(View.VISIBLE);
//                                    }
//
//                                    dateTextView.setText(R.string.addDate);
//                                    timeTextView.setText(R.string.addTime);
//                                    if(screenSize == 3){
//                                        dateTextView.setTextSize(25);
//                                        timeTextView.setTextSize(25);
//                                    }else if(screenSize == 4){
//                                        dateTextView.setTextSize(35);
//                                        timeTextView.setTextSize(35);
//                                    }else {
//                                        dateTextView.setTextSize(15);
//                                        timeTextView.setTextSize(15);
//                                    }
//
//                                    if(!lightDark) {
//                                        cancelRepeatDark.setBackgroundColor
//                                                (Color.parseColor(highlight));
//                                        dailyDark.setBackgroundColor
//                                                (Color.parseColor("#AAAAAA"));
//                                        weeklyDark.setBackgroundColor
//                                                (Color.parseColor("#AAAAAA"));
//                                        monthlyDark.setBackgroundColor
//                                                (Color.parseColor("#AAAAAA"));
//                                    }else{
//                                        cancelRepeatLight.setBackgroundColor
//                                                (Color.parseColor(highlight));
//                                        dailyLight.setBackgroundColor
//                                                (Color.parseColor("#000000"));
//                                        weeklyLight.setBackgroundColor
//                                                (Color.parseColor("#000000"));
//                                        monthlyLight.setBackgroundColor
//                                                (Color.parseColor("#000000"));
//                                    }
//
//                                }
//                            };
//                            handler3.postDelayed(runnable3, 100);
//                        }
//                    };
//                    handler2.postDelayed(runnable2, 100);
//                }
//            };
//
//            handler.postDelayed(runnable, 100);
//
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
        int id = item.getItemId();

        //Resetting alarm to off
        if (id == R.id.itemTrashReminder) {

            final Handler handler = new Handler();

            final Runnable runnable = () -> {

                miKillReminder.setVisible(false);
                miKillReminderOpen.setVisible(true);

                final Handler handler2 = new Handler();
                final Runnable runnable2 = () -> {
                    miKillReminder.setVisible(true);
                    miKillReminderOpen.setVisible(false);
                    final Handler handler3 = new Handler();
                    final Runnable runnable3 = () -> {

                        miKillReminder.setVisible(false);

                        vibrate.vibrate(50);

                        if (!boolMute) {
                            mpTrash.start();
                        }

                        deleteData();

                    };
                    handler3.postDelayed(runnable3, 100);
                };
                handler2.postDelayed(runnable2, 100);
            };

            handler.postDelayed(runnable, 100);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteData() {
        reminderPresenter.setRepeatInterval(null);
        reminderPresenter.setTimestamp(0);
        reminderPresenter.setDisplayedTimestamp(0);

        try {
            PendingIntent.getBroadcast(getApplicationContext(), task.getId(),
                    MainActivity.alertIntent, PendingIntent.FLAG_UPDATE_CURRENT).cancel();
        }catch(NullPointerException e){e.printStackTrace();}

        tempYear = -1;
        tempMonth = -1;
        tempDay = -1;
        tempHour = -1;
        tempMinute = -1;

        boolRepeatSet = false;
        boolDateSet = false;
        boolTimeSet = false;

        reminderPresenter.setYear(0);
        reminderPresenter.setMonth(0);
        reminderPresenter.setDay(0);
        reminderPresenter.setHour(0);
        reminderPresenter.setMinute(0);

        imgCalendarFaded.setVisibility(View.VISIBLE);
        imgCalendar.setVisibility(View.INVISIBLE);
        imgTimeFaded.setVisibility(View.VISIBLE);
        imgTime.setVisibility(View.INVISIBLE);

        tvDate.setText(R.string.addDate);
        tvTime.setText(R.string.addTime);
        if (intScreenSize == 3) {
            tvDate.setTextSize(25);
            tvTime.setTextSize(25);
        } else if (intScreenSize == 4) {
            tvDate.setTextSize(35);
            tvTime.setTextSize(35);
        } else {
            tvDate.setTextSize(15);
            tvTime.setTextSize(15);
        }

//        imgCancelRepeat.setVisibility(View.INVISIBLE);
//        imgCancelRepeat.setVisibility(View.VISIBLE);
//        imgDailyFaded.setVisibility(View.VISIBLE);
//        imgDaily.setVisibility(View.INVISIBLE);
//        imgWeeklyFaded.setVisibility(View.VISIBLE);
//        imgWeekly.setVisibility(View.INVISIBLE);
//        imgMonthlyFaded.setVisibility(View.VISIBLE);
//        imgMonthly.setVisibility(View.INVISIBLE);

        tvDate.setText(R.string.addDate);
        tvTime.setText(R.string.addTime);
    }

    public static class DatePickerDialogFrag extends DialogFragment
            implements DatePickerDialog.OnDateSetListener{

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){

//            //Set default values of date picker to current date
//            final Calendar calendar = Calendar.getInstance();
//            int year;
//            int month;
//            int day;
//
//            Cursor alarmResult = MainActivity.db.getAlarmData
//                    (Integer.parseInt(dbTaskId));
//            String alarmDay = "";
//            String alarmMonth = "";
//            String alarmYear = "";
//            while(alarmResult.moveToNext()){
//                alarmDay = alarmResult.getString(4);
//                alarmMonth = alarmResult.getString(5);
//                alarmYear = alarmResult.getString(6);
//            }
//
//            alarmResult.close();
//
//            //getting universal data
//            Cursor uniResult = MainActivity.db.getUniversalData();
//            int uniYear = 0;
//            int uniMonth = 0;
//            int uniDay = 0;
//            while(uniResult.moveToNext()){
//                uniYear = uniResult.getInt(11);
//                uniMonth = uniResult.getInt(12);
//                uniDay = uniResult.getInt(13);
//            }
//            uniResult.close();
//
//            //If previously picked a date without leaving the activity
//            if(datePicked && (uniYear != 0)){
//                year = uniYear;
//                month = uniMonth;
//                day = uniDay;
//            //If user previously picked a date but then left the activity
//            }else if(!alarmDay.equals("") && !alarmMonth.equals("") && !alarmYear.equals("")){
//                year = Integer.parseInt(alarmYear);
//                month = Integer.parseInt(alarmMonth);
//                day = Integer.parseInt(alarmDay);
//            //If no date set
//            }else{
//                year = calendar.get(Calendar.YEAR);
//                month = calendar.get(Calendar.MONTH);
//                day = calendar.get(Calendar.DAY_OF_MONTH);
//            }
//
//            DatePickerDialog datePickerDialog;
//
//            //Initialise date picker based on light or dark mode
//            if(!lightDark) {
//                datePickerDialog = new DatePickerDialog(getActivity(),
//                        AlertDialog.THEME_DEVICE_DEFAULT_DARK, this, year, month, day);
//            }else{
//                datePickerDialog = new DatePickerDialog(getActivity(),
//                        AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, this, year, month, day);
//            }
//
//            //Make so all previous dates are inactive.
//            // User shouldn't be able to set due date to in the past
//            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
//
//            return datePickerDialog;

            //Set default values of date picker to current date
            final Calendar calendar = Calendar.getInstance();
            int year;
            int month;
            int day;

            DatePickerDialog datePickerDialog;

            if(task.getTimestamp() != 0){
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(task.getTimestamp());
                year = cal.get(Calendar.YEAR);
                month = cal.get(Calendar.MONTH);
                day = cal.get(Calendar.DAY_OF_MONTH);
            }else if(tempDay != -1) {
                year = tempYear;
                month = tempMonth;
                day = tempDay;
            }else{
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
            }

            //Initialise date picker
            datePickerDialog = new DatePickerDialog(getActivity(),
                    AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, this, year, month, day);

            //Make so all previous dates are inactive.
            //User shouldn't be able to set due date to in the past
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

            return datePickerDialog;

        }

        public void onDateSet(DatePicker view, int year, int month, int day){

//            if(!mute){
//                blip.start();
//            }
//
//            TextView dateTextView = getActivity().findViewById(R.id.dateTextView);
//
//            //Format and display chosen date
//            String formattedMonth = "";
//
//            int intMonth = Integer.valueOf(month) + 1;
//            if(intMonth == 1){
//                formattedMonth = getString(R.string.jan);
//            }else if(intMonth == 2){
//                formattedMonth = getString(R.string.feb);
//            }else if(intMonth == 3){
//                formattedMonth = getString(R.string.mar);
//            }else if(intMonth == 4){
//                formattedMonth = getString(R.string.apr);
//            }else if(intMonth == 5){
//                formattedMonth = getString(R.string.may);
//            }else if(intMonth == 6){
//                formattedMonth = getString(R.string.jun);
//            }else if(intMonth == 7){
//                formattedMonth = getString(R.string.jul);
//            }else if(intMonth == 8){
//                formattedMonth = getString(R.string.aug);
//            }else if(intMonth == 9){
//                formattedMonth = getString(R.string.sep);
//            }else if(intMonth == 10){
//                formattedMonth = getString(R.string.oct);
//            }else if(intMonth == 11){
//                formattedMonth = getString(R.string.nov);
//            }else if(intMonth == 12){
//                formattedMonth = getString(R.string.dec);
//            }
//
//            String lang = String.valueOf(Locale.getDefault());
//            if(lang.equals("en_AS") || lang.equals("en_BM")
//                    || lang.equals("en_CA") || lang.equals("en_GU")
//                    || lang.equals("en_PH")
//                    || lang.equals("en_PR") || lang.equals("en_UM")
//                    || lang.equals("en_US") || lang.equals("en_VI")) {
//                dateTextView.setText(formattedMonth + " " + day);
//            }else{
//                dateTextView.setText(day + " " + formattedMonth);
//            }
//
//            vibrate.vibrate(50);
//
//            //Updating the database
//            db.updateYear(year);
//            db.updateMonth(month);
//            db.updateDay(day);
//            db.updateOriginalDayTemp(String.valueOf(day));
//
//            //Set default time values if user not selected time values already
//            if(!timePicked){
//                Calendar calendar = Calendar.getInstance();
//                int minute = calendar.get(Calendar.MINUTE);
//                int hour = calendar.get(Calendar.HOUR_OF_DAY);
//                int ampm = calendar.get(Calendar.AM_PM);
//                int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
//                int currentMonth = calendar.get(Calendar.MONTH);
//                int currentYear = calendar.get(Calendar.YEAR);
//
//                if((currentYear == year) && (currentMonth == month) && (currentDay == day)
//                        && (hour >= 10)) {
//                    if(hour == 23){
//                        db.updateHour(11);
//                    }else if(hour >= 13) {
//                        db.updateHour(hour - 11);
//                    }else if(hour == 12){
//                        db.updateHour(1);
//                    }else{
//                        db.updateHour(hour + 1);
//                        if (hour == 11) {
//                            ampm = 1;
//                        }
//                    }
//                    db.updateAmPm(ampm);
//                    db.updateMinute(minute);
//                }else if(hour >= 10){
//                    if(hour == 23){
//                        db.updateHour(11);
//                    }else if(hour >= 13) {
//                        db.updateHour(hour - 12);
//                    }else{
//                        db.updateHour(hour);
//                    }
//                    db.updateAmPm(ampm);
//                    db.updateMinute(minute);
//                }else{
//                    db.updateHour(10);
//                    db.updateAmPm(ampm);
//                    db.updateMinute(0);
//                }
//
//            }else{
//                Cursor alarmResult = MainActivity.db.getAlarmData
//                        (Integer.parseInt(dbTaskId));
//                String alarmMinute = "";
//                String alarmHour = "";
//                String alarmAmPm = "";
//                while(alarmResult.moveToNext()){
//                    alarmHour = alarmResult.getString(1);
//                    alarmMinute = alarmResult.getString(2);
//                    alarmAmPm = alarmResult.getString(3);
//                }
//                alarmResult.close();
//                if(!alarmMinute.equals("")) {
//                    db.updateMinute(Integer.parseInt(alarmMinute));
//                    db.updateHour(Integer.parseInt(alarmHour));
//                    db.updateAmPm(Integer.parseInt(alarmAmPm));
//                }
//            }
//
//            setDue = true;
//            datePicked = true;
//            if(lightDark){
//                calendarFadedLight.setVisibility(View.GONE);
//            }else{
//                calendarFadedDark.setVisibility(View.GONE);
//            }
//            calendar.setVisibility(View.VISIBLE);
//
//            if(screenSize == 3){
//                dateTextView.setTextSize(65);
//            }else if(screenSize == 4){
//                dateTextView.setTextSize(85);
//            }else{
//                dateTextView.setTextSize(25);
//            }
//
//            killAlarm.setVisible(true);

            if (!boolMute) {
                mpBlip.start();
            }

            TextView tvDate = getActivity().findViewById(R.id.tvDate);

            vibrate.vibrate(50);

            ImageView calendarFadedLight = getActivity().findViewById(R.id.imgCalendarFaded);
            calendarFadedLight.setVisibility(View.INVISIBLE);

            ImageView calendar = getActivity().findViewById(R.id.imgCalendar);
            calendar.setVisibility(View.VISIBLE);

            if (intScreenSize == 3) {
                tvDate.setTextSize(65);
            } else if (intScreenSize == 4) {
                tvDate.setTextSize(85);
            } else {
                tvDate.setTextSize(25);
            }

            miKillReminder.setVisible(true);

            reminderPresenter.setYear(year);
            reminderPresenter.setMonth(month);
            reminderPresenter.setDay(day);

            tempDay = day;
            tempMonth = month;
            tempYear = year;

            boolDateSet = true;

            tvDate.setText(reminderPresenter.getFormattedDate());

        }

    }

    public static class TimePickerDialogFrag extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener{

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){

//            //Set default values of date picker to current date
//            final Calendar calendar = Calendar.getInstance();
//            int hour;
//            int minute;
//
//            Cursor alarmResult = MainActivity.db.getAlarmData
//                    (Integer.parseInt(dbTaskId));
//            String alarmHour = "";
//            String alarmMinute = "";
//            String alarmAmPm = "";
//            while(alarmResult.moveToNext()){
//                alarmHour = alarmResult.getString(1);
//                alarmMinute = alarmResult.getString(2);
//                alarmAmPm = alarmResult.getString(3);
//            }
//
//            alarmResult.close();
//
//            //getting universal data
//            Cursor uniResult = MainActivity.db.getUniversalData();
//            int uniHour = 0;
//            int uniMinute = 0;
//            int uniAmPm = 0;
//            while(uniResult.moveToNext()){
//                uniHour = uniResult.getInt(14);
//                uniMinute = uniResult.getInt(15);
//                uniAmPm = uniResult.getInt(17);
//            }
//            uniResult.close();
//
//            int defaultTimePickerHour;
//
//            if(timePicked && (uniHour != 0)){
//                minute = uniMinute;
//                hour = uniHour;
//                if(uniAmPm == 1){
//                    hour += 12;
//                    defaultTimePickerHour = hour;
//                }else if(uniAmPm == 0 && hour == 12){
//                    defaultTimePickerHour = 0;
//                }else{
//                    defaultTimePickerHour = hour;
//                }
//            }else if(!alarmHour.equals("") && !alarmMinute.equals("")){
//                minute = Integer.parseInt(alarmMinute);
//                hour = Integer.parseInt(alarmHour);
//                if(alarmAmPm.equals("1")){
//                    hour += 12;
//                    defaultTimePickerHour = hour;
//                }else if (alarmAmPm.equals("0") && hour == 12){
//                    defaultTimePickerHour = 0;
//                }else{
//                    defaultTimePickerHour = hour;
//                }
//            }else{
//                minute = calendar.get(Calendar.MINUTE);
//                hour = calendar.get(Calendar.HOUR_OF_DAY);
//                defaultTimePickerHour = hour;
//            }
//
//            TimePickerDialog timePickerDialog;
//
//            //Initialising time picker based on light or dark
//            if(!lightDark) {
//                if(getResources().getConfiguration().orientation
//                        == Configuration.ORIENTATION_LANDSCAPE) {
//                    timePickerDialog = new TimePickerDialog(this.getActivity(),
//                            AlertDialog.THEME_HOLO_DARK, this,
//                            defaultTimePickerHour, minute, false);
//                }else{
//                    timePickerDialog = new TimePickerDialog(this.getActivity(),
//                            AlertDialog.THEME_DEVICE_DEFAULT_DARK, this,
//                            defaultTimePickerHour, minute, false);
//                }
//            }else{
//                if(getResources().getConfiguration().orientation
//                        == Configuration.ORIENTATION_LANDSCAPE) {
//                    timePickerDialog = new TimePickerDialog(this.getActivity(),
//                            AlertDialog.THEME_HOLO_LIGHT, this,
//                            defaultTimePickerHour, minute, false);
//                }else {
//                    timePickerDialog = new TimePickerDialog(getActivity(),
//                            AlertDialog.THEME_DEVICE_DEFAULT_LIGHT,
//                            this, defaultTimePickerHour, minute, false);
//                }
//            }
//
//            return timePickerDialog;

            //Set default values of date picker to current date
            final Calendar calendar = Calendar.getInstance();
            int hour;
            int minute;

            int defaultTimePickerHour;

            if(task.getTimestamp() != 0){
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(task.getTimestamp());
                minute = cal.get(Calendar.MINUTE);
                defaultTimePickerHour = cal.get(Calendar.HOUR_OF_DAY);
            }else if(tempMinute != -1) {
                minute = tempMinute;
                defaultTimePickerHour = tempHour;
            }else{
                minute = calendar.get(Calendar.MINUTE);
                hour = calendar.get(Calendar.HOUR_OF_DAY);
                defaultTimePickerHour = hour;
            }

            TimePickerDialog timePickerDialog;

            timePickerDialog = new TimePickerDialog(getActivity(),
                    AlertDialog.THEME_DEVICE_DEFAULT_LIGHT,
                    this, defaultTimePickerHour, minute, false);


            return timePickerDialog;

        }

        public void onTimeSet(TimePicker view, int hour, int minute){

//            TextView timeTextView = getActivity().findViewById(R.id.timeTextView);
//
//            //Formatting and displaying selected time
//            String adjustedAmPm = /*String.valueOf(R.string.am)*/"am";//TODO check to see if string references work
//            String adjustedHour = String.valueOf(hour);
//            String adjustedMinute/* = String.valueOf(minute)*/;
//
//            if(!mute){
//                blip.start();
//            }
//
//            if(hour == 0) {
//                adjustedHour = String.valueOf(12);
//                db.updateAmPm(0);
//            }else if(hour == 12){
//                adjustedAmPm = /*String.valueOf(R.string.pm)*/"pm";
//                db.updateAmPm(1);
//            }else if(hour > 12){
//                adjustedHour = String.valueOf(hour - 12);
//                adjustedAmPm = /*String.valueOf(R.string.pm)*/"pm";
//                db.updateAmPm(1);
//            }else{
//                adjustedHour = String.valueOf(hour);
//                db.updateAmPm(0);
//            }
//
//            if(minute < 10){
//                adjustedMinute = ":0" + minute;
//            }else{
//                adjustedMinute = ":" + minute;
//            }
//
//            String formattedTime = adjustedHour + adjustedMinute + adjustedAmPm;
//            timeTextView.setText(formattedTime);
//
//            MainActivity.vibrate.vibrate(50);
//
//            //Updating database
//            db.updateHour(Integer.parseInt(adjustedHour));
//            db.updateMinute(minute);
//
//            //set default date values if user not already selected
//            if(!datePicked){
//                Calendar calendar = Calendar.getInstance();
//                int day = calendar.get(Calendar.DAY_OF_MONTH);
//                int month = calendar.get(Calendar.MONTH);
//                int year = calendar.get(Calendar.YEAR);
//                db.updateDay(day);
//                db.updateOriginalDayTemp(String.valueOf(day));
//                db.updateMonth(month);
//                db.updateYear(year);
//
//            }else{
//                Cursor alarmResult = MainActivity.db.getAlarmData
//                        (Integer.parseInt(dbTaskId));
//                String alarmDay = "";
//                String alarmMonth = "";
//                String alarmYear = "";
//                while(alarmResult.moveToNext()){
//                    alarmDay = alarmResult.getString(4);
//                    alarmMonth = alarmResult.getString(5);
//                    alarmYear = alarmResult.getString(6);
//                }
//                alarmResult.close();
//                Cursor dbTaskResult = db.getData(Integer.parseInt(dbTaskId));
//                String dbOriginalDay = "";
//                while (dbTaskResult.moveToNext()) {
//                    dbOriginalDay = dbTaskResult.getString(20);
//                }
//                dbTaskResult.close();
//                if(!alarmDay.equals("")) {
//                    db.updateDay(Integer.parseInt(alarmDay));
//                    db.updateOriginalDayTemp(String.valueOf(dbOriginalDay));
//                    db.updateMonth(Integer.parseInt(alarmMonth));
//                    db.updateYear(Integer.parseInt(alarmYear));
//                }
//            }
//
//            setDue = true;
//            timePicked = true;
//            if(lightDark) {
//                timeFadedLight.setVisibility(View.GONE);
//            }else{
//                timeFadedDark.setVisibility(View.GONE);
//            }
//            time.setVisibility(View.VISIBLE);
//
//            int screenSize = getResources().getConfiguration().screenLayout &
//                    Configuration.SCREENLAYOUT_SIZE_MASK;
//
//            if(screenSize == 3){
//                timeTextView.setTextSize(65);
//            }else if(screenSize == 4){
//                timeTextView.setTextSize(85);
//            }else {
//                timeTextView.setTextSize(25);
//            }
//
//            killAlarm.setVisible(true);

            TextView timeTextView = getActivity().findViewById(R.id.tvTime);

            if (!boolMute) {
                mpBlip.start();
            }

            MainActivity.vibrate.vibrate(50);

            ImageView timeFadedLight = getActivity().findViewById(R.id.imgTimeFaded);
            timeFadedLight.setVisibility(View.INVISIBLE);

            ImageView time = getActivity().findViewById(R.id.imgTime);
            time.setVisibility(View.VISIBLE);

            int screenSize = getResources().getConfiguration().screenLayout &
                    Configuration.SCREENLAYOUT_SIZE_MASK;

            if (screenSize == 3) {
                timeTextView.setTextSize(65);
            } else if (screenSize == 4) {
                timeTextView.setTextSize(85);
            } else {
                timeTextView.setTextSize(25);
            }

            miKillReminder.setVisible(true);

            reminderPresenter.setHour(hour);
            reminderPresenter.setMinute(minute);

            tempMinute = minute;
            tempHour = hour;

            boolTimeSet = true;

            timeTextView.setText(reminderPresenter.getFormattedTime());

        }

    }

//    @Override
//    protected void onPause(){
//
//        super.onPause();
//
//    }
//
//    @Override
//    protected void onResume() {
//
//        super.onResume();
//
//    }

    @Override
    //Return to main screen when back pressed
    public void onBackPressed() {

//        //updating the alarm in myAdapter
//        if(setDue) {
//            //Determine if repeat needs to be set
//            if(!repeat.equals("none")) {
////                db.updateRepeatInterval(dbTaskId, repeat);
////                db.updateRepeat(dbTaskId, true);
//                db.updateRepeatIntervalTemp(repeat);
//                db.updateRepeatTemp(true);
//
//                if(repeat.equals("day")){
//
//                    repeatInterval = AlarmManager.INTERVAL_DAY;
//
////                    db.updateRepeatInterval(dbTaskId,"day");
//
//                    repeating = true;
//
//                    taskPropertiesShowing = false;
//
//                    //set default date values if user not already selected
//                    if(!datePicked){
//
//                        Calendar calendar = Calendar.getInstance();
//                        int day = calendar.get(Calendar.DAY_OF_MONTH);
//                        int month = calendar.get(Calendar.MONTH);
//                        int year = calendar.get(Calendar.YEAR);
//                        db.updateDay(day);
//                        db.updateOriginalDayTemp(String.valueOf(day));
//                        db.updateMonth(month);
//                        db.updateYear(year);
//                    }else{
//                        getDateFromDB();
//                    }
//
//                    //Set default time values if user not selected time values already
//                    if(!timePicked){
//
//                        Calendar calendar = Calendar.getInstance();
//                        int minute = calendar.get(Calendar.MINUTE);
//                        int hour = calendar.get(Calendar.HOUR_OF_DAY);
//                        int ampm = calendar.get(Calendar.AM_PM);
//
//                            if(hour == 23){
//                                db.updateHour(11);
//                            }else if(hour >= 13) {
//                                db.updateHour(hour - 11);
//                            }else if(hour == 12){
//                                db.updateHour(1);
//                            }else{
//                                db.updateHour(hour + 1);
//                                if (hour == 11) {
//                                    ampm = 1;
//                                }
//                            }
//                            db.updateAmPm(ampm);
//                            db.updateMinute(minute);
//
//                    }else{
//
//                        getTimeFromDB();
//                    }
//
//                    setDue = true;
//                    datePicked = true;
//                    timePicked = true;
//
//                }else if(repeat.equals("week")){
//
//                    repeatInterval = (AlarmManager.INTERVAL_DAY * 7);
//
////                    db.updateRepeatInterval(dbTaskId, "week");
//
//                    repeating = true;
//
//                    taskPropertiesShowing = false;
//
//                    //set default date values if user not already selected
//                    if(!datePicked){
//                        Calendar calendar = Calendar.getInstance();
//                        int day = calendar.get(Calendar.DAY_OF_MONTH);
//                        int month = calendar.get(Calendar.MONTH);
//                        int year = calendar.get(Calendar.YEAR);
//                        db.updateDay(day);
//                        db.updateOriginalDayTemp(String.valueOf(day));
//                        db.updateMonth(month);
//                        db.updateYear(year);
//                    }else{
//                        getDateFromDB();
//                    }
//
//                    if(!timePicked){
//
//                        Calendar calendar = Calendar.getInstance();
//                        int minute = calendar.get(Calendar.MINUTE);
//                        int hour = calendar.get(Calendar.HOUR_OF_DAY);
//                        int ampm = calendar.get(Calendar.AM_PM);
//
//                        if(hour == 23){
//                            db.updateHour(11);
//                        }else if(hour >= 13) {
//                            db.updateHour(hour - 11);
//                        }else if(hour == 12){
//                            db.updateHour(1);
//                        }else{
//                            db.updateHour(hour + 1);
//                            if (hour == 11) {
//                                ampm = 1;
//                            }
//                        }
//                        db.updateAmPm(ampm);
//                        db.updateMinute(minute);
//                    }else{
//                        getTimeFromDB();
//                    }
//
//                    setDue = true;
//                    datePicked = true;
//                    timePicked = true;
//
//                }else if(repeat.equals("month")){
//
////                    db.updateRepeatInterval(dbTaskId, "month");
//
//                    repeating = true;
//
//                    taskPropertiesShowing = false;
//
//                    //set default date values if user not already selected
//                    if(!datePicked){
//                        Calendar calendar = Calendar.getInstance();
//                        int day = calendar.get(Calendar.DAY_OF_MONTH);
//                        int month = calendar.get(Calendar.MONTH);
//                        int year = calendar.get(Calendar.YEAR);
//                        db.updateDay(day);
//                        db.updateOriginalDayTemp(String.valueOf(day));
//                        db.updateMonth(month);
//                        db.updateYear(year);
//                    }else{
//                        getDateFromDB();
//                    }
//
//                    if(!timePicked){
//
//                        Calendar calendar = Calendar.getInstance();
//                        int minute = calendar.get(Calendar.MINUTE);
//                        int hour = calendar.get(Calendar.HOUR_OF_DAY);
//                        int ampm = calendar.get(Calendar.AM_PM);
//
//                        if(hour == 23){
//                            db.updateHour(11);
//                        }else if(hour >= 13) {
//                            db.updateHour(hour - 11);
//                        }else if(hour == 12){
//                            db.updateHour(1);
//                        }else{
//                            db.updateHour(hour + 1);
//                            if (hour == 11) {
//                                ampm = 1;
//                            }
//                        }
//                        db.updateAmPm(ampm);
//                        db.updateMinute(minute);
//                    }else{
//
//                        getTimeFromDB();
//                    }
//
//                    setDue = true;
//                    datePicked = true;
//                    timePicked = true;
//
//                }
//
//            }else if(dbRepeatInterval.equals("")){
//                db.updateRepeatInterval(dbTaskId, "");
//                db.updateRepeat(dbTaskId, false);
//            }
//            db.updateSetAlarm(true);
//            db.updateIgnored(dbTaskId, false);
//            db.updateOverdue(dbTaskId, false);
//            if(!remindersAvailable && dbDueTime.equals("0")) {
//                duesSet++;
//                db.updateDuesSet(duesSet);
//            }
//            db.updateSnooze(dbTaskId, false);
//            db.updateSnoozeData(dbTaskId, "", "", "", "", "", "");
//        }else if(!remindersAvailable && !dbDueTime.equals("0") && !datePicked && !timePicked){
//            duesSet--;
//            db.updateDuesSet(duesSet);
//        }
//
//        //return to mainActivity
//        Intent intent = new Intent();
//
//        intent.setClass(getApplicationContext(), MainActivity.class);
//
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//        startActivity(intent);

        //Timestamp needs to be saved if user has set a reminder
        if (boolDateSet || boolTimeSet || boolRepeatSet) {

            long stamp = reminderPresenter.getCurrentDate();

            int currentYear = reminderPresenter.getCurrentYear(stamp);
            int currentMonth = reminderPresenter.getCurrentMonth(stamp);
            int currentDay = reminderPresenter.getCurrentDay(stamp);
            int currentHour = reminderPresenter.getCurrentHour(stamp);

            Calendar calendar = Calendar.getInstance();
            //set current date if date wasn't picked
            if (!boolDateSet) {
                reminderPresenter.setYear(calendar.get(Calendar.YEAR));
                reminderPresenter.setMonth(calendar.get(Calendar.MONTH));
                reminderPresenter.setDay(calendar.get(Calendar.DAY_OF_MONTH));
            }
            //set current time if time wasn't picked
            if (!boolTimeSet) {
                //set unspecified time to be set one hour into the future
                if(reminderPresenter.getYear() == currentYear
                        && reminderPresenter.getMonth() == currentMonth
                        && reminderPresenter.getDay() == currentDay
                        && currentHour < 9){
                    reminderPresenter.setHour(10);
                    reminderPresenter.setMinute(0);
                }else if(reminderPresenter.getYear() == currentYear
                        && reminderPresenter.getMonth() == currentMonth
                        && reminderPresenter.getDay() == currentDay
                        && currentHour != 23){
                    reminderPresenter.setHour(calendar.get(Calendar.HOUR_OF_DAY) + 1);
                    reminderPresenter.setMinute(calendar.get(Calendar.MINUTE));
                }else {
                    reminderPresenter.setHour(calendar.get(Calendar.HOUR_OF_DAY));
                    reminderPresenter.setMinute(calendar.get(Calendar.MINUTE));
                }
            }

            Calendar reminderCal = Calendar.getInstance();
            reminderCal.set(Calendar.YEAR, reminderPresenter.getYear());
            reminderCal.set(Calendar.MONTH, reminderPresenter.getMonth());
            reminderCal.set(Calendar.DAY_OF_MONTH, reminderPresenter.getDay());
            reminderCal.set(Calendar.HOUR_OF_DAY, reminderPresenter.getHour());
            reminderCal.set(Calendar.MINUTE, reminderPresenter.getMinute());

            if(reminderCal.getTimeInMillis() >= calendar.getTimeInMillis()){
                //Setting timestamp of the reminder
                calendar.set(Calendar.YEAR, reminderPresenter.getYear());
                calendar.set(Calendar.MONTH, reminderPresenter.getMonth());
                calendar.set(Calendar.DAY_OF_MONTH, reminderPresenter.getDay());
                calendar.set(Calendar.HOUR_OF_DAY, reminderPresenter.getHour());
                calendar.set(Calendar.MINUTE, reminderPresenter.getMinute());
                //Updating the task
                reminderPresenter.setTimestamp(calendar.getTimeInMillis());
                reminderPresenter.setDisplayedTimestamp(calendar.getTimeInMillis());

                if (boolRemindersAvailable) {
                    scheduleNotification();
                }

                reminderPresenter.setOriginalDay(reminderPresenter.getDay());

                //don't save. Due time set to in the past
            }else{
                deleteData();
                MainActivity.boolDueInPast = true;
            }

        }

        super.onBackPressed();

    }

//    private void getDateFromDB() {
//        Cursor alarmResult = MainActivity.db.getAlarmData
//                (Integer.parseInt(dbTaskId));
//        String alarmDay = "";
//        String alarmMonth = "";
//        String alarmYear = "";
//        while(alarmResult.moveToNext()){
//            alarmDay = alarmResult.getString(4);
//            alarmMonth = alarmResult.getString(5);
//            alarmYear = alarmResult.getString(6);
//        }
//        alarmResult.close();
//        if(!alarmDay.equals("")) {
//            Cursor dbTaskResult = db.getData(Integer.parseInt(dbTaskId));
//            String dbOriginalDay = "";
//            while (dbTaskResult.moveToNext()) {
//                dbOriginalDay = dbTaskResult.getString(20);
//            }
//            dbTaskResult.close();
//            db.updateDay(Integer.parseInt(alarmDay));
//            db.updateOriginalDayTemp(String.valueOf(dbOriginalDay));
//            db.updateMonth(Integer.parseInt(alarmMonth));
//            db.updateYear(Integer.parseInt(alarmYear));
//        }
//    }

//    private void getTimeFromDB() {
//        Cursor alarmResult = MainActivity.db.getAlarmData
//                (Integer.parseInt(dbTaskId));
//        String alarmMinute = "";
//        String alarmHour = "";
//        String alarmAmPm = "";
//        while(alarmResult.moveToNext()){
//            alarmHour = alarmResult.getString(1);
//            alarmMinute = alarmResult.getString(2);
//            alarmAmPm = alarmResult.getString(3);
//        }
//        alarmResult.close();
//        if(!alarmMinute.equals("")) {
//            db.updateMinute(Integer.parseInt(alarmMinute));
//            db.updateHour(Integer.parseInt(alarmHour));
//            db.updateAmPm(Integer.parseInt(alarmAmPm));
//        }
//    }

    private void scheduleNotification() {

        MainActivity.alertIntent = new Intent(getApplicationContext(), AlertReceiver.class);
        MainActivity.alertIntent.putExtra("snoozeStatus", false);
        MainActivity.alertIntent.putExtra("task", task);

        //Setting alarm
        MainActivity.pendingIntent = PendingIntent.getBroadcast(
                getApplicationContext(), task.getId(), MainActivity.alertIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        MainActivity.alarmManager.cancel(MainActivity.pendingIntent);

        MainActivity.alarmManager.set(AlarmManager.RTC,
                reminderPresenter.getTimestamp(),
                MainActivity.pendingIntent);

    }

}
