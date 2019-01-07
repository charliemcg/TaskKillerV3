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
        checkDarkLight(holder);
        holder.tvSubtask.setText(currentSubtask.getSubtask());
        //rename subtask on long click
        holder.subtaskLayout.setOnLongClickListener(view -> {
            subtaskView.editSubtask(currentSubtask);
            return true;
        });
    }

    private void checkDarkLight(SubtaskHolder holder) {
        if(MainActivity.boolDarkModeEnabled) {
            holder.tvSubtask.setTextColor(Color.parseColor("#AAAAAA"));
        }else{
            holder.tvSubtask.setTextColor(Color.parseColor("#000000"));
        }
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
