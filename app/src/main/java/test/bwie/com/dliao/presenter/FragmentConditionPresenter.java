package test.bwie.com.dliao.presenter;

import test.bwie.com.dliao.base.BasePresenter;
import test.bwie.com.dliao.beans.FActionFriendBean;
import test.bwie.com.dliao.beans.IndexBean;
import test.bwie.com.dliao.modle.FragmentConditionModel;
import test.bwie.com.dliao.modle.FragmentConditionModelImp;
import test.bwie.com.dliao.modle.FragmentFindModelImp;
import test.bwie.com.dliao.view.FragmentSecendView;

/**
 * Created by lenovo-pc on 2017/7/17.
 */

public class FragmentConditionPresenter extends BasePresenter<FragmentSecendView> {

    private FragmentConditionModel fragmentFindModel;

    public FragmentConditionPresenter() {
        fragmentFindModel = new FragmentConditionModelImp();
    }

    public void getData() {
        fragmentFindModel.getData(new FragmentConditionModel.DataListener() {
            @Override
            public void onSuccess(FActionFriendBean indexBean) {
                view.success(indexBean);
            }

            @Override
            public void onFailed(int code) {
                view.failed(code);
            }
        });
    }
}
