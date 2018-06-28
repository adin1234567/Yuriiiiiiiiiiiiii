package edu.arizona.uas.jefferychang.hw3;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.UUID;

public class WebPageActivity extends SingleFragmentActivity {
    @Override
    protected android.support.v4.app.Fragment createFragment() {
        return WebPageFragment.newInstance();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
