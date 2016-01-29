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
        View view = LayoutInflater.from(context).inflate(R.layout.item_gank, parent, false);
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
