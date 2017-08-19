package com.fule.mesurekeyheight;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.fule.mesurekeyheight.fragment.RefreshPractiveFragment;
import com.fule.mesurekeyheight.util.StatusBarUtil;

/**
 * 刷新框架：
 * https://github.com/scwang90/SmartRefreshLayout
 * 测试刷新框架
 */
public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";

    private enum TabFragment {
        practice(R.id.navigation_practice, RefreshPractiveFragment.class),
        styles(R.id.navigation_style, RefreshPractiveFragment.class),
        using(R.id.navigation_using, RefreshPractiveFragment.class);

        private Fragment fragment;
        private final int menuId;
        private final Class<? extends Fragment> clazz;

        TabFragment(@IdRes int menuId, Class<? extends Fragment> clazz) {
            this.menuId = menuId;
            this.clazz = clazz;
        }

        @NonNull
        public Fragment fragment() {
            if (fragment == null) {
                try {
                    fragment = clazz.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                    fragment = new Fragment();
                }
            }
            return fragment;
        }

        public static TabFragment from(int itemId) {
            for (TabFragment fragment : values()) {
                if (fragment.menuId == itemId) {
                    return fragment;
                }
            }
            return styles;
        }
        public static void onDestroy() {
            for (TabFragment fragment : values()) {
                fragment.fragment = null;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_main);
        //design包下的 底部导航栏   BottomNavigationView;
        final BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
//        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        navigation.setOnNavigationItemSelectedListener(this); //监听
        navigation.setSelectedItemId(R.id.navigation_style); //默认选中项
        //状态栏透明和间距处理
        StatusBarUtil.immersive(this, ContextCompat.getColor(this,R.color.colorPrimaryDark), 1f);

      /*  lv = (ListView) findViewById(R.id.lv);
        final RefreshLayout refreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);
        //设置 Header 为 Material风格
        refreshLayout.setRefreshHeader(new DeliveryHeader(this));
        //设置 Footer 为 球脉冲
        refreshLayout.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Scale));

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000);
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(2000);
            }
        });
*/
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //提交对应选中的fragment
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.content, TabFragment.from(item.getItemId()).fragment())
                .commit();
        return true;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        TabFragment.onDestroy();
    }
}
