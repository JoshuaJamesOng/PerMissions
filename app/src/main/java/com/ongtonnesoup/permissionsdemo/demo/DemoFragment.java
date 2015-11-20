package com.ongtonnesoup.permissionsdemo.demo;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ongtonnesoup.permissions.PermissionEvent;
import com.ongtonnesoup.permissionsdemo.R;
import com.squareup.otto.Bus;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class DemoFragment extends Fragment {

    private static final String TAG = "DemoFragment";

    @Inject
    Bus bus;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((DemoApplication) getActivity().getApplication()).getComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.demo_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.addContact)
    public void onButtonClick() {
        Log.d("JJO", "Button clicked");
        bus.post(new PermissionEvent(new String[]{Manifest.permission.WRITE_CONTACTS}, new Runnable() {
            @Override
            public void run() {
                Log.d("JJO", "Hello permissions");
            }
        }));
    }

}
