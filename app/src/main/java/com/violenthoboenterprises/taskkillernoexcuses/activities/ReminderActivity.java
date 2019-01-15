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
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import com.google.gson.Gson;

import com.google.android.gms.common.internal.safeparcel.SafeParcelableSerializer;
import com.violenthoboenterprises.taskkillernoexcuses.R;
import com.violenthoboenterprises.taskkillernoexcuses.model.Reminder;
import com.violenthoboenterprises.taskkillernoexcuses.model.ReminderPresenterImpl;
import com.violenthoboenterprises.taskkillernoexcuses.model.ReminderViewModel;
import com.violenthoboenterprises.taskkillernoexcuses.model.Task;
import com.violenthoboenterprises.taskkillernoexcuses.model.TaskViewModel;
import com.violenthoboenterprises.taskkillernoexcuses.presenter.ReminderPresenter;
import com.violenthoboenterprises.taskkillernoexcuses.utils.AlertReceiver;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Collection;
import java.util.Locale;

public class ReminderActivity extends MainActivity {

    private final String TAG = this.getClass().getSimpleName();
    private Toolbar tbReminder;
    private ImageView imgTime, imgTimeFaded, imgCalendar, imgCalendarFaded;
    private ImageView imgDailyFaded, imgWeeklyFaded, imgMonthlyFaded, imgCancelRepeatFaded;
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
    private View reminderRoot;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        tbReminder = findViewById(R.id.tbReminder);
        setSupportActionBar(tbReminder);

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
        imgWeeklyFaded = findViewById(R.id.imgWeeklyRepeatFaded);
        imgMonthlyFaded = findViewById(R.id.imgMonthlyRepeatFaded);
        imgCancelRepeatFaded = findViewById(R.id.imgCancelRepeatFaded);
        boolRepeatSet = false;
        boolDateSet = false;
        boolTimeSet = false;
        reminderRoot = findViewById(R.id.reminderRoot);

