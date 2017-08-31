package com.fule.mesurekeyheight.refreshFrame.fragment;


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
import com.fule.mesurekeyheight.refreshFrame.activity.BannerPracticeActivity;
import com.fule.mesurekeyheight.refreshFrame.activity.FeedlistPracticeActivity;
import com.fule.mesurekeyheight.refreshFrame.activity.ProfilePracticeActivity;
import com.fule.mesurekeyheight.refreshFrame.activity.QQBrowserPracticeActivity;
import com.fule.mesurekeyheight.refreshFrame.activity.RepastPracticeActivity;
import com.fule.mesurekeyheight.refreshFrame.activity.WebviewPracticeActivity;
import com.fule.mesurekeyheight.refreshFrame.activity.WeiboPracticeActivity;
import com.fule.mesurekeyheight.refreshFrame.style.BezierStyleActivity;
import com.fule.mesurekeyheight.refreshFrame.style.CircleStyleActivity;
import com.fule.mesurekeyheight.refreshFrame.style.ClassicsStyleActivity;
import com.fule.mesurekeyheight.refreshFrame.style.DeliveryStyleActivity;
import com.fule.mesurekeyheight.refreshFrame.style.DropboxStyleActivity;
import com.fule.mesurekeyheight.refreshFrame.style.FlyRefreshStyleActivity;
import com.fule.mesurekeyheight.refreshFrame.style.FunGameBattleCityStyleActivity;
import com.fule.mesurekeyheight.refreshFrame.style.FunGameHitBlockStyleActivity;
import com.fule.mesurekeyheight.refreshFrame.style.MaterialStyleActivity;
import com.fule.mesurekeyheight.refreshFrame.style.PhoenixStyleActivity;
import com.fule.mesurekeyheight.refreshFrame.style.StoreHouseStyleActivity;
import com.fule.mesurekeyheight.refreshFrame.style.TaurusStyleActivity;
import com.fule.mesurekeyheight.refreshFrame.style.WaterDropStyleActivity;
import com.fule.mesurekeyheight.refreshFrame.style.WaveSwipStyleActivity;
import com.fule.mesurekeyheight.refreshFrame.using.AssignCodeUsingActivity;
import com.fule.mesurekeyheight.refreshFrame.using.AssignDefaultUsingActivity;
import com.fule.mesurekeyheight.refreshFrame.using.AssignXmlUsingActivity;
import com.fule.mesurekeyheight.refreshFrame.using.BasicUsingActivity;
import com.fule.mesurekeyheight.refreshFrame.using.CustomUsingActivity;
import com.fule.mesurekeyheight.refreshFrame.using.ListenerUsingActivity;
import com.fule.mesurekeyheight.refreshFrame.using.NestLayoutUsingActivity;
import com.fule.mesurekeyheight.refreshFrame.using.SnapHelperUsingActivity;
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
        ((AppCompatActivity) getActivity()).setSupportActionBar((Toolbar) view.findViewById(R.id.toolbar));
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
                    case 10:
                        startActivity(new Intent(getContext(), WaveSwipStyleActivity.class));
                        break;
                    case 11:
                        startActivity(new Intent(getContext(), WaterDropStyleActivity.class));
                        break;
                    case 12:
                        startActivity(new Intent(getContext(), MaterialStyleActivity.class));
                        break;
                    case 13:
                        startActivity(new Intent(getContext(), PhoenixStyleActivity.class));
                        break;
                    case 14:
                        startActivity(new Intent(getContext(), TaurusStyleActivity.class));
                        break;
                    case 15:
                        startActivity(new Intent(getContext(), BezierStyleActivity.class));
                        break;
                    case 16:
                        startActivity(new Intent(getContext(), CircleStyleActivity.class));
                        break;
                    case 17:
                        startActivity(new Intent(getContext(), FunGameHitBlockStyleActivity.class));
                        break;
                    case 18:
                        startActivity(new Intent(getContext(), FunGameBattleCityStyleActivity.class));
                        break;
                    case 19:
                        startActivity(new Intent(getContext(), StoreHouseStyleActivity.class));
                        break;
                    case 20:
                        startActivity(new Intent(getContext(), ClassicsStyleActivity.class));
                        break;
                    case 21:
                        startActivity(new Intent(getContext(), BasicUsingActivity.class));
                        break;
                    case 22:
                        startActivity(new Intent(getContext(), AssignDefaultUsingActivity.class));
                        break;
                    case 23:
                        startActivity(new Intent(getContext(), AssignXmlUsingActivity.class));
                        break;
                    case 24:
                        startActivity(new Intent(getContext(), AssignCodeUsingActivity.class));
                        break;
                    case 25:
                        startActivity(new Intent(getContext(), ListenerUsingActivity.class));
                        break;
                    case 26:
                        startActivity(new Intent(getContext(), NestLayoutUsingActivity.class));
                        break;
                    case 27:
                        startActivity(new Intent(getContext(), CustomUsingActivity.class));
                        break;
                    case 28:
                        startActivity(new Intent(getContext(), SnapHelperUsingActivity.class));
                        break;

                    default:
                        break;
                }
            }
        });
    }
}
