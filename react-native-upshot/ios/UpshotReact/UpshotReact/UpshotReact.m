//
//  UpshotReact.m
//  UpshotReact
//
//  Created by Vinod on 07/01/20.
//  Copyright © 2020 [x]cubeLabs. All rights reserved.
//

#import "UpshotReact.h"
#import "UpshotCustomization.h"

#import <React/RCTBridge.h>
#import <React/RCTEventDispatcher.h>

@implementation UpshotReact

RCT_EXPORT_MODULE();

#pragma mark SupportedEvents

- (NSArray<NSString *> *)supportedEvents {
    
  return @[@"UpshotDeepLink"];
}

#pragma mark Initialize Upshot
 
RCT_EXPORT_METHOD(initializeUpshotWithOptions:(NSString *)options) {
    
    NSDictionary *initOptions = [self convertJsonStringToJson:options];
    [[BrandKinesis sharedInstance] initializeWithOptions:initOptions delegate:self];
    UpshotCustomization *customization = [[UpshotCustomization alloc] init];
    [[BKUIPreferences preferences] setDelegate:customization];
}

RCT_EXPORT_METHOD(initializeUpshotUsingConfigFile) {
    
    [[BrandKinesis sharedInstance] initializeWithDelegate:self];
}


#pragma mark PageViewEvent

RCT_EXPORT_METHOD(createPageviewEvent:(NSString *_Nonnull) currentPage) {
    
    if (currentPage && ![currentPage isEqualToString:@""]) {
      NSString *eventId = [[BrandKinesis sharedInstance] createEvent:BKPageViewNative params:@{BKCurrentPage: currentPage} isTimed:YES];
    }
}

#pragma mark CustomEvent

RCT_EXPORT_METHOD(createCustomEvent:(NSString *_Nonnull)eventName payload:(NSString *_Nonnull)payload timed:(BOOL)isTimed) {
    
    if (eventName && ![eventName isEqualToString: @""]) {
        NSDictionary *params = [self convertJsonStringToJson:payload];
        NSString *eventId = [[BrandKinesis sharedInstance] createEvent:eventName params:params isTimed:isTimed];
    }
}

#pragma mark LocationEvent

RCT_EXPORT_METHOD(createLocationEvent:(NSString *_Nonnull)latitude longitude:(NSString *_Nonnull)longitude) {
    
    CGFloat lat = [latitude floatValue];
    CGFloat lon = [longitude floatValue];
    [[BrandKinesis sharedInstance] createLocationEvent:lat longitude:lon];
}

#pragma mark Close And Dispatch Events

RCT_EXPORT_METHOD(setValueAndClose:(NSString *_Nonnull)payload forEvent:(NSString *_Nonnull)eventId) {
    
    NSDictionary *params = [self convertJsonStringToJson:payload];
    [[BrandKinesis sharedInstance] setValueAndClose:params forEvent:eventId];
}

RCT_EXPORT_METHOD(closeEventForId:(NSString *_Nonnull)eventId) {
    
    [[BrandKinesis sharedInstance] closeEventForID:eventId];
}

RCT_EXPORT_METHOD(dispatchEventsWithTimedEvents:(BOOL)timed) {
    
    [[BrandKinesis sharedInstance] dispatchEventsWithTimedEvents:timed completionBlock:^(BOOL dispatched) {
        
    }];
}

#pragma mark Upshot Actions

RCT_EXPORT_METHOD(showActivityWithType:(NSInteger)type andTag:(NSString *)tag) {
    UpshotCustomization *customization = [[UpshotCustomization alloc] init];
    [[BKUIPreferences preferences] setDelegate:customization];
    BKActivityType activityType = (BKActivityType)type;
    [[BrandKinesis sharedInstance] showActivityWithType:activityType andTag:tag];
}

RCT_EXPORT_METHOD(showActivityWithTag:(NSString *_Nonnull)tag) {
    
    UpshotCustomization *customization = [[UpshotCustomization alloc] init];
    [[BKUIPreferences preferences] setDelegate:customization];

    [[BrandKinesis sharedInstance] setDelegate:self];
    [[BrandKinesis sharedInstance] showActivityWithType:BKActivityTypeAny andTag:tag];
}

RCT_EXPORT_METHOD(showActivityWithId:(NSString *_Nonnull)activityId) {
    
    UpshotCustomization *customization = [[UpshotCustomization alloc] init];
    [[BKUIPreferences preferences] setDelegate:customization];

    [[BrandKinesis sharedInstance] setDelegate:self];
    [[BrandKinesis sharedInstance] showActivityWithActivityId:activityId];
}

RCT_EXPORT_METHOD(removeTutorials) {
    
    [[BrandKinesis sharedInstance] removeTutorials];
}

RCT_EXPORT_METHOD(getUserBadges:(RCTResponseSenderBlock)callback) {
    
    NSDictionary *userBadges = [[BrandKinesis sharedInstance] getUserBadges];
    callback(@[userBadges]);
}

