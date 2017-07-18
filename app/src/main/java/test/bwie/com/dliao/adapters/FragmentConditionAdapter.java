package test.bwie.com.dliao.adapters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import test.bwie.com.dliao.R;
import test.bwie.com.dliao.beans.FActionFriendBean;

/**
 * Created by lenovo-pc on 2017/7/17.
 */

public class FragmentConditionAdapter extends BaseAdapter {
    private Context context;
    private FActionFriendBean indexBean;

    public FragmentConditionAdapter(Context context, FActionFriendBean indexBean) {
        this.context = context;
        this.indexBean = indexBean;
    }

    @Override
    public int getCount() {
        return indexBean.getData().size();
    }

    @Override
    public Object getItem(int position) {
        return indexBean.getData().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ConditionHodler hodler;
        if (convertView == null) {
            hodler = new ConditionHodler();
            convertView = View.inflate(context, R.layout.item_find_1, null);
            hodler.image = (ImageView) convertView.findViewById(R.id.item_find_1_image);
            hodler.tv1 = (TextView) convertView.findViewById(R.id.item_find_1_nickname);
            hodler.tv2 = (TextView) convertView.findViewById(R.id.item_find_1_age);
            hodler.tv3 = (TextView) convertView.findViewById(R.id.item_find_1_distance);
            convertView.setTag(hodler);
        } else {
            hodler = (ConditionHodler) convertView.getTag();
        }
        Glide.with(context).load(indexBean.getData().get(position).getImagePath()).error(R.mipmap.face_error).into(hodler.image);
        hodler.tv1.setText(indexBean.getData().get(position).getNickname());
        hodler.tv2.setText(indexBean.getData().get(position).getAge());
        hodler.tv3.setText(indexBean.getData().get(position).getGender());
        return convertView;
    }

    class ConditionHodler {
        ImageView image;
        TextView tv1;
        TextView tv2;
        TextView tv3;
    }
}
