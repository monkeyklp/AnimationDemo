package klp.com.animationdemo.util;

import android.app.Application;

/**
 * 提供全局获取Application能力
 */
public class SdkApplicationUtils {

    private static Application app;

    public static void setApplication(Application app) {
        SdkApplicationUtils.app = app;
    }

    /**
     * 获取当前运用 application对象,任何地方，任何时间都可以直接获取到，不存在失败的问题。
     *
     * @return
     */
    public static Application getApplication() {
        return app;
    }
}
