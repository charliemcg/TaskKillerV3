package com.violenthoboenterprises.taskkillernoexcuses.activities;

import android.app.job.JobScheduler;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.violenthoboenterprises.taskkillernoexcuses.R;
import com.violenthoboenterprises.taskkillernoexcuses.databinding.ActivitySubtasksBinding;
import com.violenthoboenterprises.taskkillernoexcuses.model.Subtask;
import com.violenthoboenterprises.taskkillernoexcuses.model.SubtaskViewModel;
import com.violenthoboenterprises.taskkillernoexcuses.model.SubtasksAdapter;
import com.violenthoboenterprises.taskkillernoexcuses.model.SubtasksPresenterImpl;
import com.violenthoboenterprises.taskkillernoexcuses.model.Task;
import com.violenthoboenterprises.taskkillernoexcuses.presenter.SubtasksPresenter;
import com.violenthoboenterprises.taskkillernoexcuses.utils.StringConstants;
import com.violenthoboenterprises.taskkillernoexcuses.view.SubtasksView;

import java.util.Calendar;
import java.util.List;

public class SubtasksActivity extends MainActivity implements SubtasksView {

//    static InputMethodManager keyboard;
//    static EditText checklistEditText;
//    public ListAdapter[] checklistAdapter;
//    static ListView checklistView;
//    static ArrayList<String> checklist;
//    static ArrayList<Boolean> subTasksKilled;
//    static ArrayList<Integer> sortedSubtaskIds;
//    static boolean subTaskBeingEdited;
//    static boolean goToChecklistAdapter;
//    private boolean restoreListView;
//    static boolean subTasksClickable;
//    static boolean fadeSubTasks;
//    static boolean noteExists;
//    static View checklistRootView;
//    static int renameMe;
//    private static String TAG;
//    private Toolbar subTasksToolbar;
//    RelativeLayout.LayoutParams listParams;
//    RelativeLayout.LayoutParams editTextParams;
//    FrameLayout.LayoutParams rootParams;
//    RelativeLayout.LayoutParams toolbarParams;

    private String TAG = this.getClass().getSimpleName();
    private static InputMethodManager keyboard;
    private EditText etSubtask;
    //The subtask being renamed
    private Subtask subTaskBeingEdited;
    private SubtaskViewModel subtaskViewModel;
    private SubtasksPresenter subtasksPresenter;
    public static boolean boolSubtasksKeyboardShowing;
    private View subtasksRootView;
    private Toolbar tbSubtasks;

    ActivitySubtasksBinding binding;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_subtasks);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_subtasks);
        Toolbar subTasksToolbar = findViewById(R.id.tbSubtasks);

        //binding the highlight color to attributes in layout file
        binding.setHighlightColor(Color.parseColor(preferences.getString(StringConstants.HIGHLIGHT_COLOR_KEY, "#ff34ff00")));

