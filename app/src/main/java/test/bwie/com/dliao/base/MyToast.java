package test.bwie.com.dliao.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import test.bwie.com.dliao.R;


/**
 * Created by yufuhao on 2017/7/5.
 */

public class MyToast {

    public static Toast mToast;
    public static TextView textView;

    //构造方法，添加布局进Toast
    public MyToast(Context context, CharSequence text, int duration) {
        View v = LayoutInflater.from(context).inflate(R.layout.ctoast, null);
        textView = (TextView) v.findViewById(R.id.ctoast_tv);
        textView.setText(text);
        mToast = new Toast(context);
        mToast.setDuration(duration);
        mToast.setView(v);
    }

    //复写makeText方法，复用布局
    public static void makeText(Context context, CharSequence text, int duration) {
        if (mToast == null) {
            new MyToast(context, text, duration);
        } else {
            textView.setText(text);
            mToast.setDuration(duration);
        }

        //执行吐司
        mToast.show();

    }
}
