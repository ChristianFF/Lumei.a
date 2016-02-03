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
import android.support.v7.widget.CardView;
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
 * Created by feifan on 16/1/28.
 * Contacts me:404619986@qq.com
 */
public class DailyGankAdapter extends AnimRecyclerViewAdapter<DailyGankAdapter.DailyGankViewHolder> {

    private Context context;
    private List<Gank> gankList;

    public DailyGankAdapter(Context context, List<Gank> gankList) {
        this.context = context;
        this.gankList = gankList;
    }

    @Override
    public DailyGankViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_gank_daily, parent, false);
        return new DailyGankViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DailyGankViewHolder holder, int position) {
        Gank gank = gankList.get(position);
        holder.cardLink.setTag(gank);
        if (position == 0) {
            showCategory(true, holder.textCategory);
        } else {
            if (gankList.get(position).type.equals(gankList.get(position - 1).type)) {
                showCategory(false, holder.textCategory);
            } else {
                showCategory(true, holder.textCategory);
            }
        }
        holder.textCategory.setText(gank.type);
        String title = gank.desc + "@" + gank.who;
        holder.textTitle.setText(title);

        showItemAnim(holder.itemView, position);
    }

    private void showCategory(boolean show, TextView textCategory) {
        if (show) {
            textCategory.setVisibility(View.VISIBLE);
        } else {
            textCategory.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return gankList == null ? 0 : gankList.size();
    }

    class DailyGankViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.text_category)
        TextView textCategory;
        @Bind(R.id.text_title)
        TextView textTitle;
        @Bind(R.id.card_link)
        CardView cardLink;

        @OnClick(R.id.card_link)
        void cardClick() {
            Intent intent = new Intent(context, WebActivity.class);
            intent.putExtra(LumeiaConfig.GANK, (Serializable) itemView.getTag());
            context.startActivity(intent);
        }

        public DailyGankViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
