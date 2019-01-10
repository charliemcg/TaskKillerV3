package com.violenthoboenterprises.taskkillernoexcuses.activities;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.job.JobScheduler;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.graphics.Rect;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.constraint.Constraints;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.violenthoboenterprises.taskkillernoexcuses.R;
import com.violenthoboenterprises.taskkillernoexcuses.databinding.ActivityMainBinding;
import com.violenthoboenterprises.taskkillernoexcuses.model.MainActivityPresenterImpl;
import com.violenthoboenterprises.taskkillernoexcuses.model.SubtaskViewModel;
import com.violenthoboenterprises.taskkillernoexcuses.model.SubtasksPresenterImpl;
import com.violenthoboenterprises.taskkillernoexcuses.model.Task;
import com.violenthoboenterprises.taskkillernoexcuses.model.TaskAdapter;
import com.violenthoboenterprises.taskkillernoexcuses.model.TaskViewModel;
import com.violenthoboenterprises.taskkillernoexcuses.presenter.MainActivityPresenter;
import com.violenthoboenterprises.taskkillernoexcuses.presenter.SubtasksPresenter;
import com.violenthoboenterprises.taskkillernoexcuses.utils.AlertReceiver;
import com.violenthoboenterprises.taskkillernoexcuses.utils.BootReceiver;
import com.violenthoboenterprises.taskkillernoexcuses.utils.StringConstants;
import com.violenthoboenterprises.taskkillernoexcuses.view.MainActivityView;

