package com.ff.lumeia.ui.gank;

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
 * Created by feifan on 16/1/29.
 * Contacts me:404619986@qq.com
 */
class GankAdapter extends RecyclerView.Adapter<GankAdapter.GankViewHolder> {

    private List<Gank> gankList;
    private Context context;

    GankAdapter(List<Gank> gankList, Context context) {
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
        Gank gank = gankList.get(position);
        holder.itemView.setTag(gank);
        holder.textTitle.setText(gank.desc);
    }

    @Override
    public int getItemCount() {
        return gankList == null ? 0 : gankList.size();
    }

    class GankViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle;

        GankViewHolder(View itemView) {
            super(itemView);
            textTitle = (TextView) itemView.findViewById(R.id.title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(WebActivity.createIntent(context,
                            ((Gank) itemView.getTag()).desc,
                            ((Gank) itemView.getTag()).url));
                }
            });
        }
    }
}
