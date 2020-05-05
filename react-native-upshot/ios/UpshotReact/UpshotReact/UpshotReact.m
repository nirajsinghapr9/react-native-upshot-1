//
//  UpshotReact.m
//  UpshotReact
//
//  Created by Vinod on 07/01/20.
//  Copyright © 2020 [x]cubeLabs. All rights reserved.
//

#import "UpshotReact.h"
#import "UpshotCustomization.h"
#import "RCTConvert+Upshot.h"
#import <React/RCTBridge.h>
#import <React/RCTEventDispatcher.h>
#import "AppDelegate+UpshotPlugin.h"

@implementation UpshotReact

RCT_EXPORT_MODULE();

#pragma mark SupportedEvents

- (NSArray<NSString *> *)supportedEvents {
    
  return @[@"UpshotDeepLink",
           @"UpshotActivityDidAppear",
           @"UpshotActivityWillAppear",
           @"UpshotActivityDidDismiss",
           @"UpshotAuthStatus",
           @"UpshotPushToken",
           @"UpshotPushPayload"];
}



#pragma mark Initialize Upshot
 
RCT_EXPORT_METHOD(initializeUpshot) {
    
    [[BrandKinesis sharedInstance] initializeWithDelegate:self];
}


#pragma mark PageViewEvent

RCT_EXPORT_METHOD(createPageViewEvent:(NSString *_Nonnull)currentPage callback:(RCTResponseSenderBlock)callback) {
    
    if (currentPage && ![currentPage isEqualToString:@""]) {
      NSString *eventId = [[BrandKinesis sharedInstance] createEvent:BKPageViewNative params:@{BKCurrentPage: currentPage} isTimed:YES];
      callback(@[eventId]);
    } else {
      callback(@[[NSNull null]]);
    }
}

#pragma mark CustomEvent

RCT_EXPORT_METHOD(createCustomEvent:(NSString *_Nonnull)eventName payload:(NSString *)payload timed:(BOOL)isTimed callback:(RCTResponseSenderBlock)callback) {
    
    if (eventName && ![eventName isEqualToString: @""]) {
        NSDictionary *eventPayload = [self convertJsonStringToJson:payload];
        NSString *eventId = [[BrandKinesis sharedInstance] createEvent:eventName params:eventPayload isTimed:isTimed];
        callback(@[eventId]);
    } else {
      callback(@[[NSNull null]]);
    }
}

#pragma mark Close And Dispatch Events

RCT_EXPORT_METHOD(setValueAndClose:(NSString *)payload forEvent:(NSString *_Nonnull)eventId) {
    
    NSDictionary *eventPayload = [self convertJsonStringToJson:payload];
    [[BrandKinesis sharedInstance] setValueAndClose:eventPayload forEvent:eventId];
}

RCT_EXPORT_METHOD(closeEventForId:(NSString *_Nonnull)eventId) {
    
    [[BrandKinesis sharedInstance] closeEventForID:eventId];
}

RCT_EXPORT_METHOD(dispatchEventsWithTimedEvents:(BOOL)timed callback:(RCTResponseSenderBlock)callback) {
    
    [[BrandKinesis sharedInstance] dispatchEventsWithTimedEvents:timed completionBlock:^(BOOL dispatched) {
        callback(@[[NSNumber numberWithBool:dispatched]]);
    }];
}

#pragma mark LocationEvent

RCT_EXPORT_METHOD(createLocationEvent:(NSString *_Nonnull)latitude longitude:(NSString *_Nonnull)longitude) {
    
    CGFloat lat = [latitude floatValue];
    CGFloat lon = [longitude floatValue];
    [[BrandKinesis sharedInstance] createLocationEvent:lat longitude:lon];
}

#pragma mark UserProfile

RCT_EXPORT_METHOD(setUserProfile:(NSString *)userData callback:(RCTResponseSenderBlock)callback) {
     
     NSDictionary *userDict = [self convertJsonStringToJson:userData];
    [self buildUserInfoForParams:userDict completionBlock:^(BOOL success, NSError * _Nullable error) {
      callback(@[[NSNumber numberWithBool:success]]);
    }];
}

#pragma mark GetUserDetails

RCT_EXPORT_METHOD(getUserDetails:(RCTResponseSenderBlock)callback) {
        
    NSDictionary *userDetails = [[BrandKinesis sharedInstance] getUserDetails:@[]];
    NSString *jsonString = [self convertToJsonString:userDetails];
    callback(@[jsonString]);
}

