package klp.chebada.com.animationdemo;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import klp.chebada.com.animationdemo.databinding.ActivityMessageBinding;

/**
 * Created by klp13115 on 2016/8/22.
 *
 * When you create a new Handler, it is bound to the thread /
 * message queue of the thread that is creating it -- from that point on,
 * it will deliver messages and runnables to that message queue and execute
 * them as they come out of the message queue
 */
public class MessageActivity extends AppCompatActivity {

    private static final String TAG = "MessageActivity";
    private ActivityMessageBinding mBinding;
    private Handler workHandler;

    final MainHandler handler = new MainHandler();
    public static void startActivity(Activity fromActivity) {
        Intent intent = new Intent(fromActivity, MessageActivity.class);
        fromActivity.startActivity(intent);
    }
    class MainHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_message);
        mBinding.btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MessageActivity.this, "Handler线程id"+ Thread.currentThread().getId(), Toast.LENGTH_SHORT).show();
                handler.sendEmptyMessageAtTime(1, 1000);
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            Thread.sleep(1000);
//                            Toast.makeText(MessageActivity.this, "Handler线程id"+ Thread.currentThread().getId(), Toast.LENGTH_SHORT).show();
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
            }
        });

        mBinding.btnWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WorkThread();
            }
        });
        Log.e(TAG, "UI线程" + Thread.currentThread().getId());
    }

    private void WorkThread() {
        HandlerThread thread = new HandlerThread("workThread");
        thread.start();

        workHandler  = new Handler(thread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Log.e(TAG, "workHandler处理进程"+ Thread.currentThread().getId());
            }
        };
        Log.e(TAG, "HandlerThread线程"+ thread.getId());
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "workHandler发起进程"+ Thread.currentThread().getId());
                workHandler.sendEmptyMessageAtTime(1, 1000);
            }
        }).start();

//        workHandler.post(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(1000);
//                    Toast.makeText(MessageActivity.this, "workHandler处理进程"+ Thread.currentThread().getId(), Toast.LENGTH_SHORT).show();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }
}
