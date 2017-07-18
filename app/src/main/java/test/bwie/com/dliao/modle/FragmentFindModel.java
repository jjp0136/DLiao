package test.bwie.com.dliao.modle;

import test.bwie.com.dliao.beans.IndexBean;

/**
 * Created by lenovo-pc on 2017/7/12.
 */

public interface FragmentFindModel {

     void getData(int page,DataListener dataListener);

     interface DataListener{
         void onSuccess(IndexBean indexBean, int page);
         void onFailed(int code,int page);
    }
}
