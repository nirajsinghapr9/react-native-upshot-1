package com.upshotreactlibrary.upshot.customization;

import android.content.Context;
import android.graphics.Color;
import android.widget.ImageButton;
import android.widget.TextView;

import com.brandkinesis.BKUIPrefComponents;
import com.brandkinesis.utils.BKUtilLogger;

public class UpshotTutorialCustomization extends UpshotCustomization {
    Context mContext;

    public UpshotTutorialCustomization(Context context) {
        mContext = context;

    }

    @Override
    public void customizeImageButton(ImageButton button, BKUIPrefComponents.BKActivityImageButtonTypes buttonType) {
        super.customizeImageButton(button, buttonType);
        switch (buttonType) {
            case BKACTIVITY_SKIP_BUTTON:
                BKUtilLogger.showDebugLog(BKUtilLogger.BK_DEBUG, "Customization BKTutorial_skip_BUTTON");
                button.setBackgroundColor(Color.parseColor("#0679A3"));
//                button.setBackgroundResource(R.drawable.about_screen_logo);
                break;


        }


    }

    @Override
    public void customizeTextView(BKUIPrefComponents.BKActivityTextViewTypes textViewType, TextView textView) {
        super.customizeTextView(textViewType, textView);

        switch (textViewType) {

            case BKACTIVITY_TUTORIAL_VIDEO_TV:
                textView.setTextColor(Color.RED);
        }

        }
}

