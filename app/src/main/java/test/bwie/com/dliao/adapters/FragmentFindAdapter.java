package test.bwie.com.dliao.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.CoordinateConverter;
import com.amap.api.location.DPoint;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import test.bwie.com.dliao.R;
import test.bwie.com.dliao.beans.DataBean;
import test.bwie.com.dliao.beans.IndexBean;
import test.bwie.com.dliao.utils.AMapUtils;
import test.bwie.com.dliao.utils.DeviceUtils;
import test.bwie.com.dliao.utils.DisanceUtils;
import test.bwie.com.dliao.utils.NetUtil;
import test.bwie.com.dliao.utils.PreferencesUtils;

import static test.bwie.com.dliao.application.MyApplication.daosession;

/**
 * Created by lenovo-pc on 2017/7/13.
 */

public class FragmentFindAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private MyItemClickListener mItemClickListener;
    private List<DataBean> list;
    private Context context;
    private int tag = 1; // 1 先行布局 2 瀑布流
    private int itemWidth;

    public FragmentFindAdapter(Context context) {
        this.context = context;
        itemWidth = DeviceUtils.getDisplayInfomation(context).x / 3;
    }

    public void setData(IndexBean bean, int page) {

            if (list == null) {
                list = new ArrayList<DataBean>();
            }
            if (page == 1) {
                list.clear();
            }
            list.addAll(bean.getData());
            notifyDataSetChanged();
        }


    public void dataChange(int type) {
        this.tag = type;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_find_1, parent, false);
            VerticalViewHolder verticalViewHolder = new VerticalViewHolder(view,mItemClickListener);
            return verticalViewHolder;

        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_find_2, parent, false);
            PinterestViewHolder pinterestViewHolder = new PinterestViewHolder(view,mItemClickListener);
            return pinterestViewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof VerticalViewHolder) {

            //列表的形式展示
            VerticalViewHolder verticalViewHolder = (VerticalViewHolder) holder;

            verticalViewHolder.indexfragmentNickname.setText(list.get(position).getNickname());


            verticalViewHolder.indexfragmentDes.setText(list.get(position).getIntroduce());

            Glide.with(context).load(list.get(position).getImagePath()).error(R.mipmap.face_error).into(verticalViewHolder.indexfragmentFace);

            String lat = PreferencesUtils.getValueByKey(context, AMapUtils.LAT, "");
            String lng = PreferencesUtils.getValueByKey(context, AMapUtils.LNG, "");

            double olat = list.get(position).getLat();
            double olng = list.get(position).getLng();


            if (!TextUtils.isEmpty(lat) && !TextUtils.isEmpty(lng) && olat != 0.0 && olng != 0.0) {

                double dlat = Double.valueOf(lat);
                double dlng = Double.valueOf(lng);
                DPoint dPoint = new DPoint(dlat, dlng);
                DPoint oPoint = new DPoint(olat, olng);

                //计算两点之间的距离
                float dis = CoordinateConverter.calculateLineDistance(dPoint, oPoint);

                verticalViewHolder.indexfragmentAgesex.setText(list.get(position).getAge() + "岁 , " + list.get(position).getGender() + " , " + DisanceUtils.standedDistance(dis));

            } else {

                verticalViewHolder.indexfragmentAgesex.setText(list.get(position).getAge() + "岁 , " + list.get(position).getGender());

            }
        } else {
            PinterestViewHolder staggeredViewHolder = (PinterestViewHolder) holder;

            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) staggeredViewHolder.indexfragmentStagger.getLayoutParams();

            float scale = (float) itemWidth / (float) list.get(position).getPicWidth();
            params.width = itemWidth;
            params.height = (int) ((float) scale * (float) list.get(position).getPicHeight());

            staggeredViewHolder.indexfragmentStagger.setLayoutParams(params);

            Glide.with(context).load(list.get(position).getImagePath()).error(R.mipmap.face_error).into(staggeredViewHolder.indexfragmentStagger);

        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (tag == 1) {
            return 0;
        } else {
            return 1;
        }
    }

    static class VerticalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private MyItemClickListener mListener;

        @BindView(R.id.item_find_1_nickname)
        TextView indexfragmentNickname;
        @BindView(R.id.item_find_1_age)
        TextView indexfragmentAgesex;
        @BindView(R.id.item_find_1_distance)
        TextView indexfragmentDes;
        @BindView(R.id.item_find_1_image)
        ImageView indexfragmentFace;

        public VerticalViewHolder(View itemView,MyItemClickListener myItemClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.mListener = myItemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(v, getPosition());
            }
        }
    }

    static class PinterestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private MyItemClickListener mListener;
        @BindView(R.id.item_find_2_image)
        ImageView indexfragmentStagger;

        public PinterestViewHolder(View itemView,MyItemClickListener myItemClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.mListener = myItemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(v, getPosition());
            }
        }

}
    public interface MyItemClickListener {
        void onItemClick(View view, int position);
    }

    /**
     * 在activity里面adapter就是调用的这个方法,将点击事件监听传递过来,并赋值给全局的监听
     *
     * @param myItemClickListener
     */
    public void setItemClickListener(MyItemClickListener myItemClickListener) {
        this.mItemClickListener = myItemClickListener;
    }
}
