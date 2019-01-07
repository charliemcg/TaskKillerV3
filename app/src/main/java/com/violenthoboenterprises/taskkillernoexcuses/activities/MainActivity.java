package com.violenthoboenterprises.taskkillernoexcuses.activities;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.job.JobScheduler;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.violenthoboenterprises.taskkillernoexcuses.Database;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.Random;


public class MainActivity extends AppCompatActivity implements
        BillingProcessor.IBillingHandler, MainActivityView {

    private final String TAG = this.getClass().getSimpleName();

    //used to indicate that task properties are showing when deciding on what action back button should take
    public static boolean boolPropertiesShowing;
    //used to determine whether or not to show motivational toasts
    private boolean boolShowMotivation;
    //used to determine if user needs to be notified that they set a task to be due in the past
//    public static boolean boolDueInPast;
    //used to determine if the keyboard is showing
    public static boolean boolKeyboardShowing;
    //used to determine if user has access to color cycling
    private boolean boolColorCyclingAllowed;
    //used to determine if color cycling is enabled
    private boolean boolColorCyclingEnabled;
    //used to indicate if app should display dark or light theme
    public static boolean boolDarkModeEnabled;
    //Indicates if a task's options are showing
//    static boolean taskOptionsShowing;
    //Indicates if tasks can be clicked on
//    static boolean tasksAreClickable;
    //Indicates if a task is being edited
//    static boolean taskBeingEdited;
    //Indicates there are no menus or unusual stuff showing
//    private boolean restoreNormalListView;
    //Indicates that a checklist is showing
//    static boolean checklistShowing;
    //Indicates that tasks should be faded out due to keyboard being up
//    static boolean fadeTasks;
    //Used when making task centered in list view
//    static boolean centerTask;
    //Used to indicate that user is in the note screen
//    static boolean inNote;
    //Used to indicate that user is in the sub-tasks screen
//    static boolean inChecklist;
    //Used to indicate that a repeating alarm is being set
//    static boolean repeating;
    //Used to indicate that list needs to be reordered
//    static boolean reorderList;
    //Reinstate alarm after reinstating task
//    static boolean reinstateAlarm;
    //Used to determine that task is being set to complete
//    static boolean completeTask;
    //Used to determine if sound effects should play or not
    public static boolean boolMute;
    //Used to determine what colour scheme to use
//    boolean lightDark;
    //used to indicate if color picker is showing
//    boolean colorPickerShowing;
    //used to indicate if purchase options are showing
//    static boolean purchasesShowing;
    //used to indicate that user purchased ad removal
    private boolean boolAdsRemoved;
    //used to indicate that user purchased reminders
    public boolean boolRemindersAvailable;
    //used to indicate that user purchased color cycling
//    boolean colorCyclingAllowed;
    //used to indicate whether color cycling should be on or not
//    boolean colorCyclingEnabled;
    //used to indicate that the snooze options are showing
//    boolean snoozeRowShowing;
    //used to indicate that a task was long clicked
//    boolean longClicked;
//    static boolean killedAnimation;
//    static boolean alarmAnimation;
//    static boolean reinstateAnimation;
//    static int animatePosition;
//    static int animateID;
    //used to block excessive sounds and animations if user kills tasks in close succession
//    boolean blockSoundAndAnimate;
    //indicates that task was just reinstated
//    boolean justReinstated;

//    //task properties require exit animation
//    static boolean exitTaskProperties;
//    //used to determine whether or not to show motivational toasts
//    static boolean showMotivation;
//    //don't show due dates until after ids have been reordered
//    static boolean showDueDates;
//    //indicates that user has been prompted to review the app once
//    boolean reviewOne;
//    //indicates that user has been prompted to review the app twice
//    boolean reviewTwo;
//    //indicates that user has been prompted to review the app three times
//    boolean reviewThree;
//    //indicates that user has been prompted to review the app four times
//    boolean reviewFour;

    //Indicates which task has it's properties showing
//    static int activeTask;
    //Dimensions of the fab
    private int intFabHeight;
    private int intFabWidth;
    //Height of list view as viewable on screen
//    static int listViewHeight;
//    static int thePosition;
    //portrait width of device
//    static int deviceWidth;
    //portrait height of device
    public int intDeviceheight;
    //indicates how many due dates are set because free users have a limitation
//    static int duesSet;
    //indicates if the repeat hint should be shown
//    static int repeatHint;
    //indicates if the rename hint should be shown
    private int intRenameHint;
    //indicates if the review prompt should be shown
    private int intShowReviewPrompt;
    //Used to determine if color needs to be changed
    private long lngTimeColorLastChanged;
    //indicates if the reinstate hint should be shown
//    static int reinstateHint;
    //timestamp that keeps record of when user downloaded the app.
    // Used for determining when to prompt for a review
//    int launchTime;
    //timestamp that keeps record of when user downloaded the app.
    // Used for determining when to prompt for a review
    private long lngTimeInstalled;

    //Interval between repeating alarms
//    static long repeatInterval;

//    //List of task names
//    public static ArrayList<String> taskList;
//    //Keeps track of task IDs sorted by due date
//    public static ArrayList<String> sortedIDs;
//    //Keeps track of task IDs sorted by due date to be used by note class
//    public static ArrayList<String> sortedIdsForNote;

    //Toasts which show up when adding new task
    static String[] strMotivation;
    //Keep track of last phrase used so as to not have the same thing twice in a row
    static String strLastToast;
    //Colors for the auto color change feature
//    String[] darkHighlightsDec = {"-301927437", "-301943809", "-301924429", "-301924506",
//            "-301924582", "-288489728", "-286271744", "-285764608", "-285783552", "-290783233",
//            "-298520705", "-279253196", "-276435040", "-272895810", "-293081265", "-270012593",
//            "-285806940", "-285890251", "-285614910", "-285688950", "-285760348", "-285854154",
//            "-285825859", "-285912726", "-285891627", "-286226187", "-290216971", "-278092811",
//            "-274467388", "-292883375", "-275909497", "-272894895"};
//    String[] darkHighlights = {"#ee00f3f3", "#ee00b3ff", "#ee00ffb3", "#ee00ff66", "#ee00ff1a",
//            "#eecdff00", "#eeefd700", "#eef79400", "#eef74a00", "#eeaaffff", "#ee34ef7f",
//            "#ef5aef34", "#ef85efa0", "#efbbf0be", "#ee87ef4f", "#efe7ef4f", "#eef6eea4",
//            "#eef5a935", "#eef9dcc2", "#eef8bb8a", "#eef7a4a4", "#eef63636", "#eef6a4bd",
//            "#eef5516a", "#eef5a3d5", "#eef088f5", "#eeb3a3f5", "#ef6ca3f5", "#efa3f5c4",
//            "#ee8af451", "#ef8df487", "#efbbf451"};
//    String[] lightHighlightsDec = {"-301983240", "-285186049", "-285169681", "-296373249",
//            "-278169102", "-279926286", "-277085960", "-288620289", "-285277978", "-285278054",
//            "-286326712", "-286326784", "-286308352", "-286290176", "-285263636", "-286320006",
//            "-286292524", "-290551820", "-290282764", "-285957420", "-285971348", "-285970635",
//            "-286029929"};
    String[] lightHighlights = {"#ee0019f8", "#ef0067ff", "#ef00a7ef", "#ee55b3ff", "#ef6b79f2",
            "#ef50a9f2", "#ef7c00f8", "#eecc00ff", "#eeff00e6", "#eeff009a", "#eeef0048",
            "#eeef0000", "#eeef4800", "#eeef8f00", "#eeff38ec", "#eeef1a7a", "#eeef85d4",
            "#eeae87f4", "#eeb2a2f4", "#eef4a2d4", "#eef46c6c", "#eef46f35", "#eef38797"};
    //Decimal version of the highlight color
    private String strHighlightColorDec;
    //User selected highlight color
    public String strHighlightColor;

    //Required for setting notification alarms
//    static Intent alertIntent;

    //Managers notification alarms
//    static AlarmManager alarmManager;

    //Message that shows up when there are no tasks
    private ImageView imgNoTasks;
//    private ImageView noTasksToShowWhite;

    //Placeholder banner for when ad cannot be loaded
    private ImageView imgBanner;

    //The banner ad
    private AdView adView;

    //Custom toast
    private TextView toast;
    private RelativeLayout toastView;

//    static PendingIntent pendIntent;

    //The editable text box that allows for creating and editing task names
    static EditText etTask;

    //The button that facilitates the adding of tasks
//    static Button add;
//    static TextView addIcon;

    //Used for debugging purposes. Should not be visible in final version.
//    Button showDb;
//    Button showAlarmDb;
//    Button showSnoozeDb;
//    Button showUniversalDb;
//    Button showSubtasksDb;

    //Scrollable list
//    static ListView theListView;

    //The master view
    private View activityRootView;

    //The keyboard
    public static InputMethodManager keyboard;

    //Parameters of 'add' button
//    static RelativeLayout.LayoutParams params;
//    static RelativeLayout.LayoutParams iconParams;
//    RelativeLayout.LayoutParams toastParams;

    //Allow phone to vibrate
    static Vibrator vibrate;

    //Allow for updating the list
//    public static ListAdapter[] theAdapter;

    //Inflater for checklists
//    static LayoutInflater inflater;

    //Database for keeping track of notes
//    static Database db;

    //for generating random number to select toast phrases
    static Random random = new Random();

    //The user selectable highlight color
//    static String highlight;

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
//    private Toolbar toolbarDark;
    private Toolbar tb;
//    static RelativeLayout.LayoutParams toolbarParams;

    //Action bar options
    MenuItem miMute;
    MenuItem miLightDark;
    MenuItem miCustomise;
    MenuItem miPro;
    MenuItem miAutoColor;
    MenuItem miMotivation;

    //The color picker view and it's corresponding buttons
    LinearLayout colorPicker;

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

    //In-app purchases view and it's elements
//    static LinearLayout purchases;
//    LinearLayout removeAdsLayout;
//    LinearLayout getRemindersLayout;
//    LinearLayout cycleColorsLayout;
//    LinearLayout unlockAllLayout;
//    TextView removeAdsTitle;
//    TextView removeAdsDescription;
//    TextView getRemindersTitle;
//    TextView getRemindersDescription;
//    TextView cycleColorsTitle;
//    TextView cycleColorsDescription;
//    TextView unlockAllTitle;
//    TextView unlockAllDescription;

//    ImageView removeAdsImg;
//    ImageView removeAdsPurchasedImg;
//    ImageView remindersImg;
//    ImageView remindersPurchasedImg;
//    ImageView autoColorImg;
//    ImageView autoColorPurchasedImg;
//    ImageView unlockAllImg;
//    ImageView unlockAllPurchasedImg;

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.dark_gray));
        }
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        MobileAds.initialize(this, "ca-app-pub-2378583121223638~1405620900");

