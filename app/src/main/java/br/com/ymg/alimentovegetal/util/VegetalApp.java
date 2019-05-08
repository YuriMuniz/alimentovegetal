package br.com.ymg.alimentovegetal.util;


import android.app.Application;

import com.squareup.otto.Bus;

import br.com.ymg.alimentovegetal.R;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class VegetalApp extends Application{
    Bus mBus;

    @Override
    public void onCreate() {
        super.onCreate();
        mBus = new Bus();
        CalligraphyConfig.initDefault(
                new CalligraphyConfig.Builder()
                        .setDefaultFontPath("Lobster_Regular.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
    }

    public Bus getBus(){
        return mBus;
    }
}