import java.util.Calendar;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements
        BillingProcessor.IBillingHandler, MainActivityView {

    private final String TAG = this.getClass().getSimpleName();

    //used to indicate that task properties are showing when deciding on what action back button should take
    public static boolean boolPropertiesShowing;
    //used to determine whether or not to show motivational toasts
    private boolean boolShowMotivation;
    //used to determine if the keyboard is showing
    public static boolean boolKeyboardShowing;
    //used to determine if user has access to color cycling
    private boolean boolColorCyclingAllowed;
    //used to determine if color cycling is enabled
    private boolean boolColorCyclingEnabled;
    //used to indicate if app should display dark or light theme
    public static boolean boolDarkModeEnabled;
    //Used to determine if sound effects should play or not
    public static boolean boolMute;
    //used to indicate that user purchased ad removal
    private boolean boolAdsRemoved;
    //used to indicate that user purchased reminders
    public boolean boolRemindersAvailable;

    //Dimensions of the fab
    private int intFabHeight;
    private int intFabWidth;
    //height of device
    public int intDeviceheight;
    //indicates if the rename hint should be shown
    private int intRenameHint;
    //indicates if the review prompt should be shown
    private int intShowReviewPrompt;
    //Used to determine if color needs to be changed
    private long lngTimeColorLastChanged;
    //timestamp that keeps record of when user downloaded the app.
    // Used for determining when to prompt for a review
    private long lngTimeInstalled;

    //Toasts which show up when adding new task
    static String[] strMotivation;
    //Keep track of last phrase used so as to not have the same thing twice in a row
    static String strLastToast;
    String[] lightHighlights = {"#ee0019f8", "#ef0067ff", "#ef00a7ef", "#ee55b3ff", "#ef6b79f2",
            "#ef50a9f2", "#ef7c00f8", "#eecc00ff", "#eeff00e6", "#eeff009a", "#eeef0048",
            "#eeef0000", "#eeef4800", "#eeef8f00", "#eeff38ec", "#eeef1a7a", "#eeef85d4",
            "#eeae87f4", "#eeb2a2f4", "#eef4a2d4", "#eef46c6c", "#eef46f35", "#eef38797"};
    //User selected highlight color
    public String strHighlightColor;

    //Message that shows up when there are no tasks
    private ImageView imgNoTasks;

    //Placeholder banner for when ad cannot be loaded
    private ImageView imgBanner;

    //The banner ad
    private AdView adView;

    //Custom toast
    private TextView toast;
    private RelativeLayout toastView;

    //The editable text box that allows for creating and editing task names
    private EditText etTask;

    //The master view
    private View activityRootView;

    //The keyboard
    public static InputMethodManager keyboard;

    //Allow phone to vibrate
    static Vibrator vibrate;

    //for generating random number to select toast phrases
    static Random random = new Random();

    //Sound played when task marked as complete
    static MediaPlayer mpPunch;
    //Sound played when toast displays
    static MediaPlayer mpSweep;
    //Default sound played throughout the app
    static MediaPlayer mpBlip;
    //Sound played when user selects an in-app purchase
    static MediaPlayer mpChime;
    //Sound played when user selects a remove button
    static MediaPlayer mpTrash;
    //Sound played when user is presented with a hint
    static MediaPlayer mpHint;

    //The action bar
    private Toolbar tb;

    //Action bar options
    MenuItem miPro;

    private static BillingProcessor billingProcessor;

    public static TaskViewModel taskViewModel;

    private FloatingActionButton fab;

    private MainActivityPresenter mainActivityPresenter;

    public TaskAdapter adapter;

    private Task taskBeingEdited;

    public static PendingIntent pendingIntent;
    public static Intent alertIntent;
    public static AlarmManager alarmManager;

    //layout parameters of the fab
    ConstraintLayout.LayoutParams params;

    //preferences used for persisting app-wide data
    public static SharedPreferences preferences;

    //the purchases dialog
    private Dialog dialog;

    //The recycler view
    RecyclerView recyclerView;

    //Layout wrapper that holds the ad and ad placeholder
    RelativeLayout adHolder;

    //The diver that shows up between recycler view items
    DividerItemDecoration dividerItemDecoration;

//    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.dark_gray));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        MobileAds.initialize(this, "ca-app-pub-2378583121223638~1405620900");

        tb = findViewById(R.id.tb);
        setSupportActionBar(tb);

        preferences = this.getSharedPreferences("com.violenthoboenterprises.taskkiller",
                Context.MODE_PRIVATE);

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        if (lngTimeInstalled == 0) {
            long defaultTime = Calendar.getInstance().getTimeInMillis();
            preferences.edit().putLong(StringConstants.TIME_INSTALLED_KEY, defaultTime).apply();
            lngTimeInstalled = preferences.getLong(StringConstants.TIME_INSTALLED_KEY, 0);
        }

        taskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
        SubtaskViewModel subtaskViewModel =
                ViewModelProviders.of(this).get(SubtaskViewModel.class);
        SubtasksPresenter subtasksPresenter = new SubtasksPresenterImpl
                (subtaskViewModel, null);
        mainActivityPresenter = new MainActivityPresenterImpl
                (MainActivity.this, taskViewModel, getApplicationContext(), subtasksPresenter);

        if (!preferences.getBoolean(StringConstants.DATABASE_MERGED_KEY, false)) {
            mainActivityPresenter.migrateDatabase();
        }

        if (preferences.getBoolean(StringConstants.REINSTATE_REMINDERS_AFTER_REBOOT_KEY, false)) {
            BootReceiver bootReceiver = new BootReceiver();
            bootReceiver.reinstateReminders(this);
            preferences.edit().putBoolean(StringConstants.REINSTATE_REMINDERS_AFTER_REBOOT_KEY, false).apply();
        }

        boolMute = preferences.getBoolean(StringConstants.MUTE_KEY, false);
        boolAdsRemoved = preferences.getBoolean(StringConstants.ADS_REMOVED_KEY, false);//TODO change to false
        boolRemindersAvailable = preferences.getBoolean(StringConstants.REMINDERS_AVAILABLE_KEY, false);//TODO change to false
        boolShowMotivation = preferences.getBoolean(StringConstants.MOTIVATION_KEY, true);
        intRenameHint = preferences.getInt(StringConstants.RENAME_HINT_KEY, 0);
        intShowReviewPrompt = preferences.getInt(StringConstants.SHOW_REVIEW_KEY, 0);
        lngTimeInstalled = preferences.getLong(StringConstants.TIME_INSTALLED_KEY, 0);
        boolColorCyclingAllowed = preferences.getBoolean(StringConstants.COLOR_CYCLING_AVAILABLE_KEY, false);
        boolColorCyclingEnabled = preferences.getBoolean(StringConstants.COLOR_CYCLING_ENABLED_KEY, false);
        strHighlightColor = preferences.getString(StringConstants.HIGHLIGHT_COLOR_KEY, "#ff34ff00");
        lngTimeColorLastChanged = preferences.getLong(StringConstants.TIME_COLOR_LAST_CHANGED_KEY, lngTimeColorLastChanged);
        boolDarkModeEnabled = preferences.getBoolean(StringConstants.DARK_MODE_ENABLED_KEY, true);

        //binding the highlight color to attributes in layout file
//        binding.setHighlightColor(Color.parseColor(strHighlightColor));
        etTask = findViewById(R.id.etTaskName);
        keyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        vibrate = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        activityRootView = findViewById(R.id.activityRoot);
        strLastToast = "";
        mpPunch = MediaPlayer.create(this, R.raw.punch);
        mpSweep = MediaPlayer.create(this, R.raw.sweep);
        mpBlip = MediaPlayer.create(this, R.raw.blip);
        mpChime = MediaPlayer.create(this, R.raw.chime);
        mpTrash = MediaPlayer.create(this, R.raw.trash);
        mpHint = MediaPlayer.create(this, R.raw.hint);
        String BILLINGKEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA0tgG+jdhW6GSpvOiNrY58CConsEH9S6iYxyaRxp7a3+9CPzhXohy0LIJxZFZPAkLC0PSJnlA3N2JUHfGSdE5hY/7nXwHas+a8XUaQLHdYaA9usOBUEWs24MZFVNrpg4LnshBuFdM6eJ737ReMvCZAz9/lfoACrRx8ABgYEPs74Y+Ms1697DrQ/OPJFT4BRVuSDBIWmEc8GY4dAlh3/C3DK6FsofsKhkC1+bztIUa2n0XNm5UTJZO4sj4d6K/5A4Qo5qUMvWUFQ08L+1DbNif40y/j4ps8yDn3EW/LNPKbZ9m5avE4j6lLdXMRZ22a8OYhv//MVPhoCJ0/yeXcuOCwQIDAQAB";
        billingProcessor = new BillingProcessor(this, BILLINGKEY, this);
        strMotivation = new String[]{getString(R.string.getItDone),
                getString(R.string.smashThatTask), getString(R.string.beAWinner),
                getString(R.string.onlyWimpsGiveUp), getString(R.string.dontBeAFailure),
                getString(R.string.beVictorious), getString(R.string.killThisTask)};
        toast = findViewById(R.id.tvToast);
        toastView = findViewById(R.id.toastView);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        intDeviceheight = displayMetrics.heightPixels;
        imgNoTasks = findViewById(R.id.imgNoTasks);
        imgBanner = findViewById(R.id.imgBanner);
        adView = findViewById(R.id.adView);
        boolKeyboardShowing = false;
        adHolder = findViewById(R.id.adHolder);

        setImgNoTasks();

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> addTask(null));

        //Assigning highlight color
        etTask.setBackgroundColor(Color.parseColor(strHighlightColor));
        tb.setTitleTextColor(Color.parseColor(strHighlightColor));
        toast.setBackgroundColor(Color.parseColor(strHighlightColor));
        fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(strHighlightColor)));

        params = (ConstraintLayout.LayoutParams) fab.getLayoutParams();
        intFabHeight = params.height;
        intFabWidth = params.width;

        //Setting up the recycler view
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

