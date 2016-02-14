package com.roomkeeper;

import android.app.Application;

import io.relayr.android.RelayrSdk;


public class PearlyApplicataion extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initImageLoader();

//        new RelayrSdk.Builder(this).inMockMode(false).build();
    }

    private void initImageLoader() {
//        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
//        ImageLoader.getInstance().init(config);
    }
}
