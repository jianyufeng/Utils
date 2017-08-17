package com.fule.mesurekeyheight.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fule.mesurekeyheight.R;
import com.fule.mesurekeyheight.activity.RepastPracticeActivity;
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

        ListView lv = (ListView) view.findViewById(R.id.recyclerView);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getContext(),RepastPracticeActivity.class));
            }
        });
    }
}
