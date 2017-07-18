package test.bwie.com.dliao.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import test.bwie.com.dliao.R;

/**
 * Created by lenovo-pc on 2017/7/5.
 */

public class IActivity extends FragmentActivity implements View.OnClickListener {
    TextView textViewtitile;
    private ImageButton btnBack;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getSupportFragmentManager();
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    public void SetPublicTitle(String tiltle) {
        textViewtitile = (TextView) findViewById(R.id.title_text);
        textViewtitile.setText(tiltle);
    }

    public void BtnBack() {
        btnBack = (ImageButton) findViewById(R.id.title_back);
        btnBack.setOnClickListener(this);
    }

    public void switchIFragment(int pos, List<Fragment> list, int containerId) {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (!list.get(pos).isAdded()) {
            fragmentTransaction.add(containerId, list.get(pos), list.get(pos).getClass().getSimpleName());
        }
        for (int i = 0; i < list.size(); i++) {
            if (i == pos) {
                fragmentTransaction.show(list.get(pos));
            } else {
                fragmentTransaction.hide(list.get(i));
            }
        }
        fragmentTransaction.commit();
    }
    public void toIActicity(Class toActivity,Bundle bundle,int requestCode){
        Intent intent=new Intent(this,toActivity);
        if(bundle!=null){
            intent.putExtras(bundle);
        }
        if(requestCode==0){
            startActivity(intent);
        }else{
            startActivityForResult(intent,requestCode);
        }
    }
}
