package test.bwie.com.dliao.daoutils;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import test.bwie.com.dliao.beans.DataBean;

import static test.bwie.com.dliao.application.MyApplication.daosession;

/**
 * Created by lenovo-pc on 2017/7/12.
 */

public class FragmentFindDaoUtils {
    public static void insert(final List<DataBean> list){
        Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Long> e) throws Exception {



                daosession.getDataBeanDao().insertInTx(list);


            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {

                    }
                });
    }
}
