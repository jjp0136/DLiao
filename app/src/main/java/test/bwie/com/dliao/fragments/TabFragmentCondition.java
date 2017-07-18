package test.bwie.com.dliao.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import test.bwie.com.dliao.R;
import test.bwie.com.dliao.activitys.DetailsActivity;
import test.bwie.com.dliao.adapters.FragmentConditionAdapter;
import test.bwie.com.dliao.base.BaseMVPFragment;
import test.bwie.com.dliao.base.MyToast;
import test.bwie.com.dliao.beans.DataBean1;
import test.bwie.com.dliao.beans.FActionFriendBean;
import test.bwie.com.dliao.presenter.FragmentConditionPresenter;
import test.bwie.com.dliao.utils.PreferencesUtils;
import test.bwie.com.dliao.view.FragmentSecendView;

/**
 * Created by lenovo-pc on 2017/7/12.
 */

public class TabFragmentCondition extends BaseMVPFragment<FragmentSecendView, FragmentConditionPresenter> implements FragmentSecendView {

    @BindView(R.id.condition_lv)
    ListView conditionLv;
    Unbinder unbinder;
    private View view;
    private DataBean1 data;

    @Override
    public FragmentConditionPresenter initPresenter() {
        return new FragmentConditionPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_condition, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter.getData();
        return view;
    }

    @Override
    public void success(FActionFriendBean indexBean) {
        FragmentConditionAdapter adapter=new FragmentConditionAdapter(getActivity(),indexBean);
        PreferencesUtils utlis = new PreferencesUtils();
        if (utlis.getValueByKey(getActivity(), "checkLogin", false) == true) {
            conditionLv.setAdapter(adapter);
        }else{
            MyToast.makeText(getActivity(),"尚未登录",Toast.LENGTH_SHORT);
        }

    }

    @Override
    public void failed(int code) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
