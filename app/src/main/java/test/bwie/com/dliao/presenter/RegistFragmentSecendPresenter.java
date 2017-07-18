package test.bwie.com.dliao.presenter;

import android.widget.Toast;

import test.bwie.com.dliao.application.MyApplication;
import test.bwie.com.dliao.base.BasePresenter;
import test.bwie.com.dliao.beans.RegisterBean;
import test.bwie.com.dliao.modle.RegistFragmentSecendModelImp;
import test.bwie.com.dliao.modle.RegistFragmentSecendModle;
import test.bwie.com.dliao.view.RegistFragmentSecendView;

/**
 * Created by lenovo-pc on 2017/7/6.
 */

public class RegistFragmentSecendPresenter extends BasePresenter<RegistFragmentSecendView> {

    private RegistFragmentSecendModle registFragmentSecendModle;

    public RegistFragmentSecendPresenter() {
        registFragmentSecendModle = new RegistFragmentSecendModelImp();
    }

    public void vaildInfor(String phone, String nickname, String sex, String age, String area, String introduce, String password) {
        //非空判断
        registFragmentSecendModle.getData(phone, nickname, sex, age, area, introduce, password, new RegistFragmentSecendModle.RegisterInforFragmentDataListener() {
            @Override
            public void onSuccess(RegisterBean registerBean) {
                view.registerSuccess(registerBean);

            }
            @Override
            public void onFailed(int code) {
                view.registerFailed(code);
            }
        });
    }
}
