package com.reactlibrary;

import android.os.Build;
import android.os.Bundle;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;

import android.telecom.Call;
import android.text.TextUtils;
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

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.brandkinesis.BKProperties;
import com.brandkinesis.BKUIPrefComponents;
import com.brandkinesis.BKUserInfo;
import com.brandkinesis.BrandKinesis;
import com.brandkinesis.activitymanager.BKActivityTypes;
import com.brandkinesis.callback.BKActivityCallback;
import com.brandkinesis.callback.BKAuthCallback;
import com.brandkinesis.callback.BKBadgeAccessListener;
import com.brandkinesis.callback.BKDispatchCallback;
import com.brandkinesis.callback.BKInboxAccessListener;
import com.brandkinesis.callback.BKPushCompletionBlock;
import com.brandkinesis.callback.BKUserInfoCallback;
import com.brandkinesis.callback.BrandKinesisUserStateCompletion;
import com.brandkinesis.rewards.BKRewardsResponseListener;
import com.brandkinesis.utils.BKUtilLogger;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.bridge.Callback;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.facebook.react.bridge.UiThreadUtil.runOnUiThread;


public class UpshotModule extends ReactContextBaseJavaModule {

    public static final String REACT_CLASS = "UpshotReact";
    private static final String TAG = UpshotModule.class.getSimpleName();
    public static ReactApplicationContext reactContext;

    public UpshotModule(final ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    private static void emitDeviceEvent(final String eventName, @Nullable final WritableMap eventData) {
        // A method for emitting from the native side to JS
        // https://facebook.github.io/react-native/docs/native-modules-android.html#sending-events-to-javascript
        if (reactContext != null) reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, eventData);
    }

    /* React Methods */

    @ReactMethod
    public void initializeUpshot() {

    }

    /* Events Module */
    @ReactMethod
    private void createPageViewEvent(final String PageName, final Callback callback) {

        final HashMap<String, Object> pageData = new HashMap<>();
        pageData.put(BrandKinesis.BK_CURRENT_PAGE, PageName);
        final BrandKinesis bkInstance = BrandKinesis.getBKInstance();
        final String eventID = bkInstance.createEvent(BKProperties.BKPageViewEvent.NATIVE, pageData, true);
        callback.invoke(eventID);
    }

    @ReactMethod
    private void createCustomEvent(final String eventName, final String eventPayload, final boolean isTimed, final Callback callback) {

        try {
            final JSONObject jeventPayload = new JSONObject(eventPayload);
            final String eventID = BrandKinesis.getBKInstance().createEvent(eventName, jsonToHashMap(jeventPayload),
                    isTimed);
            callback.invoke(eventID);

        } catch (final JSONException e) {
            e.printStackTrace();
        }
    }