#pragma mark Upshot Actions

RCT_EXPORT_METHOD(showActivityWithType:(NSInteger)type andTag:(NSString *)tag) {
  
    UpshotCustomization *customization = [[UpshotCustomization alloc] init];
    [[BKUIPreferences preferences] setDelegate:customization];
    BKActivityType activityType = (BKActivityType)type;
    [[BrandKinesis sharedInstance] setDelegate:self];
    [[BrandKinesis sharedInstance] showActivityWithType:activityType andTag:tag];
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

RCT_EXPORT_METHOD(fetchInboxInfo:(RCTResponseSenderBlock)callback) {
    
    [[BrandKinesis sharedInstance] fetchInboxInfoWithCompletionBlock:^(NSArray * _Nonnull inbox) {
      NSString *jsonString = [self convertToJsonString:inbox];
        callback(@[jsonString]);
    }];
}

RCT_EXPORT_METHOD(getUserBadges:(RCTResponseSenderBlock)callback) {
    
    NSMutableDictionary *badges = [[[BrandKinesis sharedInstance] getUserBadges] mutableCopy];
    NSMutableArray *activeBadges = [badges[@"active_list"] mutableCopy];
    NSMutableArray *inactiveBadges = [badges[@"inactive_list"] mutableCopy];
    
    for (int index = 0; index < [activeBadges count]; index++) {
        
        NSMutableDictionary *badge = [[NSMutableDictionary alloc] initWithDictionary:[activeBadges objectAtIndex:index]];
        [badge setObject:[self writeImageToTemp:badge[@"badgeImage"] withName:badge[@"badge"]] forKey:@"image"];
        [badge setObject:badge[@"badgeDesc"] forKey:@"desc"];
        [badge removeObjectForKey:@"badgeImage"];
        [badge removeObjectForKey:@"badgeDesc"];
        [activeBadges replaceObjectAtIndex:index withObject:badge];
    }
    
    for (int index = 0; index < [inactiveBadges count]; index++) {
        
        NSMutableDictionary *badge = [[NSMutableDictionary alloc] initWithDictionary:[inactiveBadges objectAtIndex:index]];
        [badge setObject:[self writeImageToTemp:badge[@"badgeImage"] withName:badge[@"badge"]] forKey:@"image"];
        [badge setObject:badge[@"badgeDesc"] forKey:@"desc"];
        [badge removeObjectForKey:@"badgeImage"];
        [badge removeObjectForKey:@"badgeDesc"];
        [inactiveBadges replaceObjectAtIndex:index withObject:badge];
    }
  
    
    [badges setObject:activeBadges forKey:@"active_list"];
    [badges setObject:inactiveBadges forKey:@"inactive_list"];

    NSString *jsonString = [self convertToJsonString:badges];
    callback(@[jsonString]);
}

#pragma mark PushNotifications

RCT_EXPORT_METHOD(registerForPush:(BOOL)enableForeground callback:(RCTResponseSenderBlock)callback) {
  
  [self registerForPush:enableForeground callback:callback];
}

RCT_EXPORT_METHOD(sendDeviceToken:(NSString *)token) {
    
  dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(1.0 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
          BKUserInfo *userInfo = [[BKUserInfo alloc] init];
          BKExternalId *externalId = [[BKExternalId alloc] init];
          externalId.apnsID = token;
          userInfo.externalId = externalId;
          [userInfo buildUserInfoWithCompletionBlock:nil];

  });
}

RCT_EXPORT_METHOD(sendPushDataToUpshot:(NSString *)pushDetails) {
    
  dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(.02 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
      
      NSDictionary *payload = [self convertJsonStringToJson:pushDetails];
      [[BrandKinesis sharedInstance] handlePushNotificationWithParams:payload withCompletionBlock:nil];
  });
}

RCT_EXPORT_METHOD(sendPushNotificationWithDetails:(NSString *)pushDetails callback:(RCTResponseSenderBlock)callback) {
    
    NSDictionary *payload = [self convertJsonStringToJson:pushDetails];
  [[BrandKinesis sharedInstance] sendPushDetails:payload withCompletionBlock:^(BOOL status, NSError * _Nullable error) {
        callback(@[[NSNumber numberWithBool:status]]);
  }];
}


#pragma mark GDPR

