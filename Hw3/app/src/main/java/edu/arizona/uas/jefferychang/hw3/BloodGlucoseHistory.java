package edu.arizona.uas.jefferychang.hw3;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import edu.arizona.uas.jefferychang.hw3.database.BloodGlucoseBaseHelper;
import edu.arizona.uas.jefferychang.hw3.database.BloodGlucoseCursorWrapper;
import edu.arizona.uas.jefferychang.hw3.database.BloodGlucoseDbSchema.BloodGlucoseTable;

import static edu.arizona.uas.jefferychang.hw3.database.BloodGlucoseDbSchema.BloodGlucoseTable.Cols.*;

public class BloodGlucoseHistory {
    private static BloodGlucoseHistory sBloodGlucoseHistory;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    private List<BloodGlucose> mBloodGlucoses;

    public static BloodGlucoseHistory get(Context context) {
        if (sBloodGlucoseHistory == null) {
            sBloodGlucoseHistory = new BloodGlucoseHistory(context);
        }

        return sBloodGlucoseHistory;
    }

    private BloodGlucoseHistory(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new BloodGlucoseBaseHelper(mContext)
                .getWritableDatabase();
    }

    public void addBloodGlucose(BloodGlucose bloodGlucose){
        ContentValues values = getContentValues(bloodGlucose);
        mDatabase.insert(BloodGlucoseTable.NAME, null, values);
    }
    public void deleteBloodGlucosebydate(BloodGlucose bloodGlucose){
        DateFormat mediumDf = DateFormat.getDateInstance(DateFormat.MEDIUM);
        String date =mediumDf.format(bloodGlucose.getDate());
        mDatabase.delete(BloodGlucoseTable.NAME,  BloodGlucoseTable.Cols.DATE + " = '" + date + "'", null);
    }
    public void deleteBloodGlucose(BloodGlucose bloodGlucose){
        mDatabase.delete(BloodGlucoseTable.NAME,  BloodGlucoseTable.Cols.UUID + " = '" + bloodGlucose.getId() + "'", null);
    }

    public List<BloodGlucose> getBloodGlucoseData() {
        List<BloodGlucose> bloodGlucoses = new ArrayList<>();
        BloodGlucoseCursorWrapper cursor = queryBloodGlucoses(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                bloodGlucoses.add(cursor.getBloodGlucose());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return bloodGlucoses;
    }

    public BloodGlucose getBloodGlucose(UUID id) {

        BloodGlucoseCursorWrapper cursor = queryBloodGlucoses(
                BloodGlucoseTable.Cols.UUID + " = '" + id.toString()+"'",null);
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getBloodGlucose();
        } finally {
            cursor.close();
        }
    }


    public BloodGlucose getBloodGlucoseByDate(Date date) {
        try {
            DateFormat mediumDf = DateFormat.getDateInstance(DateFormat.MEDIUM);
            String time = mediumDf.format(date);
            BloodGlucoseCursorWrapper cursor = queryBloodGlucoses(
                    BloodGlucoseTable.Cols.DATE + " = '" + time + "'", null);
            try {
                if (cursor.getCount() == 0) {
                    return null;
                }
                cursor.moveToFirst();
                return cursor.getBloodGlucose();
            } finally {
                cursor.close();
            }
        }
        catch(Exception e){
            return null;
        }
    }
    public void updateBloodGlucose(BloodGlucose bloodGlucose) {
        String uuidString = bloodGlucose.getId().toString();
        ContentValues values = getContentValues(bloodGlucose);
        mDatabase.update(BloodGlucoseTable.NAME, values,
                BloodGlucoseTable.Cols.UUID + " = '" + uuidString +"'",null);
    }

    public void updateBloodGlucosebyDate(BloodGlucose bloodGlucose) {
        BloodGlucose temp = getBloodGlucoseByDate(bloodGlucose.getDate());
        if(temp !=null){
            deleteBloodGlucose(temp);
            addBloodGlucose(bloodGlucose);
        }
        else {
            DateFormat mediumDf = DateFormat.getDateInstance(DateFormat.MEDIUM);
            String date = mediumDf.format(bloodGlucose.getDate());
            ContentValues values = getContentValues(bloodGlucose);
            mDatabase.update(BloodGlucoseTable.NAME, values,
                    BloodGlucoseTable.Cols.DATE + " = '" + date + "'", null);
        }
    }

    private BloodGlucoseCursorWrapper queryBloodGlucoses(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                BloodGlucoseTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null  // orderBy
        );
        return new BloodGlucoseCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(BloodGlucose bloodGlucose) {
        DateFormat mediumDf = DateFormat.getDateInstance(DateFormat.MEDIUM);
        String time =mediumDf.format(bloodGlucose.getDate());
        ContentValues values = new ContentValues();
        values.put(UUID, bloodGlucose.getId().toString());
        values.put(BREAKFAST, bloodGlucose.getBreakfast());
        values.put(LUNCH, bloodGlucose.getLunch());
        values.put(DINNER, bloodGlucose.getDinner());
        values.put(FASTING, bloodGlucose.getFasting());
        values.put(DATE, time);
        values.put(NORMAL, bloodGlucose.ismNormal());
        return values;
    }
}
