package edu.arizona.uas.jefferychang.hw3.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import edu.arizona.uas.jefferychang.hw3.database.BloodGlucoseDbSchema.BloodGlucoseTable;

public class BloodGlucoseBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "bloodglucoseBase.db";

    public BloodGlucoseBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + BloodGlucoseTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                BloodGlucoseTable.Cols.UUID + ", " +
                BloodGlucoseTable.Cols.BREAKFAST + ", " +
                BloodGlucoseTable.Cols.LUNCH + ", " +
                BloodGlucoseTable.Cols.DINNER + ", " +
                BloodGlucoseTable.Cols.FASTING + ", " +
                BloodGlucoseTable.Cols.DATE + ", " +
                BloodGlucoseTable.Cols.NORMAL +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
