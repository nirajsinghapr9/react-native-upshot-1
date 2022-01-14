//
//  UpshotUtility.m
//  UpshotReact
//
//  Created by Vinod K on 9/3/20.
//  Copyright © 2020 [x]cubeLabs. All rights reserved.
//

#import "UpshotUtility.h"

@implementation UpshotUtility


+ (NSDictionary *)convertJsonStringToJson:(NSString *)jsonString {
  
  NSData *data = [jsonString dataUsingEncoding:NSUTF8StringEncoding];
  NSDictionary *jsonDict = [NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableLeaves error:nil];
  return jsonDict;
}

+ (NSString *)convertJsonObjToJsonString:(id)json {
  
  NSError *error = nil;
  NSData *data = [NSJSONSerialization dataWithJSONObject:json options:NSJSONWritingPrettyPrinted error:&error];
  if (error == nil) {
    NSString *jsonString = [[NSString alloc] initWithData:data encoding:kCFStringEncodingUTF8];
    return jsonString;
  }
  return @"";
}

+ (NSString *)getTokenFromdata:(NSData *)data {

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

+ (NSString *)writeImageToTemp:(UIImage *)image withName:(NSString *)name {
    
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

+ (NSString *)getInfoTypeForKey:(NSString *)key {
  
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

+ (NSString *)getBadgesData:(NSDictionary *)badgesPayload {
        
    NSMutableArray *activeBadges = badgesPayload[@"active_list"];
    NSMutableArray *inactiveBadges = badgesPayload[@"inactive_list"];

    for (int index = 0; index < [activeBadges count]; index++) {
        
        NSMutableDictionary *badge = [[NSMutableDictionary alloc] initWithDictionary:[activeBadges objectAtIndex:index]];
        [badge setObject:[UpshotUtility writeImageToTemp:badge[@"badgeImage"] withName:badge[@"badge"]] forKey:@"image"];
        [badge setObject:badge[@"badgeDesc"] forKey:@"desc"];
        [badge removeObjectForKey:@"badgeImage"];
        [badge removeObjectForKey:@"badgeDesc"];
        [activeBadges replaceObjectAtIndex:index withObject:badge];
    }
    
    for (int index = 0; index < [inactiveBadges count]; index++) {
        
        NSMutableDictionary *badge = [[NSMutableDictionary alloc] initWithDictionary:[inactiveBadges objectAtIndex:index]];
        [badge setObject:[UpshotUtility writeImageToTemp:badge[@"badgeImage"] withName:badge[@"badge"]] forKey:@"image"];
        [badge setObject:badge[@"badgeDesc"] forKey:@"desc"];
        [badge removeObjectForKey:@"badgeImage"];
        [badge removeObjectForKey:@"badgeDesc"];
        [inactiveBadges replaceObjectAtIndex:index withObject:badge];
    }
    
    NSDictionary *badges = @{@"active_list": activeBadges, @"inactive_list": inactiveBadges};
    NSString *jsonString = [UpshotUtility convertJsonObjToJsonString:badges];
    return jsonString;
}
@end
