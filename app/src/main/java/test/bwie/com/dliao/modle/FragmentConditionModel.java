package test.bwie.com.dliao.modle;

import test.bwie.com.dliao.beans.FActionFriendBean;
import test.bwie.com.dliao.beans.IndexBean;

/**
 * Created by lenovo-pc on 2017/7/17.
 */

public interface FragmentConditionModel {
    void getData(DataListener dataListener);

    interface DataListener{
        void onSuccess(FActionFriendBean indexBean);
        void onFailed(int code);
    }

}
