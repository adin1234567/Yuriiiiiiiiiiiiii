package edu.arizona.uas.jefferychang.hw3.database;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.util.Log;

import java.text.DateFormat;
import java.util.Date;
import java.util.UUID;
import edu.arizona.uas.jefferychang.hw3.BloodGlucose;
import edu.arizona.uas.jefferychang.hw3.database.BloodGlucoseDbSchema.BloodGlucoseTable;
public class BloodGlucoseCursorWrapper extends CursorWrapper {

    public BloodGlucoseCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public BloodGlucose getBloodGlucose() {
        DateFormat mediumDf = DateFormat.getDateInstance(DateFormat.MEDIUM);
        String uuidString = getString(getColumnIndex(BloodGlucoseTable.Cols.UUID));
        int breakfast = getInt(getColumnIndex(BloodGlucoseTable.Cols.BREAKFAST));
        int lunch = getInt(getColumnIndex(BloodGlucoseTable.Cols.LUNCH));
        int dinner = getInt(getColumnIndex(BloodGlucoseTable.Cols.DINNER));
        int fasting = getInt(getColumnIndex(BloodGlucoseTable.Cols.FASTING));
        String date = getString(getColumnIndex(BloodGlucoseTable.Cols.DATE));
        int isnormal = getInt(getColumnIndex(BloodGlucoseTable.Cols.NORMAL));

        Log.d("date from table::::", date);
        BloodGlucose bloodGlucose = new BloodGlucose(UUID.fromString(uuidString));
        bloodGlucose.setBreakfast(breakfast);
        bloodGlucose.setLunch(lunch);
        bloodGlucose.setDinner(dinner);
        bloodGlucose.setFasting(fasting);
        bloodGlucose.setDate(new Date(date));
        bloodGlucose.setNormal((isnormal==1));

        return bloodGlucose;
    }
}
