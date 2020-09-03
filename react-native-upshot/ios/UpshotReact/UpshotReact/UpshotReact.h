//
//  UpshotReact.h
//  UpshotReact
//
//  Created by Vinod on 07/01/20.
//  Copyright © 2020 [x]cubeLabs. All rights reserved.
//

#import <React/RCTBridgeModule.h>
#import <React/RCTEventEmitter.h>

@import Upshot;

@interface UpshotReact : RCTEventEmitter <RCTBridgeModule, BrandKinesisDelegate, UNUserNotificationCenterDelegate>

+ (void)didRegisterForRemoteNotificationsWithDeviceToken:(NSData *)deviceToken;

+ (void)didReceiveRemoteNotification:(NSDictionary *)notification;

@end
