package klp.com.animationdemo;

import android.app.Application;

import klp.com.animationdemo.util.SdkApplicationUtils;

public class DemoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SdkApplicationUtils.setApplication(this);
    }
}
