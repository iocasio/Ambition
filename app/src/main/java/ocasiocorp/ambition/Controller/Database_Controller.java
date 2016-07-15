package ocasiocorp.ambition.Controller;

/**
 * Created by Ocasiorey on 5/16/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

import ocasiocorp.ambition.Model.Goal;
import ocasiocorp.ambition.Model.GoalList;

public class Database_Controller {

    private static Database_Controller SINGLETON;
    private final Context mContext;
    private final SQLiteDatabase mDatabase;

    private Database_Controller(Context context){
        mContext = context.getApplicationContext();
        mDatabase = new Database_OpenHelper(mContext).getWritableDatabase();

    }

    public static Database_Controller get(Context context){
        if(SINGLETON == null){
            SINGLETON = new Database_Controller(context);
        }
        return SINGLETON;
    }



    public ArrayList<GoalList> getAllLists(){
        Cursor cursor = mDatabase.query(
                Database_Schema.GoalLists.NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        ListCursorWrapper wrapper = new ListCursorWrapper(cursor);
        ArrayList<GoalList> mGoalLists = new ArrayList<>();
        try{
            wrapper.moveToFirst();
            while(wrapper.isAfterLast() == false){
                mGoalLists.add(wrapper.getList());
                wrapper.moveToNext();
            }
        }finally {
            wrapper.close();
        }
        return  mGoalLists;
    }

    public GoalList getList(String title){
        Cursor cursor = mDatabase.query(
                Database_Schema.GoalLists.NAME,
                null,
                Database_Schema.GoalLists.Cols.LISTNAME + "=?",
                new String[] {title},
                null,
                null,
                null
        );
        ListCursorWrapper wrapper = new ListCursorWrapper(cursor);
        GoalList list;
        if(wrapper.getCount() >0){
            wrapper.moveToFirst();
            list = wrapper.getList();
        }else{
            list = null;
        }
        wrapper.close();

        return list;
    }

    public ArrayList<Goal> getAllListsGoals(String ListTitle){
        Log.d("TAG", ListTitle + " LIST TITLE!");
        Cursor cursor = mDatabase.query(
                Database_Schema.Goals.NAME,
                null,
                Database_Schema.Goals.Cols.WHICH_LIST + " =?",
                new String[]{ListTitle},
                null,
                null,
                null
        );

        GoalCursorWrapper wrapper = new GoalCursorWrapper(cursor);
        ArrayList<Goal> mGoalList = new ArrayList<>();
        try{
            wrapper.moveToFirst();
            while(wrapper.isAfterLast() == false){
                mGoalList.add(wrapper.getGoal());
                wrapper.moveToNext();
            }
        }finally {
            wrapper.close();
        }
        Log.d("Expected: ", ListTitle);
        for(int i = 0; i < mGoalList.size(); i++){
            Log.d("DATABASE: ",mGoalList.get(i).getWhich_List().toString());
        }
        return  mGoalList;
    }

    public void insertList(GoalList goallist){
        ContentValues values = getListContentValues(goallist);
        mDatabase.insert(Database_Schema.GoalLists.NAME, null, values);
    }

    public static ContentValues getListContentValues(GoalList list){
        ContentValues values = new ContentValues();

        values.put(Database_Schema.GoalLists.Cols.LISTNAME, list.getTitle());
        values.put(Database_Schema.GoalLists.Cols.ID, list.getUuid().toString());

        return values;
    }


    public void deleteList(String id){
        mDatabase.delete(Database_Schema.GoalLists.NAME, "id = ?", new String[]{id});
    }

    public void updateList(GoalList list){
        String id = list.getUuid().toString();
        ContentValues values = getListContentValues(list);
        mDatabase.update(Database_Schema.GoalLists.NAME,
                values,
                Database_Schema.GoalLists.Cols.ID+ "=?",
                new String[] {id});
    }

    private static class ListCursorWrapper extends CursorWrapper {
        public ListCursorWrapper(Cursor cursor) {
            super(cursor);
        }

        public GoalList getList(){
            GoalList list = new GoalList();
            list.setTitle(getString(getColumnIndex(Database_Schema.GoalLists.Cols.LISTNAME)));
            list.setUuid(UUID.fromString(getString(getColumnIndex(Database_Schema.GoalLists.Cols.ID))));
            return list;
        }
    }


    ///////////////////////////////GOAL DATABASE METHODS\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    public ArrayList<Goal> getAllGoals(){
        Cursor cursor = mDatabase.query(
                Database_Schema.Goals.NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        GoalCursorWrapper wrapper = new GoalCursorWrapper(cursor);
        ArrayList<Goal> mGoalList = new ArrayList<>();
        try{
            wrapper.moveToFirst();
            while(wrapper.isAfterLast() == false){
                mGoalList.add(wrapper.getGoal());
                wrapper.moveToNext();
            }
        }finally {
            wrapper.close();
        }
        return reverseList(mGoalList);
    }

    public static ContentValues getGoalContentValues(Goal goal){
        ContentValues values = new ContentValues();

        values.put(Database_Schema.Goals.Cols.GOALNAME, goal.getmGoalName());
        values.put(Database_Schema.Goals.Cols.ISIMPORTANT, Boolean.toString(goal.ismIsImportant()));
        values.put(Database_Schema.Goals.Cols.ID, goal.getmId().toString());
        values.put(Database_Schema.Goals.Cols.IsDone, Boolean.toString(goal.isFinished()));
        values.put(Database_Schema.Goals.Cols.WHICH_LIST, goal.getWhich_List());

        return values;
    }

    public void insertGoal(Goal goal){
        ContentValues values = getGoalContentValues(goal);
        mDatabase.insert(Database_Schema.Goals.NAME, null, values);
    }

    public Goal getGoal(String id){
        Cursor cursor = mDatabase.query(
                Database_Schema.Goals.NAME,
                null,
                "id = ?",
                new String[] {id},
                null,
                null,
                null
        );
        GoalCursorWrapper wrapper = new GoalCursorWrapper(cursor);
        Goal goal;
        if(wrapper.getCount() >0){
            wrapper.moveToFirst();
            goal = wrapper.getGoal();
        }else{
            goal = null;
        }
        wrapper.close();

        return goal;
    }

    public void deleteGoal(String id){
        mDatabase.delete(Database_Schema.Goals.NAME, "id = ?", new String[]{id});
    }

    public void updateGoal(Goal goal){
        String id = goal.getmId().toString();
        ContentValues values = getGoalContentValues(goal);
        mDatabase.update(Database_Schema.Goals.NAME,
                values,
                Database_Schema.Goals.Cols.ID + "=?",
                new String[] {id});
    }

    private static class GoalCursorWrapper extends CursorWrapper {
        public GoalCursorWrapper(Cursor cursor) {
            super(cursor);
        }

        public Goal getGoal(){
            Goal goal = new Goal();

            goal.setmGoalName(getString(getColumnIndex(Database_Schema.Goals.Cols.GOALNAME)));
            goal.setmIsImportant(Boolean.parseBoolean(getString(getColumnIndex(Database_Schema.Goals.Cols.ISIMPORTANT))));
            goal.setmId(UUID.fromString(getString(getColumnIndex(Database_Schema.Goals.Cols.ID))));
            goal.setFinished(Boolean.parseBoolean(getString(getColumnIndex(Database_Schema.Goals.Cols.IsDone))));
            goal.setWhich_List(getString(getColumnIndex(Database_Schema.Goals.Cols.WHICH_LIST)));
            return goal;
        }
    }

    private static ArrayList<Goal> reverseList(ArrayList<Goal> goalArrayList){
        Collections.reverse(goalArrayList);
        return goalArrayList;
    }
    //////////////////////////GOAL DATABASE METHODS\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
}