//        toolbarDark = findViewById(R.id.toolbar_dark);
        tb = findViewById(R.id.tb);
//        setSupportActionBar(toolbarDark);
        setSupportActionBar(tb);

        preferences = this.getSharedPreferences("com.violenthoboenterprises.blistful",
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

        if (preferences.getBoolean(StringConstants.REINSTATE_REMINDERS_AFTER_REBOOT, false)) {
            BootReceiver bootReceiver = new BootReceiver();
            bootReceiver.reinstateReminders(this);
            preferences.edit().putBoolean(StringConstants.REINSTATE_REMINDERS_AFTER_REBOOT, false).apply();
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
//        strHighlightColorDec = preferences.getString(StringConstants.DEC_HIGHLIGHT_COLOR_KEY, "");
        lngTimeColorLastChanged = preferences.getLong(StringConstants.TIME_COLOR_LAST_CHANGED, lngTimeColorLastChanged);
        boolDarkModeEnabled = preferences.getBoolean(StringConstants.DARK_MODE_ENABLED, true);

        //binding the highlight color to attributes in layout file
        binding.setHighlightColor(Color.parseColor(strHighlightColor));

        //Initialising variables
//        taskPropertiesShowing = false;
//        tasksAreClickable = true;
//        taskList = new ArrayList<>();
//        noTasksToShow = findViewById(R.id.noTasks);
//        noTasksToShowWhite = findViewById(R.id.noTasksWhite);
//        taskNameEditText = findViewById(R.id.taskNameEditText);
//        add = findViewById(R.id.add);
//        addIcon = findViewById(R.id.addIcon);
//        theListView = findViewById(R.id.theListView);
//        keyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        params = (RelativeLayout.LayoutParams) add.getLayoutParams();
//        iconParams = (RelativeLayout.LayoutParams) addIcon.getLayoutParams();
//        vibrate = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
//        inflater = LayoutInflater.from(getApplicationContext());
//        addHeight = params.height;
//        addIconHeight = iconParams.height;
//        activityRootView = findViewById(R.id.activityRoot);
//        fadeTasks = false;
//        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        db = new Database(this);
//        lastToast = "";
//        inNote = false;
//        inChecklist = false;
//        taskOptionsShowing = false;
//        repeating = false;
//        repeatInterval = 0;
//        reorderList = false;
//        sortedIDs = new ArrayList<>();
//        reinstateAlarm = false;
//        completeTask = false;
//        punch = MediaPlayer.create(this, R.raw.punch);
//        sweep = MediaPlayer.create(this, R.raw.sweep);
//        blip = MediaPlayer.create(this, R.raw.blip);
//        chime = MediaPlayer.create(this, R.raw.chime);
//        trash = MediaPlayer.create(this, R.raw.trash);
//        hint = MediaPlayer.create(this, R.raw.hint);
//        mute = false;
//        removeAdsTitle = findViewById(R.id.removeAdsTitle);
//        removeAdsDescription = findViewById(R.id.removeAdsDescription);
//        getRemindersTitle = findViewById(R.id.getRemindersTitle);
//        getRemindersDescription = findViewById(R.id.getremindersDescription);
//        cycleColorsTitle = findViewById(R.id.cycleColorsTitle);
//        cycleColorsDescription = findViewById(R.id.cycleColorsDescription);
//        unlockAllTitle = findViewById(R.id.unlockAllTitle);
//        unlockAllDescription = findViewById(R.id.unlockAllDescription);
//        bp = new BillingProcessor(this, "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmkKmTeO/yvcYI+3INnQbDgR6CdrdNvDuDKNSZ093arop02HezphS/EyiHbkvl16ncYZkrXYEhjUnneAHI+PW3lCcBIvxyQBiyXoGz+N001K9Ot+GBK5JPt1Kd0YXhkdB6W+GcE3muh9juvnYQ14xaiv6GR4Nr7AoH0m9DUxcSLXY/nRD8PUUen+BEPWKsbYUELIyuUQU7ng1aKig6386PQVLK3ugkmN6pxhkxgjombfT58ZcMvme23n1IND6M3lE849thuwJc2WBnCIcFo/aieehqEE1w+kfmZMPvRgPprzZ+15Fh3smXqbfxMiduA2J7CiWsfkMxxcdRNjVVD2+VwIDAQAB", this);
        billingProcessor = new BillingProcessor(this, "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlitP2SJaw3CXnItCLQGWsc09r+KuoqvGaOvQQwSIHjmTjwMevsZWkj4kvL0Z6I5TWBoCnmYH65QfMMZWAf24zqd3P5sKhuUtOySueM7efTDC1ysFmeSPVdnPQfTU4d1mH57W7GQfm/lzbCICR1h0c2R9cPDWM0hg9v2muEE1H/MJ/5aXCQsAuIxRSb4B3CTNJGl3dULthkP6aD/DiQehlJryKnxlbiZOalBR1qIXnKSfWwdTUkgnMdViTPD+mlD+7Laa5CTzfFPZVyX97Jv55RY6mr35Eq49gqgJbq7Jl4IkquoULFurpSVkt/xgiZjwvV9YSpftwm9BmPtIBRucRwIDAQAB", this);
//        purchases = findViewById(R.id.purchases);
//        adsRemoved = false;
//        remindersAvailable = false;
//        colorCyclingAllowed = false;
//        colorCyclingEnabled = false;
//        removeAdsLayout = findViewById(R.id.removeAds);
//        getRemindersLayout = findViewById(R.id.getReminders);
//        cycleColorsLayout = findViewById(R.id.cycleColors);
//        unlockAllLayout = findViewById(R.id.unlockAll);
//        motivation = new String[]{getString(R.string.getItDone),
//                getString(R.string.smashThatTask), getString(R.string.beAWinner),
//                getString(R.string.onlyWimpsGiveUp), getString(R.string.dontBeAFailure),
//                getString(R.string.beVictorious)};
//        exitTaskProperties = false;
//        snoozeRowShowing = false;
//        toast = findViewById(R.id.toast);
//        toastView = findViewById(R.id.toastView);
//        killedAnimation = false;
//        reinstateAnimation = false;
//        animatePosition = 0;
//        animateID = 0;
//        longClicked = false;
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        deviceWidth = displayMetrics.widthPixels;
//        deviceheight = displayMetrics.heightPixels;
//        showDb = findViewById(R.id.showDb);
//        showAlarmDb = findViewById(R.id.showAlarmDb);
//        showUniversalDb = findViewById(R.id.showUniversalDb);
//        showSubtasksDb = findViewById(R.id.showSubtasksDb);
//        duesSet = 0;
//        showMotivation = false;
//        showDueDates = true;
//        repeatHint = 0;
//        renameHint = 0;
//        launchTime = 0;
//        reviewOne = false;
//        reviewTwo = false;
//        reviewThree = false;
//        reviewFour = false;
//        removeAdsImg = findViewById(R.id.removeAdsImage);
//        removeAdsPurchasedImg = findViewById(R.id.removeAdsImagePurchased);
//        remindersImg = findViewById(R.id.getRemindersImage);
//        remindersPurchasedImg = findViewById(R.id.getRemindersImagePurchased);
//        autoColorImg = findViewById(R.id.cycleColorsImage);
//        autoColorPurchasedImg = findViewById(R.id.cycleColorsImagePurchased);
//        unlockAllImg = findViewById(R.id.unlockAllImage);
//        unlockAllPurchasedImg = findViewById(R.id.unlockAllImagePurchased);
//        toastParams = (RelativeLayout.LayoutParams) toastView.getLayoutParams();
//        toolbarParams = (RelativeLayout.LayoutParams) toolbarDark.getLayoutParams();
//        theListView.setOnScrollListener(this);
//        blockSoundAndAnimate = false;
//        justReinstated = false;
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

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> addTask(null));

        params = (ConstraintLayout.LayoutParams) fab.getLayoutParams();
        intFabHeight = params.height;
        intFabWidth = params.width;

        //Setting up the recycler view
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                getResources().getConfiguration().orientation);
        recyclerView.addItemDecoration(dividerItemDecoration);

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
                    imgBanner.setVisibility(View.GONE);
                }
            } else {
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
                    if (boolShowMotivation) {
                        //showing motivational toast
                        showKilledAffirmationToast();
                    }
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
                        } else if (boolShowMotivation) {
                            //showing motivational toast
                            showKilledAffirmationToast();
                        }
                        preferences.edit().putInt(StringConstants.REPEAT_HINT_KEY, ++timesShown).apply();
                    }else{
                        //showing motivational toast
                        showKilledAffirmationToast();
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
                        } else if (boolShowMotivation) {
                            showMotivationalToast();
                        }
                        intRenameHint++;
                        preferences.edit().putInt(StringConstants.RENAME_HINT_KEY, intRenameHint).apply();
                    } else if (boolShowMotivation) {
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

//        db.insertUniversalData(mute);

        //getting app-wide data
//        Cursor dbResult = MainActivity.db.getUniversalData();
//        while (dbResult.moveToNext()) {
//            highlight = dbResult.getString(2);
//            lightDark = dbResult.getInt(3) > 0;
//            adsRemoved = dbResult.getInt(5) > 0;
//            remindersAvailable = dbResult.getInt(6) > 0;
//            colorCyclingAllowed = dbResult.getInt(7) > 0;
//            highlightDec = dbResult.getString(29);
//        }
//        dbResult.close();

//        toolbarDark.setTitleTextColor(Color.parseColor(highlight));
//        toolbarLight.setTitleTextColor(Color.parseColor(highlight));

//        addIcon.setTextColor(Color.parseColor(highlight));
//        taskNameEditText.setBackgroundColor(Color.parseColor(highlight));
//        toast.setBackgroundColor(Color.parseColor(highlight));

//        checkLightDark(lightDark);

        //Make task clickable
//        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//
//                //Tasks are not clickable if keyboard is up
//                if(tasksAreClickable && !completeTask) {
//
//                    vibrate.vibrate(50);
//
//                    //checking if task has been killed
//                    Boolean killed = false;
//                    Cursor result = db.getData(Integer
//                            .parseInt(sortedIDs.get(position)));
//                    while (result.moveToNext()) {
//                        killed = result.getInt(6) > 0;
//                    }
//                    result.close();
//
//                    //Selecting a task to view options
//                    if (!taskPropertiesShowing && !killed) {
//
//                        viewProperties(position);
//
//                        theListView.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                theListView.setSelection(activeTask);
////                                MainActivity.theListView.smoothScrollToPosition
////                                        (MainActivity.activeTask, 0
////                                                /*(MainActivity.listViewHeight / 2)
////                                                        - (MainActivity.toolbarParams.height * 2)*/);
//                            }
//                        });
//
//                    //Removes completed task
//                    } else if (!taskPropertiesShowing && killed) {
//
//                        removeTask(position);
//
//                    //Removes task options from view
//                    } else {
//
//                        removeTaskProperties();
//
//                    }
//
//                }else {
//
//                    completeTask = false;
//
//                }
//
//            }
//
//        });

        //Long click allows for editing/reinstating task
//        theListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view,
//                                           int position, long id) {
//
//                //checking if task has been killed
//                Boolean killed = false;
//                Cursor result = db.getData(Integer
//                        .parseInt(sortedIDs.get(position)));
//                while (result.moveToNext()) {
//                    killed = result.getInt(6) > 0;
//                }
//                result.close();
//
//                //Determine if it's possible to edit task
//                if (tasksAreClickable && !killed && !taskPropertiesShowing) {
//
//                    longClicked = true;
//
//                    rename(position);
//
//                //long click reinstates task that is crossed out
//                } else if (tasksAreClickable && killed && !taskPropertiesShowing) {
//
//                    reinstate(position);
//
//                }
//
//                return true;
//
//            }
//
//        });

        //Actions to occur when 'add' selected
//        add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                taskBeingEdited = false;
//
//                goToMyAdapter = true;
//
//                vibrate.vibrate(50);
//
//                //Show keyboard
//                keyboard.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,0);
//
//                //Set return button to 'Done'
//                taskNameEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);
//
//                //Ensure that there is no previous text in the text box
//                taskNameEditText.setText("");
//
//                //Actions to occur when keyboard is showing
//                checkKeyboardShowing();
//
//            }
//
//        });

        //Used for debugging purposes
//        showDb.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Cursor res = db.getAllData();
//
//                if(res.getCount() == 0){
//                    showMessage("Error", "Nothing found");
//                }
//                StringBuffer buffer = new StringBuffer();
//                while(res.moveToNext()){
//                    buffer.append("ID: " + res.getString(0) + "\n");
//                    buffer.append("NOTE: " + res.getString(1) + "\n");
//                    buffer.append("CHECKLIST: " + res.getString(2) + "\n");
//                    buffer.append("TIMESTAMP: " + res.getString(3) + "\n");
//                    buffer.append("TASK: " + res.getString(4) + "\n");
//                    buffer.append("DUE: " + res.getString(5) + "\n");
//                    buffer.append("KILLED: " + res.getString(6) + "\n");
//                    buffer.append("BROADCAST: " + res.getString(7) + "\n");
//                    buffer.append("REPEAT: " + res.getString(8) + "\n");
//                    buffer.append("OVERDUE: " + res.getString(9) + "\n");
//                    buffer.append("SNOOZED: " + res.getString(10) + "\n");
//                    buffer.append("SHOWONCE: " + res.getString(11) + "\n");
//                    buffer.append("INTERVAL: " + res.getString(12) + "\n");
//                    buffer.append("REPEATINTERVAL: " + res.getString(13) + "\n");
//                    buffer.append("IGNORED: " + res.getString(14) + "\n");
//                    buffer.append("CREATETIMESTAMP: " + res.getString(15) + "\n");
//                    buffer.append("SORTEDINDEX: " + res.getString(16) + "\n");
//                    buffer.append("CHECKLISTSIZE: " + res.getString(17) + "\n\n");
//                }
//                res.close();
//
//                showMessage("Data", buffer.toString());
//            }
//        });

        //Used for debugging purposes
//        showAlarmDb.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Cursor res = db.getAllAlarmData();
//                if(res.getCount() == 0){
//                    showMessage("Error", "Nothing found");
//                }
//                StringBuffer buffer = new StringBuffer();
//                while(res.moveToNext()){
//                    buffer.append("ID: " + res.getString(0) + "\n");
//                    buffer.append("HOUR: " + res.getString(1) + "\n");
//                    buffer.append("MINUTE: " + res.getString(2) + "\n");
//                    buffer.append("AMPM: " + res.getString(3) + "\n");
//                    buffer.append("DAY: " + res.getString(4) + "\n");
//                    buffer.append("MONTH: " + res.getString(5) + "\n");
//                    buffer.append("YEAR: " + res.getString(6) + "\n\n");
//                }
//
//                showMessage("Data", buffer.toString());
//
//            }
//
//        });

//        //Used for debugging purposes
//        showSnoozeDb.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Cursor res = noteDb.getAllSnoozeData();
//                if(res.getCount() == 0){
//                    showMessage("Error", "Nothing found");
//                }
//                StringBuffer buffer = new StringBuffer();
//                while(res.moveToNext()){
//                    buffer.append("ID: " + res.getString(0) + "\n");
//                    buffer.append("HOUR: " + res.getString(1) + "\n");
//                    buffer.append("MINUTE: " + res.getString(2) + "\n");
//                    buffer.append("AMPM: " + res.getString(3) + "\n");
//                    buffer.append("DAY: " + res.getString(4) + "\n");
//                    buffer.append("MONTH: " + res.getString(5) + "\n");
//                    buffer.append("YEAR: " + res.getString(6) + "\n\n");
//                }
//
//                showMessage("Data", buffer.toString());
//
//            }
//
//        });

        //Used for debugging purposes
//        showUniversalDb.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Cursor res = db.getAllUniversalData();
//                if(res.getCount() == 0){
//                    showMessage("Error", "Nothing found");
//                }
//                StringBuffer buffer = new StringBuffer();
//                while(res.moveToNext()){
//                    buffer.append("ID: " + res.getString(0) + "\n");
//                    buffer.append("MUTE: " + res.getString(1) + "\n");
//                    buffer.append("HIGHLIGHT: " + res.getString(2) + "\n");
//                    buffer.append("DARKLIGHT: " + res.getString(3) + "\n");
//                    buffer.append("ACTIVETASKNAME: " + res.getString(4) + "\n");
//                    buffer.append("ADSREMOVED: " + res.getString(5) + "\n");
//                    buffer.append("REMINDERSAVAILABLE: " + res.getString(6) + "\n");
//                    buffer.append("CYCLECOLORS: " + res.getString(7) + "\n");
//                    buffer.append("TASKLISTSIZE: " + res.getString(8) + "\n");
//                    buffer.append("CHECKLISTLISTSIZE: " + res.getString(9) + "\n");
//                    buffer.append("SETALARM: " + res.getString(10) + "\n");
//                    buffer.append("YEAR: " + res.getString(11) + "\n");
//                    buffer.append("MONTH: " + res.getString(12) + "\n");
//                    buffer.append("DAY: " + res.getString(13) + "\n");
//                    buffer.append("HOUR: " + res.getString(14) + "\n");
//                    buffer.append("MINUTE: " + res.getString(15) + "\n");
//                    buffer.append("AMPM: " + res.getString(17) + "\n");
//                    buffer.append("DUESSET: " + res.getString(19) + "\n");
//                    buffer.append("HIGHLIGHTDEC: " + res.getString(29) + "\n\n");
//                }
//                res.close();
//
//                showMessage("Data", buffer.toString());
//
//            }
//
//        });

        //Used for debugging purposes
//        showSubtasksDb.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Cursor res = db.getAllSubtaskData();
//                if(res.getCount() == 0){
//                    showMessage("Error", "Nothing found");
//                }
//                StringBuffer buffer = new StringBuffer();
//                while(res.moveToNext()){
//                    buffer.append("ID: " + res.getString(0) + "\n");
//                    buffer.append("SUBTASKID: " + res.getString(1) + "\n");
//                    buffer.append("SUBTASK: " + res.getString(2) + "\n");
//                    buffer.append("SUBTASKKILLED: " + res.getString(3) + "\n");
//                    buffer.append("TIMECREATED: " + res.getString(4) + "\n\n");
//                }
//                res.close();
//
//                showMessage("Data", buffer.toString());
//
//            }
//
//        });

        //Actions to occur when user submits new task
//        taskNameEditText.setOnEditorActionListener(new TextView.OnEditorActionListener(){
//
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//
//                //Actions to take when creating new task
//                if((actionId == EditorInfo.IME_ACTION_DONE) && !taskBeingEdited){
//
//                    if(!mute) {
//                        blip.start();
//                    }
//
//                    vibrate.vibrate(50);
//
//                    //Text box and keyboard disappear
//                    taskNameEditText.setVisibility(View.GONE);
//
//                    //Hide keyboard
//                    keyboard.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY,0);
//
//                    //Getting user data
//                    String taskName = taskNameEditText.getText().toString();
//
//                    //Clear text from text box
//                    taskNameEditText.setText("");
//
//                    //Add new task in task list
//                    createTask(taskName, taskList, taskBeingEdited);
//
//                    //Checks to see if there are still tasks available
//                    noTasksLeft();
//
//                    if(!taskName.equals("")) {
//
//                        Calendar timeNow = new GregorianCalendar();
//
//                        //create records in database
//                        db.insertData(Integer.parseInt(sortedIDs
//                                .get(taskList.size() - 1)), "", taskName,
//                                Integer.parseInt(sortedIDs.get(taskList.size() - 1)),
//                                String.valueOf(timeNow.getTimeInMillis() / 1000));
//                        db.insertAlarmData(Integer.parseInt(sortedIDs
//                                        .get(taskList.size() - 1)), "", "",
//                                "", "", "", "");
//                        db.insertSnoozeData(Integer.parseInt(sortedIDs
//                                        .get(taskList.size() - 1)), "", "",
//                                "", "", "", "");
//
//                        showDueDates = false;
//
//                        reorderList = true;
//
//                        if(MainActivity.renameHint <= 2) {
//                            if(MainActivity.renameHint == 2) {
//                                MainActivity.toast.setText(R.string.longClickToRename);
//                                final Handler handler = new Handler();
//
//                                final Runnable runnable = new Runnable() {
//                                    public void run() {
//                                        hint.start();
//                                        MainActivity.toastView.startAnimation(AnimationUtils
//                                                .loadAnimation(MainActivity.this,
//                                                        R.anim.enter_from_right_fast));
//                                        MainActivity.toastView.setVisibility(View.VISIBLE);
//                                        final Handler handler2 = new Handler();
//                                        final Runnable runnable2 = new Runnable() {
//                                            public void run() {
//                                                MainActivity.toastView.startAnimation
//                                                        (AnimationUtils.loadAnimation
//                                                        (MainActivity.this,
//                                                                android.R.anim.fade_out));
//                                                MainActivity.toastView.setVisibility(View.GONE);
//                                            }
//                                        };
//                                        handler2.postDelayed(runnable2, 2500);
//                                    }
//                                };
//
//                                handler.postDelayed(runnable, 500);
//                            }else{
//                                if (showMotivation) {
//                                    //showing motivational toast
//                                    int i = random.nextInt(6);
//                                    while (motivation[i].equals(lastToast)) {
//                                        i = random.nextInt(6);
//                                    }
//                                    lastToast = motivation[i];
//                                    toast.setText(motivation[i]);
//                                    final Handler handler = new Handler();
//
//                                    final Runnable runnable = new Runnable() {
//                                        public void run() {
//                                            if (!mute) {
//                                                sweep.start();
//                                            }
//                                            toastView.startAnimation(AnimationUtils.loadAnimation
//                                                    (MainActivity.this,
//                                                            R.anim.enter_from_right_fast));
//                                            toastView.setVisibility(View.VISIBLE);
//                                            final Handler handler2 = new Handler();
//                                            final Runnable runnable2 = new Runnable() {
//                                                public void run() {
//                                                    toastView.startAnimation(
//                                                            AnimationUtils.loadAnimation
//                                                            (MainActivity.this,
//                                                                    android.R.anim.fade_out));
//                                                    toastView.setVisibility(View.GONE);
//                                                }
//                                            };
//                                            handler2.postDelayed(runnable2, 1500);
//                                        }
//                                    };
//
//                                    handler.postDelayed(runnable, 500);
//                                }
//                            }
//                            MainActivity.renameHint++;
//                            MainActivity.db.updateRenameHint(MainActivity.renameHint);
//                        }else{
//                            if (showMotivation) {
//                                //showing motivational toast
//                                int i = random.nextInt(6);
//                                while (motivation[i].equals(lastToast)) {
//                                    i = random.nextInt(6);
//                                }
//                                lastToast = motivation[i];
//                                toast.setText(motivation[i]);
//                                final Handler handler = new Handler();
//
//                                final Runnable runnable = new Runnable() {
//                                    public void run() {
//                                        if (!mute) {
//                                            sweep.start();
//                                        }
//                                        toastView.startAnimation(AnimationUtils.loadAnimation
//                                                (MainActivity.this,
//                                                        R.anim.enter_from_right_fast));
//                                        toastView.setVisibility(View.VISIBLE);
//                                        final Handler handler2 = new Handler();
//                                        final Runnable runnable2 = new Runnable() {
//                                            public void run() {
//                                                toastView.startAnimation
//                                                        (AnimationUtils.loadAnimation
//                                                        (MainActivity.this,
//                                                                android.R.anim.fade_out));
//                                                toastView.setVisibility(View.GONE);
//                                            }
//                                        };
//                                        handler2.postDelayed(runnable2, 1500);
//                                    }
//                                };
//
//                                handler.postDelayed(runnable, 500);
//                            }
//                        }
//                    }
//
//                    return true;
//
//                //Actions to take when editing existing task
//                }else if(actionId == EditorInfo.IME_ACTION_DONE){
//
//                    if(!mute) {
//                        blip.start();
//                    }
//
//                    vibrate.vibrate(50);
//
//                    taskNameEditText.setVisibility(View.GONE);
//
//                    //Hide keyboard
//                    keyboard.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY,0);
//
//                    //Getting user data
//                    String editedTaskString = taskNameEditText.getText().toString();
//
//                    createTask(editedTaskString, taskList, taskBeingEdited);
//
//                    tasksAreClickable = true;
//
//                    //Marking editing as complete
//                    taskBeingEdited = false;
//
//                    //Bringing back the 'add' button
//                    params.height = addHeight;
//                    iconParams.height = addIconHeight;
//
//                    add.setLayoutParams(params);
//                    addIcon.setLayoutParams(iconParams);
//
//                    return true;
//
//                }
//
//                return false;
//
//            }
//
//        });

    }

    private void showBannerAd() {
        boolean networkAvailable = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)
                this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            networkAvailable = true;
        }

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
        //showing motivational toast
