package com.ongtonnesoup.permissionsdemo.demo;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ongtonnesoup.permissions.PerMissionsEvent;
import com.ongtonnesoup.permissions.flow.PerMissionsContinueFlow;
import com.ongtonnesoup.permissions.flow.PerMissionsDeniedFlow;
import com.ongtonnesoup.permissionsdemo.R;
import com.squareup.otto.Bus;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class DemoFragment extends Fragment {

    private static final String TAG = "DemoFragment";

    @Inject
    Bus bus;

    @Inject
    DemoFragmentPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "create clicked");
        super.onCreate(savedInstanceState);
        ((DemoApplication) getActivity().getApplication()).getComponent().inject(this);
        presenter.setActivity(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.demo_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDetach() {
        if (presenter != null) {
            presenter.setActivity(null);
        }
        super.onDetach();
    }

    @OnClick(R.id.viewContacts)
    public void onViewContactsButtonClick() {
        bus.post(new PerMissionsEvent(new String[]{Manifest.permission.READ_CONTACTS}, new PerMissionsContinueFlow() {
            @Override
            public void onGranted() throws SecurityException {
                presenter.onViewContacts();
            }
        }, new PerMissionsDeniedFlow() {
            @Override
            public void onDenied() throws SecurityException {
            }
        }));
    }

    @OnClick(R.id.addContact)
    public void onContactButtonClick() {
        bus.post(new PerMissionsEvent(new String[]{Manifest.permission.WRITE_CONTACTS}, new PerMissionsContinueFlow() {
            @Override
            public void onGranted() throws SecurityException {
                presenter.onAddContact();
            }
        }, new PerMissionsDeniedFlow() {
            @Override
            public void onDenied() throws SecurityException {
            }
        }));
    }

    @OnClick(R.id.takePicture)
    public void onPictureButtonClick() {
        bus.post(new PerMissionsEvent(new String[]{Manifest.permission.CAMERA}, new PerMissionsContinueFlow() {
            @Override
            public void onGranted() throws SecurityException {
                presenter.onTakePhoto();
            }
        }, new PerMissionsDeniedFlow() {
            @Override
            public void onDenied() throws SecurityException {
            }
        }));
    }

}
