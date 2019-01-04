package com.violenthoboenterprises.taskkillernoexcuses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Calendar;


public class Database extends SQLiteOpenHelper {

    public static final String DBNAME = "Notes.db";
    //Main Table
    public static final String TABLE = "notes_table";
    public static final String COL1 = "ID";
    public static final String COL2 = "NOTE";
    public static final String COL3 = "CHECKLIST";
    public static final String COL4 = "TIMESTAMP";
    public static final String COL5 = "TASK";
    public static final String COL6 = "DUE";
    public static final String COL7 = "KILLED";
    public static final String COL8 = "BROADCAST";
    public static final String COL9 = "REPEAT";
    public static final String COL10 = "OVERDUE";
    public static final String COL11 = "SNOOZED";
    public static final String COL12 = "SHOWONCE";
    public static final String COL13 = "INTERVAL";
    public static final String COL14 = "REPEATINTERVAL";
    public static final String COL15 = "IGNORED";
    public static final String COL16 = "TIMECREATED";
    public static final String COL17 = "SORTEDINDEX";
    public static final String COL18 = "CHECKLISTSIZE";
    public static final String COL19 = "MANUALKILL";
    public static final String COL20 = "KILLEDEARLY";
    public static final String COL21 = "ORIGINALDAY";
    public static final String COL22 = "SNOOZEDTIMESTAMP";

    //Alarm Table
    public static final String ATABLE = "alarms_table";
    public static final String ACOL1 = "ID";
    public static final String ACOL2 = "HOUR";
    public static final String ACOL3 = "MINUTE";
    public static final String ACOL4 = "AMPM";
    public static final String ACOL5 = "DAY";
    public static final String ACOL6 = "MONTH";
    public static final String ACOL7 = "YEAR";

    //Snooze Table
    public static final String STABLE = "snooze_table";
    public static final String SCOL1 = "ID";
    public static final String SCOL2 = "HOUR";
    public static final String SCOL3 = "MINUTE";
    public static final String SCOL4 = "AMPM";
    public static final String SCOL5 = "DAY";
    public static final String SCOL6 = "MONTH";
    public static final String SCOL7 = "YEAR";

    //Universal data
    public static final String UTABLE = "universal_table";
    public static final String UCOL1 = "ID";
    public static final String UCOL2 = "MUTE";
    public static final String UCOL3 = "HIGHLIGHT";
    public static final String UCOL4 = "DARKLIGHT";
    public static final String UCOL5 = "ACTIVETASKNAME";
    public static final String UCOL6 = "ADSREMOVED";
    public static final String UCOL7 = "REMINDERSAVAILABLE";
    public static final String UCOL8 = "CYCLECOLORS";
    public static final String UCOL9 = "TASKLISTSIZE";
    public static final String UCOL10 = "CHECKLISTLISTSIZE";
    public static final String UCOL11 = "SETALARM";
    public static final String UCOL12 = "YEAR";
    public static final String UCOL13 = "MONTH";
    public static final String UCOL14 = "DAY";
    public static final String UCOL15 = "HOUR";
    public static final String UCOL16 = "MINUTE";
    public static final String UCOL17 = "COLORLASTCHANGED";
    public static final String UCOL18 = "AMPM";
    public static final String UCOL19 = "CYCLEENABLED";
    public static final String UCOL20 = "DUESSET";
    public static final String UCOL21 = "MOTIVATION";
    public static final String UCOL22 = "REPEATHINT";
    public static final String UCOL23 = "RENAMEHINT";
    public static final String UCOL24 = "REINSTATEHINT";
    public static final String UCOL25 = "TIMESTARTED";
    public static final String UCOL26 = "REVIEWONE";
    public static final String UCOL27 = "REVIEWTWO";
    public static final String UCOL28 = "REVIEWTHREE";
    public static final String UCOL29 = "REVIEWFOUR";
    public static final String UCOL30 = "HIGHLIGHTDEC";
    public static final String UCOL31 = "REPEATINTERVALTEMP";
    public static final String UCOL32 = "ORIGINALDAYTEMP";
    public static final String UCOL33 = "REPEATTEMP";

    //Subtasks
    public static final String CTABLE = "subtasks_table";
    public static final String CCOL1 = "ID";
    public static final String CCOL2 = "SUBTASKID";
    public static final String CCOL3 = "SUBTASK";
    public static final String CCOL4 = "KILLED";
    public static final String CCOL5 = "TIMECREATED";
    public static final String CCOL6 = "SORTEDINDEX";