//        int i = random.nextInt(5);
//        while (strKilledAffirmation[i].equals(strLastKilledToast)) {
//            i = random.nextInt(5);
//        }
//        strLastKilledToast = strKilledAffirmation[i];
//        toast.setText(strKilledAffirmation[i]);
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

//    public void showDueInPastToast() {
//        toast.setText(R.string.cannotSetTask);
//        final Handler handler = new Handler();
//
//        final Runnable runnable = () -> {
//            if (!boolMute) {
//                mpSweep.start();
//            }
//            toastView.startAnimation(AnimationUtils.loadAnimation
//                    (getApplicationContext(), R.anim.enter_from_right_fast));
//            toastView.setVisibility(View.VISIBLE);
//            final Handler handler2 = new Handler();
//            final Runnable runnable2 = () -> {
//                toastView.startAnimation(
//                        AnimationUtils.loadAnimation
//                                (MainActivity.this,
//                                        android.R.anim.fade_out));
//                toastView.setVisibility(View.GONE);
//            };
//            handler2.postDelayed(runnable2, 1500);
//        };
//
//        handler.postDelayed(runnable, 500);
//    }

    //Actions to occur when fab clicked
    public void addTask(Task task) {

        //track the task which is being edited. This is null when creating a new task
        taskBeingEdited = task;

        vibrate.vibrate(50);

        //Set return button to 'Done'
        etTask.setImeOptions(EditorInfo.IME_ACTION_DONE);

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
            keyboard.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
        }

        //Actions to occur when keyboard is showing
        checkKeyboardShowing();

    }

    @Override
    public void showPurchases() {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);

        dialog.setContentView(R.layout.dialog_purchases);

