package test.bwie.com.dliao.view;


import test.bwie.com.dliao.beans.IndexBean;

public interface FragmentFindView {



    void success(IndexBean indexBean, int page);
    void failed(int code, int page);

}