//        keyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        checklistEditText = findViewById(R.id.checklistEditText);
//        checklistView = findViewById(R.id.theChecklist);
//        checklist = new ArrayList<>();
//        subTasksKilled = new ArrayList<>();
//        sortedSubtaskIds = new ArrayList<>();
//        subTaskBeingEdited = false;
//        subTasksClickable = false;
//        checklistRootView = findViewById(R.id.checklistRoot);
//        TAG = "SubtasksActivity";
//        fadeSubTasks = false;
//        noteExists = false;
//        inChecklist = true;
//        renameMe = 0;
//        listParams = (RelativeLayout.LayoutParams) checklistView.getLayoutParams();
//        editTextParams = (RelativeLayout.LayoutParams) checklistEditText.getLayoutParams();
//        rootParams = (FrameLayout.LayoutParams) checklistRootView.getLayoutParams();
//        toolbarParams = (RelativeLayout.LayoutParams) subTasksToolbar.getLayoutParams();
//
//        String dbTaskId = "";
//        Boolean dbLightDark = false;
//
//        int dbID = 0;
//        String dbTask = "";
//
//        //getting app-wide data
//        Cursor dbResult = MainActivity.db.getUniversalData();
//        while (dbResult.moveToNext()) {
//            dbTaskId = dbResult.getString(4);
//            dbLightDark = dbResult.getInt(3) > 0;
//        }
//
//        //getting subtask data
//        dbResult = MainActivity.db.getData(Integer.parseInt(dbTaskId));
//        while (dbResult.moveToNext()) {
//            dbID = dbResult.getInt(0);
//            dbTask = dbResult.getString(4);
//        }
//        dbResult.close();
//
//        final String finalDbTaskId = dbTaskId;
//        final int finalDbID = dbID;
//
//        //setting highlight color
//        checklistEditText.setBackgroundColor(Color.parseColor(MainActivity.highlight));
//
//        //setting title to task name
//        subTasksToolbar.setTitle(R.string.subTasks);
//        subTasksToolbar.setSubtitle(dbTask);
//
//        //setting correct background color
//        if(!dbLightDark){
//            subTasksToolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
//            subTasksToolbar.setSubtitleTextColor(Color.parseColor("#AAAAAA"));
//            checklistView.setBackgroundColor(Color.parseColor("#333333"));
//            subTasksToolbar.setBackgroundColor(Color.parseColor("#333333"));
//            int[] colors = {0, Integer.parseInt(highlightDec), Integer.parseInt(highlightDec)};
//            checklistView.setDivider(new GradientDrawable(
//                    GradientDrawable.Orientation.LEFT_RIGHT, colors));
//            checklistView.setDividerHeight(1);
//        }else{
//            subTasksToolbar.setTitleTextColor(Color.parseColor("#000000"));
//            subTasksToolbar.setSubtitleTextColor(Color.parseColor("#666666"));
//            checklistView.setBackgroundColor(Color.parseColor("#FFFFFF"));
//            subTasksToolbar.setBackgroundColor(Color.parseColor("#FFFFFF"));
//            int[] colors = {Color.parseColor("#FFFFFF"), Integer.parseInt(highlightDec),
//                    Integer.parseInt(highlightDec)};
//            checklistView.setDivider(new GradientDrawable(
//                    GradientDrawable.Orientation.LEFT_RIGHT, colors));
//            checklistView.setDividerHeight(1);
//        }
//
//        //actions to occur when user clicks list item
//        checklistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//
//                //Getting id of selected item
//                boolean isKilled = false;
//                Cursor dbResult = db.getSubtaskData(finalDbID,
//                        sortedSubtaskIds.get(position));
//                while(dbResult.moveToNext()){
//                    isKilled = dbResult.getInt(3) > 0;
//                }
//                dbResult.close();
//
//                //removes completed task from view
//                if(isKilled && subTasksClickable){
//
//                    vibrate.vibrate(50);
//
//                    checklist.remove(position);
//
//                    subTasksKilled.remove(position);
//
//                    MainActivity.db.deleteSubtaskData(String.valueOf(finalDbID),
//                            String.valueOf(sortedSubtaskIds.get(position)));
//
//                    db.updateChecklistSize(finalDbTaskId, checklist.size());
//
//                    sortedSubtaskIds.remove(position);
//
//                    checklistView.setAdapter(checklistAdapter[0]);
//
//                }
//
//            }
//
//        });
//
//        //Actions to occur on long click of a list item
//        checklistView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view,
//                                           int position, long id) {
//
//                boolean isKilled = false;
//                Cursor dbResult = db.getSubtaskData(finalDbID,
//                        sortedSubtaskIds.get(position));
//                while(dbResult.moveToNext()){
//                    isKilled = dbResult.getInt(3) > 0;
//                }
//                dbResult.close();
//
//                //Rename subtask
//                if(subTasksClickable && !isKilled){
//
//                    keyboard.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
//
//                    goToChecklistAdapter = false;
//
//                    fadeSubTasks = true;
//
//                    //Indicates that a task is being edited
//                    subTaskBeingEdited = true;
//
//                    renameMe = position;
//
//                    checklistView.setAdapter(checklistAdapter[0]);
//
//                //Reinstate killed subtask
//                }else if(subTasksClickable && isKilled){
//
//                    //marks task as not killed in database
//                    db.updateSubtaskKilled(finalDbTaskId, String.valueOf
//                            (sortedSubtaskIds.get(position)), false);
//
//                    subTasksKilled.set(position, false);
//
//                    checklistView.setAdapter(checklistAdapter[0]);
//
//                }
//
//                return true;
//
//            }
//
//        });
//
//        //Actions to occur when keyboard's 'Done' button is pressed
//        checklistEditText.setOnEditorActionListener(new TextView.OnEditorActionListener(){
//
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//
//                //Keyboard is inactive without this line
//                checklistEditText.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
//
//                //Actions to occur when user submits new sub task
//                if (actionId == EditorInfo.IME_ACTION_DONE && !subTaskBeingEdited) {
//
//                    //Getting user data
//                    String checklistTaskName = checklistEditText.getText().toString();
//
//                    //Clear text from text box
//                    checklistEditText.setText("");
//
//                    //Don't allow blank tasks
//                    if(!checklistTaskName.equals("")) {
//
//                        vibrate.vibrate(50);
//
//                        if(!mute) {
//                            blip.start();
//                        }
//
//                        //adding data to arraylists
//                        checklist.add(checklistTaskName);
//                        subTasksKilled.add(false);
//
//                        //updating list size in database
//                        db.updateChecklistSize(String.valueOf(finalDbID), checklist.size());
//                        //getting unique subtask ID
//                        int subtaskId = 0;
//                        boolean idIsSet = false;
//                        while (!idIsSet) {
//                            Cursor dbIdResult = db.getSubtask(finalDbID);
//                            while (dbIdResult.moveToNext()) {
//                                if (dbIdResult.getInt(1) == subtaskId) {
//                                    subtaskId++;
//                                } else {
//                                    idIsSet = true;
//                                }
//                            }
//                            if (subtaskId == 0) {
//                                idIsSet = true;
//                            }
//                            dbIdResult.close();
//                        }
//
//                        Calendar timeNow = new GregorianCalendar();
//
//                        //saving subtask in database
//                        db.insertSubtaskData(finalDbID, subtaskId, checklistTaskName,
//                                String.valueOf(timeNow.getTimeInMillis() / 1000));
//
//                        //adding new ID to sortedIDs
//                        sortedSubtaskIds.add(subtaskId);
//
//                        //Reordering the list based on time subtask was created
//                        reorderList(finalDbTaskId);
//
//                    }
//
//                    return true;
//
//                //Actions to occur when editing sub tasks
//                }else if(actionId == EditorInfo.IME_ACTION_DONE && subTaskBeingEdited){
//
//                    vibrate.vibrate(50);
//
//                    //Hide keyboard
//                    keyboard.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY,0);
//
//                    //Getting user data
//                    String checklistTaskName = checklistEditText.getText().toString();
//
//                    //Clear text from text box
//                    checklistEditText.setText("");
//
//                    //Don't allow blank tasks
//                    if(!checklistTaskName.equals("")) {
//
//                        //updating arraylists
//                        checklist.set(renameMe, checklistTaskName);
//                        subTasksKilled.set(renameMe, true);
//
//                        //updating database
//                        db.updateSubtask(finalDbTaskId, String.valueOf
//                                (sortedSubtaskIds.get(renameMe)), checklistTaskName);
//                        checklistView.setAdapter(checklistAdapter[0]);
//
//                    }
//
//                    return true;
//
//                }
//
//                checklistView.setAdapter(checklistAdapter[0]);
//
//                //Marking editing as complete
//                subTaskBeingEdited = false;
//
//                return true;
//
//            }
//
//        });

        tbSubtasks = findViewById(R.id.tbSubtasks);

        //Getting the parent task to which the subtasks are related
        //The parent task to which the subtasks belong
        Task task = (Task) getIntent().getSerializableExtra("task");
        subTasksToolbar.setSubtitle(task.getTask());
        subtaskViewModel = ViewModelProviders.of(this).get(SubtaskViewModel.class);
        subtasksPresenter = new SubtasksPresenterImpl(subtaskViewModel, task);

        keyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        etSubtask = findViewById(R.id.etSubtask);

        boolSubtasksKeyboardShowing = false;
        subtasksRootView = findViewById(R.id.subtasksRoot);
        checkKeyboardShowing();

        //Setting up the recycler view
        RecyclerView recyclerView = findViewById(R.id.subTasksRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                getResources().getConfiguration().orientation);
        recyclerView.addItemDecoration(dividerItemDecoration);

        //setting up the adapter
        final SubtasksAdapter subtasksAdapter = new SubtasksAdapter(this);
        recyclerView.setAdapter(subtasksAdapter);

        //observing the recycler view items for changes
        subtaskViewModel = ViewModelProviders.of(this).get(SubtaskViewModel.class);
        //need to specifically get subtasks belonging to parent task
        subtaskViewModel.getAllSubtasks(subtasksPresenter.getId())
                .observe(this, subtasksAdapter::setSubtasks);

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
                //Saving a temporary instance of the deleted subtask should it need to be reinstated
                Subtask subtaskToReinstate = subtasksAdapter.getSubtaskAt(viewHolder.getAdapterPosition());
                subtaskViewModel.delete(subtasksAdapter.getSubtaskAt(viewHolder.getAdapterPosition()));
                String stringSnack = "Subtask deleted";
                showSnackbar(stringSnack, subtaskToReinstate);
                etSubtask.setText("");
            }
        }).attachToRecyclerView(recyclerView);

        //setting text in toolbar
        subTasksToolbar.setTitle(R.string.subTasks);

        //Actions to occur when keyboard's 'Done' button is pressed
        etSubtask.setOnEditorActionListener((v, actionId, event) -> {

            //Keyboard is inactive without this line
            etSubtask.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);

            //Actions to occur when user submits new sub task
            if (actionId == EditorInfo.IME_ACTION_DONE && subTaskBeingEdited == null) {

                //Getting user data
                String subtaskName = etSubtask.getText().toString();

                //Clear text from text box
                etSubtask.setText("");

                //Don't allow blank tasks
                if (!subtaskName.equals("")) {

                    vibrate.vibrate(50);

                    if (!boolMute) {
                        mpBlip.start();
                    }

                    subtasksPresenter.addSubtask(subtasksPresenter.getId(), subtaskName,
                            Calendar.getInstance().getTimeInMillis());

                }

                return true;

                //Actions to occur when editing sub tasks
            } else if (actionId == EditorInfo.IME_ACTION_DONE) {

                vibrate.vibrate(50);

                //Hide keyboard
                if(boolSubtasksKeyboardShowing) {
                    keyboard.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                    boolSubtasksKeyboardShowing = false;
                }

                //Getting user data
                String subtaskName = etSubtask.getText().toString();

                //Clear text from text box
                etSubtask.setText("");

                //Don't allow blank tasks
                if (!subtaskName.equals("")) {

                    subtasksPresenter.rename(subTaskBeingEdited, subtaskName);

                    subTaskBeingEdited = null;

                }

                return true;

            }

            return true;

        });

        //Show keyboard if there are no subtasks already
        List<Subtask> subtasks = subtasksPresenter.getSubtasksByParent(task.getId());
        int subtasksSize = subtasks.size();
        if(subtasksSize == 0) {
            etSubtask.requestFocus();
            boolSubtasksKeyboardShowing = true;
        }

        checkLightDark();

    }

    private void checkLightDark(){
        if(MainActivity.boolDarkModeEnabled){
            tbSubtasks.setTitleTextColor(getResources().getColor(R.color.gray));
            tbSubtasks.setSubtitleTextColor(getResources().getColor(R.color.gray));
            subtasksRootView.setBackgroundColor(getResources().getColor(R.color.dark_gray));
        }else{
            tbSubtasks.setTitleTextColor(getResources().getColor(R.color.black));
            tbSubtasks.setSubtitleTextColor(getResources().getColor(R.color.black));
            subtasksRootView.setBackgroundColor(getResources().getColor(R.color.white));
        }
    }

    private void showSnackbar(String stringSnack, final Subtask subtaskToReinstate) {
        View view = findViewById(R.id.subtasksRoot);
        Snackbar.make(view, stringSnack, Snackbar.LENGTH_SHORT)
                .setAction("UNDO", view1 -> {
                    JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
                    scheduler.cancel(StringConstants.DELETE_TASK_ID);
                    subtasksPresenter.reinstateSubTask(subtaskToReinstate);
                })
                .setActionTextColor(Color.parseColor(preferences.getString(StringConstants.HIGHLIGHT_COLOR_KEY, "#ff34ff00")))
                .show();
    }

    @Override
    public void editSubtask(Subtask currentSubtask) {

        Log.d(TAG, "keyboard showing: " + boolSubtasksKeyboardShowing);
        if(!boolSubtasksKeyboardShowing) {
            keyboard.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
            boolSubtasksKeyboardShowing = true;
        }

        //Indicates that a task is being edited
        subTaskBeingEdited = currentSubtask;

        //setting subtask name in the edit text
        etSubtask.setText(currentSubtask.getSubtask());

        //keeping a reference to the subtask which is being edited
        subTaskBeingEdited = currentSubtask;

        etSubtask.setSelection(etSubtask.getText().length());

    }

    //Actions to occur when keyboard is showing
    public void checkIfKeyboardShowing() {

//        subTasksClickable = true;
//
//        checklistRootView.getViewTreeObserver().addOnGlobalLayoutListener(
//                new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//
//                Rect screen = new Rect();
//
//                checklistRootView.getWindowVisibleDisplayFrame(screen);
//
//                if(screen.bottom != deviceheight){
//
//                    if (subTaskBeingEdited) {
//
//                        checklistRootView.setBackgroundColor(Color.parseColor("#888888"));
//
//                    }
//
//                    fadeSubTasks = true;
//
//                    if (goToChecklistAdapter) {
//
//                        if(checklist.size() != 0) {
//                            checklistView.setAdapter(checklistAdapter[0]);
//                        }
//
//                        goToChecklistAdapter = false;
//
//                    }
//
//                    subTasksClickable = false;
//
//                    //Keyboard is inactive without this line
//                    checklistEditText.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
//
//                    restoreListView = true;
//
//                }else if(restoreListView){
//
//                    checklistRootView.setBackgroundColor(Color.parseColor("#FFFFFF"));
//
//                    fadeSubTasks = false;
//
//                    subTasksClickable = true;
//
//                    checklistEditText.setText("");
//
//                    subTaskBeingEdited = false;
//
//                    if(checklist.size() != 0) {
//
//                        checklistView.setAdapter(checklistAdapter[0]);
//
//                    }
//
//                    restoreListView = false;
//
//                    goToChecklistAdapter = true;
//
//                }
//
//            }
//
//        });

        subtasksRootView.getViewTreeObserver().addOnGlobalLayoutListener
                (() -> {

                    Rect screen = new Rect();

                    subtasksRootView.getWindowVisibleDisplayFrame(screen);

                    if (screen.bottom != intDeviceheight) {

                        boolSubtasksKeyboardShowing = true;

                    } else {

                        boolSubtasksKeyboardShowing = false;

                    }

                });

    }

    //Ordering list by time created
