package com.upshotreactlibrary;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;

import com.brandkinesis.BrandKinesis;
import com.brandkinesis.activitymanager.BKActivityTypes;
import com.brandkinesis.callback.BKAuthCallback;
import com.brandkinesis.callback.BrandKinesisCallback;
import com.brandkinesis.utils.BKAppStatusUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpshotApplication extends Application implements BKAppStatusUtil.BKAppStatusListener {

    public static final String PRIMARY_CHANNEL = "default";
    private static Application application;
    public static Bundle options = null;
    public static String initType = null;

    @Override
    public void onCreate() {
        super.onCreate();

        application = this;

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {            
            registerChannel();
        }
        BKAppStatusUtil.getInstance().register(this, this);
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
    }

    public static Application get() {
        return application;
    }

    //BKAppStatusListener Callbacks
    @Override
    public void onAppComesForeground(Activity activity) {

        if (initType != null) {
            if (initType == "Config") {
                initUpshotUsingConfig();
            } else if(initType == "Options"){
                if (options != null) {
                    initUpshotUsingOptions(options);
                }
            }
        }
    }

    @Override
    public void onAppGoesBackground() {
        final BrandKinesis brandKinesis = BrandKinesis.getBKInstance();
        brandKinesis.terminate(getApplicationContext());
    }

    @Override
    public void onAppRemovedFromRecentsList() {
    }


    @TargetApi(Build.VERSION_CODES.O)
    private void registerChannel() {
        String notificationsChannelId = "notifications";
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        List<NotificationChannel> channels = notificationManager.getNotificationChannels();
        NotificationChannel existingChanel = null;
        int count = 0;
        for (NotificationChannel channel : channels) {
            String fullId = channel.getId();
            if (fullId.contains(notificationsChannelId)) {
                existingChanel = channel;
                String[] numbers = extractRegexMatches(fullId, "\\d+");
                if (numbers.length > 0) {
                    count = Integer.valueOf(numbers[0]);
                }
                break;
            }
        }
        if (existingChanel != null) {
            if (existingChanel.getImportance() < NotificationManager.IMPORTANCE_DEFAULT) {
                notificationManager.deleteNotificationChannel(existingChanel.getId());
            }
        }

        String newId = existingChanel == null ? notificationsChannelId+'_'+(count+1) : existingChanel.getId();

        NotificationChannel channel = new NotificationChannel(
                newId, notificationsChannelId, NotificationManager.IMPORTANCE_HIGH);
        channel.setLightColor(Color.GREEN);
        channel.setLockscreenVisibility(Notification.VISIBILITY_SECRET);
        notificationManager.createNotificationChannel(channel);
    }

    public String[] extractRegexMatches(String source, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);

        ArrayList<String> matches = new ArrayList<>();
        while (matcher.find()) {
            matches.add(matcher.group());
        }

        String[] result = new String[matches.size()];
        matches.toArray(result);
        return result;
    }

    public static void initUpshotUsingConfig() {
        try {
            setUpshotGlobalCallbak();
            BrandKinesis.initialiseBrandKinesis(get(), new BKAuthCallback() {
                @Override
                public void onAuthenticationError(String errorMsg) {
                UpshotModule.upshotInitStatus(false, errorMsg);
                }
                @Override
                public void onAuthenticationSuccess() {
                    UpshotModule.upshotInitStatus(true, ""  );
                }
            });
        } catch (Exception e) {
        }
    }

    public static void initUpshotUsingOptions(Bundle options) {

        setUpshotGlobalCallbak();
        BrandKinesis.initialiseBrandKinesis(get(), options, null);
    }

    private static void setUpshotGlobalCallbak() {
        BrandKinesis bkInstance = BrandKinesis.getBKInstance();
        bkInstance.setBrandkinesisCallback(new BrandKinesisCallback() {
            @Override
            public void onActivityError(int i) {

            }
            @Override
            public void brandkinesisCampaignDetailsLoaded() {
                UpshotModule.upshotCampaignDetailsLoaded();
            }
            
            @Override
            public void onActivityCreated(BKActivityTypes bkActivityTypes) {
                UpshotModule.upshotActivityCreated(bkActivityTypes);
            }

            @Override
            public void onActivityDestroyed(BKActivityTypes bkActivityTypes, HashMap<String, Object> hashMap) {
                UpshotModule.upshotActivityDestroyed(bkActivityTypes);
            }

            @Override
            public void brandKinesisActivityPerformedActionWithParams(BKActivityTypes bkActivityTypes, Map<String, Object> map) {
                UpshotModule.upshotDeeplinkCallback(bkActivityTypes, map);
            }

            @Override
            public void onAuthenticationError(String errorMsg) {
                UpshotModule.upshotInitStatus(false, errorMsg);
            }

            @Override
            public void onAuthenticationSuccess() {
                UpshotModule.upshotInitStatus(true, ""  );
            }

            @Override
            public void onBadgesAvailable(HashMap<String, List<HashMap<String, Object>>> hashMap) {

            }

            @Override
            public void getBannerView(View view, String s) {

            }

            @Override
            public void onErrorOccurred(int i) {

            }

            @Override
            public void onMessagesAvailable(List<HashMap<String, Object>> list) {

            }

            @Override
            public void onUserInfoUploaded(boolean b) {

            }

            @Override
            public void userStateCompletion(boolean b) {

            }
        });
    }
}
