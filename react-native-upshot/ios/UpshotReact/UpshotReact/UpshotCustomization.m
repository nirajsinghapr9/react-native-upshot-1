//
//  UpshotCustomization.m
//  Upshot-Objc
//
//  Created by Vinod on 15/07/19.
//  Copyright © 2019 Upshot. All rights reserved.
//

#import "UpshotCustomization.h"

#define Color(r,g,b,a) [UIColor colorWithRed:r/255 green:g/255 blue:b/255 alpha:a]

@implementation UpshotCustomization

/*This method is used to customize the images based on Activity Type
 RadioImage
 CheckBox Image
 BackgroundImage
 Logo
 */

//- (void)preferencesForUIImageView:(UIImageView *)imageView ofActivity:(BKActivityType)activityType andType:(BKActivityImageType)activityImage {
//
//    UIImage *radioDefaultImage = [UIImage imageNamed:@""];
//    UIImage *radioSelectedImage = [UIImage imageNamed:@""];
//
//    UIImage *checkboxDefaultImage = [UIImage imageNamed:@""];
//    UIImage *checkboxSelectedImage = [UIImage imageNamed:@""];
//
//    UIImage *backgroundImage = [UIImage imageNamed:@""];
//
//    UIImage *landsacpeLogo = [UIImage imageNamed:@""];
//    UIImage *portraitLogo = [UIImage imageNamed:@""];
//
//    switch (activityImage) {
//        case BKActivityRadioImage:
//            if (radioDefaultImage != nil) {
//                [imageView setImage:radioDefaultImage];
//            }
//            if (radioSelectedImage != nil) {
//                [imageView setHighlightedImage:radioSelectedImage];
//            }
//            break;
//        case BKActivityCheckboxImage:
//
//            if (checkboxDefaultImage != nil) {
//                [imageView setImage:checkboxDefaultImage];
//            }
//            if (checkboxSelectedImage != nil) {
//                [imageView setHighlightedImage:checkboxSelectedImage];
//            }
//            break;
//        case BKActivityBackgroundImage:
//            if (backgroundImage != nil) {
//                [imageView setImage:backgroundImage];
//            }
//            break;
//        case BKActivityPortraitLogo:
//
//            if (portraitLogo != nil) {
//                [imageView setImage:portraitLogo];
//            }
//            break;
//        case BKActivityLandscapeLogo:
//            if (landsacpeLogo != nil) {
//                [imageView setImage:landsacpeLogo];
//            }
//            break;
//        default:
//            break;
//    }
//}

/* This method is used to customize the Slider based on Activity Type */

//- (void)preferencesForSlider:(UISlider *)slider ofActivity:(BKActivityType)activityType {
//
//    UIImage *minTrackImage = [[UIImage imageNamed:@""] resizableImageWithCapInsets:UIEdgeInsetsMake(0, 4, 0, 0) resizingMode:UIImageResizingModeStretch];
//    UIImage *maxTrackImage = [[UIImage imageNamed:@""] resizableImageWithCapInsets:UIEdgeInsetsMake(0, 4, 0, 0) resizingMode:UIImageResizingModeStretch];
//    UIImage *thumbImage = [UIImage imageNamed:@""];
//
//    if (thumbImage != nil) {
//        [slider setThumbImage:thumbImage forState:UIControlStateNormal];
//    }
//    if (minTrackImage != nil) {
//        [slider setMinimumTrackImage:minTrackImage forState:UIControlStateNormal];
//    }
//    if (maxTrackImage != nil) {
//        [slider setMaximumTrackImage:maxTrackImage forState:UIControlStateNormal];
//    }
//}

/*This method is used to customize the graph colors based on graph type
 BarGraph,
 PieGraph
 */

//- (void)preferencesForGraphColor:(BKGraphType)graphType graphColors:(void (^)(NSArray *))block {
//
//    UIColor *color1 = Color(31.0, 146.0, 197.0, 1.0);
//    UIColor *color2 = Color(10.0, 190.0, 0.0, 1.0);
//    UIColor *color3 = Color(255.0, 210.0, 0.0, 1.0);
//    UIColor *color4 = Color(186.0, 20.0, 26.0, 1.0);
//    UIColor *color5 = Color(50.0, 0.0, 113.0, 1.0);
//
//    NSArray *graphColors = @[color1,color2,color3,color4,color5];
//    block(graphColors);
//}

/* This method is used to customize label text color, font style and font size based on activity type*/

