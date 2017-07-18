package test.bwie.com.dliao.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import test.bwie.com.dliao.MainActivity;
import test.bwie.com.dliao.R;
import test.bwie.com.dliao.application.MyApplication;
import test.bwie.com.dliao.base.IActivity;
import test.bwie.com.dliao.base.MyToast;
import test.bwie.com.dliao.beans.LoginBean;
import test.bwie.com.dliao.cipher.Md5Utils;
import test.bwie.com.dliao.cipher.aes.JNCryptorUtils;
import test.bwie.com.dliao.cipher.rsa.RsaUtils;
import test.bwie.com.dliao.network.BaseObserver;
import test.bwie.com.dliao.network.RetrofitManager;
import test.bwie.com.dliao.utils.Constants;
import test.bwie.com.dliao.utils.PreferencesUtils;


/**
 * Created by lenovo-pc on 2017/7/6.
 */

public class LoginActivity extends IActivity {
    @BindView(R.id.title_back)
    ImageButton titleBack;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.login_edit_id)
    EditText loginEditId;
    @BindView(R.id.login_layout_text_password)
    TextView loginLayoutTextPassword;
    @BindView(R.id.login_edit_password)
    EditText loginEditPassword;
    @BindView(R.id.login_image_paseeword)
    ImageView loginImagePaseeword;
    @BindView(R.id.login_text_longin_phone)
    TextView loginTextLonginPhone;
    @BindView(R.id.login_btn_login)
    Button loginBtnLogin;
    private String phone;
    private String password;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            phone = bundle.getString("phone");
            password = bundle.getString("password");
        }
        init();
        BtnBack();
        SetPublicTitle("登录");
    }

    private void init() {
        if (phone != null && password != null) {
            loginEditId.setText(phone);
            loginEditPassword.setText(password);
        }
        RxView.clicks(loginBtnLogin).throttleFirst(5, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        checkInfo();
                    }
                });


    }
    // TODO: 2017/7/11 验证整理

    public void checkInfo() {
        String username = loginEditId.getText().toString().trim();

        String password = loginEditPassword.getText().toString().trim();

        String randomKey = RsaUtils.getStringRandom(16);

        String rsaRandomKey = RsaUtils.getInstance().createRsaSecret(MyApplication.getApplication(), randomKey);

        String cipherPhone = JNCryptorUtils.getInstance().encryptData(username, MyApplication.getApplication(), randomKey);

        Map map = new HashMap<String, String>();
        map.put("user.phone", cipherPhone);
        map.put("user.password", Md5Utils.getMD5(password));
        map.put("user.secretkey", rsaRandomKey);

        RetrofitManager.post(Constants.LOGIN_ACTION, map, new BaseObserver() {
            @Override
            public void onSuccess(String result) {

                System.out.println("result = " + result);

                if (result.contains("200")) {
                    MyToast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT);
                    Intent intent = new Intent(LoginActivity.this, TableActivity.class);
                    PreferencesUtils utils = new PreferencesUtils();
                    utils.addConfigInfo(LoginActivity.this, "checkLogin", true);
                    if (utils.getValueByKey(LoginActivity.this, "checkLogin", false) == true) {
                        finish();
                    }
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailed(int code) {

            }
        });
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        finish();
//        return super.onKeyDown(keyCode, event);
//    }
}
