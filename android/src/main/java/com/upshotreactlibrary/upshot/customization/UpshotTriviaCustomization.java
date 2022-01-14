package com.upshotreactlibrary.upshot.customization;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.brandkinesis.BKUIPrefComponents;
import com.brandkinesis.BKUIPrefComponents.BKActivityButtonTypes;
import com.brandkinesis.BKUIPrefComponents.BKActivityColorTypes;
import com.brandkinesis.BKUIPrefComponents.BKActivityTextViewTypes;
import com.brandkinesis.BKUIPrefComponents.BKBGColors;
import com.brandkinesis.utils.BKUtilLogger;

import java.util.List;

import static com.brandkinesis.BKUIPrefComponents.BKActivityImageButtonTypes;
import static com.brandkinesis.BKUIPrefComponents.BKUICheckBox;

public class UpshotTriviaCustomization extends UpshotCustomization {
    private Context mContext;
    private Typeface mTypeface;

    public UpshotTriviaCustomization(Context context) {
        mContext = context;
    }

    public void customizeRadioButton(BKUICheckBox checkBox, boolean isCheckBox) {
       /* Bitmap check_select, default_select;
        check_select = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.nfl_checkbox);
        checkBox.setSelectedCheckBox(check_select);
       */ /* if (isCheckBox) {
            check_select = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.nfl_checkbox);
            checkBox.setSelectedCheckBox(check_select);
        } else {
            check_select = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.nfl_check);
            checkBox.setSelectedCheckBox(check_select);
        }*/
        /*default_select = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.nfl_uncheck);
        checkBox.setUnselectedCheckBox(default_select);
    */}

    @Override
    public void customizeButton(Button button, BKActivityButtonTypes buttonType) {
        super.customizeButton(button, buttonType);
        mTypeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/" + "violin.ttf");
        button.setTypeface(mTypeface);
        switch (buttonType) {
            case BKACTIVITY_TRIVIA_CONTINUE_BUTTON:
                button.setBackgroundColor(Color.parseColor("#179CD5"));
                button.setTextColor(Color.parseColor("#00FFFF"));
                BKUtilLogger.showDebugLog(BKUtilLogger.BK_DEBUG, "Customization TRIVIA BKACTIVITY_TRIVIA_CONTINUE_BUTTON");
                break;
            case BKACTIVITY_TRIVIA_NEXT_BUTTON:
                button.setBackgroundColor(Color.parseColor("#179CD5"));
                button.setTextColor(Color.parseColor("#00FFFF"));
                BKUtilLogger.showDebugLog(BKUtilLogger.BK_DEBUG, "Customization TRIVIA BKACTIVITY_TRIVIA_NEXT_BUTTON");
                break;
            case BKACTIVITY_TRIVIA_PREVIOUS_BUTTON:
                button.setBackgroundColor(Color.parseColor("#179CD5"));
                button.setTextColor(Color.parseColor("#00FFFF"));
                BKUtilLogger.showDebugLog(BKUtilLogger.BK_DEBUG, "Customization TRIVIA BKACTIVITY_TRIVIA_PREVIOUS_BUTTON");
                break;
            case BKACTIVITY_SUBMIT_BUTTON:
                button.setBackgroundColor(Color.parseColor("#179CD5"));
                button.setTextColor(Color.parseColor("#00FFFF"));
                BKUtilLogger.showDebugLog(BKUtilLogger.BK_DEBUG, "Customization TRIVIA BKACTIVITY_SUBMIT_BUTTON");
                // button.setBackgroundColor(Color.parseColor("#0685A3"));
                break;
        }
    }

    @Override
    public void customizeImageButton(ImageButton button, BKActivityImageButtonTypes buttonType) {
        super.customizeImageButton(button, buttonType);
        switch (buttonType) {
            case BKACTIVITY_SKIP_BUTTON:
                BKUtilLogger.showDebugLog(BKUtilLogger.BK_DEBUG, "Customization BKACTIVITY_skip_BUTTON");
//                button.setImageResource(R.drawable.close_button);
                break;
        }
    }

