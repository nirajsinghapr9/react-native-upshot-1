package com.reactlibrary;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.brandkinesis.BKProperties;
import com.brandkinesis.BKUIPrefComponents;
import com.brandkinesis.BKUserInfo;
import com.brandkinesis.BrandKinesis;
import com.brandkinesis.activitymanager.BKActivityTypes;
import com.brandkinesis.callback.BKActivityCallback;
import com.brandkinesis.callback.BKDispatchCallback;
import com.brandkinesis.callback.BKInboxAccessListener;
import com.brandkinesis.callback.BKUserInfoCallback;
import com.brandkinesis.rewards.BKRewardsResponseListener;
import com.brandkinesis.utils.BKUtilLogger;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.facebook.react.bridge.UiThreadUtil.runOnUiThread;


public class UpshotModule extends ReactContextBaseJavaModule {

    public static final String REACT_CLASS = "UpshotReact";
    public static ReactApplicationContext reactContext;

    public UpshotModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    private static void emitDeviceEvent(String eventName, @Nullable WritableMap eventData) {
        // A method for emitting from the native side to JS
        // https://facebook.github.io/react-native/docs/native-modules-android.html#sending-events-to-javascript
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, eventData);
    }

    @ReactMethod
    public void initializeUpshot() {
        /*
         * try { BrandKinesis.initialiseBrandKinesis(MainApplication.getContext(), new
         * BKAuthCallback() {
         *
         * @Override public void onAuthenticationError(String s) {
         *
         * }
         *
         * @Override public void onAuthenticationSuccess() {
         *
         * } }); } catch (Exception e) { e.printStackTrace(); }
         */

        Log.i("RNTestLibraryModule", "show");
    }

    @ReactMethod
    private void createPageviewEvent(String PageName) {
        HashMap<String, Object> pageData = new HashMap<>();
        pageData.put(BrandKinesis.BK_CURRENT_PAGE, PageName);
        BrandKinesis bkInstance = BrandKinesis.getBKInstance();

        bkInstance.createEvent(BKProperties.BKPageViewEvent.NATIVE, pageData, true);

    }

    @ReactMethod
    private void createCustomEvent(String eventName, String eventPayload) {

        try {
            JSONObject jeventPayload = new JSONObject(eventPayload);
            String eventID = BrandKinesis.getBKInstance().createEvent(eventName, jsonToHashMap(jeventPayload), false);
            Log.i("RNTestLibraryModule", "eventId" + eventID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @ReactMethod
    private void dispatchEventsWithTimedEvents(Context context, boolean forceCloseTimedEvents) {
        try {
            BrandKinesis.getBKInstance().dispatchNow(context, forceCloseTimedEvents, new BKDispatchCallback() {
                @Override
                public void onDispatchComplete(boolean status) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ReactMethod
    private void setValueAndClose(String payload, String eventId) {
        try {
            JSONObject jeventPayload = new JSONObject(payload);
            BrandKinesis.getBKInstance().closeEvent(eventId, jsonToHashMap(jeventPayload));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ReactMethod
    private void closeEventForId(String eventId) {
        try {
            BrandKinesis.getBKInstance().closeEvent(eventId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ReactMethod
    private void createLocationEvent(String latitude, String longitude) {
        try {
            String eventID = BrandKinesis.getBKInstance().createLocationEvent(Double.parseDouble(latitude), Double.parseDouble(longitude));
            Log.i("createLocationEvent", "eventId" + eventID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ReactMethod
    private void setUserProfile(String userData) {
        try {

            final JSONObject providedJson = new JSONObject(userData);

            Map<String/* unity */, String/* bk */> predefinedKeys = new HashMap<>();
            predefinedKeys.put("email", BKUserInfo.BKUserData.EMAIL);
            predefinedKeys.put("appuID", BKUserInfo.BKExternalIds.APPUID);
            predefinedKeys.put("userName", BKUserInfo.BKUserData.USER_NAME);
            JSONObject othersJson = new JSONObject();
            Bundle bundle = new Bundle();
            final Iterator<String> keys = providedJson.keys();
            while (keys.hasNext()) {

                final String key = keys.next();
                final Object value = providedJson.get(key);

                if (predefinedKeys.containsKey(key)) {
                    // predefined
                    String bkKey = predefinedKeys.get(key);
                    if (value instanceof Integer) {
                        bundle.putInt(bkKey, providedJson.optInt(key));
                    } else if (value instanceof Float || value instanceof Double) {
                        bundle.putFloat(bkKey, (float) providedJson.optDouble(key));
                    } else {
                        bundle.putString(bkKey, providedJson.optString(key));
                    }

                } else { // other
                    othersJson.put(key, value);
                }

            } // --while

            if (othersJson.length() != 0) {
                bundle.putSerializable("others", jsonToHashMap(othersJson));
            }

            BrandKinesis bkInstance = BrandKinesis.getBKInstance();
            bkInstance.setUserInfoBundle(bundle, new BKUserInfoCallback() {
                @Override
                public void onUserInfoUploaded(final boolean uploadSuccess) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.i("TAG", "is User profile uploaded :" + providedJson);

                        }
                    });
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @ReactMethod
    private void fetchInboxDetails(final String isLeadResponse) {

        BrandKinesis bkInstanceForBadges = BrandKinesis.getBKInstance();
        bkInstanceForBadges.fetchInboxInfo(new BKInboxAccessListener() {
            @Override
            public void onMessagesAvailable(List<HashMap<String, Object>> messages) {

                int count = 0;
                for (HashMap<String, Object> message : messages) {

                    for (Map.Entry<String, Object> entry : message.entrySet()) {
                        try {
                            String jKey = entry.getKey();
                            if (jKey.equalsIgnoreCase("activities")) {

                                HashMap<String, Object> activityValue = (HashMap<String, Object>) entry.getValue();
                                Set<Map.Entry<String, Object>> entries = activityValue.entrySet();

                                JSONObject activityJson = new JSONObject();
                                for (Map.Entry<String, Object> activityEntry : entries) {
                                    String key = activityEntry.getKey();
                                    activityJson.put(key, activityEntry.getValue());
                                }
                                int type = activityJson.optInt("type");
                                int taken = activityJson.optInt("taken");

                                if (type == BKActivityTypes.ACTIVITY_TRIVIA.getValue() && taken == 0) {
                                    ++count;
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                inboxCallback(count, isLeadResponse);
            }
        });
    }

    @ReactMethod
    public void getRewardsList(Context context) {
        BrandKinesis bkInstance = BrandKinesis.getBKInstance();

        bkInstance.getRewardsStatusWithCompletionBlock(context, new BKRewardsResponseListener() {
            @Override
            public void rewardsResponse(final Object response) {
                BKUtilLogger.showDebugLog("Test rewardsResponse", "response : " + response.toString());
            }

            @Override
            public void onErrorReceived(Object response) {
                BKUtilLogger.showDebugLog("Test onErrorReceived", "response : " + response.toString());

            }
        });
    }

    @ReactMethod
    public void getRewardHistoryForProgram(Context context, int bkRewardHistoryTypes, String programId) {
        BrandKinesis bkInstance = BrandKinesis.getBKInstance();

        bkInstance.getRewardHistoryForProgramId(context, programId, bkRewardHistoryTypes, new BKRewardsResponseListener() {
            @Override
            public void rewardsResponse(final Object response) {
                BKUtilLogger.showDebugLog("Test rewardsResponse", "response : " + response.toString());
            }

            @Override
            public void onErrorReceived(Object response) {
                BKUtilLogger.showDebugLog("Test onErrorReceived", "response : " + response.toString());

            }
        });
    }

    @ReactMethod
    public void redeemRewardsForProgram(Context context, String programId, int transactionAmount, int redeemValue, String tag) {
        BrandKinesis bkInstance = BrandKinesis.getBKInstance();

        bkInstance.redeemRewardsWithProgramId(context, programId, transactionAmount, redeemValue, tag, new BKRewardsResponseListener() {
            @Override
            public void rewardsResponse(final Object response) {
                BKUtilLogger.showDebugLog("Test rewardsResponse", "response : " + response.toString());
            }

            @Override
            public void onErrorReceived(Object response) {
                BKUtilLogger.showDebugLog("Test onErrorReceived", "response : " + response.toString());

            }
        });
    }

    @ReactMethod
    public void getRewardRulesforProgram(Context context, String programId) {
        BrandKinesis bkInstance = BrandKinesis.getBKInstance();

        bkInstance.getRewardDetailsForProgramId(context, programId, new BKRewardsResponseListener() {
            @Override
            public void rewardsResponse(final Object response) {
                BKUtilLogger.showDebugLog("Test rewardsResponse", "response : " + response.toString());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    }
                });
            }

            @Override
            public void onErrorReceived(Object response) {
                BKUtilLogger.showDebugLog("Test onErrorReceived", "response : " + response.toString());

            }
        });
    }

    BKUIPrefComponents customUipreferences = new BKUIPrefComponents() {

        @Override
        public void setPreferencesForRelativeLayout(RelativeLayout relativeLayout, BKActivityTypes bkActivityTypes,
                                                    BKActivityRelativeLayoutTypes bkActivityRelativeLayoutTypes, boolean b) {
            if (bkActivityTypes == BKActivityTypes.ACTIVITY_TRIVIA) {
                switch (bkActivityRelativeLayoutTypes) {
                    case BKACTIVITY_BACKGROUND_IMAGE:
                        relativeLayout.setBackgroundColor(Color.parseColor("#f5f5f5"));
                }
            }
        }

        @Override
        public void setPreferencesForImageButton(ImageButton imageButton, BKActivityTypes bkActivityTypes,
                                                 BKActivityImageButtonTypes bkActivityImageButtonTypes) {

        }

        @Override
        public void setPreferencesForButton(Button button, BKActivityTypes bkActivityTypes,
                                            BKActivityButtonTypes bkActivityButtonTypes) {

            if (bkActivityTypes == BKActivityTypes.ACTIVITY_TRIVIA) {
                switch (bkActivityButtonTypes) {
                    case BKACTIVITY_TRIVIA_PREVIOUS_BUTTON:
                    case BKACTIVITY_TRIVIA_CONTINUE_BUTTON:
                    case BKACTIVITY_TRIVIA_NEXT_BUTTON:
                    case BKACTIVITY_SUBMIT_BUTTON:
                        button.setBackgroundColor(Color.parseColor("#015CB9"));
                        button.setTextColor(Color.parseColor("#FFFFFF"));
                        break;
                }
            }
        }

        @Override
        public void setPreferencesForTextView(TextView textView, BKActivityTypes bkActivityTypes,
                                              BKActivityTextViewTypes bkActivityTextViewTypes) {

            if (bkActivityTypes == BKActivityTypes.ACTIVITY_TRIVIA) {

                switch (bkActivityTextViewTypes) {

                    case BKACTIVITY_TRIVIA_DESC_TV:
                        textView.setTextColor(Color.parseColor("#575757"));
                        break;

                    case BKACTIVITY_QUESTION_OPTION_TV:
                    case BKACTIVITY_QUESTION_TV:
                        textView.setTextColor(Color.parseColor("#575757"));
                        break;

                    case BKACTIVITY_LEADER_BOARD_SCORE_VALUE_TV:
                    case BKACTIVITY_LEADER_BOARD_GRADE_VALUE_TV:
                    case BKACTIVITY_LEADER_BOARD_TITLE_TV:

                    case BKACTIVITY_LEADER_BOARD_SCORE_TV:
                    case BKACTIVITY_LEADER_BOARD_GRADE_TV:
                    case BKACTIVITY_SCORE_TV:

                        textView.setTextColor(Color.parseColor("#015CB9"));
                        break;
                }
            }
        }

        @Override
        public void setPreferencesForImageView(ImageView imageView, BKActivityTypes bkActivityTypes,
                                               BKActivityImageViewType bkActivityImageViewType) {

        }

        @Override
        public void setPreferencesForOptionsSeparatorView(View view, BKActivityTypes bkActivityTypes) {

        }

        @Override
        public void setCheckBoxRadioSelectorResource(BKUICheckBox bkuiCheckBox, BKActivityTypes bkActivityTypes,
                                                     boolean b) {

        }

        @Override
        public void setRatingSelectorResource(List<Bitmap> list, List<Bitmap> list1, BKActivityTypes bkActivityTypes,
                                              BKActivityRatingTypes bkActivityRatingTypes) {

        }

        @Override
        public void setPreferencesForUIColor(BKBGColors bkbgColors, BKActivityTypes bkActivityTypes,
                                             BKActivityColorTypes bkActivityColorTypes) {
            if (bkActivityTypes == BKActivityTypes.ACTIVITY_TRIVIA) {
                switch (bkActivityColorTypes) {
                    case BKACTIVITY_TRIVIA_TITLE_COLOR:
                        bkbgColors.setColor(Color.parseColor("#015CB9"));
                        break;
                    case BKACTIVITY_PAGINATION_DEFAULT_COLOR:
                        bkbgColors.setColor(Color.parseColor("#575757"));
                        break;
                }

            }
        }

        @Override
        public void setPreferencesForGraphColor(BKGraphType bkGraphType, List<Integer> list,
                                                BKActivityTypes bkActivityTypes) {
            if (bkActivityTypes != BKActivityTypes.ACTIVITY_TRIVIA) {
            }
        }

        @Override
        public int getPositionPercentageFromBottom(BKActivityTypes bkActivityTypes, BKViewType bkViewType) {
            return 0;
        }

        @Override
        public void setPreferencesForSeekBar(SeekBar seekBar, BKActivityTypes bkActivityTypes,
                                             BKActivitySeekBarTypes bkActivitySeekBarTypes) {

        }

        @Override
        public void setPreferencesForEditText(EditText editText, BKActivityTypes bkActivityTypes,
                                              BKActivityEditTextTypes bkActivityEditTextTypes) {

        }

        @Override
        public void setPreferencesForLinearLayout(LinearLayout linearLayout, BKActivityTypes bkActivityTypes,
                                                  BKActivityLinearLayoutTypes bkActivityLinearLayoutTypes) {

            if (bkActivityTypes == BKActivityTypes.ACTIVITY_TRIVIA) {
                switch (bkActivityLinearLayoutTypes) {
                    case BKACTIVITY_BACKGROUND_IMAGE:
                        linearLayout.setBackgroundColor(Color.parseColor("#015cb9"));
                }
            }
        }
    };

    @ReactMethod
    private void showActivity(String tagName, Context context) {
        Log.i("RNTestLibraryModule", "tagName:" + tagName);
        BrandKinesis bkInstance = BrandKinesis.getBKInstance();
        //bkInstance.setUIPreferences(customUipreferences);
        bkInstance.getActivity(context, BKActivityTypes.ACTIVITY_ANY, tagName,
                new BKActivityCallback() {
                    @Override
                    public void onActivityError(int i) {
                        Log.i("RNTestLibraryModule", "activityerror");
                    }

                    @Override
                    public void onActivityCreated(BKActivityTypes bkActivityTypes) {
                        Log.i("RNTestLibraryModule", "createactivity");
                    }

                    @Override
                    public void onActivityDestroyed(BKActivityTypes bkActivityTypes) {
                        Log.i("RNTestLibraryModule", "destroyactivity");
                    }

                    @Override
                    public void brandKinesisActivityPerformedActionWithParams(BKActivityTypes bkActivityTypes,
                                                                              Map<String, Object> map) {

                    }
                });
        Log.i("RNTestLibraryModule", "end");
    }

    @ReactMethod
    private void sendDeviceTokenToUpshot(String token) {

        Bundle userInfo = new Bundle();
        userInfo.putString(BKUserInfo.BKExternalIds.GCM, token);

        BrandKinesis bkInstance = BrandKinesis.getBKInstance();

        bkInstance.setUserInfoBundle(userInfo, new BKUserInfoCallback() {
            @Override
            public void onUserInfoUploaded(final boolean uploadSuccess) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    }
                });

            }
        });

    }

    @ReactMethod
    private void terminateUpshot() {
        // BrandKinesis.getBKInstance().terminate(MainApplication.getContext());
    }


    @ReactMethod
    private void sendPushPayloadToUpshot(String pushPayload) {
        BrandKinesis bkInstance1 = BrandKinesis.getBKInstance();
        if (bkInstance1 != null) {
            try {
                JSONObject jPushPayload = new JSONObject(pushPayload);
                bkInstance1.buildEnhancedPushNotification(reactContext, jsonToBundle(jPushPayload), true);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static Bundle jsonToBundle(JSONObject jsonObject) throws JSONException {
        Bundle bundle = new Bundle();
        Iterator iter = jsonObject.keys();
        while (iter.hasNext()) {
            String key = (String) iter.next();
            String value = jsonObject.getString(key);
            bundle.putString(key, value);
        }
        return bundle;
    }

    public static HashMap<String, Object> jsonToHashMap(JSONObject jsonObject) throws JSONException {
        HashMap<String, Object> data = new HashMap<>();

        Iterator iter = jsonObject.keys();
        while (iter.hasNext()) {
            String key = (String) iter.next();
            String value = jsonObject.getString(key);
            data.put(key, value);
        }
        return data;
    }

    public static void pushCallback(String pushPayload) {
        // WriteableMap params = Arguments.createMap();
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit("push-callback",
                pushPayload);
    }

    public static void inboxCallback(int triviaCount, String isLeadResponse) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("triviaList", triviaCount);
            jsonObject.put("isLeadResponse", isLeadResponse);
            reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                    .emit("inbox-response-callback", jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