    @ReactMethod
    private void setValueAndClose(final String payload, final String eventId) {
        try {
            final JSONObject jeventPayload = new JSONObject(payload);
            BrandKinesis.getBKInstance().closeEvent(eventId, jsonToHashMap(jeventPayload));
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    @ReactMethod
    private void closeEventForId(final String eventId) {
        try {
            BrandKinesis.getBKInstance().closeEvent(eventId);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    @ReactMethod
    private void dispatchEventsWithTimedEvents(final boolean timed, final Callback callback) {
        try {
            BrandKinesis.getBKInstance().dispatchNow(UpshotApplication.get(), timed, new BKDispatchCallback() {
                @Override
                public void onDispatchComplete(final boolean status) {
                    callback.invoke(status);
                }
            });
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    @ReactMethod
    private void createLocationEvent(final String latitude, final String longitude) {
        try {
            final String eventID = BrandKinesis.getBKInstance().createLocationEvent(Double.parseDouble(latitude),
                    Double.parseDouble(longitude));            
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    /* Profile Module */
    @ReactMethod
    private void setUserProfile(final String userData, final Callback callback) {

        Map<String, String> predefinedKeys = new HashMap<>();
        predefinedKeys.put("lastName", BKUserInfo.BKUserData.LAST_NAME);
        predefinedKeys.put("middleName", BKUserInfo.BKUserData.MIDDLE_NAME);
        predefinedKeys.put("firstName", BKUserInfo.BKUserData.FIRST_NAME);
        predefinedKeys.put("language", BKUserInfo.BKUserData.LANGUAGE);
        predefinedKeys.put("occupation", BKUserInfo.BKUserData.OCCUPATION);
        predefinedKeys.put("qualification", BKUserInfo.BKUserData.QUALIFICATION);
        predefinedKeys.put("phone", BKUserInfo.BKUserData.PHONE);
        predefinedKeys.put("localeCode", BKUserInfo.BKUserData.LOCALE_CODE);
        predefinedKeys.put("userName", BKUserInfo.BKUserData.USER_NAME);
        predefinedKeys.put("email", BKUserInfo.BKUserData.EMAIL);
        predefinedKeys.put("appuID", BKUserInfo.BKExternalIds.APPUID);
        predefinedKeys.put("facebookID", BKUserInfo.BKExternalIds.FACEBOOK);
        predefinedKeys.put("twitterID", BKUserInfo.BKExternalIds.TWITTER);
        predefinedKeys.put("foursquareID", BKUserInfo.BKExternalIds.FOURSQUARE);
        predefinedKeys.put("linkedinID", BKUserInfo.BKExternalIds.LINKEDIN);
        predefinedKeys.put("googleplusID", BKUserInfo.BKExternalIds.GOOGLEPLUS);
        predefinedKeys.put("enterpriseUID", BKUserInfo.BKExternalIds.ENTERPRISE_UID);
        predefinedKeys.put("advertisingID", BKUserInfo.BKExternalIds.ADVERTISING_ID);
        predefinedKeys.put("instagramID", BKUserInfo.BKExternalIds.INSTAGRAM);
        predefinedKeys.put("pinterest", BKUserInfo.BKExternalIds.PINTEREST);
        predefinedKeys.put("token", BKUserInfo.BKExternalIds.GCM);
        predefinedKeys.put("gender", BKUserInfo.BKUserData.GENDER);
        predefinedKeys.put("maritalStatus", BKUserInfo.BKUserData.MARITAL_STATUS);
        predefinedKeys.put("year", BKUserInfo.BKUserDOBdata.YEAR);
        predefinedKeys.put("month", BKUserInfo.BKUserDOBdata.MONTH);
        predefinedKeys.put("day", BKUserInfo.BKUserDOBdata.DAY);
        predefinedKeys.put("age", BKUserInfo.BKUserData.AGE);
        predefinedKeys.put("email_opt", BKUserInfo.BKUserData.EMAIL_OPT_OUT);
        predefinedKeys.put("push_opt", BKUserInfo.BKUserData.PUSH_OPT_OUT);
        predefinedKeys.put("sms_opt", BKUserInfo.BKUserData.SMS_OPT_OUT);
        predefinedKeys.put("data_opt", BKUserInfo.BKUserData.DATA_OPT_OUT);
        predefinedKeys.put("ip_opt", BKUserInfo.BKUserData.IP_OPT_OUT);
        try {
            JSONObject providedJson = new JSONObject(userData);
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
            }
            if (othersJson.length() != 0) {
                bundle.putSerializable("others", (HashMap)   jsonToHashMap(othersJson));
            }

            final BrandKinesis bkInstance = BrandKinesis.getBKInstance();
            bkInstance.setUserInfoBundle(bundle, new BKUserInfoCallback() {
                @Override
                public void onUserInfoUploaded(final boolean uploadSuccess) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            callback.invoke(uploadSuccess);
                        }
                    });
                }
            });
        } catch (final JSONException e) {
            e.printStackTrace();
        }
    }

    @ReactMethod
    private  void  getUserDetails(final Callback callback) {

        final BrandKinesis bkInstance = BrandKinesis.getBKInstance();
        Set<String> keys = new HashSet<>();
        Map<String, Object> userInfo = bkInstance.getUserDetails(keys);
        JSONObject details =  new  JSONObject(userInfo);
        callback.invoke(details.toString());
    }

    /* Activity Module */
    @ReactMethod
    private void showActivityWithType(final int activityType,  final String tagName) {

        final BrandKinesis bkInstance = BrandKinesis.getBKInstance();        

        BKActivityTypes type = BKActivityTypes.parse(activityType);
        if (activityType == -1) {
            type = BKActivityTypes.ACTIVITY_ANY;
        }
        bkInstance.getActivity(UpshotApplication.get(), type, tagName, new BKActivityCallback() {
            @Override
            public void onActivityError(final int i) {

            }

            @Override
            public void onActivityCreated(final BKActivityTypes bkActivityTypes) {

                WritableMap payload = Arguments.createMap();
                payload.putInt("activityType", bkActivityTypes.getValue());
                emitDeviceEvent("UpshotActivityDidAppear", payload);
            }

            @Override
            public void onActivityDestroyed(final BKActivityTypes bkActivityTypes) {

                WritableMap payload = Arguments.createMap();
                payload.putInt("activityType", bkActivityTypes.getValue());
                emitDeviceEvent("UpshotActivityDidDismiss", payload);
            }

            @Override
            public void brandKinesisActivityPerformedActionWithParams(final BKActivityTypes bkActivityTypes,
                                                                      final Map<String, Object> map) {

                WritableMap payload = Arguments.createMap();
                payload.putMap("deepLink", (WritableMap) map);
                emitDeviceEvent("UpshotDeepLink", payload);
            }
        });
    }

    @ReactMethod
    private void showActivityWithId(final  String activityId) {

        final BrandKinesis bkInstance = BrandKinesis.getBKInstance();
        bkInstance.getActivity(activityId, new BKActivityCallback() {
            @Override
            public void onActivityError(int i) {

            }

            @Override
            public void onActivityCreated(BKActivityTypes bkActivityTypes) {

                WritableMap payload = Arguments.createMap();
                payload.putInt("activityType", bkActivityTypes.getValue());
                emitDeviceEvent("UpshotActivityDidAppear", payload);
            }

            @Override
            public void onActivityDestroyed(BKActivityTypes bkActivityTypes) {

                WritableMap payload = Arguments.createMap();
                payload.putInt("activityType", bkActivityTypes.getValue());
                emitDeviceEvent("UpshotActivityDidDismiss", payload);
            }

            @Override
            public void brandKinesisActivityPerformedActionWithParams(BKActivityTypes bkActivityTypes, Map<String, Object> map) {

                WritableMap payload = Arguments.createMap();
                payload.putMap("deepLink", (WritableMap) map);
                emitDeviceEvent("UpshotDeepLink", payload);
            }
        });
    }

    @ReactMethod 
    private void removeTutorials() {

        final BrandKinesis bkInstance = BrandKinesis.getBKInstance();
        bkInstance.removeTutorial(UpshotApplication.get());
    }

    @ReactMethod
    private void fetchInboxInfo(final Callback callback) {

        final BrandKinesis bkInstance = BrandKinesis.getBKInstance();
        bkInstance.fetchInboxInfo(new BKInboxAccessListener() {
            @Override
            public void onMessagesAvailable(List<HashMap<String, Object>> list) {

                final JSONArray messagesJson = new JSONArray();

                for (HashMap<String, Object> message : list) {

                    JSONObject messageJson = new JSONObject();

                    for (Map.Entry<String, Object> entry : message.entrySet()) {
                        try {
                            String jKey = entry.getKey();
                            if (jKey.equalsIgnoreCase("activities")) {
                                JSONArray jsonArray = new JSONArray();

                                List<HashMap<String, Object>> activityArrayList = (List<HashMap<String, Object>>) entry.getValue();

                                for (HashMap<String, Object> activityValue : activityArrayList) {

                                    JSONObject activityJson = new JSONObject();
                                    for (Map.Entry<String, Object> activityEntry : activityValue.entrySet()) {
                                        activityJson.put(activityEntry.getKey(), activityEntry.getValue());
                                    }
                                    jsonArray.put(activityJson);
                                }
                                messageJson.put(jKey, jsonArray);
                            } else {

                                messageJson.put(jKey, entry.getValue());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    messagesJson.put(messageJson);
                }
                callback.invoke(messagesJson.toString());
            }
        });
    }

    @ReactMethod 
    private void getUserBadges(final Callback callback) {

        final BrandKinesis bkInstance = BrandKinesis.getBKInstance();
        bkInstance.getBadges(new BKBadgeAccessListener() {
            @Override
            public void onBadgesAvailable(HashMap<String, List<HashMap<String, Object>>> badges) {

                final JSONArray activeBadgesArray = new JSONArray();
                final JSONArray inactiveBadgesArray = new JSONArray();

                for (Map.Entry<String, List<HashMap<String, Object>>> badgeMap : badges.entrySet()) {
                    String jKey = badgeMap.getKey();
                    List<HashMap<String, Object>> hashMapList = badges.get(jKey);

                    for (int i = 0; i < hashMapList.size(); i++) {
                        HashMap<String, Object> stringObjectHashMap = hashMapList.get(i);
                        JSONObject badgeJson = new JSONObject();

                        for (Map.Entry<String, Object> singleBadge : stringObjectHashMap.entrySet()) {
                            try {
                                String singleBadgeKey = singleBadge.getKey();
                                if (singleBadge.getValue() instanceof String) {

                                    String singleBadgeValue = (String) singleBadge.getValue();
                                    if (singleBadgeKey.equals("image")) {
                                        badgeJson.put(singleBadgeKey, "file://" + singleBadgeValue);
                                    } else {
                                        badgeJson.put(singleBadgeKey, singleBadgeValue);
                                    }

                                } else  if (singleBadge.getValue() instanceof  Double) {
                                    badgeJson.put(singleBadgeKey, singleBadge.getValue());
                                } else  if(singleBadge.getValue() instanceof  Long) {
                                    badgeJson.put(singleBadgeKey, singleBadge.getValue());
                                } else if (singleBadge.getValue() instanceof  Integer) {
                                    badgeJson.put(singleBadgeKey, singleBadge.getValue());
                                }
                                else  {
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if (jKey.equalsIgnoreCase("active_list")) {
                            activeBadgesArray.put(badgeJson);
                        } else {
                            inactiveBadgesArray.put(badgeJson);
                        }
                    }
                }

                try {
                    final JSONObject badge = new JSONObject();
                    badge.put("active_list", activeBadgesArray);
                    badge.put("inactive_list", inactiveBadgesArray);
                    callback.invoke(badge.toString());
                } catch (JSONException e){

                }
            }
        });
    }

    /* Push Notification Module */
    @ReactMethod
    private static void registerForPush(final boolean enableForeground, final Callback callback) {

    }

    @ReactMethod
    private static void sendDeviceToken(final String token) {

        final Bundle userInfo = new Bundle();
        userInfo.putString(BKUserInfo.BKExternalIds.GCM, token);

        final BrandKinesis bkInstance = BrandKinesis.getBKInstance();

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
    public static void sendPushDataToUpshot(final String pushData){

        BrandKinesis bkInstance = BrandKinesis.getBKInstance();
        try {
            bkInstance.buildEnhancedPushNotification(UpshotApplication.get(), jsonToBundle(new JSONObject(pushData)), true);
        } catch (JSONException e) {

        }
    }

    @ReactMethod
    private  void sendPushNotificationWithDetails(final String details) {

        final BrandKinesis bkInstance = BrandKinesis.getBKInstance();
        try {
            final JSONObject jPushPayload = new JSONObject(details);
            bkInstance.sendPushDetails(UpshotApplication.get(), jsonToHashMap(jPushPayload), new BKPushCompletionBlock() {
                @Override
                public void onBkPushSent(boolean b) {

                }
            });
        } catch (final JSONException e) {

        }
    }

    /* GDPR */
    @ReactMethod
    private void disableUser(final  boolean shouldDisable, final Callback callback) {

        final BrandKinesis bkInstance = BrandKinesis.getBKInstance();
        bkInstance.disableUser(shouldDisable, UpshotApplication.get(), new BrandKinesisUserStateCompletion() {
            @Override
            public void userStateCompletion(boolean b) {
                callback.invoke(b);
            }
        });
    }

    @ReactMethod
    private  void getUserId(final Callback callback) {

        final BrandKinesis bkInstance = BrandKinesis.getBKInstance();
        callback.invoke(bkInstance.getUserId(UpshotApplication.get()));
    }

    @ReactMethod
    private  void getSDKVersion(final Callback callback) {

        final BrandKinesis bkInstance = BrandKinesis.getBKInstance();
        callback.invoke(bkInstance.getSdkVersion());
    }

    /* Rewards Module */

    @ReactMethod
    public void getRewardsList(final Callback successCallback, final Callback errorCallback) {
        final BrandKinesis bkInstance = BrandKinesis.getBKInstance();

        bkInstance.getRewardsStatusWithCompletionBlock(UpshotApplication.get(), new BKRewardsResponseListener() {
            @Override
            public void rewardsResponse(final Object response) {
                successCallback.invoke(response);
            }

            @Override
            public void onErrorReceived(final Object response) {
                BKUtilLogger.showDebugLog("Test onErrorReceived", "response : " + response.toString());
                errorCallback.invoke(response);
            }
        });
    }

    @ReactMethod
    public void getRewardHistoryForProgram(final String programId, final int historyType, final Callback successCallback, final Callback failureCallback) {
        final BrandKinesis bkInstance = BrandKinesis.getBKInstance();

        bkInstance.getRewardHistoryForProgramId(UpshotApplication.get(), programId, historyType,
                new BKRewardsResponseListener() {
                    @Override
                    public void rewardsResponse(final Object response) {
                        successCallback.invoke(response);
                    }

                    @Override
                    public void onErrorReceived(final Object response) {
                        failureCallback.invoke(response);
                    }
                });
    }

    @ReactMethod
    public void redeemRewardsForProgram(final String programId, final int transactionAmount,
                                        final int redeemValue, final String tag, final  Callback successCallback, final Callback failureCallback) {
        final BrandKinesis bkInstance = BrandKinesis.getBKInstance();

        bkInstance.redeemRewardsWithProgramId(UpshotApplication.get(), programId, transactionAmount, redeemValue, tag,
                new BKRewardsResponseListener() {
                    @Override
                    public void rewardsResponse(final Object response) {
                        successCallback.invoke(response);
                    }

                    @Override
                    public void onErrorReceived(final Object response) {
                        failureCallback.invoke(response);
                    }
                });
    }

    @ReactMethod
    public void getRewardRulesforProgram(final String programId, final Callback successCallback, final Callback failureCallback) {
        final BrandKinesis bkInstance = BrandKinesis.getBKInstance();

        bkInstance.getRewardDetailsForProgramId(UpshotApplication.get(), programId, new BKRewardsResponseListener() {
            @Override
            public void rewardsResponse(final Object response) {
                successCallback.invoke(response);
            }

            @Override
            public void onErrorReceived(final Object response) {
                failureCallback.invoke(response);
            }
        });
    }

    @ReactMethod
    private void terminate() {
        
    }

    /* Utility Methods */
    public static void upshotInitStatus(boolean isSuccessCallback, String msg) {

        WritableMap payload = Arguments.createMap();
        payload.putBoolean("status", isSuccessCallback);
        payload.putString("error", msg);
        sendRegistrationToServer(FirebaseInstanceId.getInstance().getToken());
        emitDeviceEvent("UpshotAuthStatus", payload);
    }
    

    public static void sendRegistrationToServer(String token) {

        if (token.isEmpty()) {
            return;
        }
        sendDeviceToken(token);        
        WritableMap payload = Arguments.createMap();
        payload.putString("token", token);
        emitDeviceEvent("UpshotPushToken", payload);
    }

    public static void sendPushClickPayload(final String pushPayload) {
        
        WritableMap payload = Arguments.createMap();
        payload.putString("payload", pushPayload);
        emitDeviceEvent("UpshotPushPayload", payload);
    }


@RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String bundleToJsonString(Bundle bundle){

        JSONObject json = new JSONObject();
        Set<String> keys = bundle.keySet();
        for (String key : keys) {
            try {                
                json.put(key, JSONObject.wrap(bundle.get(key)));
            } catch(JSONException e) {
                
            }
    }
        return json.toString();
    }

    public static Bundle jsonToBundle(final JSONObject jsonObject) throws JSONException {

        final Bundle bundle = new Bundle();
        final Iterator iter = jsonObject.keys();
        while (iter.hasNext()) {
            final String key = (String) iter.next();
            final String value = jsonObject.getString(key);
            bundle.putString(key, value);
        }
        return bundle;
    }

    public static HashMap<String, Object> jsonToHashMap(final JSONObject jsonObject) throws JSONException {
        final HashMap<String, Object> data = new HashMap<>();

        final Iterator iter = jsonObject.keys();
        while (iter.hasNext()) {
            final String key = (String) iter.next();
            final String value = jsonObject.getString(key);
            data.put(key, value);
        }
        return data;
    }

    public static WritableMap convertJsonToMap(JSONObject jsonObject) throws JSONException {
        WritableMap map = new WritableNativeMap();

        Iterator<String> iterator = jsonObject.keys();
        while (iterator.hasNext()) {
            String key = iterator.next();
            Object value = jsonObject.get(key);
            if (value instanceof JSONObject) {
                map.putMap(key, convertJsonToMap((JSONObject) value));
            } else if (value instanceof Boolean) {
                map.putBoolean(key, (Boolean) value);
            } else if (value instanceof Integer) {
                map.putInt(key, (Integer) value);
            } else if (value instanceof Double) {
                map.putDouble(key, (Double) value);
            } else if (value instanceof String) {
                map.putString(key, (String) value);
            } else {
                map.putString(key, value.toString());
            }
        }
        return map;
    }
}
