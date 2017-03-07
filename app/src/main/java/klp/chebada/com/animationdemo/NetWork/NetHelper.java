package klp.chebada.com.animationdemo.NetWork;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by monkey on 17/3/1.
 *
 */

public class NetHelper {
    private static final long TIME_OUT = 100*6;
    private static String BASE_URL = "";
    private Retrofit mRetrofit;
    private NetHelper() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        mRetrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }

    public static NetHelper getInstance() {
        return NetHelperHolder.mNetHelper;
    }

    private static class NetHelperHolder {
        private static final NetHelper mNetHelper = new NetHelper();
    }
}
