package com.upshotreactlibrary.upshot.customization;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.brandkinesis.BKUIPrefComponents;
import com.brandkinesis.BKUIPrefComponents.BKActivityTextViewTypes;

import java.util.List;

public class UpshotRatingCustomization extends UpshotCustomization {
    private Context mContext;

    public UpshotRatingCustomization(Context context) {
        mContext = context;
    }

    public void customizeButton(Button button, BKUIPrefComponents.BKActivityButtonTypes buttonType) {
        Typeface typeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/" + "violin.ttf");
        switch (buttonType) {
            case BKACTIVITY_SUBMIT_BUTTON:
                button.setTypeface(typeface);
                button.setTextSize(28);
                //button.setTextColor(Color.parseColor(PTBKConstants.BUTTON_TEXT_COLOR));
//                button.setBackgroundResource(R.drawable.pd_submit_btn);
                //button.setBackgroundColor(Color.parseColor("#0685A3"));
                break;

            case BKACTIVITY_RATING_YES_BUTTON:
            case BKACTIVITY_RATING_NO_BUTTON:
                button.setTypeface(typeface);
                button.setTextSize(28);
                //button.setTextColor(Color.parseColor(PTBKConstants.BUTTON_TEXT_COLOR));
//                button.setBackgroundResource(R.drawable.pd_submit_btn);
                break;
            /*case BKACTIVITY_SKIP_BUTTON:
                button.setBackgroundResource(R.drawable.pd_close);
                break;*/
        }
    }


    @Override
    public void customizeTextView(BKActivityTextViewTypes textViewType, TextView textView) {
        super.customizeTextView(textViewType, textView);
        switch (textViewType) {
            case BKACTIVITY_HEADER_TV:
                //textView.setTextColor(Color.WHITE);
                setTextViewProps(textView, 28);
                break;
            case BKACTIVITY_RATING_LIKE_CAPTION_TV:
                setTextViewProps(textView, 28);
                break;
            case BKACTIVITY_RATING_DISLIKE_CAPTION_TV:
                setTextViewProps(textView, 28);
                break;
            case BKACTIVITY_THANK_YOU_TV:
                setTextViewProps(textView, 28);
            case BKACTIVITY_THANK_YOU_APPSTORE_HINT:
                //textView.setText("Thank you layout Thank you layout Thank you layout Thank you layout Thank you layout Thank you layout Thank you layout Thank you layout");
                //textView.setBackgroundColor(Color.CYAN);
                setTextViewProps(textView, 28);
                break;

            case BKACTIVITY_SEPARATOR_LABEL:
                break;
            case BKACTIVITY_LABEL_TYPE_TUTORIAL_BOTTOM_TEXT:
                break;
        }
    }

    private void setTextViewProps(TextView textView, int fontSize) {
        Typeface typeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/" + "violin.ttf");
        textView.setTypeface(typeface);
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(fontSize);
    }

    @Override
    public void customizeBGColor(BKUIPrefComponents.BKBGColors color, BKUIPrefComponents.BKActivityColorTypes colorType) {
        super.customizeBGColor(color, colorType);

        switch (colorType) {
            case BKACTIVITY_BG_COLOR:
                //color.setColor(Color.GREEN);
                color.setColor(Color.TRANSPARENT);
                break;
            case BKACTIVITY_SURVEY_HEADER_COLOR:
                //color.setColor(Color.GRAY);
                break;
            case BKACTIVITY_BOTTOM_COLOR:
                //color.setColor(Color.BLACK);
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

    public void customizeImageView(ImageView imageView, BKUIPrefComponents.BKActivityImageViewType imageType) {
        super.customizeImageView(imageView, imageType);

        switch (imageType) {
            case BACTIVITY_RATING_LIKE_BUTTON:
//                imageView.setBackgroundResource(R.drawable.upshot_rating_like_selector);
                break;
            case BACTIVITY_RATING_DISLIKE_BUTTON:
//                imageView.setBackgroundResource(R.drawable.upshot_rating_dislike_selector);
                break;

            case BKACTIVITY_PORTRAIT_LOGO:
                //imageView.setBackgroundResource(R.drawable.about_screen_logo);
                break;
            case BKACTIVITY_LANDSCAPE_LOGO:
                //imageView.setBackgroundResource(R.drawable.g);
                break;
            case BKACTIVITY_ADS_DEFAULT_IMAGE:
                break;
            /*case BKACTIVITY_BACKGROUND_IMAGE:
                imageView.setBackgroundResource(R.drawable.pd_popup_bg);
                break;*/
        }
    }
}
