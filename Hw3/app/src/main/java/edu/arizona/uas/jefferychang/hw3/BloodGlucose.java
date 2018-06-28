package edu.arizona.uas.jefferychang.hw3;

import android.util.Log;

import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class BloodGlucose {
    private UUID mId;
    private int mBreakfast;
    private int mLunch;
    private int mDinner;
    private int mFasting;
    private Date mDate;
    private boolean mNormal = false;
    private String mNote;
    private Map<String, String> mDict = new HashMap<String, String>();


    public BloodGlucose() {
        mId = UUID.randomUUID();
        mDate = new Date();
        mDict.put("Breakfast", "Normal");
        mDict.put("Lunch", "Normal");
        mDict.put("Dinner", "Normal");
        mDict.put("Fasting", "Normal");
    }
    public BloodGlucose(UUID id){
        mId = id;
        mDate = new Date();
        mDict.put("Breakfast", "Normal");
        mDict.put("Lunch", "Normal");
        mDict.put("Dinner", "Normal");
        mDict.put("Fasting", "Normal");
    }
    public UUID getId() {
        return mId;
    }

    public int getBreakfast() {
        return mBreakfast;
    }

    public void setBreakfast(int Breakfast) {
        mBreakfast = Breakfast;
        mDict.put("Breakfast", checkStatus("Breakfast", Breakfast));

    }
    public int getLunch() {
        return mLunch;
    }

    public void setLunch(int Lunch) {
        mLunch = Lunch;
        mDict.put("Lunch", checkStatus("Lunch", Lunch));
    }
    public int getDinner() {
        return mDinner;
    }

    public void setDinner(int Dinner) {
        mDinner = Dinner;
        mDict.put("Dinner", checkStatus("Dinner", Dinner));
    }
    public int getFasting() {
        return mFasting;
    }

    public void setFasting(int Fasting) {
        mFasting = Fasting;
        mDict.put("Fasting", checkStatus("Fasting", Fasting));
    }
    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean ismNormal() {
        return mNormal;
    }

    public void setNormal(boolean Normal) {
        mNormal = Normal;
    }

    public String getNote() {
        return mNote;
    }

    public void setNote(String Note) {
        mNote = Note;
    }

    public String checkStatus(String meal, int BloodGlucose){
        if(BloodGlucose < 70){
            return "Hypoglycemic";
        }
        else if (meal.equals("Fasting")){
            if(BloodGlucose > 99){
                return "Abnormal";
            }
            else{
                return "Normal";
            }
        }
        else{
            if(BloodGlucose >= 140){
                return "Abnormal";
            }
            else{
                return "Normal";
            }
        }
    }
    public void checkNormal(){
        if(((mDict.get("Breakfast").equals("Normal"))&&
                (mDict.get("Lunch").equals("Normal"))&&
                    (mDict.get("Dinner").equals("Normal"))&&
                        (mDict.get("Fasting").equals("Normal")))){
            setNormal(true);
        }
        else{
            setNormal(false);
        }
    }

    public String getResult(){
        String B = "[Breakfast: " + mDict.get("Breakfast").toString() +"] ";
        String L = "[Lunch: " + mDict.get("Lunch").toString() + "] ";
        String D = "[Dinner: " + mDict.get("Dinner").toString()+"] ";
        String F = "[Fasting: " + mDict.get("Fasting").toString()+"] ";

        return B+L+"\n"+D+F;
    }
    public JSONObject toJSON(){
        JSONObject js = new JSONObject();
        try{
            js.put("ID", mId.toString());
            js.put("Date",mDate.toString());
            js.put("Breakfast",String.valueOf(mBreakfast));
            js.put("Lunch",String.valueOf(mLunch));
            js.put("Dinner",String.valueOf(mDinner));
            js.put("Fasting",String.valueOf(mFasting));
            js.put("Note",mNote);
            js.put("Normal",String.valueOf(mNormal));
        }
        catch (Exception e){
            Log.e("JSON EXCEPTION!!!!",e.toString());
        }
        return js;
    }
}
