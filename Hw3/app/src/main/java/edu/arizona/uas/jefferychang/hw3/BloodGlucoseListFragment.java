package edu.arizona.uas.jefferychang.hw3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.List;


public class BloodGlucoseListFragment extends Fragment {
    private RecyclerView mBloodGlucoseRecyclerView;
    private BloodGlucoseAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        mBloodGlucoseRecyclerView = (RecyclerView) view.findViewById(R.id.BloodGlucose_recycler_view);
        mBloodGlucoseRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }
    @Override
    public void onResume(){
        super.onResume();
        updateUI();
    }
    private void updateUI() {
        BloodGlucoseHistory history = BloodGlucoseHistory.get(getActivity());
        List<BloodGlucose> BloodGlucoseData = history.getBloodGlucoseData();

        if (mAdapter == null) {
            mAdapter = new BloodGlucoseAdapter(BloodGlucoseData);
            mBloodGlucoseRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_item_list, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_record:
                BloodGlucose bloodGlucose = new BloodGlucose();
                BloodGlucoseHistory.get(getActivity()).addBloodGlucose(bloodGlucose);
                Intent intent = BloodGlucosePagerActivity
                        .newIntent(getActivity(), bloodGlucose.getId());
                startActivity(intent);
                return true;
            case R.id.view_record:
                Intent web_intent = new Intent();
                web_intent.setClass(getActivity(), WebPageActivity.class);
                startActivity(web_intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class BloodGlucoseHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private BloodGlucose mBloodGlucose;

        private CheckBox mNormalCheck;
        private TextView mFastingTextView;
        private TextView mDateTextView;



        public BloodGlucoseHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.fragment_item, parent, false));
            itemView.setOnClickListener(this);

            mFastingTextView = (TextView) itemView.findViewById(R.id.Fasting);
            mDateTextView = (TextView) itemView.findViewById(R.id.Date);
            mNormalCheck = (CheckBox) itemView.findViewById(R.id.checkBox);
            mNormalCheck.setClickable(false);
        }

        public void bind(BloodGlucose bloodGlucose) {
            DateFormat mediumDf = DateFormat.getDateInstance(DateFormat.MEDIUM);
            mBloodGlucose = bloodGlucose;
            mFastingTextView.setText(String.valueOf(mBloodGlucose.getFasting()));
            mDateTextView.setText(mediumDf.format(mBloodGlucose.getDate()));
            mNormalCheck.setChecked(mBloodGlucose.ismNormal());

        }

        @Override
        public void onClick(View view) {
            Intent intent = BloodGlucosePagerActivity.newIntent(getContext(), mBloodGlucose.getId());
            startActivity(intent);
        }
    }


    private class BloodGlucoseAdapter extends RecyclerView.Adapter<BloodGlucoseHolder> {

        private List<BloodGlucose> mBloodGlucoses;

        public BloodGlucoseAdapter(List<BloodGlucose> bloodGlucoses) {
            mBloodGlucoses = bloodGlucoses;
        }

        @Override
        public BloodGlucoseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new BloodGlucoseHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(BloodGlucoseHolder holder, int position) {
            BloodGlucose bloodGlucose = mBloodGlucoses.get(position);
            holder.bind(bloodGlucose);
        }

        @Override
        public int getItemCount() {
            return mBloodGlucoses.size();
        }
    }
}
