import { NativeModules } from 'react-native';

const UpshotReact = NativeModules.UpshotReact;

var Upshot = {
    
    initializeUpshot: function(options) {
        UpshotReact.initializeUpshotWithOptions(options);
    },
     
    createPageViewEvent: function(screenName) {
        UpshotReact.createPageviewEvent(screenName);
    }
};

module.exports = Upshot;
