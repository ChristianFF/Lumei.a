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

package com.ff.lumeia.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ff.lumeia.LumeiaConfig;
import com.ff.lumeia.R;
import com.ff.lumeia.model.entity.Gank;
import com.ff.lumeia.ui.WebActivity;

import java.io.Serializable;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by feifan on 16/1/29.
 * Contacts me:404619986@qq.com
 */
public class GankAdapter extends RecyclerView.Adapter<GankAdapter.GankViewHolder> {

    private List<Gank> gankList;
    private Context context;

    public GankAdapter(List<Gank> gankList, Context context) {
        this.context = context;
        this.gankList = gankList;
    }

    @Override
    public GankViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_gank, parent, false);
        return new GankViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GankViewHolder holder, int position) {
        holder.itemView.setTag(gankList.get(position));
        String title = gankList.get(position).desc + " @" + gankList.get(position).who;
        holder.textTitle.setText(title);
    }

    @Override
    public int getItemCount() {
        return gankList == null ? 0 : gankList.size();
    }

    class GankViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.text_title)
        TextView textTitle;

        @OnClick(R.id.layout_gank)
        void gankClick() {
            Intent intent = new Intent(context, WebActivity.class);
            intent.putExtra(LumeiaConfig.GANK, (Serializable) itemView.getTag());
            context.startActivity(intent);
        }

        public GankViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