//        Button positive = dialog.findViewById(R.id.btnPositive);
        TextView negative = dialog.findViewById(R.id.btnClosePurchases);

        //Buy button actions
//        positive.setOnClickListener(v -> {
//
//            dialog.dismiss();
//            if (!boolRemindersAvailable && !boolAdsRemoved) {
//
//                vibrate.vibrate(50);
//
//                if (!boolMute) {
//                    mpChime.start();
//                }
//
//                billingProcessor.purchase(MainActivity.this,
//                        /*StringConstants.TEST_PURCHASE*/StringConstants.UNLOCK_ALL);
//
//            }
//
//        });

        //Cancel button options
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
        preferences.edit().putLong(StringConstants.TIME_COLOR_LAST_CHANGED, calendar.getTimeInMillis() / 1000 / 60 / 60).apply();
        tb.setTitleTextColor(Color.parseColor(strHighlightColor));
        etTask.setBackgroundColor(Color.parseColor(strHighlightColor));
        toast.setBackgroundColor(Color.parseColor(strHighlightColor));
        fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(strHighlightColor)));
//        if(!lightDark) {
//            int i = random.nextInt(darkHighlights.length);
//            db.updateHighlight(darkHighlights[i]);
//            db.updateHighlightDec(darkHighlightsDec[i]);
//            Calendar cal = Calendar.getInstance();
//            db.updateColorLastChanged((int) (cal.getTimeInMillis() / 1000 / 60 / 60));
//            highlightDec = darkHighlightsDec[i];
//            highlight = darkHighlights[i];
//        }else{
//            int i = random.nextInt(lightHighlights.length);
//            db.updateHighlight(lightHighlights[i]);
//            db.updateHighlightDec(lightHighlightsDec[i]);
//            Calendar cal = Calendar.getInstance();
//            db.updateColorLastChanged((int) (cal.getTimeInMillis() / 1000 / 60 / 60));
//            highlightDec = lightHighlightsDec[i];
//            highlight = lightHighlights[i];
//        }
//        toolbarDark.setTitleTextColor(Color.parseColor(highlight));
//        toolbarLight.setTitleTextColor(Color.parseColor(highlight));
//        addIcon.setTextColor(Color.parseColor(highlight));
//        taskNameEditText.setBackgroundColor(Color.parseColor(highlight));
//        toast.setBackgroundColor(Color.parseColor(highlight));
//        setDividers(lightDark);
    }

    private void checkLightDark() {
        if(boolDarkModeEnabled){
            activityRootView.setBackgroundColor(getResources().getColor(R.color.dark_gray));
            tb.setSubtitleTextColor(getResources().getColor(R.color.gray));
            adapter.notifyDataSetChanged();
            imgNoTasks.setImageDrawable(getResources().getDrawable(R.drawable.no_tasks_dark));
//            theListView.setBackgroundColor(Color.parseColor("#333333"));
//            toolbarLight.setVisibility(View.INVISIBLE);
//            toolbarDark.setVisibility(View.VISIBLE);
//            setSupportActionBar(toolbarDark);
//            removeAdsTitle.setTextColor(Color.parseColor("#AAAAAA"));
//            removeAdsDescription.setTextColor(Color.parseColor("#AAAAAA"));
//            getRemindersTitle.setTextColor(Color.parseColor("#AAAAAA"));
//            getRemindersDescription.setTextColor(Color.parseColor("#AAAAAA"));
//            cycleColorsTitle.setTextColor(Color.parseColor("#AAAAAA"));
//            cycleColorsDescription.setTextColor(Color.parseColor("#AAAAAA"));
//            unlockAllTitle.setTextColor(Color.parseColor("#AAAAAA"));
//            unlockAllDescription.setTextColor(Color.parseColor("#AAAAAA"));
//            purchases.setBackgroundDrawable(ContextCompat.getDrawable
//                    (this, R.drawable.color_picker_border));
//            removeAdsLayout.setBackgroundDrawable(ContextCompat.getDrawable
//                    (this, R.drawable.color_picker_border));
//            getRemindersLayout.setBackgroundDrawable(ContextCompat.getDrawable
//                    (this, R.drawable.color_picker_border));
//            cycleColorsLayout.setBackgroundDrawable(ContextCompat.getDrawable
//                    (this, R.drawable.color_picker_border));
//            unlockAllLayout.setBackgroundDrawable(ContextCompat.getDrawable
//                    (this, R.drawable.color_picker_border));
//            setDividers(lightDark);
        }else{
            activityRootView.setBackgroundColor(getResources().getColor(R.color.white));
            tb.setSubtitleTextColor(getResources().getColor(R.color.dark_gray));
            adapter.notifyDataSetChanged();
            imgNoTasks.setImageDrawable(getResources().getDrawable(R.drawable.no_tasks_light));
//            theListView.setBackgroundColor(Color.parseColor("#FFFFFF"));
//            toolbarDark.setVisibility(View.INVISIBLE);
//            toolbarLight.setVisibility(View.VISIBLE);
//            setSupportActionBar(toolbarLight);
//            removeAdsTitle.setTextColor(Color.parseColor("#000000"));
//            removeAdsDescription.setTextColor(Color.parseColor("#000000"));
//            getRemindersTitle.setTextColor(Color.parseColor("#000000"));
//            getRemindersDescription.setTextColor(Color.parseColor("#000000"));
//            cycleColorsTitle.setTextColor(Color.parseColor("#000000"));
//            cycleColorsDescription.setTextColor(Color.parseColor("#000000"));
//            unlockAllTitle.setTextColor(Color.parseColor("#000000"));
//            unlockAllDescription.setTextColor(Color.parseColor("#000000"));
//            purchases.setBackgroundDrawable(ContextCompat.getDrawable
//                    (this, R.drawable.color_picker_border_white));
//            removeAdsLayout.setBackgroundDrawable(ContextCompat.getDrawable
//                    (this, R.drawable.purchases_dropshadow));
//            getRemindersLayout.setBackgroundDrawable(ContextCompat.getDrawable
//                    (this, R.drawable.purchases_dropshadow));
//            cycleColorsLayout.setBackgroundDrawable(ContextCompat.getDrawable
//                    (this, R.drawable.purchases_dropshadow));
//            unlockAllLayout.setBackgroundDrawable(ContextCompat.getDrawable
//                    (this, R.drawable.purchases_dropshadow));
//            setDividers(lightDark);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        if(!menu.hasVisibleItems()) {
//            getMenuInflater().inflate(R.menu.menu_main, menu);
//            if (!lightDark) {
//                lightDarkBtn = this.toolbarDark.getMenu().findItem(R.id.lightDark);
//                customiseBtn = this.toolbarDark.getMenu().findItem(R.id.highlight);
//                proBtn = this.toolbarDark.getMenu().findItem(R.id.buy);
//                autoColorBtn = this.toolbarDark.getMenu().findItem(R.id.autoColor);
//                motivationBtn = this.toolbarDark.getMenu().findItem(R.id.motivation);
//                muteBtn = this.toolbarDark.getMenu().findItem(R.id.mute);
//                lightDarkBtn.setChecked(true);
//            } else {
//                lightDarkBtn = this.toolbarLight.getMenu().findItem(R.id.lightDark);
//                customiseBtn = this.toolbarLight.getMenu().findItem(R.id.highlight);
//                proBtn = this.toolbarLight.getMenu().findItem(R.id.buy);
//                autoColorBtn = this.toolbarLight.getMenu().findItem(R.id.autoColor);
//                motivationBtn = this.toolbarLight.getMenu().findItem(R.id.motivation);
//                muteBtn = this.toolbarLight.getMenu().findItem(R.id.mute);
//                lightDarkBtn.setChecked(false);
//            }
//            if(showMotivation){
//                motivationBtn.setChecked(true);
//            }
//            if(!mute){
//                muteBtn.setChecked(true);
//            }
//            if(colorCyclingEnabled){
//                autoColorBtn.setChecked(true);
//            }
//            if(colorCyclingAllowed && adsRemoved && remindersAvailable){
//                proBtn.setVisible(false);
//            }
//            return true;
//        }else if(colorPickerShowing || purchasesShowing){
//            proBtn.setEnabled(false);
//            return false;
//        }else{
//            proBtn.setEnabled(true);
//            return false;
//        }
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
            if (boolAdsRemoved && boolRemindersAvailable) {
                miPro.setVisible(false);
            }
            if(boolDarkModeEnabled){
                miDarkMode.setChecked(true);
            }
            if (boolColorCyclingEnabled){
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

//        int id = item.getItemId();
//
//        vibrate.vibrate(50);
//
//        //TODO find out if return statements are necessary
//        //Actions to occur if user selects 'mute'
//        if (id == R.id.mute) {
//
//            if(mute){
//                mute = false;
//                item.setChecked(true);
//                db.updateMute(false);
//            }else{
//                mute = true;
//                item.setChecked(false);
//                db.updateMute(true);
//            }
//
//            return true;
//            //Actions to occur if user selects 'light mode'
//        } else if (id == R.id.lightDark) {
//
//            if (lightDark) {
//                lightDark = false;
//                checkLightDark(lightDark);
//                db.updateDarkLight(false);
//                item.setChecked(false);
//            } else {
//                lightDark = true;
//                checkLightDark(lightDark);
//                db.updateDarkLight(true);
//                item.setChecked(true);
//            }
//            noTasksLeft();
//            return true;
//            //Actions to occur if user selects 'color'
//        } else if (id == R.id.highlight) {
//
//            int colorPickerTheme;
//            if(lightDark){
//                colorPickerTheme = R.style.ColorPickerThemeLight;
//            }else{
//                colorPickerTheme = R.style.ColorPickerThemeDark;
//            }
//
//            ColorPickerDialogBuilder
//                    .with(MainActivity.this, colorPickerTheme).setTitle(getString(R.string.chooseColor))
//                    .initialColor(Integer.parseInt(highlightDec))
//                    .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
//                    .density(10).noSliders().setOnColorSelectedListener(new OnColorSelectedListener() {
//                        @Override
//                        public void onColorSelected(int selectedColor) {
//                            String tempHighlight = "#" + Integer.toHexString(selectedColor);
//                            toolbarDark.setTitleTextColor(Color.parseColor(tempHighlight));
//                            toolbarLight.setTitleTextColor(Color.parseColor(tempHighlight));
//                            addIcon.setTextColor(Color.parseColor(tempHighlight));
//                            taskNameEditText.setBackgroundColor(Color.parseColor(tempHighlight));
//                        }
//                    }).setPositiveButton(getString(R.string.oK), new ColorPickerClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
//                            highlight = "#" + Integer.toHexString(selectedColor);
//                            highlightDec = String.valueOf(selectedColor);
//                            db.updateHighlight(highlight);
//                            db.updateHighlightDec(String.valueOf(selectedColor));
//
//                            toast.setBackgroundColor(Color.parseColor(highlight));
//                            int[] colors = {0, selectedColor, 0};
//                            theListView.setDivider(new GradientDrawable
//                                    (GradientDrawable.Orientation.RIGHT_LEFT, colors));
//                            if(!lightDark) {
//                                theListView.setDividerHeight(1);
//                            }else{
//                                theListView.setDividerHeight(3);
//                            }
//                        }
//                    }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            toolbarDark.setTitleTextColor(Color.parseColor(highlight));
//                            toolbarLight.setTitleTextColor(Color.parseColor(highlight));
//                            addIcon.setTextColor(Color.parseColor(highlight));
//                            taskNameEditText.setBackgroundColor(Color.parseColor(highlight));
//                        }
//                    }).build().show();
//
//            return true;
//            //Actions to occur if user selects the pro icon
//        } else if (id == R.id.buy) {
//
//            purchasesShowing = true;
//            add.setClickable(false);
//            theListView.setOnItemClickListener(null);
//            taskPropertiesShowing = false;
//            if(lightDark){
//                onCreateOptionsMenu(toolbarLight.getMenu());
//            }else {
//                onCreateOptionsMenu(toolbarDark.getMenu());
//            }
//            purchases.startAnimation(AnimationUtils.loadAnimation
//                    (this, R.anim.enter_from_right));
//
//            final Handler handler = new Handler();
//
//            final Runnable runnable = new Runnable() {
//                public void run() {
//                    purchases.setVisibility(View.VISIBLE);
//                }
//            };
//
//            handler.postDelayed(runnable, 200);
//
//            return true;
//
//            //Actions to occur if user selects 'cycle colors'
//        } else if (id == R.id.autoColor) {
//
//            if(colorCyclingAllowed){
//                if(colorCyclingEnabled){
//                    colorCyclingEnabled = false;
//                    item.setChecked(false);
//                    db.updateCycleEnabled(false);
//                }else{
//                    colorCyclingEnabled = true;
//                    item.setChecked(true);
//                    db.updateCycleEnabled(true);
//                }
//            }else{
//                purchasesShowing = true;
//                add.setClickable(false);
//                theListView.setOnItemClickListener(null);
//                taskPropertiesShowing = false;
//                if(lightDark){
//                    onCreateOptionsMenu(toolbarLight.getMenu());
//                }else {
//                    onCreateOptionsMenu(toolbarDark.getMenu());
//                }
//
//                purchases.startAnimation(AnimationUtils.loadAnimation
//                        (this, R.anim.enter_from_right));
//
//                final Handler handler = new Handler();
//
//                final Runnable runnable = new Runnable() {
//                    public void run() {
//                        purchases.setVisibility(View.VISIBLE);
//                    }
//                };
//
//                handler.postDelayed(runnable, 200);
//            }
//
//            return true;
//
//        }
//        //Actions to occur if user selects 'motivation'
//        else if (id == R.id.motivation) {
//
//            if(showMotivation){
//                showMotivation = false;
//                item.setChecked(false);
//                db.updateMotivation(false);
//            }else{
//                showMotivation = true;
//                item.setChecked(true);
//                db.updateMotivation(true);
//            }
//
//            return true;
//
//        }
//
//        return super.onOptionsItemSelected(item);

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
                Log.d(TAG, "Dark mode is enabled. Disabling now...");
                boolDarkModeEnabled = false;
                preferences.edit().putBoolean(StringConstants.DARK_MODE_ENABLED, false).apply();
                checkLightDark();
//                db.updateDarkLight(false);
                item.setChecked(false);
            } else {
                Log.d(TAG, "Dark mode is disabled. Enabling now...");
                boolDarkModeEnabled = true;
                preferences.edit().putBoolean(StringConstants.DARK_MODE_ENABLED, true).apply();
                checkLightDark();
//                db.updateDarkLight(true);
                item.setChecked(true);
            }
//            noTasksLeft();
            return true;

            //Actions to occur if user selects 'color'
        } else if (id == R.id.itemHighlight) {

            int colorPickerTheme;
//            if(lightDark){
            colorPickerTheme = R.style.ColorPickerThemeLight;
//            }else{
//                colorPickerTheme = R.style.ColorPickerThemeDark;
//            }

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
                strHighlightColorDec = String.valueOf(selectedColor);
                preferences.edit().putString(StringConstants.HIGHLIGHT_COLOR_KEY, strHighlightColor).apply();
//                preferences.edit().putString(StringConstants.DEC_HIGHLIGHT_COLOR_KEY, strHighlightColorDec).apply();

                toast.setBackgroundColor(Color.parseColor(strHighlightColor));
//                        int[] colors = {0, selectedColor, 0};
//                            theListView.setDivider(new GradientDrawable
//                                    (GradientDrawable.Orientation.RIGHT_LEFT, colors));//TODO reinstate this
//                            if(!lightDark) {//TODO reinstate this
//                                theListView.setDividerHeight(1);//TODO reinstate this
//                            }else{//TODO reinstate this
//                                theListView.setDividerHeight(3);//TODO reinstate this
//                            }//TODO reinstate this
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
                if(boolColorCyclingEnabled){
                    boolColorCyclingEnabled = false;
                    item.setChecked(false);
                    preferences.edit().putBoolean(StringConstants.COLOR_CYCLING_ENABLED_KEY, false).apply();
//                    db.updateCycleEnabled(false);
                }else{
                    boolColorCyclingEnabled = true;
                    item.setChecked(true);
                    preferences.edit().putBoolean(StringConstants.COLOR_CYCLING_ENABLED_KEY, true).apply();
//                    db.updateCycleEnabled(true);
                }
            } else {
                showPurchases();
//                purchasesShowing = true;
//                add.setClickable(false);
//                theListView.setOnItemClickListener(null);
//                taskPropertiesShowing = false;
//                if(lightDark){
//                    onCreateOptionsMenu(toolbarLight.getMenu());
//                }else {
//                    onCreateOptionsMenu(toolbarDark.getMenu());
//                }
//
//                purchases.startAnimation(AnimationUtils.loadAnimation
//                        (this, R.anim.enter_from_right));
//
//                final Handler handler = new Handler();
//
//                final Runnable runnable = () -> purchases.setVisibility(View.VISIBLE);
//
//                handler.postDelayed(runnable, 200);
            }

            return true;

        }

        return super.onOptionsItemSelected(item);

    }

