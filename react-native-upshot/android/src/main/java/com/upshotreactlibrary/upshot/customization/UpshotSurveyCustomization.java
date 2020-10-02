package com.upshotreactlibrary.upshot.customization;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.brandkinesis.BKUIPrefComponents;
import com.brandkinesis.BKUIPrefComponents.BKActivityButtonTypes;
import com.brandkinesis.BKUIPrefComponents.BKActivityColorTypes;
import com.brandkinesis.BKUIPrefComponents.BKActivityTextViewTypes;
import com.brandkinesis.BKUIPrefComponents.BKBGColors;

import java.util.List;

public class UpshotSurveyCustomization extends UpshotCustomization {
    Context mContext;

    public UpshotSurveyCustomization(Context context) {
        mContext = context;

    }

    public void customizeRadioButton(BKUIPrefComponents.BKUICheckBox checkBox, boolean isCheckBox) {
        Bitmap check_select;
        /*if (isCheckBox) {
            check_select = BitmapFactory.decodeResource(mContext.getResources(),
                    R.drawable.pd_checkbox_selected);
            checkBox.setSelectedCheckBox(check_select);
        } else {
            check_select = BitmapFactory.decodeResource(mContext.getResources(),
                    R.drawable.pd_radio_selected);
            checkBox.setSelectedCheckBox(check_select);
        }
        checkBox.setUnselectedCheckBox(BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.pb_unselected_option));
*/
    }

    @Override
    public void customizeButton(Button button, BKActivityButtonTypes buttonType) {
        super.customizeButton(button, buttonType);
//       mTypeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/" + "regular.ttf");
//       button.setTypeface(mTypeface);

        Typeface typeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/" + "violin.ttf");
        switch (buttonType) {
            case BKACTIVITY_SURVEY_CONTINUE_BUTTON:
                button.setTypeface(typeface);
                button.setTextSize(28);
                //button.setTextColor(Color.parseColor(PTBKConstants.BUTTON_TEXT_COLOR));
//                button.setBackgroundResource(R.drawable.pd_submit_btn);
                break;
            case BKACTIVITY_SURVEY_NEXT_BUTTON:
                button.setTypeface(typeface);
                button.setTextSize(28);
                //button.setTextColor(Color.parseColor(PTBKConstants.BUTTON_TEXT_COLOR));
//                button.setBackgroundResource(R.drawable.pd_next_btn);
                break;
            case BKACTIVITY_SURVEY_PREVIOUS_BUTTON:
                button.setTypeface(typeface);
                button.setTextSize(28);
                //button.setTextColor(Color.parseColor(PTBKConstants.BUTTON_TEXT_COLOR));
//                button.setBackgroundResource(R.drawable.pd_prev_btn);
                break;
            case BKACTIVITY_SUBMIT_BUTTON:
                button.setTypeface(typeface);
                button.setTextSize(28);
                //button.setTextColor(Color.parseColor(PTBKConstants.BUTTON_TEXT_COLOR));
//                button.setBackgroundResource(R.drawable.pd_submit_btn);
                //button.setBackgroundColor(Color.parseColor("#0685A3"));
                break;
            /*case BKACTIVITY_SKIP_BUTTON:
                button.setBackgroundResource(R.drawable.pd_close);
                break;*/
        }
    }

    @Override
    public void customizeBGColor(BKBGColors color, BKActivityColorTypes colorType) {
        super.customizeBGColor(color, colorType);

        switch (colorType) {
            case BKACTIVITY_BG_COLOR:
                //color.setColor(Color.RED);
                color.setColor(Color.TRANSPARENT);
                break;
            case BKACTIVITY_SURVEY_HEADER_COLOR:
                //color.setColor(Color.GRAY);
                break;
            case BKACTIVITY_BOTTOM_COLOR:
                //color.setColor(Color.CYAN);
                break;

            default:
                break;
        }
    }

    @Override
    public void customizeRating(List<Bitmap> selectedRatingList, List<Bitmap> unselectedRatingList, BKUIPrefComponents.BKActivityRatingTypes ratingType) {
        super.customizeRating(selectedRatingList, unselectedRatingList, ratingType);
        switch (ratingType) {
            case BKACTIVITY_STAR_RATING:
                /*Bitmap selected = BitmapFactory.decodeResource(mContext.getResources(),
                        R.drawable.pd_selected_star);
                Bitmap unselected = BitmapFactory.decodeResource(mContext.getResources(),
                        R.drawable.pd_unselected_star);
                selectedRatingList.add(selected);
                unselectedRatingList.add(unselected);*/
                break;
            case BKACTIVITY_EMOJI_RATING:
                /*selectedRatingList.add(getBitmap(R.drawable.pd_vhappy_active));
                selectedRatingList.add(getBitmap(R.drawable.pd_happy_active));
                selectedRatingList.add(getBitmap(R.drawable.pd_confused_active));
                selectedRatingList.add(getBitmap(R.drawable.pd_sad_active));
                selectedRatingList.add(getBitmap(R.drawable.pd_vsad_active));

                unselectedRatingList.add(getBitmap(R.drawable.pd_vhappy_inactive));
                unselectedRatingList.add(getBitmap(R.drawable.pd_happy_inactive));
                unselectedRatingList.add(getBitmap(R.drawable.pd_confused_inactive));
                unselectedRatingList.add(getBitmap(R.drawable.pd_sad_inactive));
                unselectedRatingList.add(getBitmap(R.drawable.pd_vsad_inactive));
                */break;
        }
    }

    private Bitmap getBitmap(int id) {

        return BitmapFactory.decodeResource(mContext.getResources(), id);
    }

    @Override
    public void customizeTextView(BKActivityTextViewTypes textViewType, TextView textView) {
        super.customizeTextView(textViewType, textView);
//        mTypeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/" + "regular.ttf");
//        textView.setTypeface(mTypeface);

        switch (textViewType) {
            // question
            case BKACTIVITY_HEADER_TV:
                //textView.setBackgroundColor(Color.GREEN);
                textView.setVisibility(View.VISIBLE);
                break;
            case BKACTIVITY_QUESTION_TV:
                setTextViewProps(textView, 28);
                break;
            case BKACTIVITY_SURVEY_DESC_TV:
                setTextViewProps(textView, 28);
                break;
            case BKACTIVITY_THANK_YOU_TV:
                //textView.setBackgroundColor(Color.CYAN);
                //textView.setTextColor(Color.BLACK);
                setTextViewProps(textView, 28);
                break;

            case BKACTIVITY_SEPARATOR_LABEL:
                textView.setBackgroundColor(Color.WHITE);
                break;

            case BKACTIVITY_OPTION_TV:
                setTextViewProps(textView, 22);
                break;
        }
    }

    private void setTextViewProps(TextView textView, int fontSize) {
        Typeface typeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/" + "violin.ttf");
        textView.setTypeface(typeface);
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(fontSize);
    }

    public void customizeImageView(ImageView imageView, BKUIPrefComponents.BKActivityImageViewType imageType) {
        super.customizeImageView(imageView, imageType);

        switch (imageType) {

            case BKACTIVITY_PORTRAIT_LOGO:
                //imageView.setBackgroundResource(R.drawable.about_screen_logo);
                break;
            case BKACTIVITY_LANDSCAPE_LOGO:
                //imageView.setBackgroundResource(R.drawable.g);
                break;
            case BKACTIVITY_ADS_DEFAULT_IMAGE:
                break;
            /*case BKACTIVITY_BACKGROUND_IMAGE:
                imageView.setBackgroundResource(R.drawable.pd__survey_popup_bg);
                break;*/
        }
    }
}

