package test.bwie.com.dliao.modle;

import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import test.bwie.com.dliao.application.MyApplication;
import test.bwie.com.dliao.beans.RegisterBean;
import test.bwie.com.dliao.core.JNICore;
import test.bwie.com.dliao.core.SortUtils;
import test.bwie.com.dliao.network.BaseObserver;
import test.bwie.com.dliao.network.RetrofitManager;
import test.bwie.com.dliao.utils.PreferencesUtils;

/**
 * Created by lenovo-pc on 2017/7/6.
 */

public class RegistFragmentSecendModelImp implements RegistFragmentSecendModle {
    @Override
    public void getData(String phone, String nickname, String sex, String age, String area, String introduce, String password, final RegisterInforFragmentDataListener listener) {

        Map<String,String> map = new HashMap<String,String>();
        map.put("user.phone",phone);
        map.put("user.nickname",nickname);
        map.put("user.password",password);
        map.put("user.gender",sex);
        map.put("user.area",area);
        map.put("user.age",age);
        map.put("user.introduce",introduce);

//        String sign =  JNICore.getSign(SortUtils.getMapResult(SortUtils.sortString(map))) ;
//        map.put("user.sign",sign);


        RetrofitManager.post("http://qhb.2dyt.com/MyInterface/userAction_add.action", map, new BaseObserver() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                RegisterBean registerBean = gson.fromJson(result, RegisterBean.class);

                if(registerBean.getResult_code()==200){
                    Toast.makeText(MyApplication.myApplication,"成功",Toast.LENGTH_SHORT).show();
                    PreferencesUtils.addConfigInfo(MyApplication.getApplication(),"phone",registerBean.getData().getPhone());
                    PreferencesUtils.addConfigInfo(MyApplication.getApplication(),"password",registerBean.getData().getPassword());
                    PreferencesUtils.addConfigInfo(MyApplication.getApplication(),"yxpassword",registerBean.getData().getYxpassword());
                    PreferencesUtils.addConfigInfo(MyApplication.getApplication(),"uid",registerBean.getData().getUserId());
                    PreferencesUtils.addConfigInfo(MyApplication.getApplication(),"nickname",registerBean.getData().getNickname());
                }
                Toast.makeText(MyApplication.myApplication,registerBean.getResult_code()+"",Toast.LENGTH_SHORT).show();
                listener.onSuccess(registerBean);
            }

            @Override
            public void onFailed(int code) {
                listener.onFailed(code);
            }
        });

        System.out.println();

    }
}
