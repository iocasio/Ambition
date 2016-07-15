package ocasiocorp.ambition.Controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class Database_OpenHelper extends  SQLiteOpenHelper{


    public static final String CREATE_LISTS = "create table " + Database_Schema.GoalLists.NAME
            + "(_id integer primary key autoincrement, "
            + Database_Schema.GoalLists.Cols.ID + ", "
            + Database_Schema.GoalLists.Cols.LISTNAME+")";

    public Database_OpenHelper(Context context){
        super(context, Database_Schema.DATABASE_NAME, null, 2);// 2 is the database version
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + Database_Schema.Goals.NAME
        + "(_id integer primary key autoincrement, "
        + Database_Schema.Goals.Cols.GOALNAME + ", "
        + Database_Schema.Goals.Cols.ISIMPORTANT + ", "
        + Database_Schema.Goals.Cols.ID + ", "
                + Database_Schema.Goals.Cols.IsDone + ", "
                + Database_Schema.Goals.Cols.WHICH_LIST + ")"
        );

        db.execSQL(CREATE_LISTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion){
            case 1:
                db.execSQL("ALTER TABLE " +
                        Database_Schema.Goals.NAME +
                        " ADD COLUMN " +
                        Database_Schema.Goals.Cols.WHICH_LIST);
                db.execSQL(CREATE_LISTS);
                break;
        }
    }
}