//    @Override
//    public void onScrollStateChanged(AbsListView absListView, int i) {
//        if(absListView.getId() == R.id.theListView) {
//
//        }
//    }

//    @Override
//    public void onScroll(AbsListView absListView, final int first,
//                         final int visible, final int total)
//    {
//
//        if(absListView.getId() == R.id.theListView){
//
//            if((first + visible) == total && visible != total){
//
//                //Removes add button so as to not cover the last item
//                params.height = 0;
//                iconParams.height = 0;
//
//                add.setLayoutParams(params);
//                addIcon.setLayoutParams(iconParams);
//
//            }else if(!taskPropertiesShowing){
//
//                //Returns the 'add' button
//                params.height = addHeight;
//                iconParams.height = addIconHeight;
//
//                add.setLayoutParams(params);
//                addIcon.setLayoutParams(iconParams);
//
//            }
//        }
//    }

//    //Shows table results for debugging purposes//
//    public void showMessage(String title, String message){
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setCancelable(true);
//        builder.setTitle(title);
//        builder.setMessage(message);
//        builder.show();
//    }

//    private void removeTask(int position) {
//
//        if(!mute){
//            blip.start();
//        }
//
//        taskList.remove(position);
//
//        MainActivity.checklistShowing = true;
//
//        db.updateTaskListSize(taskList.size());
//        db.updateChecklistListSize(taskList.size());
//
//        //deleting data related to deleted task
//        db.deleteData(String.valueOf(sortedIDs.get(position)));
//        db.deleteAlarmData(String.valueOf(sortedIDs.get(position)));
//        db.deleteSnoozeData(String.valueOf(sortedIDs.get(position)));
//        db.deleteAllSubtaskData(String.valueOf(sortedIDs.get(position)));
//
//        //Cancel notification alarms if one is set
//        alarmManager.cancel(pendIntent.getService(this,
//                Integer.parseInt(sortedIDs.get(position)), alertIntent, 0));
//
//        sortedIDs.remove(position);
//
//        Cursor result;
//        String id;
//        String note;
//        Boolean checklist;
//
//        //Getting existing data before going to Database class to change ids.
//        for(int i = (activeTask + 1); i < taskList.size(); i++){
//            result = db.getData(Integer.parseInt(sortedIDs.get(i)));
//            id = "";
//            note = "";
//            checklist = false;
//            while(result.moveToNext()){
//                id = result.getString(0);
//                note = result.getString(1);
//                checklist = result.getInt(2) == 1;
//            }
//            db.updateData(id, note, checklist);
//            result.close();
//        }
//
//
//        //Checks to see if there are still tasks left
//        noTasksLeft();
//    }

    //Show selected task's properties
