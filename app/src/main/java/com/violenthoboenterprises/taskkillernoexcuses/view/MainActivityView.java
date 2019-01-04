package com.violenthoboenterprises.taskkillernoexcuses.view;

import com.violenthoboenterprises.taskkillernoexcuses.model.Task;

public interface MainActivityView {
    void addTask(Task task);

    void showPurchases();

    void toggleFab(boolean showFab);
}
