package test.bwie.com.dliao.modle;

import cn.smssdk.SMSSDK;

/**
 * Created by lenovo-pc on 2017/7/6.
 */

public class RegistFragmentFirstmodleImp implements RegistFragmentFirstmodle {
    // TODO: 2017/7/6 验证地区
    public void getVerificationCode(String phone) {
        SMSSDK.getVerificationCode("86", phone);
    }
}