//    private void viewProperties(int position) {
//
//        onPause();
//
//        //Marks task as having it's properties showing
//        taskPropertiesShowing = true;
//
//        centerTask = true;
//
//        //Gets position of selected task
//        activeTask = position;
//
//        //Can't change visibility of 'add' button. Have to set height to zero instead.
//        params.height = 0;
//        iconParams.height = 0;
//
//        add.setLayoutParams(params);
//        addIcon.setLayoutParams(iconParams);
//
//    }

    //remove selected task's properties
//    private void removeTaskProperties() {
//
//        //Marks properties as not showing
//        taskPropertiesShowing = false;
//
//        //Returns the 'add' button
//        params.height = addHeight;
//        iconParams.height = addIconHeight;
//
//        add.setLayoutParams(params);
//        addIcon.setLayoutParams(iconParams);
//    }

    //renames task
//    public void rename(int i) {
//
//        //Cannot update the list until after the task has been updated.
//        goToMyAdapter = false;
//
//        //Actions to occur when keyboard is showing
//        checkKeyboardShowing();
//
//        //Indicates that a task is being edited
//        taskBeingEdited = true;
//
//        activeTask = i;
//
//        tasksAreClickable = false;
//
//        fadeTasks = true;
//
//        centerTask = true;
//
//        //Can't change visibility of 'add' button. Have to set height to zero instead.
//        params.height = 0;
//        iconParams.height = 0;
//
//        add.setLayoutParams(params);
//        addIcon.setLayoutParams(iconParams);
//    }

    //reinstates completed task
//    public void reinstate(int i) {
//
//        if(showMotivation) {
//            toast.setText(R.string.taskReinstated);
//            final Handler handler = new Handler();
//
//            final Runnable runnable = new Runnable() {
//                public void run() {
//
//                    if(!mute) {
//                        sweep.start();
//                    }
//
//                    toastView.startAnimation(AnimationUtils.loadAnimation
//                            (MainActivity.this, R.anim.enter_from_right_fast));
//                    toastView.setVisibility(View.VISIBLE);
//                    final Handler handler2 = new Handler();
//                    final Runnable runnable2 = new Runnable() {
//                        public void run() {
//                            toastView.startAnimation(AnimationUtils.loadAnimation
//                                    (MainActivity.this, android.R.anim.fade_out));
//                            toastView.setVisibility(View.GONE);
//                        }
//                    };
//                    handler2.postDelayed(runnable2, 1500);
//                }
//            };
//
//            handler.postDelayed(runnable, 500);
//        }
//
//        //checking if task has a due date
//        Boolean due = false;
//        Cursor result = db.getData(Integer
//                .parseInt(sortedIDs.get(i)));
//        while (result.moveToNext()) {
//            due = result.getInt(5) > 0;
//        }
//        result.close();
//
//        //marks task as not killed in database
//        db.updateKilled(String.valueOf(sortedIDs.get(i)), false);
//        if(!remindersAvailable && duesSet >= 5){
//            db.updateAlarmData(String.valueOf(
//                    sortedIDs.get(i)),
//                    "", "", "", "", "", "");
//            db.updateTimestamp(String.valueOf(sortedIDs.get(i)), "0");
//            db.updateDue(String.valueOf(sortedIDs.get(i)), false);
//            db.updateRepeat(String.valueOf(sortedIDs.get(i)), false);
//            db.updateSnooze(String.valueOf(sortedIDs.get(i)), false);
//            db.updateInterval(String.valueOf(sortedIDs.get(i)),"0");
//            db.updateRepeatInterval(String.valueOf(sortedIDs.get(i)), "");
//            db.updateIgnored(String.valueOf(sortedIDs.get(i)), false);
//            db.updateOverdue(String.valueOf(sortedIDs.get(i)), false);
//        }else if(!remindersAvailable && due){
//            duesSet++;
//            db.updateDuesSet(duesSet);
//        }
//
//        reinstateAlarm = true;
//
//    }

    //Create a new task
//    private void createTask(final String taskName, ArrayList taskList, boolean taskBeingEdited) {
//
//        //Don't allow blank tasks
//        if(!taskName.equals("")) {
//
//            if(!taskBeingEdited) {
//
//                taskList.add(taskName);
//
//                db.updateTaskListSize(taskList.size());
//                db.updateChecklistListSize(taskList.size());
//
//                //finding unique ID for task
//                int i = 0;
//                boolean idIsSet = false;
//                while (!idIsSet) {
//                    if (sortedIDs.contains(String.valueOf(i))) {
//                        i++;
//                    } else {
//                        sortedIDs.add(taskList.size() - 1, String.valueOf(i));
//                        db.updateSortedIndex(String.valueOf(taskList.size() - 1), i);
//                        idIsSet = true;
//                    }
//                }
//
//                alertIntent = new Intent(this, AlertReceiver.class);
//
//                pendIntent = PendingIntent.getBroadcast(this, i, alertIntent,
//                        PendingIntent.FLAG_UPDATE_CURRENT);
//
//            }else{
//
//                taskList.set(activeTask, taskName);
//
//                db.updateName(sortedIDs.get(activeTask), taskName);
//
//            }
//
//        }
//
//    }

    //Tells user to add tasks when task list is empty
