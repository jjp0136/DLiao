package test.bwie.com.dliao.modle;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.HashMap;
import java.util.Map;

import test.bwie.com.dliao.beans.FActionFriendBean;
import test.bwie.com.dliao.beans.IndexBean;
import test.bwie.com.dliao.daoutils.FragmentFindDaoUtils;
import test.bwie.com.dliao.network.BaseObserver;
import test.bwie.com.dliao.network.RetrofitManager;
import test.bwie.com.dliao.utils.Constants;

/**
 * Created by lenovo-pc on 2017/7/17.
 */

public class FragmentConditionModelImp implements FragmentConditionModel {
    @Override
    public void getData(final DataListener dataListener) {
        long time=System.currentTimeMillis();
        Map<String,String> map = new HashMap<String, String>();
        map.put("user.currenttimer",time+"");
        RetrofitManager.post(Constants.FRIENDS, map, new BaseObserver() {
            @Override
            public void onSuccess(String result) {

                try {
                    Gson gson = new Gson();
                    FActionFriendBean indexBean = gson.fromJson(result, FActionFriendBean.class);
                    dataListener.onSuccess(indexBean);

                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int code) {
                dataListener.onFailed(code);
            }
        });
    }
}
