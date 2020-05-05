package com.reactlibrary.upshot.push;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.brandkinesis.BrandKinesis;
import com.brandkinesis.utils.BKUtilLogger;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.upshot.MainActivity;
import com.upshot.R;

import java.util.Map;

import static com.brandkinesis.push.internal.BKNotificationUtil.addChannelSupport;

/**
 * Created by PurpleTalk on 30/12/16.
 */
public class UpshotFirebaseMessagingService extends FirebaseMessagingService {

    private final static String TAG = "BKFBaseMessagingService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Bundle bundle = new Bundle();
        for (Map.Entry<String, String> entry : remoteMessage.getData().entrySet()) {
            BKUtilLogger.devD(entry.getKey() + ", valu :" + entry.getValue());
            bundle.putString(entry.getKey(), entry.getValue());
        }

        if (bundle.containsKey("bk")) {

//            bundle.putInt(BrandKinesis.BK_LOLLIPOP_NOTIFICATION_ICON, R.drawable.not_selected);
            bundle.putInt(BrandKinesis.BK_LOLLIPOP_NOTIFICATION_ICON_BG_COLOR, Color.BLUE);
            sendPushBundletoBK(bundle, this);
        } else {
            String title = "";
            String text = "";
            try {

                title = bundle.getString("title");
                text = bundle.getString("body");
            } catch (Exception e) {
            }
            //TODO have to handle application
            sendNotification(title, text,bundle);
        }
    }

    //This method is only generating push notification
    //It is same as we did in earlier posts
    private void sendNotification(String title, String messageBody, Bundle bundle) {

        Class mainActivity = null;
        Context context = getApplicationContext();
        String packageName = context.getPackageName();
        Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        String className = launchIntent.getComponent().getClassName();
        try { //loading the Main Activity to not import it in the plugin
            mainActivity = Class.forName(className);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mainActivity == null) {
            return;
        }

        int notificationId = UpshotEnhancedPushUtils.getIdFromTimestamp();
        Intent notifyIntent = new Intent(this, mainActivity);
        notifyIntent.putExtra("push", true);
notifyIntent.putExtras(bundle);// Create the PendingIntent
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, notificationId, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
        );

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        int applicationIcon = UpshotEnhancedPushUtils.getApplicationIcon(this);
        Bitmap iconBitmap = BitmapFactory.decodeResource(getResources(), applicationIcon);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(applicationIcon)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setLargeIcon(iconBitmap);



        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        addChannelSupport(this, notificationBuilder);

        notificationManager.notify(notificationId, notificationBuilder.build());
    }

    private void sendPushBundletoBK(final Bundle pushBundle, final Context mContext) {
        Log.d(TAG, "Push Bundle: " + pushBundle.toString());
        final boolean allowPushForeground = true;
        BrandKinesis bkInstance1 = BrandKinesis.getBKInstance();
        bkInstance1.buildEnhancedPushNotification(mContext, pushBundle, allowPushForeground);
    }
}
