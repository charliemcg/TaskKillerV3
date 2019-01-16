package com.violenthoboenterprises.taskkillernoexcuses.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.violenthoboenterprises.taskkillernoexcuses.R;
//import com.violenthoboenterprises.taskkillernoexcuses.databinding.ActivityNoteBinding;
import com.violenthoboenterprises.taskkillernoexcuses.model.NotePresenterImpl;
import com.violenthoboenterprises.taskkillernoexcuses.model.Task;
import com.violenthoboenterprises.taskkillernoexcuses.model.TaskViewModel;
import com.violenthoboenterprises.taskkillernoexcuses.presenter.NotePresenter;
import com.violenthoboenterprises.taskkillernoexcuses.utils.StringConstants;

public class NoteActivity extends MainActivity {

    private final String TAG = this.getClass().getSimpleName();
    private TextView tvNote;
    private EditText etNote;
    private InputMethodManager keyboard;
    private ImageView btnSubmitNote, btnSubmitNoteOne, btnSubmitNoteTwo, btnSubmitNoteThree, btnSubmitNoteFour;
    private Toolbar tbNote;
    private MenuItem itemTrashNote, itemTrashNoteOpen;
    private NotePresenter notePresenter;
    private View noteRoot;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        tbNote = findViewById(R.id.tbNote);
        setSupportActionBar(tbNote);

        //getting the task to which this note is related
        //The parent task to which the note belongs
        Task task = (Task) getIntent().getSerializableExtra("task");
        TaskViewModel taskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
        notePresenter = new NotePresenterImpl(taskViewModel, task);

        tvNote = findViewById(R.id.tvNote);
        etNote = findViewById(R.id.etNote);
        keyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        btnSubmitNote = findViewById(R.id.btnSubmitNote);
        btnSubmitNoteOne = findViewById(R.id.btnSubmitNoteOne);
        btnSubmitNoteFour = findViewById(R.id.btnSubmitNoteTwo);
        btnSubmitNoteTwo = findViewById(R.id.btnSubmitNoteThree);
        btnSubmitNoteThree = findViewById(R.id.btnSubmitNoteFour);
        noteRoot = findViewById(R.id.noteRoot);

        getSupportActionBar().setTitle(R.string.note);
        tbNote.setSubtitle(notePresenter.getTaskName());

        etNote.setBackgroundColor(Color.parseColor(preferences.getString(StringConstants.HIGHLIGHT_COLOR_KEY, "#ff34ff00")));

        tvNote.setMovementMethod(new ScrollingMovementMethod());

        String theNote = notePresenter.getNote();

        //Display note if there is one
        if (theNote != null) {

            tvNote.setText(theNote);
            this.getWindow().setSoftInputMode(WindowManager
                    .LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            etNote.setVisibility(View.GONE);
            btnSubmitNote.setVisibility(View.GONE);

        }

        onCreateOptionsMenu(tbNote.getMenu());

        //keyboard will display the default edit text instead of the custom one without this line
        etNote.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);

