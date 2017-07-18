package test.bwie.com.dliao.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import test.bwie.com.dliao.R;

/**
 * Created by lenovo-pc on 2017/7/6.
 */

public abstract class BaseMVPFragment<V,T extends BasePresenter<V>> extends Fragment {
    public T presenter ;

    public abstract T initPresenter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter=initPresenter();
    }

    public BaseMVPFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fagment_base,container,false);
    }
    @Override
    public void onResume() {
        super.onResume();
        presenter.attach((V) this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.deatch();
    }
}
