package klp.chebada.com.animationdemo;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by 柚柚的PC on 2016/4/24.
 */
public class NotificationActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 0;
    private Button mButton;
    public static void startActivity(Activity fromActivity){
        Intent intent = new Intent(fromActivity, NotificationActivity.class);
        fromActivity.startActivity(intent);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        mButton = (Button)findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buildNotification();
            }
        });
    }

    public void buildNotification(){
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.notification_icon)
                        .setContentTitle("My notification")
//                        .setColor()
                        .setContentText("Hello World!");
        mBuilder.setContentIntent(deFaultResultIntent());

        int mNotificationId = 001;
// Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
// Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }

    private PendingIntent deFaultResultIntent(){
        Intent intent = new Intent(this, NotificationActivity.class);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        REQUEST_CODE,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
        return resultPendingIntent;
    }
}