//        dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
//                getResources().getConfiguration().orientation);
        dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);

        //setting up the adapter
        adapter = new TaskAdapter(this, mainActivityPresenter,
                subtasksPresenter, activityRootView, this);
        recyclerView.setAdapter(adapter);

        //observing the recycler view items for changes
        taskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
        taskViewModel.getAllTasks().observe(this, tasks -> {
            adapter.setTasks(tasks);
            if (adapter.getItemCount() > 0) {
                imgNoTasks.setVisibility(View.GONE);
                if (adapter.getItemCount() > 4 && !boolAdsRemoved) {
                    showBannerAd();
                } else {
                    adHolder.setVisibility(View.GONE);
//                    imgBanner.setVisibility(View.GONE);
                }
            } else {
                setImgNoTasks();
                imgNoTasks.setVisibility(View.VISIBLE);
            }
        });

        //detect swipes
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                //Actions to occur when deleting non repeating task
                if (adapter.getTaskAt(viewHolder.getAdapterPosition()).getRepeatInterval() == null) {
                    //Saving a temporary instance of the deleted task should it need to be reinstated
                    Task taskToReinstate = adapter.getTaskAt(viewHolder.getAdapterPosition());
                    taskViewModel.delete(adapter.getTaskAt(viewHolder.getAdapterPosition()));
                    showSnackbar(taskToReinstate);
                    //showing motivational toast
                    showKilledAffirmationToast();
                    //Actions to occur when deleting repeating task
                } else {

                    long interval = 0;
                    if (adapter.getTaskAt(viewHolder.getAdapterPosition())
                            .getRepeatInterval().equals(StringConstants.MONTH)) {
                        interval = mainActivityPresenter.getInterval(StringConstants.MONTH,
                                adapter.getTaskAt(viewHolder.getAdapterPosition()).getTimestamp(),
                                adapter.getTaskAt(viewHolder.getAdapterPosition()).getOriginalDay());
                    }
                    long newTimestamp = adapter.getTaskAt(viewHolder.getAdapterPosition()).getTimestamp();
                    Calendar cal = Calendar.getInstance();
                    cal.setTimeInMillis(newTimestamp);
                    Calendar currentCal = Calendar.getInstance();
                    Calendar displayedCal = Calendar.getInstance();
                    displayedCal.setTimeInMillis(adapter.getTaskAt(viewHolder.getAdapterPosition()).getDisplayedTimestamp());
                    long diff = currentCal.getTimeInMillis() - displayedCal.getTimeInMillis();
                    //actions to occur if user kills a task early
                    if (diff < 0) {
                        //cancel reminder
                        if (preferences.getBoolean(StringConstants.REMINDERS_AVAILABLE_KEY, false)) {
                            PendingIntent.getBroadcast(getApplicationContext(),
                                    adapter.getTaskAt(viewHolder.getAdapterPosition()).getId(),
                                    MainActivity.alertIntent,
                                    PendingIntent.FLAG_UPDATE_CURRENT).cancel();
                        }
                        if (adapter.getTaskAt(viewHolder.getAdapterPosition()).getRepeatInterval().equals("day")) {
                            //Add another day to the timestamp
                            newTimestamp += AlarmManager.INTERVAL_DAY;
                        } else if (adapter.getTaskAt(viewHolder.getAdapterPosition()).getRepeatInterval().equals("week")) {
                            //Add another week to the timestamp
                            newTimestamp += (AlarmManager.INTERVAL_DAY * 7);
                        } else if (adapter.getTaskAt(viewHolder.getAdapterPosition()).getRepeatInterval().equals("month")) {
                            //Add another month to the timestamp
                            newTimestamp += interval;
                        }
                        adapter.getTaskAt(viewHolder.getAdapterPosition()).setTimestamp(newTimestamp);
                        adapter.getTaskAt(viewHolder.getAdapterPosition()).setDisplayedTimestamp(newTimestamp);

                        //creating new reminder
                        MainActivity.alertIntent = new Intent(getApplicationContext(), AlertReceiver.class);
                        MainActivity.alertIntent.putExtra("snoozeStatus", false);
                        MainActivity.alertIntent.putExtra("task", adapter.getTaskAt(viewHolder.getAdapterPosition()));

                        //Setting alarm
                        MainActivity.pendingIntent = PendingIntent.getBroadcast(
                                getApplicationContext(), adapter.getTaskAt(viewHolder.getAdapterPosition()).getId(), MainActivity.alertIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT);

                        MainActivity.alarmManager.cancel(MainActivity.pendingIntent);

                        MainActivity.alarmManager.set(AlarmManager.RTC,
                                newTimestamp,
                                MainActivity.pendingIntent);
                    }
                    adapter.getTaskAt(viewHolder.getAdapterPosition()).setDisplayedTimestamp(newTimestamp);
                    mainActivityPresenter.update(adapter.getTaskAt(viewHolder.getAdapterPosition()));
                    //display toast
                    int timesShown = preferences.getInt(StringConstants.REPEAT_HINT_KEY, 0);
                    if (timesShown <= 10) {
                        if (timesShown == 1 || timesShown == 10) {
                            showRepeatHintToast();
                        } else {
                            //showing motivational toast
                            showKilledAffirmationToast();
                        }
                        preferences.edit().putInt(StringConstants.REPEAT_HINT_KEY, ++timesShown).apply();
                    } else {
                        //showing motivational toast
                        showKilledAffirmationToast();
                    }
                    //If alert receiver is not functioning then need to update everything here
                    Calendar incorrectCal = Calendar.getInstance();
                    incorrectCal.setTimeInMillis(adapter.getTaskAt(viewHolder.getAdapterPosition()).getTimestamp());
                    long incorrectCalMillis = incorrectCal.getTimeInMillis() / 1000 / 60 / 60 / 24;
                    Calendar now = Calendar.getInstance();
                    long nowMillis = now.getTimeInMillis() / 1000 / 60 / 60 / 24;
                    if (incorrectCalMillis <= nowMillis) {
                        long errorCorrectedStamp = adapter.getTaskAt(viewHolder.getAdapterPosition()).getTimestamp();
                        switch (adapter.getTaskAt(viewHolder.getAdapterPosition()).getRepeatInterval()) {
                            case "day":
                                //Add another day to the timestamp
                                errorCorrectedStamp += AlarmManager.INTERVAL_DAY;
                                adapter.getTaskAt(viewHolder.getAdapterPosition()).setDisplayedTimestamp(errorCorrectedStamp);
                                adapter.getTaskAt(viewHolder.getAdapterPosition()).setTimestamp(errorCorrectedStamp);
                                mainActivityPresenter.update(adapter.getTaskAt(viewHolder.getAdapterPosition()));
                                break;
                            case "week":
                                //Add another week to the timestamp
                                errorCorrectedStamp += (AlarmManager.INTERVAL_DAY * 7);
                                adapter.getTaskAt(viewHolder.getAdapterPosition()).setDisplayedTimestamp(errorCorrectedStamp);
                                adapter.getTaskAt(viewHolder.getAdapterPosition()).setTimestamp(errorCorrectedStamp);
                                mainActivityPresenter.update(adapter.getTaskAt(viewHolder.getAdapterPosition()));
                                break;
                            case "month":
                                //Add another month to the timestamp
                                errorCorrectedStamp += interval;
                                adapter.getTaskAt(viewHolder.getAdapterPosition()).setDisplayedTimestamp(errorCorrectedStamp);
                                adapter.getTaskAt(viewHolder.getAdapterPosition()).setTimestamp(errorCorrectedStamp);
                                mainActivityPresenter.update(adapter.getTaskAt(viewHolder.getAdapterPosition()));
                                break;
                        }
                        MainActivity.alertIntent = new Intent(getApplicationContext(), AlertReceiver.class);
                        MainActivity.alertIntent.putExtra("snoozeStatus", false);
                        MainActivity.alertIntent.putExtra("task", adapter.getTaskAt(viewHolder.getAdapterPosition()));

                        //Setting alarm
                        MainActivity.pendingIntent = PendingIntent.getBroadcast(
                                getApplicationContext(), adapter.getTaskAt(viewHolder.getAdapterPosition()).getId(), MainActivity.alertIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT);

                        MainActivity.alarmManager.cancel(MainActivity.pendingIntent);

                        MainActivity.alarmManager.set(AlarmManager.RTC,
                                adapter.getTaskAt(viewHolder.getAdapterPosition()).getTimestamp(),
                                MainActivity.pendingIntent);
                    }
                    adapter.notifyDataSetChanged();
                }

                final Handler handler = new Handler();
                final Runnable runnable = () -> adapter.notifyDataSetChanged();
                handler.postDelayed(runnable, 500);
                toggleFab(true);
                etTask.setText("");
                //hide keyboard
                if (boolKeyboardShowing) {
                    keyboard.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                }

            }
        }).attachToRecyclerView(recyclerView);

        //Actions to occur when user submits new task
        etTask.setOnEditorActionListener((v, actionId, event) -> {

            toggleFab(true);

            //Actions to take when creating new task
            if (actionId == EditorInfo.IME_ACTION_DONE && taskBeingEdited == null) {

                if (!boolMute) {
                    mpBlip.start();
                }

                vibrate.vibrate(50);

                //Text box and keyboard disappear
                etTask.setVisibility(View.GONE);

                //Hide keyboard
                keyboard.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                //Getting user data
                String taskName = etTask.getText().toString();

                //Clear text from text box
                etTask.setText("");

                if (!taskName.equals("")) {

                    Calendar calendar = Calendar.getInstance();
                    mainActivityPresenter.addTask(null, 0, taskName, null,
                            calendar.getTimeInMillis(), false, false, 0);

                    if (intRenameHint <= 2) {
                        if (intRenameHint == 2) {
                            showRenameHintToast();
                        } else {
                            showMotivationalToast();
                        }
                        intRenameHint++;
                        preferences.edit().putInt(StringConstants.RENAME_HINT_KEY, intRenameHint).apply();
                    } else {
                        showMotivationalToast();
                    }
                }

                return true;

                //Actions to take when editing existing task
            } else if (actionId == EditorInfo.IME_ACTION_DONE) {

                if (!boolMute) {
                    mpBlip.start();
                }

                vibrate.vibrate(50);

                etTask.setVisibility(View.GONE);

                //Hide keyboard
                keyboard.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                //Getting user data
                String editedTaskString = etTask.getText().toString();

                etTask.setText("");

                if (!editedTaskString.equals("")) {

                    mainActivityPresenter.setTask(taskBeingEdited, editedTaskString);

                }

                taskBeingEdited = null;

                return true;

            }

            return false;

        });

        checkLightDark();

    }

    private void setImgNoTasks() {
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT && boolDarkModeEnabled) {
            imgNoTasks.setImageDrawable(getResources().getDrawable(R.drawable.no_tasks_dark));
        }else if (orientation == Configuration.ORIENTATION_PORTRAIT){
            imgNoTasks.setImageDrawable(getResources().getDrawable(R.drawable.no_tasks_light));
        }else if(orientation == Configuration.ORIENTATION_LANDSCAPE && boolDarkModeEnabled){
            imgNoTasks.setImageDrawable(getResources().getDrawable(R.drawable.no_tasks_dark_landscape));
        }else if (orientation == Configuration.ORIENTATION_LANDSCAPE){
            imgNoTasks.setImageDrawable(getResources().getDrawable(R.drawable.no_tasks_light_landscape));
        }
    }

    private void showBannerAd() {
        boolean networkAvailable = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)
                this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            networkAvailable = true;
        }

        adHolder.setVisibility(View.VISIBLE);

        if (networkAvailable) {
            adView.setVisibility(View.VISIBLE);
            final AdRequest banRequest = new AdRequest.Builder().build();
            adView.loadAd(banRequest);
        } else {
            imgBanner.setVisibility(View.VISIBLE);
        }

        adView.setAdListener(new AdListener() {

            @Override
            public void onAdFailedToLoad(int errorCode) {
                imgBanner.setVisibility(View.VISIBLE);
            }

        });
    }

    private void showMotivationalToast() {
        if (boolShowMotivation) {
            //showing motivational toast
            int i = random.nextInt(7);
            while (strMotivation[i].equals(strLastToast)) {
                i = random.nextInt(7);
            }
            strLastToast = strMotivation[i];
            toast.setText(strMotivation[i]);
            final Handler handler = new Handler();

            final Runnable runnable = () -> {
                if (!boolMute) {
                    mpSweep.start();
                }
                toastView.startAnimation(AnimationUtils.loadAnimation
                        (MainActivity.this,
                                R.anim.enter_from_right_fast));
                toastView.setVisibility(View.VISIBLE);
                final Handler handler2 = new Handler();
                final Runnable runnable2 = () -> {
                    toastView.startAnimation(
                            AnimationUtils.loadAnimation
                                    (MainActivity.this,
                                            android.R.anim.fade_out));
                    toastView.setVisibility(View.GONE);
                };
                handler2.postDelayed(runnable2, 1500);
            };

            handler.postDelayed(runnable, 500);
        }
    }

    private void showRenameHintToast() {
        toast.setText(R.string.longClickToRename);
        final Handler handler = new Handler();

        final Runnable runnable = () -> {
            mpHint.start();
            toastView.startAnimation(AnimationUtils
                    .loadAnimation(MainActivity.this,
                            R.anim.enter_from_right_fast));
            toastView.setVisibility(View.VISIBLE);
            final Handler handler2 = new Handler();
            final Runnable runnable2 = () -> {
                toastView.startAnimation
                        (AnimationUtils.loadAnimation
                                (MainActivity.this,
                                        android.R.anim.fade_out));
                toastView.setVisibility(View.GONE);
            };
            handler2.postDelayed(runnable2, 2500);
        };

        handler.postDelayed(runnable, 500);
    }

    private void showRepeatHintToast() {
        toast.setText(R.string.youCanCancelRepeat);
        final Handler handler = new Handler();

        final Runnable runnable = () -> {
            mpHint.start();
            toastView.startAnimation(AnimationUtils.loadAnimation
                    (MainActivity.this, R.anim.enter_from_right_fast));
            toastView.setVisibility(View.VISIBLE);
            final Handler handler2 = new Handler();
            final Runnable runnable2 = () -> {
                toastView.startAnimation
                        (AnimationUtils.loadAnimation
                                (MainActivity.this, android.R.anim.fade_out));
                toastView.setVisibility(View.GONE);
            };
            handler2.postDelayed(runnable2, 2500);
        };

        handler.postDelayed(runnable, 500);
    }

    private void showKilledAffirmationToast() {
        if (boolShowMotivation) {
            //showing motivational toast
            toast.setText(getResources().getString(R.string.youKilledThisTask));
            final Handler handler = new Handler();

            final Runnable runnable = () -> {
                if (!boolMute) {
                    mpSweep.start();
                }
                toastView.startAnimation(AnimationUtils.loadAnimation
                        (MainActivity.this, R.anim.enter_from_right_fast));
                toastView.setVisibility(View.VISIBLE);
                final Handler handler2 = new Handler();
                final Runnable runnable2 = () -> {
                    toastView.startAnimation(
                            AnimationUtils.loadAnimation
                                    (MainActivity.this,
                                            android.R.anim.fade_out));
                    toastView.setVisibility(View.GONE);
                };
                handler2.postDelayed(runnable2, 1500);
            };

            handler.postDelayed(runnable, 500);
        }
    }


    private void showColorCyclingToast() {
        toast.setText(R.string.turnColorCyclingOnOff);
        final Handler handler = new Handler();

        final Runnable runnable = () -> {

            if (!boolMute) {
                mpSweep.start();
            }

            toastView.startAnimation(AnimationUtils.loadAnimation
                    (MainActivity.this, R.anim.enter_from_right_fast));
            toastView.setVisibility(View.VISIBLE);
            final Handler handler2 = new Handler();
            final Runnable runnable2 = () -> {
                toastView.startAnimation(AnimationUtils.loadAnimation
                        (MainActivity.this, android.R.anim.fade_out));
                toastView.setVisibility(View.GONE);
            };
            handler2.postDelayed(runnable2, 3500);
        };

        handler.postDelayed(runnable, 500);
    }

    //Actions to occur when fab clicked
    public void addTask(Task task) {

        //track the task which is being edited. This is null when creating a new task
        taskBeingEdited = task;

        vibrate.vibrate(50);

        //Set return button to 'Done'
//        etTask.setImeOptions(EditorInfo.IME_ACTION_DONE);

        //Check if editing existing task or adding new one
        if (taskBeingEdited != null) {
            //put task name in the edit text
            etTask.setText(taskBeingEdited.getTask());
            etTask.setSelection(etTask.getText().length());
        } else {
            //Ensure that there is no previous text in the text box
            etTask.setText("");
        }

        if (!boolKeyboardShowing) {
            //Show keyboard
            keyboard.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }

        //Actions to occur when keyboard is showing
        checkKeyboardShowing();

    }

    @Override
    public void showPurchases() {
        dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);

        dialog.setContentView(R.layout.dialog_purchases);

        TextView negative = dialog.findViewById(R.id.btnClosePurchases);
        FrameLayout viewRemoveAds = dialog.findViewById(R.id.viewRemoveAds);
        FrameLayout viewGetReminders = dialog.findViewById(R.id.viewGetReminders);
        FrameLayout viewGetColorCycling = dialog.findViewById(R.id.viewGetColorCycling);
        FrameLayout viewUnlockAll = dialog.findViewById(R.id.viewUnlockAll);

        //Purchases should be grayed out and unclickable if already purchased
        if (boolAdsRemoved) {
            viewRemoveAds.setClickable(false);
            ImageView image = dialog.findViewById(R.id.removeAdsImage);
            image.setImageDrawable(getResources().getDrawable(R.drawable.remove_ads_purchased));
        } else {
            viewRemoveAds.setOnClickListener(v -> removeAds());
        }

        if (boolRemindersAvailable) {
            viewGetReminders.setClickable(false);
            ImageView image = dialog.findViewById(R.id.getRemindersImage);
            image.setImageDrawable(getResources().getDrawable(R.drawable.bell_purchased));
        } else {
            viewGetReminders.setOnClickListener(v -> getReminders());
        }

        if (boolColorCyclingAllowed) {
            viewGetColorCycling.setClickable(false);
            ImageView image = dialog.findViewById(R.id.cycleColorsImage);
            image.setImageDrawable(getResources().getDrawable(R.drawable.auto_color_purchased));
        } else {
            viewGetColorCycling.setOnClickListener(v -> getColorCycling());
        }

        if (boolAdsRemoved || boolRemindersAvailable || boolColorCyclingAllowed) {
            viewUnlockAll.setClickable(false);
            ImageView image = dialog.findViewById(R.id.unlockAllImage);
            image.setImageDrawable(getResources().getDrawable(R.drawable.unlock_all_purchased));
        } else {
            viewUnlockAll.setOnClickListener(v -> unlockAll());
        }

        //close the dialog
        negative.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    @Override
    public void toggleFab(boolean showFab) {
        if (showFab) {
            params.height = intFabHeight;
            params.width = intFabWidth;
            fab.setLayoutParams(params);
        } else {
            params.height = 1;
            params.width = 1;
            fab.setLayoutParams(params);
        }
    }

    //Give user option to undo deletion of task
    private void showSnackbar(final Task taskToReinstate) {
        View view = findViewById(R.id.activityRoot);
        Snackbar.make(view, R.string.taskDeleted, Snackbar.LENGTH_SHORT)
                .setAction("UNDO", view1 -> {
                    JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
                    scheduler.cancel(StringConstants.DELETE_TASK_ID);
                    mainActivityPresenter.reinstateTask(taskToReinstate);
                })
                .setActionTextColor(Color.parseColor(strHighlightColor))
                .show();
    }

    public void showPro(View view) {

        showPurchases();

    }

    private void showPrompt() {
        if (mainActivityPresenter.showReviewPrompt(intShowReviewPrompt, lngTimeInstalled)) {
            preferences.edit().putInt(StringConstants.SHOW_REVIEW_KEY, ++intShowReviewPrompt).apply();
            prompt();
        }
    }

    private void prompt() {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);

        dialog.setContentView(R.layout.dialog_review);

        Button positive = dialog.findViewById(R.id.btnPositive);
        Button negative = dialog.findViewById(R.id.btnNegative);

        positive.setOnClickListener(v -> {

            //show review prompt no more than four times. Setting times
            //shown to five means it'll no long be shown
            intShowReviewPrompt = 5;
            preferences.edit().putInt(StringConstants.SHOW_REVIEW_KEY, intShowReviewPrompt).apply();
            String URL = "https://play.google.com/store/apps/details?id=com.violenthoboenterprises.blistful";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(URL));
            startActivity(i);
            dialog.dismiss();

        });

        negative.setOnClickListener(v -> dialog.dismiss());

        dialog.show();

    }

    private void switchColor() {
        int i = random.nextInt(lightHighlights.length);
        strHighlightColor = lightHighlights[i];
        preferences.edit().putString(StringConstants.HIGHLIGHT_COLOR_KEY, lightHighlights[i]).apply();
        Calendar calendar = Calendar.getInstance();
        preferences.edit().putLong(StringConstants.TIME_COLOR_LAST_CHANGED_KEY, calendar.getTimeInMillis() / 1000 / 60 / 60).apply();
        tb.setTitleTextColor(Color.parseColor(strHighlightColor));
        etTask.setBackgroundColor(Color.parseColor(strHighlightColor));
        toast.setBackgroundColor(Color.parseColor(strHighlightColor));
        fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(strHighlightColor)));
    }

    private void checkLightDark() {
        if (boolDarkModeEnabled) {
            activityRootView.setBackgroundColor(getResources().getColor(R.color.dark_gray));
            tb.setSubtitleTextColor(getResources().getColor(R.color.gray));
            adapter.notifyDataSetChanged();
//            imgNoTasks.setImageDrawable(getResources().getDrawable(R.drawable.no_tasks_dark));
            dividerItemDecoration.setDrawable(ContextCompat.getDrawable(recyclerView.getContext(), R.drawable.item_decoration));
        } else {
            activityRootView.setBackgroundColor(getResources().getColor(R.color.white));
            tb.setSubtitleTextColor(getResources().getColor(R.color.dark_gray));
            adapter.notifyDataSetChanged();
//            imgNoTasks.setImageDrawable(getResources().getDrawable(R.drawable.no_tasks_light));
            dividerItemDecoration.setDrawable(ContextCompat.getDrawable(recyclerView.getContext(), R.drawable.item_decoration_light));
        }
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!menu.hasVisibleItems()) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
            //Action bar options
            miPro = this.tb.getMenu().findItem(R.id.itemBuy);
            MenuItem miMotivation = this.tb.getMenu().findItem(R.id.itemMotivation);
            MenuItem miMute = this.tb.getMenu().findItem(R.id.itemMute);
            MenuItem miAutoColor = this.tb.getMenu().findItem(R.id.itemAutoColor);
            MenuItem miDarkMode = this.tb.getMenu().findItem(R.id.itemDarkMode);
            if (boolShowMotivation) {
                miMotivation.setChecked(true);
            }
            if (!boolMute) {
                miMute.setChecked(true);
            }
            if (boolAdsRemoved && boolRemindersAvailable && boolColorCyclingAllowed) {
                miPro.setVisible(false);
            }
            if (boolDarkModeEnabled) {
                miDarkMode.setChecked(true);
            }
            if (boolColorCyclingEnabled) {
                miAutoColor.setChecked(true);
            }
            return true;
        } else {
            miPro.setEnabled(true);
            return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        vibrate.vibrate(50);

        //Actions to occur if user selects 'mute'
        if (id == R.id.itemMute) {

            if (boolMute) {
                boolMute = false;
                item.setChecked(true);
                preferences.edit().putBoolean(StringConstants.MUTE_KEY, false).apply();
            } else {
                boolMute = true;
                item.setChecked(false);
                preferences.edit().putBoolean(StringConstants.MUTE_KEY, true).apply();
            }

            return true;

            //Actions to occur if user selects the pro icon
        } else if (id == R.id.itemBuy) {

            showPurchases();

            return true;

            //Actions to occur if user selects 'motivation'
        } else if (id == R.id.itemMotivation) {

            if (boolShowMotivation) {
                boolShowMotivation = false;
                item.setChecked(false);
                preferences.edit().putBoolean(StringConstants.MOTIVATION_KEY, false).apply();
            } else {
                boolShowMotivation = true;
                item.setChecked(true);
                preferences.edit().putBoolean(StringConstants.MOTIVATION_KEY, true).apply();
            }

            return true;

            //Actions to occur if user selects to change light/dark mode
        } else if (id == R.id.itemDarkMode) {

            if (boolDarkModeEnabled) {
                boolDarkModeEnabled = false;
                preferences.edit().putBoolean(StringConstants.DARK_MODE_ENABLED_KEY, false).apply();
                checkLightDark();
                item.setChecked(false);
            } else {
                boolDarkModeEnabled = true;
                preferences.edit().putBoolean(StringConstants.DARK_MODE_ENABLED_KEY, true).apply();
                checkLightDark();
                item.setChecked(true);
            }
            setImgNoTasks();
            return true;

            //Actions to occur if user selects 'color'
        } else if (id == R.id.itemHighlight) {

            int colorPickerTheme;
            if (!boolDarkModeEnabled) {
                colorPickerTheme = R.style.ColorPickerThemeLight;
            } else {
                colorPickerTheme = R.style.ColorPickerThemeDark;
            }

            ColorPickerDialogBuilder
                    .with(MainActivity.this, colorPickerTheme).setTitle(getString(R.string.chooseColor))
                    .initialColor(Color.parseColor(preferences.getString(StringConstants.HIGHLIGHT_COLOR_KEY, "#ff34ff00")))
                    .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                    .density(10).noSliders().setOnColorSelectedListener(selectedColor -> {
                String tempHighlight = "#" + Integer.toHexString(selectedColor);
                tb.setTitleTextColor(Color.parseColor(tempHighlight));
                fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(tempHighlight)));
                etTask.setBackgroundColor(Color.parseColor(tempHighlight));
            }).setPositiveButton(getString(R.string.oK), (dialog, selectedColor, allColors) -> {
                strHighlightColor = "#" + Integer.toHexString(selectedColor);
                preferences.edit().putString(StringConstants.HIGHLIGHT_COLOR_KEY, strHighlightColor).apply();

                toast.setBackgroundColor(Color.parseColor(strHighlightColor));
            }).setNegativeButton(getString(R.string.cancel), (dialog, which) -> {
                int color = Color.parseColor(preferences.getString(StringConstants.HIGHLIGHT_COLOR_KEY, "#ff34ff00"));
                tb.setTitleTextColor(color);
                fab.setBackgroundTintList(ColorStateList.valueOf(color));
                etTask.setBackgroundColor(color);
            }).build().show();

            return true;

            //Actions to occur if user selects 'cycle colors'
        } else if (id == R.id.itemAutoColor) {

            if (boolColorCyclingAllowed) {
                if (boolColorCyclingEnabled) {
                    boolColorCyclingEnabled = false;
                    item.setChecked(false);
                    preferences.edit().putBoolean(StringConstants.COLOR_CYCLING_ENABLED_KEY, false).apply();
                } else {
                    boolColorCyclingEnabled = true;
                    item.setChecked(true);
                    preferences.edit().putBoolean(StringConstants.COLOR_CYCLING_ENABLED_KEY, true).apply();
                }
            } else {
                showPurchases();
            }

            return true;

        }

        return super.onOptionsItemSelected(item);

    }

    //Actions to occur when keyboard is showing
    void checkKeyboardShowing() {

        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener
                (() -> {

                    Rect screen = new Rect();

                    activityRootView.getWindowVisibleDisplayFrame(screen);

                    if (screen.bottom != intDeviceheight) {

                        etTask.setFocusable(true);

                        etTask.requestFocus();

                        //Textbox is visible and 'add' button is gone
                        // whenever keyboard is showing
                        etTask.setVisibility(View.VISIBLE);

                        //Keyboard is inactive without this line
                        etTask.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);

                        //remove fab when keyboard is up
                        toggleFab(false);

                        boolKeyboardShowing = true;

                    } else {

                        //Textbox is gone and 'add' button is visible whenever
                        //keyboard is not showing
                        etTask.setVisibility(View.GONE);

                        //fab must be visible when keyboard is down
                        toggleFab(true);

                        boolKeyboardShowing = false;

                    }

                });

    }

    @Override
    public void onProductPurchased(@NonNull String productId,
                                   @Nullable TransactionDetails details) {

        if (productId.equals(StringConstants.PURCHASE_ADS_KEY)) {

            preferences.edit().putBoolean(StringConstants.ADS_REMOVED_KEY, true).apply();
            boolAdsRemoved = true;
            if (boolColorCyclingAllowed && boolRemindersAvailable) {
                miPro.setVisible(false);
            }

            imgBanner.setVisibility(View.GONE);
            adView.setVisibility(View.GONE);

            dialog.dismiss();

        } else if (productId.equals(StringConstants.PURCHASE_REMINDERS_KEY)) {

            preferences.edit().putBoolean(StringConstants.REMINDERS_AVAILABLE_KEY, true).apply();
            boolRemindersAvailable = true;
            if (boolColorCyclingAllowed && boolAdsRemoved) {
                miPro.setVisible(false);
            }

            dialog.dismiss();

        } else if (productId.equals(StringConstants.PURCHASE_COLORS_KEY)) {

            showColorCyclingToast();

            preferences.edit().putBoolean(StringConstants.COLOR_CYCLING_AVAILABLE_KEY, true).apply();
            boolColorCyclingAllowed = true;
            if (boolRemindersAvailable && boolAdsRemoved) {
                miPro.setVisible(false);
            }

            dialog.dismiss();

        } else if (productId.equals(StringConstants.PURCHASE_UNLOCK_ALL_KEY)) {

            showColorCyclingToast();

            preferences.edit().putBoolean(StringConstants.ADS_REMOVED_KEY, true).apply();
            preferences.edit().putBoolean(StringConstants.REMINDERS_AVAILABLE_KEY, true).apply();
            preferences.edit().putBoolean(StringConstants.COLOR_CYCLING_AVAILABLE_KEY, true).apply();
            boolAdsRemoved = true;
            boolRemindersAvailable = true;
            boolColorCyclingAllowed = true;
            miPro.setVisible(false);

            imgBanner.setVisibility(View.GONE);
            adView.setVisibility(View.GONE);

            dialog.dismiss();

        }

    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, @Nullable Throwable error) {

        Toast.makeText(MainActivity.this, R.string.somethingWentWrong, Toast.LENGTH_LONG).show();
        dialog.dismiss();

    }

    @Override
    public void onBillingInitialized() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!billingProcessor.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);

        }
    }

    @Override
    public void onDestroy() {
        if (billingProcessor != null) {
            billingProcessor.release();
        }
        super.onDestroy();
    }

    public void removeAds() {
        if (!boolAdsRemoved) {
            vibrate.vibrate(50);
            if (!boolMute) {
                mpChime.start();
            }
            billingProcessor.purchase(this, StringConstants.PURCHASE_ADS_KEY);
        }
    }

    public void getReminders() {
        if (!boolRemindersAvailable) {
            vibrate.vibrate(50);
            if (!boolMute) {
                mpChime.start();
            }
            billingProcessor.purchase(this, StringConstants.PURCHASE_REMINDERS_KEY);
        }
    }

    public void getColorCycling() {
        if (!boolColorCyclingAllowed) {
            vibrate.vibrate(50);
            if (!boolMute) {
                mpChime.start();
            }
            billingProcessor.purchase(this, StringConstants.PURCHASE_COLORS_KEY);
        }
    }

    public void unlockAll() {
        if (!boolColorCyclingAllowed && !boolRemindersAvailable && !boolAdsRemoved) {
            vibrate.vibrate(50);
            if (!boolMute) {
                mpChime.start();
            }
            billingProcessor.purchase(this, StringConstants.PURCHASE_UNLOCK_ALL_KEY);
        }
    }

    @Override
    protected void onResume() {

        super.onResume();

        lngTimeColorLastChanged = preferences.getLong(StringConstants.TIME_COLOR_LAST_CHANGED_KEY, lngTimeInstalled);

        if (boolColorCyclingAllowed && boolColorCyclingEnabled) {
            Calendar cal = Calendar.getInstance();
            long now = cal.getTimeInMillis() / 1000 / 60 / 60;
            lngTimeColorLastChanged = lngTimeColorLastChanged / 1000 / 60 / 60;
            lngTimeColorLastChanged += 4;
            if (now >= (lngTimeColorLastChanged + 4)) {
                switchColor();
            }
        }

        showPrompt();

//        adapter.notifyItemChanged(preferences.getInt(StringConstants.REFRESH_THIS_ITEM_KEY, 0));
        adapter.notifyDataSetChanged();
        toggleFab(true);

    }

    @Override
    //Return to previous selection when back is pressed
    public void onBackPressed() {

        //If task properties are showing then the back button should close them
        if (boolPropertiesShowing) {
            adapter.notifyItemChanged(preferences.getInt(StringConstants.REFRESH_THIS_ITEM_KEY, 0));
            boolPropertiesShowing = false;
        } else {
            super.onBackPressed();
        }

    }

}