    String TAG = "Data";

    public Database(Context context) {
        super(context, DBNAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE + " (ID INTEGER PRIMARY KEY, " +
                "NOTE TEXT, CHECKLIST BOOLEAN, TIMESTAMP TEXT, TASK TEXT, DUE BOOLEAN," +
                " KILLED BOOLEAN, BROADCAST INTEGER, REPEAT BOOLEAN, OVERDUE BOOLEAN, " +
                "SNOOZED BOOLEAN, SHOWONCE BOOLEAN, INTERVAL INTEGER, REPEATINTERVAL TEXT," +
                " IGNORED BOOLEAN, TIMECREATED TEXT, SORTEDINDEX INTEGER, CHECKLISTSIZE INETGER, " +
                "MANUALKILL BOOLEAN, KILLEDEARLY BOOLEAN, ORIGINALDAY TEXT, SNOOZEDTIMESTAMP TEXT)");
        db.execSQL("create table " + ATABLE + " (ID INTEGER PRIMARY KEY, " +
                "HOUR TEXT, MINUTE TEXT, AMPM TEXT, DAY TEXT, MONTH TEXT, YEAR TEXT)");
        db.execSQL("create table " + STABLE + " (ID INTEGER PRIMARY KEY, " +
                "HOUR TEXT, MINUTE TEXT, AMPM TEXT, DAY TEXT, MONTH TEXT, YEAR TEXT)");
        db.execSQL("create table " + UTABLE + " (ID INTEGER PRIMARY KEY, MUTE BOOLEAN," +
                " HIGHLIGHT TEXT, DARKLIGHT BOOLEAN, ACTIVETASKNAME TEXT, ADSREMOVED BOOLEAN," +
                " REMINDERSAVAILABLE BOOLEAN, CYCLECOLORS BOOLEAN, TASKLISTSIZE INTEGER, " +
                "CHECKLISTLISTSIZE INTEGER, SETALARM BOOLEAN, YEAR INTEGER, MONTH INTEGER," +
                " DAY INTEGER, HOUR INTEGER, MINUTE INTEGER, COLORLASTCHANGED INTEGER, " +
                "AMPM INTEGER, CYCLEENABLED BOOLEAN, DUESSET INTEGER, MOTIVATION BOOLEAN, " +
                "REPEATHINT INTEGER, RENAMEHINT INTEGER, REINSTATEHINT INTEGER, " +
                "TIMESTARTED INTEGER, REVIEWONE BOOLEAN, REVIEWTWO BOOLEAN, REVIEWTHREE BOOLEAN, " +
                "REVIEWFOUR BOOLEAN, HIGHLIGHTDEC TEXT, REPEATINTERVALTEMP TEXT, " +
                "ORIGINALDAYTEMP TEXT, REPEATTEMP BOOLEAN)");
        db.execSQL("create table " + CTABLE + " (ID INTEGER, SUBTASKID INTEGER," +
                " SUBTASK TEXT, KILLED BOOLEAN, TIMECREATED TEXT, SORTEDINDEX INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ATABLE);
        db.execSQL("DROP TABLE IF EXISTS " + STABLE);
        db.execSQL("DROP TABLE IF EXISTS " + UTABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CTABLE);
        onCreate(db);
    }

    public boolean insertData(int id, String note, String task, int broadcast, String timeCreated){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content= new ContentValues();
        content.put(COL1, id);
        content.put(COL2, note);
        content.put(COL3, false);
        content.put(COL4, "0");
        content.put(COL5, task);
        content.put(COL6, false);
        content.put(COL7, false);
        content.put(COL8, broadcast);
        content.put(COL9, false);
        content.put(COL10, false);
        content.put(COL11, false);
        content.put(COL12, false);
        content.put(COL13, 0);
        content.put(COL14, "");
        content.put(COL15, false);
        content.put(COL16, timeCreated);
        content.put(COL17, 0);
        content.put(COL18, 0);
        content.put(COL19, 0);
        content.put(COL20, 0);
        content.put(COL21, "");
        content.put(COL22, 0);
        long result = db.insert(TABLE, null, content);
        if(result == -1){
            return false;
        }else {
            return true;
        }
    }

    public boolean insertAlarmData(int id, String hour, String minute, String ampm, String day,
                                   String month, String year){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content= new ContentValues();
        content.put(ACOL1, id);
        content.put(ACOL2, hour);
        content.put(ACOL3, minute);
        content.put(ACOL4, ampm);
        content.put(ACOL5, day);
        content.put(ACOL6, month);
        content.put(ACOL7, year);
        long result = db.insert(ATABLE, null, content);
        if(result == -1){
            return false;
        }else {
            return true;
        }
    }

    public boolean insertSnoozeData(int id, String hour, String minute, String ampm,
                                    String day, String month, String year){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content= new ContentValues();
        content.put(SCOL1, id);
        content.put(SCOL2, hour);
        content.put(SCOL3, minute);
        content.put(SCOL4, ampm);
        content.put(SCOL5, day);
        content.put(SCOL6, month);
        content.put(SCOL7, year);
        long result = db.insert(STABLE, null, content);
        if(result == -1){
            return false;
        }else {
            return true;
        }
    }

    public boolean insertUniversalData(boolean mute){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content= new ContentValues();
        Calendar calendar = Calendar.getInstance();
        content.put(UCOL1, 0);
        content.put(UCOL2, mute);
        content.put(UCOL3, "#FF00FF00");
        content.put(UCOL4, false);
        content.put(UCOL5, "");
        content.put(UCOL6, false);//TODO change back to false!!!
        content.put(UCOL7, false);//TODO change back to false!!!
        content.put(UCOL8, false);//TODO change back to false!!!
        content.put(UCOL9, 0);
        content.put(UCOL10, 0);
        content.put(UCOL11, false);
        content.put(UCOL12, 0);
        content.put(UCOL13, 0);
        content.put(UCOL14, 0);
        content.put(UCOL15, 0);
        content.put(UCOL16, 0);
        content.put(UCOL17, (calendar.getTimeInMillis() /1000 /60 /60));
        content.put(UCOL18, 0);
        content.put(UCOL19, 0);
        content.put(UCOL20, 0);
        content.put(UCOL21, true);
        content.put(UCOL22, 0);
        content.put(UCOL23, 0);
        content.put(UCOL24, 0);
        content.put(UCOL25, (calendar.getTimeInMillis() /1000 /60 /60));
        content.put(UCOL26, false);
        content.put(UCOL27, false);
        content.put(UCOL28, false);
        content.put(UCOL29, false);
        content.put(UCOL30, "-298516736");
        content.put(UCOL31, "0");
        content.put(UCOL32, "");
        content.put(UCOL33, false);
        long result = db.insert(UTABLE, null, content);
        if(result == -1){
            return false;
        }else {
            return true;
        }
    }

    public boolean insertSubtaskData(int id, int subtaskID, String subtask, String stamp){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content= new ContentValues();
        content.put(CCOL1, id);
        content.put(CCOL2, subtaskID);
        content.put(CCOL3, subtask);
        content.put(CCOL4, false);
        content.put(CCOL5, stamp);
        content.put(CCOL6, 0);
        long result = db.insert(CTABLE, null, content);
        if(result == -1){
            return false;
        }else {
            return true;
        }
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " + TABLE, null);
        return result;
    }

    public Cursor getData(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " + TABLE + " where " + COL1
                + " == " + id, null);
        return result;
    }

