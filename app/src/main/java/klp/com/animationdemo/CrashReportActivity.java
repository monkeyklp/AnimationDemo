package klp.com.animationdemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by zhangfei on 2015/6/6.
 */

public class CrashReportActivity extends AppCompatActivity {
    private static final String TAG = "CrashReportActivity";
    private static final String EXTRA_CRASH_REPORT = "crash_report";

    private String mCrashReport;

    public static void startActivity(Context context, String crashReport) {
        Intent intent = new Intent(context, CrashReportActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(EXTRA_CRASH_REPORT, crashReport);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crash_report_activity);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }

        mCrashReport = getIntent().getStringExtra(EXTRA_CRASH_REPORT);

        TextView reportDetailText = findViewById(R.id.reportDetailText);
        reportDetailText.setText(mCrashReport);


        findViewById(R.id.viewOrPostBtn).setOnClickListener(v -> postCrashLog(mCrashReport));

        findViewById(R.id.closeBtn).setOnClickListener(v -> finish());

        setTitle("啊…(⊙o⊙)…貌似崩溃了");
    }

    @SuppressLint("StaticFieldLeak")
    private void postCrashLog(final String crashLog) {
//        LoadingDialog dialog = new LoadingDialog(this);
//        dialog.setCancelable(false);
//
//        new AsyncTask<Void, Void, Boolean>() {
//
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//                dialog.show();
//            }
//
//            @Override
//            protected Boolean doInBackground(Void... params) {
//                String extend = String.format(Locale.CHINESE, "系统版本：%s,app版本：%s,品牌：%s,型号：%s,登录手机号：%s,构建时间：%s",
//                        Build.VERSION.RELEASE, // 系统版本 例:8.0
//                        AppUtils.getVersionName(getApplicationContext()), // app版本 例:6.0.0
//                        Build.BRAND,// 品牌 例:miui
//                        Build.MODEL, // 型号
//                        CbdPreferences.getPhoneNumber(getApplicationContext()),
//                        getBuildTime(getApplicationContext())); // 注册用户手机号
//
//                FormBody.Builder builder = new FormBody.Builder();
//                builder.add("platform", "Android");
//                builder.add("crashLog", crashLog);
//                builder.add("extend", extend);
//
//                Request request = new Request.Builder()
//                        .url("http://10.102.52.44:8088/crashLog/save")
//                        .post(builder.build())
//                        .build();
//                try {
//                    Response response = new OkHttpClient().newCall(request).execute();
//                    return response.code() == 200;
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    Log.d(TAG, "post failed due to " + e.getMessage());
//                }
//                return false;
//            }
//
//            @Override
//            protected void onPostExecute(Boolean result) {
//                super.onPostExecute(result);
//                dialog.dismiss();
//                if (result) {
//                    UiUtils.showToast(getApplicationContext(), "Good job, thanks!");
//                    finish();
//                } else {
//                    UiUtils.showToast(getApplicationContext(), "Failed to post crash log, sorry!");
//                }
//            }
//        }.execute();
    }

    @NonNull
    private static String getBuildTime(Context context) {
        String build_time = null;
        try {
            ApplicationInfo pInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            build_time = pInfo.metaData.getString("BUILD_TIME");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return build_time == null ? "" : build_time;
    }
}
