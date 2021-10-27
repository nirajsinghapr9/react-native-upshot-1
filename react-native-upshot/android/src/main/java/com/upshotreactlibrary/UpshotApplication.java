package com.upshotreactlibrary;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.brandkinesis.BKUIPrefComponents;
import com.brandkinesis.BrandKinesis;
import com.brandkinesis.activitymanager.BKActivityTypes;
import com.brandkinesis.callback.BKAuthCallback;
import com.brandkinesis.callback.BrandKinesisCallback;
import com.brandkinesis.utils.BKAppStatusUtil;

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
    public static Activity mactivity = null;

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
        this.mactivity = activity;
        if (initType != null) {
            if (initType == "Config") {
                initUpshotUsingConfig();
            } else if(initType == "Options"){
                if (options != null) {
                    initUpshotUsingOptions(options);
                }
            }
        }


        applyCustomization();
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

    private void applyCustomization() {

        BrandKinesis bkInstance = BrandKinesis.getBKInstance();
      //  bkInstance.setUIPreferences(customUipreferences);
    }
    private static void setUpshotGlobalCallbak() {
        BrandKinesis bkInstance = BrandKinesis.getBKInstance();
//        bkInstance.setUIPreferences(customUipreferences);
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
            public void onActivityDestroyed(BKActivityTypes bkActivityTypes,HashMap<String, Object> responsePayload) {
                UpshotModule.upshotActivityDestroyed(bkActivityTypes, responsePayload);
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

    BKUIPrefComponents customUipreferences = new BKUIPrefComponents() {
        BKTriviaCustomization triviaCustomization = new BKTriviaCustomization(get());

        @Override
        public void setPreferencesForTextView(TextView textView, BKActivityTypes activityType, BKActivityTextViewTypes textViewType) {

            switch (activityType) {
                case ACTIVITY_TRIVIA:
                    triviaCustomization.customizeTextView(textViewType, textView);
                    break;
                default:
                    break;
            }
        }

        @Override
        public void setPreferencesForImageView(ImageView imageView, BKActivityTypes activityType, BKActivityImageViewType imageType) {

            switch (activityType) {
                case ACTIVITY_TRIVIA:
                    triviaCustomization.customizeImageView(imageView, imageType);
                    break;
            }
        }

        @Override
        public void setPreferencesForOptionsSeparatorView(View view, BKActivityTypes activityTypes) {
            switch (activityTypes) {

                case ACTIVITY_TRIVIA:
                    triviaCustomization.customizeForOptionsSeparatorView(view);
                    break;
            }
        }

        @Override
        public void setCheckBoxRadioSelectorResource(BKUICheckBox checkBox, BKActivityTypes activityType, boolean isCheckBox) {
            switch (activityType) {
                case ACTIVITY_TRIVIA:
                    triviaCustomization.customizeRadioButton(checkBox, isCheckBox);
                    break;
            }
        }

        @Override
        public void setRatingSelectorResource(List<Bitmap> selectedRatingBitmapList, List<Bitmap> unSelectedRatingBitmapList, BKActivityTypes activityType, BKActivityRatingTypes ratingType) {
        }

        @Override
        public void setPreferencesForRelativeLayout(RelativeLayout relativeLayout, BKActivityTypes activityType, BKActivityRelativeLayoutTypes relativeLayoutType, boolean fullScreen) {
            switch (activityType) {
                case ACTIVITY_TRIVIA:
                    triviaCustomization.customizeRelativeLayout(relativeLayoutType, relativeLayout, fullScreen);
                    break;

                default:
                    break;
            }
        }

        @Override
        public void setPreferencesForImageButton(ImageButton button, BKActivityTypes activityType, BKActivityImageButtonTypes buttonType) {
            switch (activityType) {
                case ACTIVITY_TRIVIA:
                    triviaCustomization.customizeImageButton(button, buttonType);
                    break;

                default:
                    break;
            }
        }

        @Override
        public void setPreferencesForButton(Button button, BKActivityTypes activityType, BKActivityButtonTypes buttonType) {
            switch (activityType) {

                case ACTIVITY_TRIVIA:
                    triviaCustomization.customizeButton(button, buttonType);
                default:
                    break;
            }
        }

        @Override
        public void setPreferencesForUIColor(BKBGColors color, BKActivityTypes activityType, BKActivityColorTypes colorType) {
            switch (activityType) {

                case ACTIVITY_TRIVIA:
                    triviaCustomization.customizeBGColor(color, colorType);
                    break;

                default:
                    break;
            }
        }

        @Override
        public void setPreferencesForGraphColor(BKGraphType graphType, List<Integer> colors, BKActivityTypes activityType) {
            switch (activityType) {

                case ACTIVITY_TRIVIA:
                    switch (graphType) {

                        case BKACTIVITY_BAR_GRAPH:
                            colors.add(Color.RED);
                            colors.add(Color.BLACK);
                            colors.add(Color.BLUE);
                            colors.add(Color.GREEN);
                            colors.add(Color.CYAN);
                            triviaCustomization.customizeForGraphColor(graphType, colors);
                            break;
                        case BKACTIVITY_PIE_GRAPH:
                            colors.add(Color.RED);
                            colors.add(Color.BLACK);
                            colors.add(Color.BLUE);
                            colors.add(Color.GREEN);
                            colors.add(Color.CYAN);
                            triviaCustomization.customizeForGraphColor(graphType, colors);
                            break;
                    }
            }
        }

        @Override
        public int getPositionPercentageFromBottom(BKActivityTypes bkActivityType, BKViewType viewType) {
            switch (bkActivityType) {
                case ACTIVITY_TUTORIAL:
                    switch (viewType) {
                        case BKACTIVITY_PAGINATION_VIEW:
                            return 0;
                        case BKACTIVITY_BUTTON_SECTION_VIEW:
                            return 0;
                    }
                    break;
            }
            return 0;
        }

        @Override
        public void setPreferencesForSeekBar(SeekBar seekBar, BKActivityTypes activityTypes, BKActivitySeekBarTypes bkActivitySeekBarTypes) {

        }

        @Override
        public void setPreferencesForEditText(EditText editText, BKActivityTypes activityType, BKActivityEditTextTypes editTextTypes) {

        }

        @Override
        public void setPreferencesForLinearLayout(LinearLayout linearLayout, BKActivityTypes activityType, BKActivityLinearLayoutTypes linearLayoutTypes) {
            switch (activityType) {
                case ACTIVITY_TRIVIA:
                    triviaCustomization.customizeForLinearLayout(linearLayout, linearLayoutTypes);
                    break;
            }
        }
    };
}
