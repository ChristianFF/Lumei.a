package com.ff.lumeia.ui.setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.ff.lumeia.R;
import com.ff.lumeia.ui.BaseActivity;

public class SettingActivity extends BaseActivity {
    private SettingPresenter mPresenter;

    public static Intent createIntent(Context context) {
        return new Intent(context, SettingActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SettingFragment settingFragment = (SettingFragment) getFragmentManager().findFragmentById(R.id.setting_container);
        if (settingFragment == null) {
            settingFragment = SettingFragment.newInstance();
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.setting_container, settingFragment)
                    .commit();
        }

        mPresenter = new SettingPresenter(settingFragment);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_setting;
    }
}
