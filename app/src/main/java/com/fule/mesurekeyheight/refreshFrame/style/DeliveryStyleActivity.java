package com.fule.mesurekeyheight.refreshFrame.style;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;

import com.fule.mesurekeyheight.R;
import com.fule.mesurekeyheight.refreshFrame.adapter.BaseRecyclerAdapter;
import com.fule.mesurekeyheight.refreshFrame.adapter.SmartViewHolder;
import com.fule.mesurekeyheight.util.StatusBarUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.Arrays;

/**
 * Created by Administrator on 2017/8/19.
 *
 *   气球快递刷新
 */

public class DeliveryStyleActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private enum Item {
        默认主题("更改为默认主题颜色"),
        橙色主题("更改为橙色主题颜色"),
        红色主题("更改为红色主题颜色"),
        绿色主题("更改为绿色主题颜色"),
        蓝色主题("更改为蓝色主题颜色"),
        ;
        public String name;
        Item(String name) {
            this.name = name;
        }
    }

    private Toolbar mToolbar;
    private RefreshLayout mRefreshLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_style_delivery);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(0xfff0f0f0);  //设置状态栏颜色
        }
        if (Build.VERSION.SDK_INT >= 23) {  //Android 23 6.0后上面增加了一个Flag 就可以设置状态栏图标为黑色或者白色
            //public static final int SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            // （其实按照本意，是告诉系统状态栏顶部是白色的，
            // 需要按一个合适的模式去绘制状态栏，当然，其实就是黑色）。
            Window window = getWindow();
            int systemUiVisibility = window.getDecorView().getSystemUiVisibility();
            systemUiVisibility |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            window.getDecorView().setSystemUiVisibility(systemUiVisibility);
        }

        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mRefreshLayout = (RefreshLayout)findViewById(R.id.refreshLayout);
        mRefreshLayout.autoRefresh();//第一次进入触发自动刷新，演示效果

        View view = findViewById(R.id.recyclerView);
        if(view instanceof RecyclerView){
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(new BaseRecyclerAdapter<Item>(Arrays.asList(Item.values()),R.layout.listitem_style_delivery,this) {
                @Override
                protected void onBindViewHolder(SmartViewHolder holder, Item model, int position) {
                }
            });
        }

        StatusBarUtil.immersive(this);
        StatusBarUtil.setPaddingSmart(this,mToolbar);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (Item.values()[position]) {
            case 默认主题:
                mToolbar.setBackgroundResource(android.R.color.white);
                mToolbar.setTitleTextColor(0xffbbbbbb);
                mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_gray_24dp);
                mRefreshLayout.setPrimaryColors(0xfff0f0f0, 0xffffffff);
                if (Build.VERSION.SDK_INT >= 21) {
                    getWindow().setStatusBarColor(0xfff0f0f0);
                }
                if (Build.VERSION.SDK_INT >= 23) {
                    Window window = getWindow();
                    int systemUiVisibility = window.getDecorView().getSystemUiVisibility();
                    systemUiVisibility |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                    window.getDecorView().setSystemUiVisibility(systemUiVisibility);
                }
                break;
            case 蓝色主题:
                setThemeColor(R.color.colorPrimary, R.color.colorPrimaryDark);
                break;
            case 绿色主题:
                setThemeColor(android.R.color.holo_green_light, android.R.color.holo_green_dark);
                break;
            case 红色主题:
                setThemeColor(android.R.color.holo_red_light, android.R.color.holo_red_dark);
                break;
            case 橙色主题:
                setThemeColor(android.R.color.holo_orange_light, android.R.color.holo_orange_dark);
                break;
        }
    }

    private void setThemeColor(int colorPrimary, int colorPrimaryDark) {
        mToolbar.setBackgroundResource(colorPrimary);
        mToolbar.setTitleTextColor(ContextCompat.getColor(this, android.R.color.white));
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        mRefreshLayout.setPrimaryColorsId(colorPrimary, android.R.color.white);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, colorPrimaryDark));
        }
        if (Build.VERSION.SDK_INT >= 23) {
            Window window = getWindow();
            int systemUiVisibility = window.getDecorView().getSystemUiVisibility();
            systemUiVisibility &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            window.getDecorView().setSystemUiVisibility(systemUiVisibility);
        }
    }


}