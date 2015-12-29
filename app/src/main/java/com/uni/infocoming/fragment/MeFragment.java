package com.uni.infocoming.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uni.infocoming.BaseFragment;
import com.uni.infocoming.R;

/**
 * Created by Razer on 2015/12/25.
 */
public class MeFragment extends BaseFragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = View.inflate(activity, R.layout.fragment_me,null);
        return view;
    }
}
