package com.ff.lumeia.ui.daily;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ff.lumeia.R;
import com.ff.lumeia.model.entity.Gank;
import com.ff.lumeia.ui.web.WebActivity;

import java.util.List;

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
        Gank gank = gankList.get(position + 1);
        holder.itemView.setTag(gank);
        if (position == 0) {
            showCategory(true, holder.textCategory);
        } else {
            if (gank.type.equals(gankList.get(position).type)) {
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
        return gankList == null ? 0 : gankList.size() - 1;
    }

    class DailyGankViewHolder extends RecyclerView.ViewHolder {
        TextView textCategory;
        TextView textTitle;

        void cardClick() {
            context.startActivity(WebActivity.createIntent(context,
                    ((Gank) itemView.getTag()).desc,
                    ((Gank) itemView.getTag()).url));
        }

        public DailyGankViewHolder(View itemView) {
            super(itemView);
            textCategory = (TextView) itemView.findViewById(R.id.text_category);
            textTitle = (TextView) itemView.findViewById(R.id.text_title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cardClick();
                }
            });
        }
    }

}