    public Cursor getSortedData(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " + TABLE + " where " + COL17
                + " == " + id, null);
        return result;
    }

    public Cursor getDataByTimestamp(String stamp){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " + TABLE + " where " + COL16
                + " == " + stamp, null);
//        Log.i(TAG, "result: " + result);
//        String dbID = "";
//        boolean dbSnooze = false;
//        int dbSnoozeInterval = 0;
//        String task = "";
//        while (result.moveToNext()){
//            dbID = result.getString(0);
//            dbSnooze = result.getInt(10) > 0;
//            dbSnoozeInterval = result.getInt(12);
//            Log.i(TAG, "task: " + result.getString(4));
//        }
//        if(dbSnooze){
//            result = this.getData(Integer.parseInt(dbID));
//        }

        return result;
    }

    public Cursor getSubtaskDataByTimestamp(String stamp){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " + CTABLE + " where " + CCOL5
                + " == " + stamp, null);
        return result;
    }

    public Cursor getDataByDueTime(String stamp){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " + TABLE + " where " + COL4
                + " == " + stamp, null);
        return result;
    }

    public Cursor getDataBySnoozeTime(String stamp){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " + TABLE + " where " + COL22
                + " == " + stamp, null);
        return result;
    }

    public Cursor getAllAlarmData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " + ATABLE, null);
        return result;
    }

    public Cursor getAllSnoozeData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " + STABLE, null);
        return result;
    }

    public Cursor getAllUniversalData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " + UTABLE, null);
        return result;
    }

    public Cursor getAlarmData(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " + ATABLE + " where " + ACOL1
                + " == " + id, null);
        return result;
    }

    public Cursor getSnoozeData(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " + STABLE + " where " + SCOL1
                + " == " + id, null);
        return result;
    }

    public Cursor getUniversalData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " + UTABLE + " where " + UCOL1
                + " == " + 0, null);
        return result;
    }

    public Cursor getAllSubtaskData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " + CTABLE, null);
        return result;
    }

    public Cursor getSubtaskData(int id, int subId){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " + CTABLE + " where " + CCOL1
                + " == " + id + " AND " + CCOL2 + " == " + subId, null);
        return result;
    }

    public Cursor getSubtask(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " + CTABLE + " where " + CCOL1
                + " == " + id, null);
        return result;
    }

    public boolean updateData(String id, String note, Boolean checklist){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(COL1, id);
        if(MainActivity.inNote) {
            content.put(COL2, note);
        }else if(MainActivity.inChecklist){
            content.put(COL3, checklist);
        }else{
            content.put(COL2, note);
            content.put(COL3, checklist);
        }
        db.update(TABLE, content, "ID = ?", new String[] {id});
        return true;
    }

    public boolean updateDue(String id, Boolean due){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(COL6, due);
        db.update(TABLE, content, "ID = ?", new String[] {id});
        return true;
    }

    public boolean updateSnooze(String id, Boolean snooze){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(COL11, snooze);
        db.update(TABLE, content, "ID = ?", new String[] {id});
        return true;
    }

    public boolean updateKilled(String id, Boolean killed){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(COL7, killed);
        db.update(TABLE, content, "ID = ?", new String[] {id});
        return true;
    }

    public boolean updateName(String id, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(COL5, name);
        db.update(TABLE, content, "ID = ?", new String[] {id});
        return true;
    }

    public boolean updateRepeat(String id, Boolean repeat){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(COL9, repeat);
        db.update(TABLE, content, "ID = ?", new String[] {id});
        return true;
    }

    //TODO why do all these methods return a boolean? can they be void?
    public boolean updateTimestamp(String id, String timestamp){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content= new ContentValues();
        content.put(COL4, timestamp);
        db.update(TABLE, content, "ID = ?", new String[] {id});
        return true;
    }

    public boolean updateOverdue(String id, Boolean overdue){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(COL10, overdue);
        db.update(TABLE, content, "ID = ?", new String[] {id});
        return true;
    }

    public boolean updateShowOnce(String id, Boolean showOnce){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(COL12, showOnce);
        db.update(TABLE, content, "ID = ?", new String[] {id});
        return true;
    }

    public boolean updateInterval(String id, String interval){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(COL13, interval);
        db.update(TABLE, content, "ID = ?", new String[] {id});
        return true;
    }

    public boolean updateRepeatInterval(String id, String interval){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(COL14, interval);
        db.update(TABLE, content, "ID = ?", new String[] {id});
        return true;
    }

    public boolean updateIgnored(String id, boolean ignored){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(COL15, ignored);
        db.update(TABLE, content, "ID = ?", new String[] {id});
        return true;
    }

    public boolean updateSortedIndex(String id, int index){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(COL17, index);
        db.update(TABLE, content, "ID = ?", new String[] {id});
        return true;
    }

    public boolean updateManualKill(String id, boolean kill){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(COL19, kill);
        db.update(TABLE, content, "ID = ?", new String[] {id});
        return true;
    }

    public boolean updateKilledEarly(String id, boolean kill){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(COL20, kill);
        db.update(TABLE, content, "ID = ?", new String[] {id});
        return true;
    }

    public boolean updateSubtaskSortedIndex(String id, String subtask, int index){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(CCOL6, index);
        db.update(CTABLE, content, "ID = ? AND SUBTASKID = ?",
                new String[] {id, subtask});
        return true;
    }

    public boolean updateChecklistSize(String id, int index){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(COL18, index);
        db.update(TABLE, content, "ID = ?", new String[] {id});
        return true;
    }

    //Used to recalibrate monthly repeating tasks
    public boolean updateOriginalDay(String id, String day){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(COL21, day);
        db.update(TABLE, content, "ID = ?", new String[] {id});
        return true;
    }

    public boolean updateSnoozedTimestamp(String id, String snoozed){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(COL22, snoozed);
        db.update(TABLE, content, "ID = ?", new String[] {id});
        return true;
    }

    public boolean updateAlarmData(String id, String hour, String minute, String ampm,
                                   String day, String month, String year){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content= new ContentValues();
        content.put(ACOL1, id);
        content.put(ACOL2, hour);
        content.put(ACOL3, minute);
        content.put(ACOL4, ampm);
        content.put(ACOL5, day);
        content.put(ACOL6, month);
        content.put(ACOL7, year);
        db.update(ATABLE, content, "ID = ?", new String[] {id});
        return true;
    }

    public boolean updateSnoozeData(String id, String hour, String minute, String ampm,
                                    String day, String month, String year){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content= new ContentValues();
        content.put(SCOL1, id);
        content.put(SCOL2, hour);
        content.put(SCOL3, minute);
        content.put(SCOL4, ampm);
        content.put(SCOL5, day);
        content.put(SCOL6, month);
        content.put(SCOL7, year);
        db.update(STABLE, content, "ID = ?", new String[] {id});
        return true;
    }

    public boolean updateMute(Boolean mute){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(UCOL2, mute);
        db.update(UTABLE, content, "ID = ?", new String[] {"0"});
        return true;
    }

    public boolean updateHighlight(String highlight){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(UCOL3, highlight);
        db.update(UTABLE, content, "ID = ?", new String[] {"0"});
        return true;
    }

    public boolean updateDarkLight(boolean darkLight){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(UCOL4, darkLight);
        db.update(UTABLE, content, "ID = ?", new String[] {"0"});
        return true;
    }

    public boolean updateActiveTaskTemp(String tempTask){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(UCOL5, tempTask);
        db.update(UTABLE, content, "ID = ?", new String[] {"0"});
        return true;
    }

    public boolean updateAdsRemoved(boolean removal){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(UCOL6, removal);
        db.update(UTABLE, content, "ID = ?", new String[] {"0"});
        return true;
    }

    public boolean updateRemindersAvailable(boolean reminder){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(UCOL7, reminder);
        db.update(UTABLE, content, "ID = ?", new String[] {"0"});
        return true;
    }

    public boolean updateCycleColors(boolean cycle){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(UCOL8, cycle);
        db.update(UTABLE, content, "ID = ?", new String[] {"0"});
        return true;
    }

    public boolean updateTaskListSize(int size){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(UCOL9, size);
        db.update(UTABLE, content, "ID = ?", new String[] {"0"});
        return true;
    }

    public boolean updateChecklistListSize(int size){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(UCOL10, size);
        db.update(UTABLE, content, "ID = ?", new String[] {"0"});
        return true;
    }

    public boolean updateSetAlarm(boolean set){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(UCOL11, set);
        db.update(UTABLE, content, "ID = ?", new String[] {"0"});
        return true;
    }

    public boolean updateYear(int value){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(UCOL12, value);
        db.update(UTABLE, content, "ID = ?", new String[] {"0"});
        return true;
    }

    public boolean updateMonth(int value){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(UCOL13, value);
        db.update(UTABLE, content, "ID = ?", new String[] {"0"});
        return true;
    }

    public boolean updateDay(int value){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(UCOL14, value);
        db.update(UTABLE, content, "ID = ?", new String[] {"0"});
        return true;
    }

    public boolean updateHour(int value){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(UCOL15, value);
        db.update(UTABLE, content, "ID = ?", new String[] {"0"});
        return true;
    }

    public boolean updateMinute(int value){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(UCOL16, value);
        db.update(UTABLE, content, "ID = ?", new String[] {"0"});
        return true;
    }

    public boolean updateAmPm(int value){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(UCOL18, value);
        db.update(UTABLE, content, "ID = ?", new String[] {"0"});
        return true;
    }

    public boolean updateColorLastChanged(int value){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(UCOL17, value);
        db.update(UTABLE, content, "ID = ?", new String[] {"0"});
        return true;
    }

    public boolean updateCycleEnabled(boolean enable){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(UCOL19, enable);
        db.update(UTABLE, content, "ID = ?", new String[] {"0"});
        return true;
    }

    public boolean updateDuesSet(int dues){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(UCOL20, dues);
        db.update(UTABLE, content, "ID = ?", new String[] {"0"});
        return true;
    }

    public boolean updateMotivation(boolean motivate){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(UCOL21, motivate);
        db.update(UTABLE, content, "ID = ?", new String[] {"0"});
        return true;
    }

    public boolean updateRepeatHint(int hint){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(UCOL22, hint);
        db.update(UTABLE, content, "ID = ?", new String[] {"0"});
        return true;
    }

    public boolean updateRenameHint(int hint){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(UCOL23, hint);
        db.update(UTABLE, content, "ID = ?", new String[] {"0"});
        return true;
    }

    public boolean updateReinstateHint(int hint){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(UCOL24, hint);
        db.update(UTABLE, content, "ID = ?", new String[] {"0"});
        return true;
    }

    public boolean updateReviewOne(boolean review){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(UCOL26, review);
        db.update(UTABLE, content, "ID = ?", new String[] {"0"});
        return true;
    }

    public boolean updateReviewTwo(boolean review){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(UCOL27, review);
        db.update(UTABLE, content, "ID = ?", new String[] {"0"});
        return true;
    }

    public boolean updateReviewThree(boolean review){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(UCOL28, review);
        db.update(UTABLE, content, "ID = ?", new String[] {"0"});
        return true;
    }

    public boolean updateReviewFour(boolean review){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(UCOL29, review);
        db.update(UTABLE, content, "ID = ?", new String[] {"0"});
        return true;
    }

    public boolean updateHighlightDec(String highlight){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(UCOL30, highlight);
        db.update(UTABLE, content, "ID = ?", new String[] {"0"});
        return true;
    }

    public boolean updateRepeatIntervalTemp(String interval){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(UCOL31, interval);
        db.update(UTABLE, content, "ID = ?", new String[] {"0"});
        return true;
    }

    public boolean updateOriginalDayTemp(String day){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(UCOL32, day);
        db.update(UTABLE, content, "ID = ?", new String[] {"0"});
        return true;
    }

    public boolean updateRepeatTemp(boolean repeat){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(UCOL33, repeat);
        db.update(UTABLE, content, "ID = ?", new String[] {"0"});
        return true;
    }

    public boolean updateSubtask(String id, String subtaskId, String subtask){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(CCOL3, subtask);
        db.update(CTABLE, content, "ID = ? AND SUBTASKID = ?",
                new String[] {id, subtaskId});
        return true;
    }

    public boolean updateSubtaskKilled(String id, String subtask, Boolean killed){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(CCOL4, killed);
        db.update(CTABLE, content, "ID = ? AND SUBTASKID = ?",
                new String[] {id, subtask});
        return true;
    }

    public Integer deleteData (String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE, "ID = ?", new String[] {id});
    }

    public Integer deleteAlarmData (String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(ATABLE, "ID = ?", new String[] {id});
    }

    public Integer deleteSnoozeData (String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(STABLE, "ID = ?", new String[] {id});
    }

    public Integer deleteSubtaskData (String id, String subtask){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(CTABLE, "ID = ? AND SUBTASKID = ?", new String[] {id, subtask});
    }

    public Integer deleteAllSubtaskData (String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(CTABLE, "ID = ?", new String[] {id});
    }

    public int getTotalRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor countCursor = db.rawQuery("SELECT COUNT (*) FROM notes_table", null);
        int count = 0;
        if(countCursor != null){
            if(countCursor.getCount() > 0){
                countCursor.moveToFirst();
                count = countCursor.getInt(0);
            }
            countCursor.close();
        }
        return count;
    }

    public ArrayList<Integer> getIDs(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Integer> theList = new ArrayList<>();
        Cursor IDCursor = db.rawQuery("SELECT ID FROM notes_table", null);
        while (IDCursor.moveToNext()) {
            theList.add(IDCursor.getInt(0));
        }
        IDCursor.close();
        return theList;
    }

}
