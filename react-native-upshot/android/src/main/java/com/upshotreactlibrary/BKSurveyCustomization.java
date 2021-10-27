package com.upshotreactlibrary;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.StateSet;
import android.util.TypedValue;
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
import com.brandkinesis.BKUIPrefComponents.BKActivityButtonTypes;
import com.brandkinesis.BKUIPrefComponents.BKActivityColorTypes;
import com.brandkinesis.BKUIPrefComponents.BKActivityTextViewTypes;
import com.brandkinesis.BKUIPrefComponents.BKBGColors;
import com.brandkinesis.utils.BKUtilLogger;

import java.util.List;

import static com.brandkinesis.BKUIPrefComponents.BKActivityColorTypes.BKACTIVITY_PAGINATION_ANSWERED_COLOR;
import static com.brandkinesis.BKUIPrefComponents.BKActivityColorTypes.BKACTIVITY_PAGINATION_BORDER_COLOR;
import static com.brandkinesis.BKUIPrefComponents.BKActivityColorTypes.BKACTIVITY_PAGINATION_DEFAULT_COLOR;

public class BKSurveyCustomization extends BKCustomization {
    private Context mContext;
    private Typeface mTypeface;

    public BKSurveyCustomization(Context context) {
        mContext = context;

    }

    @Override
    public void customizeSeekBar(BKUIPrefComponents.BKActivitySeekBarTypes seekBarTypes, SeekBar seekBar) {
        super.customizeSeekBar(seekBarTypes, seekBar);

    }

    @Override
    public void customizeImageButton(ImageButton button, BKUIPrefComponents.BKActivityImageButtonTypes buttonType) {
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

    public void customizeRadioButton(BKUIPrefComponents.BKUICheckBox checkBox, boolean isCheckBox) {
        Bitmap check_select;
        check_select = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.chk_trivia_selected);
        checkBox.setSelectedCheckBox(check_select);
        check_select = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.chk_trivia_unselected);
        checkBox.setUnselectedCheckBox(check_select);
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
            case BKACTIVITY_SURVEY_CONTINUE_BUTTON:
            case BKACTIVITY_SURVEY_NEXT_BUTTON:
                button.setBackgroundResource(R.drawable.trivia_selected_but);
                button.setTextColor(Color.parseColor("#FFFFFF"));
                BKUtilLogger.showDebugLog(BKUtilLogger.BK_DEBUG, "Customization TRIVIA BKACTIVITY_TRIVIA_CONTINUE_BUTTON");
                break;
            case BKACTIVITY_SURVEY_PREVIOUS_BUTTON:
                button.setBackgroundResource(R.drawable.trivia_unselected_but);
                button.setTextColor(Color.parseColor("#FFFFFF"));
                BKUtilLogger.showDebugLog(BKUtilLogger.BK_DEBUG, "Customization TRIVIA BKACTIVITY_TRIVIA_PREVIOUS_BUTTON");
                break;
        }
    }

    @Override
    public void customizeBGColor(BKBGColors color, BKActivityColorTypes colorType) {
        super.customizeBGColor(color, colorType);
        if (colorType == BKACTIVITY_PAGINATION_DEFAULT_COLOR) {
            color.setColor(Color.BLACK);

        } else if (colorType == BKACTIVITY_PAGINATION_ANSWERED_COLOR) {
            color.setColor(Color.RED);

        } else if (colorType == BKACTIVITY_PAGINATION_BORDER_COLOR) {
            color.setColor(Color.GRAY);

        }


    }

    @Override
    public void customizeForOptionsSeparatorView(View view) {
        super.customizeForOptionsSeparatorView(view);
        view.setBackgroundColor(Color.WHITE);

    }

    public static float convertPxToDp(int dp, Context context) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }

    @Override
    public void customizeRating(List<Bitmap> selectedRatingList, List<Bitmap> unselectedRatingList, BKUIPrefComponents.BKActivityRatingTypes ratingType) {
        super.customizeRating(selectedRatingList, unselectedRatingList, ratingType);
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
        }
    }

    public void customizeImageView(ImageView imageView, BKUIPrefComponents.BKActivityImageViewType imageType) {
        super.customizeImageView(imageView, imageType);
    }

    @Override
    public void customizeEditText(BKUIPrefComponents.BKActivityEditTextTypes EditTextType, EditText editText) {
        super.customizeEditText(EditTextType, editText);
        try {
            mTypeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/" + "Gotham-Light.otf");
            editText.setTypeface(mTypeface);
        } catch (Exception e) {

        }
        editText.setTextColor(Color.BLACK);
    }
}

