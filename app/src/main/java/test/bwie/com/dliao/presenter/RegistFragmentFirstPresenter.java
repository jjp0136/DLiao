package test.bwie.com.dliao.presenter;

import android.text.TextUtils;

import test.bwie.com.dliao.base.BasePresenter;
import test.bwie.com.dliao.modle.RegistFragmentFirstmodleImp;
import test.bwie.com.dliao.utils.PhoneCheckUtils;
import test.bwie.com.dliao.view.RegistFragmentFistView;

/**
 * Created by lenovo-pc on 2017/7/6.
 */

public class RegistFragmentFirstPresenter extends BasePresenter<RegistFragmentFistView> {

    private RegistFragmentFirstmodleImp registFragmentFirstmodleImp;

    public RegistFragmentFirstPresenter(){
        registFragmentFirstmodleImp=new RegistFragmentFirstmodleImp();
    }
    public void getVerificationCode(String phone){
        if(TextUtils.isEmpty(phone)){
            view.phoneError(1);
            return;
        }
        if(!PhoneCheckUtils.isChinaPhoneLegal(phone)){
            view.phoneError(2);
            return;
        }
        registFragmentFirstmodleImp.getVerificationCode(phone);
        view.showTimer();
    }

    public void nextStep(String phone,String sms){
        if(TextUtils.isEmpty(phone)){
            view.phoneError(1);
            return;
        }
        if(!PhoneCheckUtils.isChinaPhoneLegal(phone)){
            view.phoneError(2);
            return;
        }
        //判断验证码是否合法  验证码是4为数字 \\d{4} sms 非空

        if (!PhoneCheckUtils.isHKPhonecard(sms)){

            view.phoneError(3);
            return;

        }

        view.toNextPage();

    }
}
