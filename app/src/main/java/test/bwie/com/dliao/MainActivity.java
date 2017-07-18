package test.bwie.com.dliao;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;
import test.bwie.com.dliao.activitys.LoginActivity;
import test.bwie.com.dliao.activitys.RegistActivity;
import test.bwie.com.dliao.activitys.TableActivity;
import test.bwie.com.dliao.base.IActivity;
import test.bwie.com.dliao.utils.PreferencesUtils;

public class MainActivity extends IActivity {


    @BindView(R.id.main_login)
    Button mainLogin;
    @BindView(R.id.main_regist)
    Button mainRegist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toIActicity(TableActivity.class,null,0);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        PreferencesUtils.clearFriends(MainActivity.this);
        RxView.clicks(mainLogin).throttleFirst(5, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull Object o) throws Exception {
                        toIActicity(LoginActivity.class, new Bundle(), 0);
                    }
                });
        RxView.clicks(mainRegist).throttleFirst(5, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull Object o) throws Exception {
                        toIActicity(RegistActivity.class, null, 0);
                    }
                });
    }
}
