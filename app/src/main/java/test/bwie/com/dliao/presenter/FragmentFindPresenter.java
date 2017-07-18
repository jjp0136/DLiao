package test.bwie.com.dliao.presenter;

import test.bwie.com.dliao.base.BasePresenter;
import test.bwie.com.dliao.beans.IndexBean;
import test.bwie.com.dliao.modle.FragmentFindModel;
import test.bwie.com.dliao.modle.FragmentFindModelImp;
import test.bwie.com.dliao.view.FragmentFindView;

/**
 * Created by lenovo-pc on 2017/7/12.
 */

public class FragmentFindPresenter extends BasePresenter<FragmentFindView> {

    private FragmentFindModel fragmentFindModel;

    public FragmentFindPresenter() {
        fragmentFindModel = new FragmentFindModelImp();
    }


    public void getData(int page) {
        fragmentFindModel.getData(page, new FragmentFindModel.DataListener() {
            @Override
            public void onSuccess(IndexBean indexBean, int page) {
                view.success(indexBean, page);
            }

            @Override
            public void onFailed(int code, int page) {
                view.failed(code, page);
            }
        });
    }
}