        View divTwo = findViewById(R.id.divTwo);
        divTwo.setBackgroundColor(Color.parseColor(strHighlightColor));
        View divThree = findViewById(R.id.divThree);
        divThree.setBackgroundColor(Color.parseColor(strHighlightColor));

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

        } else {
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

        checkRepeatIcons();

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

        //Actions to occur when user chooses to cancel the repeat
        imgCancelRepeatFaded.setOnClickListener(v -> {

            if (!boolMute && reminderPresenter.getRepeatInterval() == null) {
                mpBlip.start();
            }

            vibrate.vibrate(50);

            if (reminderPresenter.getYear() == 0 && reminderPresenter.getHour() == 0) {

                miKillReminder.setVisible(false);

            }

            reminderPresenter.setRepeatInterval(null);
            boolRepeatSet = false;

            checkRepeatIcons();

        });

        //Actions to occur if user selects to set a daily recurring alarm
        imgDailyFaded.setOnClickListener(v -> {

            if (!boolMute && reminderPresenter.getRepeatInterval() != null) {
                if (!reminderPresenter.getRepeatInterval().equals(REPEAT_DAY)) {
                    mpBlip.start();
                }
            }

            vibrate.vibrate(50);

            miKillReminder.setVisible(true);

            reminderPresenter.setRepeatInterval(REPEAT_DAY);
            boolRepeatSet = true;
            checkRepeatIcons();

        });

        //Actions to occur if user selects to set a weekly recurring alarm
        imgWeeklyFaded.setOnClickListener(v -> {

            if (!boolMute && reminderPresenter.getRepeatInterval() != null) {
                if (!reminderPresenter.getRepeatInterval().equals(REPEAT_WEEK)) {
                    mpBlip.start();
                }
            }

            vibrate.vibrate(50);

            miKillReminder.setVisible(true);

            reminderPresenter.setRepeatInterval(REPEAT_WEEK);
            boolRepeatSet = true;
            checkRepeatIcons();

        });

        //Actions to occur if user selects to set a monthly  recurring alarm
        imgMonthlyFaded.setOnClickListener(v -> {

            if (!boolMute && reminderPresenter.getRepeatInterval() != null) {
                if (!reminderPresenter.getRepeatInterval().equals(REPEAT_MONTH)) {
                    mpBlip.start();
                }
            }

            vibrate.vibrate(50);

            miKillReminder.setVisible(true);

            reminderPresenter.setRepeatInterval(REPEAT_MONTH);
            boolRepeatSet = true;
            checkRepeatIcons();

        });

        checkLightDark();

    }

    //light up the correct repeat icon
    private void checkRepeatIcons() {
        int fadedColor;
        if (MainActivity.boolDarkModeEnabled) {
            fadedColor = getResources().getColor(R.color.gray);
        } else {
            fadedColor = getResources().getColor(R.color.status_icons_light);
        }
        imgCancelRepeatFaded.setBackgroundColor(fadedColor);
        imgDailyFaded.setBackgroundColor(fadedColor);
        imgWeeklyFaded.setBackgroundColor(fadedColor);
        imgMonthlyFaded.setBackgroundColor(fadedColor);
        if (reminderPresenter.getRepeatInterval() == null) {
            imgCancelRepeatFaded.setBackgroundColor(Color.parseColor(strHighlightColor));
        } else if (reminderPresenter.getRepeatInterval().equals(REPEAT_DAY)) {
            imgDailyFaded.setBackgroundColor(Color.parseColor(strHighlightColor));
        } else if (reminderPresenter.getRepeatInterval().equals(REPEAT_WEEK)) {
            imgWeeklyFaded.setBackgroundColor(Color.parseColor(strHighlightColor));
        } else if (reminderPresenter.getRepeatInterval().equals(REPEAT_MONTH)) {
            imgMonthlyFaded.setBackgroundColor(Color.parseColor(strHighlightColor));
        }
    }

    private void checkLightDark() {
        if (MainActivity.boolDarkModeEnabled) {
            tbReminder.setTitleTextColor(getResources().getColor(R.color.gray));
            tbReminder.setSubtitleTextColor(getResources().getColor(R.color.gray));
            tbReminder.setBackgroundColor(getResources().getColor(R.color.dark_gray));
            reminderRoot.setBackgroundColor(getResources().getColor(R.color.dark_gray));
            tvDate.setTextColor(getResources().getColor(R.color.gray));
            tvTime.setTextColor(getResources().getColor(R.color.gray));
            imgCalendarFaded.setImageDrawable(getResources().getDrawable(R.drawable.calendar_faded));
            imgTimeFaded.setImageDrawable(getResources().getDrawable(R.drawable.time_faded));
            imgCancelRepeatFaded.setImageDrawable(getResources().getDrawable(R.drawable.repeat_none_dark));
            imgDailyFaded.setImageDrawable(getResources().getDrawable(R.drawable.repeat_daily_dark));
            imgWeeklyFaded.setImageDrawable(getResources().getDrawable(R.drawable.repeat_weekly_dark));
            imgMonthlyFaded.setImageDrawable(getResources().getDrawable(R.drawable.repeat_monthly_dark));
        } else {
            tbReminder.setTitleTextColor(getResources().getColor(R.color.black));
            tbReminder.setSubtitleTextColor(getResources().getColor(R.color.mid_gray));
            tbReminder.setBackgroundColor(getResources().getColor(R.color.white));
            reminderRoot.setBackgroundColor(getResources().getColor(R.color.white));
            tvDate.setTextColor(getResources().getColor(R.color.black));
            tvTime.setTextColor(getResources().getColor(R.color.black));
            imgCalendarFaded.setImageDrawable(getResources().getDrawable(R.drawable.calendar_faded_light));
            imgTimeFaded.setImageDrawable(getResources().getDrawable(R.drawable.time_faded_light));
            imgCancelRepeatFaded.setImageDrawable(getResources().getDrawable(R.drawable.repeat_none_light));
            imgDailyFaded.setImageDrawable(getResources().getDrawable(R.drawable.repeat_daily_light));
            imgWeeklyFaded.setImageDrawable(getResources().getDrawable(R.drawable.repeat_weekly_light));
            imgMonthlyFaded.setImageDrawable(getResources().getDrawable(R.drawable.repeat_monthly_light));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

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

        checkRepeatIcons();

        tvDate.setText(R.string.addDate);
        tvTime.setText(R.string.addTime);
    }

    public static class DatePickerDialogFrag extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            //Set default values of date picker to current date
            final Calendar calendar = Calendar.getInstance();
            int year;
            int month;
            int day;

            DatePickerDialog datePickerDialog;

            if (task.getTimestamp() != 0) {
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(task.getTimestamp());
                year = cal.get(Calendar.YEAR);
                month = cal.get(Calendar.MONTH);
                day = cal.get(Calendar.DAY_OF_MONTH);
            } else if (tempDay != -1) {
                year = tempYear;
                month = tempMonth;
                day = tempDay;
            } else {
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

        public void onDateSet(DatePicker view, int year, int month, int day) {

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
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            //Set default values of date picker to current date
            final Calendar calendar = Calendar.getInstance();
            int hour;
            int minute;

            int defaultTimePickerHour;

            if (task.getTimestamp() != 0) {
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(task.getTimestamp());
                minute = cal.get(Calendar.MINUTE);
                defaultTimePickerHour = cal.get(Calendar.HOUR_OF_DAY);
            } else if (tempMinute != -1) {
                minute = tempMinute;
                defaultTimePickerHour = tempHour;
            } else {
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

        public void onTimeSet(TimePicker view, int hour, int minute) {

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

    @Override
    //Return to main screen when back pressed
    public void onBackPressed() {

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
                if (reminderPresenter.getYear() == currentYear
                        && reminderPresenter.getMonth() == currentMonth
                        && reminderPresenter.getDay() == currentDay
                        && currentHour < 9) {
                    reminderPresenter.setHour(10);
                    reminderPresenter.setMinute(0);
                } else if (reminderPresenter.getYear() == currentYear
                        && reminderPresenter.getMonth() == currentMonth
                        && reminderPresenter.getDay() == currentDay
                        && currentHour != 23) {
                    reminderPresenter.setHour(calendar.get(Calendar.HOUR_OF_DAY) + 1);
                    reminderPresenter.setMinute(calendar.get(Calendar.MINUTE));
                } else {
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

            //if user sets a due time which has already elapsed on the current day but hasn't
            //specified a date then day is increased by one
            if (reminderCal.getTimeInMillis() < calendar.getTimeInMillis()) {
                long dueInMillis = reminderCal.getTimeInMillis();
                dueInMillis += 86400000;
                reminderCal.setTimeInMillis(dueInMillis);
                int newDay = reminderCal.get(Calendar.DAY_OF_MONTH);
                reminderPresenter.setDay(newDay);
            }
            //Updating the task
            reminderPresenter.setTimestamp(reminderCal.getTimeInMillis());
            reminderPresenter.setDisplayedTimestamp(reminderCal.getTimeInMillis());

            if (boolRemindersAvailable) {
                scheduleNotification();
            }

            reminderPresenter.setOriginalDay(reminderPresenter.getDay());

        }

        super.onBackPressed();

    }

    private void scheduleNotification() {

        MainActivity.alertIntent = new Intent(getApplicationContext(), AlertReceiver.class);
        MainActivity.alertIntent.putExtra("snoozeStatus", false);
//        MainActivity.alertIntent.putExtra("task", task);

//        String intentData = Gson.toJson(task);
        Gson gson = new Gson();
//        String json = gson.toJson(task);
//        MainActivity.alertIntent.putExtra("task", json);
        MainActivity.alertIntent.putExtra("task", task.getId());
//        MainActivity.alertIntent.putExtra("viewModel", (CharSequence) taskViewModel);
//        String blah = gson.toJson(MainActivity.taskViewModel);
//        MainActivity.alertIntent.putExtra("viewModel", blah);
//        String anotherJson = gson.toJson(MainActivity.taskViewModel);
//        MainActivity.alertIntent.putExtra("viewModel", anotherJson);
        ObjectOutputStream os = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            os = new ObjectOutputStream(out);
            os.writeObject(taskViewModel);
            out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MainActivity.alertIntent.putExtra("viewModel", String.valueOf(out));

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
