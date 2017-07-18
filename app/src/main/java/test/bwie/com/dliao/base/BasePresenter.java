package test.bwie.com.dliao.base;

/**
 * Created by lenovo-pc on 2017/7/5.
 */

public class BasePresenter<T> {

    public T view;

    public void attach(T view) {
        this.view = view;
    }
    public void deatch(){
        this.view=null;
    }
}
