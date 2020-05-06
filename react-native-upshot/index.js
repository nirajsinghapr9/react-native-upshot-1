import { NativeModules } from 'react-native';

const UpshotReact = NativeModules.UpshotReact;
function functionWithCallback(methodName, args, callback) {

    if (typeof callback === 'undefined' || callback == null || typeof callback !== 'function') {
        callback = (err, res) => {

            if(err) {
                console.log('Upshot' + methodName + 'callback error' , err);
            } else {
                console.log('Upshot' + methodName + 'callback result' , res);
            }                        
        };        
    }

    if(args == null) {
        args = [];
    }
    args.push(callback);
    UpshotReact[methodName].apply(this, args);
}


var Upshot = {
    
    initializeUpshot: function() {
        UpshotReact.initializeUpshot();
    },
         
    createPageViewEvent: function(screenName, callback) {
        functionWithCallback('createPageViewEvent', [screenName], callback); 
    },

    createCustomEvent: function(eventName, payload, isTimed, callback) {
        functionWithCallback('createCustomEvent', [eventName, payload, isTimed], callback); 
    },

    createLocationEvent: function(latitude, longitude) {

        UpshotReact.createLocationEvent(latitude, longitude);
    },

    setValueAndClose: function(payload, eventId) {

        UpshotReact.setValueAndClose(payload, eventId);
    },

    closeEventForId: function(eventId) {

        UpshotReact.closeEventForId(eventId);
    },

    dispatchEventsWithTimedEvents: function(timed) {
        UpshotReact.dispatchEventsWithTimedEvents(timed);
    },

    //Activity methods
    showActivityWithType: function(type, tag) {

        UpshotReact.showActivityWithType(type, tag);
    },

    showActivityWithTag: function(tag) {

        UpshotReact.showActivityWithTag(tag);
    },

    showActivityWithId: function(activityId) {

        UpshotReact.showActivityWithId(activityId);
    },

    removeTutorials: function() {
        UpshotReact.removeTutorials();
    },

    getUserBadges: function(callback) {

        functionWithCallback('getUserBadges', null, callback);
    },

    fetchInboxInfo: function(callback) {
        functionWithCallback('fetchInboxInfo', null, callback);
    },

    //UserProfile methods
    setUserProfile: function(userData) {

        UpshotReact.setUserProfile(userData);
    },

    getUserDetails: function(keys, callback) {

        functionWithCallback('getUserDetails', keys, callback);
    },

    //Pushnotification methods
    sendDeviceToken: function(token) {

        UpshotReact.sendDeviceToken(token);
    },

    sendPushClickDetails: function(pushPayload) {

        UpshotReact.sendPushClickDetails(pushPayload);
    },

    sendCustomPushNotificationWithDetails: function(pushDetails) {

        UpshotReact.sendPushNotificationWithDetails(pushDetails);
    },

    //GDPR methods
    disableUser: function(shouldDisable) {

        UpshotReact.disableUser(shouldDisable);
    },

    //Get Upshot userId method
    getUserId: function(callback) {
        functionWithCallback('getUserId', null, callback);
    },

    //Get Upshot SDK version
    getSDKVersion: function(callback) {

        functionWithCallback('getSDKVersion', null, callback);
    },

    //Rewards
    getRewardsList: function(callback) {
        functionWithCallback('getRewardsList', null, callback);
    },

    getRewardHistoryForProgram: function(programId, historyType, callback) {

        functionWithCallback('getRewardHistoryForProgram', [programId, historyType], callback);
    },

    getRewardRulesforProgram: function(programId, callback) {

        functionWithCallback('getRewardRulesforProgram', [programId], callback);
    },

    redeemRewardsForProgram: function(programId, transactionValue, redeemValue, tag) {

        functionWithCallback('redeemRewardsForProgram', [programId, transactionValue, redeemValue], callback);
    },

    //Terminate Upshot
    terminate: function() {
        UpshotReact.terminate();
    }
};

module.exports = Upshot
