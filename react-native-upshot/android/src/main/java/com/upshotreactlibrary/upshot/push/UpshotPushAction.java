package com.upshotreactlibrary.upshot.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.brandkinesis.BrandKinesis;
import com.brandkinesis.utils.BKUtilLogger;
import com.upshotreactlibrary.UpshotModule;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by PurpleTalk on 7/6/16.
 */
public class UpshotPushAction extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
        Log.e("Bundle BKPushAction", "" + intent.getExtras());
        String action = "";
        String appData = "";
        String launchOptions = "";
        String bk = "";
        final Bundle bundle = intent.getExtras();
        if (bundle != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                UpshotModule.sendPushClickPayload(UpshotModule.bundleToJsonString(bundle));
            }
            action = bundle.getString("actionData"); //If big image push contains button then button action
            appData = intent.getStringExtra("appData"); //push deeplink
            /**
             * Handling deeplinks - Value for "appData" key is having the deeplink info in JSON format.
             * Below we convert the string into the JSON object, then we fetch the value for that deeplink.
             * And baseo on the value we will redirect to the corresponding page.
             */
            Intent intent1 = null;
            String url = null;
            JSONObject data = null;
            try {
                data = new JSONObject(appData);
                if (data.has("deepLink")) {
                    url = data.getString("deepLink");
                }/**
                 * Along with deeplink we can pass key, value pairs (if needed) and we can use.
                 */
                else if (data.has("key1")) {
                    url = data.getString("key1");
                } else if (data.has("key2")) {
                    url = data.getString("key2");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            /**
             * Below we are handling the deeplinks and redirecting to the target screen based on deeplink value
             * when create the campaign in Upshot dashboard.
             */
            /**
             * When tap the push launching the application(Application Home activity).
             */
            BKUtilLogger.showDebugLog(BKUtilLogger.BK_DEBUG, "Push :: tapped in BKPushAction");
            Class mainActivity = null;
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
            Intent i = new Intent(context, mainActivity);
            if (i == null) {
                BKUtilLogger.showErrorLog(BKUtilLogger.BK_DEBUG, "intent null in GCMUP");
                return;
            }
            i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("actionValue", action);
            i.putExtra("appData", appData);
            i.putExtra("launchOptions", launchOptions);
            context.startActivity(i);
            BKUtilLogger.showDebugLog(BKUtilLogger.BK_DEBUG, "Push :: Started Activity in BKPushAction");

            /**
             * This is to handle push with activity and we should call this method after launching the application.
             */
            BrandKinesis bkInstance = BrandKinesis.getBKInstance();
            bkInstance.handlePushNotification(context, bundle);
        }
    }
}
