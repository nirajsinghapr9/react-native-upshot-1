//
//  AppDelegate+UpshotPlugin.m
//  PushTest
//
//  Created by Vinod on 3/3/20.
//

#import "AppDelegate+UpshotPlugin.h"

@implementation AppDelegate (UpshotPlugin)

- (void)application:(UIApplication *)application didRegisterForRemoteNotificationsWithDeviceToken:(NSData *)deviceToken {
    
  UpshotReact *react = [[UpshotReact alloc] init];
  [react getDeviceToken:[self getTokenFromdata:deviceToken]];
}

- (void)application:(UIApplication *)application didFailToRegisterForRemoteNotificationsWithError:(NSError *)error {
    UpshotReact *react = [[UpshotReact alloc] init];
    [react getDeviceToken:@""];

}

- (void)application:(UIApplication *)application didReceiveRemoteNotification:(NSDictionary *)userInfo fetchCompletionHandler:(void (^)(UIBackgroundFetchResult))completionHandler {
    
    UpshotReact *react = [[UpshotReact alloc] init];
    [react getPushPayload:userInfo];
}

- (NSString *)getTokenFromdata:(NSData *)data {

  NSUInteger dataLength = data.length;
  if (dataLength == 0) {
    return nil;
  }

  const unsigned char *dataBuffer = (const unsigned char *)data.bytes;
  NSMutableString *hexString  = [NSMutableString stringWithCapacity:(dataLength * 2)];
  for (int i = 0; i < dataLength; ++i) {
    [hexString appendFormat:@"%02x", dataBuffer[i]];
  }
  return [hexString copy];
}

@end
