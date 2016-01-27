package com.ff.lumeia.ui.about;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.TextView;

import com.ff.lumeia.BuildConfig;
import com.ff.lumeia.R;
import com.ff.lumeia.ui.BaseActivity;
import com.ff.lumeia.ui.web.WebActivity;
import com.google.common.base.Preconditions;

public class AboutActivity extends BaseActivity implements AboutContract.View {
    private TextView mTextVersion;
    private FloatingActionButton mFabThumbUp;
    private AboutContract.Presenter mPresenter;

    public static Intent createIntent(Context context) {
        return new Intent(context, AboutActivity.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        mPresenter = new AboutPresenter(this);

        mTextVersion = (TextView) findViewById(R.id.text_version);
        mFabThumbUp = (FloatingActionButton) findViewById(R.id.fab_thumb_up);
        mFabThumbUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMyGitHub();
            }
        });
        mTextVersion.setText(String.format("Version: %s", BuildConfig.VERSION_NAME));
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_about;
    }

    @Override
    public void showMyGitHub() {
        startActivity(WebActivity.createIntent(this, getString(R.string.app_name), getString(R.string.github_lumeia)));
    }

    @Override
    public void setPresenter(AboutContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);
    }
}