//- (void)preferencesForUILabel:(UILabel *)label ofActivity:(BKActivityType)activityType andType:(BKActivityLabelType)activityLabel {
//
//    switch (activityType) {
//        case BKActivityTypeSurvey:
//            switch (activityLabel) {
//                case BKActivityHeaderLabel:
//                    [label setTextColor:Color(55.0, 55.0, 55.0, 1.0)];
//                    [label setFont:[UIFont fontWithName:@"OpenSans-Semibold" size:18.0]];
//                    break;
//                case BKActivityDescriptionLabel:
//                    [label setTextColor:Color(113.0, 113.0, 113.0, 1.0)];
//                    [label setFont:[UIFont fontWithName:@"OpenSans" size:15.0]];
//                    break;
//                case BKActivityQuestionLabel:
//                    [label setTextColor:Color(85.0, 85.0, 85.0, 1.0)];
//                    [label setFont:[UIFont fontWithName:@"OpenSans-Semibold" size:18.0]];
//                    break;
//                case BKActivityOptionLabel:
//                    [label setTextColor:Color(121.0, 121.0, 121.0, 1.0)];
//                    [label setFont:[UIFont fontWithName:@"OpenSans" size:18.0]];
//                    break;
//                case BKActivityThankyouLabel:
//                    [label setTextColor:Color(0.0, 0.0, 0.0, 1.0)];
//                    [label setFont:[UIFont fontWithName:@"OpenSans" size:17.0]];
//                    break;
//                case BKActivitySeparatorLabel:
//                    [label setTextColor:Color(211.0, 214.0, 219.0, 1.0)];
//                    break;
//                case BKActivitySliderMaxValueLabel:
//                    [label setTextColor:Color(0.0, 0.0, 0.0, 1.0)];
//                    [label setFont:[UIFont fontWithName:@"OpenSans" size:13.0]];
//                    break;
//                case BKActivitySliderMinValueLabel:
//                    [label setTextColor:Color(0.0, 0.0, 0.0, 1.0)];
//                    [label setFont:[UIFont fontWithName:@"OpenSans" size:13.0]];
//                    break;
//                case BKActivitySliderScoreLabel:
//                    [label setTextColor:Color(0.0, 0.0, 0.0, 1.0)];
//                    [label setFont:[UIFont fontWithName:@"OpenSans" size:14.0]];
//                    break;
//                case BKActivityMaxValueTitleLabel:
//                    [label setTextColor:Color(0.0, 0.0, 0.0, 1.0)];
//                    [label setFont:[UIFont fontWithName:@"OpenSans" size:14.0]];
//                    break;
//                case BKActivityMinValueTitleLabel:
//                    [label setTextColor:Color(0.0, 0.0, 0.0, 1.0)];
//                    [label setFont:[UIFont fontWithName:@"OpenSans" size:14.0]];
//                    break;
//                default:
//                    break;
//            }
//            break;
//        case BKActivityTypeRating: {
//
//
//        }
//            break;
//        case BKActivityTypeOpinionPoll:{
//
//        }
//            break;
//        case BKActivityTypeTrivia: {
//
//        }
//            break;
//        default:
//            break;
//    }
//}


/*This method is used to customize the TextView based on viewType and activityType
FreeFormText,
CommentField
 */

//- (void)preferencesForTextView:(UITextView *)textView ofActivity:(BKActivityType)activityType andType:(BKActivityViewType)viewType {
//
//
//}

/*This method is used to customize buttons based on activity type */

