package com.upshotreactlibrary.upshot.push;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.brandkinesis.BrandKinesis;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.upshotreactlibrary.UpshotModule;

import java.util.Map;


/**
 * Created by PurpleTalk on 30/12/16.
 */
public class UpshotFirebaseMessagingService extends FirebaseMessagingService {

    private final static String TAG = "BKFBaseMessagingService";

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        UpshotModule.sendRegistrationToServer(token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Bundle bundle = new Bundle();
        for (Map.Entry<String, String> entry : remoteMessage.getData().entrySet()) {
            bundle.putString(entry.getKey(), entry.getValue());
        }
        Context context = getApplicationContext();
        ApplicationInfo applicationInfo = null;
        String packageName = context.getPackageName();

        try {
            applicationInfo = context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
        }
        Bundle metaData = applicationInfo.metaData;

        if (bundle.containsKey("bk")) {

            String bkSmallNotificationIcon = null;
            Integer bkSmallNotificationIconColor = null;
            boolean allowForeground = false;

            if (metaData != null) {
                bkSmallNotificationIcon = metaData.getString("UpshotPushSmallIcon");
                bkSmallNotificationIconColor = metaData.getInt("UpshotPushSmallIconColor");
                allowForeground = metaData.getBoolean("UpshotAllowForegroundPush");
            }
            if (!TextUtils.isEmpty(bkSmallNotificationIcon)) {
                Resources resources = context.getResources();
                int resourceId = resources.getIdentifier(bkSmallNotificationIcon, "drawable", packageName);
                if(resourceId > 0) {
                    bundle.putInt(BrandKinesis.BK_LOLLIPOP_NOTIFICATION_ICON, resourceId);
                }
                
            }
            if (bkSmallNotificationIconColor != null) {
                bundle.putInt(BrandKinesis.BK_LOLLIPOP_NOTIFICATION_ICON_BG_COLOR, bkSmallNotificationIconColor);
            }
            sendPushBundletoBK(bundle, this, allowForeground);
        } else {

            boolean allow = false;
            if (metaData != null) {
                allow = metaData.getBoolean("UpshotShowOtherPushes", false);
            }
            if(allow){
                try {
                    String title = bundle.getString("title");
                    String text = bundle.getString("body");
                    if (!title.isEmpty() && !text.isEmpty()) {
                        sendNotification(title, text,bundle);
                    }
                } catch (Exception e) {
                }
            }
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
    public static void addChannelSupport(Context context, Notification.Builder notificationBuilder) {
        if (Build.VERSION.SDK_INT >= 26) {
            CharSequence name = "notifications";
            int importance = 4;
            NotificationChannel mChannel = new NotificationChannel("notifications", name, NotificationManager.IMPORTANCE_HIGH);
            @SuppressLint("WrongConstant") NotificationManager notificationManager = (NotificationManager)context.getSystemService("notification");
            notificationManager.createNotificationChannel(mChannel);
            notificationBuilder.setChannelId("notifications");
        }
    }

    public static void addChannelSupport(Context context, androidx.core.app.NotificationCompat.Builder notificationBuilder) {
        if (Build.VERSION.SDK_INT >= 26) {
            CharSequence name = "notifications";
            NotificationChannel mChannel = new NotificationChannel("notifications", name, NotificationManager.IMPORTANCE_HIGH);
            @SuppressLint("WrongConstant") NotificationManager notificationManager = (NotificationManager)context.getSystemService("notification");
            notificationManager.createNotificationChannel(mChannel);
            notificationBuilder.setChannelId("notifications");
        }
    }
    private void sendPushBundletoBK(final Bundle pushBundle, final Context mContext, boolean allowPushForeground) {

        BrandKinesis bkInstance1 = BrandKinesis.getBKInstance();
        bkInstance1.buildEnhancedPushNotification(mContext, pushBundle, allowPushForeground);
    }
}
