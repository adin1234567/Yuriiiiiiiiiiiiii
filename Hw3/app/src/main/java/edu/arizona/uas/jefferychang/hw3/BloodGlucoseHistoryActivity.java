package edu.arizona.uas.jefferychang.hw3;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;

public class BloodGlucoseHistoryActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new BloodGlucoseListFragment();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

}