package test.bwie.com.dliao.modle;

import test.bwie.com.dliao.beans.RegisterBean;

/**
 * Created by lenovo-pc on 2017/7/6.
 */

public interface RegistFragmentSecendModle {
    void getData(String phone, String nickname, String sex, String age, String area, String introduce, String password, RegisterInforFragmentDataListener listener);

    interface RegisterInforFragmentDataListener {


        void onSuccess(RegisterBean registerBean);

        void onFailed(int code);

    }
}
