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
#import "UpshotUtility.h"

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

RCT_EXPORT_METHOD(initializeUpshotUsingOptions:(NSString *_Nonnull)options) {
    
    NSDictionary *json = [UpshotUtility convertJsonStringToJson:options];
    NSMutableDictionary *initOptions = [[NSMutableDictionary alloc] init];
    if ([json valueForKey:@"bkApplicationID"]) {
        [initOptions setValue:json[@"bkApplicationID"] forKey:BKApplicationID];
    }
    if ([json valueForKey:@"bkApplicationOwnerID"]) {
        [initOptions setValue:json[@"bkApplicationOwnerID"] forKey:BKApplicationOwnerID];
    }
    if ([json valueForKey:@"bkFetchLocation"]) {
        [initOptions setValue:[NSNumber numberWithBool:[json[@"bkFetchLocation"] boolValue]] forKey:BKFetchLocation];
    }
    if ([json valueForKey:@"bkExceptionHandler"]) {
        [initOptions setValue:[NSNumber numberWithBool:[json[@"bkExceptionHandler"] boolValue]] forKey:BKExceptionHandler];
    }
    [[BrandKinesis sharedInstance] initializeWithOptions:initOptions delegate:self];
}

#pragma mark Terminate

RCT_EXPORT_METHOD(terminate) {
    
    [[BrandKinesis sharedInstance] terminate];
}

#pragma mark Events

RCT_EXPORT_METHOD(setDispatchInterval:(NSInteger)interval) {
    
    [[BrandKinesis sharedInstance] setDispatchInterval:interval];
}

RCT_EXPORT_METHOD(createPageViewEvent:(NSString *_Nonnull)currentPage callback:(RCTResponseSenderBlock)callback) {
    
    NSString *eventId = nil;
    if (currentPage && ![currentPage isEqualToString:@""]) {
        eventId = [[BrandKinesis sharedInstance] createEvent:BKPageViewNative params:@{BKCurrentPage: currentPage} isTimed:YES];
    }
    
    if (callback != nil) {
        if (eventId != nil) {
            callback(@[eventId]);
        } else{
            callback(@[[NSNull null]]);
        }
    }
}

RCT_EXPORT_METHOD(createCustomEvent:(NSString *_Nonnull)eventName payload:(NSString *)payload timed:(BOOL)isTimed callback:(RCTResponseSenderBlock)callback) {
    
    NSString *eventId = nil;
    if (eventName && ![eventName isEqualToString: @""]) {
        NSDictionary *eventPayload = [UpshotUtility convertJsonStringToJson:payload];
        eventId = [[BrandKinesis sharedInstance] createEvent:eventName params:eventPayload isTimed:isTimed];
    }
    if (callback != nil) {
        if (eventId != nil) {
            callback(@[eventId]);
        } else{
            callback(@[[NSNull null]]);
        }
    }
}

RCT_EXPORT_METHOD(setValueAndClose:(NSString *)payload forEvent:(NSString *_Nonnull)eventId) {
    
    NSDictionary *eventPayload = [UpshotUtility convertJsonStringToJson:payload];
    [[BrandKinesis sharedInstance] setValueAndClose:eventPayload forEvent:eventId];
}

RCT_EXPORT_METHOD(closeEventForId:(NSString *_Nonnull)eventId) {
    
    [[BrandKinesis sharedInstance] closeEventForID:eventId];
}

RCT_EXPORT_METHOD(dispatchEventsWithTimedEvents:(BOOL)timed callback:(RCTResponseSenderBlock)callback) {
    
    [[BrandKinesis sharedInstance] dispatchEventsWithTimedEvents:timed completionBlock:^(BOOL dispatched) {
        if (callback != nil) {
            callback(@[[NSNumber numberWithBool:dispatched]]);
        }        
    }];
}

RCT_EXPORT_METHOD(createLocationEvent:(NSString *_Nonnull)latitude longitude:(NSString *_Nonnull)longitude) {
    
    CGFloat lat = [latitude floatValue];
    CGFloat lon = [longitude floatValue];
    [[BrandKinesis sharedInstance] createLocationEvent:lat longitude:lon];
}


RCT_EXPORT_METHOD(createAttributionEvent:(NSString *)payload callback:(RCTResponseSenderBlock)callback) {
    
    NSString *eventId = nil;
    if (payload && ![payload isEqualToString: @""]) {
        NSDictionary *eventPayload = [UpshotUtility convertJsonStringToJson:payload];
        eventId = [[BrandKinesis sharedInstance] createAttributionEvent:eventPayload];
    }
    if (callback != nil) {
        if (eventId != nil) {
            callback(@[eventId]);
        } else{
            callback(@[[NSNull null]]);
        }
    }
}

#pragma mark UserProfile

RCT_EXPORT_METHOD(setUserProfile:(NSString *)userData callback:(RCTResponseSenderBlock)callback) {
     
     NSDictionary *userDict = [UpshotUtility convertJsonStringToJson:userData];
    [self buildUserInfoForParams:userDict completionBlock:^(BOOL success, NSError * _Nullable error) {
      callback(@[[NSNumber numberWithBool:success]]);
    }];
}

#pragma mark GetUserDetails

RCT_EXPORT_METHOD(getUserDetails:(RCTResponseSenderBlock)callback) {
        
    NSDictionary *userDetails = [[BrandKinesis sharedInstance] getUserDetails:@[]];
    NSString *jsonString = [UpshotUtility convertJsonObjToJsonString:userDetails];
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
      NSString *jsonString = [UpshotUtility convertJsonObjToJsonString:inbox];
        callback(@[jsonString]);
    }];
}