RCT_EXPORT_METHOD(fetchInboxInfo:(RCTResponseSenderBlock)callback) {
    
    [[BrandKinesis sharedInstance] fetchInboxInfoWithCompletionBlock:^(NSArray * _Nonnull inbox) {
        callback(inbox);
    }];
}

#pragma mark UserProfile

RCT_EXPORT_METHOD(setUserProfile:(NSString *_Nonnull)userData) {
     
     NSDictionary *userDict = [self convertJsonStringToJson:userData];
     [self buildUserInfoForParams:userDict];
}

#pragma mark GetUserDetails

RCT_EXPORT_METHOD(getUserDetails:(NSArray *)keys details:(RCTResponseSenderBlock)callback) {
        
    NSDictionary *userDetails = [[BrandKinesis sharedInstance] getUserDetails:keys];
    callback(@[userDetails]);
}

#pragma mark PushNotifications

RCT_EXPORT_METHOD(sendDeviceToken:(NSString *)token) {
    
    BKUserInfo *userInfo = [[BKUserInfo alloc] init];
    BKExternalId *externalId = [[BKExternalId alloc] init];
    externalId.apnsID = token;
    userInfo.externalId = externalId;
    [userInfo buildUserInfoWithCompletionBlock:nil];
}

RCT_EXPORT_METHOD(sendPushClickDetails:(NSString *)pushDetails) {
    
    NSDictionary *payload = [self convertJsonStringToJson:pushDetails];
    [[BrandKinesis sharedInstance] handlePushNotificationWithParams:payload withCompletionBlock:nil];
}

RCT_EXPORT_METHOD(sendPushNotificationWithDetails:(NSString *)pushDetails) {
    
    NSDictionary *payload = [self convertJsonStringToJson:pushDetails];
    [[BrandKinesis sharedInstance] sendPushDetails:payload withCompletionBlock:nil];
}


#pragma mark GDPR

RCT_EXPORT_METHOD(disableUser:(BOOL)shouldDisable) {
    
    [[BrandKinesis sharedInstance] disableUser:shouldDisable completion:nil];
}

#pragma mark Upshot UserId

RCT_EXPORT_METHOD(getUserId:(RCTResponseSenderBlock)callback) {
    
    NSString *userId = [[BrandKinesis sharedInstance] getUserId];
    callback(@[userId]);
}

#pragma mark SDK Vesrion

RCT_EXPORT_METHOD(getSDKVersion:(RCTResponseSenderBlock)callback) {
    
    NSString *version = [BrandKinesis sharedInstance].version;
    callback(@[version]);
}

#pragma mark Rewards

RCT_EXPORT_METHOD(getRewardsList:(RCTResponseSenderBlock)callback) {
    
    [[BrandKinesis sharedInstance] getRewardsStatusWithCompletionBlock:^(NSDictionary * _Nullable response, NSString * _Nullable errorMessage) {
        
        NSString *error = errorMessage ? errorMessage : @"";
        NSDictionary *result = response ? result : @{};
        callback(@[result, error]);
    }];
}

RCT_EXPORT_METHOD(getRewardHistoryForProgram:(NSString *)programId historyType:(BKRewardHistoryType)type callback:(RCTResponseSenderBlock)callback) {
    
    [[BrandKinesis sharedInstance] getRewardHistoryForProgramId:programId withHistoryType:type withCompletionBlock:^(NSDictionary * _Nullable response, NSString * _Nullable errorMessage) {
        
        NSString *error = errorMessage ? errorMessage : @"";
        NSDictionary *result = response ? result : @{};
        callback(@[result, error]);
    }];
}

RCT_EXPORT_METHOD(getRewardRulesforProgram:(NSString *)programId callback:(RCTResponseSenderBlock)callback) {
    
    [[BrandKinesis sharedInstance] getRewardDetailsForProgramId:programId
                                            withCompletionblock:^(NSDictionary * _Nullable response, NSString * _Nullable errorMessage) {
        
        NSString *error = errorMessage ? errorMessage : @"";
        NSDictionary *result = response ? result : @{};
        callback(@[result, error]);
    }];
}

RCT_EXPORT_METHOD(redeemRewardsForProgram:(NSString *)programId transactionAmount:(NSInteger)amount redeemValue:(NSInteger)value tag:(NSString *)tag callback:(RCTResponseSenderBlock)callback) {
    
    [[BrandKinesis sharedInstance] redeemRewardsWithProgramId:programId transactionValue:amount redeemAmout:value tag:tag withCompletionblock:^(NSDictionary * _Nullable response, NSString * _Nullable errorMessage) {
        
        NSString *error = errorMessage ? errorMessage : @"";
        NSDictionary *result = response ? result : @{};
        callback(@[result, error]);
    }];
}


#pragma mark Terminate

RCT_EXPORT_METHOD(terminate) {
    
    [[BrandKinesis sharedInstance] terminate];
}

#pragma mark Authentication Delegate

- (void)brandKinesisAuthentication:(BrandKinesis *)brandKinesis withStatus:(BOOL)status error:(NSError *)error {
        
}

#pragma mark Activity Delegates

