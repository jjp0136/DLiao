package test.bwie.com.dliao.activitys;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import test.bwie.com.dliao.R;
import test.bwie.com.dliao.base.MyToast;
import test.bwie.com.dliao.beans.DetailsBean;
import test.bwie.com.dliao.network.BaseObserver;
import test.bwie.com.dliao.network.RetrofitManager;
import test.bwie.com.dliao.utils.Constants;
import test.bwie.com.dliao.utils.PreferencesUtils;

/**
 * Created by lenovo-pc on 2017/7/14.
 */

public class DetailsActivity extends Activity {

    @BindView(R.id.fragment_find_details_imageButton)
    ImageButton fragmentFindDetailsImageButton;
    @BindView(R.id.fragment_find_details_image)
    ImageView imageView;
    @BindView(R.id.fragment_find_details_age)
    TextView fragmentFindDetailsAge;
    @BindView(R.id.fragment_find_details_district)
    TextView fragmentFindDetailsDistrict;
    @BindView(R.id.fragment_find_details_constellation)
    TextView fragmentFindDetailsConstellation;
    @BindView(R.id.fragment_find_details_linearLayout)
    LinearLayout fragmentFindDetailsLinearLayout;
    @BindView(R.id.fragment_find_details_sex)
    ImageView imageset;
    @BindView(R.id.fragment_find_details_nickname)
    TextView nickname;
    @BindView(R.id.fragment_find_details_add)
    Button btn_add;

    private LinearLayout mGallery;
    private int[] mImgIds;
    private LayoutInflater mInflater;
    private int checked;
    private String uesrId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_find_details);
        ButterKnife.bind(this);
        System.out.println("***************"+System.currentTimeMillis()+"*****************");
        init();

    }

    private void init() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        checked = bundle.getInt("checked");
        Map<String, String> map = new HashMap<String, String>();
        map.put("user.userId", checked + "");

        RetrofitManager.post(Constants.DETAILS, map, new BaseObserver() {
            @Override
            public void onSuccess(String result) {

                try {
                    Gson gson = new Gson();
                    System.out.println(result);
                    DetailsBean bean = gson.fromJson(result, DetailsBean.class);
                    uesrId=bean.getData().getUserId()+"";
                    Glide.with(DetailsActivity.this).load(bean.getData().getImagePath()).error(R.mipmap.face_error).into(imageView);
                    fragmentFindDetailsAge.setText(bean.getData().getGender());
                    if (bean.getData().getGender().equals("男")) {
                        imageset.setImageResource(R.mipmap.correlation_info_boy);
                    } else {
                        imageset.setImageResource(R.mipmap.correlation_info_girl);
                    }
                    fragmentFindDetailsDistrict.setText(bean.getData().getArea());
                    fragmentFindDetailsImageButton.setBackgroundColor(Color.TRANSPARENT);
                    fragmentFindDetailsImageButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    });
                    nickname.setText(bean.getData().getIntroduce());
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int code) {

            }
        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map map = new HashMap<String, String>();
                map.put("relationship.friendId", uesrId);
                PreferencesUtils utlis = new PreferencesUtils();
                if (utlis.getValueByKey(DetailsActivity.this, "checkLogin", false) == true) {
                    RetrofitManager.post(Constants.ADD_FRIEND, map, new BaseObserver() {
                        @Override
                        public void onSuccess(String result) {
                            if (result.contains("200")) {
                                MyToast.makeText(DetailsActivity.this, "添加成功", Toast.LENGTH_SHORT);
//                                finish();
                            }else{
                                MyToast.makeText(DetailsActivity.this, "添加失败", Toast.LENGTH_SHORT);
                            }
                        }
                        @Override
                        public void onFailed(int code) {

                        }
                    });
                } else {
                    MyToast.makeText(DetailsActivity.this, "请先登录", Toast.LENGTH_SHORT);
                    Intent intent = new Intent(DetailsActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