RCT_EXPORT_METHOD(getUserBadges:(RCTResponseSenderBlock)callback) {
    
    NSMutableDictionary *badges = [[[BrandKinesis sharedInstance] getUserBadges] mutableCopy];
    NSString *jsonString = [UpshotUtility getBadgesData:badges];
    callback(@[jsonString]);
}

#pragma mark PushNotifications

RCT_EXPORT_METHOD(registerForPush:(RCTResponseSenderBlock)callback) {
  
  [self registerForPushWithCallback:callback];
}

RCT_EXPORT_METHOD(sendDeviceToken:(NSString *)token) {
    
    [self updateDeviceToken:token];
}

RCT_EXPORT_METHOD(sendPushDataToUpshot:(NSString *)pushDetails) {
    
    NSDictionary *payload = [UpshotUtility convertJsonStringToJson:pushDetails];
    [self updatePushResponse:payload];
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
          NSString *jsonString = [UpshotUtility convertJsonObjToJsonString:result];
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
          NSString *jsonString = [UpshotUtility convertJsonObjToJsonString:result];
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
          NSString *jsonString = [UpshotUtility convertJsonObjToJsonString:result];
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
          NSString *jsonString = [UpshotUtility convertJsonObjToJsonString:result];
          successCallback(@[jsonString]);
        }
    }];
}

- (void)startObserving {
    
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(applicationDidRegisterWithDeviceToken:) name:@"UpshotDidReceiveDeviceToken" object:nil];
    
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(didReceivePushNotifcationWithResponse:) name:@"UpshotDidReceivePushResponse" object:nil];
}

- (void)stopObserving {
    
    [[NSNotificationCenter defaultCenter] removeObserver:self];
}

#pragma mark Upshot Internal Methods

+ (void)didRegisterForRemoteNotificationsWithDeviceToken:(NSData *)deviceToken {
    
    NSString *token = [UpshotUtility getTokenFromdata:deviceToken];
    [[NSNotificationCenter defaultCenter] postNotificationName:@"UpshotDidReceiveDeviceToken" object:self userInfo:@{@"token": token}];
}

+ (void)didReceiveRemoteNotification:(NSDictionary *)notification {
    
    NSDictionary *userInfo = @{@"payload": notification};
    [[NSNotificationCenter defaultCenter] postNotificationName:@"UpshotDidReceivePushResponse" object:self userInfo: userInfo];
}

- (void)registerForPushWithCallback:(RCTResponseSenderBlock)callback {
      
    if (@available(iOS 10.0, *) ) {
        
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

- (void)updateDeviceToken:(NSString *)token {
    
    BKUserInfo *userInfo = [[BKUserInfo alloc] init];
    BKExternalId *externalId = [[BKExternalId alloc] init];
    externalId.apnsID = token;
    userInfo.externalId = externalId;
    [userInfo buildUserInfoWithCompletionBlock:nil];
}

- (void)updatePushResponse:(NSDictionary *)response {
 
    [[BrandKinesis sharedInstance] handlePushNotificationWithParams:response withCompletionBlock:nil];
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
    
    NSString *type = [UpshotUtility getInfoTypeForKey:key];
    
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


#pragma mark Upshot Events

- (void)brandKinesisAuthentication:(BrandKinesis *)brandKinesis withStatus:(BOOL)status error:(NSError *)error {
    
    [self sendEventWithName:@"UpshotAuthStatus" body:@{@"status": [NSNumber numberWithBool:status], @"error": error ? error : [NSNull null]}];
}

- (void)brandKinesisActivity:(BKActivityType)activityType performedActionWithParams:(NSDictionary *)params {
    
      [self sendEventWithName:@"UpshotDeepLink" body:@{@"deepLink": params[@"deepLink"]}];
}

- (void)brandKinesisActivityDidAppear:(BrandKinesis *)brandKinesis forActivityType:(BKActivityType)activityType {
    
    [self sendEventWithName:@"UpshotActivityDidAppear" body:@{@"activityType": [NSNumber numberWithInteger:activityType]}];
}

- (void)brandKinesisActivityDidDismiss:(BrandKinesis *)brandKinesis forActivityType:(BKActivityType)activityType {
    
    [self sendEventWithName:@"UpshotActivityDidDismiss" body:@{@"activityType": [NSNumber numberWithInteger:activityType]}];
}

- (void)applicationDidRegisterWithDeviceToken:(NSNotification *)notification {
    
    NSString *deviceToken = notification.userInfo[@"token"];
    [self updateDeviceToken:deviceToken];
    [self sendEventWithName:@"UpshotPushToken" body:@{@"token": deviceToken}];
}

- (void)didReceivePushNotifcationWithResponse:(NSNotification *)notification {
    
    NSDictionary *payload = notification.userInfo[@"payload"];
    [self updatePushResponse:payload];
    [self sendEventWithName:@"UpshotPushPayload" body:@{@"payload": [UpshotUtility convertJsonObjToJsonString:payload]}];
}

- (void)brandKinesisCarouselPushClickPayload:(NSDictionary *_Nonnull)payload {

    [self sendEventWithName:@"UpshotCarouselPushClick" body:@{@"payload": [UpshotUtility convertJsonObjToJsonString:payload]}];
}

- (void)brandkinesisCampaignDetailsLoaded {

    [self sendEventWithName:@"UpshotCampaignDetailsLoaded" body:@{}];
}
@end
