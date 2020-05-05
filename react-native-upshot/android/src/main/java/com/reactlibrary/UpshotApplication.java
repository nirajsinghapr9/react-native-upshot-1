package com.reactlibrary;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.StrictMode;

import com.brandkinesis.BrandKinesis;
import com.brandkinesis.callback.BKAuthCallback;
import com.brandkinesis.utils.BKAppStatusUtil;

public class UpshotApplication extends Application implements BKAppStatusUtil.BKAppStatusListener {

    public static final String PRIMARY_CHANNEL = "default";
    private static Application application;

    @Override
    public void onCreate() {
        super.onCreate();

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());


        application = this;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerStatusChannel();
            registerChannel();
        }
        BKAppStatusUtil.getInstance().register(this, this);
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void registerStatusChannel() {
        NotificationChannel channel = new NotificationChannel(
                "notifications", "Primary Channel", NotificationManager.IMPORTANCE_MIN);
        channel.setLightColor(Color.GREEN);
        channel.setLockscreenVisibility(Notification.VISIBILITY_SECRET);
        ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void registerChannel() {
        NotificationChannel channel = new NotificationChannel(
                PRIMARY_CHANNEL, "Primary Channel", NotificationManager.IMPORTANCE_MIN);
        channel.setLightColor(Color.GREEN);
        channel.setLockscreenVisibility(Notification.VISIBILITY_SECRET);
        ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
    }

    public static Application get() {
        return application;
    }

    public static void initBrandKinesis(Activity activity) {
        try {
            BrandKinesis.initialiseBrandKinesis(activity, new BKAuthCallback() {
                @Override
                public void onAuthenticationError(String errorMsg) {
                UpshotModule.upshotInitStatus(false, errorMsg);}

                @Override
                public void onAuthenticationSuccess() {
                    UpshotModule.upshotInitStatus(true, ""  );}
            });
        } catch (Exception e) {
        }
    }


    @Override
    public void onAppComesForeground(Activity activity) {
        initBrandKinesis(activity);
    }

    @Override
    public void onAppGoesBackground() {
        final BrandKinesis brandKinesis = BrandKinesis.getBKInstance();
        brandKinesis.terminate(getApplicationContext());

    }

    @Override
    public void onAppRemovedFromRecentsList() {
    }
}
