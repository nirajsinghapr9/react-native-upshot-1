package com.upshotreactlibrary;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
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
import com.brandkinesis.baseclass.BKActivityLayout;
import com.brandkinesis.utils.BKUtilLogger;

import java.util.List;

import static com.brandkinesis.BKUIPrefComponents.BKActivityImageButtonTypes;
import static com.brandkinesis.BKUIPrefComponents.BKUICheckBox;

public class BKTriviaCustomization extends BKCustomization {
    private Context mContext;
    private Typeface mTypeface;

    public BKTriviaCustomization(Context context) {
        mContext = context;
    }

    public void customizeRadioButton(BKUICheckBox checkBox, boolean isCheckBox) {
        Bitmap check_select;
        check_select = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.chk_trivia_selected);
        checkBox.setSelectedCheckBox(check_select);
        check_select = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.chk_trivia_unselected);
        checkBox.setUnselectedCheckBox(check_select);
    }

    @Override
    public void customizeButton(Button button, BKActivityButtonTypes buttonType) {
        super.customizeButton(button, buttonType);
        try {
            mTypeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/" + "Gotham-Light.otf");
            button.setTypeface(mTypeface);
        } catch (Exception e) {

        }


        switch (buttonType) {
            case BKACTIVITY_SUBMIT_BUTTON:

                button.setBackgroundResource(R.drawable.trivia_selected_but);
                button.setTextColor(Color.parseColor("#FFFFFF"));
                BKUtilLogger.showDebugLog(BKUtilLogger.BK_DEBUG, "Customization TRIVIA BKACTIVITY_TRIVIA_CONTINUE_BUTTON");

                try {
                    RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) button.getLayoutParams();
                    rlp.setMargins(10, 0, 10, 10);
                    button.setLayoutParams(rlp);
                    button.setBackgroundColor(Color.parseColor("#F16522"));

                } catch (Exception e) {

                }


                break;
            case BKACTIVITY_TRIVIA_NEXT_BUTTON:
            case BKACTIVITY_TRIVIA_CONTINUE_BUTTON:
                button.setBackgroundResource(R.drawable.trivia_selected_but);
                button.setTextColor(Color.parseColor("#FFFFFF"));
                BKUtilLogger.showDebugLog(BKUtilLogger.BK_DEBUG, "Customization TRIVIA BKACTIVITY_TRIVIA_CONTINUE_BUTTON");
                break;
            case BKACTIVITY_TRIVIA_PREVIOUS_BUTTON:
                button.setBackgroundResource(R.drawable.trivia_unselected_but);
                button.setTextColor(Color.parseColor("#FFFFFF"));
                BKUtilLogger.showDebugLog(BKUtilLogger.BK_DEBUG, "Customization TRIVIA BKACTIVITY_TRIVIA_PREVIOUS_BUTTON");
                break;
        }
    }

    @Override
    public void customizeImageButton(ImageButton button, BKActivityImageButtonTypes buttonType) {
        super.customizeImageButton(button, buttonType);
        switch (buttonType) {
            case BKACTIVITY_SKIP_BUTTON:
                BKUtilLogger.showDebugLog(BKUtilLogger.BK_DEBUG, "Customization BKACTIVITY_skip_BUTTON");
                button.setImageResource(R.drawable.close_button);
                break;
        }
    }

    @Override
    public void customizeRelativeLayout(BKUIPrefComponents.BKActivityRelativeLayoutTypes relativeLayoutTypes, RelativeLayout relativeLayout, boolean isFullScreen) {
        super.customizeRelativeLayout(relativeLayoutTypes, relativeLayout, isFullScreen);
        switch (relativeLayoutTypes) {
            case BKACTIVITY_BACKGROUND_IMAGE:
                BKUtilLogger.showDebugLog(BKUtilLogger.BK_DEBUG, "Customization Trivia BKACTIVITY_BACKGROUND_IMAGE");
                if (isFullScreen)
                    relativeLayout.setBackgroundResource(R.drawable.shopx_upshot_bg);
                else
                    relativeLayout.setBackgroundResource(R.drawable.shopx_upshot_bg_small);
                break;
        }
    }

    @Override
    public void customizeForLinearLayout(LinearLayout linearLayout, BKUIPrefComponents.BKActivityLinearLayoutTypes linearLayoutTypes) {
        super.customizeForLinearLayout(linearLayout, linearLayoutTypes);
        switch (linearLayoutTypes) {
            case BKACTIVITY_BACKGROUND_IMAGE:
                linearLayout.setBackgroundColor(Color.parseColor("#F16522"));

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
            case BKACTIVITY_PAGINATION_DEFAULT_COLOR:
                color.setColor(Color.BLACK);
                break;
        }

    }

    @Override
    public void customizeTextView(BKActivityTextViewTypes textViewType, TextView textView) {
        super.customizeTextView(textViewType, textView);
        try {
            mTypeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/" + "Gotham-Light.otf");
            textView.setTypeface(mTypeface);
        } catch (Exception e) {

        }
        textView.setTextColor(Color.BLACK);
        switch (textViewType) {


            case BKACTIVITY_THANK_YOU_TV:
                textView.setBackgroundColor(Color.parseColor("#ec3832"));
                textView.setBackgroundColor(Color.parseColor("#f8bc45"));
                textView.setTextColor(Color.parseColor("#ec3832"));
                try {
                    mTypeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/" + "GothamMedium.ttf");
                    textView.setTypeface(mTypeface);
                } catch (Exception e) {

                }
                break;
            case BKACTIVITY_LEADER_BOARD_GRADE_TV:
            case BKACTIVITY_LEADER_BOARD_TITLE_TV:
            case BKACTIVITY_LEADER_BOARD_GRADE_VALUE_TV:
            case BKACTIVITY_LEADER_BOARD_SCORE_TV:
            case BKACTIVITY_LEADER_BOARD_SCORE_VALUE_TV:
            case BKACTIVITY_HEADER_TV:
                textView.setTextColor(Color.parseColor("#FFFFFF"));

                break;
            case BKACTIVITY_SCORE_TV:
                textView.setTextColor(Color.parseColor("#F16522"));

                break;

            case BKACTIVITY_QUESTION_TV:

                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) textView.getLayoutParams();
                int margin5 = (int) convertPxToDp(5, mContext);
                layoutParams.setMargins(margin5, margin5, margin5, 0);
                textView.setLayoutParams(layoutParams);

                try {
                    mTypeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/" + "GothamMedium.ttf");
                    textView.setTypeface(mTypeface);
                } catch (Exception e) {

                }

                break;
/*
                // question
            case BKACTIVITY_SCORE_TV:
                textView.setTextColor(Color.BLACK);
                break;
            case BKACTIVITY_TRIVIA_DESC_TV:
                textView.setTextColor(Color.parseColor("#575757"));
                break;
            case BKACTIVITY_LEADER_BOARD_GRADE_TV:
            case BKACTIVITY_QUESTION_OPTION_TV:
            case BKACTIVITY_LEADER_BOARD_GRADE_VALUE_TV:
            case BKACTIVITY_QUESTION_TV:
                textView.setTextColor(Color.parseColor("#7f7f7f"));
                break;
            case BKACTIVITY_HEADER_TV:
                textView.setTextColor(Color.BLACK);*/


        }
    }
    public static float convertPxToDp(int dp, Context context) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
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

