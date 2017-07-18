package test.bwie.com.dliao.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.lljjcoder.citypickerview.widget.CityPicker;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import test.bwie.com.dliao.R;
import test.bwie.com.dliao.activitys.LoginActivity;
import test.bwie.com.dliao.activitys.RegistActivity;
import test.bwie.com.dliao.application.MyApplication;
import test.bwie.com.dliao.base.AppManager;
import test.bwie.com.dliao.base.BaseMVPFragment;
import test.bwie.com.dliao.base.MyToast;
import test.bwie.com.dliao.beans.RegisterBean;
import test.bwie.com.dliao.cipher.Md5Utils;
import test.bwie.com.dliao.presenter.RegistFragmentSecendPresenter;
import test.bwie.com.dliao.view.RegistFragmentSecendView;

/**
 * Created by lenovo-pc on 2017/7/6.
 */

public class RegistFragmentSecend extends BaseMVPFragment<RegistFragmentSecendView, RegistFragmentSecendPresenter> implements RegistFragmentSecendView {


    @BindView(R.id.fragment_regist_next_edit_nickname)
    EditText fragmentRegistNextEditNickname;
    @BindView(R.id.fragment_regist_next_text_sex)
    TextView fragmentRegistNextTextSex;
    @BindView(R.id.fragment_regist_next_text_age)
    TextView fragmentRegistNextTextAge;
    @BindView(R.id.fragment_regist_next_text_region)
    TextView fragmentRegistNextTextRegion;
    @BindView(R.id.fragment_regist_next_edit_synopsis)
    EditText fragmentRegistNextEditSynopsis;
    @BindView(R.id.fragment_regist_next_btn_next)
    Button fragmentRegistNextBtnNext;
    Unbinder unbinder;
    @BindView(R.id.fragment_regist_layout_text_password)
    TextView fragmentRegistLayoutTextPassword;
    @BindView(R.id.fragment_regist_edit_password)
    EditText fragmentRegistEditPassword;
    @BindView(R.id.fragment_regist_image_password)
    ImageView fragmentRegistImagePassword;
    private RegistActivity activity;


    @Override
    public void registerFailed(int code) {
        MyToast.makeText(MyApplication.getApplication(), code + "", Toast.LENGTH_SHORT);
    }

    @Override
    public RegistFragmentSecendPresenter initPresenter() {
        return new RegistFragmentSecendPresenter();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (RegistActivity) getActivity();
        View view = inflater.inflate(R.layout.fragment_regist_next, container, false);
        unbinder = ButterKnife.bind(this, view);
        toData();
        ((RegistActivity) getActivity()).BtnBack();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.fragment_regist_next_btn_next, R.id.fragment_regist_next_edit_nickname, R.id.fragment_regist_next_edit_synopsis, R.id.fragment_regist_next_text_age, R.id.fragment_regist_next_text_region, R.id.fragment_regist_next_text_sex})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_regist_next_text_sex:
                showSexChooseDialog();
                break;
            case R.id.fragment_regist_next_text_age:
                showAgeDialog();
                break;
            case R.id.fragment_regist_next_text_region:
                showRegionLog();
                break;

            case R.id.fragment_regist_image_password:
                imageChange();
                break;
        }

        KeyBoardCancle();


    }

    private void imageChange() {
        if (fragmentRegistEditPassword.getTransformationMethod() == HideReturnsTransformationMethod.getInstance()) {
            fragmentRegistEditPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            fragmentRegistEditPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }

    }

    private String phone;

    // TODO: 2017/7/7 得到从fragment1获得的数据
    public void setPhone(String phone) {
        this.phone = phone;
    }


    // TODO: 2017/7/7 数据上传
    private void toData() {

        RxView.clicks(fragmentRegistNextBtnNext).throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {

                        presenter.vaildInfor(phone, fragmentRegistNextEditNickname.getText().toString().trim(),
                                sex, age, region, fragmentRegistNextEditSynopsis.getText().toString().trim(),
                                Md5Utils.getMD5(fragmentRegistEditPassword.getText().toString().trim()));
                    }

                });
    }


    // TODO: 2017/7/7 城市选择
    private String region;

    private void showRegionLog() {

        CityPicker cityPicker = new CityPicker.Builder(getActivity())
                .textSize(14)
                .title("地址选择")
                .titleBackgroundColor("#FFFFFF")
                .cancelTextColor("#696969")
                .confirTextColor("#696969")
                .cancelTextColor("#696969")
                .province("江苏省")
                .city("常州市")
                .district("天宁区")
                .textColor(Color.parseColor("#000000"))
                .provinceCyclic(true)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .onlyShowProvinceAndCity(false)
                .build();
        cityPicker.show();
        //监听方法，获取选择结果
        cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                //省份
                String province = citySelected[0];
                //城市
                String city = citySelected[1];
                //区县（如果设定了两级联动，那么该项返回空）
                String district = citySelected[2];
                //邮编
                String code = citySelected[3];
                region = province.trim() + "-" + city.trim() + "-" + district.trim();
                //为TextView赋值
                fragmentRegistNextTextRegion.setText(region);

            }
        });

    }

    private String sex = "男";
    // TODO: 2017/7/7 性别选择
    private String[] sexArry = new String[]{"女", "男"};


    private void showSexChooseDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("请选择性别");
        builder.setItems(sexArry, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                sex = sexArry[which];
                fragmentRegistNextTextSex.setText(sex);
            }
        });
        builder.show();
    }

    AlertDialog.Builder builder;

    //// TODO: 2017/7/7 年龄选择
    private String age;

    private void showAgeDialog() {
        if (builder == null) {
            final String[] ages = getResources().getStringArray(R.array.age);
            builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("请选择年龄");
            builder.setItems(ages, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    age = ages[which];
                    fragmentRegistNextTextAge.setText(ages[which]);
                }
            });
        }

        builder.show();

    }


    public void KeyBoardCancle() {
        View view = getActivity().getWindow().peekDecorView();
        if (view != null && view != fragmentRegistNextEditSynopsis && view != fragmentRegistNextEditNickname && view != fragmentRegistEditPassword) {
            InputMethodManager inputmanger = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void registerSuccess(RegisterBean registerBean) {
        if (registerBean.getResult_code() == 200) {
            Bundle bundle = new Bundle();
            bundle.putString("phone", phone);
            bundle.putString("password", fragmentRegistEditPassword.getText().toString().trim());
            activity.toIActicity(RegistFragmentThread.class, bundle, 0);
        } else {
            MyToast.makeText(MyApplication.getApplication(), registerBean.getResult_message(), Toast.LENGTH_SHORT);
        }
    }
}