//- (void)preferencesForUIButton:(UIButton *)button ofActivity:(BKActivityType)activityType andType:(BKActivityButtonType)activityButton {
//
//    if (activityButton == BKActivitySkipButton) {
//        UIImage *skipImage = [UIImage imageNamed:@""];
//        if (skipImage != nil) {
//            [button setImage:skipImage forState:UIControlStateNormal];
//            [button setImage:skipImage forState:UIControlStateSelected];
//            [button setImage:skipImage forState:UIControlStateHighlighted];
//        }
//    }
//
//    switch (activityType) {
//        case BKActivityTypeSurvey:
//            switch (activityButton) {
//
//                case BKActivityContinueButton:
//                    [button setBackgroundColor:Color(80.0, 200.0, 255.0, 1.0)];
//                    [button setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
//                    [button setTitleColor:[UIColor blackColor] forState:UIControlStateSelected];
//                    [button setTitleColor:[UIColor blackColor] forState:UIControlStateHighlighted];
//                    [button.titleLabel setFont:[UIFont fontWithName:@"OpenSans-Semibold" size:17.0]];
//
//                    break;
//                case BKActivityNextButton:
//                    [button setBackgroundColor:Color(80.0, 200.0, 255.0, 1.0)];
//                    [button setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
//                    [button setTitleColor:[UIColor blackColor] forState:UIControlStateSelected];
//                    [button setTitleColor:[UIColor blackColor] forState:UIControlStateHighlighted];
//                    [button.titleLabel setFont:[UIFont fontWithName:@"OpenSans-Semibold" size:17.0]];
//
//                    break;
//                case BKActivityPreviousButton:
//                    [button setBackgroundColor:Color(80.0, 200.0, 255.0, 1.0)];
//                    [button setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
//                    [button setTitleColor:[UIColor blackColor] forState:UIControlStateSelected];
//                    [button setTitleColor:[UIColor blackColor] forState:UIControlStateHighlighted];
//                    [button.titleLabel setFont:[UIFont fontWithName:@"OpenSans-Semibold" size:17.0]];
//
//                    break;
//                case BKActivitySubmitButton:
//                    [button setBackgroundColor:Color(80.0, 200.0, 255.0, 1.0)];
//                    [button setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
//                    [button setTitleColor:[UIColor blackColor] forState:UIControlStateSelected];
//                    [button setTitleColor:[UIColor blackColor] forState:UIControlStateHighlighted];
//                    [button.titleLabel setFont:[UIFont fontWithName:@"OpenSans-Semibold" size:17.0]];
//
//                    break;
//                default:
//                    break;
//            }
//            break;
//        case BKActivityTypeRating: {
//            switch (activityButton) {
//                case BKActivitySubmitButton:
//                    [button setBackgroundColor:Color(80.0, 200.0, 255.0, 1.0)];
//                    [button setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
//                    [button setTitleColor:[UIColor blackColor] forState:UIControlStateSelected];
//                    [button setTitleColor:[UIColor blackColor] forState:UIControlStateHighlighted];
//                    [button.titleLabel setFont:[UIFont fontWithName:@"OpenSans-Semibold" size:17.0]];
//
//                    break;
//                case BKActivityRatingYesButton:
//                    [button setBackgroundColor:Color(80.0, 200.0, 255.0, 1.0)];
//                    [button setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
//                    [button setTitleColor:[UIColor blackColor] forState:UIControlStateSelected];
//                    [button setTitleColor:[UIColor blackColor] forState:UIControlStateHighlighted];
//                    [button.titleLabel setFont:[UIFont fontWithName:@"OpenSans-Semibold" size:17.0]];
//
//                    break;
//                case BKActivityRatingNoButton:
//                    [button setBackgroundColor:Color(80.0, 200.0, 255.0, 1.0)];
//                    [button setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
//                    [button setTitleColor:[UIColor blackColor] forState:UIControlStateSelected];
//                    [button setTitleColor:[UIColor blackColor] forState:UIControlStateHighlighted];
//                    [button.titleLabel setFont:[UIFont fontWithName:@"OpenSans-Semibold" size:17.0]];
//
//                    break;
//                default:
//                    break;
//            }
//        }
//            break;
//        case BKActivityTypeOpinionPoll: {
//            switch (activityButton) {
//                case BKActivitySubmitButton:
//                    [button setBackgroundColor:Color(80.0, 200.0, 255.0, 1.0)];
//                    [button setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
//                    [button setTitleColor:[UIColor blackColor] forState:UIControlStateSelected];
//                    [button setTitleColor:[UIColor blackColor] forState:UIControlStateHighlighted];
//                    [button.titleLabel setFont:[UIFont fontWithName:@"OpenSans-Semibold" size:17.0]];
//
//                    break;
//                default:
//                    break;
//            }
//        }
//            break;
//        case BKActivityTypeTrivia: {
//
//            switch (activityButton) {
//
//                case BKActivityContinueButton:
//                    [button setBackgroundColor:Color(80.0, 200.0, 255.0, 1.0)];
//                    [button setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
//                    [button setTitleColor:[UIColor blackColor] forState:UIControlStateSelected];
//                    [button setTitleColor:[UIColor blackColor] forState:UIControlStateHighlighted];
//                    [button.titleLabel setFont:[UIFont fontWithName:@"OpenSans-Semibold" size:17.0]];
//
//                    break;
//
//                case BKActivityNextButton:
//                    [button setBackgroundColor:Color(80.0, 200.0, 255.0, 1.0)];
//                    [button setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
//                    [button setTitleColor:[UIColor blackColor] forState:UIControlStateSelected];
//                    [button setTitleColor:[UIColor blackColor] forState:UIControlStateHighlighted];
//                    [button.titleLabel setFont:[UIFont fontWithName:@"OpenSans-Semibold" size:17.0]];
//
//                    break;
//                case BKActivityPreviousButton:
//                    [button setBackgroundColor:Color(80.0, 200.0, 255.0, 1.0)];
//                    [button setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
//                    [button setTitleColor:[UIColor blackColor] forState:UIControlStateSelected];
//                    [button setTitleColor:[UIColor blackColor] forState:UIControlStateHighlighted];
//                    [button.titleLabel setFont:[UIFont fontWithName:@"OpenSans-Semibold" size:17.0]];
//
//                    break;
//                case BKActivitySubmitButton:
//                    [button setBackgroundColor:Color(80.0, 200.0, 255.0, 1.0)];
//                    [button setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
//                    [button setTitleColor:[UIColor blackColor] forState:UIControlStateSelected];
//                    [button setTitleColor:[UIColor blackColor] forState:UIControlStateHighlighted];
//                    [button.titleLabel setFont:[UIFont fontWithName:@"OpenSans-Semibold" size:17.0]];
//
//                    break;
//
//                default:
//                    break;
//            }
//        }
//        default:
//            break;
//    }
//}


