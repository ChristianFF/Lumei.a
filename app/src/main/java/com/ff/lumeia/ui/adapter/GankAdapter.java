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
