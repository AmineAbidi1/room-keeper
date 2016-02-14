package com.roomkeeper;

import android.app.Application;

public class PearlyApplicataion extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initImageLoader();

    }

    private void initImageLoader() {
//        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
//        ImageLoader.getInstance().init(config);
    }
}
