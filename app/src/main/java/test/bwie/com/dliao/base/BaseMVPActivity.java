package test.bwie.com.dliao.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import test.bwie.com.dliao.R;

/**
 * Created by lenovo-pc on 2017/7/5.
 */

public abstract class BaseMVPActivity<V, T extends BasePresenter<V>> extends IActivity {
    public T presenter;

    public abstract T initPreserent();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        presenter = initPreserent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.attach((V) this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.deatch();
    }
}
