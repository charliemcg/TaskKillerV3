package com.violenthoboenterprises.taskkillernoexcuses.activities;

import android.app.job.JobScheduler;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
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

    //The recycler view
    RecyclerView recyclerView;

    //The diver that shows up between recycler view items
    DividerItemDecoration dividerItemDecoration;

//    ActivitySubtasksBinding binding;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subtasks);
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_subtasks);

        //binding the highlight color to attributes in layout file
//        binding.setHighlightColor(Color.parseColor(preferences.getString(StringConstants.HIGHLIGHT_COLOR_KEY, "#ff34ff00")));

        tbSubtasks = findViewById(R.id.tbSubtasks);

        //Getting the parent task to which the subtasks are related
        //The parent task to which the subtasks belong
        Task task = (Task) getIntent().getSerializableExtra("task");
        tbSubtasks.setSubtitle(task.getTask());
        subtaskViewModel = ViewModelProviders.of(this).get(SubtaskViewModel.class);
        subtasksPresenter = new SubtasksPresenterImpl(subtaskViewModel, task);

        keyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        etSubtask = findViewById(R.id.etSubtask);
        etSubtask.setBackgroundColor(Color.parseColor(strHighlightColor));

        boolSubtasksKeyboardShowing = false;
        subtasksRootView = findViewById(R.id.subtasksRoot);
        checkKeyboardShowing();

        //Setting up the recycler view
        recyclerView = findViewById(R.id.subTasksRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
//                getResources().getConfiguration().orientation);
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
        tbSubtasks.setTitle(R.string.subTasks);

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
            dividerItemDecoration.setDrawable(ContextCompat.getDrawable(recyclerView.getContext(), R.drawable.item_decoration_subtasks));
        }else{
            tbSubtasks.setTitleTextColor(getResources().getColor(R.color.black));
            tbSubtasks.setSubtitleTextColor(getResources().getColor(R.color.black));
            subtasksRootView.setBackgroundColor(getResources().getColor(R.color.white));
            dividerItemDecoration.setDrawable(ContextCompat.getDrawable(recyclerView.getContext(), R.drawable.item_decoration_subtasks));
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

    @Override
    protected void onResume() {

        super.onResume();

        etSubtask.setText("");

    }

}

