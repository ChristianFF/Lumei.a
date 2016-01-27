package com.ff.lumeia.ui.main;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ff.lumeia.R;
import com.ff.lumeia.model.entity.Meizi;
import com.ff.lumeia.ui.daily.DailyActivity;
import com.ff.lumeia.ui.picture.PictureActivity;
import com.ff.lumeia.util.DateUtils;

import java.util.List;

/**
 * Created by feifan on 16/1/27.
 * Contacts me:404619986@qq.com
 */
public class MeiziAdapter extends RecyclerView.Adapter<MeiziAdapter.MeiziViewHolder> {
    private List<Meizi> mMeiziList;
    private int lastPosition = 0;

    public MeiziAdapter(List<Meizi> meiziList) {
        this.mMeiziList = meiziList;
    }

    @Override
    public MeiziViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meizi, parent, false);
        return new MeiziViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MeiziViewHolder holder, int position) {
        holder.bind(position, mMeiziList.get(position));
    }

    @Override
    public int getItemCount() {
        return mMeiziList == null ? 0 : mMeiziList.size();
    }

    class MeiziViewHolder extends RecyclerView.ViewHolder {
        ImageView imgMeizi;
        TextView textAuthor;
        TextView textDate;
        TextView textDesc;
        RelativeLayout mTextLayout;

        MeiziViewHolder(View itemView) {
            super(itemView);
            imgMeizi = (ImageView) itemView.findViewById(R.id.img_meizi);
            textAuthor = (TextView) itemView.findViewById(R.id.text_author);
            textDate = (TextView) itemView.findViewById(R.id.text_date);
            textDesc = (TextView) itemView.findViewById(R.id.text_desc);
            mTextLayout = (RelativeLayout) itemView.findViewById(R.id.layout_text);
            imgMeizi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onMeiziClick();
                }
            });
            mTextLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    textClick();
                }
            });
        }

        private void onMeiziClick() {
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                    .makeSceneTransitionAnimation((Activity) itemView.getContext(), imgMeizi, PictureActivity.IMG_TRANSITION_NAME);
            ActivityCompat.startActivity(itemView.getContext(),
                    PictureActivity.createIntent(itemView.getContext(), (Meizi) itemView.getTag()),
                    optionsCompat.toBundle());
        }

        private void textClick() {
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.
                    makeSceneTransitionAnimation((Activity) itemView.getContext(), imgMeizi, PictureActivity.IMG_TRANSITION_NAME);
            ActivityCompat.startActivity(itemView.getContext(),
                    DailyActivity.createIntent(itemView.getContext(), (Meizi) itemView.getTag()),
                    optionsCompat.toBundle());
        }

        void bind(int position, Meizi meizi) {
            itemView.setTag(meizi);
            preloadImgColor();

            Glide.with(itemView.getContext())
                    .load(meizi.url)
                    .crossFade()
                    .into(imgMeizi);

            if (TextUtils.isEmpty(meizi.who)) {
                textAuthor.setText("代码家");
            } else {
                textAuthor.setText(meizi.who);
            }
            if (meizi.publishedAt != null) {
                textDate.setText(DateUtils.convertDateToZhString(meizi.publishedAt));
            }
            if (!TextUtils.isEmpty(meizi.desc)) {
                textDesc.setText(meizi.desc);
            }
            showItemAnimation(position);
        }

        private void preloadImgColor() {
            int red = (int) (Math.random() * 255);
            int green = (int) (Math.random() * 255);
            int blue = (int) (Math.random() * 255);
            imgMeizi.setBackgroundColor(Color.rgb(red, green, blue));
        }

        private void showItemAnimation(int position) {
            if (position > lastPosition) {
                lastPosition = position;
                ObjectAnimator.ofFloat(itemView, "translationY", 1f * itemView.getHeight(), 0f)
                        .setDuration(500)
                        .start();
            }
        }
    }
}
