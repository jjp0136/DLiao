package test.bwie.com.dliao.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import test.bwie.com.dliao.R;
import test.bwie.com.dliao.base.IActivity;
import test.bwie.com.dliao.fragments.RegistFragmentFirst;
import test.bwie.com.dliao.fragments.RegistFragmentSecend;
import test.bwie.com.dliao.fragments.RegistFragmentThread;

/**
 * Created by lenovo-pc on 2017/7/6.
 */

public class RegistActivity extends IActivity {
    private List<Fragment> list = new ArrayList<Fragment>();


    @BindView(R.id.title_back)
    ImageButton titleBack;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.regist_frameLayout)
    FrameLayout registFrameLayout;
    FragmentManager fragmentManager;
    private RegistFragmentSecend fragmentSecend;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        ButterKnife.bind(this);
        // TODO: 2017/7/6 设置标题
        SetPublicTitle(getText(R.string.zhuce).toString());

        BtnBack();
        init();
    }


    private void init() {

        fragmentManager = getSupportFragmentManager();

        fragmentSecend = new RegistFragmentSecend();

        list.add(new RegistFragmentFirst());
        list.add(fragmentSecend);
        switchIFragment(0,list,R.id.regist_frameLayout);

    }
    public void toPos(int pos){
        switchIFragment(pos,list,R.id.regist_frameLayout);
    }

    public void setPhone(String phone){
        // TODO: 2017/7/7 将第一个页面的电话号码传到第二个页面
        fragmentSecend.setPhone(phone);


    }


}
