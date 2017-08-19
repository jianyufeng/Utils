package com.fule.mesurekeyheight.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.fule.mesurekeyheight.R;
import com.fule.mesurekeyheight.util.StatusBarUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

/**
 * Created by Administrator on 2017/8/19.
 */

public class FeedlistPracticeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_feedlist);
        final RefreshLayout refreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);
        refreshLayout.autoRefresh();
        StatusBarUtil.immersive(this);
        StatusBarUtil.setPaddingSmart(this, findViewById(R.id.toolbar));
        final Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
