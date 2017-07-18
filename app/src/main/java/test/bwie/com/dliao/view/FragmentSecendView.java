package test.bwie.com.dliao.view;

import test.bwie.com.dliao.beans.FActionFriendBean;
import test.bwie.com.dliao.beans.IndexBean;

/**
 * Created by lenovo-pc on 2017/7/16.
 */

public interface FragmentSecendView {
    void success(FActionFriendBean indexBean);
    void failed(int code);
}