RCT_EXPORT_METHOD(disableUser:(BOOL)shouldDisable callback:(RCTResponseSenderBlock)callback) {
    
  [[BrandKinesis sharedInstance] disableUser:shouldDisable completion:^(BOOL status, NSError * _Nullable error) {
    callback(@[[NSNumber numberWithBool:status]]);
  }];
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

RCT_EXPORT_METHOD(getRewardsList:(RCTResponseSenderBlock)successCallback error:(RCTResponseSenderBlock)failureCallback) {
    
    [[BrandKinesis sharedInstance] getRewardsStatusWithCompletionBlock:^(NSDictionary * _Nullable response, NSString * _Nullable errorMessage) {
        
        NSString *error = errorMessage ? errorMessage : @"";
        if (errorMessage.length > 0) {
            failureCallback(@[error]);
        } else {
          NSDictionary *result = response ? response : @{};
          NSString *jsonString = [self convertToJsonString:result];
          successCallback(@[jsonString]);
        }
    }];
}

RCT_EXPORT_METHOD(getRewardHistoryForProgram:(NSString *)programId historyType:(NSInteger)type callback:(RCTResponseSenderBlock)successCallback error:(RCTResponseSenderBlock)failureCallback) {
    
  BKRewardHistoryType historyType = (BKRewardHistoryType)type;
    [[BrandKinesis sharedInstance] getRewardHistoryForProgramId:programId withHistoryType:historyType withCompletionBlock:^(NSDictionary * _Nullable response, NSString * _Nullable errorMessage) {
      
        NSString *error = errorMessage ? errorMessage : @"";
        if (errorMessage.length > 0) {
            failureCallback(@[error]);
        } else {
          NSDictionary *result = response ? response : @{};
          NSString *jsonString = [self convertToJsonString:result];
          successCallback(@[jsonString]);
        }
    }];
}

RCT_EXPORT_METHOD(getRewardRulesforProgram:(NSString *)programId callback:(RCTResponseSenderBlock)successCallback error:(RCTResponseSenderBlock)failureCallback) {
    
    [[BrandKinesis sharedInstance] getRewardDetailsForProgramId:programId
                                            withCompletionblock:^(NSDictionary * _Nullable response, NSString * _Nullable errorMessage) {
        
        NSString *error = errorMessage ? errorMessage : @"";
        if (errorMessage.length > 0) {
            failureCallback(@[error]);
        } else {
          NSDictionary *result = response ? response : @{};
          NSString *jsonString = [self convertToJsonString:result];
          successCallback(@[jsonString]);
        }
    }];
}

RCT_EXPORT_METHOD(redeemRewardsForProgram:(NSString *)programId transactionAmount:(NSInteger)amount redeemValue:(NSInteger)value tag:(NSString *)tag callback:(RCTResponseSenderBlock)successCallback error:(RCTResponseSenderBlock)failureCallback) {
    
    [[BrandKinesis sharedInstance] redeemRewardsWithProgramId:programId transactionValue:amount redeemAmout:value tag:tag withCompletionblock:^(NSDictionary * _Nullable response, NSString * _Nullable errorMessage) {
        
        NSString *error = errorMessage ? errorMessage : @"";
        if (errorMessage.length > 0) {
            failureCallback(@[error]);
        } else {
          NSDictionary *result = response ? response : @{};
          NSString *jsonString = [self convertToJsonString:result];
          successCallback(@[jsonString]);
        }
    }];
}


#pragma mark Terminate

RCT_EXPORT_METHOD(terminate) {
    
    [[BrandKinesis sharedInstance] terminate];
}

#pragma mark Authentication Delegate

- (void)brandKinesisAuthentication:(BrandKinesis *)brandKinesis withStatus:(BOOL)status error:(NSError *)error {
    
    [self sendEventWithName:@"UpshotAuthStatus" body:@{@"status": [NSNumber numberWithBool:status], @"error": error ? error : [NSNull null]}];
}

#pragma mark Activity Delegates

- (void)brandKinesisActivity:(BKActivityType)activityType performedActionWithParams:(NSDictionary *)params {
    
      [self sendEventWithName:@"UpshotDeepLink" body:@{@"deepLink": params[@"deepLink"]}];
}

- (void)brandKinesisActivityDidAppear:(BrandKinesis *)brandKinesis forActivityType:(BKActivityType)activityType {
    
    [self sendEventWithName:@"UpshotActivityDidAppear" body:@{@"activityType": [NSNumber numberWithInteger:activityType]}];
}

- (void)brandkinesisActivityWillAppear:(BrandKinesis *)brandKinesis forActivityType:(BKActivityType)activityType {
    
    [self sendEventWithName:@"UpshotActivityWillAppear" body:@{@"activityType": [NSNumber numberWithInteger:activityType]}];
}

- (void)brandKinesisActivityDidDismiss:(BrandKinesis *)brandKinesis forActivityType:(BKActivityType)activityType {
    
    [self sendEventWithName:@"UpshotActivityDidDismiss" body:@{@"activityType": [NSNumber numberWithInteger:activityType]}];
}

#pragma mark Notification Delegates

- (void)userNotificationCenter:(UNUserNotificationCenter *)center willPresentNotification:(UNNotification *)notification withCompletionHandler:(void (^)(UNNotificationPresentationOptions))completionHandler {
    
    if (self.allowForegroundPush) {

        completionHandler(UNAuthorizationOptionAlert | UNAuthorizationOptionBadge | UNAuthorizationOptionSound);
    }
}

#pragma mark Utility Methods

- (NSDictionary *)convertJsonStringToJson:(NSString *)jsonString {
  
  NSData *data = [jsonString dataUsingEncoding:NSUTF8StringEncoding];
  NSDictionary *jsonDict = [NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableLeaves error:nil];
  return jsonDict;
}

- (NSString *)convertToJsonString:(id)json {
  
  NSError *error = nil;
  NSData *data = [NSJSONSerialization dataWithJSONObject:json options:NSJSONWritingPrettyPrinted error:&error];
  if (error == nil) {
    NSString *jsonString = [[NSString alloc] initWithData:data encoding:kCFStringEncodingUTF8];
    return jsonString;
  }
  return @"";
}

- (void)registerForPushWithForeground:(BOOL)foregroundPush callback:(RCTResponseSenderBlock)callback {
      
  self.allowForegroundPush = foregroundPush;
    if ([[[UIDevice currentDevice] systemVersion]floatValue] >= 10.0 ) {
        
        UNUserNotificationCenter *notificationCenter = [UNUserNotificationCenter currentNotificationCenter];
        [notificationCenter requestAuthorizationWithOptions:(UNAuthorizationOptionAlert | UNAuthorizationOptionBadge | UNAuthorizationOptionSound ) completionHandler:^(BOOL granted, NSError * _Nullable error) {
            if (granted) {
                [notificationCenter setDelegate:self];
            }
          callback(@[[NSNumber numberWithBool:granted]]);
        }];
    } else {
        
        UIUserNotificationType types = (UIUserNotificationTypeAlert|
                                        UIUserNotificationTypeSound|
                                        UIUserNotificationTypeBadge);
        UIUserNotificationSettings *settings = [UIUserNotificationSettings settingsForTypes:types categories:nil];
        [[UIApplication sharedApplication] registerUserNotificationSettings:settings];
        [[UIApplication sharedApplication] registerForRemoteNotifications];
    }

    dispatch_async(dispatch_get_main_queue(), ^{
        [[UIApplication sharedApplication] registerForRemoteNotifications];
    });
}

- (void)getDeviceToken:(NSString *)token {
  
      [self sendEventWithName:@"UpshotPushToken" body:@{@"token": token}];
    dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(1.0 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
          BKUserInfo *userInfo = [[BKUserInfo alloc] init];
          BKExternalId *externalId = [[BKExternalId alloc] init];
          externalId.apnsID = token;
          userInfo.externalId = externalId;
          [userInfo buildUserInfoWithCompletionBlock:nil];
  });
}

- (void)getPushPayload:(NSDictionary *)payload {
  
    [self sendEventWithName:@"UpshotPushPayload" body:@{@"payload": [self convertToJsonString:userDetails]}];
    [[BrandKinesis sharedInstance] handlePushNotificationWithParams:payload withCompletionBlock:nil];
}

- (void)buildUserInfoForParams:(NSDictionary *)dict completionBlock:(void(^)(BOOL success,  NSError * _Nullable error))block {
  
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
      block(success, error);
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
                              ];
  
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
                            @"maritalStatus",
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

- (NSString *)writeImageToTemp:(UIImage *)image withName:(NSString *)name {
    
    if (!image) {
        return nil;
    }
    NSString* tempPath = [NSSearchPathForDirectoriesInDomains(NSCachesDirectory,
                                                              NSUserDomainMask,
                                                              YES) lastObject];
  
    NSString *filePath = [tempPath stringByAppendingPathComponent:[NSString stringWithFormat:@"%@%@",name,@".png"]];
    NSData *pngData = UIImagePNGRepresentation(image);
    [pngData writeToFile:filePath atomically:YES];
    return filePath;
}


@end