//    private void noTasksLeft() {
//
//        //Checks if there are any existing tasks
//        if (taskList.size() == 0){
//
//            //Inform user to add some tasks
//            if(lightDark) {
//                noTasksToShow.setVisibility(View.GONE);
//                noTasksToShowWhite.setVisibility(View.VISIBLE);
//            }else{
//                noTasksToShowWhite.setVisibility(View.GONE);
//                noTasksToShow.setVisibility(View.VISIBLE);
//            }
//
//        }else{
//            noTasksToShow.setVisibility(View.GONE);
//            noTasksToShowWhite.setVisibility(View.GONE);
//        }
//
//    }

    //Actions to occur when keyboard is showing
    void checkKeyboardShowing() {

//        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//
//                Rect screen = new Rect();
//
//                activityRootView.getWindowVisibleDisplayFrame(screen);
//
//                if(screen.bottom != deviceheight){
//
//                    fadeTasks = true;
//
//                    if (goToMyAdapter) {
//
//                        goToMyAdapter = false;
//
//                    }
//
//                    taskNameEditText.setFocusable(true);
//
//                    taskNameEditText.requestFocus();
//
//                    //Keyboard is inactive without this line
//                    taskNameEditText.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
//
//                    //Textbox is visible and 'add' button is gone
//                    // whenever keyboard is showing
//                    taskNameEditText.setVisibility(View.VISIBLE);
//
//                    params.height = 0;
//                    iconParams.height = 0;
//
//                    add.setLayoutParams(params);
//                    addIcon.setLayoutParams(iconParams);
//
//                    tasksAreClickable = false;
//
//                    restoreNormalListView = true;
//
//                }else if(restoreNormalListView){
//
//                    fadeTasks = false;
//
//                    //Textbox is gone and 'add' button is visible whenever
//                    // keyboard is not showing
//                    taskNameEditText.setVisibility(View.GONE);
//
//                    params.height = addHeight;
//                    iconParams.height = addIconHeight;
//
//                    add.setLayoutParams(params);
//                    addIcon.setLayoutParams(iconParams);
//
//                    tasksAreClickable = true;
//
//                    restoreNormalListView = false;
//
//                    //Once editing is complete the adapter can update the list
//                    goToMyAdapter = true;
//
//                }
//
//            }
//
//        });

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

//    private void colorPickerShowing() {
//        if(colorPickerShowing) {
//            colorPicker.startAnimation(AnimationUtils.loadAnimation
//                    (this, R.anim.exit_out_left));
//
//            final Handler handler = new Handler();
//
//            final Runnable runnable = new Runnable() {
//                public void run() {
//                    colorPicker.setVisibility(View.GONE);
//                }
//            };
//
//            handler.postDelayed(runnable, 200);
//            colorPickerShowing = false;
//        }else{
//            purchases.startAnimation(AnimationUtils.loadAnimation
//                    (this, R.anim.exit_out_left));
//
//            final Handler handler = new Handler();
//
//            final Runnable runnable = new Runnable() {
//                public void run() {
//                    purchases.setVisibility(View.GONE);
//                }
//            };
//
//            handler.postDelayed(runnable, 200);
//            purchasesShowing = false;
//        }
//
//        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//                @Override
//                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//
//                //Tasks are not clickable if keyboard is up
//                if(tasksAreClickable && !completeTask) {
//
//                    vibrate.vibrate(50);
//
//                    //checking if task has been killed
//                    Boolean killed = false;
//                    Cursor result = db.getData(Integer
//                            .parseInt(sortedIDs.get(position)));
//                    while (result.moveToNext()) {
//                        killed = result.getInt(6) > 0;
//                    }
//                    result.close();
//
//                    //Selecting a task to view options
//                    if (!taskPropertiesShowing && !killed) {
//
//                        viewProperties(position);
//
//                    //Removes completed task
//                    } else if (!taskPropertiesShowing && killed) {
//
//                        removeTask(position);
//
//                    //Removes task options from view
//                    } else {
//
//                        removeTaskProperties();
//
//                    }
//
//                }else {
//
//                    completeTask = false;
//
//                }
//
//            }
//
//        });
//
//        add.setClickable(true);
//
//        if(lightDark) {
//            onCreateOptionsMenu(toolbarLight.getMenu());
//        }else{
//            onCreateOptionsMenu(toolbarDark.getMenu());
//        }
//
//    }

    //actions to occur if user clicks the complete check box
//    public void complete(View view) {
//
//        if(tasksAreClickable && !MainActivity.blockSoundAndAnimate) {
//
//            Log.i(TAG, "I'm in here");
//
//            if (!mute) {
//                punch.start();
//            }
//
//            LinearLayout parentLayout = (LinearLayout) view.getParent();
//
//            thePosition = theListView.getPositionForView(parentLayout);
//
//            completeTask = true;
//
//            theListView.performItemClick(theListView.getAdapter().getView(
//                    thePosition, null, null), thePosition,
//                    theListView.getAdapter().getItemId(thePosition));
//
//            //getting task data
//            String dbTimestamp = "";
//            String dbTask = "";
//            boolean dbRepeat = false;
//            boolean dbOverdue = false;
//            boolean dbSnooze = false;
//            String dbRepeatInterval = "";
//            Cursor dbResult = MainActivity.db.getData(Integer.parseInt(
//                    MainActivity.sortedIDs.get(thePosition)));
//            while (dbResult.moveToNext()) {
//                dbTimestamp = dbResult.getString(3);
//                dbTask = dbResult.getString(4);
//                dbRepeat = dbResult.getInt(8) > 0;
//                dbOverdue = dbResult.getInt(9) > 0;
//                dbSnooze = dbResult.getInt(10) > 0;
//                dbRepeatInterval = dbResult.getString(13);
//            }
//            dbResult.close();
//
//            if(dbRepeat && !dbSnooze && !dbOverdue){
//
//                db.updateKilledEarly(MainActivity.sortedIDs.get(thePosition),true);
//
//            }
//
//            new Reorder();
//
//        }
//
//    }

//    private void setDividers(boolean lightDark) {
//        if(!lightDark) {
//            int[] colors = {0, Integer.parseInt(highlightDec), 0};
//            theListView.setDivider(new GradientDrawable
//                    (GradientDrawable.Orientation.RIGHT_LEFT, colors));
//            theListView.setDividerHeight(1);
//        }else{
//            int[] colors = {Color.parseColor("#FFFFFF"), Integer.parseInt(highlightDec),
//                    Color.parseColor("#FFFFFF")};
//            theListView.setDivider(new GradientDrawable
//                    (GradientDrawable.Orientation.RIGHT_LEFT, colors));
//            theListView.setDividerHeight(3);
//        }
//    }

//    public void showPro(View view) {
//
//        purchasesShowing = true;
//        add.setClickable(false);
//        theListView.setOnItemClickListener(null);
//        taskPropertiesShowing = false;
//        if(lightDark) {
//            onCreateOptionsMenu(toolbarLight.getMenu());
//        }else{
//            onCreateOptionsMenu(toolbarDark.getMenu());
//        }
//        purchases.startAnimation(AnimationUtils.loadAnimation
//                (this, R.anim.enter_from_right));
//
//        final Handler handler = new Handler();
//
//        final Runnable runnable = new Runnable() {
//            public void run() {
//
//                purchases.setVisibility(View.VISIBLE);
//
//            }
//        };
//
//        handler.postDelayed(runnable, 200);
//
//    }

//    private void showPrompt(final int launchTime) {
//
//        final Calendar calendar = Calendar.getInstance();
//
//        if(!reviewOne && ((launchTime <= (calendar.getTimeInMillis()
//                / 1000 / 60 / 60) - 72))){
//
//            int reviewNumber = 1;
//            prompt(reviewNumber);
//
//        }else if(!reviewTwo && ((launchTime <= (calendar.getTimeInMillis()
//                / 1000 / 60 / 60) - 168))){
//
//            int reviewNumber = 2;
//            prompt(reviewNumber);
//
//        }else if(!reviewThree && ((launchTime <= (calendar.getTimeInMillis()
//                / 1000 / 60 / 60) - 732))){
//
//            int reviewNumber = 3;
//            prompt(reviewNumber);
//
//        }else if(!reviewFour && ((launchTime <= (calendar.getTimeInMillis()
//                / 1000 / 60 / 60) - 1464))){
//
//            int reviewNumber = 4;
//            prompt(reviewNumber);
//
//        }
//    }

//    private void prompt(final int reviewNumber) {
//
//        final Dialog dialog = new Dialog(MainActivity.this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCancelable(false);
//        if(lightDark) {
//            dialog.setContentView(R.layout.review_dialog_light);
//        }else{
//            dialog.setContentView(R.layout.dialog_review);
//        }
//
//        Button positive = dialog.findViewById(R.id.positive);
//        Button negative = dialog.findViewById(R.id.negative);
//
//        positive.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                reviewOne = true;
//                reviewTwo = true;
//                reviewThree = true;
//                reviewFour = true;
//                db.updateReviewOne(reviewOne);
//                db.updateReviewTwo(reviewTwo);
//                db.updateReviewThree(reviewThree);
//                db.updateReviewFour(reviewFour);
//                //TODO find out if this needs to go into strings.xml
//                String url = "https://play.google.com/store/apps/details?id=com.violenthoboenterprises.taskkillernoexcuses";
//                Intent i = new Intent(Intent.ACTION_VIEW);
//                i.setData(Uri.parse(url));
//                startActivity(i);
//                dialog.dismiss();
//
//            }
//        });
//
//        negative.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                dialog.dismiss();
//                if(reviewNumber == 1) {
//                    reviewOne = true;
//                    db.updateReviewOne(reviewOne);
//                }else if (reviewNumber == 2){
//                    reviewTwo = true;
//                    db.updateReviewTwo(reviewTwo);
//                }else if(reviewNumber == 3){
//                    reviewThree = true;
//                    db.updateReviewThree(reviewThree);
//                }else if(reviewNumber == 4){
//                    reviewFour = true;
//                    db.updateReviewFour(reviewFour);
//                }
//
//            }
//        });
//
//        dialog.show();
//
//    }

    @Override
    public void onProductPurchased(@NonNull String productId,
                                   @Nullable TransactionDetails details) {

//        if(productId.equals("remove_ads")){
//
//            db.updateAdsRemoved(true);
//            adsRemoved = true;
//            purchasesShowing = false;
//            colorPickerShowing();
//            if(colorCyclingAllowed && remindersAvailable && adsRemoved){
//                proBtn.setVisible(false);
//            }
//            removeAdsImg.setVisibility(View.GONE);
//            removeAdsPurchasedImg.setVisibility(View.VISIBLE);
//
//        }else if(productId.equals("get_reminders")){
//
//            db.updateRemindersAvailable(true);
//            remindersAvailable = true;
//            purchasesShowing = false;
//            colorPickerShowing();
//            if(colorCyclingAllowed && remindersAvailable && adsRemoved){
//                proBtn.setVisible(false);
//            }
//            remindersImg.setVisibility(View.GONE);
//            remindersPurchasedImg.setVisibility(View.VISIBLE);
//
//        }else if(productId.equals("cycle_colors")){
//
//            toast.setText(R.string.turnColorCyclingOnOff);
//            final Handler handler = new Handler();
//
//            final Runnable runnable = new Runnable() {
//                public void run() {
//
//                    if(!mute) {
//                        sweep.start();
//                    }
//
//                    toastView.startAnimation(AnimationUtils.loadAnimation
//                            (MainActivity.this, R.anim.enter_from_right_fast));
//                    toastView.setVisibility(View.VISIBLE);
//                    final Handler handler2 = new Handler();
//                    final Runnable runnable2 = new Runnable() {
//                        public void run() {
//                            toastView.startAnimation(AnimationUtils.loadAnimation
//                                    (MainActivity.this, android.R.anim.fade_out));
//                            toastView.setVisibility(View.GONE);
//                        }
//                    };
//                    handler2.postDelayed(runnable2, 3500);
//                }
//            };
//
//            handler.postDelayed(runnable, 500);
//
//            db.updateCycleColors(true);
//            colorCyclingAllowed = true;
//            purchasesShowing = false;
//            colorPickerShowing();
//            if(colorCyclingAllowed && remindersAvailable && adsRemoved){
//                proBtn.setVisible(false);
//            }
//            autoColorImg.setVisibility(View.GONE);
//            autoColorPurchasedImg.setVisibility(View.VISIBLE);
//
//        }else if(productId.equals("unlock_all")){
//
//            toast.setText(R.string.turnColorCyclingOnOff);
//            final Handler handler = new Handler();
//
//            final Runnable runnable = new Runnable() {
//                public void run() {
//
//                    if(!mute) {
//                        sweep.start();
//                    }
//
//                    toastView.startAnimation(AnimationUtils.loadAnimation
//                            (MainActivity.this, R.anim.enter_from_right_fast));
//                    toastView.setVisibility(View.VISIBLE);
//                    final Handler handler2 = new Handler();
//                    final Runnable runnable2 = new Runnable() {
//                        public void run() {
//                            toastView.startAnimation(AnimationUtils.loadAnimation
//                                    (MainActivity.this, android.R.anim.fade_out));
//                            toastView.setVisibility(View.GONE);
//                        }
//                    };
//                    handler2.postDelayed(runnable2, 3500);
//                }
//            };
//
//            handler.postDelayed(runnable, 500);
//
//            db.updateAdsRemoved(true);
//            db.updateRemindersAvailable(true);
//            db.updateCycleColors(true);
//            adsRemoved = true;
//            remindersAvailable = true;
//            colorCyclingAllowed = true;
//            purchasesShowing = false;
//            colorPickerShowing();
//            proBtn.setVisible(false);
//            unlockAllImg.setVisibility(View.GONE);
//            unlockAllPurchasedImg.setVisibility(View.VISIBLE);
//            autoColorImg.setVisibility(View.GONE);
//            autoColorPurchasedImg.setVisibility(View.VISIBLE);
//            remindersImg.setVisibility(View.GONE);
//            remindersPurchasedImg.setVisibility(View.VISIBLE);
//            removeAdsImg.setVisibility(View.GONE);
//            removeAdsPurchasedImg.setVisibility(View.VISIBLE);
//
//        }
        //Inform user of successful purchase
        if (productId.equals(StringConstants.UNLOCK_ALL)) {

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
                handler2.postDelayed(runnable2, 2000);
            };

            handler.postDelayed(runnable, 500);

            //Update values so that app appears to be in 'pro mode'

            boolAdsRemoved = true;
            preferences.edit().putBoolean(StringConstants.ADS_REMOVED_KEY, true).apply();
            boolRemindersAvailable = true;
            preferences.edit().putBoolean(StringConstants.REMINDERS_AVAILABLE_KEY, true).apply();
            miPro.setVisible(false);
            imgBanner.setVisibility(View.GONE);

        }

    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, @Nullable Throwable error) {

        Toast.makeText(MainActivity.this, R.string.somethingWentWrong, Toast.LENGTH_LONG).show();

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

//    public void removeAds(View view) {
//
//        if(!adsRemoved) {
//            vibrate.vibrate(50);
//
//            if (!mute) {
//                chime.start();
//            }
//
//            billingProcessor.purchase(this, "remove_ads");
//
//        }
//
//    }

//    public void getReminders(View view) {
//
//        if(!remindersAvailable) {
//            vibrate.vibrate(50);
//
//            if (!mute) {
//                chime.start();
//            }
//
//            bp.purchase(this, "get_reminders");
//
//        }
//
//    }

//    public void cycleColors(View view) {
//
//        if(!colorCyclingAllowed) {
//            vibrate.vibrate(50);
//
//            if (!mute) {
//                chime.start();
//            }
//
//            bp.purchase(this, "cycle_colors");
//
//        }
//
//    }

//    public void unlockAll(View view) {
//
//        if(!colorCyclingAllowed && !remindersAvailable && !adsRemoved) {
//
//            vibrate.vibrate(50);
//
//            if (!mute) {
//                chime.start();
//            }
//
//            bp.purchase(this, "unlock_all");
//
//        }
//
//    }

//    @Override
//    protected void onPause(){
//
//        super.onPause();
//
//        //TODO check if this line is needed
//        sortedIdsForNote = sortedIDs;
//
//    }

    @Override
    protected void onResume() {

        super.onResume();

//        taskBeingEdited = false;
//        removeTaskProperties();
//
//            getSavedData();

        lngTimeColorLastChanged = preferences.getLong(StringConstants.TIME_COLOR_LAST_CHANGED, lngTimeInstalled);

        if(boolColorCyclingAllowed && boolColorCyclingEnabled) {
            Calendar cal = Calendar.getInstance();
            if((cal.getTimeInMillis() / 1000 / 60 / 60) >= (lngTimeColorLastChanged + 4)) {
                switchColor();
            }
        }

//        checkLightDark();

        showPrompt();

        adapter.notifyItemChanged(preferences.getInt(StringConstants.REFRESH_THIS_ITEM, 0));
        toggleFab(true);

//        if (boolDueInPast) {
//            showDueInPastToast();
//            boolDueInPast = false;
//        }

    }

//    private void getSavedData(){
//
//        //clearing the lists before adding data back into them so as to avoid duplication
//        taskList.clear();
//        sortedIDs.clear();
//
//        int taskLastChanged = 0;
//
//        int taskListSize = db.getTotalRows();
//
//        //getting app-wide data
//        Cursor dbResult = db.getUniversalData();
//        while (dbResult.moveToNext()) {
//            mute = dbResult.getInt(1) > 0;
//            highlight = dbResult.getString(2);
//            lightDark = dbResult.getInt(3) > 0;
//            adsRemoved = dbResult.getInt(5) > 0;
//            remindersAvailable = dbResult.getInt(6) > 0;
//            colorCyclingAllowed = dbResult.getInt(7) > 0;
//            taskLastChanged = dbResult.getInt(16);
//            colorCyclingEnabled = dbResult.getInt(18) > 0;
//            duesSet = dbResult.getInt(19);
//            showMotivation = dbResult.getInt(20) > 0;
//            repeatHint = dbResult.getInt(21);
//            renameHint = dbResult.getInt(22);
//            reinstateHint = dbResult.getInt(23);
//            launchTime = dbResult.getInt(24);
//            reviewOne = dbResult.getInt(25) > 0;
//            reviewTwo = dbResult.getInt(26) > 0;
//            reviewThree = dbResult.getInt(27) > 0;
//            reviewFour = dbResult.getInt(28) > 0;
//        }
//        dbResult.close();
//

//
//        checkLightDark(lightDark);
//
//        ArrayList<Integer> tempSortedIDs = new ArrayList<>();
//
//        ArrayList<Integer> IDList = db.getIDs();
//
//        for( int i = 0 ; i < taskListSize ; i++ ) {
//
//            Cursor sortedIdsResult = db.getData(IDList.get(i));
//            while (sortedIdsResult.moveToNext()) {
//                tempSortedIDs.add(sortedIdsResult.getInt(16));
//            }
//            sortedIdsResult.close();
//
//        }
//
//        Collections.sort(tempSortedIDs);
//
//        for(int i = 0; i < taskListSize; i ++){
//
//            sortedIDs.add(String.valueOf(tempSortedIDs.get(i)));
//
//        }
//
//        for(int i = 0; i < taskListSize; i++){
//            Cursor sharedPreferencesResult = db.getData(IDList.get(i));
//            while (sharedPreferencesResult.moveToNext()) {
//                taskList.add(sharedPreferencesResult.getString(4));
//            }
//            sharedPreferencesResult.close();
//        }
//
//        new Reorder();
//
//        theListView.post(new Runnable() {
//            @Override
//            public void run() {
//                theListView.setSelection(activeTask);
//            }
//        });
//
//        alertIntent = new Intent(this, AlertReceiver.class);
//
//        //Checks to see if there are still tasks left
//        noTasksLeft();
//
//        showPrompt(launchTime);
//
//        if(adsRemoved){
//            removeAdsImg.setVisibility(View.GONE);
//            removeAdsPurchasedImg.setVisibility(View.VISIBLE);
//        }
//        if(remindersAvailable){
//            remindersImg.setVisibility(View.GONE);
//            remindersPurchasedImg.setVisibility(View.VISIBLE);
//        }
//        if(colorCyclingAllowed){
//            autoColorImg.setVisibility(View.GONE);
//            autoColorPurchasedImg.setVisibility(View.VISIBLE);
//        }
//        if(adsRemoved || remindersAvailable || colorCyclingAllowed){
//            unlockAllImg.setVisibility(View.GONE);
//            unlockAllPurchasedImg.setVisibility(View.VISIBLE);
//        }
//
//        //Setting the position for the toast
//        toastParams.setMargins(15, (int) (deviceheight / 1.35), 0, 0);
//
//    }

    @Override
    //Return to previous selection when back is pressed
    public void onBackPressed() {

//        //options to properties
//        if(colorPickerShowing) {
//            colorPickerShowing();
//        }else if (purchasesShowing){
//            colorPickerShowing();
//        }else if(taskOptionsShowing){
//            taskOptionsShowing = false;
//        //Properties to home
//        }else if (taskPropertiesShowing){
//            exitTaskProperties = true;
//            removeTaskProperties();
//        //Exit app
//        }else{
//            super.onBackPressed();
//        }

        //If task properties are showing then the back button should close them
        if (boolPropertiesShowing) {
            adapter.notifyItemChanged(preferences.getInt(StringConstants.REFRESH_THIS_ITEM, 0));
            boolPropertiesShowing = false;
        } else {
            super.onBackPressed();
        }

    }

}