package com.violenthoboenterprises.taskkillernoexcuses.model;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.violenthoboenterprises.taskkillernoexcuses.activities.MainActivity;
import com.violenthoboenterprises.taskkillernoexcuses.R;
import com.violenthoboenterprises.taskkillernoexcuses.activities.SubtasksActivity;
import com.violenthoboenterprises.taskkillernoexcuses.view.SubtasksView;

import java.util.ArrayList;
import java.util.List;

/*
 * Adding subtasks to the recycler view
 */
public class SubtasksAdapter extends RecyclerView.Adapter<SubtasksAdapter.SubtaskHolder> {
//
//    String TAG;
//
//    public SubtasksAdapter(Context context, ArrayList<String> values) {
//        super(context, R.layout.item_subtask, values);
//    }
//
//    @Override
//    public View getView(final int position, final View convertView, final ViewGroup parent) {
//
//        final String item = getItem(position);
//        final LayoutInflater theInflater = LayoutInflater.from(getContext());
//        final View checklistItemView = theInflater.inflate
//                (R.layout.item_subtask, parent, false);
//        TextView checklistTextView = checklistItemView.findViewById(R.id.checklistTextView);
//        final ImageView tick = checklistItemView.findViewById(R.id.subtaskComplete);
//        final ImageView ticked = checklistItemView.findViewById(R.id.subtaskCompleted);
//        final ImageView tickFaded = checklistItemView.findViewById(R.id.subtaskCompleteFaded);
//        final ImageView tickedFaded = checklistItemView.findViewById(R.id.subtaskCompletedFaded);
//        final ImageView tickWhite = checklistItemView.findViewById(R.id.subtaskCompleteWhite);
//        final ImageView tickedWhite = checklistItemView.findViewById(R.id.subtaskCompletedWhite);
//        final ImageView tickWhiteFaded = checklistItemView
//                .findViewById(R.id.subtaskCompleteWhiteFaded);
//        final ImageView tickedWhiteFaded = checklistItemView
//                .findViewById(R.id.subtaskCompletedWhiteFaded);
//        TAG = "SubtasksAdapter";
//
//        String dbTaskId = "";
//
//        //getting app-wide data
////        Cursor dbResult = MainActivity.db.getUniversalData();
////        while (dbResult.moveToNext()) {
////            dbTaskId = dbResult.getString(4);
////        }
//
//        final String finalDbTaskId = dbTaskId;
//
//        //setting up UI based on light or dark mode
////        if(!MainActivity.lightDark){
////            checklistItemView.setBackgroundColor(Color.parseColor("#333333"));
////            if(SubtasksActivity.fadeSubTasks){
////                checklistTextView.setTextColor(Color.parseColor("#666666"));
////                tickFaded.setVisibility(View.VISIBLE);
////                tickWhiteFaded.setVisibility(View.GONE);
////                tick.setVisibility(View.GONE);
////                tickWhite.setVisibility(View.GONE);
////            }else {
////                tickFaded.setVisibility(View.GONE);
////                tickWhiteFaded.setVisibility(View.GONE);
////                tick.setVisibility(View.VISIBLE);
////                tickWhite.setVisibility(View.GONE);
////            }
////        }else{
////            checklistItemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
////            checklistTextView.setTextColor(Color.parseColor("#000000"));
////            if(SubtasksActivity.fadeSubTasks){
////                checklistTextView.setTextColor(Color.parseColor("#DDDDDD"));
////                tickFaded.setVisibility(View.GONE);
////                tick.setVisibility(View.GONE);
////                tickWhite.setVisibility(View.GONE);
////                tickWhiteFaded.setVisibility(View.VISIBLE);
////            }else {
////                tickFaded.setVisibility(View.GONE);
////                tickWhiteFaded.setVisibility(View.GONE);
////                tick.setVisibility(View.GONE);
////                tickWhite.setVisibility(View.VISIBLE);
////            }
////        }
//
//        //registering that subtask should be marked as done
//        tick.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                markAsDone(position, finalDbTaskId);
//
//            }
//
//        });
//
//        //registering that subtask should be marked as done
//        tickWhite.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                markAsDone(position, finalDbTaskId);
//
//            }
//
//        });
//
//        checklistTextView.setText(item);
//
//        Boolean isKilled = false;
//
//        //finding out if the subtask is completed
////        Cursor dbIdResult = MainActivity.db.getSubtaskData(Integer.parseInt(dbTaskId),
////                SubtasksActivity.sortedSubtaskIds.get(position));
////        while (dbIdResult.moveToNext()) {
////            isKilled = dbIdResult.getInt(3) > 0;
////        }
////        dbIdResult.close();
//
//        //sub task is crossed out if it is marked as done
//        if(isKilled){
//
//            checklistTextView.setPaintFlags(checklistTextView.getPaintFlags() |
//                    Paint.STRIKE_THRU_TEXT_FLAG);
//
////            if(!MainActivity.lightDark){
////                checklistItemView.setBackgroundColor(Color.parseColor("#333333"));
////                if(SubtasksActivity.fadeSubTasks){
////                    tickedFaded.setVisibility(View.VISIBLE);
////                    tickFaded.setVisibility(View.GONE);
////                }else {
////                    ticked.setVisibility(View.VISIBLE);
////                    tick.setVisibility(View.GONE);
////                }
////            }else{
////                checklistItemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
////                if(SubtasksActivity.fadeSubTasks){
////                    tickWhiteFaded.setVisibility(View.GONE);
////                    tickedWhiteFaded.setVisibility(View.VISIBLE);
////                }else {
////                    tickWhite.setVisibility(View.GONE);
////                    tickedWhite.setVisibility(View.VISIBLE);
////                }
////            }
//
//        }
//
////        if(position == SubtasksActivity.renameMe && SubtasksActivity.subTaskBeingEdited){
////            checklistTextView.setText("");
////        }
//
//        //setting sub task name
////        if (SubtasksActivity.subTaskBeingEdited && (/*Integer.parseInt(dbTaskId)*/SubtasksActivity.renameMe ==
////                SubtasksActivity.sortedSubtaskIds.get(position)) &&
////                !SubtasksActivity.goToChecklistAdapter) {
////
////            String oldSubTaskString = SubtasksActivity.checklist.get(SubtasksActivity.renameMe);
////
////            SubtasksActivity.checklistEditText.setText(oldSubTaskString);
////
////            //Focus on edit text so that keyboard does not cover it up
////                    SubtasksActivity.checklistEditText.requestFocus();
////
////            SubtasksActivity.checklistEditText.setSelection(SubtasksActivity.checklistEditText
////                    .getText().length());
////
////        }
//
//        return checklistItemView;
//
//    }
//
//    private void markAsDone(int position, String finalDbTaskId) {
//
////        MainActivity.vibrate.vibrate(50);
//
////        if(!MainActivity.mute){
////            MainActivity.trash.start();
////        }
//
//        Rect screen = new Rect();
//
////        SubtasksActivity.checklistRootView.getWindowVisibleDisplayFrame(screen);
//
//        //Screen pixel values are used to determine how much of the screen is visible
////        int heightDiff = SubtasksActivity.checklistRootView.getRootView().getHeight() -
////                (screen.bottom - screen.top);
////
////        Log.i(TAG, "rootview height: " + SubtasksActivity.checklistRootView.getRootView().getHeight()
////                + "\nscreen.bottom: " + screen.bottom + "\nscreen.top: " + screen.top
////                + "\nheightDiff: " + heightDiff);
////
////        //TODO check hardcoded pixel values
////        if ((heightDiff > 800) && (SubtasksActivity.checklistRootView.getResources()
////                .getConfiguration().orientation == 1)) {
////
////            SubtasksActivity.subTasksClickable = false;
////
////        //Similar to above but for landscape mode
////        }else if((heightDiff > 73) && (heightDiff < 800) && (SubtasksActivity.checklistRootView
////                .getResources().getConfiguration().orientation == 2)){
////
////            SubtasksActivity.subTasksClickable = false;
////
////        }else{
////
////            SubtasksActivity.subTasksClickable = true;
////
////        }
//
////        if (SubtasksActivity.subTasksClickable) {
////
////            boolean isKilled = false;
////
////            //finding out if subtask has been killed
////            Cursor dbIdResult = MainActivity.db.getSubtaskData(Integer.parseInt(finalDbTaskId),
////                    SubtasksActivity.sortedSubtaskIds.get(position));
////            while (dbIdResult.moveToNext()) {
////                isKilled = dbIdResult.getInt(3) > 0;
////            }
////            dbIdResult.close();
////
////            //Marks sub task as complete
////            if (!isKilled){
////
////                MainActivity.db.updateSubtaskKilled(finalDbTaskId, String.valueOf(SubtasksActivity
////                        .sortedSubtaskIds.get(position)), true);
////                SubtasksActivity.subTasksKilled.set(position, true);
////
////                notifyDataSetChanged();
////
////            }
////
////        }
//
//    }

