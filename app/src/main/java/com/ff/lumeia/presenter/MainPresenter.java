package com.ff.lumeia.presenter;

import android.content.Context;

import com.ff.lumeia.BuildConfig;
import com.ff.lumeia.LumeiaApp;
import com.ff.lumeia.model.MeiziData;
import com.ff.lumeia.model.RestingVideoData;
import com.ff.lumeia.model.entity.Meizi;
import com.ff.lumeia.net.MyRetrofitClient;
import com.ff.lumeia.view.IMainView;
import com.litesuits.orm.db.assit.QueryBuilder;

import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 主界面presenter
 * Created by feifan on 16/1/26.
 * Contacts me:404619986@qq.com
 */
public class MainPresenter extends BasePresenter<IMainView> {

    public MainPresenter(IMainView iView, Context context) {
        super(iView, context);
    }

    public void loadMeiziDataFromDB(List<Meizi> meiziList) {
        if (meiziList == null) {
            throw new RuntimeException("List is null!");
        }
        QueryBuilder<Meizi> queryBuilder = new QueryBuilder<>(Meizi.class);
        queryBuilder.appendOrderDescBy("PublishedDate");
        queryBuilder.limit(0, 10);
        meiziList.addAll(LumeiaApp.myDatabase.query(queryBuilder));
    }

    public void requestMeiziData(int page, boolean clean) {
        iView.showProgressBar();
        subscription = Observable.zip(MyRetrofitClient.getGankServiceInstance().getMeiziData(page),
                MyRetrofitClient.getGankServiceInstance().getRestingVideoData(page),
                (meiziData, restingVideoData) -> {
                    saveMeiziData(meiziData);
                    return createMeiziDataWithRestingVideoDesc(meiziData, restingVideoData);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meiziData -> {
                    iView.hideProgressBar();
                    if (meiziData.results.size() == 0) {
                        iView.showNoMoreData();
                    } else {
                        iView.showMeiziList(meiziData.results, clean);
                    }
                }, throwable -> {
                    if (BuildConfig.DEBUG) {
                        throwable.printStackTrace();
                    }
                    iView.hideProgressBar();
                    iView.showErrorView();
                });
    }

    private void saveMeiziData(MeiziData meiziData) {
        Collections.sort(meiziData.results, (meizi, t1) -> t1.publishedAt.compareTo(meizi.publishedAt));
        LumeiaApp.myDatabase.save(meiziData.results);
    }

    private MeiziData createMeiziDataWithRestingVideoDesc(MeiziData meiziData, RestingVideoData restingVideoData) {
        int size = Math.min(meiziData.results.size(), restingVideoData.results.size());
        for (int i = 0; i < size; i++) {
            meiziData.results.get(i).desc = meiziData.results.get(i).desc + "，"
                    + restingVideoData.results.get(i).desc;
            meiziData.results.get(i).who = restingVideoData.results.get(i).who;
        }
        return meiziData;
    }

    @Override
    public void release() {
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }


}
