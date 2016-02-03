/*
 * Copyright (C) 2015 Drakeet <drakeet.me@gmail.com>
 * Copyright (C) 2015 GuDong <maoruibin9035@gmail.com>
 * Copyright (C) 2016 Panl <panlei106@gmail.com>
 * CopyRight (C) 2016 ChristianFF <feifan0322@gmail.com>
 *
 * Lumei.a is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Lumei.a is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Lumei.a.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.ff.lumeia.ui;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;

import com.ff.lumeia.R;
import com.ff.lumeia.presenter.AboutPresenter;
import com.ff.lumeia.ui.base.ToolbarActivity;
import com.ff.lumeia.view.IAboutView;

import butterknife.Bind;
import de.hdodenhof.circleimageview.CircleImageView;

public class AboutActivity extends ToolbarActivity<AboutPresenter> implements IAboutView {

    @Bind(R.id.img_profile)
    CircleImageView imgProfile;
    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @Bind(R.id.fab_thumb_up)
    FloatingActionButton fabThumbUp;

    private AboutPresenter aboutPresenter;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initPresenter() {
        aboutPresenter = new AboutPresenter(this, this);
        aboutPresenter.init();
    }

    @Override
    public void init() {

    }
}
