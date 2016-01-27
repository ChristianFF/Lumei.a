package com.ff.lumeia.ui.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ff.lumeia.R;
import com.ff.lumeia.model.entity.Meizi;
import com.ff.lumeia.util.DateUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by feifan on 16/1/27.
 * Contacts me:404619986@qq.com
 */
public class MeiziAdapter extends RecyclerView.Adapter<MeiziAdapter.MeiziViewHolder> {

    private List<Meizi> meiziList;
    private Context context;

    private int lastPosition = 0;

    public MeiziAdapter(Context context, List<Meizi> meiziList) {
        this.context = context;
        this.meiziList = meiziList;
    }

    @Override
    public MeiziViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meizi, parent, false);
        return new MeiziViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MeiziViewHolder holder, int position) {
        final Meizi meizi = meiziList.get(position);
        holder.itemView.setTag(meizi);

        preloadImgColor(holder);

        Glide.with(context)
                .load(meizi.url)
                .crossFade()
                .into(holder.imgMeizi);

        holder.textAuthor.setText(meizi.who);
        holder.textDate.setText(DateUtils.convertDateToString(meizi.publishedAt));
        holder.textDesc.setText(meizi.desc);

        showItemAnimation(holder, position);
    }

    private void preloadImgColor(MeiziViewHolder holder) {
        int red = (int) (Math.random() * 255);
        int green = (int) (Math.random() * 255);
        int blue = (int) (Math.random() * 255);
        holder.imgMeizi.setBackgroundColor(Color.rgb(red, green, blue));
    }

    private void showItemAnimation(MeiziViewHolder holder, int position) {
        if (position > lastPosition) {
            lastPosition = position;
            ObjectAnimator.ofFloat(holder.itemView, "translationY", 1f * holder.itemView.getHeight(), 0f)
                    .setDuration(500)
                    .start();
        }
    }

    @Override
    public int getItemCount() {
        return meiziList == null ? 0 : meiziList.size();
    }

    static class MeiziViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.img_meizi)
        ImageView imgMeizi;
        @Bind(R.id.text_author)
        TextView textAuthor;
        @Bind(R.id.text_date)
        TextView textDate;
        @Bind(R.id.text_desc)
        TextView textDesc;
        @Bind(R.id.layout_text)
        RelativeLayout layoutText;

        @OnClick(R.id.img_meizi)
        void onMeiziClick() {
            //TODO go to meizi activity
        }

        @OnClick(R.id.layout_text)
        void textClick() {
            //TODO go to gank activity
        }

        public MeiziViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
