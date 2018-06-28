package edu.arizona.uas.jefferychang.hw3;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.widget.CheckBox;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;
import java.util.UUID;

public class BloodGlucoseFragment extends Fragment {
    private static final String ARG_BloodGlucose_ID = "BloodGlucose_id";
    private static final String DIALOG_DATE = "DialogDate";
    private static final int REQUEST_DATE = 0;
    Upload mUpload;
    BloodGlucose mBloodGlucose;
    Button mClear, mHistory;
    EditText mFasting, mBreakfast, mLunch, mDinner, mNote;
    TextView mResult;
    private Button mDateButton;
    private CheckBox mNormalCheckbox;
    DateFormat mediumDf = DateFormat.getDateInstance(DateFormat.MEDIUM);

    public static BloodGlucoseFragment newInstance(UUID id) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_BloodGlucose_ID, id);

        BloodGlucoseFragment fragment = new BloodGlucoseFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID BloodGlucose_id = (UUID) getArguments().getSerializable(ARG_BloodGlucose_ID);
        mBloodGlucose = BloodGlucoseHistory.get(getActivity()).getBloodGlucose(BloodGlucose_id);
        if (mBloodGlucose==null){
            mBloodGlucose = new BloodGlucose();
        }
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bloodglucose, container, false);
        Date date = new Date();
        DateFormat mediumDf = DateFormat.getDateInstance(DateFormat.MEDIUM);

        mFasting=v.findViewById(R.id.Fasting_Edit);
        mBreakfast=v.findViewById(R.id.Breakfast_Edit);
        mLunch=v.findViewById(R.id.Lunch_Edit);
        mDinner=v.findViewById(R.id.Dinner_Edit);
        mNote=v.findViewById(R.id.Note_Edit);
        mDateButton=v.findViewById(R.id.Date);
        mResult=v.findViewById(R.id.Result);
        mNormalCheckbox=v.findViewById(R.id.Normal_Check);
        mHistory = v.findViewById(R.id.History);
        mClear=v.findViewById(R.id.Clear);
        try{
            mResult.setText(mBloodGlucose.getResult());
            mFasting.setText(String.valueOf(mBloodGlucose.getFasting()));
            mBreakfast.setText(String.valueOf(mBloodGlucose.getBreakfast()));
            mLunch.setText(String.valueOf(mBloodGlucose.getLunch()));
            mDinner.setText(String.valueOf(mBloodGlucose.getDinner()));
            mDateButton.setText(mediumDf.format(mBloodGlucose.getDate()));
            mNormalCheckbox.setChecked(mBloodGlucose.ismNormal());
        }
        catch (Exception e){
            Date today = new Date();
            mResult.setText("Please Input the data");
            mDateButton.setText(mediumDf.format(today));
            mNormalCheckbox.setChecked(false);
        }

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



        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                try{
                    DatePickerFragment dialog = DatePickerFragment
                            .newInstance(mBloodGlucose.getDate());
                    dialog.setTargetFragment(BloodGlucoseFragment.this, REQUEST_DATE);
                    dialog.show(manager, DIALOG_DATE);
                }
                catch (Exception e){
                    Date temptime = new Date();
                    DatePickerFragment dialog = DatePickerFragment
                            .newInstance(temptime);
                    dialog.setTargetFragment(BloodGlucoseFragment.this, REQUEST_DATE);
                    dialog.show(manager, DIALOG_DATE);
                }
            }
        });

        mNormalCheckbox = (CheckBox) v.findViewById(R.id.Normal_Check);

        mNormalCheckbox.setClickable(false);

        mHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateHistory();
                Intent intent = new Intent();
                intent.setClass(getActivity(), BloodGlucoseHistoryActivity.class);
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
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_item, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), BloodGlucoseHistoryActivity.class);
        switch (item.getItemId()) {
            case R.id.delete_record:
                BloodGlucoseHistory.get(getActivity()).deleteBloodGlucose(mBloodGlucose);
                startActivity(intent);
                return true;
            case R.id.upload_record:
                new UploadItemsTask().execute();
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data
                    .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mBloodGlucose.setDate(date);
            mDateButton.setText(mediumDf.format(mBloodGlucose.getDate()));
        }
    }
    private void updateHistory() {
        BloodGlucoseHistory history = BloodGlucoseHistory.get(getActivity());
        BloodGlucose obj;
        obj = history.getBloodGlucoseByDate(mBloodGlucose.getDate());
        if (obj == null) {
            history.updateBloodGlucose(mBloodGlucose);
        }
        else if (obj.getId().equals(mBloodGlucose.getId())){
            history.updateBloodGlucose(mBloodGlucose);
        }
        else{
            history.deleteBloodGlucose(obj);
            history.updateBloodGlucose(mBloodGlucose);
        }
    }
    private class UploadItemsTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            mUpload = new Upload();
            return mUpload.upload(mBloodGlucose);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if(result){
                Toast toast = Toast.makeText(getContext(),
                        "Upload Successfully!!", Toast.LENGTH_SHORT);
                toast.show();
            }
            else{
                Toast toast = Toast.makeText(getContext(),
                        "Upload Fail!!", Toast.LENGTH_SHORT);
                toast.show();
            }

        }
    }


}
