package com.ff.lumeia.ui.about;

import com.ff.lumeia.ui.BasePresenter;
import com.ff.lumeia.ui.BaseView;

/**
 * Created by feifan on 2017/2/28.
 * Contacts me:404619986@qq.com
 */

public class AboutContract {
    interface View extends BaseView<Presenter> {
        void showMyGitHub();
    }

    interface Presenter extends BasePresenter {

    }
}