/*This method is used to customize the UITextField based on viewType and activityType
 FreeFormText,
 CommentField
 */

//- (void)preferencesForTextField:(UITextField *)textField ofActivity:(BKActivityType)activityType andType:(BKActivityViewType)viewType {
//
//}

/*This method is used to customize the Star and Smiley Images based on activityType
 StarRating,
 EmojiRating,
 */

//- (void)preferencesForRatingActivity:(BKActivityType)activityType andType:(BKActivityRatingType)activityRating withImages:(void (^)(NSArray *, NSArray *))block {
//
//    switch (activityRating) {
//        case BKActivityStarRating: {
//
//            UIImage *activeimage = [UIImage imageNamed:@""];
//            UIImage *inActiveimage = [UIImage imageNamed:@""];
//            if (activeimage != nil && inActiveimage != nil) {
//                NSArray *active = @[activeimage,activeimage,activeimage,activeimage,activeimage];
//                NSArray *inactive = @[inActiveimage,inActiveimage,inActiveimage,inActiveimage,inActiveimage];
//                block(inactive,active);
//            }
//        }
//            break;
//        case BKActivityEmojiRating: {
//
//            UIImage *defVerybadImage = [UIImage imageNamed:@""];
//            UIImage *defbadImage = [UIImage imageNamed:@""];
//            UIImage *defavgImage = [UIImage imageNamed:@""];
//            UIImage *defgoodImage = [UIImage imageNamed:@""];
//            UIImage *defveryGoodImage = [UIImage imageNamed:@""];
//
//            UIImage *selVerybadImage = [UIImage imageNamed:@""];
//            UIImage *selbadImage = [UIImage imageNamed:@""];
//            UIImage *selavgImage = [UIImage imageNamed:@""];
//            UIImage *selgoodImage = [UIImage imageNamed:@""];
//            UIImage *selveryGoodImage = [UIImage imageNamed:@""];
//
//            if (defVerybadImage != nil && defbadImage != nil && defavgImage != nil && defgoodImage!= nil &&
//                defveryGoodImage != nil && selVerybadImage != nil && selbadImage != nil && selavgImage != nil && selgoodImage != nil && selveryGoodImage != nil) {
//                NSArray *active = @[selVerybadImage,selbadImage,selavgImage,selgoodImage,selveryGoodImage];
//                NSArray *inactive = @[defVerybadImage,defbadImage,defavgImage,defgoodImage,defveryGoodImage];
//                block(inactive,active);
//            }
//        }
//        default:
//            break;
//    }
//}

/*This method is used to customize the Background Colors of View based on activityColor and activityType */

