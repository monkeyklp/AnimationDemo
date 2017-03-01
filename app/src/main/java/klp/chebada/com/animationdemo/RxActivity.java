package klp.chebada.com.animationdemo;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import klp.chebada.com.animationdemo.databinding.ActivityRxBinding;

/**
 * Created by monkey on 17/2/28.
 */

public class RxActivity extends AppCompatActivity {
    private static final String TAG = "RxActivity";

    private ActivityRxBinding mBinding;

    public static void startActivity(Activity fromActivity) {
        Intent intent = new Intent(fromActivity, RxActivity.class);
        fromActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_rx);
        mBinding.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Observable.defer(new Callable<ObservableSource<? extends String>>() {
                    @Override
                    public ObservableSource<? extends String> call() throws Exception {
                        SystemClock.sleep(500);
                        return Observable.just("test1", "test2", "test3");
                    }
                })
                        //.throttleFirst(500, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<String>(){
                    @Override
                    public void onNext(String value) {
                        Log.e(TAG, value);
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "Complete");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
            }
        });
    }
}
