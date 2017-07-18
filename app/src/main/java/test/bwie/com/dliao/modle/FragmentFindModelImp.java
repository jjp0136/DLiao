package test.bwie.com.dliao.modle;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.HashMap;
import java.util.Map;

import test.bwie.com.dliao.application.MyApplication;
import test.bwie.com.dliao.beans.IndexBean;
import test.bwie.com.dliao.daoutils.FragmentFindDaoUtils;
import test.bwie.com.dliao.network.BaseObserver;
import test.bwie.com.dliao.network.RetrofitManager;
import test.bwie.com.dliao.utils.Constants;

import static test.bwie.com.dliao.application.MyApplication.daosession;

/**
 * Created by lenovo-pc on 2017/7/12.
 */

public class FragmentFindModelImp implements FragmentFindModel {

    @Override
    public void getData(final int page, final DataListener listener) {
        long time=System.currentTimeMillis();
        Map<String,String> map = new HashMap<String, String>();
        map.put("user.currenttimer",time+"");
        RetrofitManager.post(Constants.ALL_USER, map, new BaseObserver() {
            @Override
            public void onSuccess(String result) {

                try {
                    Gson gson = new Gson();
                    IndexBean indexBean = gson.fromJson(result, IndexBean.class);
                    //daosession.getDataBeanDao().insertInTx(indexBean.getData());
                    FragmentFindDaoUtils.insert(indexBean.getData());
                    listener.onSuccess(indexBean, page);

                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int code) {
                listener.onFailed(code,page);
            }
        });
    }
}