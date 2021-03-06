package com.fule.mesurekeyheight.refreshFrame.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.fule.mesurekeyheight.R;
import com.fule.mesurekeyheight.refreshFrame.adapter.BaseRecyclerAdapter;
import com.fule.mesurekeyheight.refreshFrame.adapter.SmartViewHolder;
import com.fule.mesurekeyheight.util.StatusBarUtil;
import com.fule.mesurekeyheight.util.ToastUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by Administrator on 2017/8/17.
 *   刷新控件的使用 ：
 *   1、进入刷新功能
 */

public class RepastPracticeActivity  extends AppCompatActivity{



    private BaseRecyclerAdapter<Model> mAdapter;


    private  boolean isFirstEnter = true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_repast);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        final RefreshLayout refreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);
        //第一次进入演示刷新
        if (isFirstEnter) { //第一次进入；刷新功能
            isFirstEnter = false;
            refreshLayout.autoRefresh();  //自动刷新
        }

        //初始化列表和监听
        View view = findViewById(R.id.recyclerView);
        if (view instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new GridLayoutManager(this,2));
//            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter = new BaseRecyclerAdapter<Model>(loadModels(),R.layout.listitem_practive_repast) {
                @Override
                protected void onBindViewHolder(SmartViewHolder holder, Model model, int position) {
                    holder.text(R.id.name, model.name);
                    holder.text(R.id.nickname, model.nickname);
                    holder.image(R.id.image, model.imageId);
                    holder.image(R.id.avatar, model.avatarId);
                }
            });
        }
        //设置刷新和加载 监听
        refreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshlayout.finishRefresh(false); //刷新完成
                        refreshlayout.setLoadmoreFinished(false);//恢复上拉状态
                        mAdapter.refresh(loadModels());
                    }
                }, 2000);
            }

            @Override
            public void onLoadmore(final RefreshLayout refreshlayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.loadmore(loadModels());
                        refreshlayout.finishLoadmore(true);  //
                        if (mAdapter.getCount() > 12) {
                            Toast.makeText(getBaseContext(), "数据全部加载完毕", Toast.LENGTH_SHORT).show();
                            refreshlayout.setLoadmoreFinished(true);//设置之后，将不会再触发加载事件
                        }
                    }
                }, 1000);
            }
        });
        mAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Model item = (Model) mAdapter.getItem(position);
                ToastUtil.showMessage(item.name);
            }
        });
        //状态栏透明和间距处理
        StatusBarUtil.darkMode(this);
        StatusBarUtil.setPaddingSmart(this, view);
        StatusBarUtil.setPaddingSmart(this, toolbar);
        StatusBarUtil.setPaddingSmart(this, findViewById(R.id.blurview));
        StatusBarUtil.setMargin(this, findViewById(R.id.gifview));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private class Model {
        int imageId;
        int avatarId;
        String name;
        String nickname;
    }
    /**
     * 模拟数据
     */
    private Collection<Model> loadModels() {
        return Arrays.asList(
                new Model() {{
                    this.name = "但家香酥鸭";
                    this.nickname = "爱过那张脸";
                    this.imageId = R.mipmap.image_practice_repast_1;
                    this.avatarId = R.mipmap.image_avatar_1;
                }}, new Model() {{
                    this.name = "香菇蒸鸟蛋";
                    this.nickname = "淑女算个鸟";
                    this.imageId = R.mipmap.image_practice_repast_2;
                    this.avatarId = R.mipmap.image_avatar_2;
                }}, new Model() {{
                    this.name = "花溪牛肉粉";
                    this.nickname = "性感妩媚";
                    this.imageId = R.mipmap.image_practice_repast_3;
                    this.avatarId = R.mipmap.image_avatar_3;
                }}, new Model() {{
                    this.name = "破酥包";
                    this.nickname = "一丝丝纯真";
                    this.imageId = R.mipmap.image_practice_repast_4;
                    this.avatarId = R.mipmap.image_avatar_4;
                }}, new Model() {{
                    this.name = "盐菜饭";
                    this.nickname = "等着你回来";
                    this.imageId = R.mipmap.image_practice_repast_5;
                    this.avatarId = R.mipmap.image_avatar_5;
                }}, new Model() {{
                    this.name = "米豆腐";
                    this.nickname = "宝宝树人";
                    this.imageId = R.mipmap.image_practice_repast_6;
                    this.avatarId = R.mipmap.image_avatar_6;
                }});
    }
}
