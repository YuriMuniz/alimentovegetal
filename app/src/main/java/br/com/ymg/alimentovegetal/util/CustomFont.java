package br.com.ymg.alimentovegetal.util;


import android.app.Application;

import br.com.ymg.alimentovegetal.R;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class CustomFont extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(
                new CalligraphyConfig.Builder()
                        .setDefaultFontPath("Lobster_Regular.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
    }
}