    @Override
    public void customizeRelativeLayout(BKUIPrefComponents.BKActivityRelativeLayoutTypes relativeLayoutTypes, RelativeLayout relativeLayout, boolean isFullScreen) {
        super.customizeRelativeLayout(relativeLayoutTypes, relativeLayout, isFullScreen);
        switch (relativeLayoutTypes) {
            case BKACTIVITY_BACKGROUND_IMAGE:
                BKUtilLogger.showDebugLog(BKUtilLogger.BK_DEBUG, "Customization Trivia BKACTIVITY_BACKGROUND_IMAGE");
                if (isFullScreen){

//                    relativeLayout.setBackgroundResource(R.drawable.nfl_bg);
                }
                else{

//                    relativeLayout.setBackgroundResource(R.drawable.nfl_small);
                }
                break;
        }
    }

    @Override
    public void customizeForLinearLayout(LinearLayout linearLayout, BKUIPrefComponents.BKActivityLinearLayoutTypes linearLayoutTypes) {
        super.customizeForLinearLayout(linearLayout, linearLayoutTypes);
        switch (linearLayoutTypes) {
            case BKACTIVITY_BACKGROUND_IMAGE:
                linearLayout.setBackgroundColor(Color.TRANSPARENT);
                break;
        }

    }

    @Override
    public void customizeForOptionsSeparatorView(View view) {
        super.customizeForOptionsSeparatorView(view);
        view.setBackgroundColor(Color.WHITE);
    }

    @Override
    public void customizeBGColor(BKBGColors color, BKActivityColorTypes colorType) {
        super.customizeBGColor(color, colorType);

        switch (colorType) {

            case BKACTIVITY_TRIVIA_TITLE_COLOR:
                color.setColor(Color.YELLOW);
                break;
            case BKACTIVITY_TRIVIA_HEADER_COLOR:
                color.setColor(Color.YELLOW);
                break;
            case BKACTIVITY_XAXIS_COLOR:
            case BKACTIVITY_XAXIS_TEXT_COLOR_COLOR:
            case BKACTIVITY_YAXIS_TEXT_COLOR_COLOR:
            case BKACTIVITY_PAGINATION_DEFAULT_COLOR:
            case BKACTIVITY_YAXIS_COLOR:
                color.setColor(Color.WHITE);
                break;
            case BKACTIVITY_PAGINATION_ANSWERED_COLOR:
                color.setColor(Color.parseColor("#00FFFF"));
            default:
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
            case BKACTIVITY_SCORE_TV:
                textView.setTextColor(Color.parseColor("#00FFFF"));
                break;
            case BKACTIVITY_TRIVIA_DESC_TV:
                textView.setTextColor(Color.parseColor("#575757"));
                break;
            case BKACTIVITY_LEADER_BOARD_SCORE_TV:
            case BKACTIVITY_LEADER_BOARD_GRADE_TV:
            case BKACTIVITY_QUESTION_OPTION_TV:
            case BKACTIVITY_LEADER_BOARD_GRADE_VALUE_TV:
            case BKACTIVITY_LEADER_BOARD_SCORE_VALUE_TV:
            case BKACTIVITY_QUESTION_TV:
                textView.setTextColor(Color.WHITE);
                break;
            case BKACTIVITY_HEADER_TV:
                textView.setTextColor(Color.parseColor("#00FFFF"));


        }
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
                BKUtilLogger.showDebugLog(BKUtilLogger.BK_DEBUG, "Customization Trivia Bar Graph");
                break;
            case BKACTIVITY_PIE_GRAPH:
                colorsList.clear();
                colorsList.add(Color.parseColor("#1F92C5"));
                colorsList.add(Color.parseColor("#0ABE00"));
                colorsList.add(Color.parseColor("#FFD200"));
                colorsList.add(Color.parseColor("#BA141A"));
                colorsList.add(Color.parseColor("#320071"));
                BKUtilLogger.showDebugLog(BKUtilLogger.BK_DEBUG, "Customization Trivia Pie Graph");
                break;
            default:
                break;
        }
    }
}

