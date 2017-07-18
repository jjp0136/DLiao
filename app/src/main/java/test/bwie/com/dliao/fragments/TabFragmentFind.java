package test.bwie.com.dliao.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import test.bwie.com.dliao.R;
import test.bwie.com.dliao.activitys.DetailsActivity;
import test.bwie.com.dliao.activitys.TableActivity;
import test.bwie.com.dliao.adapters.FragmentFindAdapter;
import test.bwie.com.dliao.base.BaseMVPFragment;
import test.bwie.com.dliao.beans.DataBean;
import test.bwie.com.dliao.beans.IndexBean;
import test.bwie.com.dliao.presenter.FragmentFindPresenter;
import test.bwie.com.dliao.utils.NetUtil;
import test.bwie.com.dliao.view.FragmentFindView;

import static test.bwie.com.dliao.application.MyApplication.daosession;

/**
 * Created by lenovo-pc on 2017/7/12.
 */

public class TabFragmentFind extends BaseMVPFragment<FragmentFindView, FragmentFindPresenter> implements FragmentFindView {

    @BindView(R.id.fragment_find_switch)
    TextView fragmentFindSwitch;
    @BindView(R.id.fragment_find_recycler)
    RecyclerView fragmentFindRecycler;
    @BindView(R.id.fragment_find_spring)
    SpringView springview;
    Unbinder unbinder;
    private LinearLayoutManager linearLayoutManager;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private int page = 1;
    private View view;
    private TableActivity activity;
    FragmentFindAdapter adapter;
    private HorizontalDividerItemDecoration horizontalDividerItemDecoration;
    private List<DataBean> list=new ArrayList<>();


    @Override
    public FragmentFindPresenter initPresenter() {
        return new FragmentFindPresenter();
    }

    public TabFragmentFind() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_find, container, false);
        unbinder = ButterKnife.bind(this, view);
        activity = (TableActivity) getActivity();
        adapter = new FragmentFindAdapter(getActivity());
        init();
        return view;
    }

    private void init() {
        List<DataBean> dataBeen = daosession.getDataBeanDao().loadAll();
        //if (!NetUtil.isNetworkAvailable(getActivity())) {
            /*list=daosession.getDataBeanDao().loadAll();*/
            if (dataBeen.size() > 0) {
                list.addAll(dataBeen);
                IndexBean bean=new IndexBean();
                bean.setData(list);
                adapter.setData(bean,page);
        //    }
        } else {
            presenter.getData(page);

        }
        fragmentFindSwitch.setVisibility(View.VISIBLE);
        fragmentFindSwitch.setTag(1);
        fragmentFindSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tag = (int) v.getTag();
                if (tag == 1) {
                    fragmentFindSwitch.setTag(2);
                    toStaggeredGridLayoutManager();
                } else {
                    fragmentFindSwitch.setTag(1);
                    toLinearLayoutManager();
                }

            }
        });

        horizontalDividerItemDecoration = new HorizontalDividerItemDecoration.Builder(getActivity()).build();

        // TODO: 2017/7/14 点击事件
        adapter.setItemClickListener(new FragmentFindAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("checked", list.get(position).getUserId());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        toLinearLayoutManager();

        presenter.getData(page);

        springview.setHeader(new DefaultHeader(getActivity()));
        springview.setFooter(new DefaultFooter(getActivity()));


        springview.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                System.out.println("onRefresh = ");
                page = 1;
                presenter.getData(page);
                springview.onFinishFreshAndLoad();
            }

            @Override
            public void onLoadmore() {
                System.out.println("onLoadmore = ");
                presenter.getData(++page);
                springview.onFinishFreshAndLoad();

            }
        });


    }

    @Override
    public void success(IndexBean indexBean, int page) {

        adapter.setData(indexBean, page);
        if (list == null) {
            list = new ArrayList<DataBean>();
        }
        if (page == 1) {
            list.clear();
        }
        list.addAll(indexBean.getData());
    }


    @Override
    public void failed(int code, int page) {

    }

    public void toLinearLayoutManager() {
        if (linearLayoutManager == null) {
            linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        }
        adapter.dataChange(1);
        fragmentFindRecycler.setLayoutManager(linearLayoutManager);
        fragmentFindRecycler.setAdapter(adapter);

//        fragmentFindRecycler.addItemDecoration(horizontalDividerItemDecoration);

    }

    public void toStaggeredGridLayoutManager() {
        if (staggeredGridLayoutManager == null) {
            staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        }
        adapter.dataChange(2);
        fragmentFindRecycler.setLayoutManager(staggeredGridLayoutManager);
        fragmentFindRecycler.setAdapter(adapter);
        fragmentFindRecycler.removeItemDecoration(horizontalDividerItemDecoration);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
