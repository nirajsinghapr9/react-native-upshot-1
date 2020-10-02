/*
 * //***************************************************************************************************
 * //***************************************************************************************************
 * //      Project adUnitName                    	: BrandKinesis
 * //      Class Name                              :
 * //      Author                                  : PurpleTalk
 * //***************************************************************************************************
 * //     Class Description:
 * //
 * //***************************************************************************************************
 * //***************************************************************************************************
 */

package com.upshotreactlibrary.upshot.customization;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.brandkinesis.BKUIPrefComponents;
import com.brandkinesis.BKUIPrefComponents.BKActivityButtonTypes;
import com.brandkinesis.BKUIPrefComponents.BKActivityTextViewTypes;
import com.brandkinesis.utils.BKUtilLogger;

import java.util.List;

public class UpshotOpinionPollCustomization extends UpshotCustomization {
    private Context mContext;
    private Typeface mTypeface;

    public UpshotOpinionPollCustomization(Context context) {
        mContext = context;
    }

    public void customizeRadioButton(BKUIPrefComponents.BKUICheckBox checkBox, boolean isCheckBox) {
        /*Bitmap check_select, default_select;
        if (isCheckBox) {
            check_select = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.nfl_checkbox);
            checkBox.setSelectedCheckBox(check_select);

        } else {
            check_select = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.nfl_check);
            checkBox.setSelectedCheckBox(check_select);
        }
        default_select = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.nfl_uncheck);
        checkBox.setUnselectedCheckBox(default_select);*/
    }

    @Override
    public void customizeButton(Button button, BKActivityButtonTypes buttonType) {
        super.customizeButton(button, buttonType);
       mTypeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/" + "violin.ttf");
       button.setTypeface(mTypeface);
        switch (buttonType) {

            case BKACTIVITY_SURVEY_NEXT_BUTTON:
            case BKACTIVITY_SURVEY_PREVIOUS_BUTTON:
            case BKACTIVITY_SUBMIT_BUTTON:
                button.setBackgroundColor(Color.parseColor("#179CD5"));
                break;
        }
    }

    @Override
    public void customizeTextView(BKActivityTextViewTypes textViewType, TextView textView) {
        super.customizeTextView(textViewType, textView);
        mTypeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/" + "violin.ttf");
        textView.setTypeface(mTypeface);

        switch (textViewType) {
            // question
            case BKACTIVITY_QUESTION_TV:
//                textView.setBackgroundColor(Color.parseColor("#fffce4"));
                textView.setTextColor(Color.parseColor("#00FFFF"));
                break;
            case BKACTIVITY_THANK_YOU_TV:
//                textView.setBackgroundResource(R.drawable.nfl_bg);
                textView.setTextColor(Color.WHITE);
                break;

            case BKACTIVITY_OPTION_TV:
            case BKACTIVITY_QUESTION_OPTION_TV:
                textView.setTextColor(Color.WHITE);
                break;

            case BKACTIVITY_LEGEND_TV:
                textView.setTextColor(Color.WHITE);
                break;
        }

    }

    @Override
    public void customizeBGColor(BKUIPrefComponents.BKBGColors color, BKUIPrefComponents.BKActivityColorTypes colorType) {
        super.customizeBGColor(color, colorType);

        switch (colorType) {
            case BKACTIVITY_BG_COLOR:
               // color.setColor(Color.GREEN);
                break;
            case BKACTIVITY_SURVEY_HEADER_COLOR:
             //   color.setColor(Color.GRAY);
                break;
            case BKACTIVITY_BOTTOM_COLOR:
               // color.setColor(Color.BLACK);
                break;
            case BKACTIVITY_XAXIS_COLOR:
            case BKACTIVITY_XAXIS_TEXT_COLOR_COLOR:
            case BKACTIVITY_YAXIS_TEXT_COLOR_COLOR:
            case BKACTIVITY_YAXIS_COLOR:
                color.setColor(Color.WHITE);
                break;

            default:
                break;
        }
    }

    public void customizeImageView(ImageView imageView, BKUIPrefComponents.BKActivityImageViewType imageType) {
        super.customizeImageView(imageView, imageType);
        switch (imageType) {

            case BKACTIVITY_PORTRAIT_LOGO:
              //  imageView.setBackgroundResource(R.drawable.about_screen_logo);
                break;
            case BKACTIVITY_LANDSCAPE_LOGO:
               // imageView.setBackgroundResource(R.drawable.g);
                break;
            case BKACTIVITY_ADS_DEFAULT_IMAGE:
                break;
        }
    }

    @Override
    public void customizeRelativeLayout(BKUIPrefComponents.BKActivityRelativeLayoutTypes relativeLayoutTypes, RelativeLayout relativeLayout, boolean isFullScreen) {
        super.customizeRelativeLayout(relativeLayoutTypes, relativeLayout, isFullScreen);
        switch (relativeLayoutTypes) {
            case BKACTIVITY_BACKGROUND_IMAGE:
                BKUtilLogger.showDebugLog(BKUtilLogger.BK_DEBUG, "Customization Opinion poll BKACTIVITY_BACKGROUND_IMAGE");
                /*if(isFullScreen)
                    relativeLayout.setBackgroundResource(R.drawable.nfl_bg);
                else
                    relativeLayout.setBackgroundResource(R.drawable.nfl_bg_vsmall);
                */break;
        }
    }

    @Override
    public void customizeForOptionsSeparatorView(View view) {
        super.customizeForOptionsSeparatorView(view);
        view.setBackgroundColor(Color.WHITE);
    }

    @Override
    public void customizeForGraphColor(BKUIPrefComponents.BKGraphType graphType, List<Integer> colorsList) {
        super.customizeForGraphColor(graphType, colorsList);
        switch (graphType) {
            case BKACTIVITY_BAR_GRAPH:
                colorsList.clear();
                colorsList.add(Color.parseColor("#1F92C5"));
                colorsList.add(Color.parseColor("#0ABE00"));
                colorsList.add(Color.parseColor("#FFD200"));
                colorsList.add(Color.parseColor("#BA141A"));
                colorsList.add(Color.parseColor("#320071"));
                BKUtilLogger.showDebugLog(BKUtilLogger.BK_DEBUG, "Customization Opinion Poll Bar Graph");
                break;
            case BKACTIVITY_PIE_GRAPH:
                colorsList.clear();
                colorsList.add(Color.parseColor("#1F92C5"));
                colorsList.add(Color.parseColor("#0ABE00"));
                colorsList.add(Color.parseColor("#FFD200"));
                colorsList.add(Color.parseColor("#BA141A"));
                colorsList.add(Color.parseColor("#320071"));
                BKUtilLogger.showDebugLog(BKUtilLogger.BK_DEBUG, "Customization Opinion Poll Pie Graph");
                break;
                default:
                    break;
        }
    }

    @Override
    public void customizeImageButton(ImageButton button, BKUIPrefComponents.BKActivityImageButtonTypes buttonType) {
        super.customizeImageButton(button, buttonType);
        switch (buttonType) {
            case BKACTIVITY_SKIP_BUTTON:
                BKUtilLogger.showDebugLog(BKUtilLogger.BK_DEBUG, "Customization BKTutorial_skip_BUTTON");
//                button.setImageResource(R.drawable.close_button);
                break;
        }
    }

}
