package test.bwie.com.dliao.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import test.bwie.com.dliao.R;
import test.bwie.com.dliao.activitys.RegistActivity;
import test.bwie.com.dliao.application.MyApplication;
import test.bwie.com.dliao.base.BaseMVPFragment;
import test.bwie.com.dliao.base.MyToast;
import test.bwie.com.dliao.presenter.RegistFragmentFirstPresenter;
import test.bwie.com.dliao.view.RegistFragmentFistView;

/**
 * Created by lenovo-pc on 2017/7/6.
 */

public class RegistFragmentFirst extends BaseMVPFragment<RegistFragmentFistView, RegistFragmentFirstPresenter> implements RegistFragmentFistView {

    @BindView(R.id.fragment_regist_edit_phone)
    EditText fragmentRegistEditPhone;
    @BindView(R.id.fragment_regist_edit_authcode)
    EditText fragmentRegistEditAuthcode;
    @BindView(R.id.fragment_regist_btn_authcode)
    Button fragmentRegistBtnAuthcode;
    @BindView(R.id.fragment_regist_btn_next)
    Button fragmentRegistBtnNext;

    Unbinder unbinder;

    EventHandler eventHandler;

    private RegistActivity registActivity;
    private InputMethodManager im;

    @Override
    public RegistFragmentFirstPresenter initPresenter() {
        return new RegistFragmentFirstPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_regist, container, false);
        unbinder = ButterKnife.bind(this, view);
        registActivity = (RegistActivity) getActivity();

        SMSSDK.initSDK(getActivity(), "1f2a06c6cca20", "62183728136ab66360efc0378c10c6c4");
        eventHandler = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {

            }
        };
        SMSSDK.registerEventHandler(eventHandler);
        im = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        ((RegistActivity) getActivity()).BtnBack();
        return view;
    }

    @OnClick({R.id.fragment_regist_btn_authcode, R.id.fragment_regist_btn_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_regist_btn_authcode:
                presenter.getVerificationCode(fragmentRegistEditPhone.getText().toString().trim());
                break;
            case R.id.fragment_regist_btn_next:
                registActivity.setPhone(fragmentRegistEditPhone.getText().toString().trim());
                presenter.nextStep(fragmentRegistEditPhone.getText().toString().trim(), fragmentRegistEditAuthcode.getText().toString().trim());
                im.hideSoftInputFromWindow(fragmentRegistEditPhone.getWindowToken(), 0);
                break;

        }
        RxView.clicks(view).throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        KeyBoardCancle();
                    }});

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void phoneError(int type) {

        switch (type) {
            case 1:
                MyToast.makeText(MyApplication.getApplication(), "手机号码不能为空", Toast.LENGTH_SHORT);
                break;
            case 2:
                MyToast.makeText(MyApplication.getApplication(), "手机格式不正确", Toast.LENGTH_SHORT);
                break;
            case 3:
                MyToast.makeText(MyApplication.getApplication(), "验证码错误", Toast.LENGTH_SHORT);
                break;
        }

    }

    private Disposable disposable;

    @Override
    public void showTimer() {
        fragmentRegistBtnAuthcode.setClickable(false);

        Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(30)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(@NonNull Long aLong) throws Exception {
                        return 29 - aLong;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {


                        disposable = d;
//                        d.dispose();

                    }

                    @Override
                    public void onNext(@NonNull Long aLong) {

                        System.out.println("aLong = " + aLong);
                        fragmentRegistBtnAuthcode.setText(aLong + " S ");

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        fragmentRegistBtnAuthcode.setClickable(true);
                        fragmentRegistBtnAuthcode.setText("获取验证码");

                    }
                });

    }

    @Override
    public void toNextPage() {
        registActivity.toPos(1);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }
    }

    public void KeyBoardCancle() {
        View view = getActivity().getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