    private final static String TAG = "SubtasksAdapter";
    private List<Subtask> subtasks = new ArrayList<>();
    private SubtasksAdapter.OnItemClickListener listener;
    private SubtasksView subtaskView;

    public SubtasksAdapter(SubtasksView subtaskView) {
        this.subtaskView = subtaskView;
    }

    @Override
    public SubtasksAdapter.SubtaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_subtask, parent, false);
        return new SubtasksAdapter.SubtaskHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SubtasksAdapter.SubtaskHolder holder, int position) {
        //setting the subtask name in the item
        final Subtask currentSubtask = subtasks.get(position);
        holder.tvSubtask.setText(currentSubtask.getSubtask());
        //rename subtask on long click
        holder.subtaskLayout.setOnLongClickListener(view -> {
            subtaskView.editSubtask(currentSubtask);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return subtasks.size();
    }

    public void setSubtasks(List<Subtask> subtasks){
        this.subtasks = subtasks;
        notifyDataSetChanged();
    }

    public Subtask getSubtaskAt(int position){return subtasks.get(position);}

    //Building the item view
    class SubtaskHolder extends RecyclerView.ViewHolder {
        private TextView tvSubtask;
        private ConstraintLayout subtaskLayout;
        public SubtaskHolder(final View itemView) {
            super(itemView);
            tvSubtask = itemView.findViewById(R.id.tvSubtask);
            subtaskLayout = itemView.findViewById(R.id.subtask_layout);
            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION){
                    listener.onItemClick(subtasks.get(position));
                }
            });
            itemView.setOnLongClickListener(view -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION){
                    listener.onItemClick(subtasks.get(position));
                }
                return true;
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(Subtask subtask);
    }

    public void setOnItemClickListener(SubtasksAdapter.OnItemClickListener listener){this.listener = listener;}

}