//    public void reorderList(String parentID){
//
//        ArrayList<Integer> tempList = new ArrayList<>();
//
//        //Saving timestamps into a temporary array
//        for(int i = 0; i < checklist.size(); i++){
//
//            //getting timestamp
//            String dbTimestamp = "";
//            Cursor dbResult = MainActivity.db.getSubtaskData(Integer.parseInt(parentID),
//                    sortedSubtaskIds.get(i));
//            while (dbResult.moveToNext()) {
//                dbTimestamp = dbResult.getString(1);
//            }
//            dbResult.close();
//
//            tempList.add(Integer.valueOf(dbTimestamp));
//
//        }
//
//        //Ordering list by time task was created
//        ArrayList<String> whenTaskCreated = new ArrayList<>();
//        for(int i = 0; i < checklist.size(); i++){
//            String created = "";
//            Cursor createdResult = MainActivity.db.getSubtaskData(Integer.parseInt
//                    (parentID), sortedSubtaskIds.get(i));
//            while (createdResult.moveToNext()) {
//                created = createdResult.getString(4);
//            }
//            createdResult.close();
//            whenTaskCreated.add(created);
//        }
//        Collections.sort(whenTaskCreated);
//        Collections.reverse(whenTaskCreated);
//
//        ArrayList<String> tempIdsList = new ArrayList<>();
//        ArrayList<String> tempTaskList = new ArrayList<>();
//
//        //getting tasks
//        for(int i = 0; i < checklist.size(); i++){
//
//            //getting task data
//            int dbId = 0;
//            String dbTask = "";
//            Cursor dbResult = MainActivity.db.getSubtaskDataByTimestamp(
//                    whenTaskCreated.get(i));
//            while (dbResult.moveToNext()) {
//                dbId = dbResult.getInt(1);
//                dbTask = dbResult.getString(2);
//            }
//            dbResult.close();
//
//            tempIdsList.add(String.valueOf(dbId));
//            tempTaskList.add(dbTask);
//
//        }
//
//        //Setting the sorted ID in the database
//        for(int i = 0; i < checklist.size(); i++){
//
//            MainActivity.db.updateSubtaskSortedIndex(parentID, String.valueOf(i),
//                    Integer.parseInt(tempIdsList.get(i)));
//
//        }
//
//        sortedSubtaskIds.clear();
//
//        //setting the new sortedIds list
//        for(int i = 0; i < checklist.size(); i++){
//            sortedSubtaskIds.add(Integer.valueOf(tempIdsList.get(i)));
//        }
//
//        //setting the new task list in correct order
//        checklist = tempTaskList;
//
//    }

