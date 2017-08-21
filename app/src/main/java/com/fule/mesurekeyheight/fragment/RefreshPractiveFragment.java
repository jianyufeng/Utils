package com.fule.mesurekeyheight.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fule.mesurekeyheight.R;
import com.fule.mesurekeyheight.activity.BannerPracticeActivity;
import com.fule.mesurekeyheight.activity.FeedlistPracticeActivity;
import com.fule.mesurekeyheight.activity.ProfilePracticeActivity;
import com.fule.mesurekeyheight.activity.QQBrowserPracticeActivity;
import com.fule.mesurekeyheight.activity.RepastPracticeActivity;
import com.fule.mesurekeyheight.activity.WebviewPracticeActivity;
import com.fule.mesurekeyheight.activity.WeiboPracticeActivity;
import com.fule.mesurekeyheight.style.DeliveryStyleActivity;
import com.fule.mesurekeyheight.style.DropboxStyleActivity;
import com.fule.mesurekeyheight.style.FlyRefreshStyleActivity;
import com.fule.mesurekeyheight.util.StatusBarUtil;

/**
 * Created by Administrator on 2017/8/16.
 */

public class RefreshPractiveFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_refresh_practive, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        StatusBarUtil.setPaddingSmart(getContext(), view.findViewById(R.id.toolbar));
        ((AppCompatActivity)getActivity()).setSupportActionBar((Toolbar) view.findViewById(R.id.toolbar));
        ListView lv = (ListView) view.findViewById(R.id.recyclerView);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(getContext(), RepastPracticeActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(getContext(), ProfilePracticeActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(getContext(), WebviewPracticeActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(getContext(), WeiboPracticeActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(getContext(), FeedlistPracticeActivity.class));
                        break;
                    case 5:
                        startActivity(new Intent(getContext(), BannerPracticeActivity.class));
                        break;
                    case 6:
                        startActivity(new Intent(getContext(), QQBrowserPracticeActivity.class));
                        break;
                    case 7:
                        startActivity(new Intent(getContext(), DeliveryStyleActivity.class));
                        break;
                    case 8:
                        startActivity(new Intent(getContext(), DropboxStyleActivity.class));
                        break;
                    case 9:
                        startActivity(new Intent(getContext(), FlyRefreshStyleActivity.class));
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
