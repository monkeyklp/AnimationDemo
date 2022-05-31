package klp.com.animationdemo;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.util.Properties;

public class CrashHandler implements UncaughtExceptionHandler {
    private static final String TAG = "CrashHandler";
    private static final String NEW_LINE = System.getProperty("line.separator");
    private static CrashHandler INSTANCE;
    private UncaughtExceptionHandler mDefaultHandler;
    private Properties mDeviceCrashInfo = new Properties();
    private Context mContext;

    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CrashHandler();
        }
        return INSTANCE;
    }

    /**
     * Initialize to get default UncaughtException handler and set it to be default handler.
     */
    public void init(Context context) {
        mContext = context;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        handleException(thread, throwable);
    }

    private void handleException(Thread thread, Throwable throwable) {
        if (throwable == null) {
            mDefaultHandler.uncaughtException(thread, throwable);
        }

//        final String crashReport = getCrashReport(throwable);
        String crashReport = getTraceInfo(throwable);
        Log.e(TAG, crashReport);

        CrashReportActivity.startActivity(mContext, crashReport);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    private String getTraceInfo(Throwable e) {
        PrintWriter printWriter = null;
        Writer info = new StringWriter();
        try {
            printWriter = new PrintWriter(info);
            e.printStackTrace(printWriter);
            Throwable cause = e.getCause();
            while (cause != null) {
                cause.printStackTrace(printWriter);
                cause = cause.getCause();
            }
            return info.toString();
        } finally {
            if (printWriter != null) {
                printWriter.close();
            }
        }
    }

    private String getCrashReport(Throwable ex) {
        PackageInfo packageInfo;
        JSONObject json = new JSONObject();
        try {
            packageInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(),
                    PackageManager.GET_ACTIVITIES);

            json.put("Version", packageInfo.versionName + "(" + packageInfo.versionCode + ")");
            json.put("Android", Build.VERSION.RELEASE + "(" + Build.MODEL + ")");
            json.put("Version info", getVersionInfo(mContext));
            json.put("Device info", getDeviceInfo());
            json.put("Exception", ex.getMessage());
            json.put("Exception stack", getTraceInfo(ex));
            return json.toString();
        } catch (JSONException e) {
            Log.e(TAG, "jsonException", e);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            Log.e(TAG, "NameNotFoundException", e);
        }
        return json.toString();
    }

    private String getVersionInfo(Context context) {
        StringBuilder sb = new StringBuilder();
        JSONObject json = new JSONObject();

        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (packageInfo != null) {
                String versionName = packageInfo.versionName == null ? "not set" : packageInfo.versionName;
                String versionCode = packageInfo.versionCode + "";

                json.put("versionName", versionName);
                json.put("versionCode", versionCode);
            }
        } catch (NameNotFoundException e) {
            Log.e(TAG, "an error occured when collect package info", e);
        } catch (JSONException e) {
            Log.e(TAG, "jsonException", e);
        }
        sb.append("[active Package]");
        sb.append(json.toString());
        return sb.toString();
    }

    private String getDeviceInfo() {
        JSONObject json = new JSONObject();
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                json.put(field.getName(), field.get(null).toString());
                Log.d(TAG, field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                Log.e(TAG, "an error occured when collect crash info", e);
            }
        }
        return json.toString();
    }
}
