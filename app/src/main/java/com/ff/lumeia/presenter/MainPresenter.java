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
import com.litesuits.orm.db.model.ConflictAlgorithm;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func2;
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
        queryBuilder.appendOrderDescBy("publishedAt");
        queryBuilder.limit(0, 10);
        meiziList.addAll(LumeiaApp.myDatabase.query(queryBuilder));
    }

    public void requestMeiziData(int page) {
        iView.showProgress();
        subscription = Observable.zip(MyRetrofitClient.getGankServiceInstance().getMeiziData(page),
                MyRetrofitClient.getGankServiceInstance().getRestingVideoData(page),
                new Func2<MeiziData, RestingVideoData, MeiziData>() {
                    @Override
                    public MeiziData call(MeiziData meiziData, RestingVideoData restingVideoData) {
                        return createMeiziDataWithRestingVideoDesc(meiziData, restingVideoData);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<MeiziData>() {
                    @Override
                    public void call(MeiziData meiziData) {
                        if (meiziData.results.size() == 0) {
                            iView.showNoMoreData();
                        } else {
                            saveMeiziData(meiziData);
                            iView.showMeiziList(meiziData.results);
                        }
                        iView.hideProgress();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (BuildConfig.DEBUG) {
                            throwable.printStackTrace();
                        }
                        iView.showErrorView();
                        iView.hideProgress();
                    }
                });
    }

    private void saveMeiziData(MeiziData meiziData) {
        LumeiaApp.myDatabase.insert(meiziData.results, ConflictAlgorithm.Replace);
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
        subscription.unsubscribe();
    }


}
