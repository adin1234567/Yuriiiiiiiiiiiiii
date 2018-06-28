package edu.arizona.uas.jefferychang.hw3;

import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.List;
import java.util.UUID;

    public class BloodGlucosePagerActivity extends AppCompatActivity {
        private static final String EXTRA_BloodGlucose_ID =
                "edu.arizona.uas.jefferychang.hw3.BloodGlucose_id";
        private ViewPager mViewPager;
        private List<BloodGlucose> mBloodGlucoses;

        public static Intent newIntent(Context packageContext, UUID BloodGlucose_id) {
            Intent intent = new Intent(packageContext, edu.arizona.uas.jefferychang.hw3.BloodGlucosePagerActivity.class);
            intent.putExtra(EXTRA_BloodGlucose_ID, BloodGlucose_id);
            return intent;
        }
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_blood_glucose_pager);

            UUID bloodglucoseId = (UUID) getIntent()
                    .getSerializableExtra(EXTRA_BloodGlucose_ID);

            mViewPager = (ViewPager) findViewById(R.id.bloodglucose_view_pager);

            mBloodGlucoses = BloodGlucoseHistory.get(this).getBloodGlucoseData();
            FragmentManager fragmentManager = getSupportFragmentManager();
            mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {

                @Override
                public Fragment getItem(int position) {
                    BloodGlucose bloodglucose = mBloodGlucoses.get(position);
                    return BloodGlucoseFragment.newInstance(bloodglucose.getId());
                }

                @Override
                public int getCount() {
                    return mBloodGlucoses.size();
                }
            });
            Log.d("target id::::", bloodglucoseId.toString());
            for (int i = 0; i < mBloodGlucoses.size(); i++) {
                Log.d("i::::::", String.valueOf(i));

                Log.d("List(i) ID::::",mBloodGlucoses.get(i).getId().toString());
                if (mBloodGlucoses.get(i).getId().equals(bloodglucoseId)) {
                    mViewPager.setCurrentItem(i);
                    break;
                }
            }
        }
        @Override
        public void onConfigurationChanged(Configuration newConfig) {
            super.onConfigurationChanged(newConfig);
        }
    }

