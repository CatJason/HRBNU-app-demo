package com.example.jason.mxlake.changeavatar.viewModel;

import android.databinding.BaseObservable;

public abstract class ViewModel extends BaseObservable {

    public abstract void onDestroy();
    public abstract void onResume();
}