//- (void)preferencesForUIColor:(BKBGColor *)color ofActivity:(BKActivityType)activityType colorType:(BKActivityColorType)activityColor andButtonType:(BOOL)isFloatButton {
//
//    switch (activityType) {
//        case BKActivityTypeSurvey:
//            switch (activityColor) {
//                case BKActivityBGColor:
//                    [color setBackgroundColor:[UIColor whiteColor]];
//                    break;
//                case BKActivityHeaderBGColor:
//                    [color setBackgroundColor:[UIColor clearColor]];
//                    break;
//                case BKActivityBottomBGColor:
//                    [color setBackgroundColor:[UIColor clearColor]];
//                    break;
//                case BKActivityPageTintColor:
//                    [color setBackgroundColor:[UIColor grayColor]];
//                    break;
//                case BKActivityCurrentPageTintColor:
//                    [color setBackgroundColor:[UIColor blackColor]];
//                    break;
//                case BKActivityAnsweredPageTintColor:
//                    [color setBackgroundColor:Color(80.0, 200.0, 255.0, 1.0)];
//                    break;
//                case BKActivityNPSSelectedBGColor:
//                    [color setBackgroundColor:Color(245.0, 166.0, 35.0, 1.0)];
//                    break;
//                case BKActivityNPSUnSelectedBGColor:
//                    [color setBackgroundColor:[UIColor whiteColor]];
//                    break;
//                case BKActivityNPSSelectedTextColor:
//                    [color setBackgroundColor:[UIColor whiteColor]];
//                    break;
//                case BKActivityNPSUnSelectedTextColor:
//                    [color setBackgroundColor:Color(79.0, 92.0, 99.0, 1.0)];
//                    break;
//                case BKActivityNPSDefaultBorderColor:
//                    [color setBackgroundColor:Color(188.0, 188.0, 188.0, 1.0)];
//                    break;
//                case BKActivityNPSSelectedBorderColor:
//                    [color setBackgroundColor:Color(208.0, 150.0, 25.0, 1.0)];
//                    break;
//                default:
//                    break;
//            }
//            break;
//        case BKActivityTypeRating: {
//
//            switch (activityColor) {
//                case BKActivityBGColor:
//                    [color setBackgroundColor:[UIColor whiteColor]];
//                    break;
//                case BKActivityHeaderBGColor:
//                    [color setBackgroundColor:[UIColor clearColor]];
//                    break;
//                case BKActivityBottomBGColor:
//                    [color setBackgroundColor:[UIColor clearColor]];
//                    break;
//                default:
//                    break;
//            }
//        }
//            break;
//        case BKActivityTypeOpinionPoll: {
//            switch (activityColor) {
//
//                case BKActivityBGColor:
//                    [color setBackgroundColor:[UIColor whiteColor]];
//                    break;
//                case BKActivityHeaderBGColor:
//                    [color setBackgroundColor:[UIColor clearColor]];
//                    break;
//                case BKActivityBottomBGColor:
//                    [color setBackgroundColor:[UIColor clearColor]];
//                    break;
//                case BKActivityPercentageColor:
//                    [color setBackgroundColor:[UIColor blackColor]];
//                    break;
//                case BKActivityGraphLabelColor:
//                    [color setBackgroundColor:[[UIColor blackColor] colorWithAlphaComponent:0.8]];
//                    break;
//                case BKActivityStrokeColor:
//                    [color setBackgroundColor:[[UIColor blackColor] colorWithAlphaComponent:0.8]];
//                    break;
//                default:
//                    break;
//            }
//        }
//            break;
//        case BKActivityTypeTrivia: {
//
//            switch (activityColor) {
//                case BKActivityBGColor:
//                    [color setBackgroundColor:[UIColor whiteColor]];
//                    break;
//                case BKActivityHeaderBGColor:
//                    [color setBackgroundColor:[UIColor clearColor]];
//                    break;
//                case BKActivityBottomBGColor:
//                    [color setBackgroundColor:[UIColor clearColor]];
//                    break;
//                case BKActivityPercentageColor:
//                    [color setBackgroundColor:[UIColor blackColor]];
//                    break;
//                case BKActivityGraphLabelColor:
//                    [color setBackgroundColor:[[UIColor blackColor] colorWithAlphaComponent:0.8]];
//                    break;
//                case BKActivityStrokeColor:
//                    [color setBackgroundColor:[[UIColor blackColor] colorWithAlphaComponent:0.8]];
//                    break;
//                default:
//                    break;
//            }
//        }
//            break;
//        default:
//            break;
//    }
//}

//- (CGFloat)preferencesForCornerRadiustoActivity:(BKActivityType)activityType {
//    
//    return 10.0;
//}

@end
