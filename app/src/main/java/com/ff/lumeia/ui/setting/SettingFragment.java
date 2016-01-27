package com.ff.lumeia.ui.setting;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;

import com.ff.lumeia.R;
import com.ff.lumeia.util.DailyReminderUtils;
import com.ff.lumeia.util.SharedPreferenceUtils;
import com.google.common.base.Preconditions;

/**
 * Created by feifan on 2017/2/28.
 * Contacts me:404619986@qq.com
 */

public class SettingFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener, SettingContract.View {
    private SwitchPreference dailyPreference;
    private SettingContract.Presenter mPresenter;

    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting_preference);

        dailyPreference = (SwitchPreference) findPreference("daily_reminder");
        boolean isOpen = SharedPreferenceUtils.getBoolean("daily_reminder", true);
        dailyPreference.setChecked(isOpen);
        dailyPreference.setOnPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object o) {
        if (preference == dailyPreference) {
            boolean isOpen = (boolean) o;
            if (isOpen) {
                DailyReminderUtils.register();
            }
            SharedPreferenceUtils.putBoolean("daily_reminder", isOpen);
            dailyPreference.setChecked(isOpen);
            return true;
        }
        return false;
    }

    @Override
    public void setPresenter(SettingContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);
    }
}