//    @Override
//    //Tasks are saved in a manner so that they don't vanish when app closed
//    protected void onPause(){
//
//        super.onPause();
//
//    }

    @Override
    protected void onResume() {

        super.onResume();

//        inChecklist = true;
//
//        getSavedData();

        etSubtask.setText("");

    }

//    //Existing tasks are recalled when app opened
//    private void getSavedData() {
//
//        //clearing lists to prevent duplicates
//        sortedSubtaskIds.clear();
//        checklist.clear();
//        subTasksKilled.clear();
//
//        goToChecklistAdapter = true;
//
//        checkIfKeyboardShowing();
//
//        //Keyboard is inactive without this line
//        checklistEditText.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
//
//        String id = null;
//        Cursor dbResult = db.getUniversalData();
//        while (dbResult.moveToNext()) {
//            id = dbResult.getString(4);
//        }
//        dbResult.close();
//
//        Cursor dbIdResult = db.getSubtask(Integer.parseInt(id));
//        while (dbIdResult.moveToNext()) {
//            //populating arraylists
//            sortedSubtaskIds.add(dbIdResult.getInt(1));
//            checklist.add(dbIdResult.getString(2));
//            subTasksKilled.add(dbIdResult.getInt(3) > 0);
//        }
//        dbIdResult.close();
//
//        //ordering the list by time subtask created
//        reorderList(id);
//
//        //if subtasks already exist keyboard isn't displayed and therefore tasks must be clickable
//        if(checklist.size() == 0) {
//            subTasksClickable = false;
//        }else{
//            subTasksClickable = true;
//        }
//
//        //Setting height of the list view
//        int statusHeight = 0;
//        int resourceId = getResources().getIdentifier("status_bar_height",
//                "dimen", "android");
//        if (resourceId > 0) {
//            statusHeight = getResources().getDimensionPixelSize(resourceId);
//        }
//        listParams.height = deviceheight - (editTextParams.height +
//                toolbarParams.height + statusHeight);
//        checklistView.setLayoutParams(listParams);
//
//    }
//
//    @Override
//    //Return to main screen when back pressed
//    public void onBackPressed() {
//
//        //prevents UI bugs
//        if(checklistShowing) {
//
//            Intent intent = new Intent();
//
//            intent.setClass(getApplicationContext(), MainActivity.class);
//
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//            startActivity(intent);
//
//            checklistShowing = false;
//
//        }
//
//        super.onBackPressed();
//
//    }

}

