//
//  UpshotUtility.h
//  UpshotReact
//
//  Created by Vinod K on 9/3/20.
//  Copyright © 2020 [x]cubeLabs. All rights reserved.
//

#import <Foundation/Foundation.h>
@import UIKit;

NS_ASSUME_NONNULL_BEGIN

@interface UpshotUtility : NSObject

+ (NSDictionary *)convertJsonStringToJson:(NSString *)jsonString;

+ (NSString *)convertJsonObjToJsonString:(id)json;

+ (NSString *)getTokenFromdata:(NSData *)data;

+ (NSString *)writeImageToTemp:(UIImage *)image withName:(NSString *)name;

+ (NSString *)getInfoTypeForKey:(NSString *)key;

+ (NSString *)getBadgesData:(NSDictionary *)badgesPayload;

@end

NS_ASSUME_NONNULL_END
