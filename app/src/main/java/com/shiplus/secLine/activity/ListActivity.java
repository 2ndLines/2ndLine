package com.shiplus.secLine.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ViewStub;

import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.shiplus.secLine.R;
import com.shiplus.secLine.adapter.ListAdapter;
import com.shiplus.secLine.decoration.DividerItemDecoration;
import com.shiplus.secLine.listener.OnItemClickListener;

import java.util.List;

/**
 * Created by Administrator on 2015/6/16.
 */
public abstract class ListActivity<DATA> extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,
        OnMoreListener, OnItemClickListener<DATA> {
    protected SuperRecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        if (showActionBar()) {
            initToolbar();
        }
        recyclerView = (SuperRecyclerView) findViewById(R.id.list);
        recyclerView.setAdapter(getAdapter());
        recyclerView.setLayoutManager(getLayoutManager());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
    }

    private void initToolbar() {
        ViewStub stub = (ViewStub) findViewById(R.id.viewstub_toolbar);
        Toolbar toolbar = (Toolbar) stub.inflate();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    protected boolean showActionBar() {
        return true;
    }

    protected void setRefreshListener() {
        recyclerView.setRefreshListener(this);
    }

    protected void setMoreListener(int numberBeforeMore) {
        recyclerView.setupMoreListener(this, numberBeforeMore);
    }

    protected void swapData(List<DATA> data) {
        getAdapter().swapData(data);
    }

    protected void addAll(List<DATA> data) {
        getAdapter().addAll(data);
    }

    protected void removeItem(int position) {
        getAdapter().remove(position);
    }

    protected int getLayoutId() {
        return R.layout.activity_list;
    }

    /**
     * LinearLayoutManager by default.
     *
     * @return
     */
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(this);
    }

    protected abstract ListAdapter<DATA> getAdapter();

    @Override
    public void onRefresh() {

    }

    @Override
    public void onMoreAsked(int i, int i1, int i2) {

    }

    protected void hideMoreProgress() {
        recyclerView.hideMoreProgress();
    }
}
