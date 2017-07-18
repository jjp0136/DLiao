package test.bwie.com.dliao.view;

import test.bwie.com.dliao.beans.RegisterBean;

/**
 * Created by lenovo-pc on 2017/7/6.
 */

public interface RegistFragmentSecendView {
    void registerSuccess(RegisterBean registerBean);
    void registerFailed(int code);
}
