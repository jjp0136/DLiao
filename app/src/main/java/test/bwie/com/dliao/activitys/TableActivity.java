package test.bwie.com.dliao.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import test.bwie.com.dliao.R;
import test.bwie.com.dliao.base.IActivity;
import test.bwie.com.dliao.fragments.TabFragmentCondition;
import test.bwie.com.dliao.fragments.TabFragmentEmil;
import test.bwie.com.dliao.fragments.TabFragmentFind;
import test.bwie.com.dliao.fragments.TabFragmentMe;

/**
 * Created by lenovo-pc on 2017/7/4.
 */

public class TableActivity extends IActivity {
    @BindView(R.id.tab_frame)
    FrameLayout tabFrame;
    @BindView(R.id.rb1)
    RadioButton rb1;
    @BindView(R.id.rb2)
    RadioButton rb2;
    @BindView(R.id.rb3)
    RadioButton rb3;
    @BindView(R.id.rb4)
    RadioButton rb4;
    @BindView(R.id.table_radiogroup)
    RadioGroup tableRadiogroup;
    private FragmentManager fragmentManager;
    private Fragment[] mFragments;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secend);
        ButterKnife.bind(this);
        fragmentManager = getSupportFragmentManager();
        init();
    }

    private void init() {
        TabFragmentFind find = new TabFragmentFind();
        TabFragmentCondition condition = new TabFragmentCondition();
        TabFragmentEmil emil = new TabFragmentEmil();
        TabFragmentMe me = new TabFragmentMe();
        mFragments = new Fragment[]{find,condition,emil,me};
        FragmentTransaction ft =
                getSupportFragmentManager().beginTransaction();

        //添加首页
        ft.add(R.id.tab_frame,find).commit();

        //默认设置为第0个
        setIndexSelected(0);



    }
    private int mIndex;
    private void setIndexSelected(int index) {

        if(mIndex==index){
            return;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft= fragmentManager.beginTransaction();


        //隐藏
        ft.hide(mFragments[mIndex]);
        //判断是否添加
        if(!mFragments[index].isAdded()){
            ft.add(R.id.tab_frame,mFragments[index]).show(mFragments[index]);
        }else {
            ft.show(mFragments[index]);
        }

        ft.commit();
        //再次赋值
        mIndex=index;

    }
    @OnClick({R.id.rb1, R.id.rb2, R.id.rb3, R.id.rb4})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rb1:
                setIndexSelected(0);
                break;
            case R.id.rb2:
                setIndexSelected(1);
                break;
            case R.id.rb3:
                setIndexSelected(2);
                break;
            case R.id.rb4:
                setIndexSelected(3);
                break;
        }
    }
}
