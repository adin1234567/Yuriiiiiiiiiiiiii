package edu.arizona.uas.jefferychang.hw3;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.content.Intent;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.util.Log;
import android.app.DialogFragment;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.text.DateFormat;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String ARG_BloodGlucose_ID = "BloodGlucose_id";
    private static final String DIALOG_DATE = "DialogDate";
    BloodGlucose mBloodGlucose = new BloodGlucose();
    Button mHistory, mClear;
    EditText mFasting, mBreakfast, mLunch, mDinner, mNote;
    TextView mResult;
    private Button mDateButton;
    private CheckBox mNormalCheckbox;
    private static final String EXTRA_BloodGlucose_ID =
            "edu.arizona.uas.jefferychang.hw3.BloodGlucose_id";

    public static Intent newIntent(Context packageContext, UUID BloodGlucose_id) {
        Intent intent = new Intent(packageContext, MainActivity.class);
        intent.putExtra(EXTRA_BloodGlucose_ID, BloodGlucose_id);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Date today = new Date();
        DateFormat mediumDf = DateFormat.getDateInstance(DateFormat.MEDIUM);

        mFasting=findViewById(R.id.Fasting_Edit);
        mBreakfast=findViewById(R.id.Breakfast_Edit);
        mLunch=findViewById(R.id.Lunch_Edit);
        mDinner=findViewById(R.id.Dinner_Edit);
        mNote=findViewById(R.id.Note_Edit);
        mDateButton=findViewById(R.id.Date);
        mResult=findViewById(R.id.Result);
        mNormalCheckbox=findViewById(R.id.Normal_Check);
        mHistory = findViewById(R.id.History);
        mClear=findViewById(R.id.Clear);

        mDateButton.setText(mediumDf.format(today));
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getSupportFragmentManager();
                DatePickerFragment dialog = DatePickerFragment
                        .newInstance(mBloodGlucose.getDate());
                dialog.show(manager, DIALOG_DATE);
            }
        });


        mFasting.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String temp = mFasting.getText().toString();
                if(!temp.equals("")){
                    mBloodGlucose.setFasting(Integer.parseInt(temp));
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
                mBloodGlucose.checkNormal();
                mNormalCheckbox.setChecked(mBloodGlucose.ismNormal());
                mResult.setText(mBloodGlucose.getResult());
            }
        });
        mBreakfast.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String temp = mBreakfast.getText().toString();
                if(!temp.equals("")){
                    mBloodGlucose.setBreakfast(Integer.parseInt(temp));
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
                mBloodGlucose.checkNormal();
                mNormalCheckbox.setChecked(mBloodGlucose.ismNormal());
                mResult.setText(mBloodGlucose.getResult());
            }
        });
        mLunch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String temp = mLunch.getText().toString();
                if(!temp.equals("")){
                    mBloodGlucose.setLunch(Integer.parseInt(temp));
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
                mBloodGlucose.checkNormal();
                mNormalCheckbox.setChecked(mBloodGlucose.ismNormal());
                mResult.setText(mBloodGlucose.getResult());
            }
        });
        mDinner.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String temp = mDinner.getText().toString();
                if(!temp.equals("")){
                    mBloodGlucose.setDinner(Integer.parseInt(temp));
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
                mBloodGlucose.checkNormal();
                mNormalCheckbox.setChecked(mBloodGlucose.ismNormal());
                mResult.setText(mBloodGlucose.getResult());
            }
        });
        mNote.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String temp = mNote.getText().toString();
                if(!temp.equals(null)) {mBloodGlucose.setNote(temp);}
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mNormalCheckbox.setChecked(false);
        mNormalCheckbox.setClickable(false);

        mHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateHistory();
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, BloodGlucoseHistoryActivity.class);
                startActivity(intent);
            }
        });

        mClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFasting.setText("");
                mBreakfast.setText("");
                mLunch.setText("");
                mDinner.setText("");
                mNote.setText("");
            }
        });
    }
    private void updateHistory() {
        BloodGlucoseHistory history = BloodGlucoseHistory.get(this);
        BloodGlucose obj = history.getBloodGlucoseByDate(mBloodGlucose.getDate());
        if(obj==null){
            history.addBloodGlucose(mBloodGlucose);
        }
        else{
            history.updateBloodGlucosebyDate(mBloodGlucose);
        }
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }


}