        keyboard.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        //Actions to occur when user clicks submit
        btnSubmitNote.setOnClickListener(v -> {

            //Keyboard is inactive without this line
            etNote.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);

            animateSubmitButton();

            //new note being added
            String newNote = etNote.getText().toString();
            if (!newNote.equals("")) {
                notePresenter.update(newNote);

                //display new note in the view
                tvNote.setText(newNote);

            }

        });

        //Long click allows editing of text
        tvNote.setOnLongClickListener(view -> {

            MainActivity.vibrate.vibrate(50);

            itemTrashNote.setVisible(false);

            //show edit text
            etNote.setVisibility(View.VISIBLE);

            //show submit button
            btnSubmitNote.setVisibility(View.VISIBLE);

            //Focus on edit text so that keyboard does not cover it up
            etNote.requestFocus();

            //show keyboard
            keyboard.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

            //set text to existing note
            etNote.setText(notePresenter.getNote());

            //put cursor at end of text
            etNote.setSelection(etNote.getText().length());

            tvNote.setText("");

            return true;
        });

        checkDarkLight();

    }

    private void checkDarkLight() {
        if (MainActivity.boolDarkModeEnabled) {
            noteRoot.setBackgroundColor(getResources().getColor(R.color.dark_gray));
            tvNote.setTextColor(getResources().getColor(R.color.gray));
            tbNote.setTitleTextColor(getResources().getColor(R.color.gray));
            tbNote.setSubtitleTextColor(getResources().getColor(R.color.gray));
        } else {
            noteRoot.setBackgroundColor(getResources().getColor(R.color.white));
            tvNote.setTextColor(getResources().getColor(R.color.black));
            tbNote.setTitleTextColor(getResources().getColor(R.color.black));
            tbNote.setSubtitleTextColor(getResources().getColor(R.color.black));
        }
    }

    //Submit button collapses on itself on click
    private void animateSubmitButton() {
        //Animating the submit icon
        final Handler handler = new Handler();

        final Runnable runnable = () -> {

            btnSubmitNote.setVisibility(View.GONE);
            btnSubmitNoteOne.setVisibility(View.VISIBLE);

            final Handler handler2 = new Handler();

            final Runnable runnable2 = () -> {

                btnSubmitNoteOne.setVisibility(View.GONE);
                btnSubmitNoteTwo.setVisibility(View.VISIBLE);

                final Handler handler3 = new Handler();

                final Runnable runnable3 = () -> {

                    btnSubmitNoteTwo.setVisibility(View.GONE);
                    btnSubmitNoteFour.setVisibility(View.VISIBLE);

                    final Handler handler4 = new Handler();

                    final Runnable runnable4 = () -> {

                        btnSubmitNoteFour.setVisibility(View.GONE);
                        btnSubmitNoteThree.setVisibility(View.VISIBLE);

                        final Handler handler5 = new Handler();

                        final Runnable runnable5 = () -> {

                            vibrate.vibrate(50);

                            if (!boolMute) {
                                mpBlip.start();
                            }

                            btnSubmitNoteThree.setVisibility(View.GONE);

                            if (!etNote.getText().toString().equals("")) {
                                //Hide text box
                                etNote.setVisibility(View.GONE);

                                btnSubmitNote.setVisibility(View.GONE);

                                itemTrashNote.setVisible(true);

                                //Hide keyboard
                                keyboard.toggleSoftInput(InputMethodManager
                                        .HIDE_IMPLICIT_ONLY, 0);

                                //clear the edit text
                                etNote.setText("");

                            } else {
                                btnSubmitNote.setVisibility(View.VISIBLE);
                            }

                        };
                        handler5.postDelayed(runnable5, 50);
                    };
                    handler4.postDelayed(runnable4, 50);
                };
                handler3.postDelayed(runnable3, 50);
            };
            handler2.postDelayed(runnable2, 50);
        };
        handler.postDelayed(runnable, 50);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!menu.hasVisibleItems()) {
            getMenuInflater().inflate(R.menu.menu_note, tbNote.getMenu());
            itemTrashNote = this.tbNote.getMenu().findItem(R.id.itemTrashNote);
            itemTrashNoteOpen = this.tbNote.getMenu().findItem(R.id.itemTrashNoteOpen);
            if (tvNote.getText().toString().equals("")) {
                itemTrashNote.setVisible(false);
            } else {
                itemTrashNote.setVisible(true);
            }
            return true;
        } else {
            itemTrashNote.setEnabled(true);
            return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //Removing the note
        if (id == R.id.itemTrashNote) {

            //animating the trash can
            final Handler handler = new Handler();

            final Runnable runnable = () -> {

                itemTrashNote.setVisible(false);
                itemTrashNoteOpen.setVisible(true);

                vibrate.vibrate(50);

                if (!boolMute) {
                    mpTrash.start();
                }

                final Handler handler2 = new Handler();
                final Runnable runnable2 = () -> {

                    itemTrashNote.setVisible(true);
                    itemTrashNoteOpen.setVisible(false);
                    final Handler handler3 = new Handler();
                    final Runnable runnable3 = () -> {

                        itemTrashNote.setVisible(false);

                        notePresenter.update(null);

                        tvNote.setText("");

                        //show edit text
                        etNote.setVisibility(View.VISIBLE);
                        btnSubmitNote.setVisibility(View.VISIBLE);
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

}