- (void)brandKinesisActivity:(BKActivityType)activityType performedActionWithParams:(NSDictionary *)params {
    
      [self sendEventWithName:@"UpshotDeepLink" body:@{@"deepLink": params[@"deepLink"]}];

}

- (void)brandKinesisActivityDidAppear:(BrandKinesis *)brandKinesis forActivityType:(BKActivityType)activityType {
    
    [self sendEventWithName:@"UpshotActivityDidAppear" body:@{@"activityType": [NSNumber numberWithInt:activityType]}];
}

- (void)brandkinesisActivityWillAppear:(BrandKinesis *)brandKinesis forActivityType:(BKActivityType)activityType {
    
    [self sendEventWithName:@"UpshotActivityWillAppear" body:@{@"activityType": [NSNumber numberWithInt:activityType]}];
}

- (void)brandKinesisActivityDidDismiss:(BrandKinesis *)brandKinesis forActivityType:(BKActivityType)activityType {
    
    [self sendEventWithName:@"UpshotActivityDidDismiss" body:@{@"activityType": [NSNumber numberWithInt:activityType]}];
}



#pragma mark Utility Methods

- (NSDictionary *)convertJsonStringToJson:(NSString *)jsonString {
  
  NSData *data = [jsonString dataUsingEncoding:NSUTF8StringEncoding];
  NSDictionary *jsonDict = [NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableLeaves error:nil];
  return jsonDict;
}

- (NSString *)convertToJsonString:(NSDictionary *)json {
  
  NSError *error = nil;
  NSData *data = [NSJSONSerialization dataWithJSONObject:json options:NSJSONWritingPrettyPrinted error:&error];
  if (error == nil) {
    NSString *jsonString = [[NSString alloc] initWithData:data encoding:kCFStringEncodingUTF8];
    return jsonString;
  }
  return @"";
}

- (void)buildUserInfoForParams:(NSDictionary *)dict {
  
  if (dict == nil || dict.allKeys.count < 1) {
    return;
  }
  BKUserInfo *userInfo     = [[BKUserInfo alloc] init];
  BKDob *dob               = [[BKDob alloc] init];
  BKExternalId *externalId = [[BKExternalId alloc] init];
  
  NSMutableDictionary *mutDict = [[NSMutableDictionary alloc] initWithDictionary:dict];
  
  NSMutableDictionary *others = [[NSMutableDictionary alloc] init];
  
  if (dict[@"lat"] && dict[@"lng"]) {
    CLLocation *location = [[CLLocation alloc] initWithLatitude:[dict[@"lat"] floatValue] longitude:[dict[@"lng"] floatValue]];
    [userInfo setLocation:location];
    [mutDict removeObjectForKey:@"lat"];
    [mutDict removeObjectForKey:@"lng"];
  }
  
  if (dict[@"token"]) {
    [externalId setApnsID:dict[@"token"]];
    [mutDict removeObjectForKey:@"token"];
  }
  
  for (NSString *key in [mutDict allKeys]) {
    
    NSString *type = [self getInfoTypeForKey:key];
    
    id object = dict[key];
    if ([object isKindOfClass:[NSNumber class]]) {
      object = [NSNumber numberWithInt:[object intValue]];
    }
    
    if ([type  isEqual:@"BKDob"]) {
      
      [dob setValue:object forKey:key];
      
    }else if ([type isEqual:@"BKExternalId"]){
      
      [externalId setValue:object forKey:key];
      
    } else if ([type isEqual:@"UserInfo"]){
      
      [userInfo setValue:object forKey:key];
      
    }else{
      [others setObject:object forKey:key];
    }
  }
  [userInfo setOthers:others];
  [userInfo setExternalId:externalId];
  [userInfo setDateOfBirth:dob];
  
  [userInfo buildUserInfoWithCompletionBlock:^(BOOL success, NSError *error) {
  }];
}

- (NSString *)getInfoTypeForKey:(NSString *)key{
  
  NSArray *externalIdKeys = @[@"appuID",
                              @"facebookID",
                              @"twitterID",
                              @"foursquareID",
                              @"linkedinID",
                              @"googleplusID",
                              @"enterpriseUID",
                              @"advertisingID",
                              @"instagramID",
                              @"pinterest",
                              @""];
  
  if ([externalIdKeys containsObject:key]) {
    return @"BKExternalId";
  }
  
  NSArray *dobKeys = @[@"year",
                       @"month",
                       @"day"];
  
  if ([dobKeys containsObject:key]) {
    return @"BKDob";
  }
  
  NSArray *userInfoKeys = @[@"lastName",
                            @"middleName",
                            @"firstName",
                            @"language",
                            @"occupation",
                            @"qualification",
                            @"phone",
                            @"localeCode",
                            @"userName",
                            @"email",
                            @"age",
                            @"gender",
                            @"email_opt",
                            @"sms_opt",
                            @"push_opt",
                            @"data_opt",
                            @"ip_opt"];
  
  if ([userInfoKeys containsObject:key]) {
    return @"UserInfo";
  }
  
  return  @"Others";
  
}

@end
